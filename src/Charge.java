import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Charge {
    public void charge() {

        JSONParser parser = new JSONParser();

        try {
            // Lire le fichier JSON
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/stocks_pharma.json"));

            // Récupérer l'objet pharmacie
            JSONObject pharmacie = (JSONObject) jsonObject.get("pharmacie");

            // Créer une liste pour stocker les produits
            List<Products> productsList = new ArrayList<>();

            // Parcourir les catégories de produits
            JSONArray categories = (JSONArray) pharmacie.get("produits");
            for (Object categoryProduct : categories) {
                JSONObject category = (JSONObject) categoryProduct;

                // Récupérer le nom de la catégorie et sous-catégorie
                String categorie = (String) category.get("categorie");
                String sousCategorie = (String) category.get("sousCategorie");

                // Récupérer les produits de la sous-catégorie
                JSONArray produits = (JSONArray) category.get("produits");

                // Parcourir les produits et les ajouter à la liste
                for (Object productObject : produits) {
                    Products elmnt = getProducts((JSONObject) productObject, categorie);

                    productsList.add(elmnt);
                }
            }

            // Affichage des produits et de leurs catégories
            for (Products product : productsList) {
                System.out.println("\nProduit : " + product.getName());
                System.out.println("Catégorie : " + product.getCategory());
                System.out.println("Prix : " + product.getPrice());
                System.out.println("Quantité en stock : " + product.getStock());
                System.out.println("Description : " + product.getDescription());
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static Products getProducts(JSONObject productObject, String categorie) {
        JSONObject produit = productObject;
        Products elmnt = new Products(
                ((Long) produit.get("id")).intValue(),  // ID
                (String) produit.get("nom"),           // Nom
                (Double) produit.get("prix"),          // Prix
                ((Long) produit.get("quantiteStock")).intValue(),  // Quantité en stock
                (String) produit.get("description"),   // Description
                categorie  // Catégorie sous forme de String
        );
        return elmnt;
    }
}
