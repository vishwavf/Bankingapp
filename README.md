# BankingApp

BankingApp is a simple Java-based console application that simulates basic banking operations, including user sign-up, login, deposit, withdrawal, and transaction history tracking. This project is designed to demonstrate core Java programming skills, file handling, and basic OOP principles.

## Features

- **User Sign-up and Login:** Allows new users to register with a username, password, and initial balance.
- **Secure Login:** Validates credentials to ensure only registered users can access their accounts.
- **Deposit & Withdraw:** Users can deposit or withdraw funds, with updated balances.
- **Transaction History:** Maintains a record of each user's deposits and withdrawals.
- **Data Persistence:** User information, including balance, is saved to a file and loaded on subsequent logins.
- **User Interface:** Features a modern and intuitive UI for seamless user interaction.

## Tech Stack

- **Java:** Core programming language
- **Swing:** For building the user interface
- **File Handling:** For storing and retrieving user credentials and transaction history
- **Object-Oriented Programming (OOP):** Utilizes classes and objects for modularity and reusability

## Getting Started

### Prerequisites

- **Java JDK:** Version 8 or higher
- **IDE (Optional):** Any IDE that supports Java (e.g., IntelliJ IDEA, Eclipse)

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/vishwavf/BankingApp.git
   ```

2. **Navigate to the project directory:**
   
   ```bash
   cd BankingApp
   ```

3. **Compile the project:**

   Use an IDE or run the following command to compile the Java files:

   ```bash
   javac -d bin -sourcepath src src/App/Main.java
   ```
4. **Run the application:**

   ```bash
   java -cp bin App.Main
   ```

# Banking Application

## File Structure
- **src/**: Contains the Java source files.
    - **core**: Handles business logic for managing users and transactions.
    - **core**: Defines the User and Transaction data models.
    - **App**: The main class to run the application.
- **assests/**: Stores `credentials.txt`, which holds user login data.
- **bin/**: Contains the compiled bytecode files.

## Usage

### Sign Up
1. If you’re a new user, choose the "Sign Up" option.
2. Enter a username, password, and an initial balance.

### Login
1. For existing users, select "Login."
2. Enter your username and password.

### Deposit/Withdraw
1. After logging in, choose to deposit or withdraw funds.
2. The application will update and display your balance after each transaction.

### Logout
- Log out to securely save your data.

## User Interface
The application features a modern and intuitive user interface built using Java Swing. It is designed to be user-friendly and visually appealing, ensuring that users can navigate through the application effortlessly.



## Code Overview

### Main Components
- **AccountManager**: Manages user sign-up, login, balance updates, and data persistence.
- **User**: Represents a user with credentials, balance, and transaction history.
- **Transaction**: Captures each deposit or withdrawal for history tracking.

## Data Persistence
User credentials and balances are stored in `assests/credentials.txt` and updated with each transaction.

## License
This project is licensed under the MIT License - see the [LICENSE](./LICENSE) file for details.

## Contributing
Feel free to submit issues and pull requests to improve the application. Your contributions are welcome!

## Contact
For any questions or support, please contact [vishwafernando.vf@gmail.com] or open an issue in this repository.  
