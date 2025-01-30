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

    public void show(Pharmacy pharmacy) {
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
                        newProduct(pharmacy);
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

    public void newProduct(Pharmacy pharmacy) {
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
        saveProducts(pharmacy);

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




    // Méthode pour sauvegarder la liste des produits dans le fichier JSON
    public void saveProducts(Pharmacy pharmacy) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        File file = new File("stocks_pharma.json");
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, pharmacy.getProducts());
            System.out.println("✅ Produits sauvegardés !");
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }
    public boolean supprimerProduitParId(List<Products> lst_produit, int productId) {
        for (Iterator<Products> iterator = lst_produit.iterator(); iterator.hasNext();) {
            Products produit = iterator.next();
            if (produit.getId() == productId) {
                System.out.println("Vous êtes sur le point de supprimer le produit : " + produit.getName());
                System.out.print("Êtes-vous sûr de vouloir le supprimer ? (Oui/Non) : ");
                Scanner scanner = new Scanner(System.in);
                String confirmation = scanner.nextLine().trim().toLowerCase();
                if (confirmation.equals("oui")) {
                    iterator.remove();
                    System.out.println("✅ Produit supprimé !");
                    return true;
                } else {
                    System.out.println("❌ Suppression annulée.");
                    return false;
                }
            }
        }
        System.out.println("❌ Produit non trouvé.");
        return false;
    }
    public void searchProduct() {
        if (lst_produit.isEmpty()) {
            System.out.println("📭 Aucun produit dans l'inventaire.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("\n🔎 Entrez le nom du produit à rechercher : ");
        String searchName = scanner.nextLine().toLowerCase();

        List<Products> foundProducts = lst_produit.stream() //flux de données//
                .filter(p -> p.getName().toLowerCase().contains(searchName))    //on filtre les mots dans la liste par rapport a searchName  //
                .toList(); //on met le resultat dans lst_product//

        if (foundProducts.isEmpty()) {
            System.out.println("❌ Aucun produit correspondant trouvé.");
        } else {
            System.out.println("\n✅ Produits trouvés :");
            for (Products elmnt : foundProducts) {
                System.out.println("- " + elmnt.getName() + " | Prix: " + elmnt.getPrice() + " | Stock: " + elmnt.getStock() + " | Catégorie: " + elmnt.getCategory());
            }
        }

    }
}
