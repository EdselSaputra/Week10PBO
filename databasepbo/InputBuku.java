package databasepbo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InputBuku {

    static Scanner scanner = new Scanner(System.in);

    public static void insert() {
        System.out.println("\n=== Input Buku ===");
        try (Connection conn = koneksi.getConnection()) {
            System.out.print("Judul Buku: ");
            String judul_buku = scanner.nextLine();
            System.out.print("Tahun Terbit: ");
            int tahun_terbit = scanner.nextInt();
            scanner.nextLine(); 
            System.out.print("Stok: ");
            int stok = scanner.nextInt();
            scanner.nextLine(); 
            System.out.print("Nama Penulis: ");
            String penulis = scanner.nextLine();

            int penulisId = ambilIdPenulis(conn, penulis);
            if (penulisId == -1) {
                penulisId = tambahPenulis(conn, penulis);
            }

            int bukuId = tambahBuku(conn, judul_buku, tahun_terbit, stok, penulisId);

            if (bukuId != -1) {
                System.out.println("Data berhasil dimasukkan:");
                System.out.println("ID buku: " + bukuId);
                System.out.println("Judul buku: " + judul_buku);
                System.out.println("Tahun terbit: " + tahun_terbit);
                System.out.println("Stok: " + stok);
                String namaPenulis = ambilNamaPenulis(conn, penulisId);
                System.out.println("Penulis: " + namaPenulis);
            } else {
                System.out.println("Gagal memasukkan data buku.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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

    public static int tambahBuku(Connection conn, String judulBuku, int tahunTerbit, int stok, int idPenulis) {
        int bukuId = -1;
        try {
            String sql = "INSERT INTO buku (judul_buku, tahun_terbit, stok, penulis) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, judulBuku);
            ps.setInt(2, tahunTerbit);
            ps.setInt(3, stok);
            ps.setInt(4, idPenulis);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    bukuId = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bukuId;
    }

    public static String ambilNamaPenulis(Connection conn, int idPenulis) {
        try {
            String sql = "SELECT nama_penulis FROM penulis WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idPenulis);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("nama_penulis");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Penulis tidak ditemukan";
    }
}
