import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Choisissez une option");
        System.out.println("1. Liste Produit");

        while(true){
            if(scanner.hasNextInt())
            {

            }
            else{
                System.out.println("Veuillez entrez une option correcte!");
                scanner.next();
            }
        }
    }
}