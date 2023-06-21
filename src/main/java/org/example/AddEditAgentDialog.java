package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.DBDataAgenti.getAgents;

public class AddEditAgentDialog {
    static JTextField nume = new JTextField(10);
    static JTextField prenume = new JTextField(10);

    static  JSpinner varsta = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

    static JTextField telefon = new JTextField(10);

    public static String[] showAddEditInputDialog(Component parentComponent, String option, String[] initial_data) {
        JPanel principalPanel = new JPanel(new GridLayout(4, 1, 20, 0));

        JPanel numePanel = new JPanel(new FlowLayout());
        numePanel.add(new Label("Nume: "));
        numePanel.add(nume);

        JPanel prenumePanel = new JPanel((new FlowLayout()));
        prenumePanel.add(new Label("Prenume: "));
        prenumePanel.add(prenume);

        JPanel varstaPanel = new JPanel((new FlowLayout()));
        varstaPanel.add(new Label("Varsta: "));
        varstaPanel.add(varsta);

        JPanel telPanel = new JPanel((new FlowLayout()));
        telPanel.add(new Label("Telefon: "));
        telPanel.add(telefon);


        if (initial_data.length == 4) {
            nume.setText(initial_data[0]);
            prenume.setText(initial_data[1]);
            varsta.setValue(Integer.valueOf(initial_data[2]));
            telefon.setText(initial_data[3]);
        }

        principalPanel.add(numePanel);
        principalPanel.add(prenumePanel);
        principalPanel.add(varstaPanel);
        principalPanel.add(telPanel);

        String[] input = new String[5];

        int result = JOptionPane.showConfirmDialog(parentComponent, principalPanel, option, JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            input[0] = nume.getText();
            input[1] = prenume.getText();
            input[2] = String.valueOf(varsta.getValue());
            input[3] = telefon.getText();

        } else input = null;

        return input;
    }}
