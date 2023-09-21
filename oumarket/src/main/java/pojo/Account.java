/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojo;

/**
 *
 * @author ASUS
 */
public class Account {

    private String username;
    private String password;
    private String branchAddress;
    private String role;

    public Account(String username, String password, String branchAddress, String role) {
        this.username = username;
        this.password = password;
        this.branchAddress = branchAddress;
        this.role = role;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;

    }

    /**
     *
     * @return the branchAddress
     */

    public String
            getBranchAddress() {

        return branchAddress;
    }

    /**
     *
     * @param branchAddress the branchAddress to set
     */
    public void
            setBranchAddress(String branchAddress) {

        this.branchAddress = branchAddress;
    }

    /**
     *
     * @return the role
     */
    public String
            getRole() {

        return role;
    }

    /**
     *
     * @param role the role to set
     */
    public void
            setRole(String role) {

        this.role = role;
    }
}

