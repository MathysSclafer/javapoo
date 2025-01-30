import java.util.ArrayList;
import java.util.Comparator;
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
            System.out.println("4. DEBUG");
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
                case "4":
                    List<Products> lst_produits = new ArrayList<Products>();
                    //Create product
                    Products medicament5 = new Products(4, "astrepstil", 47, 545, "contre la gorge qui fait mal", "parapharmacie");
                    lst_produits.add(medicament5);
                    lst_produits.sort(new Comparator<Products>() {

                        public int compare(Products elmnt1, Products elmnt2) {
                            return elmnt1.getName().compareTo(elmnt2.getName());  // trie par nom //
                        }});
                        System.out.println("Produits disponibles :");
                        for (Products elmnt : lst_produits) {
                            System.out.println(elmnt.getName() + " " + elmnt.getStock() + " " + elmnt.getCategory());
                        } // Nettoyer l'entrée incorrecte
            }
        }
    }
}
