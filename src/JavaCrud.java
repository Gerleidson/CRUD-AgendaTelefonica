import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.*;

public class JavaCrud {
    private JPanel Main;
    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtTelefone;
    private JButton salvarButton;
    private JTable table1;
    private JButton updateButton;
    private JButton apagarButton;
    private JButton pesquisarButton;
    private JTextField txtid;
    private JScrollPane table_1;


    public static void main(String[] args) {
        JFrame frame = new JFrame("JavaCrud");
        frame.setContentPane(new JavaCrud().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public void conexao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/rbcompany", "root","root");
            System.out.println("Conexão Ok");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void table_load(){
        try
        {
            pst = con.prepareStatement("select * from javacrud");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public JavaCrud() {
        conexao();
        table_load();
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String Nome, CPF, Telefone;

                Nome = txtNome.getText();
                CPF = txtCpf.getText();
                Telefone = txtTelefone.getText();

                try {
                    pst = con.prepareStatement("insert into javacrud(Nome,CPF,Telefone)values(?,?,?)");
                    pst.setString(1, Nome);
                    pst.setString(2, CPF);
                    pst.setString(3, Telefone);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
                    table_load();
                    txtNome.setText("");
                    txtCpf.setText("");
                    txtTelefone.setText("");
                    txtNome.requestFocus();
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        pesquisarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String id = txtid.getText();

                    pst = con.prepareStatement("select Nome,CPF,Telefone from javacrud where id = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next() == true) {
                        String Nome = rs.getString(1);
                        String CPF = rs.getString(2);
                        String Telefone = rs.getString(3);

                        txtNome.setText(Nome);
                        txtCpf.setText(CPF);
                        txtTelefone.setText(Telefone);

                    } else {
                        txtNome.setText("");
                        txtCpf.setText("");
                        txtTelefone.setText("");
                        JOptionPane.showMessageDialog(null, "Id nao encontrado!");
                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String Nome, CPF, Telefone, id;

                Nome = txtNome.getText();
                CPF = txtCpf.getText();
                Telefone = txtTelefone.getText();
                id = txtid.getText();

                try {
                    pst = con.prepareStatement("update javacrud set Nome = ?,CPF = ?,Telefone = ? where id = ?");
                    pst.setString(1, Nome);
                    pst.setString(2, CPF);
                    pst.setString(3, Telefone);
                    pst.setString(4, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Atualização realizada com sucesso!");
                    table_load();
                    txtNome.setText("");
                    txtCpf.setText("");
                    txtTelefone.setText("");
                    txtNome.requestFocus();
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        apagarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String empid;
                empid = txtid.getText();

                try {
                    pst = con.prepareStatement("delete from javacrud  where id = ?");

                    pst.setString(1, empid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Apagado!");
                    table_load();
                    txtNome.setText("");
                    txtCpf.setText("");
                    txtTelefone.setText("");
                    txtNome.requestFocus();
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
    }
}