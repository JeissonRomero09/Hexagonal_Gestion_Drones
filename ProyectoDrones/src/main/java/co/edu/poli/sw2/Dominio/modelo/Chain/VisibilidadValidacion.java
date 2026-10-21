package co.edu.poli.sw2.Dominio.modelo.Chain;

import co.edu.poli.sw2.Dominio.modelo.Weather;

/** Rechaza visibilidad inferior a 5 km. */
public class VisibilidadValidacion extends ClimaValidacion {
	@Override
	protected String validarRegla(Weather weather) {
		return weather.getVisibilidad() < 5 ? "RECHAZADO: Visibilidad inferior a 5 km." : null;
	}
}
