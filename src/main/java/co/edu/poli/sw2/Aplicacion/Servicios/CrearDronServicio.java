package co.edu.poli.sw2.Aplicacion.Servicios;

import java.util.List;

import co.edu.poli.sw2.Aplicacion.Puerto.Entrada.CrearDronUseCase;
import co.edu.poli.sw2.Aplicacion.Puerto.Salida.RepositoryDron;
import co.edu.poli.sw2.Dominio.modelo.Dron;

/** Implementa el caso de uso de creación delegando la persistencia al puerto de salida. */
public class CrearDronServicio implements CrearDronUseCase {
	/** Puerto de salida utilizado para persistir los drones creados. */
	private final RepositoryDron repository;

	/**
	 * Crea el servicio con el repositorio que ejecutará la persistencia.
	 *
	 * @param repository puerto de salida para almacenar drones
	 */
	public CrearDronServicio(RepositoryDron repository) {
		this.repository = repository;
	}

	/** {@inheritDoc} */
	@Override
	public int crear(Dron dron, int pilotoId, List<Integer> sensorIds) {
	    return repository.crear(dron, pilotoId, sensorIds);
	}
}
