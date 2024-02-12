package org.example.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.Exception.ProductException;
import org.example.Exception.ProductNotFoundException;
import org.example.Exception.SellerException;
import org.example.Main;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;

import java.util.*;

import static java.lang.Integer.parseInt;

public class ProductController {
    static ProductService productService;
    static SellerService sellerService;
    static final String SERVICENAME_PRODUCT = "Product Service";
    static final String SERVICENAME_SELLER = "Seller Service";
    public ProductController(){
        productService = new ProductService();
        sellerService = new SellerService();
        productService.sellerService = sellerService;
    }

    /**
     * Define endpoints here
         * GET all sellers
         * POST add new seller
         * GET all products
         * GET product by id
         * POST add new product
         * PUT update single product by id
         * DELETE single product by id
     */
    public Javalin getAPI(){
        Javalin api = Javalin.create();

        //Seller endpoints
        api.get("/seller", ProductController::getAllSellersHandler);
        api.post("/seller", ProductController::postSellerHandler);

        // Product endpoints
        api.get("/product", ProductController::getAllProductHandler);
        api.get("/product/{id}", ProductController::getProductByIdHandler);
        api.post("/product", ProductController::postProductHandler);
        api.put("/product/{id}", ProductController::putProductByIdHandler);
        api.delete("/product/{id}", ProductController::deleteProductByIdHandler);

        return api;
    }

    private static void getAllSellersHandler(Context context){
        List<Seller> sellerList = sellerService.getAllSellers();
        context.json(sellerList);
    }

    private static void postSellerHandler(Context context){
        ObjectMapper om = new ObjectMapper();
        try{
            Seller s = om.readValue(context.body(), Seller.class);
            sellerService.insertSeller(s);
            context.json(s).status(201);
            Main.log.info("Successful POST to " + SERVICENAME_SELLER + ". Status = 201");

        } catch (JsonProcessingException e) {
            context.status(400);
            Main.log.warn("JsonProcessingException - Failed POST to " + SERVICENAME_SELLER + ". Status = 400");
        } catch (SellerException e) {
            context.status(400);
            Main.log.warn("Failed POST to " + SERVICENAME_SELLER + " due to duplicate Seller. Status = 400");
        }
    }
    public static void getAllProductHandler(Context context){
        List<Product> productList = productService.getAllProducts();
        context.json(productList).status(200);;
    }

    public static void getProductByIdHandler(Context context){
        int id = parseInt(Objects.requireNonNull(context.pathParam("id")));
        try{
            context.json(productService.getProductById(id)).status(200);
        }
        catch (NullPointerException e){
            Main.log.error("Product List is empty!");
        } catch (ProductException e) {
            Main.log.error(e.getMessage());
        }
    }

    private static void postProductHandler(Context context){
        ObjectMapper om = new ObjectMapper();
        try{
            Product p = om.readValue(context.body(), Product.class);
            productService.insertProduct(p);
            context.json(p).status(201);
            Main.log.info("Successful POST to " + SERVICENAME_PRODUCT + ". Status = 201");

        } catch (JsonProcessingException e) {
            context.status(400);
            Main.log.warn("Failed POST to " + SERVICENAME_PRODUCT + ". Status = 400");
        } catch (ProductException e) {
            context.status(400);
            Main.log.warn("Failed POST to " + SERVICENAME_PRODUCT + ". Status = 400");
        }
    }

    private static void putProductByIdHandler(Context context) {
        ObjectMapper om = new ObjectMapper();
        int id = parseInt(context.pathParam("id"));
        try {
            Product p = om.readValue(context.body(), Product.class);
            Product updatedProduct = productService.updateProduct(id, p);
            context.json(updatedProduct).status(201);
            Main.log.info("Successful PUT to " + SERVICENAME_PRODUCT);

        } catch (JsonProcessingException | ProductException e) {
            Main.log.warn("Failed PUT to " + SERVICENAME_PRODUCT);
        }
    }

    private static void deleteProductByIdHandler(Context context) {

        int id = parseInt(context.pathParam("id"));
        try {
            productService.deleteProduct(id);
            context.json("Success").status(200);
            Main.log.info("Successful DELETE on " + SERVICENAME_PRODUCT + ". Status = 200");

        } catch (ProductNotFoundException e) {
            context.json("Fail - Product does not exist").status(200);
            Main.log.info("Failed DELETE on " + SERVICENAME_PRODUCT + ". Status = 200");
        }
    }
}
