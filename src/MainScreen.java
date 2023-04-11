import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame{
    static int BUTTON_COUNT = 5, BUTTON_WIDTH=150, BUTTON_HEIGHT=30;
    private String[] buttonNames = {"Головна", "Всі товари", "Групи товарів", "Товари по групам", "Пошук"};
    JPanel panel = new JPanel();
    JFrame frame = new JFrame("Магазин");

    public static void main(String[] args) {
        new MainScreen();
    }

    public MainScreen() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 1000));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setLocationRelativeTo(null);
        buttonsPanel();
//        ButtonPanel menu = new ButtonPanel();
//        menu.setVisible(true);
//        frame.add(menu);

        panel.setBackground(new Color(125,155,125));

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
    public void buttonsPanel() {
        for (int i = 0; i < BUTTON_COUNT; i++) {
            JButton button = new JButton(buttonNames[i]);
            button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
            if (i == 0) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("button1");
                    }
                });
            }else if (i == 1) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("button2");
                    }
                });
            }else if (i == 2) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        productGroupsFrame pr = new productGroupsFrame();
                        System.out.println("button3");
                    }
                });
            } else if(i==3){
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("button4");
                    }
                });

            }else {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("button5");
                    }
                });

            }
            panel.add(button,BorderLayout.SOUTH);
            frame.add(panel);
            frame.pack();
            frame.setVisible(true);
        }
    }
}