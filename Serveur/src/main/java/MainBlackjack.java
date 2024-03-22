import java.util.ArrayList;

public class MainBlackjack {

    private ArrayList<Carte> cartes = new ArrayList<>();

    /** Constructeur */
    public MainBlackjack() {}

    /**
     * Ajoute une carte à la main
     * @param carte
     */
    public void ajouterCarte(Carte carte) {
        this.cartes.add(carte);
    }

    /**
     * Retourne la valeur totale de la main. S'occupe de gérer les as et les figures.
     * @return Le pointage de la main
     */
    public int getValeurTotale() {
        int valeurMain = 0;
        int nombreAs = 0;

        for (Carte carte : this.cartes) {
            if (carte.getValeur().equals(Carte.Valeur.AS)) {
                nombreAs++;
            } else if (carte.getValeur().ordinal() >= Carte.Valeur.DIX.ordinal()) {
                valeurMain += 10;
            } else {
                valeurMain += carte.getValeur().ordinal() + 1;
            }
        }

        // Gérer les as
        while (nombreAs > 0 && valeurMain + 11 + (nombreAs - 1) > 21) {
            valeurMain += 1;
            nombreAs--;
        }
        for (int i = 0; i < nombreAs; i++) {
            if (valeurMain + 11 <= 21) {
                valeurMain += 11;
            } else {
                valeurMain += 1;
            }
        }

        return valeurMain;
    }

    /**
     * Retourne les cartes de la main. Le tableau retourné est une copie de la liste originale pour éviter de modifier la liste originale.
     * @return Un tableau de cartes
     */
    public Carte[] getMain() {
        return this.cartes.toArray(new Carte[0]);
    }

    /**
     * Retourne si la main est un blackjack, c'est-à-dire une main de 2 cartes valant 21 points.
     * @return Si la main est un blackjack
     */
    public boolean estBlackjack() {
        return this.cartes.size() == 2 && this.getValeurTotale() == 21;
    }

    /**
     * Retourne si la main est pleine, c'est-à-dire une main de 5 cartes.
     * @return Si la main est pleine
     */
    public boolean estPleine() {
        return this.cartes.size() == 5;
    }

    /**
     * Retourne si la main est brûlée, c'est-à-dire une main de plus de 21 points.
     * @return Si la main est brûlée
     */
    public boolean estBrulee() {
        return this.getValeurTotale() > 21;
    }

}