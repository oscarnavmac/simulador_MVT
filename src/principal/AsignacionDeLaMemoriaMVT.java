package principal;

import java.util.ArrayList;

import controlador.Controlador;
import modelo.MVT;
import modelo.PasoDeMVT;
import modelo.Proceso;
import vista.Simulacion;

public class AsignacionDeLaMemoriaMVT {
	
	private static final int tiempoInfinito = -1;
	
	public static void main(String[] args)
	{
		//creacion de procesos
		Proceso SO = new Proceso("SO", 10, 0, tiempoInfinito); //el sistema operativo tiene tiempo de llegada 0 porque es un proceso que asumimos que ya esta alojado en memoria
		Proceso A = new Proceso("A", 8, 1, 7);
		Proceso B = new Proceso("B", 14, 2, 7);
		Proceso C = new Proceso("C", 18, 3, 4);
		Proceso D = new Proceso("D", 6, 4, 6);
		Proceso E = new Proceso("E", 14, 5, 5);
		
		//Proceso F = new Proceso("F", 10, 10, 5); //proceso que puede ser usado para verificar que el algoritmo funciona bien
		
		//se agregan a la cola de procesos
		ArrayList<Proceso> procesos = new ArrayList<Proceso>();
		procesos.add(A);
		procesos.add(B);
		procesos.add(C);
		procesos.add(D);
		procesos.add(E);
		//procesos.add(F);
		
		Simulacion simulacion = new Simulacion(procesos);
		
		//tamanio de la memoria
		int tamMemoria = 64;
		
		ArrayList<PasoDeMVT> pasos = MVT.iniciar(procesos, tamMemoria, SO);
		
		Controlador controlador = new Controlador(simulacion, pasos, tamMemoria);
		
		controlador.iniciar();
		simulacion.setVisible(true);
	}
}
