# Maven

## Add this to pom.xml

```
 <dependencies>
        <dependency>
            <groupId>org.xerial</groupId>
            <artifactId>sqlite-jdbc</artifactId>
            <version>3.8.7</version>
        </dependency>
    </dependencies>
```

## Create db conection and create tables with if not exist
```java
public class Connect {
    public static Connection connect(){
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
    }

        return conn;
    }
}
```

## Get data from db
### pay attention to connection. if appear errors. remove con from try 

```java
public class DBDataAgenti {
    static DefaultTableModel tableModel = new DefaultTableModel(
            new Object[][]{},
            new Object[]{"CodAgent", "nume", "prenume", "varsta", "telefon"}
    );

    public static void loadDataAgent(JTable tabel) {
        try (Connection con = Connect.connect()){
            Statement st = con.createStatement();
            ResultSet response = st.executeQuery("select * from Agent order by CodAgent");

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

    public static void updateAgent(String nume, String prenume, Integer varsta, String telefon, Integer cod) {
        try(Connection con = Connect.connect()) {
            String sql = "update Agent set nume = ?, prenume =?, varsta=?, telefon=? where CodAgent=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, nume);
            st.setString(2, prenume);
            st.setInt(3, varsta);
            st.setString(4, telefon);
            st.setInt(5, cod);

            st.execute();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet getAgents(){
        try(Connection con = Connect.connect()) {
            String sql = "select * from Agent";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet x = st.executeQuery();
            st.close();
            return x;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

```

## Create frame (start with these without fk, ignore listeners for now)
```java
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
```

## Create AddEdit dialog 

```java
public class AddEditApartamentDialog {
    static JSpinner etaj = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
    static JSpinner nrCamere = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
    static JTextField pret = new JTextField("100", 10);
    static  JSpinner metri = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));

    static JTextField agent = new JTextField("1", 10);

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
        agentPanel.add(agent);


        if (initial_data.length == 5) {
            etaj.setValue(Integer.valueOf(initial_data[0]));
            nrCamere.setValue(Integer.valueOf(initial_data[1]));
            pret.setText(initial_data[2]);
            metri.setValue(Integer.valueOf(initial_data[3]));
            agent.setText(initial_data[3]);
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
            input[4] = agent.getText();

        } else input = null;

        return input;
}}

```

## Add event listeners and menu listeners

## Add this dialog
```java
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
```

## Add interogations 
```java
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
```
