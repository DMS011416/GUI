import javax.swing.*;
import java.awt.*;

// Abstract class for different screens
abstract class ScreenPanel extends JPanel {
    protected AuthenticationApp app;
    public ScreenPanel(AuthenticationApp app) {
        this.app = app;
    }
    abstract void display();
}

// ---------------- LOGIN PANEL ----------------
class LoginPanel extends ScreenPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton forgotPasswordButton;
    private JButton signUpButton;

    public LoginPanel(AuthenticationApp app) {
        super(app);
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Log In");
        forgotPasswordButton = new JButton("Forgot Password?");
        signUpButton = new JButton("Sign Up");

        setLayout(new GridLayout(4, 2, 5, 5));
        add(new JLabel("Username or Email:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("")); // Empty space
        add(loginButton);
        add(forgotPasswordButton);
        add(signUpButton);

        // Login logic
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            JOptionPane.showMessageDialog(app, "Logged in as: " + username);
        });

        // Navigate to Forgot Password
        forgotPasswordButton.addActionListener(e -> app.showScreen("ForgotPassword"));

        // Navigate to Sign Up
        signUpButton.addActionListener(e -> app.showScreen("SignUp"));
    }

    @Override
    public void display() {
        System.out.println("Login screen displayed");
    }
}

// ---------------- SIGNUP PANEL ----------------
class SignupPanel extends ScreenPanel {
    private JTextField emailField;
    private JTextField fullNameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton signUpButton;
    private JButton backButton;

    public SignupPanel(AuthenticationApp app) {
        super(app);
        emailField = new JTextField(20);
        fullNameField = new JTextField(20);
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        signUpButton = new JButton("Sign Up");
        backButton = new JButton("Back to Login");

        setLayout(new GridLayout(7, 2, 5, 5));
        add(new JLabel("Email or Phone:"));
        add(emailField);
        add(new JLabel("Full Name:"));
        add(fullNameField);
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("Confirm Password:"));
        add(confirmPasswordField);
        add(backButton);
        add(signUpButton);

        signUpButton.addActionListener(e -> {
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            if (password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(app, "Signed up successfully as: " + fullNameField.getText());
                app.showScreen("Login");
            } else {
                JOptionPane.showMessageDialog(app, "Passwords do not match!");
            }
        });

        backButton.addActionListener(e -> app.showScreen("Login"));
    }

    @Override
    public void display() {
        System.out.println("Sign Up screen displayed");
    }
}

// ---------------- FORGOT PASSWORD PANEL ----------------
class ForgotPasswordPanel extends ScreenPanel {
    private JTextField emailField;
    private JButton resetButton;
    private JButton backButton;

    public ForgotPasswordPanel(AuthenticationApp app) {
        super(app);
        emailField = new JTextField(20);
        resetButton = new JButton("Send Reset Link");
        backButton = new JButton("Back to Login");

        setLayout(new GridLayout(3, 2, 5, 5));
        add(new JLabel("Enter your Email:"));
        add(emailField);
        add(backButton);
        add(resetButton);

        resetButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(app, "Password reset link sent to " + emailField.getText());
            app.showScreen("Login");
        });

        backButton.addActionListener(e -> app.showScreen("Login"));
    }

    @Override
    public void display() {
        System.out.println("Forgot Password screen displayed");
    }
}

// ---------------- MAIN FRAME ----------------
public class AuthenticationApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public AuthenticationApp() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create and add panels
        cardPanel.add(new LoginPanel(this), "Login");
        cardPanel.add(new SignupPanel(this), "SignUp");
        cardPanel.add(new ForgotPasswordPanel(this), "ForgotPassword");

        add(cardPanel);

        // Start on login screen
        showScreen("Login");

        setTitle("Authentication App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // Method to switch screens
    public void showScreen(String name) {
        cardLayout.show(cardPanel, name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AuthenticationApp().setVisible(true));
    }
}
