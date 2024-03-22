import java.util.ArrayList;
import java.util.Collections;

public class PaquetDeCarte {

    /**
     * Liste des cartes
     */
    private final ArrayList<Carte> cartes = new ArrayList<>();

    /**
     * Constructeur
     */
    public PaquetDeCarte() {
        for (Carte.Couleur c : Carte.Couleur.values()) {
            for (Carte.Valeur v : Carte.Valeur.values()) {
                cartes.add(new Carte(v, c));
            }
        }
    }

    /**
     * Brasse les cartes
     */
    public void brasser() {
        Collections.shuffle(this.cartes);
    }

    /**
     * Pige une carte
     *
     * @return Carte pigée
     */
    public Carte piger() {
        if (this.cartes.isEmpty()) {
            return null;
        }
        return this.cartes.remove(0);
    }

    /**
     * Retourne les cartes. Le tableau retourné est une copie de la liste originale pour éviter de modifier la liste originale.
     *
     * @return Cartes
     */
    public Carte[] getCartes() {
        return this.cartes.toArray(new Carte[0]);
    }

    /**
     * Retourne le nombre de cartes
     *
     * @return Nombre de cartes
     */
    public int getNombreDeCartes() {
        return this.cartes.size();
    }

}