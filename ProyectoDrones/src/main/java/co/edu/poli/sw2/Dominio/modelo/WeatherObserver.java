package co.edu.poli.sw2.Dominio.modelo;

import java.util.function.Consumer;

/** Contrato Subject solicitado para administrar las actualizaciones meteorológicas. */
public interface WeatherObserver {
	/**
	 * Suscribe un observador a las actualizaciones meteorológicas de un dron.
	 *
	 * @param dron dron al que pertenece la suscripción
	 * @param observador receptor de las actualizaciones meteorológicas
	 * @return {@code true} si se creó la suscripción
	 */
	boolean suscribir(Dron dron, Consumer<Weather> observador);

	/**
	 * Cancela la suscripción de un dron.
	 *
	 * @param dronId identificador del dron
	 * @return {@code true} si existía una suscripción
	 */
	boolean cancelar(int dronId);

	/**
	 * Publica una actualización con temperatura, humedad, viento, presión y visibilidad.
	 *
	 * @param weather datos meteorológicos obtenidos
	 */
	void notificar(Weather weather);
}
