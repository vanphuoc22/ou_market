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
public class Customer {
    private String id;
    private String name;
    private String ngaySinh;
    private String phone;
    private double point;
    
    {
        setId(UUID.randomUUID().toString());
    }
    
    public Customer() {
        
    }
    
    public Customer(String name, String ngaySinh, String phone, double point) {
        this.name = name;
        this.ngaySinh = ngaySinh;
        this.phone = phone;
        this.point = point;
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
     * @return the ngaySinh
     */
    public String getNgaySinh() {
        return ngaySinh;
    }

    /**
     * @param ngaySinh the ngaySinh to set
     */
    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the point
     */
    public double getPoint() {
        return point;
    }

    /**
     * @param point the point to set
     */
    public void setPoint(double point) {
        this.point = point;
    }
}
