package dominio;
import java.util.ArrayList;
public class LiderGym {
	private int numGym;
	private String nombre;
	private String estado;
	private int cantPokemones; // hola
	private ArrayList<String> equipo;
	
	
	public LiderGym(int numGym, String nombre, String estado, int cantPokemones, ArrayList<String> equipo) {
		super();
		this.numGym = numGym;
		this.nombre = nombre;
		this.estado = estado;
		this.cantPokemones = cantPokemones;
		this.equipo = equipo;
	}

	public void aniadirPoke(String poke) {
		equipo.add(poke);
	}
	
	public int getNumGym() {
		return numGym;
	}
	public void setNumGym(int numGym) {
		this.numGym = numGym;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getCantPokemones() {
		return cantPokemones;
	}
	public void setCantPokemones(int cantPokemones) {
		this.cantPokemones = cantPokemones;
	}
	public ArrayList<String> getEquipo() {
		return equipo;
	}
	public void setEquipo(ArrayList<String> equipo) {
		this.equipo = equipo;
	}
	
	
}
