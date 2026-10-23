package co.edu.poli.sw2.Dominio.modelo;

/** Receptor de una actualización meteorológica publicada por el Subject. */
public interface WeatherUpdateObserver {
	/**
	 * Procesa los cinco datos meteorológicos recibidos.
	 *
	 * @param weather actualización publicada
	 */
	void update(Weather weather);
}
