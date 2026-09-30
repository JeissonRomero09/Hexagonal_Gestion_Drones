package co.edu.poli.sw2.Aplicacion.Puerto.Salida;

import java.util.List;

import co.edu.poli.sw2.Dominio.modelo.Dron;

/** Puerto de salida que abstrae la persistencia de drones para la aplicación. */
public interface RepositoryDron {
	/**
	 * Persiste un dron junto con sus asociaciones.
	 *
	 * @param dron datos del dron
	 * @param pilotoId identificador del piloto
	 * @param sensorId identificador del sensor
	 * @return identificador generado para el dron
	 */
	int crear(Dron dron, int pilotoId, int sensorId);

	/**
	 * Recupera un dron por su ID.
	 *
	 * @param id identificador del dron
	 * @return el dron encontrado o {@code null} si no existe
	 */
	Dron buscar(int id);

	/**
	 * Recupera todos los drones persistidos.
	 *
	 * @return lista de drones, posiblemente vacía
	 */
	List<Dron> buscarTodos();

	/**
	 * Actualiza los datos y las asociaciones del dron.
	 *
	 * @param dron datos actualizados; el ID identifica el registro
	 * @param pilotoId identificador del piloto
	 * @param sensorId identificador del sensor
	 * @return {@code true} si hubo un registro actualizado
	 */
	boolean actualizar(Dron dron, int pilotoId, int sensorId);

	/**
	 * Elimina un dron por su ID.
	 *
	 * @param id identificador del dron
	 * @return {@code true} si se eliminó un registro
	 */
	boolean eliminar(int id);
}
