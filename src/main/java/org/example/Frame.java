package org.example;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.example.DBDataApartamente.addApartament;
import static org.example.DBDataApartamente.loadDataApartamente;

public class Frame extends JFrame {
    JMenuBar menuBar = new JMenuBar();
    JMenu apartamante = new JMenu("Apartamente");
    JMenu agenti = new JMenu("Agenti");
    JTable tableApartamente = new JTable();
    JButton add = new JButton();
    JButton edit = new JButton();

    public Frame(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel bpanel = new JPanel();

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

            }
        });

        bpanel.add(add);
        bpanel.add(edit);

        JScrollPane scrollPane = new JScrollPane();


        panel.add(tableApartamente, BorderLayout.CENTER);
        scrollPane.setViewportView(tableApartamente);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bpanel, BorderLayout.SOUTH);


        pack();
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setLocationRelativeTo(null);
        setName("Animale");
    }

}
