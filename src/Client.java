import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Client extends User {
    public Client(String name, String firstName, String email, String password, String uid, String status) {
        super(name, firstName, email, password, uid, status);
    }

    public Client() {
        super();
    }

    @Override
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;

        while (!quit) {
            final String RESET = "\u001B[0m";
            final String YELLOW = "\u001B[33m";
            final String GREEN = "\u001B[32m";
            final String BLUE = "\u001B[34m";
            final String CYAN = "\u001B[36m";
            final String RED = "\u001B[31m";  // Définition de la couleur rouge

            System.out.println(CYAN +
                    "============================== "+"Bonjour, Client"+ " ==============================" + RESET);
            System.out.println(YELLOW +
                    "  _____  _                                  _____           _   _                   \n" +
                    " |  __ \\| |                                |  __ \\         | | (_)                  \n" +
                    " | |__) | |__   __ _ _ __ _ __ ___   __ _  | |__) | __ __ _| |_ _  __ _ _   _  ___ \n" +
                    " |  ___/| '_ \\ / _` | '__| '_ ` _ \\ / _` | |  ___/ '__/ _` | __| |/ _` | | | |/ _ \\ \n" +
                    " | |    | | | | (_| | |  | | | | | | (_| | | |   | | | (_| | |_| | (_| | |_| |  __/ \n" +
                    " |_|    |_| |_|___,_|_|  |_| |_| |_|___,_| |_|   |_|  \\__,_|___|_|___, |__,_|\\___| \n" +
                    "                                                                     | |            \n" +
                    "                                                                     |_|            " + RESET);

            System.out.println("\n" + GREEN + "===== MENU CLIENT =====" + RESET);
            System.out.println("1. Afficher les produits");
            System.out.println("2. Faire une commande");
            System.out.println("3. Déconnexion");
            System.out.print(CYAN + "Votre choix : " + RESET);

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("\n" + GREEN + "Ouverture de la liste produit..." + RESET);
                    ShowProduct showProduct = new ShowProduct();
                    showProduct.showProducts();
                    break;
                case "2":
                    System.out.println("commande");
                    Pharmacy pharmacy = new Pharmacy("Pharmacie","13 rue");
                    pharmacy.getProducts();
                    Command command = new Command(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
                    if (!command.startCommand(pharmacy)) {
                        command = null;
                        System.out.println("La commande a bien été supprimée.");
                    } else {
                        System.out.println("Commande confirmée!");
                    }
                    break;
                case "3":
                    System.out.println("\n" + RED + "Déconnexion..." + RESET);
                    quit = true;
                    break;
                default:
                    System.out.println("❌ " + RED + "Choix invalide !" + RESET);
            }
        }
    }
}
