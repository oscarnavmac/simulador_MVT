package modelo;

public class Particion {
	
	private int localidad;
	private int tamanio;
	private String estado;
	
	public Particion(int localidad, int tamanio, String estado)
	{
		this.localidad = localidad;
		this.tamanio = tamanio;
		this.estado = estado;
	}

	public int getLocalidad() {
		return localidad;
	}

	public void setLocalidad(int localidad) {
		this.localidad = localidad;
	}

	public int getTamanio() {
		return tamanio;
	}

	public void setTamanio(int tamanio) {
		this.tamanio = tamanio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "localidad=" + localidad + ", tamanio=" + tamanio + ", estado=" + estado;
	}

}