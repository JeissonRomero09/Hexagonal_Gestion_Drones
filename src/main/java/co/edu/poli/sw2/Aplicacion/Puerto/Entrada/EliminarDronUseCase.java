package co.edu.poli.sw2.Aplicacion.Puerto.Entrada;

/** Define la operación de aplicación para eliminar un dron. */
public interface EliminarDronUseCase {
    /**
     * Elimina el dron identificado por el ID indicado.
     *
     * @param id identificador del dron que se va a eliminar
     * @return {@code true} si se eliminó un registro; {@code false} si no existía
     */
    boolean eliminar(int id);
}