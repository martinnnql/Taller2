// Martin Arancibia | 22.273.853-9  && Ignacio Valdivia | 22.179.357-9
package logica;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

import dominio.*;

public class Main {
	static ArrayList<String> habitats = new ArrayList<>();
	static ArrayList<Pokemon> pokemones = new ArrayList<>();
	static ArrayList<Pokemon> pokemonesJugador = new ArrayList<>();
	static ArrayList<Pokemon> pokemonesJugadorContinuePartida = new ArrayList<>();
	static ArrayList<LiderGym> lideres = new ArrayList<>();

	public static void main(String[] args) throws IOException {

		cargarHabitats();
		cargarPokemones();

		// Primer menu

		int opcionMenu1 = cargarMenu1();

		if (opcionMenu1 == 3) {
			System.out.println("Saliendo . . .");
		}

		if (opcionMenu1 == 1) {

			cargarRegistro();

			cargarOpciones();
		}

		if (opcionMenu1 == 2) {

			String nombreJugador = ingresarNombre();
			System.out.println("\nBienvenido " + nombreJugador + "!!");

			nuevoJugador(nombreJugador);

			cargarOpciones();
		}

	}

	private static void cargarOpciones() throws IOException {
		boolean salir = false;
		while (!salir) {

			int opcionMenu2 = cargarMenu2();

			switch (opcionMenu2) {
			case 1:
				// Revisar equipo

				revisarEquipo();

				break;
			case 2:
				// Salir a capturar

				salirACapturar();

				break;
			case 3:
				// Acceso al PC (Cambiar pokemones del equipo)
				pcCambiarPokes();
				
				break;
			case 4:
				// Retar un GYM

				break;
			case 5:
				// Desafio Alto Mando

				break;
			case 6:
				// Curar Pokemones

				curarEquipo();
				System.out.println("Equipo recuperado con exito!");

				break;
			case 7:
				// Guardar
				System.out.println("Guardando...");
				guardarPartida();
				break;
			case 8:
				// Guardar y salir
				System.out.println("Guardando y saliendo...");
				guardarPartida();
				salir = true;
				break;

			default:
				System.err.println("Ingrese un numero.");
				break;
			}
		}
	}

	private static void guardarPartida() throws IOException {
		File archivo = new File("Registros.txt");
		Scanner s = new Scanner(archivo);
		String linea = s.nextLine(); // nombre;medallas
		BufferedWriter bf = new BufferedWriter(new FileWriter("Registros.txt"));
		bf.write(linea);
		bf.newLine();
	
		for (Pokemon p : pokemonesJugador) {
			String lineaPoke = p.getNombre() + ";" + p.getEstado();
			bf.write(lineaPoke);
			bf.newLine();
		}
		
		bf.close();
	}

	private static void pcCambiarPokes() {
		Scanner s = new Scanner(System.in);
		
		// mostrar de manera numerada todos los Pokémon que el usuario haya capturado.
		int cont = 1;
		for (Pokemon pokemon : pokemonesJugador) {
			System.out.println("\n"+cont + ") " + pokemon.getNombre());
			cont++;
		}
		
		// mostrar opciones
		int opcion = 0;
		boolean condicion = false;

		while (!condicion) {

			try {
				System.out.println("1) Cambiar Pokémon. \n2) Salir.");
				System.out.print("> ");
				opcion = s.nextInt();
				s.nextLine();

				if (opcion <= 2 && opcion >= 1) {
					condicion = true;
				} else {
					System.err.println("\nIngrese un numero valido\n");
				}

				if (opcion == 2) {
					System.out.println("\nVolviendo . . . ");
					return;
				}

			} catch (Exception e) {
				System.err.println("\nIngrese un valor valido\n");
				s.nextLine();
			}

		}
		
		// condicion true
		if(opcion == 1) {
			intercambiarPokes();
		}
		
		
		
	}

