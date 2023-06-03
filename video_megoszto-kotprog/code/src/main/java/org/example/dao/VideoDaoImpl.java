package org.example.dao;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.model.Video;

import java.io.File;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VideoDaoImpl implements VideoDao {

    private static final String CONN = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = "homeuser";
    private static final String PASSWORD = "password2";
    private static final String LIST_VIDEOS = "SELECT * FROM VIDEO";
    private static final String GET_USER_VIDEOS = "SELECT * FROM VIDEO WHERE FELTOLTO_NEV = ?";
    private static final String LIST_ALL_VIDEO = "SELECT CIM, FELTOLTO_NEV, STILL_KEP, LEIRAS FROM VIDEO";
    private static final String LIST_SEARCH_VIDEOS = "SELECT CIM, MEGTEKINTESEK_SZAMA, KATEGORIA, FELTOLTES_DATUMA, FELTOLTO_NEV FROM VIDEO WHERE CIM LIKE?";
    private static final String DELETE_VIDEO = "DELETE FROM VIDEO WHERE VIDEO_ID = ?";
    private static final String NEW_BAN_VIDEO = "INSERT INTO TILTAS_V (VIDEO_ID,TILTOTT_VIDEO) VALUES (?,?)";
    private static final String UPDATE_BAN_VIDEO = "UPDATE TILTAS_V SET TILTOTT_VIDEO = 1 WHERE VIDEO_ID = ?";
    private static final String UPDATE_UNBAN_VIDEO = "UPDATE TILTAS_V SET TILTOTT_VIDEO = 0 WHERE VIDEO_ID = ?";
    private static final String BANOLTE = "SELECT VIDEO_ID FROM TILTAS_V WHERE VIDEO_ID = ?";
    private static final String GET_LIKES = "SELECT KEDVELESEK_SZAMA FROM VIDEO WHERE VIDEO_ID = ?";
    private static final String UPDATE_LIKES = "UPDATE VIDEO SET KEDVELESEK_SZAMA = ? WHERE VIDEO_ID = ?";
    private static final String INSERT_VIDEO = "INSERT INTO VIDEO (CIM, KATEGORIA, LEIRAS, FAJL_NEVE, FELTOLTES_DATUMA, STILL_KEP, FELTOLTO_NEV) VALUES (?,?,?,?,?,?,?)";
    private static final String GET_ID = "SELECT VIDEO_ID FROM VIDEO WHERE CIM = ?";
    private static final String UPDATE_VIEW_COUNT = "UPDATE VIDEO SET MEGTEKINTESEK_SZAMA = ? WHERE VIDEO_ID = ?";

    Video video = new Video();

    @Override
    public int getIdFromTitle(String title) {
        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
             PreparedStatement asd = c.prepareStatement(GET_ID)) {
            asd.setString(1, title);
            ResultSet resultSet = asd.executeQuery();

            if (resultSet.next()) {
                int a = resultSet.getInt(1);
                resultSet.close();
                return a;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public List<Video> getVideo() {

        List<Video> result = new ArrayList<>();

        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD); PreparedStatement pst = c.prepareStatement(LIST_VIDEOS)) {

            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                Video video = new Video();

                video.setVideoID(resultSet.getInt(1));
                video.setCim(resultSet.getString(2));
                video.setMegtekintesekSzama(String.valueOf(resultSet.getInt(3)));
                video.setKategoria(resultSet.getString(4));
                video.setLeiras(resultSet.getString(5));
                video.setKedvelesekSzama(String.valueOf(resultSet.getInt(6)));
                video.setFajlNeve(resultSet.getString(7));
                video.setFeltoltesDatuma(String.valueOf(resultSet.getDate(8)));

                File file = new File("kepek/" + resultSet.getString(9) + ".png");
                System.out.println(file.toURI());
                Image image = new Image(file.toURI().toString());
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(40);
                imageView.setFitWidth(60);

                video.setStillView(imageView);
                video.setFeltoltoNeve(resultSet.getString(10));


                result.add(video);

            }

            resultSet.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return result;
    }


    @Override
    public List<Video> getSearchVideo(String tag) {

        List<Video> result = new ArrayList<>();

        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD); PreparedStatement pst = c.prepareStatement(LIST_SEARCH_VIDEOS)) {
            pst.setString(1, tag);

            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {

                video.setCim(resultSet.getString(1));
                video.setMegtekintesekSzama(String.valueOf(resultSet.getInt(2)));
                video.setKategoria(resultSet.getString(3));
                video.setFeltoltesDatuma(String.valueOf(resultSet.getDate(4)));
                video.setFeltoltoNeve(resultSet.getString(5));
                result.add(video);

            }

            resultSet.close();

        } catch (SQLException e) {
            System.out.println("[SEARCH VIDEO] " + e.toString());
//            e.printStackTrace();
        }
        return result;
    }


    @Override
    public List<Video> allVideo() {

        List<Video> result = new ArrayList<>();

        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD); Statement listAllVideo = c.createStatement()) {

            ResultSet resultSet = listAllVideo.executeQuery(LIST_ALL_VIDEO);

            while (resultSet.next()) {
                Video video = new Video();
                video.setCim(resultSet.getString("CIM"));
                video.setStillKep(resultSet.getString("STILL_KEP"));
                video.setLeiras(resultSet.getString("LEIRAS"));
                video.setFeltoltoNeve(resultSet.getString("FELTOLTO_NEV"));
                result.add(video);
            }

            resultSet.close();

        } catch (SQLException e) {
            System.out.println("[LIST ALL VIDEO] " + e.toString());
        }

        return result;

    }


    @Override
    public boolean banVideo(int videoID) {
        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
             PreparedStatement ujban = c.prepareStatement(NEW_BAN_VIDEO);
             PreparedStatement updateban = c.prepareStatement(UPDATE_BAN_VIDEO);
             PreparedStatement vane = c.prepareStatement(BANOLTE)) {
            ujban.setInt(1, videoID);
            ujban.setInt(2, 1);
            updateban.setInt(1, videoID);
            vane.setInt(1, videoID);
            ResultSet resultSet = vane.executeQuery();

            while (resultSet.next()) {
                updateban.execute();
                resultSet.close();

            }

            ujban.execute();
            resultSet.close();
            return true;

        } catch (SQLException e) {
            System.out.println("[BAN VIDEO] " + e.toString());
            //e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean unbanVideo(int videoID) {
        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
             PreparedStatement ujban = c.prepareStatement(NEW_BAN_VIDEO);
             PreparedStatement updateban = c.prepareStatement(UPDATE_UNBAN_VIDEO);
             PreparedStatement vane = c.prepareStatement(BANOLTE)) {
            ujban.setInt(1, videoID);
            ujban.setInt(2, 0);
            updateban.setInt(1, videoID);
            vane.setInt(1, videoID);
            ResultSet resultSet = vane.executeQuery();

            if (resultSet.next()) {
                updateban.execute();
                resultSet.close();
                return true;
            }
            ujban.execute();
            resultSet.close();

        } catch (SQLException e) {
            System.out.println("[UNBAN VIDEO] " + e.toString());
            //e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean addVideo(Video video) {
        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD); PreparedStatement ujVideo = c.prepareStatement(INSERT_VIDEO)) {

            SimpleDateFormat format = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.ROOT);
            java.util.Date parsed = format.parse(video.getFeltoltesDatuma());
            java.sql.Date sql = new java.sql.Date(parsed.getTime());


            ujVideo.setString(1, video.getCim());
            ujVideo.setString(2, video.getKategoria());
            ujVideo.setString(3, video.getLeiras());
            ujVideo.setString(4, video.getFajlNeve());
            ujVideo.setDate(5, sql);
            ujVideo.setString(6, video.getStillKep());
            ujVideo.setString(7, video.getFeltoltoNeve());

            return ujVideo.executeUpdate() == 1;

        } catch (SQLException | ParseException e) {
//            System.out.println("[ADD VIDEO] " + e.toString());
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean deleteVideo(int videoID) {
        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD); PreparedStatement deleteVideo = c.prepareStatement(DELETE_VIDEO)) {
            deleteVideo.setInt(1, videoID);
            ResultSet resultSet = deleteVideo.executeQuery();

            while (resultSet.next()) {
                if (videoID == resultSet.getInt("VIDEO_ID")) {
                    return true;
                }
            }

            resultSet.close();

        } catch (SQLException e) {
            System.out.println("[DELETE VIDEO] " + e.toString());
            //e.printStackTrace();
        }
        return false;
    }


    @Override
    public int getLikes(int videoID) {

        int likes = 0;

        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD); PreparedStatement getLikes = c.prepareStatement(GET_LIKES)) {

            getLikes.setInt(1, videoID);
            ResultSet resultSet = getLikes.executeQuery();

            while (resultSet.next()) {
                likes = resultSet.getInt(1);
            }

            resultSet.close();

        } catch (SQLException e) {
            System.out.println("[GET LIKES] " + e.toString());
        }

        return likes;
    }


    @Override
    public boolean updateLikes(int videoID, int kedvelesekSzama) {
        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD); PreparedStatement updateLikes = c.prepareStatement(UPDATE_LIKES)) {
            updateLikes.setInt(1, kedvelesekSzama);
            updateLikes.setInt(2, videoID);

            return updateLikes.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("[UPDATE LIKES] " + e.toString());
        }

        return false;

    }


    @Override
    public List<Video> getUserVideo(String username) {

        List<Video> result = new ArrayList<>();

        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD); PreparedStatement getUserVideos = c.prepareStatement(GET_USER_VIDEOS)) {

            getUserVideos.setString(1, username);
            ResultSet resultSet = getUserVideos.executeQuery();

            while (resultSet.next()) {
                Video video = new Video();

                File file = new File("kepek/" + resultSet.getString("STILL_KEP") + ".png");
                System.out.println(file.toURI().toString());
                Image image = new Image(file.toURI().toString());
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(40);
                imageView.setFitWidth(60);
                video.setStillView(imageView);
                video.setCim(resultSet.getString("CIM"));

                result.add(video);
            }

            resultSet.close();

        } catch (SQLException e) {
            System.out.println("[GET USER VIDEOS] " + e.toString());
        }

        return result;

    }


    @Override
    public boolean updateViewCount(int videoID, int viewCount) {
        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD); PreparedStatement updateViewCount = c.prepareStatement(UPDATE_VIEW_COUNT)) {

            updateViewCount.setInt(1, viewCount);
            updateViewCount.setInt(2, videoID);

            return updateViewCount.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("[UPDATE VIEW COUNT] " + e.toString());
        }

        return false;

    }
}