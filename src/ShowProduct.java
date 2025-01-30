import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ShowProduct {

    private List<Products> lst_produit;
    private Scanner scanner;

    public ShowProduct() {
        Charge charge = new Charge();
        this.lst_produit = charge.charge();
        this.scanner = new Scanner(System.in);
        // Tri par ordre alphabétique au démarrage
        lst_produit.sort(Comparator.comparing(Products::getName));
    }

    public void show() {
        while (true) {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1. Liste des Produits");
            System.out.println("2. Ajouter un Produit");
            System.out.println("3. Quitter");
            System.out.print("Votre choix : ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consommer le retour à la ligne

                switch (choice) {
                    case 1:
                        showProducts();
                        break;
                    case 2:
                        newProduct();
                        break;
                    case 3:
                        System.out.println("👋 Au revoir !");
                        scanner.close();
                        return; // Quitte proprement la boucle
                    default:
                        System.out.println("❌ Option invalide, veuillez réessayer.");
                }
            } else {
                System.out.println("❌ Veuillez entrer un numéro valide !");
                scanner.next(); // Nettoyer l'entrée invalide
            }
        }
    }

    public void showProducts() {
        lst_produit.sort(Comparator.comparing(Products::getName));
        System.out.println("\n--- Liste des Produits ---");
        if (lst_produit.isEmpty()) {
            System.out.println("Aucun produit n'est disponible !");
        } else {
            for (Products p : lst_produit) {
                System.out.println("- " + p.getName() + " | Stock: " + p.getStock());
            }
        }
    }

    public void newProduct() {
        System.out.println("\n--- Ajout d'un Produit ---");

        System.out.print("Nom du produit : ");
        String nom = scanner.nextLine();

        double prix = readDouble("Prix : ");
        int stock = readInt("Stock : ");

        System.out.print("Description : ");
        String description = scanner.nextLine();

        System.out.println("Catégorie : ");
        String category = scanner.nextLine();
        // Création et ajout du produit
        Products newProduct = new Products(lst_produit.size() + 1, nom, prix, stock, description,category);
        lst_produit.add(newProduct);

        // Re-trier après l'ajout
        lst_produit.sort(Comparator.comparing(Products::getName));

        // Sauvegarde après ajout
        saveProducts();

        System.out.println("✅ Produit ajouté avec succès !");
    }

    private double readDouble(String message) {
        double value;
        while (true) {
            System.out.print(message);
            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                scanner.nextLine(); // Nettoyer le buffer
                break;
            } else {
                System.out.println("❌ Veuillez entrer un nombre valide.");
                scanner.next(); // Nettoyer l'entrée incorrecte
            }
        }
        return value;
    }

    private int readInt(String message) {
        int value;
        while (true) {
            System.out.print(message);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.nextLine(); // Nettoyer le buffer
                break;
            } else {
                System.out.println("❌ Veuillez entrer un nombre valide.");
                scanner.next(); // Nettoyer l'entrée incorrecte
            }
        }
        return value;
    }

    private void saveProducts() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        File file = new File("stocks_pharma.json");
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, lst_produit);
            System.out.println("✅ Produits sauvegardés !");
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }
}
