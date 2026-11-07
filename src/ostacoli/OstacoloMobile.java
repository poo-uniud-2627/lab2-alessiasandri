// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
// Consegna 4: gli ostacoli. Scadenza: domenica 15 novembre 2026.
package ostacoli;

import geometria.Rettangolo;
import java.awt.Color;

/**
 * L'ostacolo mobile: va su e giù fra un limite alto e un limite basso, sempre alla stessa velocità,
 * e ai limiti torna indietro. Non si rompe mai: colpito, non cambia niente.
 *
 * IL PEZZO MANCANTE: i corpi dei metodi segnati «scrivete qui» e i campi che vi servono.
 * I contratti dei metodi sono quelli dell'interfaccia Ostacolo (aprite Ostacolo.java), più quello
 * che dice questo commento e quelli sopra il costruttore e sopra avanza.
 */
public class OstacoloMobile implements Ostacolo {
    public static final Color COLORE = new Color(80, 200, 120);

    private Rettangolo area;   // non è final: l'ostacolo si muove
    private final double velocita;
    private final double limiteAlto;
    private final double limiteBasso;
    private boolean versoGiu;

    /**
     * Pretende: area non è null; velocita è maggiore di 0; l'area sta fra i due limiti
     * (limiteAlto <= area.y() e area.y() + area.altezza() <= limiteBasso).
     * Promette: un ostacolo che sta in area e che, al primo avanza, comincia a scendere.
     */
    public OstacoloMobile(Rettangolo area, double velocita, double limiteAlto, double limiteBasso) {
        this.area = area;

        if (!(velocita > 0)) throw new IllegalArgumentException("la velocità deve essere maggiore di 0. Era " + velocita);
        this.velocita = velocita;

        if (limiteAlto > area.y()) throw new IllegalArgumentException("il limite alto deve essere minore o uguale all'altezza del rettangolo. Era " + limiteAlto + ". Altezza: " + area.y());
        this.limiteAlto = limiteAlto;

        if (limiteBasso < area.y() + area.altezza()) throw new IllegalArgumentException("il limite basso deve essere maggiore o uguale alla base del rettangolo. Era " + limiteBasso + ". Altezza: " + area.y());
        this.limiteBasso = limiteBasso;

        this.versoGiu = true;
    }

    @Override
    public Rettangolo getArea() { return area; }

    /** Non si rompe mai: il colpo non cambia niente (né dove sta, né come si muove). */
    @Override
    public void colpisci(Ostacoli campo) {
    }

    @Override
    public boolean isRotto() {
        return false;
    }

    /** Promette: COLORE. */
    @Override
    public Color getColore() {
        return COLORE;
    }

    /**
     * Promette: l'ostacolo si sposta in verticale di velocita pixel nel verso in cui sta andando
     * (all'inizio verso il basso). Se così arriverebbe su un limite o lo supererebbe, si ferma
     * esattamente sul limite e cambia verso: al passo dopo va dall'altra parte.
     * Il bordo alto non va mai sopra limiteAlto, il bordo basso mai sotto limiteBasso
     * (area.y() + area.altezza() <= limiteBasso); x, larghezza e altezza non cambiano.
     */
    @Override
    public void avanza() {
        // si sposta nel verso giusto
        if (versoGiu) {
            area = new Rettangolo(area.x(), area.y() + velocita, area.larghezza(), area.altezza());
        }
        else {
            area = new Rettangolo(area.x(), area.y() - velocita, area.larghezza(), area.altezza());
        }

        // controlla se tocca un limite
        if (area.y() + area.altezza() >= limiteBasso) {
            versoGiu = false;
            area = new Rettangolo(area.x(), limiteBasso - area.altezza(), area.larghezza(), area.altezza());
        }
        else if (area.y() <= limiteAlto) {
            versoGiu = true;
            area = new Rettangolo(area.x(), limiteAlto, area.larghezza(), area.altezza());
        }
    }
}
