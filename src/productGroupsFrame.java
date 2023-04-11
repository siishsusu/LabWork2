import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class productGroupsFrame extends JFrame {
    JFrame frame = new JFrame("Групи товарів"); JPanel panel = new JPanel();
    JButton add = new JButton("Додати групу"), edit = new JButton("Редагувати групу"), delete = new JButton("Видалити групу");
    JTable groupTable;
    Object[][] groups = {
            {1, "Продовольчі", "Товари, що призначені для споживання"},
            {2, "Непродовольчі", "Товари, що не призначені для споживання"},
            {3, "Помідор", "Товари, що не призначені для споживання"},
            {4, "Помідор", "Товари, що не призначені для споживання"},
            {5, "Помідор", "Товари, що не призначені для споживання"}
    };

    public static void main(String[] args) {
        new productGroupsFrame();
    }
    productGroupsFrame(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 1000));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setLocationRelativeTo(null);

        ButtonPanel menu = new ButtonPanel();
        frame.add(menu, BorderLayout.NORTH);

        panel.setBackground(new Color(125,155,125));
        setupGroups();
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    void createTxtGroups(String newGroupName){
        try {
            FileWriter writer = new FileWriter("groups.txt", true);
            writer.write(newGroupName + "\n");
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    void setupGroups() {
        JPanel searchPanel = new JPanel();
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Пошук");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText().trim();
                if (searchText.isEmpty()) {
                    groupTable.setRowSorter(null);
                } else {
                    TableRowSorter<TableModel> sorter = new TableRowSorter<>(groupTable.getModel());
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
                    groupTable.setRowSorter(sorter);
                }
            }
        });
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        panel.add(searchPanel);

        String[] columnNames = {"№", "Назва групи", "Опис"};
        DefaultTableModel tableModel = new DefaultTableModel(groups, columnNames);

        groupTable = new JTable(tableModel);
        groupTable.setPreferredSize(new Dimension(800, 600));
        panel.add(groupTable);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField groupNameField = new JTextField();
                JTextArea groupDescriptionArea = new JTextArea();
                JScrollPane scrollPane = new JScrollPane(groupDescriptionArea);
                scrollPane.setPreferredSize(new Dimension(250, 80));

                JPanel addPanel = new JPanel();
                addPanel.setLayout(new GridLayout(0, 2, 5, 5));
                addPanel.add(new JLabel("Назва групи:"));
                addPanel.add(groupNameField);
                addPanel.add(new JLabel("Опис групи:"));
                addPanel.add(scrollPane);

                int result = JOptionPane.showConfirmDialog(frame, addPanel, "Додавання групи товарів", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String groupName = groupNameField.getText();
                    String groupDescription = groupDescriptionArea.getText();
                    DefaultTableModel tableModel = (DefaultTableModel) groupTable.getModel();
                    tableModel.addRow(new Object[]{tableModel.getRowCount() + 1, groupName, groupDescription});
                    createTxtGroups(groupName);
                    JOptionPane.showMessageDialog(frame, "Групу товарів було успішно додано.", "Інформація", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        buttonPanel.add(add);

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = groupTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Ви не обрали жодної групи.", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String groupName = (String) groupTable.getValueAt(selectedRow, 1);
                String groupDescription = (String) groupTable.getValueAt(selectedRow, 2);

                JTextField groupNameField = new JTextField(groupName);
                JTextArea groupDescriptionArea = new JTextArea(groupDescription);
                JScrollPane scrollPane = new JScrollPane(groupDescriptionArea);
                scrollPane.setPreferredSize(new Dimension(250, 80));

                JPanel editPanel = new JPanel();
                editPanel.setLayout(new GridLayout(0, 2, 5, 5));
                editPanel.add(new JLabel("Назва групи:"));
                editPanel.add(groupNameField);
                editPanel.add(new JLabel("Опис групи:"));
                editPanel.add(scrollPane);

                int result = JOptionPane.showConfirmDialog(frame, editPanel, "Редагування групи товарів", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String editedGroupName = groupNameField.getText();
                    String editedGroupDescription = groupDescriptionArea.getText();
                    groupTable.setValueAt(editedGroupName, selectedRow, 1);
                    groupTable.setValueAt(editedGroupDescription, selectedRow, 2);
                    JOptionPane.showMessageDialog(frame, "Групу товарів було успішно відредаговано.", "Інформація", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        buttonPanel.add(edit);

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = groupTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Ви не обрали жодної групи.", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (selectedRow >= 0) {
                    DefaultTableModel tableModel = (DefaultTableModel) groupTable.getModel();
                    tableModel.removeRow(selectedRow);
                }
            }
        });
        buttonPanel.add(delete);

        panel.add(buttonPanel);
    }
}
