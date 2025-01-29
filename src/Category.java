import java.util.List;
import java.util.*;
import java.io.*;

public class Category {
    private String category;
    private String subCategory;
    private List<Products> products;


    public Category(String category , String subCategory , List<Products> products){

        this.category= category;
        this.subCategory = subCategory;
        this.products = products;

    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public List<Products> getProducts() {
        return products;

    }

    public void showList(){

        for(Products element : products){

            System.out.println(element + " ");
        }
    }

}

