import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel{
    static int BUTTON_COUNT = 3, BUTTON_WIDTH=150, BUTTON_HEIGHT=30;
    private String[] buttonNames = {"Головна", "Групи товарів", "Товари по групам"};
    ButtonPanel(){
        super();
        buttonsPanel();
    }
    public JPanel buttonsPanel() {
        JPanel menuPanel = new JPanel();
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

            }add(button);
        }
        return menuPanel;
    }
}
