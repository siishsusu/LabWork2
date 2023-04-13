import java.util.ArrayList;

public class setUp {
    void database(Shop shop) {
        Group one = new Group("Ручки для армреслінгу", "опис групи");
        one.addProduct(new Product("Ексцентрична ручка", "опис", "IFactory", 10, 700));
        one.addProduct(new Product("Ручка на лямках", "опис", "IFactory", 10, 600));
        one.addProduct(new Product("Конусна ручка", "опис", "IFactory", 10, 700));
        one.addProduct(new Product("Ручка на підшипниках", "опис", "IFactory", 10, 650));

        Group two = new Group("Фітнес гумки", "опис групи");
        two.addProduct(new Product("Набір гумок 20-70 кг", "неопис", "SuperRezyna", 15, 1400));
        two.addProduct(new Product("Фітнес-гумка 20 кг", "неопис2", "SuperGymka", 20, 300));
        two.addProduct(new Product("Фітнес-гумка 30 кг", "неопис2", "SuperGymka", 17, 300));
        two.addProduct(new Product("Фітнес-гумка 40 кг", "неопис2", "SuperGymka", 16, 300));
        two.addProduct(new Product("Фітнес-гумка 50 кг", "неопис2", "SuperGymka", 20, 300));
        two.addProduct(new Product("Фітнес-гумка 60 кг", "неопис2", "SuperRezyna", 23, 300));
        two.addProduct(new Product("Фітнес-гумка 70 кг", "неопис2", "SuperGymka", 25, 300));

        Group three = new Group("Лавки для жиму лежачи", "опис групи");
        three.addProduct(new Product("Лавка для жиму нерегульованна", "неопис", "IFactory", 5, 2000));
        three.addProduct(new Product("Лавка для жиму регульованна, ", "неопис2", "IFactory", 2, 3500));


        Group four = new Group("Гантелі", "опис групи");
        four.addProduct(new Product("Гантелі розбірні 28 кг", "неопис", "GymIron", 50, 2000));
        four.addProduct(new Product("Гантелі нерозбірні 28 кг", "неопис2", "GymIron", 50, 2500));

        Group five = new Group("Грифи", "опис групи");
        five.addProduct(new Product("Гриф олімійський", "неопис", "невиробник", 10, 7000));
        five.addProduct(new Product("Гриф V-форма", "неопис2", "невиробник2", 24, 800));
        shop.addGroup(one);
        shop.addGroup(two);
        shop.addGroup(three);
        shop.addGroup(four);
        shop.addGroup(five);

    }
}
