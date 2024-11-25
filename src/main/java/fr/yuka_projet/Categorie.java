package fr.yuka_projet;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente une catégorie à laquelle peut appartenir un produit alimentaire
 * <p>Cette classe est annotée avec {@code @Entity} pour indiquer qu'il s'agit d'une entité JPA.</p>
 * <p>Elle est également annotée avec {@code @Table(name="categorie")} pour spécifier le nom de la table correspondante dans la base de données.</p>
 */
@Entity
@Table(name="categorie")
public class Categorie {

     /**
      * L'identifiant unique de la catégorie dans la base de donnée - la clé primaire
      *<p>Ce champ est annoté avec {@code @Id} pour indiquer qu'il s'agit de la clé primaire de l'entité</p>
      *<p>Il est également annoté avec {@code @GeneratedValue(strategy = GenerationType.IDENTITY)} pour spécifier que la valeur de cet identifiant
      *est généré automatiquement par la base de données en utilisant une stratégie d'identité</p>
      */
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private long id;

     /**
      * Le nom de la catégorie
      * <p>Ce champ est mappé à la colonne "nom" dans la base de données avec les contraintes suivantes :</p>
      * <ul>
      *     <li>Longueur maximale : 255 caractères</li>
      *     <li>Ne doit pas ête nul</li>
      *     <li>Doit être unique</li>
      * </ul>
      */
     @Column(name="nom", length = 255, nullable = false, unique = true)
     private String nom;

     /**
      * Représente la liste des produits appartenant à cette catégorie
      * <p>Ce champ est annoté avec {@code @OneToMany(mappedBy = "categorie")} pour indiquer qu'il s'agit d'une relation un-à-plusieurs
      * avec l'entité {@code Produit}.
      * Le champ {@code categorie} dans l'entité {@code Produit} est utilisé pour mapper cette relation.</p>
      */
     @OneToMany(mappedBy = "categorie")
     List<Produit> produits = new ArrayList<>();

     /**
      * Un bean entité doit obligatoirement avoir un constructeur sans paramètre pour le jpa
      */
     public Categorie(){

     }

     /**
      * Constructeur de la classe Categorie
      * @param nom
      */
     public Categorie(String nom) {
          this.nom = nom;
     }

     /**
      * Permet d'obtenir l'identité unique de la catégorie
      * @return l'identité unique de la catégorie
      */
     public long getId_categorie() {
          return id;
     }

     /**
      * Permet d'obtenir le nom de la catégorie
      * @return le nom de la catégorie
      */
     public String getNom() {
          return nom;
     }

     /**
      * Définir un nom pour la catégorie
      * @param nom Le nom de la catégorie
      */
     public void setNom(String nom) {
          this.nom = nom;
     }

     /**
      * Permet d'obtenir la liste des produits appartenant à cette catégorie
      * @return une liste de produits de cette catégorie
      */
     public List<Produit> getProduits() {
          return produits;
     }

     public long getId() {
          return id;
     }

     /**
      * Retourne une représentation sous forme de chaîne de caractères de l'objet Catégorie
      * @return une chaîne de caractère représentant l'objet Catégorie
      */
     @Override
     public String toString() {
          return "Categorie{" +
                  "id_categorie=" + id +
                  ", nom='" + nom + '\'' +
                  ", produits=" + produits +
                  '}';
     }
}
