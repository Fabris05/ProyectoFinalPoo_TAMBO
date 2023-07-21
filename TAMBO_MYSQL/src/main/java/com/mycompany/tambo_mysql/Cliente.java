/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tambo_mysql;

import Modelos.Ventas;
import SQL.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.*; 
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author fabri
 */


public class Cliente {
    String DNI;
    String Nombre;
    String Apellido;
    String Direccion;
    String Celular;
    String Email;
    String Pass;

    
    public Cliente(String DNI, String Nombre, String Apellido, String Direccion, String Celular, String Email, String Pass) {
        this.DNI = DNI;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Direccion = Direccion;
        this.Celular = Celular;
        this.Email = Email;
        this.Pass = Pass;
    }

    public Cliente(String Email, String Pass) {
        this.Email = Email;
        this.Pass = Pass;
    }

    Cliente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
    
    void RegistroDB(){
        
        if(DNI.isEmpty() || Nombre.isEmpty() || Apellido.isEmpty() || Direccion.isEmpty() || Celular.isEmpty() || Email.isEmpty() || Pass.isEmpty()){
            JOptionPane.showMessageDialog(null, "Debes rellenar todos los campos");
        }else{
            try{
            Connection con=Conexion.getConexion();
            PreparedStatement ps=con.prepareStatement("INSERT INTO CLIENTE (DNI, Nombre, Apellido, Direccion, Celular, Email ,Pass) VALUES(?,?,?,?,?,?,?)");
            
            ps.setString(1, DNI);
            ps.setString(2, Nombre);
            ps.setString(3, Apellido);
            ps.setString(4, Direccion);
            ps.setString(5, Celular);
            ps.setString(6, Email);
            ps.setString(7, Pass);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null , "Registro guardado, proceda a iniciar sesión");
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null , ex.toString());
            }
                Login LoginFrame = new Login();
                LoginFrame.setVisible(true);
                LoginFrame.pack();
                LoginFrame.setLocationRelativeTo(null);//Centra el formulario
        }  
    }
    
    void InicioSeionDB(){
            String url="SELECT * "+"FROM CLIENTE WHERE Email='"+Email+"'and Pass='"+Pass+"'";
            try{
               Connection con=Conexion.getConexion();
               PreparedStatement ps=con.prepareStatement(url); //Precompila la consulta de SQL
               ResultSet rs=ps.executeQuery();
               
               if(rs.next()){
                   //Si existe el Email
                   String em=rs.getString("Email");
                   String pa=rs.getString("Pass");
                   String nom=rs.getString("Nombre");
                   String ape=rs.getString("Apellido");
                   String dni=rs.getString("DNI");
                   
                   if(Pass.equals(pa)){
                       //Si la contraseña es igual, se redirecciona a siguiente proceso
                       
                       JOptionPane.showMessageDialog(null, "Bienvenido");
                       
                       Login Lg=new Login();
                       Lg.setVisible(false);
                       
                       MenuPrincipal Menu=new MenuPrincipal();
                       Menu.setVisible(true);
                       Menu.setLocationRelativeTo(null);
                       
                       nom=nom.toUpperCase();
                       ape=ape.toUpperCase();
                       
                       Menu.jlbDNI_Historial.setText(nom+" "+ape);
                       MenuPrincipal.jlbDNIHis.setText(" "+dni);
                        
                       Login lg=new Login();
                       lg.dispose();
                       
                       
                       
                   }else{
                       JOptionPane.showMessageDialog(null, "La contraseña es incorrecta");
                   }
               }else{
                   //El Email no existe
                    JOptionPane.showMessageDialog(null, "El correo electrónico no está registrado");
               }
               
               
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null, toString());
            }
            
            
        }
    
    public void Tabla(){
        
    }
    
}
