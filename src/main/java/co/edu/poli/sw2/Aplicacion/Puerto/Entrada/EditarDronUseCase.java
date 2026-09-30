package co.edu.poli.sw2.Aplicacion.Puerto.Entrada;

import java.util.List;

import co.edu.poli.sw2.Dominio.modelo.Dron;

/**
 * Define la operación de aplicación para actualizar un dron y sus asociaciones.
 */
public interface EditarDronUseCase {

	/**
	 * Actualiza los datos del dron y las asociaciones de piloto y sensor.
	 *
	 * @param dron      datos actualizados; su ID identifica el registro
	 * @param pilotoId  identificador del piloto asociado
	 * @param sensorIds identificadores de los sensores asociados
	 * @return {@code true} si se actualizó el dron; {@code false} si no existe
	 */
	boolean actualizar(Dron dron, int pilotoId, List<Integer> sensorIds);

}