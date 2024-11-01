package core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password; // Store plain password
    private double balance;
    private List<Transaction> transactionHistory;

    public User(String username, String password, double initialBalance) {
        if (username == null || username.length() < 3) {
            throw new IllegalArgumentException("Username must be at least 3 characters long.");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long.");
        }

        this.username = username;
        this.password = password; // Store plain password
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password); // Compare with plain password
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        this.balance = balance;
    }

    // Add a transaction to the user's history
    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    // Get transaction history as a list
    public List<Transaction> getTransactionHistory() {
        return transactionHistory; // Return the List directly
    }

    public String getPassword() {
        return password; // You might want to change this to return null or a secure version
    }
}
