package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Service layer for the User Entities.
 * Provides methods for the CRUD operations (create, read, update, delete) on the User repository.
 */
@Service
public class UserService {

    /* VARIABLES*/
    private final UserRepository userRepository;
    private final Logger logger = Logger.getLogger(UserService.class.getName());

    /* CONSTRUCTOR */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* METHODS */

    /**
     * Retrieves a list of all User entities.
     * @return a list of User objects
     */
    public List<User> getUsers() {
        logger.fine("Fetching all users.");
        List<User> users = userRepository.findAll();
        logger.info("Found " + users.size() + " users.");

        return users;
    }

    /**
     * Retrieve a User entity by its ID.
     * @param id the ID of the User to retrieve.
     * @return an Optional containing the matching User or an empty Optional object.
     */
    public Optional<User> getUser(Integer id) {
        logger.fine("Fetching the user with the id: " + id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            logger.info("Found a user: " + user.get().getUsername());
        } else {
            logger.warning("No user found for the id: " + id);
        }
        return user;
    }

    /**
     * Retrieve a User entity by its username.
     * @param username String : the username of the User to retrieve.
     * @return a User object matching the username or Null.
     */
    // TODO : ajouter constraint unique
    public User findUserByUsername(String username) {
        logger.fine("Fetching the user for the username: " + username);
        User user = userRepository.findByUsername(username);
        if (user != null) {
            logger.info("User found: " + user.getUsername());
        } else {
            logger.warning("No user found with the username: " + username);
        }
        return user;
    }

    /**
     * Saves a User entity to the repository after checking the password and encrypting it.
     * @param user the User to save.
     */
    public void saveNewUser(User user) {
        logger.fine("Saving the user: " + user.getUsername());
        if (isPasswordCorrect(user.getPassword())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            logger.info("New user added: " + user.getUsername());
        }
    }

    /**
     * Update a User entity, retrieved thanks to its ID.
     * @param user a User object with new information.
     * @param id the id of the User to update.
     */
    public void updateUser(User user, Integer id) {
        logger.fine("Updating the user: " + user.getUsername());
        Optional<User> userToUpdate = getUser(id);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (userToUpdate.isPresent()) {
            logger.fine("One user found for the id: " + id + " with username: " + userToUpdate.get().getUsername());
            userToUpdate.get().setFullname(user.getFullname());
            userToUpdate.get().setUsername(user.getUsername());
            userToUpdate.get().setPassword(encoder.encode(user.getPassword()));
            userToUpdate.get().setRole(user.getRole());

            userRepository.save(userToUpdate.get());
            logger.info("User save: " + user.getUsername());
        } else {
            logger.warning("User not found: " + user.getUsername());
        }
    }

    /**
     * Deletes a User entity matching the given ID.
     * @param id Integer: the ID of the User to delete.
     */
    public void deleteUser(Integer id) {
        logger.fine("Deleting the user with the id: " + id);
        userRepository.deleteById(id);
        logger.info("User deleted: " + id);
    }

    /**
     * Helper method to check that the given password matches the requisite pattern.
     * @param password String : the password to check.
     * @return a boolean set to true if it passes the conditions.
     * @throws IllegalArgumentException if a pattern isn't correct.
     */
    private boolean isPasswordCorrect(String password) {
        logger.fine("Checking the password.");
        if (password.length() < 8) {
            throw new IllegalArgumentException("At least 8 characters");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("At leat one Uppercase");
        }
        if (!password.matches(".*[0-9].*")) {
            throw new IllegalArgumentException("At least one number");
        }
        if (!password.matches(".*[!@#$%^&*()\\-_=+\\[\\]{}|;:,.<>?].*")) {
            throw new IllegalArgumentException("At least one symbol among: !@#$%^&*()-_=+[]{}|;:,.<>?");
        }
        logger.fine("The password matches the pattern.");
        return true;
    }
}