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

    public static void updateAnimal(Integer etaj, Integer nrCamere, Integer pret, Integer metri, Integer agent, Integer apartament) {
        try(Connection con = Connect.connect()) {
            String sql = "update Apartament set etaj = ?, nrCamere =?, Pret=?, metriPatrati=?, CodAgent =? where CodApartament=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, etaj);
            st.setInt(2, nrCamere);
            st.setInt(3, pret);
            st.setInt(4, metri);
            st.setInt(5, agent);
            st.setInt(6, apartament);

            st.execute();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
