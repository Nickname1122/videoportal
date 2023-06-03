package org.example.controller;

import org.example.dao.ListaDao;
import org.example.dao.ListaDaoImpl;
import org.example.model.LejatszasiLista;

import java.util.List;

public class ListaController {

    private ListaDao listaDao = new ListaDaoImpl();
    private static ListaController single_instance = null;

    public ListaController() {
    }

    public static ListaController getInstance() {
        if (single_instance == null) {
            single_instance = new ListaController();
        }
        return single_instance;
    }


    public List<LejatszasiLista> getList(String name) {
        return listaDao.getLista(name);
    }


    public boolean addToList(LejatszasiLista lejatszasiLista) {
        return listaDao.addToList(lejatszasiLista);
    }


    public boolean addList(LejatszasiLista lejatszasiLista) {
        return listaDao.addList(lejatszasiLista);
    }


}
