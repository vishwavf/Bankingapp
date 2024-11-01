package core;

import core.Transaction;
import core.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountManager {
    private Map<String, User> userDatabase;
    private User currentUser;
    private final String credentialsFile = "assests/credentials.txt"; // File to store login credentials

    public AccountManager() {
        userDatabase = new HashMap<>();
        loadCredentials(); // Load existing credentials at startup
    }

    // Sign up a new user
    public boolean signup(String username, String password, double initialBalance) {
        if (userDatabase.containsKey(username)) {
            return false; // Username already exists
        }
        User newUser = new User(username, password, initialBalance);
        userDatabase.put(username, newUser);
        saveCredentials(username, password, initialBalance); // Save credentials to file
        return true;
    }

    // Log in an existing user
    public boolean login(String username, String password) {
        if (checkCredentials(username, password)) {
            currentUser = userDatabase.get(username); // Load currentUser from userDatabase
            return true; // Successful login
        }
        return false; // Login failed
    }

    // Deposit money into the current user's account
    public void deposit(double amount) {
        if (currentUser != null && amount > 0) {
            currentUser.setBalance(currentUser.getBalance() + amount);
            addTransaction(new Transaction(Transaction.TransactionType.DEPOSIT, amount));
            updateCredentials(); // Update file with new balance
        }
    }

    // Withdraw money from the current user's account
    public boolean withdraw(double amount) {
        if (currentUser != null && amount > 0 && amount <= currentUser.getBalance()) {
            currentUser.setBalance(currentUser.getBalance() - amount);
            addTransaction(new Transaction(Transaction.TransactionType.WITHDRAWAL, amount));
            updateCredentials(); // Update file with new balance
            return true;
        }
        return false; // Withdrawal failed
    }

    // Get the current user's balance
    public double getBalance() {
        return currentUser != null ? currentUser.getBalance() : 0.0;
    }

    // Add a transaction to the current user's history
    public void addTransaction(Transaction transaction) {
        if (currentUser != null) {
            currentUser.addTransaction(transaction);
        }
    }

    // Get transaction history for the current user
    public List<Transaction> getTransactionHistory() {
        return currentUser != null ? currentUser.getTransactionHistory() : List.of();
    }

    // Save current user's data to a file
    public void saveUserData() {
        if (currentUser != null) {
            String fileName = currentUser.getUsername() + ".txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                writer.write("----- User Data -----\n");
                writer.write("Username: " + currentUser.getUsername() + "\n");
                writer.write("Balance: $" + String.format("%.2f", currentUser.getBalance()) + "\n");
                writer.write("Transaction History:\n");

                List<Transaction> transactions = currentUser.getTransactionHistory();
                for (Transaction transaction : transactions) {
                    writer.write(transaction.toString() + "\n");
                }

                writer.write("-------------------------\n");
                writer.flush();
            } catch (IOException e) {
                System.err.println("Error saving user data to file: " + e.getMessage());
            }
        } else {
            System.err.println("No user is currently logged in.");
        }
    }

    // Logout the current user
    public void logout() {
        saveUserData();
        updateCredentials(); // Ensure the credentials file reflects the latest balance
        currentUser = null;
    }

    // Save the credentials to a file in a standardized format
    private void saveCredentials(String username, String password, double initialBalance) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(credentialsFile, true))) {
            writer.write(username + ":" + password + ":" + initialBalance + "\n"); 
        } catch (IOException e) {
            System.err.println("Error saving credentials: " + e.getMessage());
        }
    }

    // Load credentials from the file into the user database
    private void loadCredentials() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(credentialsFile));
            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    User user = new User(username, password, balance);
                    userDatabase.put(username, user);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading credentials: " + e.getMessage());
        }
    }

    // Check if the credentials are valid
    private boolean checkCredentials(String username, String password) {
        User user = userDatabase.get(username);
        return user != null && user.getPassword().equals(password);
    }

    // Update the credentials file with the current balance for all users
    private void updateCredentials() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(credentialsFile))) {
            for (User user : userDatabase.values()) {
                writer.write(user.getUsername() + ":" + user.getPassword() + ":" + user.getBalance() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error updating credentials: " + e.getMessage());
        }
    }
}
