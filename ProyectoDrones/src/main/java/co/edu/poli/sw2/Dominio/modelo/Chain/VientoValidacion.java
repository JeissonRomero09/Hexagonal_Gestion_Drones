package co.edu.poli.sw2.Dominio.modelo.Chain;

import co.edu.poli.sw2.Dominio.modelo.Weather;

/** Rechaza velocidades del viento superiores a 40 km/h. */
public class VientoValidacion extends ClimaValidacion {
	@Override
	protected String validarRegla(Weather weather) {
		return weather.getVelocidadViento() > 40 ? "RECHAZADO: Viento superior a 40 km/h." : null;
	}
}
