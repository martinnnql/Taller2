package dominio;

public class Pokemon {
	private String nombre;
	private String habitat;
	private double porcentajeAparicion;
	private int vida;
	private int vidaMax;
	private int ataque;
	private int defensa;
	private int ataqueEspecial;
	private int defensaEspecial;
	private int velocidad;
	private String tipo;
	private String estado = "Vivo";
	
	
	public Pokemon(String nombre, String habitat, double porcentajeAparicion, int vida, int ataque, int defensa,
			int ataqueEspecial, int defensaEspecial, int velocidad, String tipo) {
		super();
		this.nombre = nombre;
		this.habitat = habitat;
		this.porcentajeAparicion = porcentajeAparicion;
		this.vida = vida;
		this.vidaMax = vida;
		this.ataque = ataque;
		this.defensa = defensa;
		this.ataqueEspecial = ataqueEspecial;
		this.defensaEspecial = defensaEspecial;
		this.velocidad = velocidad;
		this.tipo = tipo;
		
	}


	
	
	public int getVidaMax() {
		return vidaMax;
	}




	public void setVidaMax(int vidaMax) {
		this.vidaMax = vidaMax;
	}


	

	public String getEstado() {
		return estado;
	}




	public void setEstado(String estado) {
		this.estado = estado;
	}




	public boolean getEstadoPoke() {
		if (estado.equals("Vivo")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getHabitat() {
		return habitat;
	}


	public void setHabitat(String habitat) {
		this.habitat = habitat;
	}


	public double getPorcentajeAparicion() {
		return porcentajeAparicion;
	}


	public void setPorcentajeAparicion(double porcentajeAparicion) {
		this.porcentajeAparicion = porcentajeAparicion;
	}


	public int getVida() {
		return vida;
	}


	public void setVida(int vida) {
		this.vida = vida;
	}


	public int getAtaque() {
		return ataque;
	}


	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}


	public int getDefensa() {
		return defensa;
	}


	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}


	public int getAtaqueEspecial() {
		return ataqueEspecial;
	}


	public void setAtaqueEspecial(int ataqueEspecial) {
		this.ataqueEspecial = ataqueEspecial;
	}


	public int getDefensaEspecial() {
		return defensaEspecial;
	}


	public void setDefensaEspecial(int defensaEspecial) {
		this.defensaEspecial = defensaEspecial;
	}


	public int getVelocidad() {
		return velocidad;
	}


	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}




	public int sumaStats() {
		// TODO Auto-generated method stub
		int sum = 0;
		
		sum = vida + ataque + defensa + ataqueEspecial + defensaEspecial + velocidad;
		
		return sum;
	}
	
	
	
}
