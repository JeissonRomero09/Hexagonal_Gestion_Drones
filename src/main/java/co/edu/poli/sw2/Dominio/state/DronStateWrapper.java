package co.edu.poli.sw2.Dominio.state;

/** Contexto del patrón State; mantiene el estado operativo fuera del modelo Dron. */
public class DronStateWrapper {
	private final int dronId;
	private DroneState estado;

	public DronStateWrapper(int dronId) {
		if (dronId <= 0) {
			throw new IllegalArgumentException("El ID del dron debe ser positivo.");
		}
		this.dronId = dronId;
		this.estado = new OffState();
	}

	public int getDronId() {
		return dronId;
	}

	public String getNombreEstado() {
		return estado.getNombre();
	}

	public void encender() {
		estado.encender(this);
	}

	public void despegar() {
		estado.despegar(this);
	}

	public void iniciarAterrizaje() {
		estado.iniciarAterrizaje(this);
	}

	public void finalizarAterrizaje() {
		estado.finalizarAterrizaje(this);
	}

	public void apagar() {
		estado.apagar(this);
	}

	void cambiarEstado(DroneState nuevoEstado) {
		this.estado = nuevoEstado;
	}
}
