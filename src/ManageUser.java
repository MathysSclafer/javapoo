import java.util.InputMismatchException;
import java.util.Scanner;

public class ManageUser extends Global{
    private final Admin admin = new Admin();
    private final Authentication auth = new Authentication();
    private final Scanner scanner = new Scanner(System.in);

    public void manageUser() {
        auth.showUser();

        if (auth.users.isEmpty()) {
            System.out.println("⚠ Aucun utilisateur à gérer !");
            admin.showMenu();
            return;
        }

        int selectedIndex = getUserChoice("Voulez-vous manager quel utilisateur ? (0 pour annuler) : ", auth.users.size());
        if (selectedIndex == 0) {
            admin.showMenu();
            return;
        }

        Client selectedUser = auth.users.get(selectedIndex - 1);
        System.out.println("Utilisateur sélectionné : " + selectedUser.getName() + " " + selectedUser.getFirstName() + " | Email : " + selectedUser.getEmail() + " | Statut : " + selectedUser.getStatus());

        while (true) {
            System.out.println("\nQue voulez-vous faire ?");
            System.out.println("1 : Supprimer");
            System.out.println("2 : Modifier le rôle");
            System.out.println("3 : Quitter");

            int choice = getUserChoice("Votre choix : ", 3);
            switch (choice) {
                case 1:
                    confirmAndDeleteUser(selectedUser);
                    return;
                case 2:
                    changeUserRole(selectedUser);
                    return;
                case 3:
                    admin.showMenu();
                    return;
            }
        }
    }

    private int getUserChoice(String message, int maxOption) {
        int choice = -1;
        while (true) {
            try {
                System.out.print(message);
                choice = scanner.nextInt();
                scanner.nextLine(); // Éviter les problèmes avec nextInt()

                if (choice >= 0 && choice <= maxOption) {
                    return choice;
                }
                System.out.println("❌ Choix invalide ! Veuillez entrer un nombre entre 0 et " + maxOption + ".");
            } catch (InputMismatchException e) {
                System.out.println("❌ Entrée invalide ! Veuillez entrer un nombre.");
                scanner.nextLine(); // Nettoyer l'entrée incorrecte
            }
        }
    }

    private void confirmAndDeleteUser(Client userToDelete) {
        System.out.print("❗ Êtes-vous sûr de vouloir supprimer " + userToDelete.getName() + " ? (oui/non) : ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (confirmation.equals("oui")) {
            if (auth.users.remove(userToDelete)) {
                System.out.println("✅ Utilisateur supprimé avec succès !");
                Pharmacy pharmacy = null;
                auth.save(pharmacy);
            } else {
                System.out.println("❌ Impossible de supprimer l'utilisateur. Il n'a pas été trouvé.");
            }
        } else {
            System.out.println("❌ Suppression annulée.");
        }
        manageUser();
    }

    private void changeUserRole(Client user) {
        String newStatus ="client";
        String currentStatus = user.getStatus();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Quelle role voulez vous donner ?");
        System.out.println("1 : Client");
        System.out.println("2 : Admin");
        System.out.println("3 : Pharmacien");
        int choice = getUserChoice("Votre choix : ", 3);
        switch (choice) {
            case 1:
                newStatus = "client";
                break;
                case 2:
                    newStatus = "admin";
                    break;
                    case 3:
                        newStatus = "pharmacist";
                        break;
                        default:
                            newStatus = "client";
                            break;

        }
        System.out.println("Le rôle de " + user.getName() + " va être changé de " + currentStatus + " à " + newStatus);
        System.out.print("Confirmer ? (oui/non) : ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (confirmation.equals("oui")) {
            user.setStatus(newStatus);
            System.out.println("✅ Le rôle a été changé avec succès !");
            Pharmacy pharmacy = null;
            auth.save(pharmacy);
        } else {
            System.out.println("❌ Modification annulée.");
        }
        manageUser();
    }
}
