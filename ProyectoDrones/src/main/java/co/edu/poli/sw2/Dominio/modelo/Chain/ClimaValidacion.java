package co.edu.poli.sw2.Dominio.modelo.Chain;

import java.util.Objects;

import co.edu.poli.sw2.Dominio.modelo.Weather;

/** Procesa una regla meteorológica y delega en el siguiente eslabón si la aprueba. */
public abstract class ClimaValidacion {
	private ClimaValidacion siguiente;

	/**
	 * Enlaza el siguiente validador de la cadena.
	 *
	 * @param siguiente validador que se ejecutará después de esta regla
	 * @return el validador enlazado, para continuar configurando la cadena
	 */
	public final ClimaValidacion enlazar(ClimaValidacion siguiente) {
		this.siguiente = Objects.requireNonNull(siguiente, "siguiente");
		return siguiente;
	}

	/**
	 * Evalúa esta regla y continúa solo si no rechaza las condiciones.
	 *
	 * @param weather observación que se evaluará
	 * @return motivo del primer rechazo o {@code APTO PARA VOLAR}
	 */
	public final String evaluar(Weather weather) {
		Objects.requireNonNull(weather, "weather");
		String rechazo = validarRegla(weather);
		if (rechazo != null) {
			return rechazo;
		}
		return siguiente == null ? "APTO PARA VOLAR" : siguiente.evaluar(weather);
	}

	/** Evalúa la regla específica implementada por cada eslabón. */
	protected abstract String validarRegla(Weather weather);

	/**
	 * Construye la cadena en el orden operativo requerido.
	 *
	 * @return temperatura, humedad, viento, presión y visibilidad enlazados
	 */
	public static ClimaValidacion crearCadena() {
		ClimaValidacion cadena = new TemperaturaValidacion();
		cadena.enlazar(new HumedadValidacion())
				.enlazar(new VientoValidacion())
				.enlazar(new PresionValidacion())
				.enlazar(new VisibilidadValidacion());
		return cadena;
	}
}
