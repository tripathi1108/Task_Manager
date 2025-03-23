package com.taskmanager.taskmanager.service;



import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create a new user
    public User createUser(User user) {
      User nayaUser = new User();
      nayaUser.setFirstName(user.getFirstName());
        nayaUser.setLastName(user.getLastName());
        nayaUser.setTimezone(user.getTimezone());
        nayaUser.setActive(user.isActive());
       return userRepository.save(nayaUser);

    }

    // Retrieve all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Retrieve a user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Update a user
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setTimezone(userDetails.getTimezone());
        user.setActive(userDetails.isActive());

        return userRepository.save(user);
    }

    // Delete a user by ID
    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "User Deleted Successfully: " + id;
    }
}