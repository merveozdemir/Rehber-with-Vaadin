package com.vaadin.db;

import com.vaadin.domain.Kisi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBOperations {
    final static String JDBC_CONNECTION_STR = "jdbc:mysql://127.0.0.1:3306/rehber?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";
    final static String USERNAME = "root";
    final static String PASSWORD = "12345";

    public void rehbereEkle(Kisi kisi) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String sql = "insert into kisi (ad, soyad, tel_no)" +
                "values (?, ?, ?) ";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, kisi.getAdi());
            preparedStatement.setString(2, kisi.getSoyadi());
            preparedStatement.setString(3, kisi.getTelefon());

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(affectedRows + " satır eklendi.");

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rehberdenSil(int id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String sql = "delete from kisi m where id = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            Class.forName("com.mysql.jdbc.Driver");
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(affectedRows + " satır silindi.");

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public List<Kisi> tumRehberListele() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String sql = "Select * from kisi";
        List<Kisi> kisiList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            //    System.out.println("Kişi ID  |  Ad  |  Soyad  |  Telefon ");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String adi = resultSet.getString("ad");
                String soyadi = resultSet.getString("soyad");
                String telefonNo = resultSet.getString("tel_no");
                //  System.out.printf("%d | %s | %s  | %s \n", id, adi, soyadi, telefonNo);

                Kisi kisi = new Kisi(adi, soyadi, telefonNo);
                kisi.setId(id);
                kisiList.add(kisi);

            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kisiList;
    }

    public List<Kisi> adaGoreFiltrele(String aramaFiltresi) {
        List<Kisi> kisiList = tumRehberListele();
        List<Kisi> filtrelenenListe = new ArrayList<>();
        for (Kisi kisi : kisiList) {
            if (kisi.getAdi().contains(aramaFiltresi)) {
                filtrelenenListe.add(kisi);
            }
        }
        return filtrelenenListe;
    }

    public List<Kisi> telNosunaGoreListele(String aramaFiltresi) {
        List<Kisi> kisiList = tumRehberListele();
        List<Kisi> filtrelenenListe = new ArrayList<>();
        for (Kisi kisi : kisiList) {
            if (kisi.getTelefon().contains(aramaFiltresi)) {
                filtrelenenListe.add(kisi);
            }
        }
        return filtrelenenListe;
    }


    public List<Kisi> soyadaGoreListele(String aramaFiltresi) {
        List<Kisi> kisiList = tumRehberListele();
        List<Kisi> filtrelenenListe = new ArrayList<>();
        for (Kisi kisi : kisiList) {
            if (kisi.getSoyadi().contains(aramaFiltresi)) {
                filtrelenenListe.add(kisi);
            }
        }
        return filtrelenenListe;
    }

}
