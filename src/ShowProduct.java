import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ShowProduct {

    private List<Products> productList;
    private final Scanner scanner;

    public ShowProduct() {
        Charge charge = new Charge();
        this.productList = charge.charge();
        this.scanner = new Scanner(System.in);
        // Tri des produits par ordre alphabétique au démarrage
        productList.sort(Comparator.comparing(Products::getName));
    }

    public void show() {
        while (true) {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1. Liste des Produits");
            System.out.println("2. Ajouter un Produit");
            System.out.println("3. Supprimer un Produit");
            System.out.println("4. Quitter");
            System.out.print("Votre choix : ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consommer le retour à la ligne

                switch (choice) {
                    case 1 -> showProducts();
                    case 2 -> addProduct();
                    case 3 -> deleteProductByName();
                    case 4 -> {
                        System.out.println("\uD83D\uDC4B Au revoir !");
                        return;
                    }
                    default -> System.out.println("❌ Option invalide, veuillez réessayer.");
                }
            } else {
                System.out.println("❌ Veuillez entrer un numéro valide !");
                scanner.next(); // Nettoyer l'entrée invalide
            }
        }
    }

    public void showProducts() {
        productList.sort(Comparator.comparing(Products::getName));
        System.out.println("\n--- Liste des Produits ---");
        if (productList.isEmpty()) {
            System.out.println("Aucun produit n'est disponible !");
        } else {
            for (Products p : productList) {
                System.out.println("- " + p.getName() + " | Stock: " + p.getStock());
            }
        }
    }

    public void addProduct() {
        System.out.println("\n--- Ajout d'un Produit ---");

        System.out.print("Nom du produit : ");
        String name = scanner.nextLine();

        double price = readDouble("Prix : ");
        int stock = readInt("Stock : ");

        System.out.print("Description : ");
        String description = scanner.nextLine();

        System.out.print("Catégorie : ");
        String category = scanner.nextLine();

        // Création et ajout du produit
        Products newProduct = new Products(productList.size() + 1, name, price, stock, description, category);
        productList.add(newProduct);

        // Tri après ajout
        productList.sort(Comparator.comparing(Products::getName));

        // Sauvegarde
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

    // Méthode pour sauvegarder la liste des produits dans le fichier JSON
    public void saveProducts() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        File file = new File("stocks_pharma.json");
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, productList);
            System.out.println("✅ Produits sauvegardés avec succès !");
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }

    public boolean deleteProductByName() {
        System.out.print("\nQuel produit voulez-vous supprimer ? ");
        String productName = scanner.nextLine();

        Iterator<Products> iterator = productList.iterator();
        while (iterator.hasNext()) {
            Products product = iterator.next();
            if (product.getName().equalsIgnoreCase(productName)) {
                System.out.println("Vous êtes sur le point de supprimer le produit : " + product.getName());
                System.out.print("Êtes-vous sûr de vouloir le supprimer ? (Oui/Non) : ");
                String confirmation = scanner.nextLine().trim().toLowerCase();

                if (confirmation.equals("oui")) {
                    iterator.remove();
                    System.out.println("✅ Produit supprimé !");
                    saveProducts();
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
        if (productList.isEmpty()) {
            System.out.println("📭 Aucun produit dans l'inventaire.");
            return;
        }

        System.out.print("\n🔎 Entrez le nom du produit à rechercher : ");
        String searchName = scanner.nextLine().toLowerCase();

        List<Products> foundProducts = productList.stream()
                .filter(p -> p.getName().toLowerCase().contains(searchName))
                .toList();

        if (foundProducts.isEmpty()) {
            System.out.println("❌ Aucun produit correspondant trouvé.");
        } else {
            System.out.println("\n✅ Produits trouvés :");
            for (Products p : foundProducts) {
                System.out.println("- " + p.getName() + " | Prix: " + p.getPrice() + " | Stock: " + p.getStock() + " | Catégorie: " + p.getCategory());
            }
        }
    }
}
