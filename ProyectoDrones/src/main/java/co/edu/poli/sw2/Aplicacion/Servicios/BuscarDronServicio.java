package co.edu.poli.sw2.Aplicacion.Servicios;

import co.edu.poli.sw2.Aplicacion.Puerto.Entrada.BuscarDronUseCase;
import co.edu.poli.sw2.Aplicacion.Puerto.Salida.RepositoryDron;
import co.edu.poli.sw2.Dominio.modelo.Dron;

/** Implementa la búsqueda individual de drones usando el puerto de persistencia. */
public class BuscarDronServicio implements BuscarDronUseCase {
	/** Puerto de salida utilizado para consultar drones por ID. */
	private final RepositoryDron repository;

	/**
	 * Crea el servicio de búsqueda.
	 *
	 * @param repository puerto de salida que consulta los drones
	 */
	public BuscarDronServicio(RepositoryDron repository) {
		this.repository = repository;
	}

	/** {@inheritDoc} */
	@Override
	public Dron buscar(int id) {
		return repository.buscar(id);
	}
}
