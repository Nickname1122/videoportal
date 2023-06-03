package org.example.dao;

import org.example.model.Komment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KommentDaoImpl implements KommentDao {

    private static final String CONN = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = "homeuser";
    private static final String PASSWORD = "password2";
    private static final String COMMENTS = "SELECT * FROM KOMMENT WHERE VIDEO_ID = ?";
    private static final String ADD_COMMENTS = "INSERT INTO KOMMENT (FELHASZNALONEV, KOMMENT_TARTALOM, VIDEO_ID) VALUES (?,?,?)";

    public KommentDaoImpl() {
    }


    @Override
    public List<Komment> getKomment(int videoID) {
        List<Komment> result = new ArrayList<>();

        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD); PreparedStatement komm = c.prepareStatement(COMMENTS)) {
            komm.setInt(1, videoID);

            ResultSet resultSet = komm.executeQuery();

            while (resultSet.next()) {
                Komment komment = new Komment();
                komment.setFelhasznalonev(resultSet.getString(1));
                komment.setKommentID(resultSet.getInt(2));
                komment.setKommentTartalom(resultSet.getString(3));
                komment.setKommentID(resultSet.getInt(4));
                result.add(komment);
            }

            resultSet.close();

        } catch (SQLException e) {
            System.out.println("[COMMENTS] " + e.toString());
        }
        return result;
    }


    @Override
    public boolean addKomment(Komment komment) {
        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD); PreparedStatement ujKomment = c.prepareStatement(ADD_COMMENTS)) {
            ujKomment.setString(1, komment.getFelhasznalonev());
            ujKomment.setString(2, komment.getKommentTartalom());
            ujKomment.setInt(3, komment.getVideoID());

            return ujKomment.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
