import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Pharmacy pharmacy = new Pharmacy("Phamarcie", "13 rue");
        Product doliprane = new Product(0,
                "Doliprane", 100, 6, "CACACA");
        Product caca = new Product(1,
                "caca", 100, 2, "CACACA");


        pharmacy.addProductToPharmacy(doliprane);
        pharmacy.addProductToPharmacy(caca);

        menu(pharmacy);
    }

    public static void menu(Pharmacy pharmacy){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choisissez une option");
        System.out.println("1. Passer une commande");
        System.out.println("2. Historique de commande");

        while(true){
            if(scanner.hasNextInt())
            {
                if(scanner.nextInt() == 1){
                    Command command = new Command(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
                    if (!command.startCommand(pharmacy)) {
                        command = null;
                        System.out.println("La commande a bien été supprimée.");
                    } else {
                        System.out.println("Commande confirmée!");
                    }
                    menu(pharmacy);
                }
                if(scanner.nextInt() == 2){
                    CommandHistory.historyCommand(pharmacy);
                }
            }
            else{
                System.out.println("Veuillez entrez une option correcte!");
                scanner.next();
            }
        }
    }
}