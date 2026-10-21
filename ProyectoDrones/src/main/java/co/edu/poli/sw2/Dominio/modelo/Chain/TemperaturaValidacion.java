package co.edu.poli.sw2.Dominio.modelo.Chain;

import co.edu.poli.sw2.Dominio.modelo.Weather;

/** Rechaza temperaturas inferiores a 0 °C o superiores a 40 °C. */
public class TemperaturaValidacion extends ClimaValidacion {
	@Override
	protected String validarRegla(Weather weather) {
		return weather.getTemperatura() < 0 || weather.getTemperatura() > 40
				? "RECHAZADO: Temperatura fuera del rango de 0 a 40 °C."
				: null;
	}
}
