package fr.yuka_projet;

/**
 * Enumération représentant les différentes catégories de score nutritionnel selon le système Nutri-Score en France.
 * Cette énumération contient les différentes valeurs possibles pour le score nutritionnel :
 * <ul>
 *   <li>{@code A} : Excellente qualité nutritionnelle</li>
 *   <li>{@code B} : Bonne qualité nutritionnelle</li>
 *   <li>{@code C} : Qualité nutritionnelle moyenne</li>
 *   <li>{@code D} : Qualité nutritionnelle insuffisante</li>
 *   <li>{@code E} : Très mauvaise qualité nutritionnelle</li>
 *   <li>{@code F} : Score nutritionnel non défini (potentiellement une erreur ou une absence de calcul)</li>
 * </ul>
 */
public enum NutritionGradeFr {
    A, B, C, D, E, F;


}

