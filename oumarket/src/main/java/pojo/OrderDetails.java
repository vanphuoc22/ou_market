/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojo;

import javafx.collections.ObservableList;

/**
 *
 * @author Khoa Tran
 */
public class OrderDetails {
    private Product product;
    private double quantity;

    public OrderDetails(Product product, double quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getPrice() {
        return getProduct().getPrice();
    }
    
    public double getPriceDiscount() {
        return getProduct().getPriceDiscount();
    }

    public double getTotal() {
        return getPriceDiscount() * quantity;
    }
    
    /**
     * @return the quantity
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }
}

