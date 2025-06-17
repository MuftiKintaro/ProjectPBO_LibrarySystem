package ProjectLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {
    private List<Book> books;
    private Map<String, Integer> borrowedBooks; // ProjectLibrary.Book title and days borrowed

    public Library() {
        books = new ArrayList<>();
        borrowedBooks = new HashMap<>();
    }

    public void addBook(String title) {
        books.add(new Book(title));
    }

    public void removeBook(String title) {
        books.removeIf(book -> book.getTitle().equals(title));
    }

    public List<Book> getBooks() {
        return books;
    }

    public void borrowBook(String title, int days) {
        for (Book book : books) {
            if (book.getTitle().equals(title) && !book.isBorrowed()) {
                book.setBorrowed(true);
                borrowedBooks.put(title, days);
                System.out.println("Buku '" + title + "' berhasil dipinjam selama " + days + " hari.");
                return;
            }
        }
        System.out.println("Buku tidak tersedia atau sudah dipinjam.");
    }

    public void returnBook(String title) {
        if (borrowedBooks.containsKey(title)) {
            int days = borrowedBooks.get(title);
            borrowedBooks.remove(title);
            for (Book book : books) {
                if (book.getTitle().equals(title)) {
                    book.setBorrowed(false);
                    int fine = (days > 7) ? (days - 7) * 500 : 0; // Denda jika lebih dari 7 hari
                    if (fine > 0) {
                        System.out.println("Buku '" + title + "' dikembalikan. Denda: " + fine + " Rupiah.");
                    } else {
                        System.out.println("Buku '" + title + "' dikembalikan tanpa denda.");
                    }
                    return;
                }
            }
        } else {
            System.out.println("Buku tidak ditemukan dalam peminjaman.");
        }
    }

    public void viewBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("Tidak ada buku yang dipinjam.");
        } else {
            System.out.println("Buku yang dipinjam:");
            for (Map.Entry<String, Integer> entry : borrowedBooks.entrySet()) {
                System.out.println(" - " + entry.getKey() + " (Jangka waktu: " + entry.getValue() + " hari)");
            }
        }
    }
}

