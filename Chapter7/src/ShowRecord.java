/* 
*Student Name   : Soo Cia Yang
*Student ID     : 17WMD05652
*Tutotial Group : GP1
*Practical 7 Question 1
*--------------------------------------
*/

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class ShowRecord extends JFrame {

    private String host = "jdbc:derby://localhost:1527/collegedb";
    private String user = "nbuser";
    private String password = "nbuser";
    private String tableName = "Programme";
    private Connection conn;
    private PreparedStatement stmt;
    private String sqlStr;
    private ResultSet rs;
    private JTextField jtfCode = new JTextField();
    private JTextField jtfName = new JTextField();
    private JTextField jtfFaculty = new JTextField();
    private JButton jbtFirst = new JButton("First");
    private JButton jbtPrevious = new JButton("Previous");
    private JButton jbtNext = new JButton("Next");
    private JButton jbtLast = new JButton("Last");

    public ShowRecord() {
        JPanel jpCenter = new JPanel(new GridLayout(3, 2));
        jpCenter.add(new JLabel("Programme Code"));
        jpCenter.add(jtfCode);
        jpCenter.add(new JLabel("Programme Name"));
        jpCenter.add(jtfName);
        jpCenter.add(new JLabel("Faculty"));
        jpCenter.add(jtfFaculty);
        add(jpCenter);

        JPanel jpSouth = new JPanel();
        jpSouth.add(jbtFirst);
        jpSouth.add(jbtPrevious);
        jpSouth.add(jbtNext);
        jpSouth.add(jbtLast);
        add(jpSouth, BorderLayout.SOUTH);

        ButtonListener listener = new ButtonListener();
        jbtFirst.addActionListener(listener);
        jbtLast.addActionListener(listener);
        jbtNext.addActionListener(listener);
        jbtPrevious.addActionListener(listener);

        createConnection();
       
    }

    public void displayRecord() {
        try {
            jtfCode.setText(rs.getString("Code"));
            jtfName.setText(rs.getString("Name"));
            jtfFaculty.setText(rs.getString("Faculty"));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getSource() == jbtFirst) {
                    rs.first();
                } else if (e.getSource() == jbtLast) {
                    rs.last();
                } else if (e.getSource() == jbtNext) {
                    rs.next();
                } else if (e.getSource() == jbtPrevious) {
                    rs.previous();
                }
                displayRecord();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void createConnection() {
        try {
            conn = DriverManager.getConnection(host, user, password);
            sqlStr = "SELECT * FROM " + tableName;
            stmt = conn.prepareStatement(sqlStr, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void shutDown() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        ShowRecord frm = new ShowRecord();
        frm.setTitle("Show Record");
        frm.setSize(600, 200);
        frm.setLocationRelativeTo(null);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
    }
}
