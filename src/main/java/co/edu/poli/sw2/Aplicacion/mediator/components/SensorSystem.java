package co.edu.poli.sw2.Aplicacion.mediator.components;

import java.util.List;
import java.util.Set;

import co.edu.poli.sw2.Aplicacion.mediator.SensorCatalog;

/** Simula la activación del conjunto de sensores solicitado para una misión. */
public class SensorSystem {
    private static final Set<String> SELECTABLE_SENSORS =
            Set.copyOf(SensorCatalog.getSelectableSensorNames());

    public boolean activateSensors(List<String> requiredSensors) {
        return requiredSensors != null
                && !requiredSensors.isEmpty()
                && requiredSensors.stream().allMatch(SELECTABLE_SENSORS::contains);
    }
}
