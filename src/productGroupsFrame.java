import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class productGroupsFrame extends JFrame {
    JFrame frame = new JFrame("Групи товарів"); JPanel panel = new JPanel();
    JButton add = new JButton("Додати групу"), edit = new JButton("Редагувати групу"), delete = new JButton("Видалити групу");
    JTable groupTable;
    Files file = new Files();
    DefaultTableModel groupTableModel = new DefaultTableModel();
    Shop shop;

    File groups = new File("groups.txt");

    productGroupsFrame(Shop s){
        shop=s;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 800));
        try {
            panel=new JPanelWithBackground("C:\\Users\\Igor\\Downloads\\back.jpg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setLocationRelativeTo(null);

        ButtonPanel menu = new ButtonPanel(frame,shop);
        frame.add(menu, BorderLayout.NORTH);

        //panel.setBackground(new Color(125,155,125));
        setupGroups();
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    void setupGroups() {
        JPanel searchPanel = new JPanel();
        JTextField searchField = new JTextField(20);

        JButton searchButton = new JButton("Пошук");
        searchButton.setBackground(new Color(126,110,225));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setFont(new Font("Tahoma", Font.BOLD, 14));
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

        groupTableModel = new DefaultTableModel();
        groupTableModel.addColumn("№");
        groupTableModel.addColumn("Назва групи");
        groupTableModel.addColumn("Опис групи");
        groupTableModel.addColumn("Загальна вартість товарів групи");
        groupTable = new JTable(groupTableModel);
        //groupTable.setBackground(new Color(0,0,0,0));

        groupTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        groupTable.getTableHeader().setBackground(new Color(106,90,205));
        groupTable.getTableHeader().setForeground(new Color(255,255,255));
        groupTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(groupTable);
        scrollPane.setPreferredSize(new Dimension(800, 600));
        panel.add(scrollPane);
        setUpGroups();

        JPanel buttonPanel= new JPanel();
      //  buttonPanel.setBackground(new Color(65,105,225));
//        try {
//            buttonPanel=new JPanelWithBackground("C:\\Users\\Igor\\Downloads\\back.jpg");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
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
                    boolean isUnique = shop.isUniqueGroup(groupName);
                    if(isUnique==true){
                        shop.addGroup(new Group(groupName,groupDescription));
                        DefaultTableModel tableModel = (DefaultTableModel) groupTable.getModel();
                        tableModel.addRow(new Object[]{tableModel.getRowCount() + 1, groupName, groupDescription});
                        file.createTxtGroups(groupName,groups);
                        JOptionPane.showMessageDialog(frame, "Групу товарів було успішно додано.", "Інформація", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(frame, "Групу товарів не було додано, бо її назва не унікальна", "Помилка", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        add.setBackground(new Color(106,90,205));
        add.setForeground(Color.WHITE);
        add.setFocusPainted(false);
        add.setFont(new Font("Tahoma", Font.BOLD, 14));
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

                int result = JOptionPane.showConfirmDialog(frame, editPanel, "Редагування товару", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String editedGroupName = groupNameField.getText();
                    String editedGroupDescription = groupDescriptionArea.getText();
                    boolean isUnique = shop.isUniqueGroup(editedGroupName);
                    if(isUnique==true||editedGroupName.equalsIgnoreCase(groupName)) {
                    for (Group group : shop.getGroups()) {
                        if (group.getName().equals(groupName) && group.getDescription().equals(groupDescription)) {
                                group.setName(editedGroupName);
                                group.setDescription(editedGroupDescription);
                                file.editTxtGroups(editedGroupName, groupName, groups);
                                break;
                        }
                    }
                    groupTable.setValueAt(editedGroupName, selectedRow, 1);
                    groupTable.setValueAt(editedGroupDescription, selectedRow, 2);
                    JOptionPane.showMessageDialog(frame, "Товар було успішно відредаговано.", "Інформація", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(frame, "Групу товарів не було відредаговано, бо її назва не унікальна", "Помилка", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        edit.setBackground(new Color(106,90,205));
        edit.setForeground(Color.WHITE);
        edit.setFocusPainted(false);
        edit.setFont(new Font("Tahoma", Font.BOLD, 14));

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
                    String groupName = (String) groupTable.getValueAt(selectedRow, 1);
                    String groupDescription = (String) groupTable.getValueAt(selectedRow, 2);
                    Group toDelete = null;
                    for (Group group : shop.getGroups()) {
                        if (group.getName().equals(groupName) && group.getDescription().equals(groupDescription)) {
                            toDelete = group;
                            break;
                        }
                    }
                    if (toDelete != null) {
                        shop.wasDeletedGroup(toDelete.getName());
                        shop.deleteGroup(toDelete);
                        file.clearTxtFile(groups);
                        for (Group group : shop.getGroups()) {
                            file.createTxtGroups(group.getName(), groups);
                        }
                        DefaultTableModel tableModel = (DefaultTableModel) groupTable.getModel();
                        tableModel.removeRow(selectedRow);
                    }
                }
            }
        });
        delete.setBackground(new Color(185, 94, 80));
        delete.setForeground(Color.WHITE);
        delete.setFocusPainted(false);
        delete.setFont(new Font("Tahoma", Font.BOLD, 14));

        buttonPanel.add(delete);

        panel.add(buttonPanel);
    }
    private void setUpGroups(){
        file.clearTxtFile(groups);
        int count=1;
        for (Group group : shop.getGroups()) {
            file.createTxtGroups(group.getName(),groups);
            groupTableModel.addRow(new Object[]{count, group.getName(), group.getDescription(), shop.totalCost(group)});
            count++;
        }
    }
}