	private static void intercambiarPokes() {
		Scanner s = new Scanner(System.in);
		int cantPokes = pokemonesJugador.size();
		
		
		int intercambio1=0;
		do {
			System.out.println("Elije el primer pokemon para cambiar: ");
			System.out.print("> ");

			if (s.hasNextInt()) {
				intercambio1 = s.nextInt();
				
				if(intercambio1<0 || intercambio1>cantPokes) {
					System.err.println("Ingrese un número válido.");
				}
				
				
			}else {
				System.err.println("Elije un número válido.");
				intercambio1 =0;
			}
			
		}while(intercambio1<0 || intercambio1>cantPokes);
		
		int intercambio2=0;
		do {
			System.out.println("Elije el segundo pokemon para cambiar: ");
			System.out.print("> ");

			if (s.hasNextInt()) {
				intercambio2 = s.nextInt();
				
				if(intercambio2<0 || intercambio2>cantPokes) {
					System.err.println("Ingrese un número válido.");
				}
				
				
			}else {
				System.err.println("Elije un número válido.");
				intercambio1 =0;
			}
			
		}while(intercambio2<0 || intercambio2>cantPokes);
		
		intercambio1 -= 1;
		intercambio2 -= 1;
		
		// hacer el intercambio con los index (intercambio -1) *CANT POKES +1 POSIBLEMENTE*
		
		int aux = intercambio2;
		Pokemon auxPoke = pokemonesJugador.get(intercambio1);
		
		pokemonesJugador.set(intercambio1, pokemonesJugador.get(intercambio2));
		
		pokemonesJugador.set(aux,auxPoke);
		
		
		System.out.println("Intercambio realizado con exito!");
	}

	private static void nuevoJugador(String nombreJugador) throws IOException {
		BufferedWriter bf = new BufferedWriter(new FileWriter("Registros.txt"));
		String linea = nombreJugador + ";" + 0;
		bf.write(linea);
		bf.newLine();
		bf.close();
	}

	private static void cargarRegistro() throws FileNotFoundException {
		// TODO Auto-generated method stub
		File archivo = new File("Registros.txt");
		Scanner lector = new Scanner(archivo);

		String linea = lector.nextLine();
		String[] partes = linea.split(";");

		String nombreJugador = partes[0];
		int nMedallas = Integer.parseInt(partes[1]);

		while (lector.hasNextLine()) {
			String lineaPoke = lector.nextLine();
			String[] partesPoke = lineaPoke.split(";");

			String nombrePoke = partesPoke[0];
			String estadoPoke = partesPoke[1];

			// PA DESPUES, PRIMERO TERMINAR GUARDAR Y GUARDAR Y SALIR
			identificarPokes(nombrePoke);
		}
		System.out.println("\nBienvenido " + nombreJugador + "!!");

		lector.close();

	}

	private static void identificarPokes(String nombrePoke) {
		// TODO Auto-generated method stub
		for (Pokemon pokemon : pokemones) {
			if (nombrePoke.equals(pokemon.getNombre())) {
				pokemonesJugador.add(pokemon);

			}

		}
	}

	private static void revisarEquipo() {

		if (pokemonesJugador.size() == 0) {
			System.out.println("\nNo tienes pokemones en tu equipo!");
		}
		mostrarequipoNuevaPartida();

	}

	private static void mostrarequipoNuevaPartida() {
		// TODO Auto-generated method stub
		int cont = 1;

		for (Pokemon pokemon : pokemonesJugador) {

			System.out.println(cont + ") " + pokemon.getNombre() + " | " + pokemon.getTipo() + " | " + "Stats totales: "
					+ pokemon.sumaStats() + "\n");
			cont++;
		}

	}

	private static void curarEquipo() {

		for (Pokemon pokemon : pokemonesJugador) {
			if (pokemon.getVida() == 0) {
				pokemon.setVida(pokemon.getVidaMax());
				pokemon.setEstado("Vivo");
				pokemon.getEstadoPoke(); // devuelve True, lo que significa que esta vivo | False = muerto

			}
		}

	}

