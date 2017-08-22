package com.example.aspire.tiquets;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

/**
 * Created by CARLOS OSORIO on 21/8/2017.
 */

public class ConexionBase {

    private static ConexionBase instance=null;
    private static final String URL="jdbc:jtds:sqlserver://localhost:1433/Eventos;";
    private static final String USER="sa";
    private static final String PASS="root";
    private static  Connection connection=null;
    String classs = "net.sourceforge.jtds.jdbc.Driver";


    private ConexionBase(){}

    public static ConexionBase getInstance(){
        if(instance==null)
            instance=new ConexionBase();
        return instance;
    }

    public Connection getConnection(){
        if (connection==null)
            connection=conectar();
        return connection;
    }

    private Connection conectar(){
        Connection conn=null;
        try {
            //(new Driver()).getClass();
            Class.forName(classs);
            conn= DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return conn;
    }
}
