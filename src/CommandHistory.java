import java.util.Map;
import java.util.Objects;

public class CommandHistory extends Global {

    public static void historyCommand(Pharmacy pharmacy){
        System.out.println(CYAN + "==========Historique des commandes==========" + RESET);
        for(Command command : pharmacy.getCommands()){
            System.out.println(command.getDate() +  "   Pharmacien: "  + command.getPharmacistName());
            if(Objects.equals(command.getType(), "urgent")){
                System.out.println(RED + "Commande " + command.getType() + RESET
                        + "         Client: " + command.getCustomerName());
            }
            else{
                System.out.println("Commande " + command.getType()
                        + "         Client: " + command.getCustomerName());
            }

            System.out.println("Produits: ");
            for(Map.Entry<Product, Integer> entry : command.getProducts().entrySet()){
                System.out.println(entry.getValue() + " " + entry.getKey().getName());
            }

            System.out.println("--------------------------------------------");
        }
    }
}
