package dominio;
import java.util.ArrayList;
public class AltoMando {
	private int numAltoMando;
	private String nombre;
	private ArrayList<String> equipo;
	
	
	public AltoMando(int numAltoMando, String nombre, ArrayList<String> equipo) {
		super();
		this.numAltoMando = numAltoMando;
		this.nombre = nombre;
		this.equipo = equipo;
	}


	public int getNumAltoMando() {
		return numAltoMando;
	}


	public void setNumAltoMando(int numAltoMando) {
		this.numAltoMando = numAltoMando;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public ArrayList<String> getEquipo() {
		return equipo;
	}


	public void setEquipo(ArrayList<String> equipo) {
		this.equipo = equipo;
	}

	public void añadirPoke(String poke) {
		equipo.add(poke);
	}
}
