package co.edu.poli.sw2.Aplicacion.Servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import co.edu.poli.sw2.Dominio.Command.EscanearZonaCommand;
import co.edu.poli.sw2.Dominio.Command.MedirTemperaturaCommand;
import co.edu.poli.sw2.Dominio.Command.TomarFotografiaCommand;
import co.edu.poli.sw2.Dominio.modelo.Dron;

class ProgramadorTareasTest {

	@Test
	void ejecutaLasTareasEnElOrdenProgramado() {
		DroneOperacionService receptor = new DroneOperacionService(
				new Dron(7, "DR-007", "Modelo", "Fabricante", 2));
		ProgramadorTareas programador = new ProgramadorTareas();
		programador.programar(new MedirTemperaturaCommand(receptor));
		programador.programar(new TomarFotografiaCommand(receptor));
		programador.programar(new EscanearZonaCommand(receptor));

		List<String> resultados = programador.ejecutarTareas();

		assertEquals(List.of(
				"[Simulación] Medición de temperatura ejecutada para el dron DR-007; no hay integración de hardware.",
				"[Simulación] Fotografía tomada para el dron DR-007; no hay integración de hardware.",
				"[Simulación] Zona escaneada para el dron DR-007; no hay integración de hardware."), resultados);
	}
}