## Readme de MyCar ##

Esta aplicación es el fruto del Trabajo de Fin de Grado del mismo nombre.

Aplicación Android de ayuda al mantenimiento correcto del vehículo a través del sistema OBD2.  
Sus funciones básicas son 2: análisis del vehículo mediante el sistema OBD2 enfocado al público general, y una lista predictiva y detallada de los recambios efectuados

Programado por: David Pérez Núñez  
Titulado por: Pedro Ángel Castillo Valdivieso  
Basado en: Pires | android-obd-reader | https://github.com/pires/android-obd-reader  

## Características ##

Aplicación Android programada en Java con una estructura estandar (separación FrontEnd-BackEnd-Build).  
Trabajo realizado en Android Studio.

La conectividad con el sistema OBD2 se ha realizado a traves de librerias externas del mismo Pires, incluidas en la base de la que se ha partido.  
La base de datos esta hecha en SQL, mediante la libreria SQLiteOpenHelper

## Requisitos ##

Se necesitan 3 dispositivos para su correcto funcionamiento:

- Terminal de desarrollo que compile el proyecto y lo mande al terminal objetivo:  
	No precisa de características mínimas mas que el poder albergar Android Studio y conectarse con el dispositivo objetivo.

- Terminal objetivo que ejecute el programa y se conecte al vehiculo:  
	Los requisitos mínimos de software para la aplicación vienen en el archivo build.gradle  
	Los requisitos mínimos de hardware son: capacidad de conexión con el terminal de desarrollo y con el interfaz para el sisitema OBD2.

- Interfaz del sistema OBD2 que adapte el puerto físico de dicho sistema a un formato que sea conectable con el terminal objetivo:  
	Debido a que las librerias de conectividad estan hechas con comandos para el microcontrolador ELM327, la interfaz debe ser de este tipo, pudiendo variar el formato al que adapta el puerto

A día de hoy, 12/01/2022, no se necesita nada más para compilar y ejecutar

