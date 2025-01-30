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
            Object obj = parser.parse(new FileReader("stocks_pharma.json"));

            if (obj instanceof JSONArray) {
                JSONArray productsArray = (JSONArray) obj;

                // Parcourir les produits
                for (Object productObject : productsArray) {
                    JSONObject productJSON = (JSONObject) productObject;

                    Products product = new Products(
                            ((Long) productJSON.get("id")).intValue(),
                            (String) productJSON.get("name"),
                            (Double) productJSON.get("price"),
                            ((Long) productJSON.get("stock")).intValue(),
                            (String) productJSON.get("description"),
                            (productJSON.get("category") != null) ? (String) productJSON.get("category") : "Non catégorisé" // Gestion du `null`
                    );

                    productsList.add(product);
                }
            } else {
                System.out.println("❌ Erreur : Le fichier JSON doit être un tableau de produits !");
            }

        } catch (IOException e) {
            System.out.println("❌ Erreur de lecture du fichier JSON !");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("❌ Erreur de parsing du fichier JSON !");
            e.printStackTrace();
        }

        return productsList;
    }
}
