public class Products implements Stockable{
    private int id;
    private String name;
    private double price;
    private int stock;
    private String description;
    private String categorie;

    public Products(int id , String name , double price , int stock , String description , String categorie) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.categorie = categorie;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public boolean validProduct(double price, int stock) {
        return this.price > 0 && this.stock > 0;        //on verifie si le produit qu'on ajoute est valide//
    }



    public void addStock(int quantity) {

        if(quantity > 0){           //si le nombre est positif//
            this.stock += quantity;
        }
    }


    public void removeStock(int quantity) {

        if(quantity < this.stock){           //si le nombre a enlever est inferieur au nombre disponible //
            this.stock -= quantity;

        }
        else {

            System.out.println("La quantité a enlever est supérieur au stock disponible ! ");
        }
    }


    public boolean isInStock() {
        return this.stock > 0;      // si le stock est superieur a 1 //
    }

    public void addProduct(){


    }
}
