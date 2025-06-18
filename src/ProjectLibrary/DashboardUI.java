package ProjectLibrary;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardUI extends Application {

    private static User currentUser; // Diset sebelum start() dipanggil

    // Setter untuk mengoper data user dari login
    public static void setUser(User user) {
        currentUser = user;
    }

    @Override
    public void start(Stage primaryStage) {
        if (currentUser == null) {
            System.err.println("User belum diset. Tidak bisa memuat dashboard.");
            return;
        }

        primaryStage.setTitle("Dashboard Perpustakaan");

        VBox root = new VBox(15);
        root.setStyle("-fx-padding: 30; -fx-alignment: center; -fx-background-color: #f4f4f4;");

        Label welcomeLabel = new Label("Selamat datang, " + currentUser.getUsername());
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Tombol umum
        Button viewBooksBtn = new Button("Lihat Daftar Buku");
        viewBooksBtn.setPrefWidth(200);
        viewBooksBtn.setOnAction(e -> {
            System.out.println("Tampilkan daftar buku...");
            // Tambahkan logika menampilkan buku
        });

        root.getChildren().addAll(welcomeLabel, viewBooksBtn);

        // Tombol untuk admin
        if (currentUser.isAdmin()) {
            Button addBookBtn = new Button("Tambah Buku");
            Button manageUsersBtn = new Button("Kelola Pengguna");

            addBookBtn.setPrefWidth(200);
            manageUsersBtn.setPrefWidth(200);

            addBookBtn.setOnAction(e -> {
                System.out.println("Admin: Tambahkan buku...");
                // Tambahkan logika tambah buku
            });

            manageUsersBtn.setOnAction(e -> {
                System.out.println("Admin: Kelola pengguna...");
                // Tambahkan logika kelola user
            });

            root.getChildren().addAll(addBookBtn, manageUsersBtn);

        } else {
            // Tombol untuk user biasa
            Button borrowBookBtn = new Button("Pinjam Buku");
            Button returnBookBtn = new Button("Kembalikan Buku");

            borrowBookBtn.setPrefWidth(200);
            returnBookBtn.setPrefWidth(200);

            borrowBookBtn.setOnAction(e -> {
                System.out.println("User: Pinjam buku...");
                // Tambahkan logika peminjaman
            });

            returnBookBtn.setOnAction(e -> {
                System.out.println("User: Kembalikan buku...");
                // Tambahkan logika pengembalian
            });

            root.getChildren().addAll(borrowBookBtn, returnBookBtn);
        }

        // Tombol logout (opsional)
        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        logoutBtn.setPrefWidth(200);
        logoutBtn.setOnAction(e -> {
            try {
                new LibraryApp().start(primaryStage); // Kembali ke halaman login
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        root.getChildren().add(logoutBtn);

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

