package com.example.platproject.controllers;

import com.example.platproject.entities.Plat;
import com.example.platproject.entities.Utilisateur;
import com.example.platproject.service.PlatService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlatController {
    @FXML
    private TextField nomField;
    @FXML
    private TextField categorieField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField descriptifField;
    @FXML
    private TextField prixField;
    @FXML
    private TextField caloriesField;
    @FXML
    private Button ajouterButton;
    @FXML
    private Button modifierButton;
    @FXML
    private Button supprimerButton;
    @FXML
    private TableView<Plat> platTable;
    @FXML
    private TableColumn<Plat, Integer> idColumn;
    @FXML
    private TableColumn<Plat, String> nomColumn;
    @FXML
    private TableColumn<Plat, String> categorieColumn;
    @FXML
    private TableColumn<Plat, String> typeColumn;
    @FXML
    private TableColumn<Plat, String> descriptifColumn;
    @FXML
    private TableColumn<Plat, Double> prixColumn;
    @FXML
    private TableColumn<Plat, Integer> caloriesColumn;
    @FXML
    private Label messageLabel;

    private PlatService platService = new PlatService();
    private List<Plat> platList = new ArrayList<>();

    private Utilisateur utilisateur = new Utilisateur();

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setPlatService(PlatService platService) {
        this.platService = platService;
    }

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        nomColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNom()));
        categorieColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCatégorie()));
        typeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getType()));
        descriptifColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDescriptif()));
        prixColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrix()));
        caloriesColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCalories()));

        platTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillFieldsWithSelectedPlat(newSelection);
            } else {
                clearFields();
            }
        });
        platList = FXCollections.observableArrayList(platService.findAll(utilisateur.getId()));
        platTable.setItems((ObservableList<Plat>) platList);
    }

    @FXML
    private void handleAjouter(ActionEvent event) {
        String nom = nomField.getText();
        String categorie = categorieField.getText();
        String type = typeField.getText();
        String descriptif = descriptifField.getText();
        double prix = Double.parseDouble(prixField.getText());
        int calories = Integer.parseInt(caloriesField.getText());

        Plat plat = new Plat(nom, categorie, type, descriptif, prix, calories);
        platService.save(plat, getUtilisateur().getId());

        platList.add(plat);
        clearFields();
        messageLabel.setText("Plat ajouté avec succès.");

        platTable.setItems((ObservableList<Plat>) platList);
    }

    @FXML
    private void handleModifier(ActionEvent event) {
        Plat selectedPlat = platTable.getSelectionModel().getSelectedItem();
        if (selectedPlat != null) {
            String nom = nomField.getText();
            String categorie = categorieField.getText();
            String type = typeField.getText();
            String descriptif = descriptifField.getText();
            double prix = Double.parseDouble(prixField.getText());
            int calories = Integer.parseInt(caloriesField.getText());

            selectedPlat.setNom(nom);
            selectedPlat.setCatégorie(categorie);
            selectedPlat.setType(type);
            selectedPlat.setDescriptif(descriptif);
            selectedPlat.setPrix(prix);
            selectedPlat.setCalories(calories);

            platService.update(selectedPlat, selectedPlat.getId(), getUtilisateur().getId());
            messageLabel.setText("Plat modifié avec succès.");

            platTable.refresh();
            clearFields();
        } else {
            messageLabel.setText("Aucun plat sélectionné.");
        }
    }

    @FXML
    private void handleSupprimer(ActionEvent event) {
        Plat selectedPlat = platTable.getSelectionModel().getSelectedItem();
        if (selectedPlat != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce plat ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                platService.remove(selectedPlat.getId(), getUtilisateur().getId());
                platList.remove(selectedPlat);
                messageLabel.setText("Plat supprimé avec succès.");

                clearFields();
            }
        } else {
            messageLabel.setText("Aucun plat sélectionné.");
        }
    }

    private void fillFieldsWithSelectedPlat(Plat plat) {
        nomField.setText(plat.getNom());
        categorieField.setText(plat.getCatégorie());
        typeField.setText(plat.getType());
        descriptifField.setText(plat.getDescriptif());
        prixField.setText(String.valueOf(plat.getPrix()));
        caloriesField.setText(String.valueOf(plat.getCalories()));
    }

    @FXML
    private void clearFields() {
        nomField.clear();
        categorieField.clear();
        typeField.clear();
        descriptifField.clear();
        prixField.clear();
        caloriesField.clear();
    }

    public void handleActualiser(ActionEvent actionEvent) {
        platList = FXCollections.observableArrayList(platService.findAll(utilisateur.getId()));
        platTable.setItems((ObservableList<Plat>) platList);
    }


    public void exporterEnJSON(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Exporter un fichier JSON");
        dialog.setHeaderText(null);
        dialog.setContentText("Entrez le nom du fichier JSON :");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(nomFichier -> {
            // Appel à la méthode du service pour lire le fichier JSON
            platService.ecrireFichierJson(nomFichier, utilisateur.getId());
        });
    }

    public void exporterEnExcel(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Exporter un fichier Excel");
        dialog.setHeaderText(null);
        dialog.setContentText("Entrez le nom du fichier Excel :");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(nomFichier -> {
            // Appel à la méthode du service pour lire le fichier Excel
            platService.ecrireFichierExcel(nomFichier, utilisateur.getId());
        });
    }

    public void exporterEnTexte(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Importer un fichier texte");
        dialog.setHeaderText(null);
        dialog.setContentText("Entrez le nom du fichier texte :");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(nomFichier -> {
            // Appel à la méthode du service pour lire le fichier texte
            platService.ecrireFichierTexte(nomFichier, utilisateur.getId());
        });
    }

    public void importerFichierJSON(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Importer un fichier JSON");
        dialog.setHeaderText(null);
        dialog.setContentText("Entrez le nom du fichier JSON :");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(nomFichier -> {
            // Appel à la méthode du service pour lire le fichier JSON
            platService.lireFichierJson(nomFichier, utilisateur.getId());
        });
    }

    public void importerFichierExcel(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Importer un fichier Excel");
        dialog.setHeaderText(null);
        dialog.setContentText("Entrez le nom du fichier Excel :");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(nomFichier -> {
            // Appel à la méthode du service pour lire le fichier Excel
            platService.lireFichierExcel(nomFichier, utilisateur.getId());
        });
    }

    public void importerFichierTexte(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Importer un fichier texte");
        dialog.setHeaderText(null);
        dialog.setContentText("Entrez le nom du fichier texte :");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(nomFichier -> {
            // Appel à la méthode du service pour lire le fichier texte
            platService.lireFichierTexte(nomFichier, utilisateur.getId());
        });
    }
}
