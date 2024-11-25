package fr.yuka_projet;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  Représente l'ingrédient que contient un  produit alimentaire
 * <p>Cette classe est annotée avec {@code @Entity} pour indiquer qu'il s'agit d'une entité JPA.</p>
 * <p>Elle est également annotée avec {@code @Table(name="ingredient")} pour spécifier le nom de la table correspondante dans la base de données.</p>
 */
@Entity
@Table(name="ingredient")
public class Ingredient {

    /**
     * L'identifiant unique de l'ingrédient dans la base de donnée - la clé primaire
     *<p>Ce champ est annoté avec {@code @Id} pour indiquer qu'il s'agit de la clé primaire de l'entité</p>
     *<p>Il est également annoté avec {@code @GeneratedValue(strategy = GenerationType.IDENTITY)} pour spécifier que la valeur de cet identifiant
     *est généré automatiquement par la base de données en utilisant une stratégie d'identité</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Le nom de l'ingrédient
     * <p>Ce champ est mappé à la colonne "nom" de la base de données avec les contraintes suivatntes :</p>
     * <ul>
     *     <li>Longueur maximale : 255 caractères</li>
     *     <li>Ne peut pas être nul</li>
     *     <li>Doit être unique</li>
     * </ul>
     */
    @Column(name="nom", length = 255, nullable = false, unique = true)
    private String nom;

    /**
     * Un bean entité doit obligatoirement avoir un constructeur sans paramètre pour le jpa
     */
    public Ingredient(){

    }

    /**
     * Le constructeur de la classe Ingredient
     * @param nom le nom de l'ingrédient
     */
    public Ingredient(String nom) {
        this.nom = nom;
    }

    /**
     * Représente la liste des produits associés à cet ingrédient
     * Elle est lié à une table d'association dans la base de donnée appelée "pro_ing" qui contient les 2 clés étrangères :
     * "id_ing" pointant l'identifiant unique de l'ingrédient et "id_pro" pointant l'identifiant unique du produit
     */
    @ManyToMany
    @JoinTable(name="pro_ing",
            joinColumns= @JoinColumn(name="ingredient_id", referencedColumnName=
                    "id"),
            inverseJoinColumns= @JoinColumn(name="produit_id", referencedColumnName=
                    "id")
    )
    List<Produit> produits = new ArrayList<>();

    /**
     * Obtient l'identifiant unique de l'ingrédient
     * @return
     */
    public long getId_ingredient() {
        return id;
    }

    /**
     * Permet d'obtenir le nom de l'ingrédient
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Permet de définir un nom
     * @param nom de l'ingrédient
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Permet d'obtenir une liste de produits
     * @return la liste
     */
    public List<Produit> getProduits() {
        return produits;
    }

    /**
     * Permet de définir la liste de produits
     * @param produits
     */
    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }


    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'objet Ingrédient
     * @return Une chaîne de caractères représentant l'objet Ingrédient
     */
    @Override
    public String toString() {
        return "Ingredient{" +
                "id_ingredient=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
