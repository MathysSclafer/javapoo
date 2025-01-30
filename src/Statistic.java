import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Statistic {

    public static void createCsvStatistic(Pharmacy pharmacy) {
        File csv = new File("commands.csv");

        try {
            FileWriter outputFile = new FileWriter(csv);
            CSVWriter writer = new CSVWriter(outputFile);

            // Écrire l'en-tête du fichier CSV
            String[] header = { "Produit", "Quantité Vendue", "Chiffre d'Affaires", "Produit le plus vendu" };
            writer.writeNext(header);

            // Calcul des statistiques
            Map<String, Integer> productSales = calculateProductSales(pharmacy); // Calcul des quantités par produit
            Map<String, Double> productRevenue = calculateProductRevenue(pharmacy); // Calcul du chiffre d'affaires par produit
            String mostSoldProduct = getMostSoldProduct(productSales); // Identifie le produit le plus vendu
            double totalSales = calculateTotalSales(pharmacy); // Total des ventes

            System.out.println(productSales);
            // Trier les produits par quantité vendue
            List<Map.Entry<String, Integer>> sortedByQuantity = sortProductsByQuantity(productSales);

            // Parcours des produits triés et écriture dans le fichier CSV
            for (Map.Entry<String, Integer> entry : sortedByQuantity) {
                String productName = entry.getKey();
                int quantitySold = entry.getValue();
                double revenue = productRevenue.getOrDefault(productName, 0.0); // Récupère le chiffre d'affaires du produit
                String[] data = createCsvRow(productName, quantitySold, revenue, mostSoldProduct);
                writer.writeNext(data);
            }

            // Ajouter le total des ventes à la fin
            addTotalSalesRow(writer, totalSales);

            // Fermer le writer
            writer.close();
            System.out.println("✅ Les statistiques ont été enregistrées dans 'commands.csv'.");

        } catch (IOException e) {
            System.err.println("❌ Erreur lors de la création du fichier CSV : " + e.getMessage());
        }
    }

    // Calculer les ventes totales par produit
    private static Map<String, Integer> calculateProductSales(Pharmacy pharmacy) {
        Map<String, Integer> productSales = new HashMap<>();
        for (Command command : pharmacy.getCommands()) {
            for (Map.Entry<Products, Integer> entry : command.getProducts().entrySet()) {
                String productName = entry.getKey().getName();
                int quantity = entry.getValue();
                productSales.put(productName, productSales.getOrDefault(productName, 0) + quantity);
            }
        }
        return productSales;
    }

    // Calculer le chiffre d'affaires par produit
    private static Map<String, Double> calculateProductRevenue(Pharmacy pharmacy) {
        Map<String, Double> productRevenue = new HashMap<>();
        for (Command command : pharmacy.getCommands()) {
            for (Map.Entry<Products, Integer> entry : command.getProducts().entrySet()) {
                String productName = entry.getKey().getName();
                int quantity = entry.getValue();
                double revenue = entry.getKey().getPrice() * quantity;
                productRevenue.put(productName, productRevenue.getOrDefault(productName, 0.0) + revenue);
            }
        }
        return productRevenue;
    }

    // Trouver le produit le plus vendu
    private static String getMostSoldProduct(Map<String, Integer> productSales) {
        return productSales.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    // Calculer le total des ventes
    private static double calculateTotalSales(Pharmacy pharmacy) {
        double totalSales = 0;
        for (Command command : pharmacy.getCommands()) {
            for (Map.Entry<Products, Integer> entry : command.getProducts().entrySet()) {
                Products product = entry.getKey();
                int quantity = entry.getValue();
                totalSales += product.calculateTotalPrice(quantity);
            }
        }
        return totalSales;
    }

    // Trier les produits par quantité vendue
    private static List<Map.Entry<String, Integer>> sortProductsByQuantity(Map<String, Integer> productSales) {
        List<Map.Entry<String, Integer>> sortedByQuantity = new ArrayList<>(productSales.entrySet());
        sortedByQuantity.sort((entry1, entry2) -> entry2.getValue() - entry1.getValue()); // Tri décroissant
        return sortedByQuantity;
    }

    // Créer une ligne pour chaque produit dans le CSV
    private static String[] createCsvRow(String productName, int quantitySold, double revenue, String mostSoldProduct) {
        String[] row = new String[4];
        row[0] = productName;
        row[1] = String.valueOf(quantitySold);
        row[2] = String.format("%.2f", revenue);
        row[3] = productName.equals(mostSoldProduct) ? "Oui" : "Non";
        return row;
    }

    // Ajouter une ligne de total des ventes
    private static void addTotalSalesRow(CSVWriter writer, double totalSales) {
        String[] totalData = { "Total des ventes", "", String.format("%.2f", totalSales), "" };
        writer.writeNext(totalData);
    }
}
