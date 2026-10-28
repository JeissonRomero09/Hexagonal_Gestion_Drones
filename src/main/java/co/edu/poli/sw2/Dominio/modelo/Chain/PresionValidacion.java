package co.edu.poli.sw2.Dominio.modelo.Chain;

import co.edu.poli.sw2.Dominio.modelo.Wather.Weather;

/** Rechaza presiones inferiores a 950 hPa o superiores a 1050 hPa. */
public class PresionValidacion extends ClimaValidacion {
	@Override
	protected String validarRegla(Weather weather) {
		return weather.getPresion() < 950 || weather.getPresion() > 1050
				? "Presión fuera del rango de 950 a 1050 hPa."
				: null;
	}
}
