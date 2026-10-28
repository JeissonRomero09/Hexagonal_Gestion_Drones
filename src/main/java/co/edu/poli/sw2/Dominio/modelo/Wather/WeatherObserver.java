package co.edu.poli.sw2.Dominio.modelo.Wather;

/** Observador que recibe las actualizaciones meteorológicas de un dron. */
public interface WeatherObserver {
	/**
	 * Procesa los cinco datos de la actualización meteorológica.
	 *
	 * @param weather actualización recibida
	 */
	void update(Weather weather);
}
