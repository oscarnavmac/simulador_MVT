package modelo;

public class Hueco extends Particion{
	
	private int orden;

	public Hueco(int localidad, int tamanio, String estado, int orden) {
		super(localidad, tamanio, estado);
		this.orden = orden;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	@Override
	public String toString() {
		return "Hueco [" + super.toString() + " orden=" + orden + "]";
	}
	

}
