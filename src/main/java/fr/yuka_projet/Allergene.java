package fr.yuka_projet;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente l'allergène que peut contenir un produit alimentaire
 * <p>Cette classe est annotée avec {@code @Entity} pour indiquer qu'il s'agit d'une entité JPA.</p>
 * <p>Elle est également annotée avec {@code @Table(name="allergene")} pour spécifier le nom de la table correspondante dans la base de données.</p>
 */
@Entity
@Table(name="allergene")
public class Allergene {

    /**
     * Représente l'identifiant unique de l'allergène dans la base de donnée - la clé primaire
     *<p>Ce champ est annoté avec {@code @Id} pour indiquer qu'il s'agit de la clé primaire de l'entité</p>
     *<p>Il est également annoté avec {@code @GeneratedValue(strategy = GenerationType.IDENTITY)} pour spécifier que la valeur de cet identifiant
     *est généré automatiquement par la base de données en utilisant une stratégie d'identité</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Le nom de l'allergène
     * <p>Ce champ est mappé à la colonne "nom" de la base de données avec les contraintes suivantes :</p>
     * <ul>
     *     <li>La longueur maximale : 255</li>
     *     <li>Ne peut pas être nul</li>
     *     <li>Doit être unique</li>
     * </ul>
     */
    @Column(name="nom", length = 255, nullable = false, unique = true)
    private String nom;

    /**
     * Représente la liste des produits contenant cette allergène
     * Elle est lié à une table d'association dans la base de donnée appelée "all_pro" qui contient les 2 clés étrangères :
     * "id_all" pointant l'identifiant unique de l'allergène et "id_pro" pointant l'identifiant unique du produit
     */
    @ManyToMany
    @JoinTable(name="all_pro",
            joinColumns= @JoinColumn(name="allergen_id", referencedColumnName=
                    "id"),
            inverseJoinColumns= @JoinColumn(name="produit_id", referencedColumnName=
                    "id")
    )
    List<Produit> produits = new ArrayList<>();

    /**
     * Un bean entité doit obligatoirement avoir un constructeur sans paramètre pour le jpa
     */
    public Allergene(){

    }

    /**
     * Le constructeur de la classe Allergène
     * @param nom le nom de l'allergène
     */
    public Allergene(String nom) {
        this.nom = nom;
    }

    /**
     * Obtient l'identifiant unique de l'allergène
     * @return
     */
    public long getId_allergene() {
        return id;
    }

    /**
     * Permet d'obtenir le nom de l'allergène
     * @return Le nom de l'allergène
     */
    public String getNom() {
        return nom;
    }

    /**
     * Permet de définir le nom de l'allergène
     * @param nom Le nom de l'allergène
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Permet d'obtenir la liste des produits contenant cette allergène
     * @return Une liste de produits
     */
    public List<Produit> getProduits() {
        return produits;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'objet Allergène
     * @return Une chaîne de caractères représentant l'objet Allergène
     */
    @Override
    public String toString() {
        return "Allergene{" +
                "id_allergene=" + id +
                ", nom='" + nom + '\'' +
                ", produits=" + produits +
                '}';
    }
}
