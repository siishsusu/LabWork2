import java.util.ArrayList;

public class Group {
    private String name;
    private String description;

    @Override
    public String toString() {
        return "Назва групи = " + name + '\'' +
                ", опис=" + description;
    }

    Group(String name, String description){
        this.name=name;
        this.description=description;
        this.products = new ArrayList<>();
    }
    private ArrayList<Product> products;
    public void addProduct(Product product){
        products.add(product);
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