	private static void salirACapturar() throws IOException {
		Scanner s = new Scanner(System.in);

		System.out.println("\nDonde deseas ir a explorar?");
		System.out.println("\nZonas disponibles:");

		int opcion = 0;
		boolean condicion = false;

		while (!condicion) {

			try {
				System.out.println("\n1) Lago\r\n" + "2) Cueva\r\n" + "3) Montaña\r\n" + "4) Bosque\r\n"
						+ "5) Prado\r\n" + "6) Mar\r\n" + "7) Volver al menu.");
				System.out.print("Ingrese zona: ");
				opcion = s.nextInt();
				s.nextLine();

				if (opcion <= 7 && opcion >= 1) {
					condicion = true;
				} else {
					System.err.println("\nIngrese un numero valido mongolo\n");
				}

				if (opcion == 7) {
					System.out.println("\nVolviendo . . . ");
					return;
				}

			} catch (Exception e) {
				System.err.println("\nIngrese un valor validoooooooo putoooooo\n");
				s.nextLine();
			}

		}

		// condicion true asi q sigue el code, ahora toca ver q habitat escogieron
		String habitat = verHabitaEscogida(opcion);

		// Hacer aparecer al poke random de su habitat

		encuentroPokemon(habitat);

	}

	private static void encuentroPokemon(String habitat) throws IOException {
		Random r = new Random();
		ArrayList<Pokemon> pokeHabitat = new ArrayList<Pokemon>();

		for (Pokemon pokemon : pokemones) {
			if (pokemon.getHabitat().equals(habitat)) {
				pokeHabitat.add(pokemon);
			}
		}

		double total = 0;
		for (Pokemon pokemon : pokeHabitat) {
			total += pokemon.getPorcentajeAparicion();
		}

		double numero = r.nextDouble() * total;

		double acumulado = 0;
		Pokemon pokeSalvaje = null;

		for (Pokemon pokemon : pokeHabitat) {
			acumulado += pokemon.getPorcentajeAparicion();

			if (numero < acumulado) {
				pokeSalvaje = pokemon;
				break;
			}
		}

		System.out.println("\nOh!! Ha aparecido un increible " + pokeSalvaje.getNombre() + "!!");

		System.out.println("\nQue deseas hacer? \n");
		int opcionMenu = cargarMenuCapturar();

		if (opcionMenu == 2) {
			System.out.println("\nHas huido con exito !");
			return;
		} else if (opcionMenu == 1) {
			// hola nacho aqui falta hacer todo lo de capturar al pokemon ( q tenga
			// probabilidad de capturarlo ) y meterlo al equipo

			System.out.println(pokeSalvaje.getNombre() + " capturado con exito!!");

			System.out.println(pokeSalvaje.getNombre() + " ha sido agregado a tu equipo!");
			// ACA AÑADIR POKEMONES A LA LISTA DE POKEMONES DE LA PERSONA, ARRAYLIST DE
			// POKEMONES EN GENERAL Y LISTA POKEMONES EQUIPO
			// AUNQUE CREO QUE TIENE QUE ESTAR EN UN TXT PERO ESO HAY QUE CRANEARLO MAS OK

			pokemonesJugador.add(pokeSalvaje);
			añadirPokeARegistros(pokeSalvaje);
		}

	}

	private static void añadirPokeARegistros(Pokemon pokeSalvaje) throws IOException {

		BufferedWriter bf = new BufferedWriter(new FileWriter("Registros.txt", true));
		String linea = pokeSalvaje.getNombre() + ";" + pokeSalvaje.getEstado();

		bf.write(linea);
		bf.newLine();
		bf.close();
	}

	private static int cargarMenuCapturar() {
		Scanner s = new Scanner(System.in);
		int opcionMenu = 0;
		boolean condicion = false;
		while (!condicion) {
			try {
				System.out.println("1) Capturar\r\n" + "2) Huir");
				System.out.print("Ingrese Opcion: ");

				opcionMenu = s.nextInt();
				if (opcionMenu <= 2 && opcionMenu >= 1) {
					condicion = true;
				} else {
					System.err.println("\nIngrese un numero valido. \n");
				}

			} catch (Exception e) {
				System.err.println("\nIngrese un valor valido. \n");
				s.nextLine();
			}
		}

		s.nextLine();
		return opcionMenu;
	}

	private static String verHabitaEscogida(int opcion) {
		String habitat = null;

		if (opcion == 1) {
			habitat = "Lago";
		} else if (opcion == 2) {
			habitat = "Cueva";
		} else if (opcion == 3) {
			habitat = "Montaña";
		} else if (opcion == 4) {
			habitat = "Bosque";
		} else if (opcion == 5) {
			habitat = "Prado";
		} else if (opcion == 6) {
			habitat = "Mar";
		}

		return habitat;
	}

