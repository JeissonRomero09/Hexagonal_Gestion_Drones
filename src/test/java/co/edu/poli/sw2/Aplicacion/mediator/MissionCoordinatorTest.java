package co.edu.poli.sw2.Aplicacion.mediator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import co.edu.poli.sw2.Aplicacion.mediator.components.BatterySystem;
import co.edu.poli.sw2.Aplicacion.mediator.components.CommunicationSystem;
import co.edu.poli.sw2.Aplicacion.mediator.components.NavigationSystem;
import co.edu.poli.sw2.Aplicacion.mediator.components.SensorSystem;

class MissionCoordinatorTest {
    @Test
    void startsMissionWhenEverySystemIsReady() {
        MissionCoordinator coordinator = coordinator(new CommunicationSystem(true));

        MissionResult result = coordinator.coordinateSurveillance(
                new MissionRequest("DRON-01", "sur-norte", List.of("Sensor CMOS", "Sensor Infrarrojo"), 85, 40));

        assertTrue(result.isStarted());
        assertEquals(6, result.getSteps().size());
        assertEquals("Dron seleccionado: DRON-01.", result.getSteps().get(0));
        assertEquals("Batería verificada: 85% disponible.", result.getSteps().get(1));
        assertEquals("Ruta preparada: sur-norte.", result.getSteps().get(2));
        assertEquals("Sensores activados: Sensor CMOS, Sensor Infrarrojo.", result.getSteps().get(3));
        assertEquals("Enlace de comunicación establecido.", result.getSteps().get(4));
        assertEquals("Misión de vigilancia iniciada.", result.getSteps().get(5));
    }

    @Test
    void stopsBeforePreparingOtherSystemsWhenBatteryIsInsufficient() {
        MissionCoordinator coordinator = coordinator(new CommunicationSystem(true));

        MissionResult result = coordinator.coordinateSurveillance(
                new MissionRequest("DRON-01", "sur-norte", List.of("Sensor CMOS"), 20, 40));

        assertFalse(result.isStarted());
        assertEquals(2, result.getSteps().size());
        assertTrue(result.getSteps().get(1).contains("batería insuficiente"));
    }

    @Test
    void stopsWhenRouteCannotBePrepared() {
        MissionCoordinator coordinator = coordinator(new CommunicationSystem(true));

        MissionResult result = coordinator.coordinateSurveillance(
                new MissionRequest("DRON-01", "sur--norte", List.of("Sensor CMOS"), 85, 40));

        assertFalse(result.isStarted());
        assertEquals(3, result.getSteps().size());
        assertTrue(result.getSteps().get(2).contains("no se pudo preparar la ruta"));
    }

    @Test
    void stopsWhenRequiredSensorsCannotBeActivated() {
        MissionCoordinator coordinator = coordinator(new CommunicationSystem(true));

        MissionResult result = coordinator.coordinateSurveillance(
                new MissionRequest("DRON-01", "sur-norte", List.of(), 85, 40));

        assertFalse(result.isStarted());
        assertEquals(4, result.getSteps().size());
        assertTrue(result.getSteps().get(3).contains("no se pudieron activar los sensores"));
    }

    @Test
    void doesNotStartMissionWhenCommunicationLinkIsUnavailable() {
        MissionCoordinator coordinator = coordinator(new CommunicationSystem(false));

        MissionResult result = coordinator.coordinateSurveillance(
                new MissionRequest("DRON-01", "sur-norte", List.of("Sensor CMOS"), 85, 40));

        assertFalse(result.isStarted());
        assertEquals(5, result.getSteps().size());
        assertTrue(result.getSteps().get(4).contains("enlace de comunicación"));
    }

    private MissionCoordinator coordinator(CommunicationSystem communicationSystem) {
        return new MissionCoordinator(
                new NavigationSystem(),
                new SensorSystem(),
                new BatterySystem(),
                communicationSystem);
    }
}
