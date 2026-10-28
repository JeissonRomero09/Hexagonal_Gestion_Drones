package co.edu.poli.sw2.Dominio.modelo.Chain;

import co.edu.poli.sw2.Dominio.modelo.Wather.Weather;

/** Rechaza humedad relativa superior al 85 %. */
public class HumedadValidacion extends ClimaValidacion {
	@Override
	protected String validarRegla(Weather weather) {
		return weather.getHumedad() > 85 ? "Humedad superior al 85 %." : null;
	}
}
