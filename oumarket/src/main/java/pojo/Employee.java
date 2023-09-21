/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojo;
/**
 *
 * @author Khoa Tran
 */
public class Employee {
  
    private int id;
    private String fullname;
    
    public Employee (String name) {
        this.fullname = name;
    }
    
    public Employee (int id, String name) {
        this.id = id;
        this.fullname = name;
    }
    
    public Employee() {
        
    }

    /**
     * @return the name
     */
    public String getName() {
        return fullname;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.fullname = name;
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
}

