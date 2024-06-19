package databasepbo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class EditBuku {

    static Scanner scanner = new Scanner(System.in);

    public static void edit() {
        System.out.println("\n=== Edit Buku ===");
        try (Connection conn = koneksi.getConnection()) {
            System.out.print("Masukkan ID Buku yang akan diedit: ");
            int idBuku = scanner.nextInt();
            scanner.nextLine(); 

            if (cekBukuAda(conn, idBuku)) {
                System.out.print("Judul Buku baru: ");
                String judul_buku = scanner.nextLine();
                System.out.print("Tahun Terbit baru: ");
                int tahun_terbit = scanner.nextInt();
                scanner.nextLine(); 
                System.out.print("Stok baru: ");
                int stok = scanner.nextInt();
                scanner.nextLine(); 
                System.out.print("Nama Penulis baru: ");
                String penulis = scanner.nextLine();

                int penulisId = ambilIdPenulis(conn, penulis);
                if (penulisId == -1) {
                    penulisId = tambahPenulis(conn, penulis);
                }

                boolean updated = updateBuku(conn, idBuku, judul_buku, tahun_terbit, stok, penulisId);
                if (updated) {
                    System.out.println("Data buku berhasil diperbarui.");
                } else {
                    System.out.println("Gagal memperbarui data buku.");
                }
            } else {
                System.out.println("Buku dengan ID " + idBuku + " tidak ditemukan.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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

    public static int ambilIdPenulis(Connection conn, String namaPenulis) {
        int penulisId = -1;
        try {
            String sql = "SELECT id FROM penulis WHERE nama_penulis = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, namaPenulis);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                penulisId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return penulisId;
    }

    public static int tambahPenulis(Connection conn, String namaPenulis) {
        int penulisId = -1;
        try {
            String sql = "INSERT INTO penulis (nama_penulis) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, namaPenulis);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    penulisId = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return penulisId;
    }

    public static boolean updateBuku(Connection conn, int idBuku, String judulBuku, int tahunTerbit, int stok, int idPenulis) {
        try {
            String sql = "UPDATE buku SET judul_buku = ?, tahun_terbit = ?, stok = ?, penulis = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, judulBuku);
            ps.setInt(2, tahunTerbit);
            ps.setInt(3, stok);
            ps.setInt(4, idPenulis);
            ps.setInt(5, idBuku);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
