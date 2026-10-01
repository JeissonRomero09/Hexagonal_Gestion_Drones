package co.edu.poli.sw2.Dominio.modelo;

/** Representa un sensor que puede estar asociado a un dron. */
public class Sensores {

    /** Identificador único del sensor. */
    private int id;
    /** Tipo de sensor, por ejemplo cámara o LiDAR. */
    private String tipo;
    /** Fabricante del sensor. */
    private String fabricante;

    /** Crea un sensor sin valores iniciales. */
    public Sensores() {
    }

    /**
     * Crea un sensor sin identificador persistido.
     *
     * @param tipo categoría del sensor
     * @param fabricante fabricante del sensor
     */
    public Sensores(String tipo, String fabricante) {
        this.tipo = tipo;
        this.fabricante = fabricante;
    }

    /**
     * Crea un sensor con todos sus datos.
     *
     * @param id identificador único
     * @param tipo categoría del sensor
     * @param fabricante fabricante del sensor
     */
    public Sensores(int id, String tipo, String fabricante) {
        this.id = id;
        this.tipo = tipo;
        this.fabricante = fabricante;
    }

    /** @return identificador único del sensor */
    public int getId() {
        return id;
    }

    /** @param id nuevo identificador del sensor */
    public void setId(int id) {
        this.id = id;
    }

    /** @return categoría del sensor */
    public String getTipo() {
        return tipo;
    }

    /** @param tipo nueva categoría del sensor */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /** @return fabricante del sensor */
    public String getFabricante() {
        return fabricante;
    }

    /** @param fabricante nuevo fabricante del sensor */
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    /** @return representación textual de los datos del sensor */
    @Override
    public String toString() {
        return "Sensores [id=" + id + ", tipo=" + tipo + ", fabricante=" + fabricante + "]";
    }
}