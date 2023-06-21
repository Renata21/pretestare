package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class DBDataApartamente {
    static DefaultTableModel tableModel = new DefaultTableModel(
            new Object[][]{},
            new Object[]{"CodApartament", "etaj", "nrCamere", "Pret", "metriPatrati", "CodAgent"}
    );

    public static void loadDataApartamente(JTable tabel) {
        try (Connection con = Connect.connect()){
            Statement st = con.createStatement();
            ResultSet response = st.executeQuery("select * from Apartament order by CodApartament");

            tableModel.setRowCount(0);

            while (response.next()) {
                tableModel.addRow(new Object[]{
                        response.getString("CodApartament"),
                        response.getString("etaj"),
                        response.getString("nrCamere"),
                        response.getString("Pret"),
                        response.getString("metriPatrati"),
                        response.getString("CodAgent")});
            }
            tabel.setModel(tableModel);

            response.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addApartament(Integer etaj, Integer nrCamere, Integer pret, Integer metri, Integer agent) {
        try(Connection con = Connect.connect()) {
            String sql = "insert into Apartament(etaj, nrCamere, Pret, metriPatrati, codAgent) values(?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, etaj);
            st.setInt(2, nrCamere);
            st.setInt(3, pret);
            st.setInt(4, metri);
            st.setInt(5, agent);

            st.execute();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateApartament(String etaj, String nrCamere, String pret, String metri, String agent, Integer apartament) {
        try(Connection con = Connect.connect()) {
            String sql = "update Apartament set etaj = ?, nrCamere =?, Pret=?, metriPatrati=?, CodAgent =? where CodApartament=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, Integer.parseInt(etaj));
            st.setInt(2, Integer.parseInt(nrCamere));
            st.setInt(3, Integer.parseInt(pret));
            st.setInt(4, Integer.parseInt(metri));
            st.setInt(5, Integer.parseInt(agent));
            st.setInt(6, apartament);

            st.execute();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet listApartament() {
        ResultSet resultset = null;
        Connection con = Connect.connect();
        try {
            Statement statement = con.createStatement();
            resultset = statement.executeQuery("SELECT * FROM Apartament WHERE NrCamere = 4 AND Etaj IN (2, 3)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultset;
    }

    public static ResultSet listAgenti(int varstaMinima, int varstaMaxima) {
        ResultSet resultSet = null;
        Connection con = Connect.connect();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "SELECT * FROM Agent WHERE Varsta >= ? AND Varsta <= ?"
            );
            preparedStatement.setInt(1, varstaMinima);
            preparedStatement.setInt(2, varstaMaxima);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet agentivanzari() {
        ResultSet resultSet = null;
        Connection con = Connect.connect();
        try {
            Statement statement = con.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT Agent.*, SUM(Apartament.Pret) AS TotalVanzari " +
                            "FROM Agent LEFT JOIN Apartament ON Agent.CodAgent = Apartament.CodAgent " +
                            "GROUP BY Agent.CodAgent"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
