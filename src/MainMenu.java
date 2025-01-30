import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    public static void mainMenu() {
        Authentication auth = new Authentication();
        Scanner scanner = new Scanner(System.in);

        final String RESET = "\u001B[0m";
        final String YELLOW = "\u001B[33m";
        final String GREEN = "\u001B[32m";
        final String CYAN = "\u001B[36m";
        final String RED = "\u001B[31m";

        while (true) {
            System.out.println(CYAN +
                    "========================== MENU PRINCIPAL ===========================" + RESET);
            System.out.println(YELLOW +
                    "  _____  _                                  _____           _   _                   \n" +
                    " |  __ \\| |                                |  __ \\         | | (_)                  \n" +
                    " | |__) | |__   __ _ _ __ _ __ ___   __ _  | |__) | __ __ _| |_ _  __ _ _   _  ___ \n" +
                    " |  ___/| '_ \\ / _` | '__| '_ ` _ \\ / _` | |  ___/ '__/ _` | __| |/ _` | | | |/ _ \\ \n" +
                    " | |    | | | | (_| | |  | | | | | | (_| | | |   | | | (_| | |_| | (_| | |_| |  __/ \n" +
                    " |_|    |_| |_|___,_|_|  |_| |_| |_|___,_| |_|   |_|  \\__,_|___|_|___, |__,_|\\___| \n" +
                    "                                                                     | |            \n" +
                    "                                                                     |_|            " + RESET);

            System.out.println("\n" + GREEN + "===== MENU PRINCIPAL =====" + RESET);
            System.out.println("1. Créer un compte");
            System.out.println("2. Se connecter");
            System.out.println("3. Quitter");
            System.out.println("4. DEBUG (Afficher les produits)");
            System.out.print(CYAN + "Votre choix : " + RESET);

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println(GREEN + "Création d'un compte..." + RESET);
                    auth.createUser("client");
                    break;
                case "2":
                    System.out.println(GREEN + "Connexion..." + RESET);
                    auth.loginUser();
                    break;
                case "3":
                    System.out.println(RED + "Merci d'avoir utilisé l'application !" + RESET);
                    System.exit(0);
                    break;
                case "4":
                    System.out.println("commande");
                    Pharmacy pharmacy = new Pharmacy("salit","salit");
                    pharmacy.getProducts();
                    Command command = new Command(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
                    if (!command.startCommand(pharmacy)) {
                        command = null;
                        System.out.println("La commande a bien été supprimée.");
                    } else {
                        System.out.println("Commande confirmée!");
                    }
                    break;
                case "5":
                    ShowProduct showProduct = new ShowProduct();
                    showProduct.show();
                default:
                    System.out.println(RED + "❌ Choix invalide !" + RESET);
            }
        }
    }
}
