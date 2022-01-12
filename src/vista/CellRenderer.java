/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
/**
 *
 * @author negri
 */
@SuppressWarnings("serial")
public class CellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable tabla, Object valor, boolean seleccion, boolean centrado, int row, int column){
        //establecer fondo como vacio
        setBackground(Color.DARK_GRAY);
        
        //constructor de la clase
        super.getTableCellRendererComponent(tabla,valor, seleccion, centrado, row, column);
        
        boolean oddRow = (row % 2 == 0);
        
        Color c = new Color(93,193,185);
        
        if(oddRow){
            setBackground(c);
            setForeground(Color.WHITE);
            setFont(new Font("HP Simplified Light",Font.BOLD,12));
        }
        
        if(!oddRow){
           setForeground(Color.WHITE); 
           setFont(new Font("HP Simplified Light",Font.BOLD,12));
        }
        setHorizontalAlignment(SwingConstants.CENTER); //alineacion centro
        
        return this;
    }
    
}
