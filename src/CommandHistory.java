import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommandHistory extends Global {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static void historyCommand(Pharmacy pharmacy) {
        // Trier les commandes avant l'affichage
        dateSorter(pharmacy);

        System.out.println(CYAN + "========== Historique des commandes ==========" + RESET);
        for (Command command : pharmacy.getCommands()) {
            System.out.println(command.getDate() + "   Pharmacien: " + command.getPharmacistName());

            if (Objects.equals(command.getType(), "urgent")) {
                System.out.println(RED + "Commande " + command.getType() + "e" + RESET
                        + "         Client: " + command.getCustomerName());
            } else {
                System.out.println("Commande " + command.getType()
                        + "         Client: " + command.getCustomerName());
            }

            System.out.println("Produits: ");
            for (Map.Entry<Products, Integer> entry : command.getProducts().entrySet()) {
                System.out.println(entry.getValue() + " " + entry.getKey().getName());
            }

            System.out.println("--------------------------------------------");
        }
    }

    public static void dateSorter(Pharmacy pharmacy) {
        List<Command> commands = pharmacy.getCommands();
        int n = commands.size();

        for (int i = 1; i < n; i++) {
            Command key = commands.get(i);
            Date keyDate;
            try {
                keyDate = dateFormat.parse(key.getDate());
            } catch (ParseException e) {
                throw new RuntimeException("Erreur de format de date : " + key.getDate(), e);
            }

            int j = i - 1;
            while (j >= 0) {
                Date currentDate;
                try {
                    currentDate = dateFormat.parse(commands.get(j).getDate());
                } catch (ParseException e) {
                    throw new RuntimeException("Erreur de format de date : " + commands.get(j).getDate(), e);
                }

                if (currentDate.before(keyDate)) { // Trie en ordre décroissant (du plus récent au plus ancien)
                    commands.set(j + 1, commands.get(j));
                    j--;
                } else {
                    break;
                }
            }
            commands.set(j + 1, key);
        }
    }
}
