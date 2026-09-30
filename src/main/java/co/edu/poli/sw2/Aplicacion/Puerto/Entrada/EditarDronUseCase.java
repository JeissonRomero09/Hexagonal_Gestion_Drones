package co.edu.poli.sw2.Aplicacion.Puerto.Entrada;

import co.edu.poli.sw2.Dominio.modelo.Dron;

/** Define la operación de aplicación para actualizar un dron y sus asociaciones. */
public interface EditarDronUseCase {
    /**
     * Actualiza los datos del dron y las asociaciones de piloto y sensor.
     *
     * @param dron datos actualizados; su ID identifica el registro
     * @param pilotoId identificador del piloto asociado
     * @param sensorId identificador del sensor asociado
     * @return {@code true} si se actualizó el dron; {@code false} si no existe
     */
    boolean actualizar(Dron dron, int pilotoId, int sensorId);
}