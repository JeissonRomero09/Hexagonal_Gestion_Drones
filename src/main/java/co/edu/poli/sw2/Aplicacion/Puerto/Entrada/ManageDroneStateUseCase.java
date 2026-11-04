package co.edu.poli.sw2.Aplicacion.Puerto.Entrada;

/** Operaciones de aplicación para gestionar el estado operativo de un dron. */
public interface ManageDroneStateUseCase {
	void encender(int dronId);

	void despegar(int dronId);

	void iniciarAterrizaje(int dronId);

	void finalizarAterrizaje(int dronId);

	void apagar(int dronId);

	String obtenerEstado(int dronId);
}
