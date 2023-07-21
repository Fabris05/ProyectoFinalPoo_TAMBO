/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import SQL.Conexion;
import com.mycompany.tambo_mysql.Login;
import com.mycompany.tambo_mysql.MenuPrincipal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author fabri
 */
public class MetodoPago {
    
    String NumTarjeta;
    String FechaVencimiento;
    String CCV;

    public MetodoPago(String NumTarjeta, String FechaVencimiento, String CCV) {
        this.NumTarjeta = NumTarjeta;
        this.FechaVencimiento = FechaVencimiento;
        this.CCV = CCV;
    }
    
    public void ValidarTarjeta(){
        String url="SELECT NumTarjeta, FechaVencimiento, CCV FROM TARJETA WHERE NumTarjeta='"+NumTarjeta+"' "+"and FechaVencimiento='"+
                FechaVencimiento+"' and CCV='"+CCV+"'";
        
        try{
               Connection con=Conexion.getConexion();
               PreparedStatement ps=con.prepareStatement(url); //Precompila la consulta de SQL
               ResultSet rs=ps.executeQuery();
               
               if(rs.next()){
                   String nt=rs.getString("NumTarjeta");
                   String fv=rs.getString("FechaVencimiento");
                   String ccv=rs.getString("CCV");
                   
                   if(NumTarjeta.equals(nt) && FechaVencimiento.equals(fv) && CCV.equals(ccv)){

                     JOptionPane.showMessageDialog(null, "COMPRA PROCESADA!");
                     
                     
                     //Guardamos la compra en la base de datos
                     String DNI=MenuPrincipal.jtxtDNI_Cliente.getText().trim();
                     String Importe=MenuPrincipal.jlbResumenTotal.getText().trim();
                     LocalDateTime fechaHoraActual = LocalDateTime.now();
                     DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
                     String fechaHoraFormateada = fechaHoraActual.format(formato);
                     
                     PreparedStatement BD=con.prepareStatement("INSERT INTO VENTAS (DNI, Fecha, MetodoPago, Importe) VALUES(?,?,?,?)");
                     BD.setString(1, DNI);
                     BD.setString(2, fechaHoraFormateada);
                     BD.setString(3, "Tarjeta Bancaria");
                     BD.setString(4, Importe);
                     BD.executeUpdate(); 
                     
                     MenuPrincipal.jtxtNumTarjeta.setText("");
                     MenuPrincipal.jtxtFechaVencimiento.setText("");
                     MenuPrincipal.jtxtCCV.setText("");
                     
                   }else{
                       JOptionPane.showMessageDialog(null, "PAGO RECHAZADO, REVISE LOS DATOS INGRESADOS");
                   }
               }
        }catch(SQLException ex){
            System.err.println(ex); 
        }
    }
}
