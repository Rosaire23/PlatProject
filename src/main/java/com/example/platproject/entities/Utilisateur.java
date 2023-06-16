package com.example.platproject.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utilisateur {
    private int id;
    private String email;
    private String nom;
    private String prenom;
    private String pseudo;
    private String genre;
    private String MotDePasse;
    private List<Plat> plats;

    private Boolean estConnecte = false;
    // Constructeur sans paramètre
    public Utilisateur() { }

    // Constructeur avec paramètres
    public Utilisateur(String email, String nom, String prenom, String pseudo, String genre) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.pseudo = pseudo;
        this.genre = genre;
        plats = new ArrayList<>();
    }
    // Accesseurs
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Boolean getEstConnecte() {
        return estConnecte;
    }

    public void setEstConnecte(Boolean estConnecte) {
        this.estConnecte = estConnecte;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getMotDePasse() {
        return MotDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        MotDePasse = motDePasse;
    }

    public void ajouterPlat(Plat plat) {
        plats.add(plat);
    }

    public void supprimerPlat(Plat plat) {
        plats.remove(plat);
    }

    public void setPlats(List<Plat> plats) {
        this.plats = plats;
    }

    public List<Plat> getPlats() {
        return plats;
    }
    // Méthode toString
    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
    // Méthode hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id, email, nom, prenom, pseudo, genre);
    }
    // Méthode equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Utilisateur utilisateur = (Utilisateur) obj;
        return  Objects.equals(email, utilisateur.email) &&
                Objects.equals(nom, utilisateur.nom) &&
                Objects.equals(prenom, utilisateur.prenom) &&
                Objects.equals(pseudo, utilisateur.pseudo) &&
                Objects.equals(genre, utilisateur.genre);
    }
}
