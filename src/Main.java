import java.util.Scanner;

public class Main  {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Choisissez une option");
        System.out.println("Choisissez une categorie");
        System.out.println("Médicaments 1");
        System.out.println("Cosmétiques 2");
        System.out.println("Parapharmacie 3");
        System.out.println("Compléments alimentaires 4");

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