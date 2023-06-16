package com.example.platproject.dao.PlatDaoImpl;

import com.example.platproject.dao.PlatDao;
import com.example.platproject.entities.Plat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlatDaoImpl implements PlatDao {

    private Connection conn = DB.getConnection();

    @Override
    public void save(Plat plat, int userId) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(
                    "INSERT INTO Plat (nom, categorie, type, descriptif, Prix, calories, utilisateurId) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, plat.getNom());
            ps.setString(2, plat.getCatégorie());
            ps.setString(3, plat.getType());
            ps.setString(4, plat.getDescriptif());
            ps.setDouble(5, plat.getPrix());
            ps.setInt(6, plat.getCalories());
            ps.setInt(7, userId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    plat.setId(id);
                }

                DB.closeResultSet(rs);
            } else {
                System.out.println("Aucune ligne renvoyée");
            }
        } catch (SQLException e) {
            System.err.println("Problème d'insertion d'un plat " + e);
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(Plat plat, int id, int userId) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(
                    "UPDATE Plat SET nom = ?, categorie = ?, type = ?, descriptif = ?, Prix = ?, calories = ? WHERE Id = ? AND utilisateurId = ?");

            ps.setString(1, plat.getNom());
            ps.setString(2, plat.getCatégorie());
            ps.setString(3, plat.getType());
            ps.setString(4, plat.getDescriptif());
            ps.setDouble(5, plat.getPrix());
            ps.setInt(6, plat.getCalories());
            ps.setInt(7, id);
            ps.setInt(8, userId);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Problème de mise à jour d'un plat " + e);
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void delete(int id, int userId) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM Plat WHERE id = ? AND utilisateurId = ?");

            ps.setInt(1, id);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Problème de suppression d'un plat");
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Plat findById(int id, int userId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(
                    "SELECT * FROM Plat WHERE id = ? AND utilisateurId = ?");
            ps.setInt(1, id);
            ps.setInt(2, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return instantiatePlat(rs);
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Problème de requête pour trouver le plat" + e);
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public List<Plat> findAll(int userId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Plat> plats = new ArrayList<>();

        try {
            ps = conn.prepareStatement("SELECT * FROM Plat WHERE utilisateurId = ?");
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Plat plat = instantiatePlat(rs);
                plats.add(plat);
            }

            return plats;
        } catch (SQLException e) {
            System.err.println("Problème de requête pour trouver tous les plats" + e);
            return plats;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public List<Plat> findByCategorie(String categorie, int userId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Plat> plats = new ArrayList<>();

        try {
            ps = conn.prepareStatement("SELECT * FROM Plat WHERE utilisateurId = ? AND Plat.categorie = ?");
            ps.setInt(1, userId);
            ps.setString(2,categorie);
            rs = ps.executeQuery();

            while (rs.next()) {
                Plat plat = instantiatePlat(rs);
                plats.add(plat);
            }

            return plats;
        } catch (SQLException e) {
            System.err.println("Problème de requête pour trouver tous les plats" + e);
            return plats;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    private Plat instantiatePlat(ResultSet rs) throws SQLException {
        Plat plat = new Plat();
        plat.setId(rs.getInt("id"));
        plat.setNom(rs.getString("nom"));
        plat.setCatégorie(rs.getString("categorie"));
        plat.setType(rs.getString("type"));
        plat.setDescriptif(rs.getString("descriptif"));
        plat.setPrix(rs.getDouble("Prix"));
        plat.setCalories(rs.getInt("calories"));
        return plat;
    }
}
