package com.example.platproject.service;


import com.example.platproject.dao.UtilisateurDao;
import com.example.platproject.dao.PlatDaoImpl.ConnextionImpl;
import com.example.platproject.entities.Utilisateur;

public class UtilisateurService {
    private UtilisateurDao utilisateurDao = new ConnextionImpl();
    public void inscrire(Utilisateur utilisateur) { utilisateurDao.sInscrire(utilisateur);}
    public Utilisateur seconnecter(String email, String motDePasse) {return  utilisateurDao.seConnecter(email, motDePasse);}
}
