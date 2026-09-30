package co.edu.poli.sw2.Dominio.modelo;

/**
 * Representa un piloto dentro del sistema de gestión de drones.
 *
 * @author Jeison Romero
 * @version 1.0
 */
public class Piloto {

    /**
     * Identificador único del piloto.
     */
    private int id;

    /**
     * Nombre del piloto.
     */
    private String nombre;

    /**
     * Experiencia del piloto.
     */
    private String experiencia;

    /**
     * Número telefónico del piloto.
     */
    private int telefono;

    /**
     * Constructor vacío.
     */
    public Piloto() {
    }

    /**
     * Constructor con todos los atributos.
     *
     * @param id identificador del piloto.
     * @param nombre nombre del piloto.
     * @param experiencia experiencia del piloto.
     * @param telefono teléfono del piloto.
     */
    public Piloto(int id, String nombre, String experiencia, int telefono) {
        this.id = id;
        this.nombre = nombre;
        this.experiencia = experiencia;
        this.telefono = telefono;
    }

    /**
     * Obtiene el identificador del piloto.
     *
     * @return identificador único
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del piloto.
     *
     * @param id nuevo identificador
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del piloto.
     *
     * @return nombre del piloto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del piloto.
     *
     * @param nombre nombre del piloto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el nivel de experiencia del piloto.
     *
     * @return descripción de la experiencia
     */
    public String getExperiencia() {
        return experiencia;
    }

    /**
     * Establece el nivel de experiencia del piloto.
     *
     * @param experiencia descripción de la experiencia
     */
    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    /**
     * Obtiene el teléfono del piloto.
     *
     * @return número telefónico
     */
    public int getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del piloto.
     *
     * @param telefono nuevo número telefónico
     */
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    /**
     * Devuelve una representación textual del piloto.
     *
     * @return datos principales del piloto en formato de texto
     */
    @Override
    public String toString() {
        return "Piloto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", experiencia='" + experiencia + '\'' +
                ", telefono=" + telefono +
                '}';
    }
}