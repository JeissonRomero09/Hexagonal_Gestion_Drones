package co.edu.poli.sw2.Aplicacion.Puerto.Entrada;

import co.edu.poli.sw2.Dominio.modelo.Dron;

/** Operaciones de aplicación para gestionar el estado operativo de un dron. */
public interface ManageDroneStateUseCase {
	void encender(Dron dron);

	void despegar(Dron dron);

	void iniciarAterrizaje(Dron dron);

	void finalizarAterrizaje(Dron dron);

	void apagar(Dron dron);

	String obtenerEstado(Dron dron);
}
