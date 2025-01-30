import java.util.ArrayList;
import java.util.List;

public class ProductSorter extends  Global{
    public static void insertionSortAndPrint(List<Products> products) {
        List<Products> lowStock = new ArrayList<>();
        for (Products product : products) {
            if (product.getStock() < 5) {
                lowStock.add(product);
            }
        }
        int n = lowStock.size();

        for (int i = 1; i < n; i++) {
            Products key = lowStock.get(i);
            int j = i - 1;

            while (j >= 0 && lowStock.get(j).getStock() > key.getStock()) {
                lowStock.set(j + 1, lowStock.get(j));
                j--;
            }
            lowStock.set(j + 1, key);
        }

        System.out.println("=====PRODUITS PROCHE DE LA RUPTURE DE STOCK=====\n");
        System.out.println(CYAN + "Produit            Stock        Prix/u(€)" + RESET);

        for (Products product : lowStock) {
            String color = product.getStock() == 0 ? RED : RESET;
            System.out.printf("%s%-20s %-10d %.2f€%n%s", color, product.getName(), product.getStock(), product.getPrice(), RESET);
        }


    }
}
