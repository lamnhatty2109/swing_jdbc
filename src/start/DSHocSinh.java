package start;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

import static start.database.getHS;

class DSHocSinh extends JFrame {
    JFrame frame = new JFrame("Danh sach hoc sinh");
    JTable table;

    DSHocSinh() throws SQLException {
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

}
