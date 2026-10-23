package co.edu.poli.sw2.Aplicacion.Servicios.observer;

import java.util.function.Consumer;

import co.edu.poli.sw2.Dominio.modelo.Weather;

/** Contrato de los observadores concretos de clima en la capa de aplicación. */
public interface WeatherUpdateObserver extends Consumer<Weather> {
	/**
	 * Procesa los cinco datos meteorológicos recibidos.
	 *
	 * @param weather actualización publicada
	 */
	void update(Weather weather);

	@Override
	default void accept(Weather weather) {
		update(weather);
	}
}
