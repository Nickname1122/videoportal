package org.example.dao;

import org.example.model.Video;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AjanlasDaoImpl implements AjanlasDao {

    private static final String CONN = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = "homeuser";
    private static final String PASSWORD = "password2";
    private static final String AJANL = "SELECT V_ID, V_CIME FROM VIDEO_AJANLAS";

    public AjanlasDaoImpl() {
    }


    @Override
    public List<Video> ajanlas() {
        List<Video> result = new ArrayList<>();

        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD); Statement listAjanlas = c.createStatement()) {
            ResultSet resultSet = listAjanlas.executeQuery(AJANL);

            while (resultSet.next()) {
                Video video = new Video();
                video.setVideoID(resultSet.getInt(1));
                video.setCim(resultSet.getString(2));
                result.add(video);
            }

            resultSet.close();

        } catch (SQLException e) {
            System.out.println("[AJANLAS] " + e.toString());
        }
        return result;
    }


}
