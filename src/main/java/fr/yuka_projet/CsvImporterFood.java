package fr.yuka_projet;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import jakarta.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvImporterFood {

    private EntityManager em;

    // Constructeur avec EntityManager
    public CsvImporterFood(EntityManager em) {
        this.em = em;
    }

    public void importCSVToDatabase(String filePath) throws FileNotFoundException, IOException {
        //extraire les données séparés par |
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder().withSeparator('|').build())
                .build()) {

            List<String[]> rows = reader.readAll();
            // Vérifie si le fichier contient des lignes
            if (rows.isEmpty()) {
                throw new IOException("Le fichier CSV est vide.");
            }

            // Ignorer la première ligne (les en-têtes)
            rows.remove(0);

            // Démarrer une transaction
            em.getTransaction().begin();

            for (int lineNumber = 0; lineNumber < rows.size(); lineNumber++) {
                String[] columns = rows.get(lineNumber);

                // Vérification du nombre de colonnes
                if (columns.length < 30) {
                    System.out.println("Ligne " + (lineNumber + 2) + " ignorée : pas assez de colonnes.");
                    continue;
                }

                // Vérifie si la catégorie existe déjà
                Categorie categorie = em.createQuery("SELECT c FROM Categorie c WHERE c.nom = :nom", Categorie.class)
                        .setParameter("nom", columns[0])
                        .getResultStream()
                        .findFirst()
                        .orElse(null);

                if (categorie == null) {
                    // Si la catégorie n'existe pas, créez une nouvelle catégorie et la persister
                    categorie = new Categorie(columns[0]);
                    em.persist(categorie);

                }
                // Vérifie si la marque existe déjà
                Marque marque = em.createQuery("SELECT m FROM Marque m WHERE m.nom = :nom", Marque.class)
                        .setParameter("nom", columns[1])
                        .getResultStream()
                        .findFirst()
                        .orElse(null);

                if (marque == null) {
                    // Si la marque n'existe pas, crée une nouvelle marque
                    marque = new Marque(columns[1]);
                    em.persist(marque);
                }

                    try {
                    // Crée un produit et remplir ses champs
                    Produit produit = new Produit();

                    produit.setCategorie(new Categorie(columns[0]));
                    produit.setMarque(new Marque(columns[1]));
                    produit.setNom(columns[2]);
                    // Vérifie si le produit existe déjà
                    Produit existingProduit = em.createQuery(
                                    "SELECT p FROM Produit p WHERE p.nom = :nom AND p.categorie = :categorie AND p.marque = :marque",
                                    Produit.class)
                            .setParameter("nom", columns[2])
                            .setParameter("categorie", produit.getCategorie())
                            .setParameter("marque", produit.getMarque())
                            .getResultStream()
                            .findFirst()
                            .orElse(null);

                    try {
                        produit.setScoreNutritionnel(NutritionGradeFr.valueOf(columns[3].toUpperCase()));
                    } catch (IllegalArgumentException | NullPointerException e) {
                        System.out.println("Ligne " + (lineNumber + 2) + " ignorée : Score nutritionnel invalide (" + columns[3] + ").");
                        continue;
                    }

                    // Extrait les ingrédients
                    List<Ingredient> ingredients = splitIngredients(columns[4]);
                    produit.setIngredients(ingredients);

                    // Extrait les allergènes
                    List<Allergene> allergens = splitAllergens(columns[28]);
                    produit.setAllergenes(allergens);

                    // Enregistre le produit dans la base de données
                    em.persist(produit);
                } catch (Exception e) {
                    System.out.println("Erreur lors du traitement de la ligne " + (lineNumber + 2) + " : " + e.getMessage());
                }
            }

            // Commit de la transaction
            em.getTransaction().commit();

        } catch (Exception e) {
            // Si une erreur se produit, annule la transaction
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new IOException("Erreur lors de l'importation du fichier CSV", e);
        }
    }

    // Méthode pour découper les ingrédients
    private List<Ingredient> splitIngredients(String ingredientsColumn) {
        // On découpe la chaîne par les séparateurs ",", ":", "et"
        String[] ingredientsArray = ingredientsColumn.split("[,;:et]+");

        List<Ingredient> ingredientsList = new ArrayList<>();

        for (String ingredient : ingredientsArray) {
            String trimmedIngredient = ingredient.trim(); // Supprime les espaces superflus autour de l'ingrédient
            if (!trimmedIngredient.isEmpty()) { // Vérifie que l'ingrédient n'est pas vide
                ingredientsList.add(new Ingredient(trimmedIngredient)); // Ajoute l'ingrédient à la liste
            }
        }

        return ingredientsList;
    }

    // Méthode pour découper les allergènes
    private List<Allergene> splitAllergens(String allergensColumn) {
        // On découpe la chaîne par les séparateurs ",", ";", "-"
        String[] allergensArray = allergensColumn.split("[,;\\-]+");

        List<Allergene> allergenesList = new ArrayList<>();

        for (String allergene : allergensArray) {
            String trimmedAllergene = allergene.trim(); // Supprime les espaces superflus autour de l'allergène
            if (!trimmedAllergene.isEmpty()) { // Vérifie que l'allergène n'est pas vide
                allergenesList.add(new Allergene(trimmedAllergene)); // Ajoute l'allergène à la liste
            }
        }

        return allergenesList;
    }

}
