import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;
import java.io.File;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Authentication extends Global implements Serializable{
    private static final String FILE_PATH = "users.json";
    public List<Client> users = new ArrayList<>();
    public String loggedUid = "";
    boolean login = false;
    Admin admin = new Admin();
    Phamarcist Phamarcist = new Phamarcist();

    // Permet de créé un user
    public void createUser(String status) {
        Pharmacy pharmacy = null;
        load(pharmacy);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrer votre nom");
        String name = scanner.nextLine().trim();
        if (name.isEmpty() || name.length() < 3) {
            System.out.println("❌ Le nom ne peut pas être vide !");
            createUser("client");
            return;
        }

        System.out.println("Entrer votre prénom");
        String prenom = scanner.nextLine().trim();
        if (prenom.isEmpty() || prenom.length() < 3) {
            System.out.println("❌ Le prénom ne peut pas être vide ! Et doit faire minimum 3 caractéres ! ");
            createUser("client");
            return;
        }

        System.out.println("Entrer votre email");
        String email = scanner.nextLine().trim();
        if (!isValidEmail(email)) {
            System.out.println("❌ Email invalide !");
            createUser("client");
            return;
        }

        if (isEmailAlreadyUsed(email)) {
            System.out.println("❌ Cet email est déjà utilisé !");
            createUser("client");
            return;
        }

        System.out.println("Entrer votre mot de passe (min. 8 caractères, 1 majuscule, 1 chiffre)");
        String password = scanner.nextLine().trim();
        if (!isValidPassword(password)) {
            System.out.println("❌ Mot de passe trop faible !");
            createUser("client");
            return;
        }
        String uid = UUID.randomUUID().toString();

        users.add(new Client(name, prenom, email, password, uid, status));

        System.out.println("✅ Création du compte Client réussie !");
        save(pharmacy);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean isEmailAlreadyUsed(String email) {
        return users.stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d).{8,}$";
        return Pattern.matches(passwordRegex, password);
    }
    //Permet de print tous les users
    public void showUser() {
        Pharmacy pharmacy = null;
        load(pharmacy);
        int i = 0;
        for (Client u : users) {
            i++;
            System.out.println(i + " : Nom : " + u.getName() + " | Prénom : " + u.getFirstName() + " | Email : " + u.getEmail() + " | Mot de passe : " + u.getPassword() + " | Statut : " + u.getStatus());
        }
    }

    // Permet de se connecter
    public void loginUser() {
        Pharmacy pharmacy = null;
        load(pharmacy);
        Scanner user = new Scanner(System.in);
        System.out.println("Entrer votre email");
        String email = user.nextLine();
        System.out.println("Entrer votre mot de passe");
        String pass = user.nextLine();

        // For each pour regarder tous les utilisateurs et comparer les entrée
        for (Client u : users) {
            if (u.getEmail() != null && u.getEmail().equals(email) && u.getPassword() != null && u.getPassword().equals(pass)) {
                System.out.println("Connexion réussie !");
                login = true;
                if (u.getStatus() != null && u.getStatus().equals("client")) {
                    u.showMenu();
                    loggedUid = u.getUid();
                } else if (u.getStatus() != null && u.getStatus().equals("admin")) {
                    admin.showMenu();
                    loggedUid = u.getUid();

                } else if (u.getStatus() != null && u.getStatus().equals("pharmacist")) {
                    Phamarcist.showMenu();
                    loggedUid = u.getUid();
                } else {
                    System.out.println("Statut utilisateur invalide.");
                }
                break;
            }
        }

        if (!login) {
            System.out.println("Mot de passe ou Email invalide !");
            System.out.println("Souhaitez-vous créer un nouveau compte ? (Oui/Non)");
            String choice = user.nextLine().trim();
            if (choice.equalsIgnoreCase("Oui")) {
                createUser("client");
            }
        }
    }
    // Permet de lire le fichier User.json et de stocker les utilisateurs dans la liste client
    @Override
    public void load(Pharmacy pharmacy) {
        ObjectMapper objectMapper = new ObjectMapper();
        // Permet d'avoir un Json indenté
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // Permet de ne pas avoir d'erreur en cas de chose en plus dans le json
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try {
                users = objectMapper.readValue(file, new TypeReference<List<Client>>() {});
            } catch (IOException e) {
                System.err.println("❌ Erreur lors de la lecture du fichier : " + e.getMessage());
            }
        } else {
            System.out.println("❌ Le fichier d'utilisateurs n'existe pas. Aucun utilisateur chargé.");
        }
    }
    // Permet de save les clients dans le fichier json
    @Override
    public void save(Pharmacy pharmacy) {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(FILE_PATH);
        try {
            // Permet d'avoir un json beau aussi et d'ecrire dedans
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, users);
            System.out.println("✅ Utilisateurs sauvegardés !");
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }

}