package com.example.projectone;

import java.sql.Connection;
import java.sql.DriverManager;

public class mySqlConnectionClass {
    public static Connection doConnect() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/projectjava", "root", "arnav123");
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return con;
    }

    public static void main(String args[])
    {
        if(doConnect()==null)
            System.out.println("No connection");
        else
            System.out.println("Connected");
    }
}
