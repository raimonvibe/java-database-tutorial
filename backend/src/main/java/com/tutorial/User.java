package com.tutorial;

import javax.persistence.*;

/**
 * User entity class that represents a user in our database
 * This class maps to a 'users' table in PostgreSQL
 */
@Entity // This annotation tells JPA that this class is a database entity
@Table(name = "users") // Specify the table name in the database
public class User {
    
    /**
     * Primary key field - unique identifier for each user
     */
    @Id // This field is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment the ID
    private Long id;
    
    /**
     * User's name field
     */
    @Column(name = "name", nullable = false) // Map to 'name' column, cannot be null
    private String name;
    
    /**
     * User's email field
     */
    @Column(name = "email", nullable = false, unique = true) // Email must be unique
    private String email;
    
    /**
     * Default constructor (required by JPA)
     */
    public User() {
    }
    
    /**
     * Constructor to create a new user with name and email
     * @param name the user's name
     * @param email the user's email
     */
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Override toString method for easy debugging
     * @return string representation of the user
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
