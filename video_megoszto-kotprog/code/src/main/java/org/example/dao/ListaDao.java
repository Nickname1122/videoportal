package org.example.dao;

import org.example.model.LejatszasiLista;

import java.util.List;

public interface ListaDao {

    List<LejatszasiLista> getLista(String username);

    boolean addList(LejatszasiLista lejatszasiLista);

    boolean addToList(LejatszasiLista lejatszasiLista);


}
