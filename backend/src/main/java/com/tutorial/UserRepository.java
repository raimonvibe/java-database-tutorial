package com.tutorial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for User entity
 * This interface provides basic CRUD operations for User objects
 * Spring Data JPA automatically implements this interface
 */
@Repository // This annotation marks this as a repository component
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Spring Data JPA automatically provides these methods:
     * - save(User user) - saves a user to database
     * - findAll() - gets all users from database
     * - findById(Long id) - finds user by ID
     * - deleteById(Long id) - deletes user by ID
     * - count() - counts total users
     */
    
    /**
     * Custom method to find user by email
     * Spring Data JPA automatically implements this based on method name
     * @param email the email to search for
     * @return User object if found, null otherwise
     */
    User findByEmail(String email);
}
