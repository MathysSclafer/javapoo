import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Charge {
    public List<Products> charge() {
        JSONParser parser = new JSONParser();
        List<Products> productsList = new ArrayList<>();

        try {
            // Lire le fichier JSON
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/stocks_pharma.json"));

            // Récupérer l'objet pharmacie
            JSONObject pharmacie = (JSONObject) jsonObject.get("pharmacie");

            // Vérification si la structure JSON est valide
            if (pharmacie == null || !pharmacie.containsKey("produits")) {
                System.out.println("❌ Erreur : Structure JSON invalide !");
                return productsList;  // Retourne une liste vide en cas d'erreur
            }

            // Parcourir les catégories de produits
            JSONArray categories = (JSONArray) pharmacie.get("produits");
            for (Object categoryProduct : categories) {
                JSONObject categorys = (JSONObject) categoryProduct;

                // Récupérer le nom de la catégorie et sous-catégorie
                String category = (String) categorys.get("categorie");

                // Récupérer les produits de la sous-catégorie
                JSONArray produits = (JSONArray) categorys.get("produits");

                // Parcourir les produits et les ajouter à la liste
                for (Object productObject : produits) {
                    Products elmnt = getProducts((JSONObject) productObject, category);
                    productsList.add(elmnt);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Erreur de lecture du fichier JSON !");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("❌ Erreur de parsing du fichier JSON !");
            e.printStackTrace();
        }

        return productsList;  // Retourne la liste des produits
    }

    private static Products getProducts(JSONObject productObject, String category ) {
        return new Products(
                ((Long) productObject.get("id")).intValue(),  // ID
                (String) productObject.get("nom"),           // Nom
                (Double) productObject.get("prix"),          // Prix
                ((Long) productObject.get("quantiteStock")).intValue(),  // Quantité en stock
                (String) productObject.get("description"),   // Description
                (String) productObject.get("category")
                // Description
        );
    }
}
