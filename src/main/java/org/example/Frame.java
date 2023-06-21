package org.example;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.DBDataApartamente.*;
import static org.example.Main.apartamentFrame;

public class Frame extends JFrame {
    JMenuBar menuBar = new JMenuBar();
    JMenu apartamante = new JMenu("Apartamente");
    JMenu agenti = new JMenu("Agenti");
    JTable tableApartamente = new JTable();
    JButton add = new JButton();
    JButton edit = new JButton();
    JButton interogare = new JButton();

    public Frame(AgentFrame agentFrame){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel bpanel = new JPanel();

        menuBar.add(apartamante);

        agenti.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent menuEvent) {
                agentFrame.setVisible(true);
                setVisible(false);
            }

            @Override
            public void menuDeselected(MenuEvent menuEvent) {

            }

            @Override
            public void menuCanceled(MenuEvent menuEvent) {

            }
        });

        menuBar.add(agenti);

        DBDataApartamente.loadDataApartamente(tableApartamente);
        JTableHeader tableHeader = tableApartamente.getTableHeader();
        tableHeader.setBackground(Color.BLACK);
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setFont(new Font("Serif", Font.BOLD, 15));
        tableApartamente.removeColumn(tableApartamente.getColumnModel().getColumn(0));

        bpanel.setLayout(new GridLayout(1,2));

        add.setText("Adaugare");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String[] input = AddEditApartamentDialog.showAddEditInputDialog(panel, "Adauga apartament", new String[]{});
                if (input != null) {
                    addApartament(Integer.valueOf(input[0]), Integer.valueOf(input[1]), Integer.valueOf(input[2]), Integer.valueOf(input[3]), Integer.valueOf(input[4]));
                    loadDataApartamente(tableApartamente);
                }
            }

        });

        edit.setText("Editare");
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int selectedRow = tableApartamente.getSelectedRow();

                if (selectedRow != -1) {
                    Integer id = Integer.valueOf((String) tableApartamente.getModel().getValueAt(tableApartamente.getSelectedRow(), 0));
                    String[] data = {
                            (String) tableApartamente.getModel().getValueAt(tableApartamente.getSelectedRow(), 2),
                            (String) tableApartamente.getModel().getValueAt(tableApartamente.getSelectedRow(), 3),
                            (String) tableApartamente.getModel().getValueAt(tableApartamente.getSelectedRow(), 4),
                            (String) tableApartamente.getModel().getValueAt(tableApartamente.getSelectedRow(), 5)
                    };

                    String[] input = AddEditApartamentDialog.showAddEditInputDialog(panel, "Modifica apartament", data);

                    if (input != null) {
                        updateApartament(input[0], input[1], input[2], input[3], input[4], id);
                        loadDataApartamente(tableApartamente);
                    }
                }
            }

        });

        interogare.setText("Interogare");
        interogare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ResultSet resultSet = listApartament();
                String[] columnNames = {"Etaj", "NrCamere", "Pret", "MetriPatrati",};
                createResultDialog("Apartamente cu 4 camere, etaje 2 și 3", columnNames, resultSet);
            }
        });

        bpanel.add(add);
        bpanel.add(edit);
        bpanel.add(interogare);

        JScrollPane scrollPane = new JScrollPane();


        panel.add(tableApartamente, BorderLayout.CENTER);
        scrollPane.setViewportView(tableApartamente);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bpanel, BorderLayout.SOUTH);

        setJMenuBar(menuBar);
        pack();
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setLocationRelativeTo(null);
        setName("Apartament");
    }
    public static void createResultDialog(String title, String[] columnNames, ResultSet resultSet) {
        JDialog dialog = new JDialog(apartamentFrame, title, true);

        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable resultTable = new JTable(model);

        try {
            while (resultSet.next()) {
                Object[] rowData = new Object[columnNames.length];
                for (int i = 0; i < columnNames.length; i++) {
                    rowData[i] = resultSet.getString(columnNames[i]);
                }
                model.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(resultTable);
        dialog.getContentPane().add(scrollPane);

        dialog.pack();
        dialog.setLocationRelativeTo(apartamentFrame);
        dialog.setVisible(true);
    }

}
