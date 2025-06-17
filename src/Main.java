import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String pilihan;
        double angka1, angka2, hasil;

        while (true) {
            System.out.println("=== Kalkulator Sederhana ===");
            System.out.println("1. Penjumlahan");
            System.out.println("2. Pengurangan");
            System.out.println("3. Perkalian");
            System.out.println("4. Pembagian");
            System.out.println("5. Keluar");
            System.out.print("Pilih operasi (1-5): ");
            pilihan = scanner.nextLine();

            if (pilihan.equals("5")) {
                System.out.println("Terima kasih telah menggunakan kalkulator.");
                break;
            }

            System.out.print("Masukkan angka pertama: ");
            angka1 = scanner.nextDouble();
            System.out.print("Masukkan angka kedua: ");
            angka2 = scanner.nextDouble();
            scanner.nextLine(); // consume newline

            switch (pilihan) {
                case "1":
                    hasil = angka1 + angka2;
                    System.out.println("Hasil: " + hasil);
                    break;
                case "2":
                    hasil = angka1 - angka2;
                    System.out.println("Hasil: " + hasil);
                    break;
                case "3":
                    hasil = angka1 * angka2;
                    System.out.println("Hasil: " + hasil);
                    break;
                case "4":
                    if (angka2 == 0) {
                        System.out.println("Error: Pembagian dengan nol tidak diperbolehkan.");
                    } else {
                        hasil = angka1 / angka2;
                        System.out.println("Hasil: " + hasil);
                    }
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
            System.out.println();
        }

        scanner.close();
    }
}