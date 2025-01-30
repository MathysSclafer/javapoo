import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Main {
    public static void main(String[] args) {



        //on  creer les produits //

        Products medicament1 = new Products(1, "smecta", 12, 55, "contre la chiasse", "medicament");
        Products medicament2 = new Products(2, "douliprane", 8, 14, "contre la diarrhée liquide", "cosmetique");
        Products medicament3 = new Products(3, "spasfon", 4, 158, "contre la constipation", "complement alimentaire");
        Products medicament4 = new Products(4, "strepstil", 47, 545, "contre la gorge qui fait mal", "parapharmacie");


        //on creer une liste ajustable des produits //

        List<Products>  lst_produit = new ArrayList<>();



        //oin ajoite a la liste les produits //

        lst_produit.add(medicament1);
        lst_produit.add(medicament2);
        lst_produit.add(medicament3);
        lst_produit.add(medicament4);





        // Tri par ordre alphabétique //

        lst_produit.sort(new Comparator<Products>() {

            public int compare(Products elmnt1, Products elmnt2) {
                return elmnt1.getName().compareTo(elmnt2.getName());  // trie par nom //
            }
        });

        //on creer un scanner//

        Scanner scanner = new Scanner(System.in);

        System.out.println("Choisissez une option :");
        System.out.println("1. Liste Produit");

        while (true) {
            if (scanner.hasNextInt()) {
                int choix = scanner.nextInt();
                if (choix == 1) {
                    System.out.println("Produits disponibles :");
                    for (Products elmnt : lst_produit) {
                        System.out.println(elmnt.getName() + " " + elmnt.getStock() + " " + elmnt.getCategorie());
                    }
                    break;  // Sortie après affichage
                } else {
                    System.out.println("Option invalide, veuillez réessayer.");
                }
            } else {
                System.out.println("Veuillez entrer une option correcte !");
                scanner.next();  // Nettoyer l'entrée incorrecte
            }
        }
        scanner.close(); // Fermer le scanner pour éviter les fuites mémoire

//        // Affichage des produits et de leurs catégories
//        for (Products product : productsList) {
//            System.out.println("\nProduit : " + product.getName());
//            System.out.println("Catégorie : " + product.getCategorie());
//            System.out.println("Prix : " + product.getPrice());
//            System.out.println("Quantité en stock : " + product.getStock());
//            System.out.println("Description : " + product.getDescription());
//        }



    }

}