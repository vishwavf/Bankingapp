package core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL
    }

    private final TransactionType type;
    private final double amount;
    private final LocalDateTime date;

    public Transaction(TransactionType type, double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non-negative.");
        }
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.now(); // Sets the transaction date to current time
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        // Format the date for display
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(dateFormat);
    }

    @Override
    public String toString() {
        return getDate() + " - " + type + ": $" + amount;
    }
}
