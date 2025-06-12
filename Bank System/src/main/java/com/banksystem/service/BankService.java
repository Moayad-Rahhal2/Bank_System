package com.banksystem.service;

import com.banksystem.dao.AccountDAO;
import com.banksystem.dao.TransactionDAO;
import com.banksystem.dao.UserDAO;
import com.banksystem.entity.Account;
import com.banksystem.entity.Transaction;
import com.banksystem.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class BankService {

    private final UserDAO userDAO;
    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;


    public BankService() {
        this.userDAO = new UserDAO();
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
    }


    // Open new account for a user
    public void openAccount(int userId, Account.AccountType accountType) {
        User user = userDAO.getUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        Account account = new Account();
        account.setUser(user);
        account.setAccountType(accountType);
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(BigDecimal.ZERO);

        accountDAO.createAccount(account);
        System.out.println("Account created: " + account.getAccountNumber());
    }

    // Deposit money into an account
    public void deposit(String accountNumber, BigDecimal amount, String description) {
        Account account = accountDAO.getAccountByNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        account.setBalance(account.getBalance().add(amount));
        accountDAO.updateAccount(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType(Transaction.TransactionType.DEPOSIT);
        transaction.setDescription(description);

        transactionDAO.saveTransaction(transaction);
        System.out.println("Deposit successful.");
    }

    // Withdraw money from an account
    public void withdraw(String accountNumber, BigDecimal amount, String description) {
        Account account = accountDAO.getAccountByNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        if (account.getBalance().compareTo(amount) < 0) {
            System.out.println("Insufficient balance.");
            return;
        }
        // to reduce the process
        if (account.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Insufficient balance.");
            return;
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountDAO.updateAccount(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType(Transaction.TransactionType.WITHDRAWAL);
        transaction.setDescription(description);

        transactionDAO.saveTransaction(transaction);
        System.out.println("Withdrawal successful.");
    }

    // Transfer between accounts
    public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description) {
        Account fromAccount = accountDAO.getAccountByNumber(fromAccountNumber);
        Account toAccount = accountDAO.getAccountByNumber(toAccountNumber);

        if (fromAccount == null || toAccount == null) {
            System.out.println("One or both accounts not found.");
            return;
        }

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            System.out.println("Insufficient balance.");
            return;
        }
        // to reduce the process
        if (fromAccount.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Insufficient balance.");
            return;
        }

        // Debit sender
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        accountDAO.updateAccount(fromAccount);

        // Credit receiver
        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountDAO.updateAccount(toAccount);

        // Transaction for sender
        Transaction debitTransaction = new Transaction();
        debitTransaction.setAccount(fromAccount);
        debitTransaction.setAmount(amount);
        debitTransaction.setType(Transaction.TransactionType.TRANSFER);
        debitTransaction.setDescription("Transfer to " + toAccountNumber);
        debitTransaction.setRelatedAccount(toAccount);
//        debitTransaction.setRelatedAccountId(toAccount.getAccountId());
        transactionDAO.saveTransaction(debitTransaction);

        // Transaction for receiver
        Transaction creditTransaction = new Transaction();
        creditTransaction.setAccount(toAccount);
        creditTransaction.setAmount(amount);
        creditTransaction.setType(Transaction.TransactionType.TRANSFER);
        creditTransaction.setDescription("Transfer from " + fromAccountNumber);
        creditTransaction.setRelatedAccount(fromAccount);

        transactionDAO.saveTransaction(creditTransaction);

        System.out.println("Transfer successful.");
    }



    // Generate random 12-digit account number
    private String generateAccountNumber() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }
    public List<Transaction> viewTransactions(String accountNumber) {
        Account account = accountDAO.getAccountByNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return List.of();  // return empty list instead of null
        }
        return transactionDAO.getTransactionsByAccountId(account.getAccountId());
    }
}
