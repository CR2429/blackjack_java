public class Carte {

    /**
     * Valeurs possibles pour une carte
     */
    public enum Valeur {
        AS, DEUX, TROIS, QUATRE, CINQ, SIX, SEPT, HUIT, NEUF, DIX, VALET, DAME, ROI
    }

    /**
     * Couleurs possibles pour une carte
     */
    public enum Couleur {
        COEUR, PIQUE, TREFLE, CARREAU
    }

    /**
     * Valeur et couleur de la carte
     */
    private Valeur valeur;
    private Couleur couleur;

    /**
     * Constructeur
     *
     * @param valeur  Valeur de la carte
     * @param couleur Couleur de la carte
     */
    public Carte(Valeur valeur, Couleur couleur) {
        this.valeur = valeur;
        this.couleur = couleur;
    }

    /**
     * Retourne la représentation textuelle de la carte
     */
    @Override
    public String toString() {
        return String.format("%s de %s", this.valeur, this.couleur);
    }

    /**
     * Retourne la valeur de la carte
     */
    public Valeur getValeur() {
        return valeur;
    }

    /**
     * Retourne la couleur de la carte
     */
    public Couleur getCouleur() {
        return couleur;
    }
}