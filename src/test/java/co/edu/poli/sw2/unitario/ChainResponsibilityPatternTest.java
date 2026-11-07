package co.edu.poli.sw2.unitario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import co.edu.poli.sw2.Dominio.modelo.Chain.ClimaValidacion;
import co.edu.poli.sw2.Dominio.modelo.Wather.Weather;

class ChainResponsibilityPatternTest {
	@Test
	void apruebaElClimaCuandoTodasLasReglasSeCumplen() {
		String resultado = ClimaValidacion.crearCadena().evaluar(new Weather(20, 50, 20, 1013, 10));

		assertEquals("CLIMA APTO PARA OPERACIÓN DE DRONES", resultado);
	}

	@Test
	void rechazaLaPrimeraReglaIncumplidaEnElOrdenDeLaCadena() {
		String resultado = ClimaValidacion.crearCadena().evaluar(new Weather(41, 90, 50, 900, 2));

		assertEquals("CLIMA NO APTO PARA OPERACIÓN DE DRONES: Temperatura fuera del rango de 0 a 40 °C.", resultado);
	}

	@Test
	void detectaIncumplimientosDeCadaEslabon() {
		ClimaValidacion cadena = ClimaValidacion.crearCadena();

		assertEquals("Humedad superior al 85 %.", rechazoDe(cadena, new Weather(20, 86, 20, 1013, 10)));
		assertEquals("Viento superior a 40 km/h.", rechazoDe(cadena, new Weather(20, 50, 41, 1013, 10)));
		assertEquals("Presión fuera del rango de 950 a 1050 hPa.",
				rechazoDe(cadena, new Weather(20, 50, 20, 949, 10)));
		assertEquals("Visibilidad inferior a 5 km.", rechazoDe(cadena, new Weather(20, 50, 20, 1013, 4)));
	}

	@Test
	void noPermiteEvaluarClimaNuloNiEnlazarUnValidadorNulo() {
		ClimaValidacion cadena = ClimaValidacion.crearCadena();

		assertThrows(NullPointerException.class, () -> cadena.evaluar(null));
		assertThrows(NullPointerException.class, () -> cadena.enlazar(null));
	}

	private String rechazoDe(ClimaValidacion cadena, Weather weather) {
		return cadena.evaluar(weather).replace("CLIMA NO APTO PARA OPERACIÓN DE DRONES: ", "");
	}
}