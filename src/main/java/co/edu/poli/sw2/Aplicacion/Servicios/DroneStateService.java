package co.edu.poli.sw2.Aplicacion.Servicios;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import co.edu.poli.sw2.Aplicacion.Puerto.Entrada.ManageDroneStateUseCase;
import co.edu.poli.sw2.Dominio.state.DronStateWrapper;

/** Mantiene y delega al contexto State asociado a cada dron. */
public class DroneStateService implements ManageDroneStateUseCase {
	private final ConcurrentMap<Integer, DronStateWrapper> drones = new ConcurrentHashMap<>();

	@Override
	public void encender(int dronId) {
		obtenerWrapper(dronId).encender();
	}

	@Override
	public void despegar(int dronId) {
		obtenerWrapper(dronId).despegar();
	}

	@Override
	public void iniciarAterrizaje(int dronId) {
		obtenerWrapper(dronId).iniciarAterrizaje();
	}

	@Override
	public void finalizarAterrizaje(int dronId) {
		obtenerWrapper(dronId).finalizarAterrizaje();
	}

	@Override
	public void apagar(int dronId) {
		obtenerWrapper(dronId).apagar();
	}

	@Override
	public String obtenerEstado(int dronId) {
		return obtenerWrapper(dronId).getNombreEstado();
	}

	private DronStateWrapper obtenerWrapper(int dronId) {
		if (dronId <= 0) {
			throw new IllegalArgumentException("El ID del dron debe ser positivo.");
		}
		return drones.computeIfAbsent(dronId, DronStateWrapper::new);
	}
}