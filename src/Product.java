import java.util.ArrayList;
import java.util.List;

public class Product extends Global{
    private int id;
    private String name;
    private double price;
    private int stock;
    private String description;
    private String category;

    public Product(int id, String name, double price, int stock, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public double calculateTotalPrice(int nbOfProductBought){
        return price*nbOfProductBought;
    }

    public boolean isInStock(int number) {
        if(stock >=  number){
            return true;
        }
        else{
            return false;
        }
    }

    public void removeFromStock(int quantity) {
        stock -= quantity;

    }

    public void checkStock() {
        if(stock < 5){
            System.out.println(RED + "Attention! Il reste " + stock + " de " + name + RESET);
        }
    }
}
