package modelo;

import java.util.ArrayList;

public class MVT {
	
	private static int tiempo;
	
	public static ArrayList<PasoDeMVT> iniciar(ArrayList<Proceso> procesos, int tamMemoria, Proceso sistema)
	{	
		//se instancian las tablas de particiones y de areas libres
		ArrayList<Hueco> TAL = new ArrayList<Hueco>();
		ArrayList<ParticionProceso> TP = new ArrayList<ParticionProceso>();
		
		ArrayList<PasoDeMVT> pasos = new ArrayList<PasoDeMVT>(); //se instancia la lista en donde se guardar� cada paso
		
		TP.add(new ParticionProceso(0, sistema.getTamanio(), "Ocupado", sistema)); //Se aloja el sistema operativo, el orden es cero porque se trata de una particion de proceso
		TAL.add(new Hueco(sistema.getTamanio(), tamMemoria - sistema.getTamanio(), "Disponible", 1)); //primer area libre
		
		tiempo = 0;
		
		for (Proceso p : procesos)
		{
			if (p.getTiempoLlegada()+p.getDuracion() > tiempo)
				tiempo = p.getTiempoLlegada()+p.getDuracion();
		}
		
		pasos.add(new PasoDeMVT(clonarHueco(TAL), clonarParticionProceso(TP), 0));
		
		for (int i = 0; i <= tiempo; i++)
		{	
			ArrayList<ParticionProceso> particionesAEliminar = new ArrayList<ParticionProceso>(); //auxiliar para guardar los procesos a sacar dado que no se pueden sacar dentro del for avanzado
			for (ParticionProceso p : TP) //ver si algun proceso sale
			{
				if (p.getProceso().getTiempoLlegada()+p.getProceso().getDuracion() == i)
				{
					sacarProceso(p, TP, TAL);
					particionesAEliminar.add(p);
				}
			}
			
			for (ParticionProceso p: particionesAEliminar) //eliminar los procesos, uno por uno (cada eliminacion cuenta como un paso)
			{
				TP.remove(p);
				pasos.add(new PasoDeMVT(clonarHueco(TAL), clonarParticionProceso(TP), i));
			}
			
			
			for (Proceso p : procesos) //ver si algun proceso entra
			{
				if (p.getTiempoLlegada() == i)
				{
					if (meterProceso(p, TP, TAL)) //si el proceso pudo entrar
						pasos.add(new PasoDeMVT(clonarHueco(TAL), clonarParticionProceso(TP), i)); //cada proceso que entre cuenta como un paso
				}
			}
			
		}
		
		return pasos;
	}
	
	//MEJOR AJUSTE
	public static boolean meterProceso(Proceso proceso, ArrayList<ParticionProceso> TP, ArrayList<Hueco> TAL)
	{
		boolean noPudoEntrar = true;
		Hueco hueco = TAL.get(0); //dummy para asegurarse de que la referencia no sea nula
		int orden = TAL.size() + 1; //sirve como dato inicial (orden) para comparar
		for (Hueco a : TAL)
		{	
			if (a.getTamanio() >= proceso.getTamanio())
			{
				if (a.getOrden() < orden) //busca el area libre con el menor orden
				{
					hueco = a;
					orden = hueco.getOrden();
				}
				noPudoEntrar = false;
			}
		}
		
		if (!noPudoEntrar)
		{
			TP.add(new ParticionProceso(hueco.getLocalidad(), proceso.getTamanio(), "Ocupado", proceso)); // se aloja el proceso
			hueco.setLocalidad(hueco.getLocalidad() + proceso.getTamanio()); //cambia la localidad del area libre
			hueco.setTamanio(hueco.getTamanio() - proceso.getTamanio()); //se reduce el tamanio del area libre
			hueco.setOrden(obtenerOrden(TAL, hueco.getTamanio(), hueco.getOrden()));
			
		}
		
		if (noPudoEntrar)
		{
			proceso.setTiempoLlegada(proceso.getTiempoLlegada() + 1); //se pospone hasta que pueda entrar 
			tiempo++;
			return false; //no se pudo meter el proceso
		}
		
		return true; //si se pudo meter el proceso
		
	}
	
