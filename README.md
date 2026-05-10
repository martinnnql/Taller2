Integrantes:
Martin Alonso Arancibia Alzamora | 22.273.853-9 | GitHub: @martinnnql
Ignacio Bastian Valdivia Rodriguez | 22.179.357-9 | GitHub: @nchq7

Descripcion del proyecto:
​Es un simulador de combate y gestión Pokémon desarrollado en Java utilizando Programación Orientada a Objetos. El sistema permite cargar una base de datos de Pokémon desde archivos externos, gestionar un equipo de Pokemones con diferentes tipos (Fuego, Agua, Planta, etc.) y ejecutar una lógica de batalla donde se calculan daños, estados y efectividad de tipos.

Estructura del Proyecto:
Separamos en carpetas lo siguiente:

-Logica:
Main.java: Es el "motor" del juego. Contiene el bucle principal, la lectura de los archivos .txt para cargar los Pokémon disponibles y la interfaz de usuario por consola.

-Dominio:

​Pokemon.java: Define atributos comunes como vida (HP), ataque, defensa, nombre, entre otros.
​TablaTipos.java: Clase que ayuda en el sistema de combate de los pokemones.

LiderGym.java: Clase que nos sirve para guardar nombre, equipo, numero de gimnasio, entre otros.

AltoMando.java: Clase muy similar a LiderGym. Nos sirve para ver el nombre, numero de alto mando y equipo de un lider del altomando.


Instrucciones de ejecucion:

1. Abrir el proyecto en Eclipse. | 2. Asegurarse de que los archivos de texto estén en la misma carpeta del proyecto y asegurarse de que existan las carpetas de Dominio y Logica con sus respectivos archivos ".java". | 3. Ejecutar la clase principal.
