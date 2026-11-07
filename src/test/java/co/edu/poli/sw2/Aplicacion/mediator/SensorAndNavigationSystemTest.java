package co.edu.poli.sw2.Aplicacion.mediator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import co.edu.poli.sw2.Aplicacion.mediator.components.NavigationSystem;
import co.edu.poli.sw2.Aplicacion.mediator.components.SensorSystem;

class SensorAndNavigationSystemTest {
    private final NavigationSystem navigationSystem = new NavigationSystem();
    private final SensorSystem sensorSystem = new SensorSystem();

    @Test
    void acceptsHyphenSeparatedRouteSegments() {
        assertTrue(navigationSystem.prepareRoute("sur-norte"));
        assertTrue(navigationSystem.prepareRoute("sur-centro-norte"));
    }

    @Test
    void rejectsRoutesWithMissingSegments() {
        assertFalse(navigationSystem.prepareRoute("sur"));
        assertFalse(navigationSystem.prepareRoute("sur--norte"));
        assertFalse(navigationSystem.prepareRoute("-norte"));
        assertFalse(navigationSystem.prepareRoute("sur-"));
    }

    @Test
    void exposesSelectableSensorsFromTheCompositeHierarchy() {
        assertEquals(List.of(
                "Sensor Infrarrojo", "RTD", "Sensor CMOS", "Sensor CCD",
                "Sensor Analógico", "SPI", "UART", "Sensor inteligente"),
                SensorCatalog.getSelectableSensorNames());
    }

    @Test
    void acceptsCatalogSensorsAndRejectsCategoriesNotInTheCatalogLeaves() {
        assertTrue(sensorSystem.activateSensors(List.of("Sensor CMOS", "UART")));
        assertFalse(sensorSystem.activateSensors(List.of("Sensor General")));
        assertFalse(sensorSystem.activateSensors(List.of("sensor desconocido")));
    }
}
