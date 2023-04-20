import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainScreen {
    JPanel panel = new JPanel();
    JFrame frame = new JFrame("Магазин");
    Shop shop = new Shop();
    setUp setUp = new setUp();
    JLabel title;

    public static void main(String[] args) {
        new MainScreen();
    }

    public MainScreen()  {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 800));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        frame.setLocationRelativeTo(null);
        setUp.database(shop);
        ButtonPanel menu = new ButtonPanel(frame, shop);
        frame.add(menu, BorderLayout.NORTH);
        try {
            panel=new JPanelWithBackground("back.jpg");
            panel.setLayout(new BorderLayout());
            frame.add(panel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setMainScreen();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
    public MainScreen(Shop shop1)  {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 800));
//        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        frame.setLocationRelativeTo(null);

        ButtonPanel menu = new ButtonPanel(frame, shop1);
        frame.add(menu, BorderLayout.NORTH);
        try {
            panel=new JPanelWithBackground("back.jpg");
            frame.add(panel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setMainScreen();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
    private void setMainScreen(){
        title=new JLabel();
        title.setText("Вітаємо в нашому магазині!!!");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 54));
        title.setForeground(Color.BLACK);
        title.setForeground(Color.WHITE);
        panel.add(title, BorderLayout.NORTH);
        JLabel picture1 = new JLabel();
        picture1.setIcon(new ImageIcon("500.png"));
        picture1.setOpaque(false);
        JPanel jPanel =new JPanel();
        jPanel.add(picture1);
        panel.add(jPanel, BorderLayout.CENTER);
    }

}