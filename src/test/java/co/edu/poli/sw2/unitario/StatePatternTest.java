package co.edu.poli.sw2.unitario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import co.edu.poli.sw2.Dominio.modelo.Dron;
import co.edu.poli.sw2.Dominio.state.DronStateWrapper;

class StatePatternTest {
	@Test
	void permiteElCicloCompletoDeVuelo() {
		DronStateWrapper dron = new DronStateWrapper(new Dron(1, "S-1", "M1", "Fabricante", 2));

		assertEquals("APAGADO", dron.getNombreEstado());
		dron.encender();
		assertEquals("EN ESPERA", dron.getNombreEstado());
		dron.despegar();
		assertEquals("VOLANDO", dron.getNombreEstado());
		dron.iniciarAterrizaje();
		assertEquals("ATERRIZANDO", dron.getNombreEstado());
		dron.finalizarAterrizaje();
		assertEquals("EN ESPERA", dron.getNombreEstado());
		dron.apagar();
		assertEquals("APAGADO", dron.getNombreEstado());
	}

	@Test
	void rechazaOperacionesNoPermitidasEnElEstadoActual() {
		DronStateWrapper dron = new DronStateWrapper(new Dron(1, "S-1", "M1", "Fabricante", 2));

		assertThrows(IllegalStateException.class, dron::despegar);
		dron.encender();
		assertThrows(IllegalStateException.class, dron::encender);
		assertThrows(IllegalStateException.class, dron::iniciarAterrizaje);
	}

	@Test
	void requiereUnDronConIdentificadorPositivo() {
		assertThrows(IllegalArgumentException.class,
				() -> new DronStateWrapper(new Dron(0, "S-0", "M1", "Fabricante", 2)));
	}
}