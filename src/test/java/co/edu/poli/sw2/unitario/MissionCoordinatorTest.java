package co.edu.poli.sw2.unitario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import co.edu.poli.sw2.Aplicacion.mediator.MissionCoordinator;
import co.edu.poli.sw2.Aplicacion.mediator.MissionRequest;
import co.edu.poli.sw2.Aplicacion.mediator.MissionResult;
import co.edu.poli.sw2.Aplicacion.mediator.components.BatterySystem;
import co.edu.poli.sw2.Aplicacion.mediator.components.CommunicationSystem;
import co.edu.poli.sw2.Aplicacion.mediator.components.NavigationSystem;
import co.edu.poli.sw2.Aplicacion.mediator.components.SensorSystem;

class MissionCoordinatorTest {
	@Test
	void iniciaLaMisionCuandoTodosLosSistemasEstanListos() {
		MissionResult resultado = coordinador(true).coordinateSurveillance(
				solicitud("A-B-C", List.of("Sensor CMOS"), 80, 50));

		assertTrue(resultado.isStarted());
		assertEquals(6, resultado.getSteps().size());
		assertEquals("Misión de vigilancia iniciada.", resultado.getSteps().get(5));
	}

	@Test
	void cancelaLaMisionSiLaBateriaEsInsuficiente() {
		MissionResult resultado = coordinador(true).coordinateSurveillance(
				solicitud("A-B-C", List.of("Sensor CMOS"), 20, 50));

		assertFalse(resultado.isStarted());
		assertEquals(2, resultado.getSteps().size());
		assertTrue(resultado.getSteps().get(1).contains("batería insuficiente"));
	}

	@Test
	void cancelaLaMisionSiNoPuedePrepararLaRuta() {
		MissionResult resultado = coordinador(true).coordinateSurveillance(
				solicitud("A-", List.of("Sensor CMOS"), 80, 50));

		assertFalse(resultado.isStarted());
		assertEquals(3, resultado.getSteps().size());
		assertTrue(resultado.getSteps().get(2).contains("no se pudo preparar la ruta"));
	}

	@Test
	void cancelaLaMisionSiNoPuedeActivarLosSensores() {
		MissionResult resultado = coordinador(true).coordinateSurveillance(
				solicitud("A-B-C", List.of("Sensor no disponible"), 80, 50));

		assertFalse(resultado.isStarted());
		assertEquals(4, resultado.getSteps().size());
		assertTrue(resultado.getSteps().get(3).contains("no se pudieron activar los sensores"));
	}

	@Test
	void cancelaLaMisionSiNoPuedeEstablecerLaComunicacion() {
		MissionResult resultado = coordinador(false).coordinateSurveillance(
				solicitud("A-B-C", List.of("Sensor CMOS"), 80, 50));

		assertFalse(resultado.isStarted());
		assertEquals(5, resultado.getSteps().size());
		assertTrue(resultado.getSteps().get(4).contains("no se pudo establecer el enlace"));
	}

	private MissionCoordinator coordinador(boolean enlaceDisponible) {
		return new MissionCoordinator(
				new NavigationSystem(),
				new SensorSystem(),
				new BatterySystem(),
				new CommunicationSystem(enlaceDisponible));
	}

	private MissionRequest solicitud(String ruta, List<String> sensores, int bateriaDisponible, int bateriaRequerida) {
		return new MissionRequest("DR-7", ruta, sensores, bateriaDisponible, bateriaRequerida);
	}
}