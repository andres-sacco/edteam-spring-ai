# Prueba de concepto de AI

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Este repositorio contiene todo lo necesario para poder ejecutar la prueba

## Tabla de contenidos

Los suguientes son los topicos o puntos mas relevantes de este archivo:
- [Requerimientos](#Requerimientos)
- [Comprobar requerimientos](#Comprobar-requerimientos)
- [Ejecutar Ollama](#Ejecutar-Ollama)

## Requerimientos

Para poder utilizar el código de este proyecto deberás tener las siguientes herramientas instaladas:

- [Java](https://www.oracle.com/ar/java/technologies/downloads/)
- [Maven](https://maven.apache.org/)
- [Git](https://git-scm.com/)
- [Docker](https://www.docker.com/)

Si no tienes algunas de estas herramientas instaladas en tu computadora, sigue las instrucciones en la documentación oficial de cada herramienta.

## Comprobar requerimientos


Si instaló en su computadora algunas de estas herramientas anteriormente o instalaste todas las herramientas ahora, verifica si todo funciona bien.

- Para comprobar que version de Java tenes en tu computadora podes usar este comando:
   ````
   % java -version
   openjdk 21.0.2 2024-01-16
   OpenJDK Runtime Environment GraalVM CE 21.0.2+13.1 (build 21.0.2+13-jvmci-23.1-b30)
   OpenJDK 64-Bit Server VM GraalVM CE 21.0.2+13.1 (build 21.0.2+13-jvmci-23.1-b30, mixed mode, sharing)
   ````

- Comprueba si la version de Maven es 3.8.0 o superior. Puedes comprobar la version de Maven con el siguiente comando:
   ````
   % mvn --version
   Apache Maven 3.8.3
   Maven home: /usr/share/maven
   ````

- Comprueba si la version de Docker que se encuentra en tu computadora es 18.09.0 o superior. Puedes comprobar la version de Docker con el siguiente comando:
   ````
   % docker --version
  Docker version 24.0.2, build cb74dfc
   ````

## Ejecutar Ollama

Para ejecutar localmente Ollama se tiene que ejecutar el siguiente commando:
   ````
   % docker run -it --rm -p 11434:11434 --name ollama ollama/ollama
   ````

Despues de eso hay que instalar el model usando el siguiente comando:
   ````
   % docker exec -it ollama ollama pull llama3
   ````