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
            panel=new JPanelWithBackground("C:\\Users\\Igor\\Downloads\\back.jpg");
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
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        frame.setLocationRelativeTo(null);

        ButtonPanel menu = new ButtonPanel(frame, shop1);
        frame.add(menu, BorderLayout.NORTH);
        try {
            panel=new JPanelWithBackground("C:\\Users\\Igor\\Downloads\\back.jpg");
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
        title.setFont(new Font("Tahoma", Font.BOLD, 34));
        title.setForeground(Color.WHITE);
        panel.add(title);


    }

}