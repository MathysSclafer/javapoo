import java.util.*;

public class Command extends Global{
    private String date;
    private Map<Products, Integer> products = new HashMap<>(); // Stocker Product au lieu de String
    private String type;
    private String pharmacistName;
    private String customerName;
    private Scanner scanner = new Scanner(System.in);

    public Command(String date) {
        this.date = date;
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

        while (product == null) {
            System.out.println(YELLOW + "Entrez le nom d'un produit: " + RESET);
            String productText = scanner.nextLine().trim();

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

        while (true) {
            System.out.println(YELLOW + "Entrez la quantité de " + product.getName() + " que vous voulez :" +
                    "\n (Il en reste " + product.getStock() + " en stock)" + RESET);
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

        System.out.println(YELLOW + "Voulez-vous ajouter un autre médicament ('oui' ou 'non') ? " + RESET);
        String response = scanner.nextLine().trim();
        if (response.equalsIgnoreCase("oui")) {
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
                return true;
            } else {
                System.out.println("Veuillez entrer une réponse valide !");
            }
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
