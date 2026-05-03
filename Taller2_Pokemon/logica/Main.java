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
