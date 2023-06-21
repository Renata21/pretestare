package org.example;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.DBDataAgenti.*;
import static org.example.DBDataApartamente.*;
import static org.example.DBDataApartamente.loadDataApartamente;
import static org.example.Frame.createResultDialog;
import static org.example.Main.apartamentFrame;

public class AgentFrame extends JFrame {
    JMenuBar menuBar = new JMenuBar();
    JMenu apartamante = new JMenu("Apartamente");
    JMenu agenti = new JMenu("Agenti");

    JTable tableAgent = new JTable();
    JButton add = new JButton();
    JButton edit = new JButton();
    JButton interogare = new JButton();
    JButton interogare2 = new JButton();
    JButton interogare3 = new JButton();

    public AgentFrame(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel bpanel = new JPanel();

        menuBar.add(apartamante);
        apartamante.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent menuEvent) {
                setVisible(false);
                apartamentFrame.setVisible(true);
            }

            @Override
            public void menuDeselected(MenuEvent menuEvent) {

            }

            @Override
            public void menuCanceled(MenuEvent menuEvent) {

            }
        });

        menuBar.add(agenti);

        DBDataAgenti.loadDataAgent(tableAgent);
        JTableHeader tableHeader = tableAgent.getTableHeader();
        tableHeader.setBackground(Color.BLACK);
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setFont(new Font("Serif", Font.BOLD, 15));
        tableAgent.removeColumn(tableAgent.getColumnModel().getColumn(0));

        bpanel.setLayout(new GridLayout(1,2));

        add.setText("Adaugare");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String[] input = AddEditAgentDialog.showAddEditInputDialog(panel, "Adauga apartament", new String[]{});
                if (input != null) {
                    addAgent(input[0], input[1], Integer.valueOf(input[2]), input[3]);
                    loadDataAgent(tableAgent);
                }
            }

        });

        edit.setText("Editare");
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int selectedRow = tableAgent.getSelectedRow();

                if (selectedRow != -1) {
                    Integer id = Integer.valueOf((String) tableAgent.getModel().getValueAt(tableAgent.getSelectedRow(), 0));
                    String[] data = {
                            (String) tableAgent.getModel().getValueAt(tableAgent.getSelectedRow(), 1),
                            (String) tableAgent.getModel().getValueAt(tableAgent.getSelectedRow(), 2),
                            (String) tableAgent.getModel().getValueAt(tableAgent.getSelectedRow(), 3),
                            (String) tableAgent.getModel().getValueAt(tableAgent.getSelectedRow(), 4),
                    };

                    String[] input = AddEditAgentDialog.showAddEditInputDialog(panel, "Modifica agent", data);

                    if (input != null) {
                        updateAgent(input[0], input[1], Integer.valueOf(input[2]), input[3], id);
                        loadDataAgent(tableAgent);
                    }
                }
            }

        });
        bpanel.add(add);
        bpanel.add(edit);

        interogare2.setText("Interogare 2");

        interogare2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ResultSet resultSet = null;
                resultSet = listAgenti(20, 30);
                String[] columnNames = {"Nume", "Prenume", "Varsta", "Telefon"};
                createResultDialog("Agenti diapazon 20-30 ani", columnNames, resultSet);
            }
        });
        bpanel.add(interogare2);

        interogare3.setText("Interogare 3 ");

        interogare3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ResultSet resultSet = null;
                resultSet = agentivanzari();
                String[] columnNames = {"Nume", "Prenume", "Varsta", "Telefon"};
                createResultDialog("Agenti cu vanzari", columnNames, resultSet);
            }
        });

        bpanel.add(interogare3);

        JScrollPane scrollPane = new JScrollPane();


        panel.add(tableAgent, BorderLayout.CENTER);
        scrollPane.setViewportView(tableAgent);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bpanel, BorderLayout.SOUTH);

        setJMenuBar(menuBar);
        pack();
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setLocationRelativeTo(null);
        setName("Agent");
    }

}
