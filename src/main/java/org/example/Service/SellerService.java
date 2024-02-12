package org.example.Service;

import org.example.Exception.SellerException;
import org.example.Main;
import org.example.Model.Seller;

import java.util.*;

public class SellerService {

    Set<String> sellerNames;
    List<Seller> sellerList;

    public SellerService(){
        sellerList = new ArrayList<>();
        sellerNames = new LinkedHashSet<>();
        Main.log.info("New Seller List created");
    }
    public List<Seller> getAllSellers(){
        Main.log.info("Seller List returned: " + sellerList);
        return sellerList;
    }

    public void insertSeller(Seller seller) throws SellerException {
        if(seller.getName().isEmpty()){
            Main.log.warn("Seller name is empty");
            throw new SellerException("Seller name is empty");
        }
        if(isDuplicate(seller.getName())){
            Main.log.warn("Seller already exists: " + seller.getName());
            throw new SellerException("Seller already exists: " + seller.getName());
        }
        sellerList.add(seller);
        sellerNames.add(seller.getName());
        Main.log.info("Seller added: " + seller);
    }

    public boolean isDuplicate (String name){
        for (String seller : sellerNames) {
            if(name.equalsIgnoreCase(seller)) return true;
        }
        return false;
    }
}
