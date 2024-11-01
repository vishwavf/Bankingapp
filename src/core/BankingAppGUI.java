package core;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class BankingAppGUI extends JFrame {
    private AccountManager accountManager;
    private JTextField usernameField, signupUsernameField, signupBalanceField, depositField, withdrawField;
    private JPasswordField passwordField, signupPasswordField;
    private JTable transactionTable;
    private DefaultTableModel transactionTableModel;
    private JPanel loginPanel, mainPanel, signupPanel;
    private NumberFormat currencyFormat;

    public BankingAppGUI() {
        setTitle("Banking App");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        accountManager = new AccountManager();
        currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        setLayout(new CardLayout());

        loginPanel = createBackgroundPanel(createLoginPanel());
        signupPanel = createBackgroundPanel(createSignupPanel());
        mainPanel = createMainPanel();

        add(loginPanel, "Login");
        add(signupPanel, "Signup");
        add(mainPanel, "Main");

        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Login");}


    private void setCustomStyles() {

        Font commonFont = new Font("Segoe UI", Font.PLAIN, 16);
    }
    private JPanel createBackgroundPanel(JPanel panel) {
        JPanel backgroundPanel = new JPanel() {
          @Override
         protected void paintComponent(Graphics g) {
               super.paintComponent(g);
                Image img = new ImageIcon("assests/bg.jpg").getImage();
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                }
            };
            backgroundPanel.setLayout(new GridBagLayout());
            backgroundPanel.add(panel); // Add the main panel on top of the background
            return backgroundPanel;
        }
    private JPanel createLoginPanel() {
    JPanel panel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Define gradient colors for a grey-to-white look
            Color color1 = Color.LIGHT_GRAY;
            Color color2 = Color.WHITE;
            GradientPaint gradientPaint = new GradientPaint(0, 0, color1, 0, getHeight(), color2);

            g2d.setPaint(gradientPaint);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25); // Rounded corners
        }
    };
    panel.setOpaque(false); // Ensures gradient is visible
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Title Label
    JLabel titleLabel = new JLabel("Welcome to Your Bank", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
    titleLabel.setForeground(new Color(0, 0, 102)); // Dark blue text for a modern look
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    panel.add(titleLabel, gbc);

    // Username Field
    usernameField = createModernTextField("Username");
    gbc.gridy = 1;
    gbc.gridwidth = 2;
    panel.add(usernameField, gbc);

    // Password Field
    passwordField = createModernPasswordField1("Password");
    gbc.gridy = 2;
    panel.add(passwordField, gbc);

    // Login Button
    JButton loginButton = createRoundedButton("Login");
    loginButton.addActionListener(e -> {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (accountManager.login(username, password)) {
            transactionTableModel.setRowCount(0);
            loadTransactionHistory();
            JOptionPane.showMessageDialog(panel, "Login Successful! Welcome, " + username + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
            ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Main");
        } else {
            JOptionPane.showMessageDialog(panel, "Login Failed. Please check your credentials.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });
    gbc.gridy = 3;
    panel.add(loginButton, gbc);

    // Sign Up Button
    JButton signupSwitchButton = createRoundedButton("Sign Up");
    signupSwitchButton.addActionListener(e -> ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Signup"));
    gbc.gridy = 4;
    panel.add(signupSwitchButton, gbc);

    return panel;
}

// Create a modern, rounded button with hover effect
    private JButton createRoundedButton(String text) {
    JButton button = new JButton(text) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            super.paintComponent(g);
        }
    };
    button.setFont(new Font("Arial", Font.BOLD, 16));
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setBorderPainted(false);
    button.setOpaque(false);
    button.setContentAreaFilled(false);
    button.setBackground(new Color(0, 123, 255));

    button.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
		public void mouseEntered(java.awt.event.MouseEvent evt) {
            button.setBackground(new Color(0, 105, 217));
        }

        @Override
		public void mouseExited(java.awt.event.MouseEvent evt) {
            button.setBackground(new Color(0, 123, 255));
        }
    });

    return button;
}

// Modern styled text field with placeholder text
private JTextField createModernTextField(String placeholderText) {
    JTextField textField = new JTextField(15);
    textField.setFont(new Font("Arial", Font.PLAIN, 16));
    textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    textField.setText(placeholderText);
    textField.setForeground(Color.GRAY);
    textField.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            if (textField.getText().equals(placeholderText)) {
                textField.setText("");
                textField.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (textField.getText().isEmpty()) {
                textField.setText(placeholderText);
                textField.setForeground(Color.GRAY);
            }
        }
    });
    return textField;
}

