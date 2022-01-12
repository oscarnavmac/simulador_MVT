package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import modelo.Hueco;
import modelo.ParticionProceso;
import modelo.PasoDeMVT;
import vista.Simulacion;

public class Controlador {
	
	private Simulacion simulacion;
	private static int paso;
	
	public Controlador(Simulacion simulacion, ArrayList<PasoDeMVT> pasos, int tamMemoria)
	{
		this.simulacion = simulacion;
		
		paso = 0;
		
		simulacion.btnPaso.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					
				if (paso < pasos.size())
					siguientePaso(paso, pasos, tamMemoria);
				else
					simulacion.dispose();
				
				paso++;
				simulacion.btnPaso.setText("Paso: " + paso);
				
				if (paso == pasos.size())
					simulacion.btnPaso.setText("Finalizada");
				
			}
		});
	}
	
	public void iniciar()
	{
		simulacion.setTitle("Simulación de asignación de memoria con MVT");
		simulacion.setLocationRelativeTo(null);
		simulacion.setResizable(false);
	}
	
	public void siguientePaso(int paso, ArrayList<PasoDeMVT> pasos, int tamMemoria)
	{
		PasoDeMVT p = pasos.get(paso);
		ArrayList<ParticionProceso> TP = p.getTP();
		ArrayList<Hueco> TAL = p.getTAL();
		
		simulacion.actualizarMemoria(TP, TAL, tamMemoria);
        simulacion.actualizarTablas(TP, TAL);
        
        simulacion.tiempo.setText("Tiempo: " + p.getTiempo());
	}
}
