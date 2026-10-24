package co.edu.poli.sw2.Aplicacion.Servicios;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import co.edu.poli.sw2.Dominio.modelo.Dron;
import co.edu.poli.sw2.Dominio.modelo.Weather;
import co.edu.poli.sw2.Dominio.modelo.WeatherObserver;

/** Coordina suscripciones de drones y difunde actualizaciones meteorológicas. */
public class WeatherNotificationService {
	private final Map<Integer, Map<WeatherObserver, Suscripcion>> suscripciones = new LinkedHashMap<>();

	/**
	 * Suscribe un observador al dron, evitando registrar dos veces el mismo observador.
	 *
	 * @param dron dron que recibirá las actualizaciones
	 * @param observador receptor de los datos meteorológicos
	 * @return {@code true} cuando se registra la suscripción
	 */
	public synchronized boolean suscribir(Dron dron, WeatherObserver observador) {
		Dron dronSuscrito = Objects.requireNonNull(dron, "dron");
		WeatherObserver observadorSuscrito = Objects.requireNonNull(observador, "observador");
		Map<WeatherObserver, Suscripcion> observadores = suscripciones.computeIfAbsent(
				dronSuscrito.getId(), ignored -> new LinkedHashMap<>());
		if (observadores.containsKey(observadorSuscrito)) {
			return false;
		}
		dronSuscrito.subscribe(observadorSuscrito);
		observadores.put(observadorSuscrito, new Suscripcion(dronSuscrito, observadorSuscrito));
		return true;
	}

	/**
	 * Cancela la suscripción activa de un dron.
	 *
	 * @param dronId identificador del dron
	 * @return {@code true} si existía una suscripción para ese ID
	 */
	public synchronized boolean cancelar(int dronId) {
		Map<WeatherObserver, Suscripcion> observadores = suscripciones.remove(dronId);
		if (observadores == null) {
			return false;
		}
		for (Suscripcion suscripcion : observadores.values()) {
			suscripcion.dron.unsubscribe(suscripcion.observador);
		}
		return true;
	}

	/**
	 * Notifica a todos los drones suscritos con la misma actualización.
	 *
	 * @param weather datos meteorológicos consultados
	 */
	public void notificar(Weather weather) {
		Objects.requireNonNull(weather, "weather");
		Set<Dron> dronesSuscritos = new LinkedHashSet<>();
		synchronized (this) {
			for (Map<WeatherObserver, Suscripcion> observadores : suscripciones.values()) {
				for (Suscripcion suscripcion : observadores.values()) {
					dronesSuscritos.add(suscripcion.dron);
				}
			}
		}
		IllegalStateException falloNotificacion = null;
		for (Dron dron : dronesSuscritos) {
			try {
				dron.notifyObservers(weather);
			} catch (RuntimeException exception) {
				if (falloNotificacion == null) {
					falloNotificacion = new IllegalStateException(
							"No se pudo notificar a uno o más drones suscritos.");
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
		private final WeatherObserver observador;

		/** Guarda el dron y el observador asociados a una suscripción. */
		private Suscripcion(Dron dron, WeatherObserver observador) {
			this.dron = dron;
			this.observador = observador;
		}
	}
}