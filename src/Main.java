import java.util.*;

public class Main {
    public static void main(String[] args) {


        //on  creer les produits //

        Products medicament1 = new Products(1, "smecta", 12, 55, "contre la chiasse","medicament");
        Products medicament2 = new Products(2, "douliprane", 8, 14, "contre la diarrhée liquide","cosmetique");
        Products medicament3 = new Products(3, "spasfon", 4, 158, "contre la constipation","complement alimentaire");
        Products medicament4 = new Products(4, "strepstil", 47, 545, "contre la gorge qui fait mal","parapharmacie");


        //on creer une liste ajustable des produits //

        List<Products> lst_produit = new ArrayList<>();

        //oin ajoite a la liste les produits //

        lst_produit.add(medicament1);
        lst_produit.add(medicament2);
        lst_produit.add(medicament3);
        lst_produit.add(medicament4);

        // Tri par ordre alphabétique //

        lst_produit.sort(new Comparator<Products>() {
            @Override
            public int compare(Products elmnt1, Products elmnt2) {
                return elmnt1.getName().compareTo(elmnt2.getName());  // trie par nom //
            }
        });

        // on affiche les produit trié//
        for (Products elmnt : lst_produit) {
            System.out.println(elmnt);
        }

        //on creer une catégorie de produit//

        Category category1 = new Category("cosmétique", "anti-biotique", lst_produit);



        //on creer un scanner//

        Scanner scanner = new Scanner(System.in);

        //on fait une demende de l'utilisateur//

        System.out.println("Choisissez une option :");
        System.out.println("1. Liste Produit");

        while (true) {      //tant que l'utilisateur ne tape rien//

            if (scanner.hasNextInt()) {     //verifie l'entréé d'un nombre//
                int choice = scanner.nextInt();  //on recupère l'entrée//
                if (choice == 1) {               //si l'entrée est : 1 //

                    System.out.println("Produits disponibles : \n");
                    for (Products elmnt : lst_produit) {                //on affiche tous les produits de la liste //
                        System.out.println("Produit: " + elmnt.getName() + " || Prix : " + elmnt.getPrice() +"$"+ " || Quantité: " + elmnt.getStock() + " || Catégorie: " + elmnt.getCategorie() + "\n");
                    }
                    break;  // on sort de la bouocle quand c fini pour eviter une boucle infini//

                } else {
                    System.out.println("Option invalide, veuillez réessayer.");  //si autre que "1" on retourne erreur//
                }

            } else {
                System.out.println("Veuillez entrer une option correcte !");
                scanner.next();     //on enleve l'erreur de la mémoire , on evite la boucle infini//
            }
        }

        scanner.close(); // Fermer le scanner pour éviter les fuites mémoire //



    }
}
