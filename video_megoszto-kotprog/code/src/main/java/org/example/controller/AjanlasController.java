package org.example.controller;

import org.example.dao.AjanlasDao;
import org.example.dao.AjanlasDaoImpl;
import org.example.model.Video;

import java.util.List;

public class AjanlasController {

    private AjanlasDao ajanlasDao = new AjanlasDaoImpl();
    private static AjanlasController single_instance = null;

    public AjanlasController() {
    }

    public static AjanlasController getInstance() {
        if (single_instance == null) {
            single_instance = new AjanlasController();
        }

        return single_instance;
    }

    public List<Video> ajanlas() {
        return ajanlasDao.ajanlas();
    }

}
