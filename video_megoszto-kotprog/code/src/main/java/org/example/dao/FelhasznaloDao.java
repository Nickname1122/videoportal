package org.example.dao;

import org.example.model.Felhasznalo;

import java.util.List;

public interface FelhasznaloDao {

    boolean addUser(Felhasznalo felhasznalo);

    boolean loginUser(String username, String password);

    boolean logoutUser();

    boolean isAdmin();

    boolean banUser(String username);

    boolean unbanUser(String username);

    boolean deleteUser(String username);

    Felhasznalo getData(String username);

    List<Felhasznalo> allUser();

    List<Felhasznalo> searchUser(String tag);

}
