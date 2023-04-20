import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.IOException;

public class MainScreen {
    JPanel panel = new JPanel();
    JFrame frame = new JFrame("Магазин");
    Shop shop = new Shop();
    setUp setUp = new setUp();
    JLabel title;
    JButton surprisedButton;

    public static void main(String[] args) {
        new MainScreen();
    }

    public MainScreen() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 800));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        frame.setLocationRelativeTo(null);
        setUp.database(shop);
        ButtonPanel menu = new ButtonPanel(frame, shop);
        frame.add(menu, BorderLayout.NORTH);
        try {
            panel = new JPanelWithBackground("back.jpg");
            frame.add(panel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setMainScreen();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public MainScreen(Shop shop1) {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 800));

        frame.setLocationRelativeTo(null);

        ButtonPanel menu = new ButtonPanel(frame, shop1);
        frame.add(menu, BorderLayout.NORTH);
        try {
            panel = new JPanelWithBackground("back.jpg");
            frame.add(panel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setMainScreen();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    private void setMainScreen() {
        title = new JLabel();
        title.setText("Вітаємо в нашому магазині!!!");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 54));
        title.setForeground(Color.BLACK);
        title.setForeground(Color.WHITE);
        panel.add(title);
        JLabel picture1 = new JLabel();
        picture1.setIcon(new ImageIcon("600.png"));
        surprisedButton = new JButton("Цікава кнопка");
        surprisedButton.setPreferredSize(new Dimension(180, 30));
        surprisedButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        surprisedButton.setBackground(new Color(106, 90, 205));
        surprisedButton.setForeground(Color.WHITE);
        surprisedButton.setFocusPainted(false);
        panel.add(picture1);
        frame.add(surprisedButton, BorderLayout.SOUTH);
        surprisedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog(frame, "Віконечко добра");
                dialog.setSize(new Dimension(300, 375));
                dialog.setLocationRelativeTo(null);
                JPanel dialogPanel = null;
                try {
                    dialogPanel = new JPanelWithBackground("back.jpg");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                JLabel text = new JLabel("Поставите ");
                text.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                text.setBackground(new Color(106, 90, 205));
                JLabel text1 = new JLabel("гарний бал");
                text1.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                text1.setBackground(new Color(106, 90, 205));
                JLabel text2= new JLabel("за лабу");
                text2.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                text2.setBackground(new Color(106, 90, 205));
                JLabel picture = new JLabel();
                picture.setIcon(new ImageIcon("smile.jpg"));
                JLabel text3= new JLabel("будь ласочка");
                text3.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                text3.setBackground(new Color(106, 90, 205));
                dialogPanel.add(text);
                dialogPanel.add(text1);
                dialogPanel.add(text2);
                dialogPanel.add(picture);
                dialogPanel.add(text3);
                dialog.add(dialogPanel);
                dialog.setVisible(true);
            }
        });
    }


}