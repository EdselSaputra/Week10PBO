package databasepbo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteBuku {

    static Scanner scanner = new Scanner(System.in);

    public static void delete() {
        System.out.println("\n=== Delete Buku ===");
        try (Connection conn = koneksi.getConnection()) {
            System.out.print("Masukkan ID Buku yang akan dihapus: ");
            int idBuku = scanner.nextInt();
            scanner.nextLine();

            boolean deleted = deleteBuku(conn, idBuku);
            if (deleted) {
                System.out.println("Buku dengan ID " + idBuku + " berhasil dihapus.");
            } else {
                System.out.println("Buku dengan ID " + idBuku + " tidak ditemukan.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteBuku(Connection conn, int idBuku) {
        try {
            if (!cekBukuAda(conn, idBuku)) {
                return false;
            }

            String sql = "DELETE FROM buku WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idBuku);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean cekBukuAda(Connection conn, int idBuku) {
        try {
            String sql = "SELECT * FROM buku WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idBuku);

            return ps.executeQuery().next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
