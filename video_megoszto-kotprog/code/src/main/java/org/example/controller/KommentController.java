package org.example.controller;

import org.example.dao.KommentDao;
import org.example.dao.KommentDaoImpl;
import org.example.model.Komment;

import java.util.List;

public class KommentController {

    private KommentDao kommentDao = new KommentDaoImpl();
    private static KommentController single_instance = null;

    public KommentController() {
    }

    public static KommentController getInstance() {
        if (single_instance == null) {
            single_instance = new KommentController();
        }

        return single_instance;
    }

    public List<Komment> getKomment(int videoID) {
        return kommentDao.getKomment(videoID);
    }

    public boolean addKomment(Komment komment) {
        return kommentDao.addKomment(komment);
    }

}
