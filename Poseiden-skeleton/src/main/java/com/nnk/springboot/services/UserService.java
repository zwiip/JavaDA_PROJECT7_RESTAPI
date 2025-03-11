package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {
    /* VARIABLES*/
    private final UserRepository userRepository;

    private final Logger logger = Logger.getLogger(UserService.class.getName());

    /* CONSTRUCTOR */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* METHODS */// TODO : gestion des erreurs
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Integer id) {
        return userRepository.findById(id);
    }

    public User findUserByUsername(String username) { return userRepository.findByUsername(username); }

    public void saveNewUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void updateUser(User user, Integer id) {
        Optional<User> userToUpdate = getUser(id);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (userToUpdate.isPresent()) {
            userToUpdate.get().setFullname(user.getFullname());
            userToUpdate.get().setUsername(user.getUsername());
            userToUpdate.get().setPassword(encoder.encode(user.getPassword()));
            userToUpdate.get().setRole(user.getRole());

            userRepository.save(userToUpdate.get());
        } else {
            logger.warning("User not found");
        }
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}