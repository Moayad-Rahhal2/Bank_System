package com.banksystem.service;

import com.banksystem.dao.UserDAO;
import com.banksystem.entity.User;
import com.banksystem.util.PasswordHasher;

import java.util.UUID;

public class AuthService {

    private final UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public boolean registerUser(String firstName, String lastName, String email, String phone, String plainPassword) {
        if (userDAO.getUserByEmail(email) != null) {
            System.out.println("A user with this email already exists.");
            return false;
        }

        String salt = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
        String hashedPassword = PasswordHasher.hashPassword(plainPassword, salt);

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setSalt(salt);
        user.setPasswordHash(hashedPassword);

        userDAO.createUser(user);
        return true;
    }

    public User authenticateUser(String email, String plainPassword) {
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            System.out.println("No user found with this email.");
            return null;
        }

        String hashedInput = PasswordHasher.hashPassword(plainPassword, user.getSalt());
        if (hashedInput.equals(user.getPasswordHash())) {
            System.out.println("Login successful.");
            return user;
        } else {
            System.out.println("Incorrect password.");
            return null;
        }
    }
}
