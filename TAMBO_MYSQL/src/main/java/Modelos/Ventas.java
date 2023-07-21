/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import SQL.Conexion;
import com.mycompany.tambo_mysql.MenuPrincipal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author fabri
 */
public class Ventas {
    
    
    
    public void LlenarTabla(){ 
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Código");
        tableModel.addColumn("DNI");
        tableModel.addColumn("Fecha");
        tableModel.addColumn("Método de Pago");
        tableModel.addColumn("Importe");
        
        String DNI = MenuPrincipal.jlbDNIHis.getText().trim();
        try{
            Connection con = Conexion.getConexion();
            System.out.println("DNI3:" +DNI);
            PreparedStatement sp = con.prepareStatement("SELECT * FROM VENTAS WHERE DNI = ?");
            sp.setString(1, DNI);
            ResultSet rt = sp.executeQuery();

            while (rt.next()) {
                int Codigo = rt.getInt("Codigo");
                String DNI1 = rt.getString("DNI");
                String Fecha = rt.getString("Fecha");
                String MetodoPago = rt.getString("MetodoPago");
                String Importe = rt.getString("Importe");

                System.out.println(""+Codigo);
                System.out.println(""+DNI1);

                Object[] fila = { Codigo, DNI1, Fecha, MetodoPago, Importe };
                tableModel.addRow(fila);
                        
            }
            MenuPrincipal.jtblHistorial.setModel(tableModel);
            System.out.println(""+DNI);
 
            }catch(SQLException ex){
                System.err.println(ex);
        }
                       
    }
 
}
