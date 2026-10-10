# Defensa de QTZ2

Ejercicio 5 de CC2008: simulador de consola con herencia, polimorfismo y MVC.

## Requisitos y ejecución

Java 17 o posterior. No requiere bibliotecas externas. Desde esta carpeta:

```powershell
powershell -ExecutionPolicy Bypass -File .\compilar.ps1
java '-Dfile.encoding=UTF-8' -cp bin qtz2.Main
```

En Windows también se puede abrir `ejecutar.cmd` con doble clic. `bin` incluye los
compilados de la entrega; `src` incluye todo el código. Ambos se versionan por
solicitud del autor. Recompilar después de cualquier cambio en las fuentes.

## Pruebas

```powershell
java '-Dfile.encoding=UTF-8' -cp bin qtz2.pruebas.Pruebas
```

Las pruebas cubren el ejemplo oficial, recursos persistentes, inactividad,
energía insuficiente, descarga parcial, orden de operación, consultas, empates,
IDs únicos y conservación de datos durante 20 ciclos.

## Organización

- `src/qtz2/modelo`: módulos, misión y recursos compartidos.
- `src/qtz2/vista`: entrada y salida por consola.
- `src/qtz2/controlador`: coordinación del menú.
- `src/qtz2/Main.java`: inicio y carga de diez módulos.
- `src/qtz2/pruebas`: comprobaciones sin dependencias.
- `bin`: clases Java compiladas con destino Java 17.
- `output/pdf/analisis-diseno.pdf`: análisis y diagrama UML.

En NetBeans, crear un proyecto Java con fuentes existentes, seleccionar `src`
como directorio de fuentes y `qtz2.Main` como clase principal. Los scripts
permiten trabajar sin depender de una versión específica de NetBeans.

## Reglas y decisiones

La misión comienza en el ciclo 10 con 20 unidades de energía, 80 MB en órbita y
160 MB descargados. Los instrumentos han recolectado 240 MB históricamente.
Los ciclos efectivos previos pueden diferir porque los módulos no siempre operan.
La salud se almacena entre 0 y 100; no controla la actividad porque el enunciado
no define ese efecto. Las capacidades son constantes y los históricos de vuelo
y energía se calculan a partir de ciclos efectivos. No se incluyen baterías.

Cada módulo modifica los recursos antes de la operación del siguiente. El
catálogo ordena una copia, conservando el orden inicial. La búsqueda combina ID
exacto con nombre completo sin distinción de mayúsculas. Todos los empates en
descargas se incluyen, incluso si los módulos están inactivos. El estado dura
durante la ejecución; al reiniciar se recupera la carga inicial.

## Entrega en GitHub

Subir `src`, `bin`, `output`, este README y los scripts. No subir `tmp`.
El repositorio remoto debe ser proporcionado o seleccionado por el autor.
La rúbrica anuncia 55 puntos de implementación pero desglosa 45; consultar la
diferencia al docente. Esto no altera los requisitos implementados.
