package co.edu.poli.sw2.Dominio.state;

/** Estado volando: permite iniciar el proceso de aterrizaje. */
public class FlyingState implements DroneState {
	@Override
	public void encender(DronStateWrapper dron) {
		throw operacionNoPermitida("encender");
	}

	@Override
	public void despegar(DronStateWrapper dron) {
		throw operacionNoPermitida("despegar");
	}

	@Override
	public void iniciarAterrizaje(DronStateWrapper dron) {
		dron.cambiarEstado(new LandingState());
	}

	@Override
	public void finalizarAterrizaje(DronStateWrapper dron) {
		throw operacionNoPermitida("finalizar aterrizaje");
	}

	@Override
	public void apagar(DronStateWrapper dron) {
		throw operacionNoPermitida("apagar");
	}

	@Override
	public String getNombre() {
		return "VOLANDO";
	}

	private IllegalStateException operacionNoPermitida(String operacion) {
		return new IllegalStateException("No se puede " + operacion + " con el dron VOLANDO.");
	}
}
