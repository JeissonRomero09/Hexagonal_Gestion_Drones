package co.edu.poli.sw2.Aplicacion.Servicios;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import co.edu.poli.sw2.Dominio.modelo.Dron;
import co.edu.poli.sw2.Dominio.modelo.Weather;
import co.edu.poli.sw2.Dominio.modelo.WeatherObserver;

/** Coordina suscripciones de drones y difunde actualizaciones meteorológicas. */
public class WeatherNotificationService {
	private final Map<Integer, Suscripcion> suscripciones = new LinkedHashMap<>();

	/**
	 * Suscribe un observador al dron si todavía no hay una suscripción para su ID.
	 *
	 * @param dron dron que recibirá las actualizaciones
	 * @param observador receptor de los datos meteorológicos
	 * @return {@code true} cuando se registra la suscripción
	 */
	public synchronized boolean suscribir(Dron dron, WeatherObserver observador) {
		Dron dronSuscrito = Objects.requireNonNull(dron, "dron");
		WeatherObserver observadorSuscrito = Objects.requireNonNull(observador, "observador");
		if (suscripciones.containsKey(dronSuscrito.getId())) {
			return false;
		}
		dronSuscrito.subscribe(observadorSuscrito);
		suscripciones.put(dronSuscrito.getId(), new Suscripcion(dronSuscrito, observadorSuscrito));
		return true;
	}

	/**
	 * Cancela la suscripción activa de un dron.
	 *
	 * @param dronId identificador del dron
	 * @return {@code true} si existía una suscripción para ese ID
	 */
	public synchronized boolean cancelarSuscripcion(int dronId) {
		Suscripcion suscripcion = suscripciones.remove(dronId);
		if (suscripcion == null) {
			return false;
		}
		suscripcion.dron.unsubscribe(suscripcion.observador);
		return true;
	}

	/**
	 * Notifica a todos los drones suscritos con la misma actualización.
	 *
	 * @param weather datos meteorológicos consultados
	 */
	public void notificar(Weather weather) {
		List<Dron> drones;
		synchronized (this) {
			drones = new ArrayList<>();
			for (Suscripcion suscripcion : suscripciones.values()) {
				drones.add(suscripcion.dron);
			}
		}
		for (Dron dron : drones) {
			dron.notifyObservers(weather);
		}
	}

	private static final class Suscripcion {
		private final Dron dron;
		private final WeatherObserver observador;

		/** Guarda el dron y el observador asociados a una suscripción. */
		private Suscripcion(Dron dron, WeatherObserver observador) {
			this.dron = dron;
			this.observador = observador;
		}
	}
}