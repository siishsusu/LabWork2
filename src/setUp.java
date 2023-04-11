import java.util.ArrayList;

public class setUp {
    void database(Shop shop){
        Group one = new Group("назва групи", "опис групи");
        one.addProduct(new Product("назва1", "опис","виробник", 10, 15));
        one.addProduct(new Product("назва2", "опис","виробник", 10, 15));
        one.addProduct(new Product("назва3", "опис","виробник", 10, 15));
        one.addProduct(new Product("назва4", "опис","виробник", 10, 15));

        Group two = new Group("назва fff", "опис групи");
        two.addProduct(new Product("неназва", "неопис","невиробник", 50, 3));
        two.addProduct(new Product("неназва2", "неопис2","невиробник2", 50, 3));

        shop.addGroup(one);
        shop.addGroup(two);
    }
}
