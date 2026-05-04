// Martin Arancibia | 22.273.853-9  && Ignacio Valdivia | 22.179.357-9
package logica;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

import dominio.*;

public class Main {
	static ArrayList<String> habitats = new ArrayList<>();
	static ArrayList<Pokemon> pokemones = new ArrayList<>();
	static ArrayList<LiderGym> lideres = new ArrayList<>();

	public static void main(String[] args) throws FileNotFoundException {

		cargarHabitats();
		cargarPokemones();

		// Primer menu
		boolean salir = false;
		int opcionMenu1 = cargarMenu1();

		if (opcionMenu1 == 3) {
			System.out.println("Saliendo . . .");
		}

		if (opcionMenu1 == 2) {
			String nombreJugador = ingresarNombre();
			System.out.println("\nBienvenido " + nombreJugador + "!!");

			while (!salir) {

				int opcionMenu2 = cargarMenu2();

				switch (opcionMenu2) {
				case 1:
					// Revisar equipo

					break;
				case 2:
					// Salir a capturar

					salirACapturar();

					break;
				case 3:
					// Acceso al PC (Cambiar pokemones del equipo)

					break;
				case 4:
					// Retar un GYM

					break;
				case 5:
					// Desafio Alto Mando

					break;
				case 6:
					// Curar Pokemones

					break;
				case 7:
					// Guardar

					break;
				case 8:
					// Guardar y salir

					break;

				default:
					System.err.println("Ingrese un numero.");
					break;
				}
			}
		}

	}

	private static void salirACapturar() {
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

	private static void encuentroPokemon(String habitat) {
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
			// ACA AÑADIR POKEMONES A LA LISTA DE POKEMONES DE LA PERSONA, ARRAYLIST DE POKEMONES EN GENERAL Y LISTA POKEMONES EQUIPO
			// AUNQUE CREO QUE TIENE QUE ESTAR EN UN TXT PERO ESO HAY QUE CRANEARLO MAS OK
		}

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
