package org.example.dao;

import org.example.model.LejatszasiLista;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListaDaoImpl implements ListaDao {
    private static final String CONN = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = "homeuser";
    private static final String PASSWORD = "password2";
    private static final String ADD_LIST = "INSERT INTO LEJATSZASI_LISTA (LISTA_CIME, FELHASZNALONEV, VIDEO_ID) VALUES (?,?,?)";
    private static final String LIST_LISTA = "SELECT DISTINCT LISTA_CIME FROM LEJATSZASI_LISTA WHERE FELHASZNALONEV = ?";
    private static final String INSERT_VIDEO = "INSERT INTO LEJATSZASI_LISTA(LISTA_CIME, FELHASZNALONEV, VIDEO_ID) VALUES(?,?,?)";


    @Override
    public List<LejatszasiLista> getLista(String username) {
        List<LejatszasiLista> alista = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
             PreparedStatement getLista = c.prepareStatement(LIST_LISTA)) {

            getLista.setString(1, username);
            ResultSet resultSet = getLista.executeQuery();

            while (resultSet.next()) {
                LejatszasiLista lista = new LejatszasiLista();
                lista.setListaCime(resultSet.getString("LISTA_CIME"));

                alista.add(lista);
            }

            resultSet.close();

        } catch (SQLException e) {
            System.out.println("[GET LISTA] " + e.toString());
        }

        return alista;
    }


    @Override
    public boolean addToList(LejatszasiLista lejatszasiLista) {
        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD); PreparedStatement other = c.prepareStatement(ADD_LIST)) {

            other.setString(1, lejatszasiLista.getListaCime());
            other.setString(2, lejatszasiLista.getFelhasznalonev());
            other.setInt(3, lejatszasiLista.getVideoID());

            return other.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("[ADD LIST] " + e.toString());
        }

        return false;
    }



    @Override
    public boolean addList(LejatszasiLista lejatszasiLista) {
        try (Connection c = DriverManager.getConnection(CONN, USERNAME, PASSWORD); PreparedStatement addList = c.prepareStatement(ADD_LIST)) {

            addList.setString(1, lejatszasiLista.getListaCime());
            addList.setString(2, lejatszasiLista.getFelhasznalonev());
            addList.setInt(3, lejatszasiLista.getVideoID());

            return addList.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("[ADD LIST] " + e.toString());
        }

        return false;

    }
}
