public class Product {
    private String name, description, manufacturer;
    private double amount, price;

    public Product(String name, String description, String manufacturer, double amount, double price) {
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.amount = amount;
        this.price = price;
    }
    public String toString(Product product){
        return "Назва: "+getName()+", Опис: "+getDescription()+", Виробник: "+getManufacturer()+", Кількість: "
                +getAmount()+", Ціна за одиницю: "+getPrice()+", Загальна вартість: "+priceForAll(product);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public double priceForAll(Product product){return getPrice() * getAmount();}
}


