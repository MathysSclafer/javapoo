import java.util.ArrayList;
import java.util.List;

public class Pharmacy{
    private String name;
    private String address;
    private List<Products> products = new ArrayList<>();
    private List<Command> commands = new ArrayList<>();


    public Pharmacy(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void addProductToPharmacy(Products product) {
        products.add(product);
    }

    public void addCommandToPharmacy(Command command) {
        commands.add(command);
    }


    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public List<Products> getProducts() {
        return products;
    }
}
