package org.example.Test;

import org.example.Exception.SellerException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class SellerServiceTest {
    ProductService productService;
    SellerService sellerService;


    @Before
    public void setup() throws SellerException {
        sellerService = new SellerService();



    }
    @Test
    public void getAllEmpty(){
        List<Seller> sellerList = sellerService.getAllSellers();
        Assert.assertTrue(sellerList.isEmpty());
    }


    @Test
    public void insertSeller(){
        Seller testSeller = new Seller();
        testSeller.setName("Tommy Wiseau");

        try{
            sellerService.insertSeller(testSeller);
        } catch (SellerException e) {
            e.printStackTrace();
            Assert.fail();
        }

        List<Seller> sellerList = sellerService.getAllSellers();
        Seller actual = sellerList.get(0);
        Assert.assertEquals("Tommy Wiseau", actual.getName());
    }

    @Test
    public void insertDuplicateSeller(){
        Seller testSeller = new Seller();
        testSeller.setName("Tommy Wiseau");

        try{
            sellerService.insertSeller(testSeller);
            sellerService.insertSeller(testSeller);
            Assert.fail();
        } catch (SellerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertEmptySeller(){
        Seller testSeller = new Seller();
        testSeller.setName("");

        try{
            sellerService.insertSeller(testSeller);
            Assert.fail();
        } catch (SellerException e) {
            e.printStackTrace();
        }
    }
}