	private static int cargarMenu2() {
		Scanner s = new Scanner(System.in);
		int opcionMenu = 0;
		boolean condicion = false;
		while (!condicion) {
			try {
				System.out.println(
						"\n1) Revisar equipo. \n2) Salir a capturar. \n3) Acceso al PC (cambiar Pokémon del equipo). \n4) Retar un gimnasio. \n5) Desafío al Alto Mando. \n6) Curar Pokémon. \n7) Guardar. \n8) Guardar y Salir.");
				System.out.print("Ingrese Opcion: ");

				opcionMenu = s.nextInt();
				if (opcionMenu <= 8 && opcionMenu >= 1) {
					condicion = true;
				} else {
					System.err.println("\nIngrese un numero valido. \n");
				}

			} catch (Exception e) {
				System.err.println("\nIngrese un valor valido. \n");
				s.nextLine();
			}
		}

		s.nextLine();
		return opcionMenu;
	}

	private static String ingresarNombre() {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		System.out.print("Ingrese Apodo: ");
		String apodo = s.nextLine();
		return apodo;
	}

	private static int cargarMenu1() {
		Scanner s = new Scanner(System.in);
		int opcionMenu = 0;
		boolean condicion = false;
		while (!condicion) {
			try {
				System.out.println("1) Continuar. \n2) Nueva Partida. \n3) Salir.");
				System.out.print("Ingrese Opcion: ");

				opcionMenu = s.nextInt();
				if (opcionMenu <= 3 && opcionMenu >= 1) {
					condicion = true;
				} else {
					System.err.println("\nIngrese un numero valido. \n");
				}

			} catch (Exception e) {
				System.err.println("\nIngrese un valor valido. \n");
				s.nextLine();
			}
		}

		s.nextLine();
		return opcionMenu;
	}

	public static void cargarHabitats() throws FileNotFoundException { // solo carga los habitats
		File archivo = new File("Habitats.txt");
		Scanner lector = new Scanner(archivo);
		while (lector.hasNextLine()) {
			String linea = lector.nextLine();
			habitats.add(linea);
		}
	}

	public static void cargarPokemones() throws FileNotFoundException { // mete a todos los pokemones del txt en un
																		// arraylist
		File archivo = new File("Pokedex.txt");
		Scanner lector = new Scanner(archivo);

		while (lector.hasNextLine()) {
			String linea = lector.nextLine();
			String[] partes = linea.split(";");

			String nombrePokemon = partes[0];
			String habitat = partes[1];
			double porcAparicion = Double.parseDouble(partes[2]);
			int vida = Integer.parseInt(partes[3]);
			int ataque = Integer.parseInt(partes[4]);
			int defensa = Integer.parseInt(partes[5]);
			int ataqueEspecial = Integer.parseInt(partes[6]);
			int defensaEspecial = Integer.parseInt(partes[7]);
			int velocidad = Integer.parseInt(partes[8]);
			String tipo = partes[9];
			Pokemon nuevoPokemon = new Pokemon(nombrePokemon, habitat, porcAparicion, vida, ataque, defensa,
					ataqueEspecial, defensaEspecial, velocidad, tipo);
			pokemones.add(nuevoPokemon);

		}
	}

	public static void cargarLideres() throws FileNotFoundException {
		File archivo = new File("Gimnasios.txt");
		Scanner lector = new Scanner(archivo);

		while (lector.hasNextLine()) {
			String linea = lector.nextLine();
			String[] partes = linea.split(";");

			int numGym = Integer.parseInt(partes[0]);
			String nombre = partes[1];
			String estado = partes[2];
			int cantPokemones = Integer.parseInt(partes[3]);
			ArrayList<String> pokeTemp = new ArrayList<>();
			for (int i = 1; i < cantPokemones; i++) {
				String pokemon = partes[3 + i];
				pokeTemp.add(pokemon);
			}
			LiderGym nuevoLider = new LiderGym(numGym, nombre, estado, cantPokemones, pokeTemp);
			lideres.add(nuevoLider);
		}
	}
}
