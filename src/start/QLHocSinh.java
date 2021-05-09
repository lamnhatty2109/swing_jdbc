package start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import static start.database.connect;
import static start.database.getHS;

class QLHocSinh extends JFrame implements ActionListener {
    JPanel connectionPanel;
    JLabel user_label, password_label, message, host_label, port_label, db_name_label;
    JTextField userName_text;
    JPasswordField password_text;
    JTextField host_text;
    JTextField port_text;
    JTextField db_name_text;

    JButton submit, cancel;

    QLHocSinh() {
        // Host Label
        host_label = new JLabel();
        host_label.setText("Host: ");
        host_text = new JTextField();

        // Host Label
        port_label = new JLabel();
        port_label.setText("Port: ");
        port_text = new JTextField();

        // DBName Label
        db_name_label = new JLabel();
        db_name_label.setText("Database name: ");
        db_name_text = new JTextField();

        // User Label
        user_label = new JLabel();
        user_label.setText("User Name :");
        userName_text = new JTextField();

        // Password
        password_label = new JLabel();
        password_label.setText("Password :");
        password_text = new JPasswordField();

        // Submit
        submit = new JButton("SUBMIT");
        connectionPanel = new JPanel(new GridLayout(6, 1));

        connectionPanel.add(host_label);
        connectionPanel.add(host_text);
        connectionPanel.add(port_label);
        connectionPanel.add(port_text);
        connectionPanel.add(db_name_label);
        connectionPanel.add(db_name_text);
        connectionPanel.add(user_label);
        connectionPanel.add(userName_text);
        connectionPanel.add(password_label);
        connectionPanel.add(password_text);

        message = new JLabel();
        connectionPanel.add(message);
        connectionPanel.add(submit);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        submit.addActionListener(this);
        add(connectionPanel, BorderLayout.CENTER);
        setTitle("Connect sql server");
        setSize(500, 200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            connect(
                    host_text.getText(),
                    port_text.getText(),
                    db_name_text.getText(),
                    userName_text.getText(),
                    password_text.getText()
            );
        } catch (SQLException ex) {
            message.setText("Connect to db failed, please try again");
            message.setForeground(Color.red);
            return;
        }

        setVisible(false);
//        createFrame();
        try {
            new DSHocSinh();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        message.setText("Connection successful");
        message.setForeground(Color.blue);
    }

    public static void createFrame()
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JFrame frame = new JFrame("Danh sach hoc sinh");
                JTable table;
                frame.setVisible(true);
                frame.setSize(1000, 500);

                List<hocsinh> ds = null;
                try {
                    ds = getHS();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                String[][] data = new String[ds.size()][6];

                int id = 0;
                for (hocsinh item : ds) {
                    System.out.println(item.mahs);
                    data[id][0] = item.mahs;
                    data[id][1] = item.tenhs;
                    data[id][2] = Float.toString(item.diem);
                    data[id][3] = item.hinh;
                    data[id][4] = item.diachi;
                    data[id][5] = item.note;
                }
                if (data != null) {
                    String[] columnNames = { "Ma hoc sinh", "Ten hoc sinh", "Diem", "Hinh anh", "Dia chi", "Ghi chu" };
                    table = new JTable(data, columnNames);
                    table.setBounds(30, 40, 200, 300);

                    JScrollPane sp = new JScrollPane(table);
                    frame.add(sp);
                }
                frame.setSize(500, 200);
                frame.setVisible(true);
            }
        });
    }
}
