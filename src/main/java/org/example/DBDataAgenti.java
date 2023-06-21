package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class DBDataAgenti {
    static DefaultTableModel tableModel = new DefaultTableModel(
            new Object[][]{},
            new Object[]{"CodAgent", "nume", "prenume", "varsta", "telefon"}
    );

    public static void loadDataAgent(JTable tabel) {
        try (Connection con = Connect.connect()){
            Statement st = con.createStatement();
            ResultSet response = st.executeQuery("select * from Agent order by id");

            tableModel.setRowCount(0);

            while (response.next()) {
                tableModel.addRow(new Object[]{
                        response.getString("CodAgent"),
                        response.getString("nume"),
                        response.getString("prenume"),
                        response.getString("varsta"),
                        response.getString("telefon")});
            }
            tabel.setModel(tableModel);

            response.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addAgent(String nume, String prenume, Integer varsta, String telefon) {
        try(Connection con = Connect.connect()) {
            String sql = "insert into Agent(nume, prenume, varsta, telefon) values(?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, nume);
            st.setString(2, prenume);
            st.setInt(3, varsta);
            st.setString(4, telefon);


            st.execute();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateAgent(String nume, String prenume, Integer varsta, String telefon, String cod) {
        try(Connection con = Connect.connect()) {
            String sql = "update Agent set nume = ?, prenume =?, varsta=?, telefon=? where CodAgent=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, nume);
            st.setString(2, prenume);
            st.setInt(3, varsta);
            st.setString(4, telefon);
            st.setString(5, cod);

            st.execute();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet getAgents(){
        try(Connection con = Connect.connect()) {
            String sql = "select CodAgent, nume from Agent";
            PreparedStatement st = con.prepareStatement(sql);

            return st.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
