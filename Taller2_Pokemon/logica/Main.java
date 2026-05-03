// Martin Arancibia | 22.273.853-9  && Ignacio Valdivia | 22.179.357-9
package logica;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import dominio.*;
public class Main {
	static ArrayList<String> habitats = new ArrayList<>();
	static ArrayList<Pokemon> pokemones = new ArrayList<>();
	
	public static void main(String[] args) throws FileNotFoundException{

		cargarHabitats();
		cargarPokemones();
		
		
		//Primer menu
		int opcionMenu1 = cargarMenu1();
		
		if (opcionMenu1 == 3) {
			System.out.println("Saliendo . . .");
		}
		
		if (opcionMenu1 == 2) {
			String nombreJugador = ingresarNombre();
			System.out.println("\nBienvenido "+ nombreJugador + "!!");
			int opcionMenu2 = cargarMenu2();
			
			switch (opcionMenu2) {
			case 1:
				// Revisar equipo
				
				
				
				
				break;
			case 2:
				// Salir a capturar
				
				
				
				
				
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
	
	private static int cargarMenu2() {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		System.out.println("\n1) Revisar equipo. \n2) Salir a capturar. \n3) Acceso al PC (cambiar Pokémon del equipo). \n4) Retar un gimnasio. \n5) Desafío al Alto Mando. \n6) Curar Pokémon. \n7) Guardar. \n8) Guardar y Salir.");
		System.out.print("Ingrese Opcion: ");
		int opcionMenu = s.nextInt();
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
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		System.out.println("1) Continuar. \n2) Nueva Partida. \n3) Salir.");
		System.out.print("Ingrese Opcion: ");
		int opcionMenu = s.nextInt();
		s.nextLine();
		return opcionMenu;
	}

	public static void cargarHabitats() throws FileNotFoundException{ // solo carga los habitats
		File archivo = new File("Habitats.txt");
		Scanner lector = new Scanner(archivo);
		while (lector.hasNextLine()) {
			String linea = lector.nextLine();
			habitats.add(linea);
		}
	}
	
	public static void cargarPokemones() throws FileNotFoundException{ //mete a todos los pokemones del txt en un arraylist
		File archivo = new File("Pokedex.txt");
		Scanner lector = new Scanner(archivo);
		
		while(lector.hasNextLine()) {
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
			Pokemon nuevoPokemon = new Pokemon(nombrePokemon,habitat,porcAparicion,vida,ataque,defensa,ataqueEspecial,defensaEspecial,velocidad,tipo);
			pokemones.add(nuevoPokemon);
			
		}
	}
}
