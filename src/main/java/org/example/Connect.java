package org.example;

import java.sql.*;

public class Connect {
    public static void connect(){
    Connection conn = null;
        try {
        // db parameters
        String url = "jdbc:sqlite:apartamente.db";
        // create a connection to the database
        conn = DriverManager.getConnection(url);
        Statement st = conn.createStatement();

        st.execute("create table if not exists Agent(" +
                    "CodAgent integer primary key autoincrement," +
                    "nume char(30)," +
                    "prenume char(30)," +
                    "varsta integer," +
                    "telefon char(30))");

        st.execute("create table if not exists Apartament(" +
                "CodApartament integer primary key autoincrement," +
                "etaj integer," +
                "nrCamere integer," +
                "Pret integer," +
                "metriPatrati integer, " +
                "CodAgent integer," +
                "constraint CodAgentFK foreign key (CodAgent) " +
                "references Agent(CodAgent))");

        System.out.println("Connection to SQLite has been established.");
    } catch (
    SQLException e) {
        System.out.println(e.getMessage());
    } finally {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
}
