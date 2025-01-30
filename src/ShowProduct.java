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
            System.out.println("3. Supprimer un Produit");
            System.out.println("4. Quitter");
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
                        // Supprimer un produit
                        System.out.println("\n--- Suppression d'un Produit ---");
                        System.out.println("Voulez-vous supprimer un produit par (1) Nom ou (2) ID ?");
                        int choiceDelete = scanner.nextInt();
                        scanner.nextLine();  // Consommer la ligne restante

                        if (choiceDelete == 1) {
                            // Supprimer par nom
                            System.out.print("Nom du produit à supprimer : ");
                            String productName = scanner.nextLine();

                            // Recherche et suppression du produit
                            if (supprimerProduitParNom(lst_produit, productName)) {
                                // Sauvegarder après suppression
                                saveProducts(lst_produit);
                            }
                        } else if (choiceDelete == 2) {
                            // Supprimer par ID
                            System.out.print("ID du produit à supprimer : ");
                            int productId = scanner.nextInt();
                            scanner.nextLine();  // Consommer la ligne restante

                            // Recherche et suppression du produit
                            if (supprimerProduitParId(lst_produit, productId)) {
                                // Sauvegarder après suppression
                                saveProducts(lst_produit);
                            }
                        } else {
                            System.out.println("❌ Option invalide.");
                        }
                        break;

                    case 4:
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

    // Méthode pour supprimer un produit par nom
    public boolean supprimerProduitParNom(List<Products> lst_produit, String nomProduit) {
        for (Iterator<Products> iterator = lst_produit.iterator(); iterator.hasNext();) {
            Products produit = iterator.next();
            if (produit.getName().equalsIgnoreCase(nomProduit)) {
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

    // Méthode pour supprimer un produit par ID
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

    // Méthode pour sauvegarder la liste des produits dans le fichier JSON
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
