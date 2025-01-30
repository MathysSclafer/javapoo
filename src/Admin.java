import java.util.Scanner;

public class Admin extends User {
    Pharmacy pharmacy = new Pharmacy("Pharmacie","13 rue");
    public Admin(String name, String firstName, String email, String password, String uid, String status) {
        super(name, firstName, email, password, uid, status);
    }

    public Admin() {
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
                    "============================== "+"Bonjour, Admin"+ " ==============================" + RESET);
            System.out.println(YELLOW +
                    "  _____  _                                  _____           _   _                   \n" +
                    " |  __ \\| |                                |  __ \\         | | (_)                  \n" +
                    " | |__) | |__   __ _ _ __ _ __ ___   __ _  | |__) | __ __ _| |_ _  __ _ _   _  ___ \n" +
                    " |  ___/| '_ \\ / _` | '__| '_ ` _ \\ / _` | |  ___/ '__/ _` | __| |/ _` | | | |/ _ \\ \n" +
                    " | |    | | | | (_| | |  | | | | | | (_| | | |   | | | (_| | |_| | (_| | |_| |  __/ \n" +
                    " |_|    |_| |_|___,_|_|  |_| |_| |_|___,_| |_|   |_|  \\__,_|___|_|___, |__,_|\\___| \n" +
                    "                                                                     | |            \n" +
                    "                                                                     |_|            " + RESET);

            System.out.println("\n" + GREEN + "===== MENU Admin =====" + RESET);
            System.out.println("1. Panel produit");
            System.out.println("2. Historique des ventes");
            System.out.println("3. Stock critique");
            System.out.println("4. Accès au panel utilisateurs");
            System.out.println("5. Création d'un compte");
            System.out.println("6. Déconnexion");
            System.out.print(CYAN + "Votre choix : " + RESET);

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("\n" + BLUE + "Panel produit.." + RESET);
                    ShowProduct show = new ShowProduct();
                    show.show();
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
                    System.out.println("\n" + YELLOW + "Accès au panel utilisateurs..." + RESET);
                    ManageUser manageUser = new ManageUser();
                    manageUser.manageUser();
                    break;
                case "5":
                    System.out.println("\n" + YELLOW + "Création d'un compte..." + RESET);
                    Authentication authentication = new Authentication();
                    String status = "Client";
                    System.out.println("Voulez vous créer un :");
                    System.out.println("1 : Client ");
                    System.out.println("2 : Pharmacien");
                    System.out.println("3 : Admin");
                    String choiceperm = scanner.nextLine();
                    switch (choiceperm) {
                        case "1":
                            status = "client";
                            break;
                        case "2":
                            status = "pharmacist";
                            break;
                        case "3":
                            status = "admin";
                            break;
                        default:
                            status = "client";
                            break;
                    }
                    authentication.createUser(status);
                    break;
                case "6":
                    System.out.println("\n" + RED + "Déconnexion..." + RESET);
                    quit = true;
                    MainMenu mainMenu = new MainMenu();
                    mainMenu.mainMenu();
                    break;
                default:
                    System.out.println("❌ " + RED + "Choix invalide !" + RESET);
            }
        }
    }

    public void addUser() {
        System.out.println("add user");
    }

    public void deleteUser() {
        System.out.println("delete user");
    }
    public void manageUser() {
        System.out.println("manage user");
    }
}
