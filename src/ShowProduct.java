import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ShowProduct extends Global{

    private List<Products> productList;
    private final Scanner scanner;

    public ShowProduct() {
        Charge charge = new Charge();
        this.productList = charge.charge();
        this.scanner = new Scanner(System.in);
        // Tri des produits par ordre alphabétique au démarrage
        quickSort(productList, 0, productList.size() - 1);

    }

    public void show(Pharmacy pharmacy) {
        while (true) {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1. Liste des Produits");
            System.out.println("2. Ajouter un Produit");
            System.out.println("3. Supprimer un Produit");
            System.out.println("4. Quitter");
            System.out.print("Votre choix : ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> showProducts();
                    case 2 -> addProduct(pharmacy);
                    case 3 -> deleteProductByName(pharmacy);
                    case 4 -> {
                        System.out.println("\uD83D\uDC4B Au revoir !");
                        return;
                    }
                    default -> System.out.println("❌ Option invalide, veuillez réessayer.");
                }
            } else {
                System.out.println("❌ Veuillez entrer un numéro valide !");
                scanner.next();
            }
        }
    }

    public void showProducts() {

        quickSort(productList, 0, productList.size() - 1);
        System.out.println("=========LISTE DES PRODUITS=========\n");
        if (productList.isEmpty()) {
            System.out.println("Aucun produit n'est disponible !");
        } else {
            System.out.println(CYAN + "Produit             Stock      Prix/u(€)   Catégorie" + RESET);
            System.out.println("------------------------------------------------------------");

            int maxNameLength = 20;

            for (Products p : productList) {
                String name = p.getName();
                List<String> nameLines = new ArrayList<>();

                // Découpage du nom si trop long
                while (name.length() > maxNameLength) {
                    nameLines.add(name.substring(0, maxNameLength));
                    name = name.substring(maxNameLength);
                }
                nameLines.add(name);

                int numLines = nameLines.size();

                System.out.printf("%-20s %-10d %.2f€   %s%n",
                        nameLines.get(0), p.getStock(), p.getPrice(), p.getCategory());

                for (int i = 1; i < numLines; i++) {
                    System.out.printf("%-20s%n", nameLines.get(i));
                }
            }
        }



    }

    public void addProduct(Pharmacy pharmacy) {
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
        Products newProduct = new Products(pharmacy.getProducts().size() + 1, name, price, stock, description, category);
        pharmacy.getProducts().add(newProduct);

        // Tri après ajout
        quickSort(pharmacy.getProducts(), 0, productList.size() - 1);

        // Sauvegarde
        saveProducts(pharmacy);

        System.out.println("✅ Produit ajouté avec succès !");
    }

    private double readDouble(String message) {
        double value;
        while (true) {
            System.out.print(message);
            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                scanner.nextLine();
                break;
            } else {
                System.out.println("❌ Veuillez entrer un nombre valide.");
                scanner.next();
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
                scanner.nextLine();
                break;
            } else {
                System.out.println("❌ Veuillez entrer un nombre valide.");
                scanner.next();
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
            System.out.println("✅ Produits sauvegardés avec succès !");
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }

    public boolean deleteProductByName(Pharmacy pharmacy) {
        System.out.print("\nQuel produit voulez-vous supprimer ? ");
        String productName = scanner.nextLine();

        Iterator<Products> iterator = pharmacy.getProducts().iterator();
        while (iterator.hasNext()) {
            Products product = iterator.next();
            if (product.getName().equalsIgnoreCase(productName)) {
                System.out.println("Vous êtes sur le point de supprimer le produit : " + product.getName());
                System.out.print("Êtes-vous sûr de vouloir le supprimer ? (Oui/Non) : ");
                String confirmation = scanner.nextLine().trim().toLowerCase();

                if (confirmation.equals("oui")) {
                    iterator.remove();
                    System.out.println("✅ Produit supprimé !");
                    saveProducts(pharmacy);
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
    private void quickSort(List<Products> products, int low, int high) {
        if (low < high) {
            int partitionIndex = partition(products, low, high);
            quickSort(products, low, partitionIndex - 1);
            quickSort(products, partitionIndex + 1, high);
        }
    }

    private int partition(List<Products> products, int low, int high) {
        String pivot = products.get(high).getName();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (products.get(j).getName().compareToIgnoreCase(pivot) <= 0) {
                i++;
                Collections.swap(products, i, j);
            }
        }
        Collections.swap(products, i + 1, high);
        return i + 1;
    }
}
