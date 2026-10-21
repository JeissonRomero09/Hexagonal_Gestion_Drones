package co.edu.poli.sw2.Aplicacion.Servicios;

import java.util.List;

import co.edu.poli.sw2.Aplicacion.Puerto.Entrada.EditarDronUseCase;
import co.edu.poli.sw2.Aplicacion.Puerto.Salida.RepositoryDron;
import co.edu.poli.sw2.Dominio.modelo.Dron;

/** Implementa la actualización de drones delegando en el puerto de persistencia. */
public class EditarDronServicio implements EditarDronUseCase {
	/** Puerto de salida utilizado para actualizar drones persistidos. */
	private final RepositoryDron repository;

	/**
	 * Crea el servicio de edición.
	 *
	 * @param repository puerto de salida que actualiza los drones
	 */
	public EditarDronServicio(RepositoryDron repository) {
		this.repository = repository;
	}

	/** {@inheritDoc} */
	@Override
	public boolean actualizar(Dron dron, int pilotoId, List<Integer> sensorIds) {
		return repository.actualizar(dron, pilotoId, sensorIds);
	}
}