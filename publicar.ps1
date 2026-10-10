$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot

function Invoke-GitChecked {
    param([Parameter(ValueFromRemainingArguments = $true)][string[]]$GitArguments)
    & git @GitArguments
    if ($LASTEXITCODE -ne 0) {
        throw "Git fallo: $($GitArguments -join ' '). No se continuara con la publicacion."
    }
}

& powershell -ExecutionPolicy Bypass -File .\compilar.ps1
if ($LASTEXITCODE -ne 0) { throw 'No se pudo compilar.' }
& java '-Dfile.encoding=UTF-8' -cp bin qtz2.pruebas.Pruebas
if ($LASTEXITCODE -ne 0) { throw 'Las pruebas fallaron.' }

if (-not (Test-Path -LiteralPath '.git')) { Invoke-GitChecked init -b main }
$repositoryUrl = 'https://github.com/jacobjuarezguerra/defensa-qtz2.git'
$originUrl = & git remote get-url origin 2>$null
if ($LASTEXITCODE -ne 0) {
    Invoke-GitChecked remote add origin $repositoryUrl
} elseif ($originUrl -ne $repositoryUrl) {
    throw "El remoto origin apunta a otro repositorio: $originUrl"
}

Invoke-GitChecked add -- src bin output/pdf/analisis-diseno.pdf README.md .gitignore compilar.ps1 ejecutar.cmd publicar.ps1
& git diff --cached --quiet
if ($LASTEXITCODE -eq 1) {
    Invoke-GitChecked commit -m 'Entrega completa: fuentes, compilados y analisis UML'
} elseif ($LASTEXITCODE -ne 0) {
    throw 'No se pudieron comprobar los cambios preparados.'
}

# Integra la documentacion ya publicada sin sobrescribir el historial remoto.
Invoke-GitChecked fetch origin main
Invoke-GitChecked merge origin/main --allow-unrelated-histories --no-edit
Invoke-GitChecked push -u origin HEAD:main
Write-Host 'Entrega publicada: https://github.com/jacobjuarezguerra/defensa-qtz2'
