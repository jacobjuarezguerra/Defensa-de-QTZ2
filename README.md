# Defensa de QTZ2

Proyecto del ejercicio 5 de CC2008, realizado en Java.

El programa simula una misión con diez módulos. Se usa desde un menú de texto y permite llevar el control de la energía, los datos recolectados y las descargas.

La misión avanza por ciclos. Cada ciclo es un turno en el que los módulos pueden realizar sus tareas.

## Requisitos del programa

- Tener instalado Java 17 o una versión posterior, con las herramientas para preparar y ejecutar el código (JDK).
- Tener los archivos del proyecto en una misma carpeta.

No hace falta instalar bibliotecas adicionales.

## Cómo ejecutar el programa

En Windows, puedes abrir `ejecutar.cmd` con doble clic.

También puedes abrir PowerShell dentro de la carpeta del proyecto y ejecutar estos comandos en orden:

```powershell
powershell -ExecutionPolicy Bypass -File .\compilar.ps1
java '-Dfile.encoding=UTF-8' -cp bin qtz2.Main
```

El primer comando prepara el código para que Java pueda ejecutarlo. El segundo inicia el programa.

Si cambias el código de la carpeta `src`, vuelve a ejecutar ambos comandos para usar la versión actualizada.

## Cómo ejecutar las pruebas

El proyecto incluye comprobaciones para revisar el funcionamiento del programa. Después de preparar el código, ejecuta:

```powershell
java '-Dfile.encoding=UTF-8' -cp bin qtz2.pruebas.Pruebas
```

Estas pruebas revisan:

- El ejemplo proporcionado en el enunciado.
- Que la energía y los datos se mantengan correctamente entre ciclos.
- Qué ocurre cuando un módulo está inactivo o no tiene suficiente energía.
- Las descargas que solo pueden completarse en parte.
- El orden en que trabajan los módulos.
- Las consultas y los empates en la cantidad de datos descargados.
- Que cada módulo tenga un identificador diferente.
- Que los datos se conserven correctamente durante 20 ciclos.

## Cómo están organizados los archivos

| Carpeta o archivo | Para qué sirve |
| --- | --- |
| `src/qtz2/modelo` | Guarda la información de los módulos, la misión y los recursos que comparten. También contiene las reglas de funcionamiento. |
| `src/qtz2/vista` | Muestra los mensajes y recibe lo que escribe el usuario. |
| `src/qtz2/controlador` | Conecta las opciones del menú con las acciones del programa. |
| `src/qtz2/Main.java` | Inicia el programa y carga los diez módulos. |
| `src/qtz2/pruebas` | Contiene las comprobaciones del programa. |
| `bin` | Contiene los archivos preparados para ejecutarse con Java 17 o posterior. |
| `output/pdf/analisis-diseno.pdf` | Explica el diseño e incluye un diagrama de cómo se relacionan las partes del programa. |

El proyecto practica cómo compartir características entre módulos y cómo hacer que cada tipo realice su propia tarea. También separa las reglas de la misión, los mensajes del menú y las acciones que elige el usuario.

Se incluyen tanto `src` como `bin` por decisión del autor.


## Datos al iniciar la misión

| Dato | Valor inicial |
| --- | --- |
| Ciclo actual | 10 |
| Energía disponible | 20 unidades |
| Datos en órbita | 80 MB |
| Datos descargados | 160 MB |
| Total recolectado por los instrumentos hasta ese momento | 240 MB |

Los módulos pueden tener distintas cantidades de ciclos trabajados, porque no todos funcionan en cada ciclo.

## Reglas del programa

- **Los módulos trabajan en orden.** Cada uno cambia la energía o los datos disponibles antes de que trabaje el siguiente.
- **La salud va de 0 a 100.** Este valor no decide si un módulo funciona, porque el enunciado no indica que deba hacerlo.
- **Las capacidades no cambian.** Los valores acumulados de vuelo y energía se calculan según los ciclos en los que cada módulo realmente trabajó.
- **No se incluyen baterías.**
- **Ordenar el catálogo no cambia el orden de trabajo.** Solo se ordena una copia para mostrarla.
- **La búsqueda usa el identificador exacto o el nombre completo.** Al buscar por nombre, da igual escribir en mayúsculas o minúsculas.
- **Los empates en descargas muestran a todos los módulos empatados.** También se incluyen los que estén inactivos.
- **Los cambios se conservan mientras el programa está abierto.** Al cerrarlo y volverlo a abrir, la misión comienza con los datos iniciales.

