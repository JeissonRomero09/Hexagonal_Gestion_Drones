package co.edu.poli.sw2.Dominio.state;

/** Estado aterrizando: solo permite finalizar el aterrizaje. */
public class LandingState implements DroneState {
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
		throw operacionNoPermitida("iniciar aterrizaje");
	}

	@Override
	public void finalizarAterrizaje(DronStateWrapper dron) {
		dron.cambiarEstado(new ReadyState());
	}

	@Override
	public void apagar(DronStateWrapper dron) {
		throw operacionNoPermitida("apagar");
	}

	@Override
	public String getNombre() {
		return "ATERRIZANDO";
	}

	private IllegalStateException operacionNoPermitida(String operacion) {
		return new IllegalStateException("No se puede " + operacion + " con el dron ATERRIZANDO.");
	}
}
