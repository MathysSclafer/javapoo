public interface Stockable {

    public boolean isInStock(int number);

    public void removeFromStock(int quantity);

    public void checkStock();

}
