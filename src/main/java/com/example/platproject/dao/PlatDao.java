package com.example.platproject.dao;

import com.example.platproject.entities.Plat;

import java.util.List;

public interface PlatDao {
    void save(Plat plat, int userId);
    void update(Plat plat, int id, int userId);
    void delete(int id, int userId);
    Plat findById(int id, int userId);
    List<Plat> findAll(int userId);
    List<Plat> findByCategorie(String categorie, int userId);
}
