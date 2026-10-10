$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot
New-Item -ItemType Directory -Force bin | Out-Null
$fuentes = @(Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
& javac -encoding UTF-8 --release 17 -d bin $fuentes
if ($LASTEXITCODE -ne 0) { throw 'La compilación falló.' }
Write-Host 'Compilación correcta. Archivos generados en bin.'
