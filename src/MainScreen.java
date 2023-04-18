import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/*
Автоматизоване робоче місце.
Необхідно автоматизувати роботу невеликого підприємства по роботі з складом.
Існує декілька груп товарів (наприклад: Продовольчі, непродовольчі...).
В кожній групі товарів існують конкретні товари (наприклад: борошно, гречка ...).
У кожного товару є наступні властивості - назва, опис,
виробник, кількість на складі, ціна за одиницю. Група товарів містить наступні властивості - назва, опис.
Реалізувати:

Реалізувати графічний інтерфейс користувача
Збереження даних в файл/файли. Один з варіантів:
 Існує файл в якому знаходяться назви всіх груп товарів. Товари з кожної групи товарів знаходяться в окремому файлі.
Назва товару - унікальна
 (не може зустрічатися більше в жодній групі товарів).
Назва групи товарів - унікальна.
Реалізувати додавання/редагування/видалення групи
 товарів - при видаленні групи товарів, видаляти і всі товари.
Реалізувати додавання/редагування/видалення товару в
групу товарів (мається на увазі назва, опис, виробник, ціна за одиницю).
Реалізувати інтерфейс додавання товару (прийшло на склад
 крупи гречаної - 10 штук), інтерфейс списання товару (продали крупи гречаної - 5 шт.)
Пошук товару.
Вивід статистичних даних: вивід всіх товарів з інформацією
по складу, вивід усіх товарів по групі товарів з інформацією,
загальна вартість товару на складі (кількість * на ціну), загальна вартість товарів в групі товарів.
До роботи додати звіт про виконання роботи з описом розподілу ролей.*/
public class MainScreen {
    JPanel panel = new JPanel();
    JFrame frame = new JFrame("Магазин");
    Shop shop = new Shop();
    setUp setUp = new setUp();

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
       // panel.setBackground(new Color(125,155,125));
        //frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}