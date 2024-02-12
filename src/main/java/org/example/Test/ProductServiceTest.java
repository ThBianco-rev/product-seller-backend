package org.example.Test;

import org.example.Exception.ProductException;
import org.example.Exception.ProductNotFoundException;
import org.example.Exception.SellerException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ProductServiceTest {
    ProductService productService;
    SellerService sellerService;
    Seller testSeller;


    @Before
    public void setup() throws SellerException {
        productService = new ProductService();
        sellerService = new SellerService();

        testSeller = new Seller();
        testSeller.setName("Tommy Wiseau");
        sellerService.insertSeller(testSeller);

        // Dependency injection to simulate what happens in the controller
        productService.sellerService = sellerService;
    }

    @Test
    public void emptyAtStart(){
        List<Product> productList = productService.getAllProducts();
        Assert.assertTrue(productList.isEmpty());
    }

    @Test
    public void insertProduct(){
        String name = "Football";
        Integer price = 15;
        String sellerName = "Tommy Wiseau";

        Product testProduct = new Product();
        testProduct.setName(name);
        testProduct.setPrice(price);
        testProduct.setSellerName(sellerName);

        try{
            productService.insertProduct(testProduct);
        } catch (ProductException e) {
            e.printStackTrace();
            Assert.fail("Exception should not be thrown");
        }

        List<Product> productList = productService.getAllProducts();
        Product actual = productList.get(0);
        Assert.assertTrue(actual.getId() > 0);
        Assert.assertEquals(name, actual.getName());
        Assert.assertEquals(price, actual.getPrice());
        Assert.assertEquals(sellerName, actual.getSellerName());
    }

    @Test
    public void insertProductEmptyName(){
        String name = "";
        Integer price = 15;
        String sellerName = "Tommy Wiseau";

        Product testProduct = new Product();
        testProduct.setName(name);
        testProduct.setPrice(price);
        testProduct.setSellerName(sellerName);

        try{
            productService.insertProduct(testProduct);
            Assert.fail();
        } catch (ProductException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertProductNegativePrice(){
        String name = "Football";
        Integer price = -15;
        String sellerName = "Tommy Wiseau";

        Product testProduct = new Product();
        testProduct.setName(name);
        testProduct.setPrice(price);
        testProduct.setSellerName(sellerName);

        try{
            productService.insertProduct(testProduct);
            Assert.fail();
        } catch (ProductException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertProductInvalidSeller(){
        String name = "Football";
        Integer price = 15;
        String sellerName = "Bon Jovi";

        Product testProduct = new Product();
        testProduct.setName(name);
        testProduct.setPrice(price);
        testProduct.setSellerName(sellerName);

        try{
            productService.insertProduct(testProduct);
            Assert.fail();
        } catch (ProductException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getProductById(){
        insertProduct();

        List<Product> productList = productService.getAllProducts();
        int id = productList.get(0).getId();

        try{
            productService.getProductById(id);
        } catch (ProductNotFoundException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void getProductByIdNotFound(){
        insertProduct();

        List<Product> productList = productService.getAllProducts();
        int id = productList.get(0).getId();

        try{
            productService.getProductById(42);
            Assert.fail();
        } catch (ProductNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateProduct(){

        String name = "Gun";
        Integer price = 2000;
        String sellerName = "Tommy Wiseau";

        Product testProduct = new Product();
        testProduct.setName(name);
        testProduct.setPrice(price);
        testProduct.setSellerName(sellerName);

        insertProduct();

        List<Product> productList = productService.getAllProducts();
        int id = productList.get(0).getId();

        try{
            productService.updateProduct(id,testProduct);

        } catch (ProductException e) {
            e.printStackTrace();
            Assert.fail();
        }

        Product actual = productList.get(0);
        Assert.assertTrue(actual.getId() > 0);
        Assert.assertEquals(name, actual.getName());
        Assert.assertEquals(price, actual.getPrice());
        Assert.assertEquals(sellerName, actual.getSellerName());
    }

    @Test
    public void updateProductEmptyName(){

        String name = "";
        Integer price = 2000;
        String sellerName = "Tommy Wiseau";

        Product testProduct = new Product();
        testProduct.setName(name);
        testProduct.setPrice(price);
        testProduct.setSellerName(sellerName);

        insertProduct();

        List<Product> productList = productService.getAllProducts();
        int id = productList.get(0).getId();

        try{
            productService.updateProduct(id,testProduct);
            Assert.fail();

        } catch (ProductException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateProductNegativePrice(){

        String name = "Gun";
        Integer price = -2000;
        String sellerName = "Tommy Wiseau";

        Product testProduct = new Product();
        testProduct.setName(name);
        testProduct.setPrice(price);
        testProduct.setSellerName(sellerName);

        insertProduct();

        List<Product> productList = productService.getAllProducts();
        int id = productList.get(0).getId();

        try{
            productService.updateProduct(id,testProduct);
            Assert.fail();

        } catch (ProductException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateProductInvalidSeller(){

        String name = "Gun";
        Integer price = 2000;
        String sellerName = "Mark";

        Product testProduct = new Product();
        testProduct.setName(name);
        testProduct.setPrice(price);
        testProduct.setSellerName(sellerName);

        insertProduct();

        List<Product> productList = productService.getAllProducts();
        int id = productList.get(0).getId();

        try{
            productService.updateProduct(id,testProduct);
            Assert.fail();

        } catch (ProductException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteProduct(){
        insertProduct();

        List<Product> productList = productService.getAllProducts();
        int id = productList.get(0).getId();

        try{
            productService.deleteProduct(id);

        } catch (ProductException e) {
            e.printStackTrace();
            Assert.fail();
        }

        Assert.assertTrue(productList.isEmpty());
    }
}
