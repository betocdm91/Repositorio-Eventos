/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.swing.JOptionPane;

/**
 *
 * @author Aspire
 */
@WebService
@Stateless
@LocalBean
public class ServicioWeb {

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement stmt = null;
    
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    @WebMethod(operationName = "marcadores")
    public Object[] marcadores() {
        //TODO write your implementation code here:
        
        List<String> listMarcadores = new ArrayList<String>();
        
        //String query= "SELECT nombreEvento FROM dbo.Evento";
        String query= "SELECT latitud, longitud, nombreEvento, idTipo FROM dbo.Evento";
        
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con=DriverManager.getConnection(
                    "jdbc:sqlserver://localhost\\ASPIRE-PC:1433;databaseName=Eventos","sa","root1234");
            stmt=con.prepareStatement(query);
            //stmt.getResultSet();
            rs=stmt.executeQuery();
            
            while(rs.next()){
                listMarcadores.add(rs.getString("latitud") 
                        +";"+ rs.getString("longitud") 
                        +";"+ rs.getString("nombreEvento"));
            }
            con.close();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return listMarcadores.toArray();//lista.toArray();
    }
    
    @WebMethod(operationName = "eventos")
    public Object[] eventos() {
        //TODO write your implementation code here:
        
        List<String> listEventos = new ArrayList<String>();
        
        String query= "select nombreEvento, fechaEvento, horaInicio, horaFin, detalleEvento, imagenEvento "
                + "from Evento";
        
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con=DriverManager.getConnection(
                    "jdbc:sqlserver://localhost\\ASPIRE-PC:1433;databaseName=Eventos","sa","root1234");
            stmt=con.prepareStatement(query);
            //stmt.getResultSet();
            rs=stmt.executeQuery();
            
            while(rs.next()){
                listEventos.add(rs.getString("nombreEvento") 
                        +";"+ rs.getString("fechaEvento") 
                        +";"+ rs.getString("horaInicio")
                        +";"+ rs.getString("detalleEvento")
                        +";"+ rs.getString("imagenEvento"));
            }
            con.close();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return listEventos.toArray();//lista.toArray();
    }
    
    @WebMethod(operationName = "logueoSQL")
    public String logueoSQL(@WebParam(name = "user") String user, @WebParam(name = "pass") String pass) {
        //TODO write your implementation code here:
        
        String msj="";
        String query = "SELECT usuario, contrasenia FROM dbo.Usuario where usuario=? and contrasenia =?;";
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con=DriverManager.getConnection("jdbc:sqlserver://localhost\\ASPIRE-PC:1433;databaseName=Eventos","sa","root1234");
            stmt=con.prepareStatement(query);
            stmt.setString(1,user);
            stmt.setString(2,pass);
            
            rs=stmt.executeQuery();
            if(rs.next()){
                msj = "correcto";
            }else{
                msj = "usuario incorrecto";
            }
            con.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return msj;
    }
}
