package co.edu.poli.sw2.Aplicacion.Servicios;

import java.util.List;

import co.edu.poli.sw2.Aplicacion.Puerto.Entrada.BuscarListaDronesUseCase;
import co.edu.poli.sw2.Aplicacion.Puerto.Salida.RepositoryDron;
import co.edu.poli.sw2.Dominio.modelo.Dron;

/** Implementa la consulta completa del inventario de drones. */
public class BuscarListaDronesServicio implements BuscarListaDronesUseCase {
	/** Puerto de salida utilizado para consultar el inventario. */
	private final RepositoryDron repository;

	/**
	 * Crea el servicio de listado.
	 *
	 * @param repository puerto de salida que recupera los drones
	 */
	public BuscarListaDronesServicio(RepositoryDron repository) {
		this.repository = repository;
	}

	/** {@inheritDoc} */
	@Override
	public List<Dron> buscarTodos() {
		return repository.buscarTodos();
	}
}
