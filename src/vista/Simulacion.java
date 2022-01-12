package vista;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.LineBorder;

import modelo.Hueco;
import modelo.ParticionProceso;
import modelo.Proceso;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

@SuppressWarnings("serial")
public class Simulacion extends JFrame {

	private int infinito = -1;
	private JPanel contentPane;
	
	public JButton btnPaso;
	public JPanel memoria;
	private JPanel etiqueta;
        
        //Tablas
        private JTable tab;
        private JTable parti;
        private JTable ar;
        
        //titulos
        private JLabel titulo;
        private JLabel entrada;
        private JLabel area;
        private JLabel part;
        private JLabel mem;
        public JLabel tiempo;
        
        public DefaultTableModel modeloTP;
        public DefaultTableModel modeloTAL;
        public DefaultTableModel modeloTabla;
        
        public JScrollPane scroll1;
        public JScrollPane scroll2;
        public JScrollPane scroll3;
        
        private Color fondo;

	public Simulacion(ArrayList<Proceso> procesos) {
                
        fondo = new Color(245, 243, 234);
                
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1089, 745);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(fondo);
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		
		memoria = new JPanel();
		memoria.setBackground(fondo);
		memoria.setBounds(750, 120, 200, 550);
		contentPane.add(memoria);
		memoria.setLayout(null);
		
		btnPaso = new JButton("Iniciar");
		btnPaso.setForeground(Color.WHITE);
		btnPaso.setBackground(new Color(67, 115, 113));
		btnPaso.setFont(new Font("HP Simplified Light", Font.BOLD, 20));
		btnPaso.setBounds(392, 607, 197, 70);
		contentPane.add(btnPaso);
		
		etiqueta = new JPanel();
		etiqueta.setBounds(memoria.getX()-30, memoria.getY(), 30, memoria.getHeight());
		etiqueta.setBackground(fondo);
		contentPane.add(etiqueta);
		etiqueta.setLayout(null);
                
        //Titulo
        titulo=new JLabel("SIMULACIÓN DE ASIGNACIÓN DE MEMORIA CON MVT");
		titulo.setFont(new Font("HP Simplified Light", Font.BOLD, 18));
		titulo.setForeground(Color.BLACK);
        titulo.setBounds(20,10,650,40);
        contentPane.add(titulo);
                
        //Etiquetas
        entrada=new JLabel("Entradas del problema");
		entrada.setFont(new Font("HP Simplified Light", Font.BOLD, 14));
		entrada.setForeground(Color.BLACK);
        entrada.setBounds(20,40,200,40);
        contentPane.add(entrada);
                
        area=new JLabel("Tabla de Áreas Libres");
		area.setFont(new Font("HP Simplified Light", Font.BOLD, 14));
		area.setForeground(Color.BLACK);
        area.setBounds(20,220,200,40);
        contentPane.add(area);
                
        part=new JLabel("Tabla de Particiones");
		part.setFont(new Font("HP Simplified Light", Font.BOLD, 14));
		part.setForeground(Color.BLACK);
        part.setBounds(20,350,200,40);
        contentPane.add(part);
        
        mem = new JLabel("Representación Gráfica");
        mem.setFont(new Font("HP Simplified Light", Font.BOLD, 14));
        mem.setForeground(Color.BLACK);
        mem.setBounds(memoria.getX(), entrada.getY()+20, 200, 40);
        contentPane.add(mem);
        
        tiempo = new JLabel("Tiempo: ");
        tiempo.setFont(new Font("HP Simplified Light", Font.BOLD, 18));
        tiempo.setForeground(Color.BLACK);
        tiempo.setBounds(40, btnPaso.getY(), 150, 30);
        contentPane.add(tiempo);
        
        
                
        //Tablas
        modeloTabla = new DefaultTableModel();
        
        modeloTabla.addColumn("Proceso");
        modeloTabla.addColumn("Tamaño (K)");
        modeloTabla.addColumn("Tiempo de Llegada");
        modeloTabla.addColumn("Duración");
        
        for (Proceso p: procesos)
        	modeloTabla.addRow(new Object[] {p.getNombre(), p.getTamanio(), p.getTiempoLlegada(), p.getDuracion()});
        
        tab = new JTable(modeloTabla);
        tab.setRowSelectionAllowed(false);
        tab.setBounds(20, 75, 500, 100);
        setCellRender(tab);
        theader(tab);
        contentPane.add(tab);
        
