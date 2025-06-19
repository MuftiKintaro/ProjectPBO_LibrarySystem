package ProjectLibrary;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class LibraryApp extends Application {

    private Stage primaryStage;
    private Scene loginScene, adminScene, userScene, registrationScene;

    // Data storage
    private Map<String, User> users = new HashMap<>();
    private List<Book> books = new ArrayList<>();
    private List<BorrowRecord> borrowRecords = new ArrayList<>();

    // Logged user
    private User currentUser;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        initData(); // Init sample users and books

        primaryStage.setTitle("Perpustakaan");

        initLoginScene();
        initAdminScene();
        initUserScene();
        initRegistrationScene();

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void initData() {
        // Sample users
        users.put("admin", new User("admin", "admin123", true));
        users.put("user", new User("user", "user123", false));

        // Sample books
        books.add(new Book("Java Programming"));
        books.add(new Book("Data Structures"));
        books.add(new Book("Design Patterns"));
        books.add(new Book("Sejarah 10 November"));
        books.add(new Book("40 Hari Di Malam Kubur"));
        books.add(new Book("Logika Komputasi"));
        books.add(new Book("G30S PKI"));
    }

    // ==================== REGISTRATION SCENE ====================
    private void initRegistrationScene() {
        VBox registrationBox = new VBox(20);
        registrationBox.setAlignment(Pos.CENTER);
        registrationBox.setPadding(new Insets(30));
        registrationBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-background-radius: 20;");

        Label titleLabel = new Label("Registrasi Anggota");
        titleLabel.setFont(new Font("Segoe UI", 24));

        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setVisible(false);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(240);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(240);

        Button registerBtn = new Button("Daftar");
        registerBtn.setPrefWidth(240);
        registerBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        registerBtn.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Username dan password tidak boleh kosong");
                errorLabel.setVisible(true);
                return;
            }

            if (users.containsKey(username)) {
                errorLabel.setText("Username sudah digunakan");
                errorLabel.setVisible(true);
                return;
            }

            users.put(username, new User(username, password, false));
            errorLabel.setText("Registrasi berhasil! Silakan login");
            errorLabel.setTextFill(Color.GREEN);
            errorLabel.setVisible(true);

            usernameField.clear();
            passwordField.clear();
        });

        Button backToLoginBtn = new Button("Kembali ke Login");
        backToLoginBtn.setStyle("-fx-text-fill: #007bff;");
        backToLoginBtn.setOnAction(e -> {
            errorLabel.setVisible(false);
            primaryStage.setScene(loginScene);
        });

        registrationBox.getChildren().addAll(
                titleLabel,
                errorLabel,
                usernameField,
                passwordField,
                registerBtn,
                backToLoginBtn
        );

        registrationScene = new Scene(registrationBox, 400, 400);
    }


    // ==================== LOGIN SCENE ====================
    private void initLoginScene() {
        // ==================== BG =======================
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

        //====== Username dan Password =======
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

        Button loginBtn = new Button("Login");
        Label messageLabel = new Label();
        loginBtn.setPrefWidth(240);
        loginBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 8;");

        Button registerBtn = new Button("Daftar Anggota Baru");
        registerBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-underline: true;");
        registerBtn.setOnAction(e -> {
            errorLabel.setVisible(false);
            primaryStage.setScene(registrationScene);
        });


        Button forgotButton = new Button("Lupa Password?");
        forgotButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-underline: true;");
        forgotButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reset Password");
            alert.setHeaderText(null);
            alert.setContentText("Silakan hubungi admin untuk reset password.");
            alert.showAndWait();
        });


        //===== Login Button Logic ====
        loginBtn.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Username dan password tidak boleh kosong.");
                errorLabel.setVisible(true);
                return; // hentikan eksekusi jika input kosong
            }

            User user = users.get(username);
            if (user != null && user.getPassword().equals(password)) {
                currentUser = user;
                errorLabel.setVisible(false); // sembunyikan error
                usernameField.clear();
                passwordField.clear();

                if (user.isAdmin()) {
                    refreshAdminData();
                    primaryStage.setScene(adminScene);
                } else {
                    refreshUserData();
                    primaryStage.setScene(userScene);
                }
            } else {
                errorLabel.setText("Username atau password salah.");
                errorLabel.setVisible(true);
            }
        });

        //===== BOX HITAM DI TENGAH! =====
        VBox loginBox = new VBox(10, titleLabel, errorLabel,  usernameField, passwordField, loginBtn, forgotButton, registerBtn);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setMaxWidth(500);
        loginBox.setPadding(new Insets(30));
        loginBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-background-radius: 20; ");

        VBox content = new VBox(20, logoView, loginBox);
        content.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(bgView, content);

        loginScene  = new Scene(root, 1280, 720);
        bgView.fitWidthProperty().bind(loginScene.widthProperty());
        bgView.fitHeightProperty().bind(loginScene.heightProperty());


    }

    // ==================== ADMIN SCENE ====================
    private TableView<Book> booksTableAdmin;
    private TableView<BorrowRecord> borrowTableAdmin;
    private TextField newBookField, newUserField, newUserPassField;
    private Label adminMessageLabel;

    private TableView<User> userTableAdmin;

    private void initAdminScene() {
        // Books Table & Controls
        booksTableAdmin = new TableView<>();
        TableColumn<Book, String> bookTitleCol = new TableColumn<>("Judul Buku");
        bookTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Book, String> bookStatusCol = new TableColumn<>("Status");
        bookStatusCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().isBorrowed() ? "Dipinjam" : "Tersedia"));
        booksTableAdmin.getColumns().addAll(bookTitleCol, bookStatusCol);
        booksTableAdmin.setPrefHeight(180);

        newBookField = new TextField();
        newBookField.setPromptText("Judul buku baru");
        Button addBookBtn = new Button("Tambah Buku");
        addBookBtn.setOnAction(e -> {
            String title = newBookField.getText().trim();
            try {
                if (title.isEmpty()) throw new Exception("Judul buku tidak boleh kosong.");
                if (books.stream().anyMatch(b -> b.getTitle().equalsIgnoreCase(title)))
                    throw new Exception("Buku sudah ada.");
                books.add(new Book(title));
                newBookField.clear();
                adminMessageLabel.setText("Buku '" + title + "' berhasil ditambahkan.");
                refreshAdminData();
            } catch (Exception ex) {
                adminMessageLabel.setText(ex.getMessage());
            }
        });

        Button removeBookBtn = new Button("Hapus Buku Terpilih");
        removeBookBtn.setOnAction(e -> {
            Book selected = booksTableAdmin.getSelectionModel().getSelectedItem();
            try {
                if (selected == null) throw new Exception("Pilih buku terlebih dahulu.");
                if (selected.isBorrowed()) throw new Exception("Buku sedang dipinjam, tidak bisa dihapus.");
                books.remove(selected);
                adminMessageLabel.setText("Buku '" + selected.getTitle() + "' berhasil dihapus.");
                refreshAdminData();
            } catch (Exception ex) {
                adminMessageLabel.setText(ex.getMessage());
            }
        });

        HBox bookControls = new HBox(10, newBookField, addBookBtn, removeBookBtn);
        bookControls.setAlignment(Pos.CENTER_LEFT);

        // Borrow records table
        borrowTableAdmin = new TableView<>();
        TableColumn<BorrowRecord, String> brBookCol = new TableColumn<>("Judul Buku");
        brBookCol.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        TableColumn<BorrowRecord, String> brUserCol = new TableColumn<>("Nama ProjectLibrary.User");
        brUserCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<BorrowRecord, LocalDate> brDateCol = new TableColumn<>("Tanggal Pinjam");
        brDateCol.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        TableColumn<BorrowRecord, Integer> brDaysCol = new TableColumn<>("Jangka Waktu (hari)");
        brDaysCol.setCellValueFactory(new PropertyValueFactory<>("days"));
        TableColumn<BorrowRecord, String> brFineCol = new TableColumn<>("Denda (Rupiah)");
        brFineCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().calculateFine())
        ));
        borrowTableAdmin.getColumns().addAll(brBookCol, brUserCol, brDateCol, brDaysCol, brFineCol);
        borrowTableAdmin.setPrefHeight(180);

        // Users Table & Controls
        userTableAdmin = new TableView<>();
        TableColumn<User, String> userNameCol = new TableColumn<>("Username");
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<User, String> userRoleCol = new TableColumn<>("Role");
        userRoleCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().isAdmin() ? "Admin" : "User"
        ));
        userTableAdmin.getColumns().addAll(userNameCol, userRoleCol);
        userTableAdmin.setPrefHeight(180);

        newUserField = new TextField();
        newUserField.setPromptText("Username baru");
        newUserPassField = new TextField();
        newUserPassField.setPromptText("Password");
        Button addUserBtn = new Button("Tambah User");
        addUserBtn.setOnAction(e -> {
            String uname = newUserField.getText().trim();
            String upass = newUserPassField.getText();
            try {
                if (uname.isEmpty() || upass.isEmpty()) throw new Exception("Username dan password tidak boleh kosong.");
                if (users.containsKey(uname)) throw new Exception("ProjectLibrary.User sudah ada.");
                users.put(uname, new User(uname, upass, false));
                newUserField.clear();
                newUserPassField.clear();
                adminMessageLabel.setText("User '" + uname + "' berhasil ditambahkan.");
                refreshAdminData();
            } catch (Exception ex) {
                adminMessageLabel.setText(ex.getMessage());
            }
        });

        Button removeUserBtn = new Button("Hapus User Terpilih");
        removeUserBtn.setOnAction(e -> {
            User u = userTableAdmin.getSelectionModel().getSelectedItem();
            try {
                if (u == null) throw new Exception("Pilih user terlebih dahulu.");
                if (u.isAdmin()) throw new Exception("Tidak bisa menghapus admin.");
                users.remove(u.getUsername());
                adminMessageLabel.setText("User '" + u.getUsername() + "' berhasil dihapus.");
                refreshAdminData();
            } catch (Exception ex) {
                adminMessageLabel.setText(ex.getMessage());
            }
        });

        HBox userControls = new HBox(10, newUserField, newUserPassField, addUserBtn, removeUserBtn);
        userControls.setAlignment(Pos.CENTER_LEFT);

        // Admin message label
        adminMessageLabel = new Label();

        // Logout button
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> {
            currentUser = null;
            adminMessageLabel.setText("");
            primaryStage.setScene(loginScene);
        });

        VBox adminLayout = new VBox(15,
                new Label("Daftar Buku:"), booksTableAdmin, bookControls,
                new Label("Data Peminjaman:"), borrowTableAdmin,
                new Label("User:"), userTableAdmin, userControls,
                adminMessageLabel,
                logoutBtn
        );
        adminLayout.setPadding(new Insets(15, 15, 15,300));
        adminLayout.setPrefHeight(600);
        adminScene = new Scene(adminLayout, 1280, 720);
    }

    // ==================== USER SCENE ====================
    private TableView<Book> booksTableUser;
    private TextField searchBookField;
    private Spinner<Integer> daysSpinner;
    private Label userMessageLabel;
    private TableView<BorrowRecord> userBorrowTable;

    private void initUserScene() {
        Label searchLabel = new Label("Cari Buku:");
        searchBookField = new TextField();
        searchBookField.setPromptText("Masukkan judul buku");
        searchBookField.textProperty().addListener((obs, old, niu) -> filterBooks());

        booksTableUser = new TableView<>();
        TableColumn<Book, String> titleCol = new TableColumn<>("Judul Buku");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Book, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().isBorrowed() ? "Dipinjam" : "Tersedia"
        ));
        booksTableUser.getColumns().addAll(titleCol, statusCol);
        booksTableUser.setPrefHeight(200);

        daysSpinner = new Spinner<>(1, 30, 7);
        daysSpinner.setEditable(true);

        Button borrowBtn = new Button("Pinjam Buku");
        borrowBtn.setOnAction(e -> {
            Book selected = booksTableUser.getSelectionModel().getSelectedItem();
            if (selected == null) {
                userMessageLabel.setText("Pilih buku terlebih dahulu.");
                return;
            }
            if (selected.isBorrowed()) {
                userMessageLabel.setText("Buku sudah dipinjam oleh orang lain.");
                return;
            }
            int days = daysSpinner.getValue();
            if (days <= 0) {
                userMessageLabel.setText("Jangka waktu peminjaman harus positif.");
                return;
            }
            borrowRecords.add(new BorrowRecord(currentUser.getUsername(), selected.getTitle(), LocalDate.now(), days));
            selected.setBorrowed(true);
            userMessageLabel.setText("Berhasil meminjam '" + selected.getTitle() + "' selama " + days + " hari.");
            refreshUserData();
        });

        userMessageLabel = new Label();

        // Table user borrow records
        userBorrowTable = new TableView<>();
        TableColumn<BorrowRecord, String> urBookCol = new TableColumn<>("Judul Buku");
        urBookCol.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        TableColumn<BorrowRecord, LocalDate> urBorrowDateCol = new TableColumn<>("Tanggal Pinjam");
        urBorrowDateCol.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        TableColumn<BorrowRecord, Integer> urDaysCol = new TableColumn<>("Jangka Waktu (hari)");
        urDaysCol.setCellValueFactory(new PropertyValueFactory<>("days"));
        TableColumn<BorrowRecord, String> urFineCol = new TableColumn<>("Denda (Rp.)");
        urFineCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().calculateFine())
        ));
        userBorrowTable.getColumns().addAll(urBookCol, urBorrowDateCol, urDaysCol, urFineCol);
        userBorrowTable.setPrefHeight(150);

        Button returnBookBtn = new Button("Kembalikan Buku Terpilih");
        returnBookBtn.setOnAction(e -> {
            BorrowRecord selected = userBorrowTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                userMessageLabel.setText("Pilih peminjaman yang ingin dikembalikan.");
                return;
            }
            borrowRecords.remove(selected);
            // Set buku menjadi tersedia
            books.stream()
                    .filter(b -> b.getTitle().equals(selected.getBookTitle()))
                    .findFirst()
                    .ifPresent(b -> b.setBorrowed(false));
            userMessageLabel.setText("Buku '" + selected.getBookTitle() + "' berhasil dikembalikan.");
            refreshUserData();
        });

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> {
            currentUser = null;
            userMessageLabel.setText("");
            primaryStage.setScene(loginScene);
        });

        VBox userLayout = new VBox(10,
                new HBox(10, searchLabel, searchBookField),
                booksTableUser,
                new HBox(10, new Label("Jangka waktu (hari):"), daysSpinner, borrowBtn),
                userMessageLabel,
                new Label("Data Peminjaman Anda:"),
                userBorrowTable,
                returnBookBtn,
                logoutBtn
        );


        userLayout.setPadding(new Insets(15,15,15,300));

        userScene = new Scene(userLayout, 1280, 720);
    }

    private void filterBooks() {
        String filter = searchBookField.getText().toLowerCase();
        List<Book> filtered = books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(filter))
                .collect(Collectors.toList());
        booksTableUser.getItems().setAll(filtered);
    }

    private void refreshAdminData() {
        booksTableAdmin.getItems().setAll(books);
        borrowTableAdmin.getItems().setAll(borrowRecords);
        userTableAdmin.getItems().setAll(users.values());
    }

    private void refreshUserData() {
        booksTableUser.getItems().setAll(books);
        List<BorrowRecord> userBorrows = borrowRecords.stream()
                .filter(b -> b.getUsername().equals(currentUser.getUsername()))
                .collect(Collectors.toList());
        userBorrowTable.getItems().setAll(userBorrows);
        filterBooks();
    }

    // ========= MODEL CLASSES ========= //

    public static class User {
        private String username;
        private String password;
        private boolean isAdmin;

        public User(String username, String password, boolean isAdmin) {
            this.username = username;
            this.password = password;
            this.isAdmin = isAdmin;
        }

        public String getUsername() { return username; }

        public String getPassword() { return password; }

        public boolean isAdmin() { return isAdmin; }
    }

    public static class Book {
        private String title;
        private boolean borrowed;

        public Book(String title) {
            this.title = title;
            this.borrowed = false;
        }

        public String getTitle() { return title; }
        public boolean isBorrowed() { return borrowed; }
        public void setBorrowed(boolean borrowed) { this.borrowed = borrowed; }
    }

    public static class BorrowRecord {
        private String username;
        private String bookTitle;
        private LocalDate borrowDate;
        private int days;

        public BorrowRecord(String username, String bookTitle, LocalDate borrowDate, int days) {
            this.username = username;
            this.bookTitle = bookTitle;
            this.borrowDate = borrowDate;
            this.days = days;
        }

        public String getUsername() { return username; }

        public String getBookTitle() { return bookTitle; }

        public LocalDate getBorrowDate() { return borrowDate; }

        public int getDays() { return days; }

        public int calculateFine() {
            long overdueDays = ChronoUnit.DAYS.between(borrowDate.plusDays(days), LocalDate.now());
            if (overdueDays > 0) {
                return (int)(overdueDays * 500);
            }
            return 0;
        }
    }
}