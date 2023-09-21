/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 *
 * @author Khoa Tran
 */
public class Order {

    private String id;
    private String orderDate;
    private Double total;
    private Double tienKhachDua;
    private Double tienTraKhach;

    {
        setId(UUID.randomUUID().toString());
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        setOrderDate(currentDate.format(formatter));
    }

    public Order() {

    }

    public Order(String orderDate, double total, double tienKhachDua, double tienTraKhach) {
        this.total = total;
        this.tienKhachDua = tienKhachDua;
        this.tienTraKhach = tienTraKhach;
    }
    public Order(String id, String orderDate, double total, double tienKhachDua, double tienTraKhach) {
        this.id = id;
        this.orderDate = orderDate;
        this.total = total;
        this.tienKhachDua = tienKhachDua;
        this.tienTraKhach = tienTraKhach;
    }

    public Order(String id, String orderDate, double total) {
        this.id = id;
        this.total = total;
        this.orderDate = orderDate;
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
     * @return the orderDate
     */
    public String getOrderDate() {
        return orderDate;
    }

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return the total
     */
    public Double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * @return the tienKhachDua
     */
    public Double getTienKhachDua() {
        return tienKhachDua;
    }

    /**
     * @param tienKhachDua the tienKhachDua to set
     */
    public void setTienKhachDua(Double tienKhachDua) {
        this.tienKhachDua = tienKhachDua;
    }

    /**
     * @return the tienTraKhach
     */
    public Double getTienTraKhach() {
        return tienTraKhach;
    }

    /**
     * @param tienTraKhach the tienTraKhach to set
     */
    public void setTienTraKhach(Double tienTraKhach) {
        this.tienTraKhach = tienTraKhach;
    }

}
