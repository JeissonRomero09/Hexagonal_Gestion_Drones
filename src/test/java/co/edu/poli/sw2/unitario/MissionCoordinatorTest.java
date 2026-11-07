package co.edu.poli.sw2.unitario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import co.edu.poli.sw2.Aplicacion.mediator.MissionCoordinator;
import co.edu.poli.sw2.Aplicacion.mediator.components.BatterySystem;
import co.edu.poli.sw2.Aplicacion.mediator.components.CommunicationSystem;
import co.edu.poli.sw2.Aplicacion.mediator.components.NavigationSystem;
import co.edu.poli.sw2.Aplicacion.mediator.components.SensorSystem;

class MissionCoordinatorTest {
    @Test
    void iniciaLaMisionCuandoTodosLosSistemasEstanListos() {
        List<String> resultado = coordinador(true).coordinateSurveillance(
                "DR-7", "A-B-C", List.of("Sensor CMOS"), 80, 50);

        assertTrue(resultado.contains("Misión de vigilancia iniciada."));
        assertEquals(6, resultado.size());
        assertEquals("Misión de vigilancia iniciada.", resultado.get(5));
    }

    @Test
    void cancelaLaMisionSiLaBateriaEsInsuficiente() {
        List<String> resultado = coordinador(true).coordinateSurveillance(
                "DR-7", "A-B-C", List.of("Sensor CMOS"), 20, 50);

        assertFalse(resultado.contains("Misión de vigilancia iniciada."));
        assertEquals(2, resultado.size());
        assertTrue(resultado.get(1).contains("batería insuficiente"));
    }

    @Test
    void cancelaLaMisionSiNoPuedePrepararLaRuta() {
        List<String> resultado = coordinador(true).coordinateSurveillance(
                "DR-7", "A-", List.of("Sensor CMOS"), 80, 50);

        assertFalse(resultado.contains("Misión de vigilancia iniciada."));
        assertEquals(3, resultado.size());
        assertTrue(resultado.get(2).contains("no se pudo preparar la ruta"));
    }

    @Test
    void cancelaLaMisionSiNoPuedeActivarLosSensores() {
        List<String> resultado = coordinador(true).coordinateSurveillance(
                "DR-7", "A-B-C", List.of("Sensor no disponible"), 80, 50);

        assertFalse(resultado.contains("Misión de vigilancia iniciada."));
        assertEquals(4, resultado.size());
        assertTrue(resultado.get(3).contains("no se pudieron activar los sensores"));
    }

    @Test
    void cancelaLaMisionSiNoPuedeEstablecerLaComunicacion() {
        List<String> resultado = coordinador(false).coordinateSurveillance(
                "DR-7", "A-B-C", List.of("Sensor CMOS"), 80, 50);

        assertFalse(resultado.contains("Misión de vigilancia iniciada."));
        assertEquals(5, resultado.size());
        assertTrue(resultado.get(4).contains("no se pudo establecer el enlace"));
    }

    private MissionCoordinator coordinador(boolean enlaceDisponible) {
        return new MissionCoordinator(
                new NavigationSystem(),
                new SensorSystem(),
                new BatterySystem(),
                new CommunicationSystem(enlaceDisponible));
    }
}
