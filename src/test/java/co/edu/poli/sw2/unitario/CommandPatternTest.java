package co.edu.poli.sw2.unitario;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import co.edu.poli.sw2.Dominio.Command.DroneOperacion;
import co.edu.poli.sw2.Dominio.Command.EscanearZonaCommand;
import co.edu.poli.sw2.Dominio.Command.MedirTemperaturaCommand;
import co.edu.poli.sw2.Dominio.Command.TomarFotografiaCommand;

class CommandPatternTest {
	@Test
	void comandosDeleganLaOperacionAlReceptor() {
		DroneOperacion receptor = new FakeDroneOperacion();

		assertEquals("fotografia", new TomarFotografiaCommand(receptor).ejecutar());
		assertEquals("escaneo", new EscanearZonaCommand(receptor).ejecutar());
		assertEquals("temperatura", new MedirTemperaturaCommand(receptor).ejecutar());
	}

	private static class FakeDroneOperacion implements DroneOperacion {
		@Override
		public String tomarFotografia() {
			return "fotografia";
		}

		@Override
		public String escanearZona() {
			return "escaneo";
		}

		@Override
		public String medirTemperatura() {
			return "temperatura";
		}
	}
}