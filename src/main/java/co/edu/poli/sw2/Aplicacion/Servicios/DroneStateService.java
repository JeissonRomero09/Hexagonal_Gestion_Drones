package co.edu.poli.sw2.Aplicacion.Servicios;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import co.edu.poli.sw2.Aplicacion.Puerto.Entrada.ManageDroneStateUseCase;
import co.edu.poli.sw2.Dominio.modelo.Dron;
import co.edu.poli.sw2.Dominio.state.DronStateWrapper;
import co.edu.poli.sw2.Dominio.state.OffState;

/** Mantiene y delega al contexto State asociado a cada dron. */
public class DroneStateService implements ManageDroneStateUseCase {
	private final ConcurrentMap<Integer, DronStateWrapper> drones = new ConcurrentHashMap<>();

	@Override
	public void encender(Dron dron) {
		obtenerWrapper(dron).encender();
	}

	@Override
	public void despegar(Dron dron) {
		obtenerWrapper(dron).despegar();
	}

	@Override
	public void iniciarAterrizaje(Dron dron) {
		obtenerWrapper(dron).iniciarAterrizaje();
	}

	@Override
	public void finalizarAterrizaje(Dron dron) {
		obtenerWrapper(dron).finalizarAterrizaje();
	}

	@Override
	public void apagar(Dron dron) {
		obtenerWrapper(dron).apagar();
	}

	@Override
	public String obtenerEstado(Dron dron) {
		return obtenerWrapper(dron).getNombreEstado();
	}

	private DronStateWrapper obtenerWrapper(Dron dron) {
		if (dron == null) {
			throw new IllegalArgumentException("El dron es obligatorio.");
		}
		if (dron.getId() <= 0) {
			throw new IllegalArgumentException("El ID del dron debe ser positivo.");
		}
		return drones.computeIfAbsent(dron.getId(), id -> new DronStateWrapper(dron, new OffState()));
	}
}