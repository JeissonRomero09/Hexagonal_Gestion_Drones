package co.edu.poli.sw2.Dominio.state;

/** Estado en espera: permite despegar o apagar el dron. */
public class ReadyState implements DroneState {
	@Override
	public void encender(DronStateWrapper dron) {
		throw operacionNoPermitida("encender");
	}

	@Override
	public void despegar(DronStateWrapper dron) {
		dron.cambiarEstado(new FlyingState());
	}

	@Override
	public void iniciarAterrizaje(DronStateWrapper dron) {
		throw operacionNoPermitida("iniciar aterrizaje");
	}

	@Override
	public void finalizarAterrizaje(DronStateWrapper dron) {
		throw operacionNoPermitida("finalizar aterrizaje");
	}

	@Override
	public void apagar(DronStateWrapper dron) {
		dron.cambiarEstado(new OffState());
	}

	@Override
	public String getNombre() {
		return "EN ESPERA";
	}

	private IllegalStateException operacionNoPermitida(String operacion) {
		return new IllegalStateException("No se puede " + operacion + " con el dron EN ESPERA.");
	}
}
