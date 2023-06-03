package org.example.dao;

import org.example.model.Felhasznalo;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FelhasznaloDaoImpl implements FelhasznaloDao {

    private Felhasznalo felhasznalo = new Felhasznalo();

    private static final String CONN = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = "homeuser";
    private static final String PASSWORD = "password2";
    private static final String ADD_USER = "INSERT INTO FELHASZNALO (FELHASZNALONEV, JELSZO, EMAIL, SZULETESI_DATUM) VALUES (?,?,?,?)";
    private static final String SEARCH_USER = "SELECT FELHASZNALONEV, EMAIL FROM FELHASZNALO WHERE FELHASZNALONEV = ?";
    private static final String LOGIN_USER = "SELECT FELHASZNALONEV, JELSZO, BEJELENTKEZVE FROM FELHASZNALO";
    private static final String LIST_ALL_USER = "SELECT * FROM FELHASZNALO";
    private static final String GET_DATA = "SELECT * FROM FELHASZNALO WHERE FELHASZNALONEV = ?";
    private static final String LOGGED_IN = "UPDATE FELHASZNALO SET BEJELENTKEZVE = 1 WHERE FELHASZNALONEV = ?";
    private static final String LOGGED_OUT = "UPDATE FELHASZNALO SET BEJELENTKEZVE = 0 WHERE FELHASZNALONEV = ?";
    private static final String IS_ADMIN = "SELECT FELHASZNALONEV FROM ADMIN WHERE FELHASZNALONEV = ?";
    private static final String DELETE_USER = "DELETE FROM FELHASZNALO WHERE FELHASZNALONEV=?";
    private static final String NEW_BAN_USER = "INSERT INTO TILTAS_F (FELHASZNALONEV,TILTOTT_FELHASZNALO)VALUES (?,?)";
    private static final String UPDATE_BAN_USER = "UPDATE TILTAS_F SET TILTOTT_FELHASZNALO = 1 WHERE FELHASZNALONEV = ?";
    private static final String UPDATE_UNBAN_USER = "UPDATE TILTAS_F SET TILTOTT_FELHASZNALO = 0 WHERE FELHASZNALONEV = ?";
    private static final String BANOLTE = "SELECT FELHASZNALONEV FROM TILTAS_F WHERE FELHASZNALONEV = ?";
    private static String felhasznaloakibevanjelentkezve = null;
    private static String felhasznaloakibevanjelentkezvejelszo = null;


    //konstruktor
    public FelhasznaloDaoImpl() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("[FELHASZNALODAO KONSTRUKTOR] " + e.toString());
        }
    }


    @Override
    public boolean addUser(Felhasznalo felhasznalo) {
        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD); PreparedStatement pst = c.prepareStatement(ADD_USER)) {

            SimpleDateFormat format = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
            java.util.Date parsed = format.parse(felhasznalo.getSzuletesiDatum());
            java.sql.Date sql = new java.sql.Date(parsed.getTime());

            pst.setString(1, felhasznalo.getFelhasznalonev());
            pst.setString(2, felhasznalo.getJelszo());
            pst.setString(3, felhasznalo.getEmail());
            pst.setDate(4, sql);

            return pst.executeUpdate() == 1;

        } catch (SQLException | ParseException e) {
//            System.out.println("[ADD USER] " + e.toString());
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean logoutUser() {
        if (felhasznaloakibevanjelentkezve != null && felhasznaloakibevanjelentkezvejelszo != null) {
            try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
                 Statement login = c.createStatement();
                 PreparedStatement logged_out = c.prepareStatement(LOGGED_OUT)) {
                logged_out.setString(1, felhasznaloakibevanjelentkezve);
                ResultSet resultSet = login.executeQuery(LOGIN_USER);

                while (resultSet.next()) {
                    if (felhasznaloakibevanjelentkezve.equals(resultSet.getString("FELHASZNALONEV")) && felhasznaloakibevanjelentkezvejelszo.equals(resultSet.getString("JELSZO"))) {
                        logged_out.executeUpdate();
                        resultSet.close();
                        return true;
                    }
                }

                resultSet.close();

            } catch (SQLException e) {
                System.out.println("[LOGOUT USER] " + e.toString());
                //e.printStackTrace();
            }

        }
        return false;
    }


    @Override
    public boolean loginUser(String username, String password) {
        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD); Statement login = c.createStatement(); PreparedStatement logged_in = c.prepareStatement(LOGGED_IN)) {
            logged_in.setString(1, username);
            ResultSet resultSet = login.executeQuery(LOGIN_USER);

            while (resultSet.next()) {
                if (username.equals(resultSet.getString("FELHASZNALONEV")) && password.equals(resultSet.getString("JELSZO"))) {
                    felhasznaloakibevanjelentkezve = username;
                    felhasznaloakibevanjelentkezvejelszo = password;
                    logged_in.executeUpdate();
                    resultSet.close();
                    return true;
                }
            }

            resultSet.close();

        } catch (SQLException e) {
            System.out.println("[LOGIN USER] " + e.toString());
            //e.printStackTrace();
        }

        return false;

    }


    @Override
    public List<Felhasznalo> allUser() {

        List<Felhasznalo> result = new ArrayList<>();

        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD); Statement listAllUser = c.createStatement()) {

            ResultSet resultSet = listAllUser.executeQuery(LIST_ALL_USER);

            while (resultSet.next()) {
                Felhasznalo felhasznalo = new Felhasznalo(
                        resultSet.getString("FELHASZNALONEV"),
                        resultSet.getString("JELSZO"),
                        resultSet.getString("EMAIL"),
                        String.valueOf(resultSet.getDate("SZULETESI_DATUM"))
                );
                result.add(felhasznalo);
            }

            resultSet.close();

        } catch (SQLException e) {
            System.out.println("[LIST ALL USER] " + e.toString());
        }

        return result;

    }


    @Override
    public List<Felhasznalo> searchUser(String tag) {

        List<Felhasznalo> result = new ArrayList<>();

        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD); PreparedStatement searchUser = c.prepareStatement(SEARCH_USER)) {
            searchUser.setString(1, tag);

            ResultSet resultSet = searchUser.executeQuery();

            while (resultSet.next()) {

                String uname = resultSet.getString("FELHASZNALONEV");
                String email = resultSet.getString("EMAIL");

                if (uname.contains(tag)) {
                    felhasznalo.setFelhasznalonev(uname);
                    felhasznalo.setEmail(email);
                    result.add(felhasznalo);
                }
            }

            resultSet.close();

        } catch (SQLException e) {
            System.out.println("[SEARCH USER] " + e.toString());
//            e.printStackTrace();
        }

        return result;

    }


    @Override
    public boolean isAdmin() {
        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD); PreparedStatement admin = c.prepareStatement(IS_ADMIN)) {
            admin.setString(1, felhasznaloakibevanjelentkezve);
            ResultSet resultSet = admin.executeQuery();

            while (resultSet.next()) {
                if (felhasznaloakibevanjelentkezve.equals(resultSet.getString("FELHASZNALONEV"))) {
                    resultSet.close();
                    return true;
                }
            }

            resultSet.close();

        } catch (SQLException e) {
            System.out.println("[LOGIN USER] " + e.toString());
            //e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean banUser(String username) {
        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
             PreparedStatement ujban = c.prepareStatement(NEW_BAN_USER);
             PreparedStatement updateban = c.prepareStatement(UPDATE_BAN_USER);
             PreparedStatement vane = c.prepareStatement(BANOLTE)) {
            ujban.setString(1, username);
            ujban.setInt(2, 1);
            updateban.setString(1, username);
            vane.setString(1, username);
            ResultSet resultSet = vane.executeQuery();

            if (resultSet.next()) {
                updateban.execute();
                resultSet.close();
                return true;
            }
            ujban.execute();
            resultSet.close();

        } catch (SQLException e) {
            System.out.println("[LOGIN USER] " + e.toString());
            //e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean unbanUser(String username) {
        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
             PreparedStatement ujban = c.prepareStatement(NEW_BAN_USER);
             PreparedStatement updateban = c.prepareStatement(UPDATE_UNBAN_USER);
             PreparedStatement vane = c.prepareStatement(BANOLTE)) {
            ujban.setString(1, username);
            ujban.setInt(2, 0);
            updateban.setString(1, username);
            vane.setString(1, username);
            ResultSet resultSet = vane.executeQuery();

            if (resultSet.next()) {
                updateban.execute();
                resultSet.close();
                return true;
            }
            ujban.execute();
            resultSet.close();

        } catch (SQLException e) {
            System.out.println("[LOGIN USER] " + e.toString());
            //e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean deleteUser(String username) {
        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD); PreparedStatement delete = c.prepareStatement(DELETE_USER)) {
            delete.setString(1, felhasznaloakibevanjelentkezve);
            delete.executeQuery();
        } catch (SQLException e) {
            System.out.println("[LOGIN USER] " + e.toString());
            //e.printStackTrace();
        }
        return false;
    }


    @Override
    public Felhasznalo getData(String username) {
        Felhasznalo felhasznalo = new Felhasznalo();
        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD); PreparedStatement getData = c.prepareStatement(GET_DATA)) {

            getData.setString(1, username);
            ResultSet resultSet = getData.executeQuery();

            while (resultSet.next()) {
                felhasznalo.setFelhasznalonev(resultSet.getString("FELHASZNALONEV"));
                felhasznalo.setEmail(resultSet.getString("EMAIL"));
                felhasznalo.setSzuletesiDatum(String.valueOf(resultSet.getDate("SZULETESI_DATUM")));
            }

            resultSet.close();

        } catch (SQLException e) {
            System.out.println("[GET DATA] " + e.toString());
        }

        return felhasznalo;

    }


}
