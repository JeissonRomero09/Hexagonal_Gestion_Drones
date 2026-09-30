package co.edu.poli.sw2.Aplicacion.Servicios;

import co.edu.poli.sw2.Aplicacion.Puerto.Entrada.EliminarDronUseCase;
import co.edu.poli.sw2.Aplicacion.Puerto.Salida.RepositoryDron;

/** Implementa la eliminación de drones delegando en el repositorio. */
public class EliminarDronServicio implements EliminarDronUseCase {
	/** Puerto de salida utilizado para eliminar drones persistidos. */
	private final RepositoryDron repository;

	/**
	 * Crea el servicio de eliminación.
	 *
	 * @param repository puerto de salida que elimina los drones
	 */
	public EliminarDronServicio(RepositoryDron repository) {
		this.repository = repository;
	}

	/** {@inheritDoc} */
	@Override
	public boolean eliminar(int id) {
		return repository.eliminar(id);
	}
}
