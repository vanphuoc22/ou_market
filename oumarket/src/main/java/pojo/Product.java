/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojo;

import java.util.UUID;

/**
 *
 * @author Khoa Tran
 */
public class Product {
    private String id;
    private String name;
    private int categoryId;
    private String categoryName;
    private double price;
    private String unit;
    private int quantity;
    private double priceDiscount = 0;
    private int promotion_id;
    private String promotion_name;
    private double total;
    
    {
        setId(UUID.randomUUID().toString());
    }
    
    public Product (String name, int categoryId, double price, int quantity, String unit) {
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
        
    }
    
    public Product (String id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    
    public Product(String id, String name, int categoryId, double price, int quantity, String unit, int promotion_id) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
        this.promotion_id = promotion_id;
    }
    
    public Product() {
        
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return the categoryId
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * @return the promotion_id
     */
    public int getPromotion_id() {
        return promotion_id;
    }

    /**
     * @param promotion_id the promotion_id to set
     */
    public void setPromotion_id(int promotion_id) {
        this.promotion_id = promotion_id;
    }

    /**
     * @return the promotion_name
     */
    public String getPromotion_name() {
        return promotion_name;
    }

    /**
     * @param promotion_name the promotion_name to set
     */
    public void setPromotion_name(String promotion_name) {
        this.promotion_name = promotion_name;
    }

    /**
     * @return the priceDiscount
     */
    public double getPriceDiscount() {
        return priceDiscount;
    }

    /**
     * @param priceDiscount the priceDiscount to set
     */
    public void setPriceDiscount(double priceDiscount) {
        this.priceDiscount = priceDiscount;
    }


}
