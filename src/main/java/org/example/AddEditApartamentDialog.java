package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.DBDataAgenti.getAgents;

public class AddEditApartamentDialog {
    static JSpinner etaj = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
    static JSpinner nrCamere = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
    static JTextField pret = new JTextField(10);
    static  JSpinner metri = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));

    static JComboBox<String> agent = new JComboBox<>();

    public static String[] showAddEditInputDialog(Component parentComponent, String option, String[] initial_data) {
        JPanel principalPanel = new JPanel(new GridLayout(4, 1, 20, 0));

        JPanel etajPanel = new JPanel(new FlowLayout());
        etajPanel.add(new Label("Etaj: "));
        etajPanel.add(etaj);

        JPanel camPanel = new JPanel((new FlowLayout()));
        camPanel.add(new Label("Numar camere: "));
        camPanel.add(nrCamere);

        JPanel pretPanel = new JPanel((new FlowLayout()));
        pretPanel.add(new Label("Pret: "));
        pretPanel.add(pret);

        JPanel metriPanel = new JPanel((new FlowLayout()));
        metriPanel.add(new Label("Metri Patrati: "));
        metriPanel.add(metri);

        JPanel agentPanel = new JPanel(new FlowLayout());
        agentPanel.add(new JLabel("Agent: "));

        ResultSet rs = getAgents();
        try {
            while (rs.next()) {
                agent.addItem(rs.getString(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        agentPanel.add(agent);


        if (initial_data.length == 4) {
            etaj.setValue(Integer.valueOf(initial_data[0]));
            nrCamere.setValue(Integer.valueOf(initial_data[1]));
            pret.setText(initial_data[2]);
            metri.setValue(Integer.valueOf(initial_data[3]));
        }

        principalPanel.add(etajPanel);
        principalPanel.add(camPanel);
        principalPanel.add(pretPanel);
        principalPanel.add(metriPanel);
        principalPanel.add(agentPanel);

        String[] input = new String[5];

        int result = JOptionPane.showConfirmDialog(parentComponent, principalPanel, option, JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            input[0] = String.valueOf(etaj.getValue());
            input[1] = String.valueOf(nrCamere.getValue());
            input[2] = pret.getText();
            input[3] = String.valueOf(metri.getValue());
            input[4] = String.valueOf(agent.getSelectedItem());

        } else input = null;

        return input;
}}
