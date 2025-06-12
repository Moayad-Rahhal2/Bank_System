package com.banksystem.service;

import com.banksystem.dao.AccountDAO;
import com.banksystem.dao.TransactionDAO;
import com.banksystem.dao.UserDAO;
import com.banksystem.entity.Account;
import com.banksystem.entity.Transaction;
import com.banksystem.entity.User;

import java.util.List;

public class AdminService {

    private final UserDAO userDAO = new UserDAO();
    private final AccountDAO accountDAO = new AccountDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();


    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public boolean userExists(int userId) {
        return userDAO.getUserById(userId) != null;
    }

    public boolean accountExistsByNumber(String accountNumber) {
        return accountDAO.getAccountByNumber(accountNumber) != null;
    }

    public void deleteUser(int userId) {
        userDAO.deleteUser(userId);
    }

//    public Account getAccountById(int accountId) {
//        return accountDAO.getAccountById(accountId);
//    }

    public Account getAccountByNumber(String accountNumber) {
        return accountDAO.getAccountByNumber(accountNumber);
    }


    public List<Account> getAccountsByUserId(int userId) {
        return accountDAO.getAccountsByUserId(userId);
    }

    public void deleteAccount(int accountId) {
        accountDAO.deleteAccount(accountId);
    }

    public List<Transaction> getAllTransactions() {
        return transactionDAO.getAllTransactions();
    }

    public Transaction getTransactionById(int transactionId) {
        return transactionDAO.getTransactionById(transactionId);
    }
}
