import java.util.ArrayList;

public class Group {
    private String name;
    private String description;
    Shop shop = new Shop();

    @Override
    public String toString() {
        return "Назва групи = " + name + '\'' +
                ", опис=" + description;
    }
    Group(){}

    Group(String name, String description){
        this.name=name;
        this.description=description;
        this.products = new ArrayList<>();
    }
    private ArrayList<Product> products = new ArrayList<>();
    public boolean addProduct(Product product) {
        boolean alreadyAdded = false;
        for (Product products : products) {
            if (product.getName().equalsIgnoreCase(products.getName())) {
                alreadyAdded = true;
                break;
            }
        }
        if (alreadyAdded == false) {
            products.add(product);
            return true;
        }
        return false;
    }
    public void deleteProduct(Product product){
        products.remove(product);
    }
    public void deleteAllProducts(){
        products.clear();
    }
    public ArrayList<Product> getProducts() {
        return products;
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

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
