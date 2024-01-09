import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private JFrame frame;
    private JTabbedPane tabbedPane;
    private TennisPlayerManager playerManager;
    private OfficialsManager moderatorManager;
    private ReportGenerator reportGenerator;

    private JTextField playerNameField;
    private JTextField playerEmailField;
    private JTextField playerDOBField;
    private JComboBox<String> categoryComboBox;

    private JTextField moderatorUsernameField;
    private JPasswordField moderatorPasswordField;
    private JTextField playerIDField;
    private JTextField set01Field;
    private JTextField set02Field;
    private JTextField set03Field;
    private JTextField winField;

    private JTextField reportsPlayerIDField;

    private boolean isModeratorLoggedIn = false;

    public GUI() {
        this.frame = new JFrame("Tennis Tournament Management System");
        this.tabbedPane = new JTabbedPane();
        this.playerManager = new TennisPlayerManager();
        this.moderatorManager = new OfficialsManager();
        this.reportGenerator = new ReportGenerator();

        initializeGUI();
    }

    private void initializeGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        // Create tabs
        createPlayerRegistrationTab();
        createModeratorTab();
        createReportsTab();

        frame.getContentPane().add(tabbedPane);
        frame.setVisible(true);
    }

    private void createPlayerRegistrationTab() {
        JPanel playerRegistrationPanel = new JPanel();
        playerRegistrationPanel.setLayout(new GridLayout(5, 2));

        JLabel nameLabel = new JLabel("Name:");
        playerNameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        playerEmailField = new JTextField();
        JLabel dobLabel = new JLabel("Date of Birth:");
        playerDOBField = new JTextField();
        JLabel categoryLabel = new JLabel("Category:");

        String[] categories = {"Beginner", "Intermediate", "National Championship"};
        categoryComboBox = new JComboBox<>(categories);

        JButton registerButton = new JButton("Register");      
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerPlayer();
            }
        });

        playerRegistrationPanel.add(nameLabel);
        playerRegistrationPanel.add(playerNameField);
        playerRegistrationPanel.add(emailLabel);
        playerRegistrationPanel.add(playerEmailField);
        playerRegistrationPanel.add(dobLabel);
        playerRegistrationPanel.add(playerDOBField);
        playerRegistrationPanel.add(categoryLabel);
        playerRegistrationPanel.add(categoryComboBox);
        playerRegistrationPanel.add(registerButton);

        tabbedPane.addTab("Player Registration", playerRegistrationPanel);
    }

    private void createModeratorTab() {
        JPanel moderatorPanel = new JPanel();
        moderatorPanel.setLayout(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("Username:");
        moderatorUsernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        moderatorPasswordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginOfficials();
            }
        });

        JLabel playerIDLabel = new JLabel("Player ID:");
        playerIDField = new JTextField();
        JLabel attackLabel = new JLabel("Set01 Scores:");
        set01Field = new JTextField();
        JLabel defenseLabel = new JLabel("Set02 Scores:");
        set02Field = new JTextField();
        JLabel boostingLabel = new JLabel("Set03 Scores:");
        set03Field = new JTextField();
        JLabel blockingLabel = new JLabel("Win Scores:");
        winField = new JTextField();
        JButton submitScoreButton = new JButton("Submit Score");
        submitScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitScore();
            }
        });

        moderatorPanel.add(usernameLabel);
        moderatorPanel.add(moderatorUsernameField);
        moderatorPanel.add(passwordLabel);
        moderatorPanel.add(moderatorPasswordField);
        moderatorPanel.add(loginButton);
        moderatorPanel.add(playerIDLabel);
        moderatorPanel.add(playerIDField);
        moderatorPanel.add(attackLabel);
        moderatorPanel.add(set01Field);
        moderatorPanel.add(defenseLabel);
        moderatorPanel.add(set02Field);
        moderatorPanel.add(boostingLabel);
        moderatorPanel.add(set03Field);
        moderatorPanel.add(blockingLabel);
        moderatorPanel.add(winField);
        moderatorPanel.add(submitScoreButton);

        tabbedPane.addTab(" Officials", moderatorPanel);
    }

    private void createReportsTab() {
        JPanel reportsPanel = new JPanel();
        reportsPanel.setLayout(new GridLayout(2, 2));

        JLabel playerIDLabel = new JLabel("Player ID:");
        reportsPlayerIDField = new JTextField();
        JButton averageScoreButton = new JButton("Generate Average Score Report");
        averageScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateAverageScoreReport();
            }
        });

        JButton fullReportButton = new JButton("Generate Full Report");
        fullReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateFullReport();
            }
        });

        reportsPanel.add(playerIDLabel);
        reportsPanel.add(reportsPlayerIDField);
        reportsPanel.add(averageScoreButton);
        reportsPanel.add(fullReportButton);

        tabbedPane.addTab("Reports", reportsPanel);
    }

    private void registerPlayer() {
        try {
            String name = playerNameField.getText();
            String email = playerEmailField.getText();
            String dob = playerDOBField.getText();
            String category = (String) categoryComboBox.getSelectedItem();

            playerManager.registerPlayer(name, email, dob, category);
            JOptionPane.showMessageDialog(frame, "Player registered successfully!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, "Error registering player: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loginOfficials() {
        try {
            String username = moderatorUsernameField.getText();
            char[] passwordChars = moderatorPasswordField.getPassword();
            String password = new String(passwordChars);

            if (username.isEmpty() || password.isEmpty()) {
                throw new IllegalArgumentException("Username and Password are required fields.");
            }

            // Check moderator credentials
            if (moderatorManager.login(username, password)) {
                JOptionPane.showMessageDialog(frame, "Officials login successful");
                // Update login status
                isModeratorLoggedIn = true;
            } else {
                JOptionPane.showMessageDialog(frame, "Officials login failed");
            }

            moderatorUsernameField.setText("");
            moderatorPasswordField.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error logging in: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void submitScore() {
        try {
            // Check if the moderator is logged in
            if (!isModeratorLoggedIn) {
                throw new IllegalStateException("Officials' must log in before submitting scores.");
            }

            int playerID = Integer.parseInt(playerIDField.getText());
            int set01 = Integer.parseInt(set01Field.getText());
            int set02 = Integer.parseInt(set02Field.getText());
            int set03 = Integer.parseInt(set03Field.getText());
            int win = Integer.parseInt(winField.getText());

            // Perform input validation before submitting the score
            if (set01 < 0 || set02 < 0 || set03 < 0 || win < 0) {
                throw new IllegalArgumentException("Scores cannot be negative.");
            }

            moderatorManager.inputScores(playerID, set01, set02, set03, win);
            JOptionPane.showMessageDialog(frame, "Score submitted successfully!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            JOptionPane.showMessageDialog(frame, "Error submitting score: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateAverageScoreReport() throws NumberFormatException {
        try {
            int playerID = Integer.parseInt(reportsPlayerIDField.getText());
            String report = reportGenerator.generateAverageScoreReport(playerID);
            JOptionPane.showMessageDialog(frame, report);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, "Error generating average score report: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateFullReport() throws NumberFormatException {
        try {
            int playerID = Integer.parseInt(reportsPlayerIDField.getText());
            String report = reportGenerator.generateFullReport(playerID);
            JOptionPane.showMessageDialog(frame, report);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, "Error generating full report: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI());
    }
}
