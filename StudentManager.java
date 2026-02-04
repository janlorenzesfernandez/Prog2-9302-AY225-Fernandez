import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentManager extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtGrade;

    public StudentManager() {
        setTitle("Student Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table Model
        model = new DefaultTableModel(new String[]{"ID", "Name", "Grade"}, 0);
        table = new JTable(model);

        // Load CSV on startup
        loadCSV();

        // Input fields
        txtId = new JTextField(5);
        txtName = new JTextField(10);
        txtGrade = new JTextField(5);

        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");

        // Add button action
        btnAdd.addActionListener(e -> {
            String id = txtId.getText();
            String name = txtName.getText();
            String grade = txtGrade.getText();

            model.addRow(new Object[]{id, name, grade});

            txtId.setText("");
            txtName.setText("");
            txtGrade.setText("");
        });

        // Delete button action
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                model.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            }
        });

        // Layout
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(txtId);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(txtName);
        inputPanel.add(new JLabel("Grade:"));
        inputPanel.add(txtGrade);
        inputPanel.add(btnAdd);
        inputPanel.add(btnDelete);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }

    private void loadCSV() {
        String filePath = "MOCK_DATA.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error reading file: " + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManager().setVisible(true));
    }
}
