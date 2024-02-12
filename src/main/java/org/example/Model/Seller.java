package org.example.Model;

public class Seller {
    private String name;

    public Seller() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "name='" + name + '\'' +
                '}';
    }
}
