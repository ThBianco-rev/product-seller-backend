package org.example.Service;
import org.example.Exception.ProductException;
import org.example.Exception.ProductFormatException;
import org.example.Exception.ProductNotFoundException;
import org.example.Main;
import org.example.Model.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    List<Product> productList;
    public SellerService sellerService;

    public ProductService(){
        productList = new ArrayList<>();
        Main.log.info("New Product List created");
    }
    public List<Product> getAllProducts(){
        Main.log.info("Product List returned: " + productList);
        return productList;
    }
    public void insertProduct(Product product) throws ProductException {
        //when Product is created, set the ID to a randomized value
        product.setId((int) (Math.random() * 100000000));
        // Check if given Seller is a Seller in our Seller List
        String sellerName = product.getSellerName();
        if(product.getName() == null || product.getName().isEmpty()){
            Main.log.warn("Product name is empty");
            throw new ProductException("Product name is empty");
        }
        if(product.getPrice() <= 0){
            Main.log.warn("Product price is less than or equal to 0");
            throw new ProductException("Product price is less than or equal to 0");
        }
        if(!isVerifiedSeller(sellerName)){
            Main.log.warn("Seller " + sellerName + " is not a verified Seller");
            throw new ProductException("Seller " + sellerName + " is not a verified Seller");
        }

        productList.add(product);
        Main.log.info("Product added: " + product.toString());
    }

    public Product getProductById(int id) throws ProductNotFoundException {
        for (Product product: productList) {
            if(product.getId() == id){
                Main.log.info("Product found by ID: " + product.toString());
                return product;
            }
        }
        Main.log.warn("Product with ID " + id + " was not found");
        throw new ProductNotFoundException("Product not found");
    }

    private boolean isVerifiedSeller(String sellerName){
        return sellerService.isDuplicate(sellerName);
    }

    public Product updateProduct(int id, Product product) throws ProductException {
        String sellerName = product.getSellerName();
        if(product.getName() == null || product.getName().isEmpty()){
            Main.log.warn("Product name is empty");
            throw new ProductFormatException("Product name is empty");
        }
        if(product.getPrice() <= 0){
            Main.log.warn("Product price is less than or equal to 0");
            throw new ProductFormatException("Product price is less than or equal to 0");
        }
        if(!isVerifiedSeller(sellerName)){
            Main.log.warn("Seller " + sellerName + " is not a verified Seller");
            throw new ProductFormatException("Seller " + sellerName + " is not a verified Seller");
        }

        Product productToUpdate = getProductById(id);
        productToUpdate.setName(product.getName());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setSellerName(product.getSellerName());

        Main.log.info("Product updated. New values: " + productToUpdate);
        return productToUpdate;
    }

    public void deleteProduct(int id) throws ProductNotFoundException {

        Product productToRemove = getProductById(id);
        productList.remove(productToRemove);

        Main.log.info("Product removed: " + productToRemove);
    }
}
