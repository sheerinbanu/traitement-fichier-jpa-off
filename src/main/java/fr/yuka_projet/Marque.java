package fr.yuka_projet;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Représente une marque de produit alimentaire
 * <p>Cette classe est annotée avec {@code @Entity} pour indiquer qu'il s'agit d'une entité JPA.</p>
 * <p>Elle est également annotée avec {@code @Table(name="marque")} pour spécifier le nom de la table correspondante dans la base de données.</p>
 */
@Entity
@Table(name="marque")
public class Marque {

    /**
     * L'identifiant unique de la marque dans la base de donnée
     * <p>Ce champ est annoté avec {@code @Id} pour indiquer qu'il s'agit de la clé primaire de l'entité</p>
     * <p>Il est également annoté avec {@code @GeneratedValue(strategy = GenerationType.IDENTITY)} pour spécifier que la valeur de cet identifiant
     * est généré automatiquement par la base de données en utilisant une stratégie d'identité</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Le nom de la marque
     * <p>Ce champ est mappé à la colonne "nom" de la base de données avec les contraintes suivantes :</p>
     * <ul>
     *     <li>Longueur maximale : 255 caractères</li>
     *     <li></li>
     *     <li></li>
     * </ul>
     */
    @Column(name="nom", length = 255, nullable = false, unique = true)
    private String nom;

    /**
     * Représente la liste des produits associés à cette marque
     * <p>Ce champ est annoté avec {@code @OneToMany(mappedBy = "marque")} pour indiquer qu'il s'agit d'une relation un-à-plusieurs
     * avec l'entité {@code Produit}.
     * Le champ {@code marque} dans l'entité {@code Produit} est utilisé pour mapper cette relation.</p>
     */
    @OneToMany(mappedBy = "marque", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Produit> produits = new ArrayList<>();

    /**
     * Un bean entité doit obligatoirement avoir un constructeur sans paramètre pour le jpa
     */
    public Marque(){

    }

    /**
     * Constructeur de la classe Marque
     * @param nom Le nom de la marque
     */
    public Marque(String nom) {
        this.nom = nom;
    }

    /**
     * Obtient l'identifiant unique de la marque
     * @return l'identfiant unique de la marque
     */
    public long getId() {
        return id;
    }

    /**
     * Obtient le nom de la marque
     * @return le nom de la marque
     */
    public String getNom() {
        return nom;
    }

    /**
     * Permet de définir le nom de la marque
     * @param nom Le nom de la marque
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'objet Marque
     * @return Une chaîne de caractères de l'objet Marque
     */
    @Override
    public String toString() {
        return "Marque{" +
                "id_marque=" + id +
                ", nom='" + nom + '\'' +
                ", produits=" + produits +
                '}';
    }
}