        scroll3 = new JScrollPane(tab, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll3.setBounds(tab.getBounds());
        //scroll3.getViewport().setBackground(fondo);
        scroll3.setBackground(fondo);
        //scroll3.setBorder(BorderFactory.createEmptyBorder());
        contentPane.add(scroll3);
                
        //Tabla areas libres
        modeloTAL = new DefaultTableModel();
        
        modeloTAL.addColumn("Localidad (K)");
        modeloTAL.addColumn("Tamaño (K)");
        modeloTAL.addColumn("Estado");
        modeloTAL.addColumn("Orden");
        
        ar = new JTable(modeloTAL);
        ar.setRowSelectionAllowed(false);
        ar.setBounds(20, 250, 400, 100);
        ar.setGridColor(fondo);
        setCellRender(ar);
        theader(ar);
        contentPane.add(ar);
        
        scroll1 = new JScrollPane(ar, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll1.setBounds(ar.getBounds());
        scroll1.getViewport().setBackground(fondo);
        scroll1.setBackground(fondo);
        scroll1.setBorder(BorderFactory.createEmptyBorder());
        contentPane.add(scroll1);
        
        
        //Tabla particiones
        modeloTP = new DefaultTableModel();
        
        modeloTP.addColumn("Localidad (K)");
        modeloTP.addColumn("Tamaño (K)");
        modeloTP.addColumn("Estado");
        modeloTP.addColumn("Proceso");
        
        parti = new JTable(modeloTP);
        parti.setBounds(20, 380, 400, 100);
        parti.setGridColor(fondo);
        setCellRender(parti);
        theader(parti);
        contentPane.add(parti);
        
        scroll2 = new JScrollPane(parti, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll2.setBounds(parti.getBounds());
        scroll2.getViewport().setBackground(fondo);
        scroll2.setBackground(fondo);
        scroll2.setBorder(BorderFactory.createEmptyBorder());
        contentPane.add(scroll2);       
                
	}
	
        public void setCellRender(JTable tabla){
            Enumeration<TableColumn> en = tabla.getColumnModel().getColumns();
            while (en.hasMoreElements()){
                TableColumn tc = en.nextElement();
                tc.setCellRenderer(new CellRenderer());
            }
        }
        
        public void theader(JTable tabla){
            JTableHeader thead= tabla.getTableHeader();
            thead.setForeground(Color.WHITE);
            thead.setBackground(Color.DARK_GRAY);
            thead.setFont(new Font("HP Simplified Light",Font.BOLD, 12));
            
            
        }
        
        public void actualizarTablas(ArrayList<ParticionProceso> TP, ArrayList<Hueco> TAL){
           
  
        	modeloTAL.setRowCount(0); //reinicia tabla TAL
        	modeloTP.setRowCount(0); //reinicia tabla TP
            
            
            //Tabla area libre
        	for (Hueco h: TAL)
        		modeloTAL.addRow(new Object[] {h.getLocalidad(), h.getTamanio(), h.getEstado(), h.getOrden()});
            
            //Tabla particiones
        	for (ParticionProceso p: TP)
        		if (p.getProceso().getDuracion() != infinito) //para no colocar al sistema operativo en la tabla de particiones
        			modeloTP.addRow(new Object[] {p.getLocalidad(), p.getTamanio(), p.getEstado(), p.getProceso().getNombre()});
        	
         
        }
        
	public void actualizarMemoria(ArrayList<ParticionProceso> TP, ArrayList<Hueco> TAL, int tamMemoria)
	{
		memoria.removeAll();
		etiqueta.removeAll();
		
		Color colorHueco;
		
		if (TAL.size() == 1) //no hay fragmentacion
			colorHueco = Color.DARK_GRAY; //gris
		else //fragmentacion
			colorHueco = new Color(172, 56, 56); //rojo
		
		int ratio = memoria.getHeight()/tamMemoria; 
		
		JLabel limite = new JLabel(tamMemoria + "k");
		limite.setBounds(0, tamMemoria*ratio - 10, etiqueta.getWidth(), 15);
		limite.setFont(new Font("HP Simplified Light", Font.BOLD, 15));
		etiqueta.add(limite);
		
		
		for (ParticionProceso p: TP)
		{
			//particion
			JPanel particion = new JPanel();
			if (p.getProceso().getDuracion() == infinito) //particion del sistema
				particion.setBackground(new Color(66, 181, 139));
			else
				particion.setBackground(new Color(15, 106, 179));
			particion.setBorder(new LineBorder(fondo, 3));
			particion.setBounds(0, p.getLocalidad()*ratio, memoria.getWidth(), p.getTamanio()*ratio);
			particion.setLayout(new GridBagLayout());
			memoria.add(particion);
		
			
			//etiqueta para nombre de particion
			JLabel proceso = new JLabel(p.getProceso().getNombre());
			proceso.setFont(new Font("HP Simplified Light", Font.BOLD, 18));
			proceso.setForeground(Color.WHITE);
			particion.add(proceso);
			
			//etiqueta para localidad de particon
			JLabel localidad = new JLabel(Integer.toString(p.getLocalidad())+"k");
			localidad.setBounds(0, p.getLocalidad()*ratio, etiqueta.getWidth(), 15);
			localidad.setFont(new Font("HP Simplified Light", Font.BOLD, 15));
			etiqueta.add(localidad);
			setContentPane(contentPane);
                        
		}
		
		//areas libres
		for (Hueco h: TAL)
		{
			//particion
			JPanel particion = new JPanel();
			particion.setBackground(colorHueco);
			particion.setBorder(new LineBorder(fondo, 3));
			particion.setBounds(0, h.getLocalidad()*ratio, memoria.getWidth(), h.getTamanio()*ratio);
			particion.setLayout(new GridBagLayout());
			memoria.add(particion);
		
			
			//etiqueta para areas libres
			JLabel proceso = new JLabel("area libre");
			proceso.setFont(new Font("HP Simplified Light", Font.BOLD, 18));
			proceso.setForeground(Color.WHITE);
			particion.add(proceso);
			
			//etiqueta para localidad de area libre
			JLabel localidad = new JLabel(Integer.toString(h.getLocalidad())+"k");
			localidad.setBounds(0, h.getLocalidad()*ratio, etiqueta.getWidth(), 15);
			localidad.setFont(new Font("HP Simplified Light", Font.BOLD, 15));
			etiqueta.add(localidad);
			setContentPane(contentPane);
		}
		
	}
}
