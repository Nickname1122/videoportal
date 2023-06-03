package org.example.dao;

import org.example.model.Komment;

import java.util.List;

public interface KommentDao {

    List<Komment> getKomment(int videoID);

    boolean addKomment(Komment komment);
}
