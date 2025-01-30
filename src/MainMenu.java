import java.util.Scanner;

public class MainMenu {
    public static void mainMenu() {
        Authentication auth = new Authentication();
        Scanner scanner = new Scanner(System.in);

        final String RESET = "\u001B[0m";
        final String YELLOW = "\u001B[33m";
        final String GREEN = "\u001B[32m";
        final String CYAN = "\u001B[36m";
        final String RED = "\u001B[31m";  // Définition de la couleur rouge

        while (true) {
            System.out.println(CYAN +
                    "========================== MENU PRINCIPAL ===========================" + RESET);
            System.out.println(YELLOW +
                    "  _____                           _           _                     \n" +
                    " |  __ \\                         | |         | |                    \n" +
                    " | |__) |__ _ __ __ _ _ __ _ __ _| |_ ___  __| |_ ___ _ __ __ _ _ __ \n" +
                    " |  ___/ _ \\ '__/ _` | '__| '__| | __/ _ \\/ _` | '__| '__/ _` | '__|\n" +
                    " | |  |  __/ | | (_| | |  | |  | | ||  __/ (_| | |  | | | (_| | |   \n" +
                    " |_|   \\___|_|  \\__,_|_|  |_|  |_|\\__\\___|\\__,_|_|  |_|  \\__,_|_|   \n" +
                    "                                                                 " + RESET);

            System.out.println("\n" + GREEN + "===== MENU PRINCIPAL =====" + RESET);
            System.out.println("1. Créer un compte");
            System.out.println("2. Se connecter");
            System.out.println("3. Quitter");
            System.out.print(CYAN + "Votre choix : " + RESET);

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println(GREEN + "Création d'un compte..." + RESET);
                    auth.createUser();
                    break;
                case "2":
                    System.out.println(GREEN + "Connexion..." + RESET);
                    auth.loginUser();
                    break;
                case "3":
                    System.out.println(RED + "Merci d'avoir utilisé l'application !" + RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println("❌ " + RED + "Choix invalide !" + RESET);
            }
        }
    }
}
