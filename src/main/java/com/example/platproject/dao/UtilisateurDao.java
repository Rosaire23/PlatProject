package com.example.platproject.dao;

import com.example.platproject.entities.Utilisateur;

public interface UtilisateurDao {
    Utilisateur seConnecter(String email, String motDePasse);
    void sInscrire(Utilisateur utilisateur);
}
