package co.edu.poli.sw2.Dominio.state;

/** Estado apagado: solo permite encender el dron. */
public class OffState implements DroneState {
	@Override
	public void encender(DronStateWrapper dron) {
		dron.cambiarEstado(new ReadyState());
	}

	@Override
	public void despegar(DronStateWrapper dron) {
		throw operacionNoPermitida("despegar(OffState)");
	}

	@Override
	public void iniciarAterrizaje(DronStateWrapper dron) {
		throw operacionNoPermitida("iniciar aterrizaje(OffState)");
	}

	@Override
	public void finalizarAterrizaje(DronStateWrapper dron) {
		throw operacionNoPermitida("finalizar aterrizaje(OffState)");
	}

	@Override
	public void apagar(DronStateWrapper dron) {
		throw operacionNoPermitida("apagar(OffState)");
	}

	@Override
	public String getNombre() {
		return "APAGADO(OffState)";
	}

	private IllegalStateException operacionNoPermitida(String operacion) {
		return new IllegalStateException("No se puede " + operacion + " con el dron APAGADO(OffState).");
	}
}
