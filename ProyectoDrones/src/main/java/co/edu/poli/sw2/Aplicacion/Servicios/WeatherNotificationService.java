package co.edu.poli.sw2.Aplicacion.Servicios;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import co.edu.poli.sw2.Dominio.modelo.Dron;
import co.edu.poli.sw2.Dominio.modelo.Weather;
import co.edu.poli.sw2.Dominio.modelo.WeatherObserver;

/** Coordina suscripciones de drones y difunde actualizaciones meteorológicas. */
public class WeatherNotificationService implements WeatherObserver {
	private final Map<Integer, Suscripcion> suscripciones = new LinkedHashMap<>();

	/**
	 * Suscribe un observador al dron si todavía no hay una suscripción para su ID.
	 *
	 * @param dron dron que recibirá las actualizaciones
	 * @param observador receptor de los datos meteorológicos
	 * @return {@code true} cuando se registra la suscripción
	 */
	@Override
	public synchronized boolean suscribir(Dron dron, Consumer<Weather> observador) {
		Dron dronSuscrito = Objects.requireNonNull(dron, "dron");
		Consumer<Weather> observadorSuscrito = Objects.requireNonNull(observador, "observador");
		if (suscripciones.containsKey(dronSuscrito.getId())) {
			return false;
		}
		suscripciones.put(dronSuscrito.getId(), new Suscripcion(dronSuscrito, observadorSuscrito));
		return true;
	}

	/**
	 * Cancela la suscripción activa de un dron.
	 *
	 * @param dronId identificador del dron
	 * @return {@code true} si existía una suscripción para ese ID
	 */
	@Override
	public synchronized boolean cancelar(int dronId) {
		return suscripciones.remove(dronId) != null;
	}

	/**
	 * Notifica a todos los drones suscritos con la misma actualización.
	 *
	 * @param weather datos meteorológicos consultados
	 */
	@Override
	public void notificar(Weather weather) {
		Objects.requireNonNull(weather, "weather");
		List<Suscripcion> suscripcionesActivas;
		synchronized (this) {
			suscripcionesActivas = new ArrayList<>(suscripciones.values());
		}
		IllegalStateException falloNotificacion = null;
		for (Suscripcion suscripcion : suscripcionesActivas) {
			try {
				suscripcion.observador.accept(weather);
			} catch (RuntimeException exception) {
				if (falloNotificacion == null) {
					falloNotificacion = new IllegalStateException(
							"No se pudo notificar a uno o más observadores meteorológicos.");
				}
				falloNotificacion.addSuppressed(exception);
			}
		}
		if (falloNotificacion != null) {
			throw falloNotificacion;
		}
	}

	private static final class Suscripcion {
		private final Dron dron;
		private final Consumer<Weather> observador;

		/** Guarda el dron y el observador asociados a una suscripción. */
		private Suscripcion(Dron dron, Consumer<Weather> observador) {
			this.dron = dron;
			this.observador = observador;
		}
	}
}