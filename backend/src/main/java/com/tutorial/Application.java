package com.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class that starts our Spring Boot backend server
 * This is the entry point of our Java application
 */
@SpringBootApplication // This annotation enables auto-configuration and component scanning
public class Application {
    
    /**
     * Main method that runs when we start the application
     * @param args command line arguments (not used in this simple app)
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("🚀 Backend server is running on http://localhost:8080");
    }
}
