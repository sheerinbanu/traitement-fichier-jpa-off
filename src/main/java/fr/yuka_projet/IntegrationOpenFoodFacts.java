package fr.yuka_projet;

import com.opencsv.exceptions.CsvValidationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.io.FileNotFoundException;
import java.io.IOException;

public class IntegrationOpenFoodFacts {

    public static void main(String[] args) {
        // Créer l'EntityManagerFactory et l'EntityManager
        EntityManagerFactory dbFactory = null;
        EntityManager em = null;

        try {
            dbFactory = Persistence.createEntityManagerFactory("food"); // Nom de l'unité de persistance
            em = dbFactory.createEntityManager();

            // Initialisation de CsvImporterFood avec l'EntityManager
            CsvImporterFood importer = new CsvImporterFood(em);

            System.out.println("Spring Boot started successfully !!");

            // Appeler la méthode d'importation de CSV dans la base de données
            importer.importCSVToDatabase("C:\\Temp\\open-food-facts.csv");

        } catch (IOException e) { // Gérer  IOException
            System.err.println("Erreur de lecture/écriture : " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Fermer EntityManager et EntityManagerFactory
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (dbFactory != null) {
                dbFactory.close();
            }
        }
    }
}
