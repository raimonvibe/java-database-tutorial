package com.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller that handles HTTP requests for User operations
 * This class defines the API endpoints that our frontend will call
 */
@RestController // This annotation makes this class a REST controller
@RequestMapping("/api/users") // All endpoints in this class start with /api/users
@CrossOrigin(origins = "*") // Allow requests from any origin (for development)
public class UserController {
    
    /**
     * Inject the UserRepository to perform database operations
     */
    @Autowired // Spring automatically injects the repository
    private UserRepository userRepository;
    
    /**
     * GET endpoint to retrieve all users
     * URL: GET /api/users
     * @return list of all users in the database
     */
    @GetMapping
    public List<User> getAllUsers() {
        System.out.println("📋 Getting all users from database");
        return userRepository.findAll();
    }
    
    /**
     * POST endpoint to create a new user
     * URL: POST /api/users
     * @param user the user data sent in the request body
     * @return the created user with generated ID
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("➕ Creating new user: " + user.getName());
        
        if (userRepository.findByEmail(user.getEmail()) != null) {
            System.out.println("❌ Email already exists: " + user.getEmail());
            return ResponseEntity.badRequest().build();
        }
        
        User savedUser = userRepository.save(user);
        System.out.println("✅ User created successfully with ID: " + savedUser.getId());
        
        return ResponseEntity.ok(savedUser);
    }
    
    /**
     * GET endpoint to retrieve a specific user by ID
     * URL: GET /api/users/{id}
     * @param id the user ID from the URL path
     * @return the user if found, 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        System.out.println("🔍 Looking for user with ID: " + id);
        
        return userRepository.findById(id)
                .map(user -> {
                    System.out.println("✅ Found user: " + user.getName());
                    return ResponseEntity.ok(user);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * DELETE endpoint to remove a user by ID
     * URL: DELETE /api/users/{id}
     * @param id the user ID to delete
     * @return success or not found response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        System.out.println("🗑️ Attempting to delete user with ID: " + id);
        
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    System.out.println("✅ User deleted successfully: " + user.getName());
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
