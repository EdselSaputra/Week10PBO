package databasepbo;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Aplikasi Manajemen Buku ===");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Hapus Buku");
            System.out.println("3. Tampilkan Daftar Buku");
            System.out.println("4. Edit Buku");
            System.out.println("5. Keluar");
            System.out.print("Pilih opsi: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    InsertBuku.insert();
                    break;
                case 2:
                    DeleteBuku.delete();
                    break;
                case 3:
                    ShowBuku.show();
                    break;
                case 4:
                    EditBuku.edit();
                    break;
                case 5:
                    System.out.println("Terima kasih telah menggunakan aplikasi ini.");
                    System.exit(0);
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }
}
