## Readme de MyCar ##

Esta aplicacion es el fruto del Trabajo de Fin de Grado del mismo nombre.

Aplicacion Android de ayuda al mantenimiento correcto del vehiculo a traves del sistema OBD2.
Sus funciones basicas son 2: analisis del vehiculo mediante el sistema OBD2 enfocado al publico general, y una lista predictiva y detallada de los recambios efectuados

Programado por: David Pérez Núñez
Titulado por: Pedro Ángel Castillo Valdivieso
Basado en: Pires | android-obd-reader | https://github.com/pires/android-obd-reader

## Caracteristicas ##

Aplicacion Android programada en Java con una estructura estandar (separacion FrontEnd-BackEnd-Build).
Trabajo realizado en Android Studio.

La conectividad con el sistema OBD2 se ha realizado a traves de librerias externas del mismo Pires, incluidas en la base de la que se ha partido.
La base de datos esta hecha en SQL, mediante la libreria SQLiteOpenHelper

## Requisitos ##

Se necesitan 3 dispositivos para su correcto funcionamiento:

- Terminal de desarrollo que compile el proyecto y lo mande al terminal objetivo:
	No precisa de caracteristicas minimas mas que el que pueda albergar Android Studio y conectarse con el dispositivo objetivo.

- Terminal objetivo que ejecute el programa y se conecte al vehiculo:
	Los requisitos minimos de software para la aplicacion vienen en el archivo build.gradle
	Los requisitos minimos de hardware son: capacidad de conexion con el terminal de desarrollo y con el interfaz para el sisitema OBD2.

- Interfaz del sistema OBD2 que adapte el puerto fisico de sicho sistema a un formato que sea conectable con el terminal objetivo.

A dia de hoy, 12/01/2022, no se necesita nada mas para compilar y ejecutar

