import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Phamarcist extends User {
    Pharmacy pharmacy = new Pharmacy("Pharmacie", "13 rue de la rue");
    public Phamarcist(String name, String firstName, String email, String password, String uid, String status) {
        super(name, firstName, email, password, uid, status);
    }

    public Phamarcist() {
        super();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        Command commandLoad = new Command("12 janvier");
        commandLoad.load(pharmacy);


        while (!quit) {
            final String RESET = "\u001B[0m";
            final String YELLOW = "\u001B[33m";
            final String GREEN = "\u001B[32m";
            final String BLUE = "\u001B[34m";
            final String CYAN = "\u001B[36m";
            final String RED = "\u001B[31m";  // Définition de la couleur rouge

            System.out.println(CYAN +
                    "============================== "+"Bonjour, Pharmacien"+ " ==============================" + RESET);
            System.out.println(YELLOW +
                    "  _____  _                                  _____           _   _                   \n" +
                    " |  __ \\| |                                |  __ \\         | | (_)                  \n" +
                    " | |__) | |__   __ _ _ __ _ __ ___   __ _  | |__) | __ __ _| |_ _  __ _ _   _  ___ \n" +
                    " |  ___/| '_ \\ / _` | '__| '_ ` _ \\ / _` | |  ___/ '__/ _` | __| |/ _` | | | |/ _ \\ \n" +
                    " | |    | | | | (_| | |  | | | | | | (_| | | |   | | | (_| | |_| | (_| | |_| |  __/ \n" +
                    " |_|    |_| |_|___,_|_|  |_| |_| |_|___,_| |_|   |_|  \\__,_|___|_|___, |__,_|\\___| \n" +
                    "                                                                     | |            \n" +
                    "                                                                     |_|            " + RESET);

            System.out.println("\n" + GREEN + "===== MENU Pharmacien =====" + RESET);
            System.out.println("1 : Faire une vente");
            System.out.println("2 : Historique des ventes");
            System.out.println("3 : Stock critique");
            System.out.println("4 : Déconnexion");
            System.out.print(CYAN + "Votre choix : " + RESET);

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" :
                    System.out.println("\n" + BLUE + "Faire une vente.." + RESET);
                    Command command = new Command(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
                    if (!command.startCommand(pharmacy, this)) {
                        command = null;
                        System.out.println("La commande a bien été supprimée.");
                    } else {
                        System.out.println("Commande confirmée!");
                    }
                    break;
                case "2":
                    System.out.println("\n" + YELLOW + "Historique des ventes.." + RESET);
                    CommandHistory.historyCommand(pharmacy);
                    break;
                case "3":
                    System.out.println("\n" + GREEN + "Stock critique.." + RESET);
                    ProductSorter.insertionSortAndPrint(pharmacy.getProducts());
                    break;
                case "4":
                    System.out.println("\n" + RED + "Déconnexion..." + RESET);
                    quit = true;
                    break;
                default:
                    System.out.println("❌ " + RED + "Choix invalide !" + RESET);
            }
        }
    }
}
