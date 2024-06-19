package databasepbo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowBuku {

    public static void show() {
        System.out.println("\n=== Daftar Buku ===");
        try (Connection conn = koneksi.getConnection()) {
            String sql = "SELECT b.id, b.judul_buku, b.tahun_terbit, b.stok, p.nama_penulis " +
                         "FROM buku b " +
                         "INNER JOIN penulis p ON b.penulis = p.id";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idBuku = rs.getInt("id");
                String judulBuku = rs.getString("judul_buku");
                int tahunTerbit = rs.getInt("tahun_terbit");
                int stok = rs.getInt("stok");
                String namaPenulis = rs.getString("nama_penulis");

                System.out.println("ID Buku: " + idBuku);
                System.out.println("Judul Buku: " + judulBuku);
                System.out.println("Tahun Terbit: " + tahunTerbit);
                System.out.println("Stok: " + stok);
                System.out.println("Nama Penulis: " + namaPenulis);
                System.out.println("--------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
