package co.edu.poli.sw2.Dominio.state;

/** Define las operaciones permitidas para el estado operativo de un dron. */
public interface DroneState {
	void encender(DronStateWrapper dron);

	void despegar(DronStateWrapper dron);

	void iniciarAterrizaje(DronStateWrapper dron);

	void finalizarAterrizaje(DronStateWrapper dron);

	void apagar(DronStateWrapper dron);

	String getNombre();
}
