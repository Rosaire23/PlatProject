package com.example.platproject.entities;

import java.io.Serializable;
import java.util.Objects;

public class Plat implements Serializable {

    private int id;
    private String nom;
    private String categorie;
    private String type;
    private String descriptif;
    private double prix;
    private int calories;


    //Constructeur avec paramètres

    public Plat(String nom, String categorie, String type, String descriptif, double prix, int calories) {
        this.nom = nom;
        this.categorie = categorie;
        this.type = type;
        this.descriptif = descriptif;
        this.prix = prix;
        this.calories = calories;

    }

    //Constructeur sans paramètres

    public Plat() {

    }


    //Accesseurs

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCatégorie() {
        return categorie;
    }

    public void setCatégorie(String categorie) {
        this.categorie = categorie;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescriptif() {
        return descriptif;
    }

    public void setDescriptif(String descriptif) {
        this.descriptif = descriptif;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }


    @Override
    public int hashCode() {
        return Objects.hash(calories, categorie, descriptif, id, nom, prix, type);
    }

    //Redéfinition de la méthode toString

    @Override
    public String toString() {
        return "Plat [id=" + id + ", nom=" + nom + ", catégorie=" + categorie + ", type=" + type + ", descriptif="
                + descriptif + ", prix=" + prix + ", calories=" + calories + "]";
    }



    //méthode equals

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Plat other = (Plat) obj;
        return calories == other.calories && Objects.equals(categorie, other.categorie)
                && Objects.equals(descriptif, other.descriptif) && id == other.id && Objects.equals(nom, other.nom)
                && Double.doubleToLongBits(prix) == Double.doubleToLongBits(other.prix)
                && Objects.equals(type, other.type);
    }



}
