import java.io.IOException;
import java.util.*;
import java.io.File;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Command extends Global{
    private String date;
    private Map<Products, Integer> products = new HashMap<>(); // Stocker Product au lieu de String
    private String type;
    private String pharmacistName;
    private String customerName;
    private Scanner scanner = new Scanner(System.in);
    private static final String FILE_PATH = "commands.json";

    public Command(String date) {
        this.date = date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setProducts(Map<Products, Integer> products) {
        this.products = products;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPharmacistName(String pharmacistName) {
        this.pharmacistName = pharmacistName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public boolean startCommand(Pharmacy pharmacy) {
        while (true) {
            System.out.println(YELLOW + "Veuillez choisir votre type de commande ('urgent' ou 'standard') : " + RESET);
            this.pharmacistName = "Roger";
            String inputType = scanner.nextLine().trim();
            if (inputType.equalsIgnoreCase("urgent") || inputType.equalsIgnoreCase("standard")) {
                this.type = inputType;
                break;
            } else {
                System.out.println(RED + "Veuillez choisir une option correcte !" + RESET);
            }
        }

        askForNames();
        askForProduct(pharmacy);

        return confirmCommand(pharmacy);
    }

    public void askForNames() {
        while (true) {
            System.out.println(YELLOW + "Entrez le nom du client : " + RESET);
            String inputName = scanner.nextLine().trim();

            if (!inputName.isEmpty()) {
                this.customerName = inputName;
                break;
            } else {
                System.out.println(RED + "Veuillez entrer un nom valide !" + RESET);
            }
        }
    }
    public void askForProduct(Pharmacy pharmacy) {
        Products product = null;
        int quantity;
        boolean continueQuestion = false;

        while (product == null) {
            System.out.println(YELLOW + "Entrez le nom d'un produit ('continuer' pour passer): " + RESET);
            String productText = scanner.nextLine().trim();

            if(productText.equalsIgnoreCase("continuer")){
                continueQuestion = true;
                break;
            }

            product = pharmacy.getProducts().stream()
                    .filter(p -> p.getName().equalsIgnoreCase(productText))
                    .findFirst()
                    .orElse(null);

            if (product == null) {
                System.out.println(RED + "Le produit que vous avez choisi n'est pas disponible en stock." + RESET);
            }
            else if (!product.isInStock(1)) {
                System.out.println(RED + "Il n'y a plus ce produit en stock !" + RESET);
                product = null;
            }
        }

        int alreadyOrdered = products.getOrDefault(product, 0);

        while (!continueQuestion) {
            System.out.println(YELLOW + "Entrez la quantité de " + product.getName() + " que vous voulez :" +
                    "\n (Il en reste " + (product.getStock() - alreadyOrdered) + " en stock)" + RESET);
            if (scanner.hasNextInt()) {
                quantity = scanner.nextInt();
                scanner.nextLine();

                int totalRequested = alreadyOrdered + quantity;

                if (product.isInStock(totalRequested)) {
                    // Mise à jour de la quantité totale
                    products.put(product, totalRequested);
                    System.out.println(GREEN + "Produit ajouté : " + product.getName() + " - Quantité totale : " + totalRequested + RESET);
                    break;
                } else {
                    System.out.println(RED + "Stock insuffisant ! Vous avez déjà sélectionné " + alreadyOrdered +
                            " unités. Il ne reste que " + (product.getStock() - alreadyOrdered) + " en stock." + RESET);
                    if((product.getStock() - alreadyOrdered) == 0){
                        break;
                    }
                }
            } else {
                System.out.println(RED + "Veuillez entrer un nombre valide !" + RESET);
                scanner.next();
            }
        }
        if(!continueQuestion){
            askForProduct(pharmacy);
        }
    }


    public boolean confirmCommand(Pharmacy pharmacy) {
        while (true) {
            System.out.println(PURPLE + "Êtes-vous sûr de vouloir confirmer la commande ('oui' / 'non' / 'annuler') ?" + RESET);
            String response = scanner.nextLine().trim();

            if (response.equalsIgnoreCase("non")) {
                askForProduct(pharmacy);
            } else if (response.equalsIgnoreCase("annuler")) {
                System.out.println("Commande annulée !");
                return false;
            } else if (response.equalsIgnoreCase("oui")) {
                System.out.println("-------------Résumé de la commande-------------");
                System.out.println("Nom du pharmacien: " + this.pharmacistName);
                System.out.println("Nom du client: " + this.customerName);
                System.out.println("Type de commande : " + this.type);
                System.out.println("Date de commande: " + this.date);
                System.out.println("Produits dans le panier :");

                double totalPrice = 0;
                for (Map.Entry<Products, Integer> entry : this.products.entrySet()) {
                    Products product = entry.getKey();
                    int quantity = entry.getValue();

                    if (quantity > 1) {
                        System.out.println(quantity + " "
                                + product.getName() + "s "
                                + " => " + product.calculateTotalPrice(quantity) + "€"
                                + " (" + product.getPrice() + "€/u)");
                    } else {
                        System.out.println(quantity + " "
                                + product.getName() + " => " + product.getPrice() + "€");
                    }

                    totalPrice += product.calculateTotalPrice(quantity);
                    product.removeFromStock(quantity);
                    product.checkStock();
                }

                pharmacy.addCommandToPharmacy(this);
                System.out.println(GREEN + "Prix total: " + totalPrice + "€" + RESET);
                System.out.println("-------------------------------------------");
                saveCommand(pharmacy);
                Statistic.createCsvStatistic(pharmacy);
                ShowProduct showProduct = new ShowProduct();
                showProduct.saveProducts(pharmacy);
                return true;
            } else {
                System.out.println("Veuillez entrer une réponse valide !");
            }
        }
    }

    private void saveCommand(Pharmacy pharmacy) {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(FILE_PATH);

        List<Map<String, Object>> serializedCommands = new ArrayList<>();

        // Sérialiser toutes les commandes dans la pharmacie
        for (Command command : pharmacy.getCommands()) {
            List<Map<String, Object>> serializedProducts = new ArrayList<>();

            for (Map.Entry<Products, Integer> entry : command.getProducts().entrySet()) {
                Products product = entry.getKey();
                Map<String, Object> productMap = new HashMap<>();
                productMap.put("id", product.getId());
                productMap.put("name", product.getName());
                productMap.put("quantity", entry.getValue());
                serializedProducts.add(productMap);
            }

            // Créez la map de commande
            Map<String, Object> commandMap = new HashMap<>();
            commandMap.put("date", command.getDate());
            commandMap.put("pharmacistName", command.getPharmacistName());
            commandMap.put("customerName", command.getCustomerName());
            commandMap.put("type", command.getType());
            commandMap.put("products", serializedProducts);

            serializedCommands.add(commandMap);
        }

        try {
            // Sérialisez la liste complète des commandes
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, serializedCommands);
            System.out.println("✅ Toutes les commandes ont été sauvegardées avec succès !");
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de l'écriture des commandes dans le fichier : " + e.getMessage());
        }
    }



    public static void loadCommands(Pharmacy pharmacy) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        File file = new File(FILE_PATH);

        if (file.exists()) {
            try {
                List<Map<String, Object>> loadedData = objectMapper.readValue(file, new TypeReference<List<Map<String, Object>>>() {});


                // Liste des commandes à ajouter à la pharmacie
                List<Command> commands = new ArrayList<>();


                // Pour chaque objet dans le tableau, créer une Commande et ajouter les produits
                for (Map<String, Object> commandMap : loadedData) {
                    String date = (String) commandMap.get("date");
                    String pharmacistName = (String) commandMap.get("pharmacistName");
                    String customerName = (String) commandMap.get("customerName");
                    String type = (String) commandMap.get("type");

                    // Extraire les produits de la commande
                    List<Map<String, Object>> serializedProducts = (List<Map<String, Object>>) commandMap.get("products");

                    Map<Products, Integer> products = new HashMap<>();
                    for (Map<String, Object> productMap : serializedProducts) {
                        int productId = (int) productMap.get("id");
                        String productName = (String) productMap.get("name");
                        int quantity = (int) productMap.get("quantity");

                        // Rechercher le produit dans la pharmacie en fonction de son ID
                        Products product = pharmacy.getProducts().stream()
                                .filter(p -> p.getId() == productId)
                                .findFirst()
                                .orElse(null);

                        if (product != null) {
                            products.put(product, quantity);
                        }
                    }

                    // Créer la commande et ajouter les produits
                    Command command = new Command(date);
                    command.setPharmacistName(pharmacistName);
                    command.setCustomerName(customerName);
                    command.setType(type);
                    command.setProducts(products);

                    commands.add(command);
                }

                // Ajouter toutes les commandes à la pharmacie
                pharmacy.setCommands(commands);
                System.out.println("✅ Commandes chargées avec succès !");
            } catch (IOException e) {
                System.err.println("❌ Erreur lors de la lecture du fichier : " + e.getMessage());
            }
        } else {
            System.out.println("❌ Le fichier des commandes n'existe pas. Aucune commande chargée.");
        }
    }





    public Map<Products, Integer> getProducts() {
        return products;
    }

    public String getDate() {
        return date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPharmacistName() {
        return pharmacistName;
    }

    public String getType() {
        return type;
    }
}
