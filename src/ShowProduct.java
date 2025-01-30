import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ShowProduct {

    public void show() {
        Charge charge = new Charge();
        // Création de la liste des produits
        List<Products> lst_produit = charge.charge();

        // Tri par ordre alphabétique
        lst_produit.sort(Comparator.comparing(Products::getName));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1. Liste des Produits");
            System.out.println("2. Ajouter un Produit");
            System.out.println("3. Quitter");
            System.out.print("Votre choix : ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consommer le retour à la ligne

                switch (choice) {
                    case 1:
                        System.out.println("\n--- Liste des Produits ---");
                        if (lst_produit.isEmpty()) {
                            System.out.println("Aucun produit n'est disponible !");
                        } else {
                            for (Products p : lst_produit) {
                                System.out.println("- " + p.getName() + " | Stock: " + p.getStock() + " | Catégorie: " + p.getCategory());
                            }
                        }
                        break;

                    case 2:
                        System.out.println("\n--- Ajout d'un Produit ---");

                        System.out.print("Nom du produit : ");
                        String nom = scanner.nextLine();

                        double prix = 0;
                        while (true) {
                            System.out.print("Prix : ");
                            if (scanner.hasNextDouble()) {
                                prix = scanner.nextDouble();
                                scanner.nextLine();  // Nettoyer le buffer
                                break;
                            } else {
                                System.out.println("❌ Veuillez entrer un nombre valide pour le prix.");
                                scanner.next();  // Nettoyer l'entrée incorrecte
                            }
                        }

                        int stock = 0;
                        while (true) {
                            System.out.print("Stock : ");
                            if (scanner.hasNextInt()) {
                                stock = scanner.nextInt();
                                scanner.nextLine();  // Nettoyer le buffer
                                break;
                            } else {
                                System.out.println("❌ Veuillez entrer un nombre valide pour le stock.");
                                scanner.next();  // Nettoyer l'entrée incorrecte
                            }
                        }

                        System.out.print("Description : ");
                        String description = scanner.nextLine();

                        System.out.print("Catégorie : ");
                        String categorie = scanner.nextLine();

                        // Création et ajout du produit
                        Products newProduct = new Products(lst_produit.size() + 1, nom, prix, stock, description, categorie);
                        lst_produit.add(newProduct);

                        // Re-trier après l'ajout
                        lst_produit.sort(Comparator.comparing(Products::getName));

                        // Sauvegarder après ajout
                        saveProducts(lst_produit);

                        System.out.println("✅ Produit ajouté avec succès !");
                        break;

                    case 3:
                        System.out.println("👋 Au revoir !");
                        scanner.close();
                        return;  // Quitte proprement la boucle

                    default:
                        System.out.println("❌ Option invalide, veuillez réessayer.");
                }
            } else {
                System.out.println("❌ Veuillez entrer un numéro valide !");
                scanner.next();  // Nettoyer l'entrée invalide
            }
        }
    }

    public void saveProducts(List<Products> lst_produit) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Formater JSON pour être lisible
        File file = new File("stocks_pharma.json"); // Ajout de l'extension correcte

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, lst_produit);
            System.out.println("✅ Produits sauvegardés !");
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }
}
