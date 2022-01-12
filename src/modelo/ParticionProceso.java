package modelo;

public class ParticionProceso extends Particion{
	
	private Proceso proceso;

	public ParticionProceso(int localidad, int tamanio, String estado, Proceso proceso) {
		super(localidad, tamanio, estado);
		this.proceso = proceso;
	}

	public Proceso getProceso() {
		return proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}
	
	@Override
	public String toString() {
		return "Particion [" + super.toString() + " proceso=" + proceso + "]";
	}

}
