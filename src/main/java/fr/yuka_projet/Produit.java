package fr.yuka_projet;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  Représente le produit alimentaire
 * <p>Cette classe est annotée avec {@code @Entity} pour indiquer qu'il s'agit d'une entité JPA.</p>
 * <p>Elle est également annotée avec {@code @Table(name="produit")} pour spécifier le nom de la table correspondante dans la base de données.</p>
 */
@Entity
@Table(name="produit")
public class Produit {

    /**
     * L'identifiant unique du produit dans la base de donnée - la clé primaire
     *<p>Ce champ est annoté avec {@code @Id} pour indiquer qu'il s'agit de la clé primaire de l'entité</p>
     *<p>Il est également annoté avec {@code @GeneratedValue(strategy = GenerationType.IDENTITY)} pour spécifier que la valeur de cet identifiant
     *est généré automatiquement par la base de données en utilisant une stratégie d'identité</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Nom du produit, taille de châine max 255, non nul et unique
     */
    @Column(name="nom", length = 255, nullable = false, unique = true)
    private String nom;

    /**
     * représente le score nutritionnel d'un produit, basé sur le système de notation français(Nutri-Score)
     *Ce champ utilise une énumération {@link NutritionGradeFr}
     * pour définir les différentes catégories de scores nutritionnels
     * stocke la valeur de l'énumération sous forme de chaîne de caractère dans la base de données
     * Champ non nul
     * @see NutritionGradeFr
     */
    @Enumerated(EnumType.STRING)
    @Column(name="score_nutritionnel", nullable = false)
    private  NutritionGradeFr scoreNutritionnel;

    /**
     * Champ représentant la relation entre un produit et sa marque.
     * Relation @ManyToOne avec le champ marque indiquant aue plusieurs produits peuvent appartenir à une marque
     * La clé étrangère utilisée pour cette relation est la colonne nommée {@code id_marq} dans la base de données
     */
    @ManyToOne
    @JoinColumn(name="id_marq")
    private Marque marque;

    /**
     * Champ représentant la relation entre un produit et sa catégorie
     * Relation @ManyToOne avec le champ catégorie indiquant que plusieurs produits peuvent appartenir à une catégorie
     * La clé étrangère utilisée pour cette relation est la collonne nommée {@code id_cat} dans la base de données
     */
    @ManyToOne
    @JoinColumn(name="id_cat")
    private Categorie categorie;

    /**
     * représente la liste des allergènes associée à un produit
     * Relation @ManyToMany entre produit et allergène signifiant qu'un produit peut être lié à plusieurs allergènes,
     * et qu'un allergène peut être associé à plusieurs produits.
     * L'annotation {@code @ManyToMany} indique une relation bidirectionnelle,"plusieurs à plusieurs" entre les entités.
     * L'annotation {@code @JoinColumn(name = "produit_id")} définit la clé étrangère dans la table d'association pointant vers l'entité Produit.
     * L'Annotation {@code @InverseJoinColumn(name = "allergen_id")} définit la clé étrangère dans la table d'association
     * pointant vers l'entité Allergene.
     *
     */
    @ManyToMany
    @JoinTable(name="all_pro",
            joinColumns= @JoinColumn(name="produit_id", referencedColumnName=
                    "id"),
            inverseJoinColumns= @JoinColumn(name="allergen_id", referencedColumnName=
                    "id")
    )
    List<Allergene> allergenes = new ArrayList<>();


    /**
     * représente la liste des ingrédients associée à un produit
     * Relation @ManyToMany entre produit et ingrédient signifiant qu'un produit peut contenir plusieurs ingrédients,
     * et qu'un ingrédient peut être contenu dans plusieurs produits.
     * L'annotation {@code @ManyToMany} indique une relation bidirectionnelle,"plusieurs à plusieurs" entre les entités.
     * L'annotation {@code @JoinColumn(name = "produit_id")} définit la clé étrangère dans la table d'association pointant vers l'entité Produit.
     * L'Annotation {@code @InverseJoinColumn(name = "ingredient_id")} définit la clé étrangère dans la table d'association
     * pointant vers l'entité Ingrédient.
     */
    @ManyToMany
    @JoinTable(name="pro_ing",
            joinColumns= @JoinColumn(name="produit_id", referencedColumnName=
                    "id"),
            inverseJoinColumns= @JoinColumn(name="ingredient_id", referencedColumnName=
                    "id")
    )
    List<Ingredient> ingredients = new ArrayList<>();

    /**
     * Constructeur vide
     */
    public Produit(){

    }

    /**
     * Constructeur de la classe Produit
     * @param nom
     * @param scoreNutritionnel
     * @param marque
     * @param categorie
     */
    public Produit(String nom, NutritionGradeFr scoreNutritionnel, Marque marque, Categorie categorie) {
        this.nom = nom;
        this.scoreNutritionnel = scoreNutritionnel;
        this.marque = marque;
        this.categorie = categorie;
    }

    /**
     * Permet d'obtenir l'identifiant unique du produit
     * @return id du produit
     */
    public long getId_produit() {
        return id;
    }

    /**
     * Permet d'obtenir le nom du produit
     * @return le nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Permet de définir le nom
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Permet de récupérer le score nutritionnel du produit
     * @return
     */
    public NutritionGradeFr getScoreNutritionnel() {
        return scoreNutritionnel;
    }

    /**
     * Permet de définir le score nutritionnel
     * @param scoreNutritionnel
     */
    public void setScoreNutritionnel(NutritionGradeFr scoreNutritionnel) {
        this.scoreNutritionnel = scoreNutritionnel;
    }

    /**
     * Permet de récupérer la marque du produit
     * @return
     */
    public Marque getMarque() {
        return marque;
    }

    /**
     * Permet de définir la marque
     * @param marque
     */
    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    /**
     * Permet de récupérer la catégorie auquel appartient le produit
     * @return
     */
    public Categorie getCategorie() {
        return categorie;
    }

    /**
     * Permet de définir la catégorie
     * @param categorie
     */
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    /**
     * Permet d'obtenir la liste d'allergènes relative au produit
     * @return
     */
    public List<Allergene> getAllergenes() {
        return allergenes;
    }

    /**
     * Permet de définir une liste d'allergènes
     * @param allergenes
     */
    public void setAllergenes(List<Allergene> allergenes) {
        this.allergenes = allergenes;
    }

    /**
     * Permet d'obtenir la liste d'ingrédients associés au produit
     * @return
     */
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * Permet de définir une liste d'ingrédients
     * @param ingredients
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'objet Produit
     * @return Une chaîne de caractères de l'objet Produit
     */
    @Override
    public String toString() {
        return "Produit{" +
                "id_produit=" + id +
                ", nom='" + nom + '\'' +
                ", scoreNutritionnel=" + scoreNutritionnel +
                ", marque=" + marque +
                ", categorie=" + categorie +
                ", allergenes=" + allergenes +
                '}';
    }
}
