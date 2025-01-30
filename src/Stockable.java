public interface Stockable {
    void addStock(int quantity);
    void removeStock(int quantity);
    boolean isInStock();
}