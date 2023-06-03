package org.example.controller;

import org.example.dao.FelhasznaloDao;
import org.example.dao.FelhasznaloDaoImpl;
import org.example.model.Felhasznalo;

import java.util.List;

public class FelhasznaloController {

    private final FelhasznaloDao felhasznaloDao = new FelhasznaloDaoImpl();
    private static FelhasznaloController single_instance = null;

    public FelhasznaloController() {
    }

    public static FelhasznaloController getInstance() {
        if (single_instance == null) {
            single_instance = new FelhasznaloController();
        }

        return single_instance;

    }

    public boolean addUser(Felhasznalo felhasznalo) {
        return felhasznaloDao.addUser(felhasznalo);
    }

    public boolean loginUser(String username, String password) {
        return felhasznaloDao.loginUser(username, password);
    }

    public boolean logoutUser() {
        return felhasznaloDao.logoutUser();
    }

    public boolean banUser(String username) {
        return felhasznaloDao.banUser(username);
    }

    public boolean deleteUser(String username) {
        return felhasznaloDao.deleteUser(username);
    }

    public boolean unbanUser(String username) {
        return felhasznaloDao.unbanUser(username);
    }

    public List<Felhasznalo> searchUser(String tag) {
        return felhasznaloDao.searchUser(tag);
    }

    public List<Felhasznalo> allUser() {
        return felhasznaloDao.allUser();
    }

    public boolean isAdmin() {
        return felhasznaloDao.isAdmin();
    }

    public Felhasznalo getData(String username) {
        return felhasznaloDao.getData(username);
    }

}
