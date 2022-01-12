package modelo;

import java.util.ArrayList;

public class PasoDeMVT {
	
	ArrayList<Hueco> TAL;
	ArrayList<ParticionProceso> TP;
	int tiempo;
	
	public PasoDeMVT(ArrayList<Hueco> TAL, ArrayList<ParticionProceso> TP, int tiempo)
	{
		this.TAL = TAL;
		this.TP = TP;
		this.tiempo = tiempo;
	}

	public ArrayList<Hueco> getTAL() {
		return TAL;
	}

	public void setTAL(ArrayList<Hueco> tAL) {
		TAL = tAL;
	}

	public ArrayList<ParticionProceso> getTP() {
		return TP;
	}

	public void setTP(ArrayList<ParticionProceso> tP) {
		TP = tP;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

}
