import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class productsFrame extends JFrame {
    JFrame frame = new JFrame("Товари по групам");
    JPanel panel = new JPanel();
    JButton add = new JButton("Додати товар"), edit = new JButton("Редагувати товар"),
            delete = new JButton("Видалити товар"), delivered = new JButton("Привезли товар"), sold = new JButton("Продали товар");
    JTable productTable;
    Files file = new Files();
    DefaultTableModel productTableModel = new DefaultTableModel();
    Shop shop;
    setUp setUp = new setUp();
    JComboBox groupSelector = new JComboBox();
    String groupName;

    productsFrame(Shop s) {
        shop=s;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 800));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setLocationRelativeTo(null);

        ButtonPanel menu = new ButtonPanel(frame,shop);
        frame.add(menu, BorderLayout.NORTH);
        try {
            panel=new JPanelWithBackground("C:\\Users\\Igor\\Downloads\\back.jpg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //panel.setBackground(new Color(125, 155, 125));
        setupGroups();
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    void setupGroups() {
        JPanel searchPanel = new JPanel();
        groupSelector.setBounds(50, 50, 90, 20);
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

        JScrollPane scrollPane = new JScrollPane(productTable);
        if (productTable.getRowHeight(1) < getPreferredSize().height) {
            productTable.setRowHeight(1, getPreferredSize().height);
        }
        scrollPane.setPreferredSize(new Dimension(800, 600));
        panel.add(scrollPane);
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
                    double productAmount = Integer.parseInt(productAmountField.getText());
                    double productPrice = Double.parseDouble(productPriceField.getText());
                    double productPriceForAll = productPrice * productAmount;
                    boolean isUnique = shop.isUniqueProduct(productName);
                    if(isUnique==true){
                        boolean added = shop.getOneGroup(groupName).addProduct(new Product(productName, productDescription, productManufacture, productAmount, productPrice));
                        if(added==true) {
                            DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
                            tableModel.addRow(new Object[]{tableModel.getRowCount() + 1, productName, productDescription, productManufacture,
                                    productAmount, productPrice, productPriceForAll});
                            file.createTxtProducts(productName, new File(groupName + ".txt"));
                            JOptionPane.showMessageDialog(frame, "Товар було успішно додано.", "Інформація", JOptionPane.INFORMATION_MESSAGE);
                        }else {

                            JOptionPane.showMessageDialog(frame, "Товар не було додано, бо він уже наявний", "Помилка", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }else{
                        JOptionPane.showMessageDialog(frame, "Товар не було додано, бо його назва не унікальна", "Помилка", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        add.setBackground(new Color(125, 104, 80));
        add.setForeground(Color.WHITE);
        add.setFocusPainted(false);
        add.setFont(new Font("Tahoma", Font.BOLD, 14));
        buttonPanel.add(add);

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Ви не обрали жодного товару.", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String productName = (String) productTable.getValueAt(selectedRow, 1);
                String productDescription = (String) productTable.getValueAt(selectedRow, 2);
                String productManufacture = (String) productTable.getValueAt(selectedRow, 3);
                double productAmount = (double) productTable.getValueAt(selectedRow, 4);
                double productPrice = (double) productTable.getValueAt(selectedRow, 5);

                JTextField productNameField = new JTextField();
                JTextArea productDescriptionArea = new JTextArea();
                productDescriptionArea.setText(productDescription);
                JTextArea productManufactureField = new JTextArea();
                JTextArea productAmountField = new JTextArea();
                JTextArea productPriceField = new JTextArea();
                JScrollPane scrollPane = new JScrollPane(productDescriptionArea);
                scrollPane.setPreferredSize(new Dimension(250, 50));

                JPanel editPanel = new JPanel();
                editPanel.setLayout(new GridLayout(0, 2, 5, 5));
                editPanel.add(new JLabel("Назва товару:"));
                productNameField.setText(productName);
                editPanel.add(productNameField);
                editPanel.add(new JLabel("Опис товару:"));
                editPanel.add(scrollPane);
                editPanel.add(new JLabel("Виробник:"));
                productManufactureField.setText(productManufacture);
                editPanel.add(productManufactureField);
                editPanel.add(new JLabel("Кількість:"));
                productAmountField.setText(String.valueOf(productAmount));
                editPanel.add(productAmountField);
                editPanel.add(new JLabel("Ціна за одиницю:"));
                productPriceField.setText(String.valueOf(productPrice));
                editPanel.add(productPriceField);

                int result = JOptionPane.showConfirmDialog(frame, editPanel, "Редагування товару", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String editedProductName = productNameField.getText();
                    String editedProductDescription = productDescriptionArea.getText();
                    String editedProductManufacture = productManufactureField.getText();
                    double editedProductAmount = Double.parseDouble(productAmountField.getText());
                    double editedProductPrice = Double.parseDouble(productPriceField.getText());
                    double editedProductPriceForAll = editedProductPrice * editedProductAmount;

                    boolean isUnique = shop.isUniqueProduct(editedProductName);
                    if(isUnique==true||editedProductName.equalsIgnoreCase(productName)){
                    if (!groupName.equals("Всі товари")) {
                        for (Group group : shop.getGroups()) {
                            if (group.getName().equals(groupName)) {
                                for (Product product : group.getProducts()) {
                                    if (product.getName().equals(productName) && product.getDescription().equals(productDescription) &&
                                            product.getManufacturer().equals(productManufacture) &&
                                            product.getAmount() == productAmount && product.getPrice() == productPrice) {
                                            product.setName(editedProductName);
                                            product.setDescription(editedProductDescription);
                                            product.setManufacturer(editedProductManufacture);
                                            product.setAmount(editedProductAmount);
                                            product.setPrice(editedProductPrice);
                                            file.editTxtProducts(editedProductName, productName, new File(groupName + ".txt"));
                                            break;
                                    }
                                }
                            }
                        }
                    } else {
                        for (Group group : shop.getGroups()) {
                            for (Product product : group.getProducts()) {
                                if (product.getName().equals(productName) && product.getDescription().equals(productDescription) &&
                                        product.getManufacturer().equals(productManufacture) &&
                                        product.getAmount() == productAmount && product.getPrice() == productPrice) {
                                    product.setName(editedProductName);
                                    product.setDescription(editedProductDescription);
                                    product.setManufacturer(editedProductManufacture);
                                    product.setAmount(editedProductAmount);
                                    product.setPrice(editedProductPrice);
                                    file.editTxtProducts(editedProductName, productName, new File(group.getName() + ".txt"));
                                    break;
                                }
                            }
                        }
                    }
                    productTable.setValueAt(editedProductName, selectedRow, 1);
                    productTable.setValueAt(editedProductDescription, selectedRow, 2);
                    productTable.setValueAt(editedProductManufacture, selectedRow, 3);
                    productTable.setValueAt(editedProductAmount, selectedRow, 4);
                    productTable.setValueAt(editedProductPrice, selectedRow, 5);
                    productTable.setValueAt(editedProductPriceForAll, selectedRow, 6);

                    JOptionPane.showMessageDialog(frame, "Групу товарів було успішно відредаговано.", "Інформація", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(frame, "Товар не було відредаговано, бо його назва не унікальна", "Помилка", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        edit.setBackground(new Color(125, 104, 80));
        edit.setForeground(Color.WHITE);
        edit.setFocusPainted(false);
        edit.setFont(new Font("Tahoma", Font.BOLD, 14));
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
                    String productName = (String) productTable.getValueAt(selectedRow, 1);
                    String productDescription = (String) productTable.getValueAt(selectedRow, 2);
                    String productManufacture = (String) productTable.getValueAt(selectedRow, 3);
                    double productAmount = (double) productTable.getValueAt(selectedRow, 4);
                    double productPrice = (double) productTable.getValueAt(selectedRow, 5);

                    Product toDelete = null;
                    Group deleteFromWhere = null;
                    if (!groupName.equals("Всі товари")) {
                        for (Group group : shop.getGroups()) {
                            if (group.getName().equals(groupName)) {
                                for (Product product : group.getProducts()) {
                                    if (product.getName().equals(productName) && product.getDescription().equals(productDescription) &&
                                            product.getManufacturer().equals(productManufacture) &&
                                            product.getAmount() == productAmount && product.getPrice() == productPrice) {
                                        toDelete = product;
                                        deleteFromWhere = group;
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        for (Group group : shop.getGroups()) {
                            for (Product product : group.getProducts()) {
                                if (product.getName().equals(productName) && product.getDescription().equals(productDescription) &&
                                        product.getManufacturer().equals(productManufacture) &&
                                        product.getAmount() == productAmount && product.getPrice() == productPrice) {
                                    toDelete = product;
                                    deleteFromWhere = group;
                                    break;
                                }
                            }
                        }
                    }
                    if (toDelete != null) {
                        deleteFromWhere.deleteProduct(toDelete);
                        file.clearTxtFile(new File(deleteFromWhere.getName() + ".txt"));
                        for (Product product : deleteFromWhere.getProducts()) {
                            file.createTxtProducts(product.getName(), new File(deleteFromWhere.getName() + ".txt"));
                        }
                        DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
                        tableModel.removeRow(selectedRow);
                    }
                }
            }
        });
        delete.setBackground(new Color(125, 104, 80));
        delete.setForeground(Color.WHITE);
        delete.setFocusPainted(false);
        delete.setFont(new Font("Tahoma", Font.BOLD, 14));
        buttonPanel.add(delete);


        delivered.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Ви не обрали жодної групи.", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String productName = (String) productTable.getValueAt(selectedRow, 1);
                String productDescription = (String) productTable.getValueAt(selectedRow, 2);
                String productManufacture = (String) productTable.getValueAt(selectedRow, 3);
                double productAmount = (double) productTable.getValueAt(selectedRow, 4);
                double productPrice = (double) productTable.getValueAt(selectedRow, 5);

                JTextArea productAmountField = new JTextArea();

                JPanel deliveredPanel = new JPanel();
                deliveredPanel.setLayout(new GridLayout(1, 2, 5, 5));
                deliveredPanel.add(new JLabel("Скільки привезли:"));
                productAmountField.setText(String.valueOf(productAmount));
                deliveredPanel.add(productAmountField);

                int result = JOptionPane.showConfirmDialog(frame, deliveredPanel, "Привезли товару", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    double editedProductAmount = Double.parseDouble(productAmountField.getText());
                    double editedProductPriceForAll = productPrice *( editedProductAmount+productAmount);
                    if (editedProductAmount<1){
                        JOptionPane.showMessageDialog(frame, "Товару не може бути привезено менше 1", "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                        for (Group group : shop.getGroups()) {
                            if (group.getName().equals(groupName)) {
                                for (Product product : group.getProducts()) {
                                    if (product.getName().equals(productName) && product.getDescription().equals(productDescription) &&
                                            product.getManufacturer().equals(productManufacture) &&
                                            product.getAmount() == productAmount && product.getPrice() == productPrice) {
                                        product.setAmount( productAmount+editedProductAmount);
                                        file.editTxtProducts(productName, productName, new File(groupName + ".txt"));
                                        break;
                                    }
                                }
                            }
                        }

                    productTable.setValueAt(productAmount+editedProductAmount, selectedRow, 4);
                    productTable.setValueAt(editedProductPriceForAll, selectedRow, 6);

                    JOptionPane.showMessageDialog(frame, "Товари було успішно привезено на склад.", "Інформація", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        delivered.setBackground(new Color(125, 104, 80));
        delivered.setForeground(Color.WHITE);
        delivered.setFocusPainted(false);
        delivered.setFont(new Font("Tahoma", Font.BOLD, 14));
        buttonPanel.add(delivered);


        sold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Ви не обрали жодної групи.", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String productName = (String) productTable.getValueAt(selectedRow, 1);
                String productDescription = (String) productTable.getValueAt(selectedRow, 2);
                String productManufacture = (String) productTable.getValueAt(selectedRow, 3);
                double productAmount = (double) productTable.getValueAt(selectedRow, 4);
                double productPrice = (double) productTable.getValueAt(selectedRow, 5);

                JTextArea productAmountField = new JTextArea();

                JPanel deliveredPanel = new JPanel();
                deliveredPanel.setLayout(new GridLayout(1, 2, 5, 5));
                deliveredPanel.add(new JLabel("Скільки привезли:"));
                productAmountField.setText(String.valueOf(productAmount));
                deliveredPanel.add(productAmountField);

                int result = JOptionPane.showConfirmDialog(frame, deliveredPanel, "Привезли товару", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    double editedProductAmount = Double.parseDouble(productAmountField.getText());
                    double editedProductPriceForAll = productPrice *( productAmount-editedProductAmount);
                    if (editedProductAmount>productAmount){
                        JOptionPane.showMessageDialog(frame, "Товару не може бути продано більше, ніж є на складі", "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (editedProductAmount<1){
                        JOptionPane.showMessageDialog(frame, "Товару не може бути продано менше 1", "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    for (Group group : shop.getGroups()) {
                        if (group.getName().equals(groupName)) {
                            for (Product product : group.getProducts()) {
                                if (product.getName().equals(productName) && product.getDescription().equals(productDescription) &&
                                        product.getManufacturer().equals(productManufacture) &&
                                        product.getAmount() == productAmount && product.getPrice() == productPrice) {
                                    product.setAmount( productAmount-editedProductAmount);
                                    file.editTxtProducts(productName, productName, new File(groupName + ".txt"));
                                    break;
                                }
                            }
                        }
                    }

                    productTable.setValueAt(productAmount-editedProductAmount, selectedRow, 4);
                    productTable.setValueAt(editedProductPriceForAll, selectedRow, 6);

                    JOptionPane.showMessageDialog(frame, "Товари було успішно продано.", "Інформація", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        sold.setBackground(new Color(125, 104, 80));
        sold.setForeground(Color.WHITE);
        sold.setFocusPainted(false);
        sold.setFont(new Font("Tahoma", Font.BOLD, 14));
        buttonPanel.add(sold);
        panel.add(buttonPanel);

    }

    private void addRowsToTable(String groupName) {
        productTableModel.setRowCount(0);
        if (!groupName.equals("Всі товари")) {
            file.clearTxtFile(new File(groupName + ".txt"));
            int count = 1;
            for (Group group : shop.getGroups()) {
                if (group.getName().equals(groupName)) {
                    for (Product product : group.getProducts()) {
                        file.createTxtProducts(product.getName(), new File(group.getName() + ".txt"));
                        productTableModel.addRow(new Object[]{count, product.getName(), product.getDescription(), product.getManufacturer(),
                                product.getAmount(), product.getPrice(), product.priceForAll(product)});
                        count++;
                    }
                }
            }
        } else {
            int count = 1;
            for (Group group : shop.getGroups()) {
                for (Product product : group.getProducts()) {
                    file.createTxtProducts(product.getName(), new File(group.getName() + ".txt"));
                    productTableModel.addRow(new Object[]{count, product.getName(), product.getDescription(), product.getManufacturer(),
                            product.getAmount(), product.getPrice(), product.priceForAll(product)});
                    count++;
                }
            }
        }
    }
}
