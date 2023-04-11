import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class productsFrame extends JFrame {
    JFrame frame = new JFrame("Товари по групам"); JPanel panel = new JPanel();
    JButton add = new JButton("Додати товар"), edit = new JButton("Редагувати товар"), delete = new JButton("Видалити товар");
    JTable productTable;
    Files file = new Files();
    DefaultTableModel productTableModel = new DefaultTableModel();
    Shop shop = new Shop(); setUp setUp = new setUp();
    JComboBox groupSelector=new JComboBox(); String groupName;

    public static void main(String[] args) {
        new productsFrame();
    }
    productsFrame(){
        setUp.database(shop);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 800));
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

    void setupGroups() {
        JPanel searchPanel = new JPanel();
        groupSelector.setBounds(50, 50,90,20);
        groupSelector.addItem("Всі товари");
        for (Group group : shop.getGroups()) {
            groupSelector.addItem(group.getName());
        }
        searchPanel.add(groupSelector);
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Пошук");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText().trim();
                if (searchText.isEmpty()) {
                    productTable.setRowSorter(null);
                } else {
                    TableRowSorter<TableModel> sorter = new TableRowSorter<>(productTable.getModel());
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
                    productTable.setRowSorter(sorter);
                }
            }
        });
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        panel.add(searchPanel);

        productTableModel = new DefaultTableModel();
        productTableModel.addColumn("№");
        productTableModel.addColumn("Назва товару");
        productTableModel.addColumn("Опис товару");
        productTableModel.addColumn("Виробник");
        productTableModel.addColumn("Кількість на складі");
        productTableModel.addColumn("Ціна");
        productTableModel.addColumn("Вартість");
        productTable = new JTable(productTableModel);


        productTable.setPreferredSize(new Dimension(800, 600));
        panel.add(productTable);
        groupSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
                groupName = (String) comboBox.getSelectedItem();
                addRowsToTable(groupName);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField productNameField = new JTextField();
                JTextArea productDescriptionArea = new JTextArea();
                JTextArea productManufactureField = new JTextArea();
                JTextArea productAmountField = new JTextArea();
                JTextArea productPriceField = new JTextArea();
                JScrollPane scrollPane = new JScrollPane(productDescriptionArea);
                scrollPane.setPreferredSize(new Dimension(250, 50));

                JPanel addPanel = new JPanel();
                addPanel.setLayout(new GridLayout(0, 2, 5, 5));
                addPanel.add(new JLabel("Назва товару:"));
                addPanel.add(productNameField);
                addPanel.add(new JLabel("Опис товару:"));
                addPanel.add(scrollPane);
                addPanel.add(new JLabel("Виробник:"));
                addPanel.add(productManufactureField);
                addPanel.add(new JLabel("Кількість:"));
                addPanel.add(productAmountField);
                addPanel.add(new JLabel("Ціна за одиницю:"));
                addPanel.add(productPriceField);

                int result = JOptionPane.showConfirmDialog(frame, addPanel, "Додавання товарів", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String productName = productNameField.getText();
                    String productDescription = productDescriptionArea.getText();
                    String productManufacture = productManufactureField.getText();
                    int productAmount = Integer.parseInt(productAmountField.getText());
                    double productPrice = Double.parseDouble(productPriceField.getText());
                    double productPriceForAll = productPrice*productAmount;
                    shop.getOneGroup(groupName).addProduct(new Product(productName,productDescription,productManufacture,productAmount,productPrice));
                    DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
                    tableModel.addRow(new Object[]{tableModel.getRowCount() + 1, productName, productDescription,productManufacture,
                            productAmount,productPrice,productPriceForAll});
                    file.createTxtProducts(productName, new File(groupName + ".txt"));
                    JOptionPane.showMessageDialog(frame, "Товар було успішно додано.", "Інформація", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        buttonPanel.add(add);

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Ви не обрали жодної групи.", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String groupName = (String) productTable.getValueAt(selectedRow, 1);
                String groupDescription = (String) productTable.getValueAt(selectedRow, 2);

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

                    for (Group group : shop.getGroups()) {
                        if (group.getName().equals(groupName) && group.getDescription().equals(groupDescription)) {
                            group.setName(editedGroupName); group.setDescription(editedGroupDescription);
                            //file.editTxtGroups(editedGroupName,groupName);
                            break;
                        }
                    }
                    productTable.setValueAt(editedGroupName, selectedRow, 1);
                    productTable.setValueAt(editedGroupDescription, selectedRow, 2);
                    JOptionPane.showMessageDialog(frame, "Групу товарів було успішно відредаговано.", "Інформація", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        buttonPanel.add(edit);

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Ви не обрали жодної групи.", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (selectedRow >= 0) {
                    String groupName = (String) productTable.getValueAt(selectedRow, 1);
                    String groupDescription = (String) productTable.getValueAt(selectedRow, 2);
                    Group toDelete = null;
                    for (Group group : shop.getGroups()) {
                        if (group.getName().equals(groupName) && group.getDescription().equals(groupDescription)) {
                            toDelete = group;
                            break;
                        }
                    }
                    if (toDelete != null) {
                        shop.deleteGroup(toDelete);
                        DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
                        tableModel.removeRow(selectedRow);
                    }
                }
            }
        });
        buttonPanel.add(delete);

        panel.add(buttonPanel);
    }
    private void addRowsToTable(String groupName){
        productTableModel.setRowCount(0);
        if(!groupName.equals("Всі товари")){
            file.clearTxtFile(new File(groupName+".txt"));
            int count = 1;
            for (Group group : shop.getGroups()) {
                if(group.getName().equals(groupName)){
                    for (Product product : group.getProducts()) {
                        file.createTxtProducts(product.getName(),new File(group.getName()+".txt"));
                        productTableModel.addRow(new Object[]{count, product.getName(), product.getDescription(), product.getManufacturer(),
                                product.getAmount(), product.getPrice(), product.priceForAll(product)});
                        count++;
                    }
                }
            }
        }else{
            int count = 1;
            for (Group group : shop.getGroups()) {
                    for (Product product : group.getProducts()) {
                        productTableModel.addRow(new Object[]{count, product.getName(), product.getDescription(), product.getManufacturer(),
                                product.getAmount(), product.getPrice(), product.priceForAll(product)});
                        count++;
                    }
            }
        }
    }
}
