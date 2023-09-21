/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojo;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Khoa Tran
 */
public class Promotion {
    private int id;
    private String name;
    private double value;
    private Date timeStart;
    private Date timeEnd;
    
    public Promotion () {
        
    }
    
    public Promotion (String name, double value) {
        this.value = value;
        this.name = name;
    }
    
    public Promotion (String name, double value, Date start, Date end) {
        this.timeStart = start;
        this.timeEnd = end;
        this.name = name;
        this.value = value;
    }
    
    public Promotion (int id, String name, double value, Date start, Date end) {
        this.id = id;
        this.timeStart = start;
        this.timeEnd = end;
        this.name = name;
        this.value = value;
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
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the timeStart
     */
    public Date getTimeStart() {
        return timeStart;
    }

    /**
     * @param timeStart the timeStart to set
     */
    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    /**
     * @return the timeEnd
     */
    public Date getTimeEnd() {
        return timeEnd;
    }

    /**
     * @param timeEnd the timeEnd to set
     */
    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    @Override
    public String toString() {
        return this.getName();
    }
    
    
}
