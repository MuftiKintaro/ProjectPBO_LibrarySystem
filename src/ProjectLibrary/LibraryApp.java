package ProjectLibrary;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LibraryApp extends Application {

    // Dummy users
    private final Map<String, String> userPasswords = new HashMap<>();
    private final Map<String, String> userRoles = new HashMap<>();

    private void initDummyUsers() {
        userPasswords.put("oke", "admin123");
        userRoles.put("admin@example.com", "admin");

        userPasswords.put("oyi", "user123");
        userRoles.put("user@example.com", "user");
    }

    @Override
    public void start(Stage primaryStage) {
        initDummyUsers();

        // ===== Background image =====
        Image bgImage = new Image((getClass().getResource("/assets/IMG_1204.png").toExternalForm())); // Ganti dengan path yang valid
        ImageView bgView = new ImageView(bgImage);
        bgView.setPreserveRatio(false);
        bgView.setSmooth(true);
        bgView.setCache(true);

        // ===== Logo =====
        Image logoImage = new Image((getClass().getResource("/assets/Logo_umm.png").toExternalForm())); // Ganti path sesuai logo Anda
        ImageView logoView = new ImageView(logoImage);
        logoView.setFitHeight(200);
        logoView.setPreserveRatio(true);

        // ===== Login form =====
        Label titleLabel = new Label("Selamat Datang Di Perpustakaan UMM");
        titleLabel.setFont(new Font("Segoe UI", 24));
        titleLabel.setTextFill(Color.WHITE);

        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setFont(Font.font(12));
        errorLabel.setVisible(false);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(240);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(240);

        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(240);
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 8;");

        Button forgotButton = new Button("Lupa Password?");
        forgotButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-underline: true;");
        forgotButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reset Password");
            alert.setHeaderText(null);
            alert.setContentText("Silakan hubungi admin untuk reset password.");
            alert.showAndWait();
        });

        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Username dan password tidak boleh kosong.");
                errorLabel.setVisible(true);
            } else if (!userPasswords.containsKey(username) || !userPasswords.get(username).equals(password)) {
                errorLabel.setText("Username atau password salah.");
                errorLabel.setVisible(true);
            } else {
                errorLabel.setVisible(false);
                // Pindah ke dashboard
                // ✅ Tambahkan ini:
                String role = userRoles.get(username);  // Ambil role berdasarkan username
                User user = new User(username, role);  // Buat objek user

                // ✅ Kirim user ke DashboardUI
                DashboardUI.setUser(user); // <<< INI BAGIAN YANG WAJIB ADA

                // ✅ Buka dashboard
                try {
                    new DashboardUI().start(primaryStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        VBox loginBox = new VBox(10, titleLabel, errorLabel, usernameField, passwordField, loginButton, forgotButton);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setMaxWidth(500);
        loginBox.setPadding(new Insets(30));
        loginBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-background-radius: 20; ");

        VBox content = new VBox(20, logoView, loginBox);
        content.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(bgView, content);

        Scene scene = new Scene(root, 1280, 720);
        bgView.fitWidthProperty().bind(scene.widthProperty());
        bgView.fitHeightProperty().bind(scene.heightProperty());

        primaryStage.setTitle("Sistem Perpustakaan UMM");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
