package com.example.platproject.service;

public interface PlatFile {
    void lireFichierTexte(String nomFichier, int userId);
    void ecrireFichierTexte(String nomFichier, int userId);
    void lireFichierExcel(String nomFichier, int userId);
    void ecrireFichierExcel(String nomFichier, int userId);
    void lireFichierJson(String nomFichier, int userId);
    void ecrireFichierJson(String nomFichier, int userId);
    void addPlat(String nom, String categorie, String type, String descriptif, Double prix, int calorie, int userId);
}
