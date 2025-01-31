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
        ShowProduct showProduct = new ShowProduct();
        boolean quit = false;

        while (!quit) {
            final String RESET = "\u001B[0m";
            final String YELLOW = "\u001B[33m";
            final String GREEN = "\u001B[32m";
            final String BLUE = "\u001B[34m";
            final String CYAN = "\u001B[36m";
            final String RED = "\u001B[31m";

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
            System.out.println("1 : Afficher les produits");
            System.out.println("2 : Chercher un produit par nom");
            System.out.println("3 : Déconnexion");
            System.out.print(CYAN + "Votre choix : " + RESET);
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    showProduct.showProducts();
                    break;
                case "2":
                    showProduct.searchProduct();
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
