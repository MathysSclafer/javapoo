import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;

public class Charge {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();

        try {
            // Lire le fichier JSON
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/stocks_pharma.json"));

            // Récupérer l'objet pharmacie
            JSONObject pharmacie = (JSONObject) jsonObject.get("pharmacie");

            // Afficher les informations de la pharmacie
            System.out.println("Nom: " + pharmacie.get("nom"));
            System.out.println("Adresse: " + pharmacie.get("adresse"));

            // Récupérer les catégories de produits
            JSONArray categories = (JSONArray) pharmacie.get("produits");

            // Parcourir les catégories de produits
            for (Object categoryObject : categories) {
                JSONObject category = (JSONObject) categoryObject;
                System.out.println("\nCatégorie: " + category.get("categorie"));
                System.out.println("Sous-catégorie: " + category.get("sousCategorie"));

                // Récupérer les produits de la sous-catégorie
                JSONArray produits = (JSONArray) category.get("produits");

                // Parcourir les produits
                for (Object produitObject : produits) {
                    JSONObject produit = (JSONObject) produitObject;
                    System.out.println("\nID: " + produit.get("id"));
                    System.out.println("Nom: " + produit.get("nom"));
                    System.out.println("Prix: " + produit.get("prix"));
                    System.out.println("Quantité en stock: " + produit.get("quantiteStock"));
                    System.out.println("Description: " + produit.get("description"));
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
