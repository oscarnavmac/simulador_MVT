package modelo;

public class Proceso {
	
	private String nombre;
	private int tamanio;
	private int tiempoLlegada;
	private int duracion;
	
	public Proceso(String nombre, int tamanio, int tiempoLlegada, int duracion)
	{
		this.nombre = nombre;
		this.tamanio = tamanio;
		this.tiempoLlegada = tiempoLlegada;
		this.duracion = duracion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTamanio() {
		return tamanio;
	}

	public void setTamanio(int tamanio) {
		this.tamanio = tamanio;
	}

	public int getTiempoLlegada() {
		return tiempoLlegada;
	}

	public void setTiempoLlegada(int tiempoLlegada) {
		this.tiempoLlegada = tiempoLlegada;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	@Override
	public String toString() {
		return "Proceso [nombre=" + nombre + ", tamanio=" + tamanio + ", tiempoLlegada=" + tiempoLlegada + ", duracion="
				+ duracion + "]";
	}
	
	
		
}
