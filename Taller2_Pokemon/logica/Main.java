// Martin Arancibia | 22.273.853-9 ICCI && Ignacio Valdivia | 22.179.357-9 ICCI
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
	static ArrayList<AltoMando> altoMando = new ArrayList<>();
	static int cantMedallas = 0;

	public static void main(String[] args) throws IOException {

		cargarHabitats();
		cargarPokemones();
		cargarLideres();
		cargarAltoMando();
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

	private static void cargarAltoMando() throws FileNotFoundException {
		File archivo = new File("Alto Mando.txt");
		Scanner lector = new Scanner(archivo);

		while (lector.hasNextLine()) {
			String linea = lector.nextLine();
			String[] partes = linea.split(";");
			
			int numAltoMando = Integer.parseInt(partes[0]);
			String nombreAltoMando = partes[1];
			int cantPokemones = 6;
			ArrayList<String> pokeTemp = new ArrayList<>();
			for (int i = 0; i < cantPokemones; i++) {
				String pokemon = partes[2 + i];

				pokeTemp.add(pokemon);
			}
			AltoMando nuevoAltoMando = new AltoMando(numAltoMando, nombreAltoMando, pokeTemp);
			altoMando.add(nuevoAltoMando);
			
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

				salirACapturar(); // OJO ACÁ, NO SE PUEDE VOLVER A CAPTURAR UN POKEMON QUE YA TIENES (FALTA
									// ARREGLAR)

				break;
			case 3:
				// Acceso al PC (Cambiar pokemones del equipo)
				pcCambiarPokes();

				break;
			case 4:
				// Retar un GYM

				if (cantMedallas != 8) {
					retarGym();
				} else {
					System.out.println("Ya derrotaste a todos los lideres!");
				}

				break;
			case 5:
				// Desafio Alto Mando
				
				if(cantMedallas == 8) {
					retarAltoMando();
				}else {
					System.out.println("Necesitas 8 medallas para retar al alto mando (tienes " + cantMedallas + ")");
				}
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
				System.out.println("\nNos vemos entrenador...\r\n" + "");
				guardarPartida();
				salir = true;
				break;

			default:
				System.err.println("Ingrese un numero.");
				break;
			}
		}
	}

	private static void retarAltoMando() throws FileNotFoundException {
	    ArrayList<Pokemon> AptoParaCombate = new ArrayList<>();
	    
	    for (int j = 0; j < pokemonesJugador.size(); j++) {
	        if (j == 6) {
	            break;
	        }
	        if (pokemonesJugador.get(j).getEstado().equalsIgnoreCase("Vivo")) {
	            AptoParaCombate.add(pokemonesJugador.get(j));
	        }
	    }

	    if (AptoParaCombate.size() == 0) {
	        System.out.println("No tienes pokemones en condiciones de luchar. . .");
	        return;
	    }

	    int victorias = 0;
	    boolean rendido = false;

	    // este es el for de los líderes del Alto Mando
	    for (int i = 0; i < altoMando.size(); i++) {
	        ArrayList<Pokemon> PokemonesAltoMando = identificarPokesAltoMando(i);
	        
	        System.out.println("\n--- DESAFÍO CONTRA: " + altoMando.get(i).getNombre() + " ---");

	        // while del combate con X lider
	        while (AptoParaCombate.size() != 0 && PokemonesAltoMando.size() != 0) {
	            
	            System.out.println("\n" + altoMando.get(i).getNombre() + " saca a " + PokemonesAltoMando.get(0).getNombre());
	            System.out.println(obtenernombreJugador() + " saca a " + AptoParaCombate.get(0).getNombre());

	            int opcionCombate = cargarMenuCombate();

	            if (opcionCombate == 3) { 
	                rendido = true;
	                break;
	            }

	            if (opcionCombate == 1) { 
	                Pokemon atacante = AptoParaCombate.get(0);
	                Pokemon defensor = PokemonesAltoMando.get(0);

	                double efectividad = TablaTipos.calcularEfectividad(atacante.getTipo(), defensor.getTipo());
	                mostrarEfectividad(efectividad, atacante, defensor);
	                mostrarNuevasStats(efectividad, atacante.sumaStats(), defensor.sumaStats(), atacante, defensor);

	                if (defensor.getEstado().equals("Debilitado")) {
	                    PokemonesAltoMando.remove(0);
	                }
	                if (atacante.getEstado().equals("Debilitado")) {
	                    AptoParaCombate.remove(0);
	                }
	            }

	            if (opcionCombate == 2) { 
	                cambiarPokemones(AptoParaCombate);
	            }

	            
	            if (AptoParaCombate.size() == 0) {
	                System.out.println("\nHas perdido contra " + altoMando.get(i).getNombre() + " . . .");
	                
	                
	                for (int k = 0; k < pokemonesJugador.size(); k++) {
	                    if (k == 6) {
	                        break;
	                    }
	                    pokemonesJugador.get(k).setEstado("Debilitado");
	                }
	                rendido = true;
	                break;
	            }

	            
	            if (PokemonesAltoMando.size() == 0) {
	                System.out.println("¡Has derrotado a " + altoMando.get(i).getNombre() + "!");
	            }
	        }

	        if (rendido) {
	            break;
	        }
	        
	        victorias++;
	    }

	    if (victorias == altoMando.size()) {
	        System.out.println("\n¡¡FELICIDADES CAMPEON!!");
	    } else {
	        System.out.println("\nHas fallado en el reto del Alto Mando. Intentalo denuevo.");
	    }
	}

	private static ArrayList<Pokemon> identificarPokesAltoMando(int indice) {
		// el indice sirve para ver a que lider del alto mando estamos enfrentando
		ArrayList<Pokemon> pokesAltoMando = new ArrayList<>();
		for (String nombre : altoMando.get(indice).getEquipo()) {
			pokesAltoMando.add(identificarPokesLider(nombre));
		}
		return pokesAltoMando;
	}

	private static void retarGym() throws FileNotFoundException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("A cual lider deseas retar??");
		System.out.println("");
		validarLideres();
		for (int i = 0; i < lideres.size(); i++) {
			System.out.println(i + 1 + ") " + lideres.get(i).getNombre() + " - Estado: " + lideres.get(i).getEstado());

		}
		int opcion = 0;
		do { // control de error numeros y letras + control error desafiar lider
			System.out.print("> ");
			if (scanner.hasNextInt()) {
				opcion = scanner.nextInt();

				if (opcion < 0 || opcion > lideres.size()) {
					System.err.println("Porfavor, ingrese número válido.");
				}

				if (opcion - 1 > cantMedallas) {
					System.err.println("Aún no puedes retar a este líder! Debes vencer a los anteriores primero.");
					opcion = -1;
				} else if (lideres.get(opcion - 1).getEstado().equalsIgnoreCase("Derrotado")) {
					System.out.println("Ya has derrotado a este lider! Intenta desafiar al siguiente! ("
							+ lideres.get(cantMedallas).getNombre() + ")");
					opcion = -1;
				}
			} else {
				System.err.println("Porfavor, ingresa un NÚMERO.");
				scanner.next();
				opcion = -1;
			}

		} while (opcion < 0 || opcion > lideres.size());

		combateGym(opcion - 1);

	}

	private static void combateGym(int i) throws FileNotFoundException {
		// TODO Auto-generated method stub
		ArrayList<Pokemon> pokemonesLider = new ArrayList<>();
		System.out.println("Desafiando a " + lideres.get(i).getNombre());
		ArrayList<Pokemon> AptoParaCombate = new ArrayList<>();

		// ESTE FOR RELLENA LA LISTA POKEMONESLIDER CON TODOS LOS POKES DEL LIDER

		for (String nombrePoke : lideres.get(i).getEquipo()) {
			pokemonesLider.add(identificarPokesLider(nombrePoke));

		}

		// ESTE FOR RELLENA LA LISTA APTOPARACOMBATE CON TODOS LOS POKEMONES DEL EQUIPO
		// QUE ESTÉN VIVOS.
		for (int j = 0; j < pokemonesJugador.size(); j++) {
			if (j == 6) {
				break;
			}
			if (pokemonesJugador.get(j).getEstado().equalsIgnoreCase("Vivo")) {
				AptoParaCombate.add(pokemonesJugador.get(j));
			}
		}

		do {
			if (AptoParaCombate.size() == 0) {

				System.out.println("Has perdido la batalla . . .");
				break;
			} else if (pokemonesLider.size() == 0) {
				System.out.println("Has ganado la batalla !");
				cantMedallas++;
				break;
			}
			// inicio
			
			System.out.println();
			System.out.println(lideres.get(i).getNombre() + " saca a " + pokemonesLider.get(0).getNombre() + "!");
			System.out.println(obtenernombreJugador() + " saca a " + AptoParaCombate.get(0).getNombre() + "!");

			// cargar opciones combate
			int opcionCombate = cargarMenuCombate();

			if (opcionCombate == 3) {
				break;
			}

			if (opcionCombate == 1) {
				System.out.println(pokemonesLider.size());

				// Atacar pokemon

				/*
				 * HACER UN METODO O ALGO QUE VERIFIQUE SI EL PRIMER POKEMON DEL JUGADOR ESTÁ
				 * MUERTO Y SI ESTÁ MUERTO, PASAR AL SIGUIENTE, ASÍ HASTA SACAR AL POKEMON QUE
				 * ESTÉ VIVO, Y SI NO HAY VIVO HACER QUE EL COMBATE TERMINE O DIRECTAMENTE QUE
				 * NO SE PUEDA PELEAR.
				 * 
				 * HACER UN METODO RESTAURAR EQUIPO LIDER DESPUES DE CADA COMBATE
				 */

				Pokemon atacante = AptoParaCombate.get(0);
				int statsAtacante = AptoParaCombate.get(0).sumaStats();

				Pokemon defensor = pokemonesLider.get(0);
				int statsDefensor = pokemonesLider.get(0).sumaStats();

				/*
				 * ESTE FOR LO QUE HACE ES VER LOS PRIMEROS 6 POKEMONES DEL USUARIO Y PONE EN LA
				 * LISTA TEMPORAL DE APTOPARACOMBATE SOLO LOS POKEMONES QUE ESTÁN VIVOS, ESTO
				 * SIRVE PARA QUE CUANDO PELEEMOS Y NOS MATEN A UN POKEMON, LO ELIMINEMOS DE LA
				 * LISTA Y NO AFECTARÁ EN NADA, ASÍ QUE LA PELEA TERMINARÍA CUANDO EL SIZE SEA =
				 * 0. Y DESPUÉS A LOS PRIMEROS 6 POKEMONES DEL JUGADOR LOS PONEMOS EN DEBILITADO
				 * EN LA LISTA ESTATICA Y LISTO
				 */
				System.out.println();
				System.out.println(atacante.getNombre() + " -> " + statsAtacante);
				System.out.println(defensor.getNombre() + " -> " + statsDefensor);

				double efectividad = TablaTipos.calcularEfectividad(atacante.getTipo(), defensor.getTipo());

				mostrarEfectividad(efectividad, atacante, defensor);
				mostrarNuevasStats(efectividad, statsAtacante, statsDefensor, atacante, defensor);

				if (defensor.getEstado().equals("Debilitado")) {
					pokemonesLider.remove(0);

				}
				if (atacante.getEstado().equals("Debilitado")) {
					AptoParaCombate.remove(0);
				}

			}
			if (opcionCombate == 2) {
				// Cambiar Pokemon
				cambiarPokemones(AptoParaCombate);
			}

		} while (AptoParaCombate.size() != 0 || pokemonesLider.size() != 0); // se repita hasta que aptoparacombate sea
																				// = 0

		// Aqui iria metodo restaurar lider
	}

	private static void cambiarPokemones(ArrayList<Pokemon> aptoParaCombate) {
		// Al elegir cambiar, se podrá elegir uno de los primeros 6 Pokémon para
		// enviarlo al combate.
		// Mostrar lista enumerada de pokemones con: sus stats (sumadas) y su estado, y
		// al hacer el cambio el nuevo poke es quien se enfrenta al lider

		Scanner s = new Scanner(System.in);

		System.out.println("\nElige el pokemon para enviar al combate:\n");

		for (int i = 0; i < aptoParaCombate.size(); i++) {

			Pokemon pokemon = aptoParaCombate.get(i);

			System.out.println((i + 1) + ") " + pokemon.getNombre() + " | " + pokemon.getTipo() + " | Stats: "
					+ pokemon.sumaStats() + " | Estado: " + pokemon.getEstado());
		}

		int opcion = 0;
		boolean condicion = false;

		while (!condicion) {

			try {

				System.out.print("> ");
				opcion = s.nextInt();

				if (opcion >= 1 && opcion <= aptoParaCombate.size()) {

					condicion = true;

				} else {

					System.err.println("Ingrese un numero valido.");
				}

			} catch (Exception e) {

				System.err.println("Ingrese un valor valido.");
				s.nextLine();
			}
		}

		opcion--;

		// SI ELIGE EL MISMO POKEMON
		if (opcion == 0) {

			System.out.println("Ese pokemon ya esta en combate!");
			return;
		}

		// INTERCAMBIO
		Pokemon aux = aptoParaCombate.get(0);

		aptoParaCombate.set(0, aptoParaCombate.get(opcion));

		aptoParaCombate.set(opcion, aux);

		System.out.println("\nAdelante " + aptoParaCombate.get(0).getNombre() + "!!");
	}

	private static void mostrarNuevasStats(double efectividad, int statsAtacante, int statsDefensor, Pokemon atacante,
			Pokemon defensor) {

		int nuevaStatAtacante = 0;
		int nuevaStatDefensor = 0;
		if (efectividad == 2.0) {
			nuevaStatDefensor = (statsDefensor / 2);
			nuevaStatAtacante = statsAtacante;

		}
		if (efectividad == 1.0) {
			nuevaStatAtacante = statsAtacante;
			nuevaStatDefensor = statsDefensor;
		}
		if (efectividad == 0.5) {
			nuevaStatAtacante = (statsAtacante / 2);
			nuevaStatDefensor = statsDefensor;
		}

		System.out.println("\n" + "Nuevo Puntaje: ");
		System.out.println(atacante.getNombre() + " -> " + nuevaStatAtacante);
		System.out.println(defensor.getNombre() + " -> " + nuevaStatDefensor);

		if (nuevaStatAtacante > nuevaStatDefensor) {
			System.out.println(defensor.getNombre() + " Ha sido derrotado!");

			defensor.setEstado("Debilitado");
		} else if (nuevaStatDefensor > nuevaStatAtacante) {
			System.out.println(atacante.getNombre() + " Ha sido derrotado!");

			atacante.setEstado("Debilitado");

		}

	}

	private static void mostrarEfectividad(double efectividad, Pokemon atacante, Pokemon defensor) {
		// TODO Auto-generated method stub
		if (efectividad == 2.0) {
			System.out.println("\n" + atacante.getNombre() + " es super efectivo contra " + defensor.getNombre() + "!");
		}
		if (efectividad == 1.0) {
			System.out.println("\n" + atacante.getNombre() + " ataco a " + defensor.getNombre() + "!");
		}
		if (efectividad == 0.5) {
			System.out
					.println("\n" + atacante.getNombre() + " no es efectivo contra " + defensor.getNombre() + ". . .");
		}
	}

	private static int cargarMenuCombate() {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		int opcionMenu = 0;
		boolean condicion = false;
		while (!condicion) {
			try {
				System.out.println(
						"\nQue deseas hacer?\r\n" + "1) Atacar\r\n" + "2) Cambiar de pokemon\r\n" + "3) Rendirse");
				System.out.print("Ingrese Opcion: ");

				opcionMenu = s.nextInt();
				if (opcionMenu <= 3 && opcionMenu >= 1) {
					condicion = true;
				} else {
					System.err.println("\nIngrese un numero valido. \n");
				}

				if (opcionMenu == 3) {
					System.out.println();
					System.out.println("Has decidido rendirte . . . ");
					break;
				}

			} catch (Exception e) {
				System.err.println("\nIngrese un valor valido. \n");
				s.nextLine();
			}
		}

		s.nextLine();
		return opcionMenu;
	}

	private static String obtenernombreJugador() throws FileNotFoundException {
		// TODO Auto-generated method stub
		File archivo = new File("Registros.txt");
		Scanner lector = new Scanner(archivo);

		String linea = lector.nextLine();
		String[] partes = linea.split(";");

		String nombreJugador = partes[0];

		return nombreJugador;
	}

	private static Pokemon identificarPokesLider(String nombrePoke) {
		// TODO Auto-generated method stub

		for (Pokemon pokemon : pokemones) {
			if (nombrePoke.equals(pokemon.getNombre())) {
				return pokemon;

			}

		}
		return null;

	}

	private static void validarLideres() throws FileNotFoundException {
		for (int i = 0; i < cantMedallas; i++) {
			lideres.get(i).setEstado("Derrotado");
		}
	}

	private static void guardarPartida() throws IOException {
		File archivo = new File("Registros.txt");
		Scanner s = new Scanner(archivo);

		String linea = s.nextLine();
		String[] partes = linea.split(";");

		String nombreJugador = partes[0];

		BufferedWriter bf = new BufferedWriter(new FileWriter("Registros.txt"));

		// GUARDAR NUEVAS MEDALLAS
		bf.write(nombreJugador + ";" + cantMedallas);
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
			System.out.println("\n" + cont + ") " + pokemon.getNombre());
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
		if (opcion == 1) {
			intercambiarPokes();
		}

	}

	private static void intercambiarPokes() {

	    Scanner s = new Scanner(System.in);

	    int cantPokes = pokemonesJugador.size();

	    int intercambio1 = 0;

	    do {

	        System.out.println("Elije el primer pokemon para cambiar: ");
	        System.out.print("> ");

	        if (s.hasNextInt()) {

	            intercambio1 = s.nextInt();

	            if (intercambio1 <= 0 || intercambio1 > cantPokes) {

	                System.err.println("Ingrese un número válido.");
	            }

	        } else {

	            System.err.println("Ingrese un número!.");
	            s.nextLine(); // LIMPIA EL BUFFER
	            intercambio1 = 0;
	        }

	    } while (intercambio1 <= 0 || intercambio1 > cantPokes);

	    int intercambio2 = 0;

	    do {

	        System.out.println("Elije el segundo pokemon para cambiar: ");
	        System.out.print("> ");

	        if (s.hasNextInt()) {

	            intercambio2 = s.nextInt();

	            if (intercambio2 <= 0 || intercambio2 > cantPokes) {

	                System.err.println("Ingrese un número válido.");
	            }

	        } else {

	            System.err.println("Ingrese un número!.");
	            s.nextLine(); // LIMPIA EL BUFFER
	            intercambio2 = 0;
	        }

	    } while (intercambio2 <= 0 || intercambio2 > cantPokes);

	    intercambio1 -= 1;
	    intercambio2 -= 1;

	    Pokemon auxPoke = pokemonesJugador.get(intercambio1);

	    pokemonesJugador.set(intercambio1, pokemonesJugador.get(intercambio2));

	    pokemonesJugador.set(intercambio2, auxPoke);

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
		cantMedallas = Integer.parseInt(partes[1]);

		while (lector.hasNextLine()) {
			String lineaPoke = lector.nextLine();
			String[] partesPoke = lineaPoke.split(";");

			String nombrePoke = partesPoke[0];
			String estadoPoke = partesPoke[1];

			// PA DESPUES, PRIMERO TERMINAR GUARDAR Y GUARDAR Y SALIR
			identificarPokes(nombrePoke, estadoPoke);
		}
		System.out.println("\nBienvenido " + nombreJugador + "!!");

		lector.close();

	}

	private static void identificarPokes(String nombrePoke, String estadoPoke) {
		// TODO Auto-generated method stub
		for (Pokemon pokemon : pokemones) {
			if (nombrePoke.equals(pokemon.getNombre())) {
				pokemon.setEstado(estadoPoke);
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
			if (cont == 7) {
				break;
			}
		}

	}

	private static void curarEquipo() {

		for (Pokemon pokemon : pokemonesJugador) {

			if (pokemon.getEstado().equalsIgnoreCase("Debilitado")) {

				pokemon.setEstado("Vivo");
				pokemon.setVida(pokemon.getVidaMax());

				System.out.println(pokemon.getNombre() + " ha sido curado!");
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
					System.err.println("\nIngrese un numero valido\n");
				}

				if (opcion == 7) {
					System.out.println("\nVolviendo . . . ");
					return;
				}

			} catch (Exception e) {
				System.err.println("\nIngrese un valor valido\n");
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
		boolean existePokemon = true;
		for (int i = 0; i < pokemonesJugador.size(); i++) {
			if (pokemonesJugador.get(i).getNombre().equals(pokeSalvaje.getNombre())) {
				System.out.println("Ya tienes a este pokémon! no puedes capturarlo!");
				System.out.println("Volviendo al menú. . .");
				existePokemon = false;
			}
		}

		if (opcionMenu == 2) {
			System.out.println("\nHas huido con exito !");
			return;
		} else if (opcionMenu == 1 && existePokemon == true) {
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
			nuevoPokemon.setEstado("Vivo");
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
			for (int i = 1; i < cantPokemones + 1; i++) {
				String pokemon = partes[3 + i];
				pokeTemp.add(pokemon);
			}
			LiderGym nuevoLider = new LiderGym(numGym, nombre, estado, cantPokemones, pokeTemp);
			lideres.add(nuevoLider);
		}
	}
}
