
package co.edu.poli.sw2.Dominio.modelo;

/**
 * Representa un dron especializado para actividades de vigilancia.
 *
 * <p>Esta clase extiende {@link Dron} con la capacidad de detección térmica.</p>
 *
 * @author Jeisson Romero
 * @version 3.0
 */
public class Vigilancia extends Dron {

    /**
     * Indica si el dron cuenta con capacidad de detección térmica.
     */
    private boolean deteccionTermica;

    /**
        * Crea un dron de vigilancia sin valores iniciales.
     */
    public Vigilancia() {
        super();
    }

    /**
     * Constructor de la clase Vigilancia.
     *
     * @param id identificador único del dron
     * @param serial número serial del dron
     * @param modelo modelo del dron
     * @param fabricante fabricante del dron
     * @param peso peso del dron
     * @param deteccionTermica indica si el dron cuenta con detección térmica
     */
    public Vigilancia(int id, String serial, String modelo, String fabricante,
                      int peso, boolean deteccionTermica) {

        super(id, serial, modelo, fabricante, peso);
        this.deteccionTermica = deteccionTermica;
    }

    

    /**
     * Verifica si el dron cuenta con detección térmica.
     *
     * @return {@code true} si cuenta con detección térmica;
     *         {@code false} en caso contrario
     */
    public boolean isDeteccionTermica() {

        return deteccionTermica;
    }

    /**
     * Modifica el estado de la detección térmica.
     *
     * @param deteccionTermica nuevo estado de la detección térmica
     */
    public void setDeteccionTermica(boolean deteccionTermica) {

        this.deteccionTermica = deteccionTermica;
    }

    /**
     * Devuelve una representación textual del objeto Vigilancia.
     *
     * @return cadena de texto con los datos del dron de vigilancia
     */
    @Override
    public String toString() {

        return "Vigilancia{" +
                "id=" + getId() +
                ", serial='" + getSerial() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", fabricante='" + getFabricante() + '\'' +
                ", peso=" + getPeso() +
                ", deteccionTermica=" + deteccionTermica +
                '}';
    }
}