	public static void sacarProceso(Particion particion, ArrayList<ParticionProceso> TP, ArrayList<Hueco> TAL)
	{	
		ArrayList<Hueco> auxiliarAreasLibres = new ArrayList<Hueco>();
		boolean contiguaAbajo = true; //caso tipico
		
		for (Hueco h : TAL)
		{
			if (particion.getLocalidad() == h.getLocalidad() + h.getTamanio()) //area libre contigua abajo
			{
				auxiliarAreasLibres.add(h);
			}
			
			if (particion.getLocalidad() + particion.getTamanio() == h.getLocalidad()) //area libre contigua arriba
			{
				auxiliarAreasLibres.add(h);
				contiguaAbajo = false;
			}
		}
		
		switch(auxiliarAreasLibres.size())
		{
		case 0: //no hay areas libres contiguas, entonces ocurre fragmentacion y se crea una nueva area libre
			Hueco areaLibre = new Hueco(particion.getLocalidad(), particion.getTamanio(), "Disponible", obtenerOrden(TAL, particion.getTamanio()));
			TAL.add(areaLibre);
			break;
			
		case 1: //un area libre contigua, se une con la actual
			if (contiguaAbajo)
			{
				auxiliarAreasLibres.get(0).setTamanio(auxiliarAreasLibres.get(0).getTamanio() + particion.getTamanio());
				auxiliarAreasLibres.get(0).setOrden(obtenerOrden(TAL, auxiliarAreasLibres.get(0).getTamanio()));
			}
			else
			{
				auxiliarAreasLibres.get(0).setLocalidad(particion.getLocalidad());
				auxiliarAreasLibres.get(0).setTamanio(auxiliarAreasLibres.get(0).getTamanio() + particion.getTamanio());
				auxiliarAreasLibres.get(0).setOrden(obtenerOrden(TAL, auxiliarAreasLibres.get(0).getTamanio()));
			}
			break;
			
		case 2: //dos particiones contiguas, una arriba y otra abajo, se unen las tres (aqui se elimina un hueco, por eso se actualiza el orden)
			Hueco arriba;
			Hueco abajo;
			if (auxiliarAreasLibres.get(1).getLocalidad() > auxiliarAreasLibres.get(0).getLocalidad())
			{
				arriba = auxiliarAreasLibres.get(1);
				abajo = auxiliarAreasLibres.get(0);
			}
			else
			{
				arriba = auxiliarAreasLibres.get(0);
				abajo = auxiliarAreasLibres.get(1);
			}

			abajo.setTamanio(abajo.getTamanio() + particion.getTamanio() + arriba.getTamanio());
			TAL.remove(arriba);
			actualizarOrden(TAL, arriba.getOrden());
			abajo.setOrden(obtenerOrden(TAL, abajo.getTamanio(), abajo.getOrden()));
			break;
		}
	}
	
	public static ArrayList<ParticionProceso> clonarParticionProceso(ArrayList<ParticionProceso> particion) //metodo para crear nueva referencia de un TP
	{
		ArrayList<ParticionProceso> clon = new ArrayList<ParticionProceso>();
		for (ParticionProceso p : particion)
		{
			clon.add(new ParticionProceso(p.getLocalidad(), p.getTamanio(), p.getEstado(), p.getProceso()));
		}
		return clon;
	}
	
	public static ArrayList<Hueco> clonarHueco(ArrayList<Hueco> particion) //metodo para crear nueva referencia de un TAL
	{
		ArrayList<Hueco> clon = new ArrayList<Hueco>();
		for (Hueco h : particion)
		{
			clon.add(new Hueco(h.getLocalidad(), h.getTamanio(), h.getEstado(), h.getOrden()));
		}
		return clon;
	}
	
	public static int obtenerOrden(ArrayList<Hueco> TAL, int tamanio) //caso en donde se quiere calcular el orden de un nuevo hueco
	{	
		int orden = 1; 
		
		for (Hueco h: TAL)
		{
			if (orden < h.getOrden()) //se calcula el orden mas grande de la tabla
				orden = h.getOrden();
		}
		
		for (int i = orden; i >= 1; i--) //del orden mas grande al mas chico
		{
			for (Hueco h: TAL)
			{
				if (h.getOrden() == i) //busca de un solo orden
				{
					if (tamanio <= h.getTamanio())
					{
						if (tamanio == h.getTamanio())
							orden = h.getOrden();
						else
						{
							
							orden = h.getOrden();
							h.setOrden(h.getOrden()+1);
						}
					}
					
					else
						if (i == orden)
							return ++orden;
						else 
							return orden;
				}
			}
		}
		
		return orden;
	}
	
	public static int obtenerOrden(ArrayList<Hueco> TAL, int tamanio, int antiguoOrden) //caso en donde se quiere actualizar el orden de un hueco existente que fue modificado
	{	
		int NuevoOrden = 1; 
		
		for (Hueco h: TAL)
		{
			if (NuevoOrden < h.getOrden()) //se calcula el orden mas grande de la tabla
				NuevoOrden = h.getOrden();
		}
		
		for (int i = NuevoOrden; i >= 1; i--) //del orden mas grande al mas chico
		{
			for (Hueco h: TAL)
			{
				if (h.getOrden() == i) //busca de un solo orden
				{
					if (tamanio <= h.getTamanio())
					{
						if (tamanio == h.getTamanio())
							NuevoOrden = h.getOrden();
						else
						{
							if (antiguoOrden > h.getOrden()) //solo cambiar orden si se cumple que el hueco es mas chico pero con mayor orden, pues si es mas chico su orden debe ser menor
							{
								NuevoOrden = h.getOrden();
								h.setOrden(h.getOrden()+1);
							}
						}
					}
					
					else
						if (i == NuevoOrden)
							return ++NuevoOrden;
						else 
							return NuevoOrden;
				}
			}
		}
		
		return NuevoOrden;
	}
	
	public static void actualizarOrden(ArrayList<Hueco> TAL, int orden) //metodo para actualizar el orden de todos los huecos al eliminar un hueco
	{
		for (Hueco h: TAL)
		{
			if (h.getOrden() == orden) //caso base, si el orden del hueco que fue eliminado tambien es el orden de otro hueco, entonces no hacer nada
				return;
		}
		
		for (Hueco h: TAL) //sino, mover el orden de cada hueco
		{
			if (h.getOrden() > orden)
				h.setOrden(h.getOrden()-1);
		}
	}
}