// Modern styled password field with placeholder text
private JPasswordField createModernPasswordField1(String placeholderText) {
    JPasswordField passwordField = new JPasswordField(15);
    passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
    passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    passwordField.setText(placeholderText);
    passwordField.setForeground(Color.GRAY);
    passwordField.setEchoChar((char) 0); // No masking to show placeholder
    passwordField.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            if (String.valueOf(passwordField.getPassword()).equals(placeholderText)) {
                passwordField.setText("");
                passwordField.setForeground(Color.BLACK);
                passwordField.setEchoChar('\u2022'); // Set bullet character
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                passwordField.setForeground(Color.GRAY);
                passwordField.setText(placeholderText);
                passwordField.setEchoChar((char) 0); // No masking to show placeholder
            }
        }
    });
    return passwordField;
}

private JPanel createSignupPanel() {
    JPanel panel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Gradient background (grey to white)
            Color color1 = Color.LIGHT_GRAY;
            Color color2 = Color.WHITE;
            GradientPaint gradientPaint = new GradientPaint(0, 0, color1, 0, getHeight(), color2);

            g2d.setPaint(gradientPaint);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25); // Rounded corners
        }
    };

    panel.setOpaque(false); // Ensures gradient is visible
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Title Label
    JLabel titleLabel = new JLabel("Create Your Account", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
    titleLabel.setForeground(new Color(0, 0, 102));
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    panel.add(titleLabel, gbc);

    // Username Field
    signupUsernameField = createModernTextField("Username (min 4 characters)");
    gbc.gridy = 1;
    panel.add(signupUsernameField, gbc);

    // Password Field
    signupPasswordField = createModernPasswordField1("Password (min 6 characters)");
    gbc.gridy = 2;
    panel.add(signupPasswordField, gbc);

    // Initial Balance Field
    signupBalanceField = createModernTextField("Initial Balance (must be >= 0)");
    gbc.gridy = 3;
    panel.add(signupBalanceField, gbc);

    // Signup Button
    JButton signupButton = createRoundedButton("Sign Up");
    signupButton.addActionListener(e -> {
        String username = signupUsernameField.getText().trim();
        String password = new String(signupPasswordField.getPassword()).trim();
        String balanceText = signupBalanceField.getText().trim();

        if (username.length() < 4) {
            JOptionPane.showMessageDialog(panel, "Username must be at least 4 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(panel, "Password must be at least 6 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double initialBalance;
        try {
            initialBalance = Double.parseDouble(balanceText);
            if (initialBalance < 0) {
                throw new NumberFormatException("Negative amount");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Invalid balance amount. Please enter a non-negative number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (accountManager.signup(username, password, initialBalance)) {
            JOptionPane.showMessageDialog(panel, "Signup Successful! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
            ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Login");
        } else {
            JOptionPane.showMessageDialog(panel, "Username already exists. Please choose another.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });
    gbc.gridy = 4;
    panel.add(signupButton, gbc);

    // Switch to Login Button
    JButton loginSwitchButton = createRoundedButton("Already have an account? Login");
    loginSwitchButton.addActionListener(e -> ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Login"));
    gbc.gridy = 5;
    panel.add(loginSwitchButton, gbc);

    return panel;
}

private JPanel createMainPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());

    // Balance Label at the top
    JLabel balanceLabel = new JLabel("Balance: $0.00", SwingConstants.CENTER);
    balanceLabel.setFont(new Font("Arial", Font.BOLD, 24));
    balanceLabel.setForeground(new Color(34, 139, 34)); // Dark green color for balance
    panel.add(balanceLabel, BorderLayout.NORTH);

    // Customize the transaction table
    transactionTableModel = new DefaultTableModel(new String[]{"Date", "Type", "Amount"}, 0);
    transactionTable = new JTable(transactionTableModel);
    transactionTable.setFillsViewportHeight(true);
    transactionTable.setBackground(new Color(245, 245, 245)); // Light gray background for table
    transactionTable.setGridColor(Color.LIGHT_GRAY); // Light grid color
    transactionTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14)); // Custom font for headers
    transactionTable.getTableHeader().setBackground(new Color(230, 230, 230)); // Light header background
    transactionTable.setRowHeight(30); // Increase row height for readability

    transactionTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (isSelected) {
                c.setBackground(table.getSelectionBackground());
            } else {
                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 240, 240)); // Alternate row color
            }
            return c;
        }
    });

    JScrollPane scrollPane = new JScrollPane(transactionTable);
    panel.add(scrollPane, BorderLayout.CENTER);

    // Input panel for deposit and withdrawal
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new GridBagLayout());
    inputPanel.setBackground(new Color(200, 200, 200)); // Light background for input area
    inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
    ));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Deposit Field with rounded corners
    depositField = createModernTextField("Deposit Amount");
    gbc.gridx = 0;
    gbc.gridy = 0;
    inputPanel.add(depositField, gbc);

    // Deposit Button with rounded edges and a modern look
    JButton depositButton = createModernButton("Deposit");
    depositButton.setBackground(new Color(100, 149, 237)); // Cornflower blue background
    depositButton.setForeground(Color.WHITE);
    depositButton.setFocusPainted(false);
    depositButton.setFont(new Font("Arial", Font.BOLD, 14));
    depositButton.addActionListener(e -> {
        String amountText = depositField.getText().trim();
        if (amountText.isEmpty() || !amountText.matches("\\d+(\\.\\d{1,2})?")) {
            JOptionPane.showMessageDialog(panel, "Please enter a valid deposit amount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double amount = Double.parseDouble(amountText);
        accountManager.deposit(amount);
        updateBalanceLabel(balanceLabel);
        loadTransactionHistory();
        depositField.setText("");
    });
    gbc.gridx = 1;
    inputPanel.add(depositButton, gbc);

    // Withdraw Field with rounded corners
    withdrawField = createModernTextField("Withdraw Amount");
    gbc.gridx = 0;
    gbc.gridy = 1;
    inputPanel.add(withdrawField, gbc);

    // Withdraw Button with a modern look
    JButton withdrawButton = createModernButton1("Withdraw");
    withdrawButton.setBackground(new Color(178, 34, 34)); // Firebrick red background for withdraw
    withdrawButton.setForeground(Color.WHITE);
    withdrawButton.setFocusPainted(false);
    withdrawButton.setFont(new Font("Arial", Font.BOLD, 14));
    withdrawButton.addActionListener(e -> {
        String amountText = withdrawField.getText().trim();
        if (amountText.isEmpty() || !amountText.matches("\\d+(\\.\\d{1,2})?")) {
            JOptionPane.showMessageDialog(panel, "Please enter a valid withdrawal amount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double amount = Double.parseDouble(amountText);
        if (accountManager.withdraw(amount)) {
            updateBalanceLabel(balanceLabel);
            loadTransactionHistory();
            withdrawField.setText("");
        } else {
            JOptionPane.showMessageDialog(panel, "Insufficient funds for withdrawal.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });
    gbc.gridx = 1;
    inputPanel.add(withdrawButton, gbc);

    JButton logoutButton = createModernButton("Logout");
    logoutButton.setBackground(new Color(0, 123, 255)); // Bootstrap primary color
    logoutButton.setForeground(Color.WHITE);
    logoutButton.setFocusPainted(false);
    logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
    logoutButton.addActionListener(e -> {
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Login");
        usernameField.setText(""); // Clear the username field
        passwordField.setText(""); // Clear the password field
    });
    gbc.gridx = 1;
    gbc.gridy = 2; // Position it below the Withdraw button
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(10, 10, 10, 10);
    inputPanel.add(logoutButton, gbc);


    panel.add(inputPanel, BorderLayout.SOUTH);
    return panel;
}

    private void updateBalanceLabel(JLabel balanceLabel) {
        balanceLabel.setText("Balance: " + currencyFormat.format(accountManager.getBalance()));
    }

    private void loadTransactionHistory() {
        List<Transaction> transactions = accountManager.getTransactionHistory();
        transactionTableModel.setRowCount(0); // Clear existing data
        for (Transaction transaction : transactions) {
            transactionTableModel.addRow(new Object[]{
                    transaction.getDate(),
                    transaction.getType(),
                    currencyFormat.format(transaction.getAmount())
            });
        }
    }


    private JPasswordField createModernPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setPreferredSize(new Dimension(200, 30));
        addPlaceholderFocusListener(passwordField, placeholder);
        return passwordField;
    }

    private JButton createModernButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 123, 255)); // Bootstrap primary color
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(150, 40));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
			public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 105, 217)); // Darker blue on hover
            }

            @Override
			public void mouseExited(java.awt.event.MouseEvent evt) {

				button.setBackground(new Color(0, 123, 255)); // Original color
            }
        });
        return button;
    }

    private JButton createModernButton1(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 123, 255)); // Bootstrap primary color
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(150, 40));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
			public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(139, 0, 0)); // Darker blue on hover
            }

            @Override
			public void mouseExited(java.awt.event.MouseEvent evt) {

				button.setBackground(new Color(178, 34, 34)); // Original color
            }
        });
        return button;
    }

    private void addPlaceholderFocusListener(JTextField textField, String placeholder) {
        textField.setForeground(Color.LIGHT_GRAY);
        textField.setText(placeholder);
        textField.addFocusListener(new FocusAdapter() {
            @Override
			public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
			public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.LIGHT_GRAY);
                    textField.setText(placeholder);
                }
            }
        });
    }

    private void addPlaceholderFocusListener(JPasswordField passwordField, String placeholder) {
        passwordField.setForeground(Color.LIGHT_GRAY);
        passwordField.setText(placeholder);
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
			public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                }
            }

            @Override
			public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setForeground(Color.LIGHT_GRAY);
                    passwordField.setText(placeholder);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BankingAppGUI().setVisible(true);
        });
    }
}