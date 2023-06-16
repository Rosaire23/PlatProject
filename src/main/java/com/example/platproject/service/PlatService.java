package com.example.platproject.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.platproject.dao.PlatDao;
import com.example.platproject.dao.PlatDaoImpl.PlatDaoImpl;
import com.example.platproject.entities.Plat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class PlatService implements PlatFile {
    private PlatDao platDao = new PlatDaoImpl();
    private List<Plat> plat = new ArrayList<>();

    public List<Plat> findAll(int userId) {
        return platDao.findAll(userId);
    }
    public List<Plat> findByCategorie(String c, int userId) {
        return platDao.findByCategorie(c, userId);
    }
    public Plat findById(int id, int userId) {
        return platDao.findById(id, userId);
    }

    public void save(Plat plat, int userId) {
        platDao.save(plat, userId);
    }
    public void update(Plat plat, int id, int userId) {
        platDao.update(plat, id, userId);
    }
    public void remove(int i, int userId) {
        platDao.delete(i, userId);
    }

    @Override
    public void lireFichierTexte(String nomFichier, int userId) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(nomFichier));
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(",");
                try {
                    addPlat(parts[0], parts[1], parts[2], parts[3], Double.parseDouble(parts[4]),Integer.parseInt(parts[5]), userId);
                } catch (Exception e){
                    System.out.println("Erreur de Saisie ");
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ecrireFichierTexte(String nomFichier, int userId) {
        try {
            plat = this.findAll(userId);
            BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier));
            for (Plat plats : plat) {
                String line = plats.getNom() + "," + plats.getCatégorie() + "," + plats.getType() + "," + plats.getDescriptif() + "," + plats.getPrix() + "," + plats.getCalories();
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lireFichierExcel(String nomFichier, int userId) {
        try {
            FileInputStream file = new FileInputStream(new File(nomFichier));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            List<Plat> plats = new ArrayList<>();
            rowIterator.hasNext();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                String nom = "";
                String categorie = "";
                String type = "";
                String descriptif = "";
                double prix=0;
                int calorie = 0;

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();
                    switch (columnIndex) {
                        case 0:
                            nom = cell.getStringCellValue();
                            break;
                        case 1:
                            categorie = cell.getStringCellValue();
                            break;
                        case 2:
                            type = cell.getStringCellValue();
                            break;

                        case 3:
                            descriptif = cell.getStringCellValue();
                            break;
                        case 4:
                            if(cell.getCellType() == CellType.NUMERIC) {
                                prix = cell.getNumericCellValue();
                            } else if(cell.getCellType() == CellType.STRING) {
                                prix = Double.parseDouble(cell.getStringCellValue());
                            }
                            break;
                        case 5:
                            if(cell.getCellType() == CellType.NUMERIC) {
                                calorie = (int) cell.getNumericCellValue();
                            } else if(cell.getCellType() == CellType.STRING) {
                                calorie = Integer.parseInt(cell.getStringCellValue());
                            }
                            break;
                    }
                }
                try {
                    addPlat(nom, categorie, type, descriptif,prix,calorie, userId);
                } catch (Exception e){
                    System.out.println("Erreur de Saisie " + e);
                }
            }
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ecrireFichierExcel(String nomFichier, int userId) {
        plat = this.findAll(userId);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Plats");

        // Créer un TreeMap pour trier les plats par ordre alphabétique du nom
        Map<String, Plat> sortedPlats = new TreeMap<>();
        for (Plat plats : plat) {
            sortedPlats.put(plats.getNom(), plats);
        }

        Set<String> keySet = sortedPlats.keySet();
        int rownum = 0;
        for (String key : keySet) {
            XSSFRow row = sheet.createRow(rownum++);
            Plat plats = sortedPlats.get(key);

            Cell nomCell = row.createCell(0);
            nomCell.setCellValue(plats.getNom());

            Cell categorieCell = row.createCell(1);
            categorieCell.setCellValue(plats.getCatégorie());

            Cell typeCell = row.createCell(2);
            typeCell.setCellValue(plats.getType());

            Cell descripCell = row.createCell(3);
            descripCell.setCellValue(plats.getDescriptif());

            Cell prixCell = row.createCell(4);
            prixCell.setCellValue(plats.getPrix());

            Cell calorieCell = row.createCell(5);
            calorieCell.setCellValue(plats.getCalories());
        }
        try {
            FileOutputStream out = new FileOutputStream(new File(nomFichier));
            workbook.write(out);
            out.close();
            System.out.println("Fichier Excel écrit avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lireFichierJson(String nomFichier, int userId) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(nomFichier));
            JSONArray jsonArray = (JSONArray) obj;
            Iterator<?> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                JSONObject jsonObject = (JSONObject) iterator.next();
                String nom = (String) jsonObject.get("nom");
                String categorie = (String) jsonObject.get("categorie");
                String type = (String) jsonObject.get("type");
                String descriptif = (String) jsonObject.get("descriptif");
                String prix= (String) jsonObject.get("prix");
                String calorie = (String) jsonObject.get("calorie");
                try {
                    addPlat(nom, categorie, type, descriptif,Double.parseDouble(prix), Integer.parseInt(calorie), userId);
                } catch (Exception e){
                    System.out.println("Erreur de Saisie ");
                }
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ecrireFichierJson(String nomFichier, int userId) {
        try {
            plat = this.findAll(userId);
            // Création d'un objet Gson pour la sérialisation en JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // Ouverture du fichier en écriture
            FileWriter writer = new FileWriter(nomFichier);

            // Sérialisation de la liste de plats en JSON et écriture dans le fichier
            gson.toJson(plat, writer);

            // Fermeture du fichier
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPlat(String nom, String categorie, String type, String descriptif,Double prix, int calorie, int userId) {
        try {
                Plat plat = new Plat(nom, categorie, type, descriptif, prix, calorie);
                platDao.save(plat, userId);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private boolean platExiste(Plat plat, int userId) {
        for (Plat plat1 : platDao.findAll(userId)){
            if (plat1.equals(plat)) return true;
        }
        return false;
    }
}
