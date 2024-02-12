package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.


import io.javalin.Javalin;
import org.example.Controller.ProductController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        ProductController productController = new ProductController();
        Javalin api = productController.getAPI();
        api.start(9017);
    }
}