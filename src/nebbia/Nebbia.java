// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
// Consegna 5, variante 1: la nebbia. I contratti ci sono, i campi e i corpi dei metodi li scrivete voi.
package nebbia;

import geometria.Posizione;
import geometria.Rettangolo;

/**
 * Una zona del campo in cui la palla non si vede: quando ci entra sparisce, quando esce ricompare.
 * Gli ostacoli e le racchette nella nebbia si vedono. La nebbia non cambia mai.
 * Quale nebbia c'è in un livello lo decide il MotoreDeiLivelli.
 */
public class Nebbia {

    private final Rettangolo zona;
    private Nebbia(Rettangolo zona) {
        this.zona = zona;
    }

    /** Nessuna nebbia: non nasconde niente. */
    public static Nebbia nessuna() {
        return new Nebbia(null);
    }

    /**
     * La striscia al centro di un campo largo larghezzaCampo e alto altezzaCampo pixel:
     * alta come il campo, larga frazione della larghezza del campo, con il centro al centro del campo.
     * Con frazione 0 è nessuna().
     * Lancia IllegalArgumentException se larghezzaCampo o altezzaCampo non sono positivi,
     * o se frazione non è fra 0 (compreso) e 1 (escluso).
     */
    public static Nebbia alCentro(int larghezzaCampo, int altezzaCampo, double frazione) {
        if ((larghezzaCampo <= 0) || (altezzaCampo <= 0) || (frazione < 0 || frazione >= 1) || Double.isNaN(frazione)) {
            throw new IllegalArgumentException("Argomenti non validi");
        } else if (frazione == 0){
            return nessuna();
        } else {
            double larghezza = larghezzaCampo * frazione;
            double x = (larghezzaCampo - larghezza) / 2;
            int y = 0;
            int altezza = altezzaCampo;
            return new Nebbia(new Rettangolo(x, y, larghezza, altezza));
        }
    }

    /** Vero se la nebbia c'è. */
    public boolean isPresente() {
        if (this.zona == null) return false;
        else return true;
    }

    /** La zona coperta dalla nebbia. Pretende: isPresente(). Lancia IllegalStateException se la nebbia non c'è. */
    public Rettangolo getZona() {
        if (zona == null) {
            throw new IllegalStateException("Non c'è nebbia");
        } else {
            return zona;
        }
    }

    /**
     * Vero se un punto (per esempio il centro della palla) è nella nebbia, bordi compresi.
     * Promette: falso se la nebbia non c'è.
     * Lancia IllegalArgumentException se punto è null.
     */
    public boolean nasconde(Posizione punto) {
        if (punto == null) {
            throw new IllegalArgumentException("Argomento non valido");
        } else if (isPresente() == false) {
            return false;
        } else {
            return punto.x() >= zona.x()
            && punto.x() <= zona.x() + zona.larghezza()
            && punto.y() >= zona.y()
            && punto.y() <= zona.y() + zona.altezza();
        }
    }
}
