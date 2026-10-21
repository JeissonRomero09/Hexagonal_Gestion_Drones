package co.edu.poli.sw2.Dominio.modelo;

/** Receptor de actualizaciones meteorológicas publicadas por un dron. */
public interface WeatherObserver {
	/**
	 * Procesa una actualización con temperatura, humedad, viento, presión y visibilidad.
	 *
	 * @param weather datos meteorológicos recibidos
	 */
	void update(Weather weather);
}
