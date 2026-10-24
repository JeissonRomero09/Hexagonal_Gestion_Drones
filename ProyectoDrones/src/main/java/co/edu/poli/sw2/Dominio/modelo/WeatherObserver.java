package co.edu.poli.sw2.Dominio.modelo;

/** Observador que recibe las actualizaciones meteorológicas de un dron. */
public interface WeatherObserver {
	/**
	 * Procesa los cinco datos de la actualización meteorológica.
	 *
	 * @param weather actualización recibida
	 */
	void update(Weather weather);
}
