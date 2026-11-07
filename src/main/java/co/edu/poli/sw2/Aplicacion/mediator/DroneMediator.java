package co.edu.poli.sw2.Aplicacion.mediator;

import java.util.List;

/** Coordina los sistemas que participan en una misión de vigilancia. */
public interface DroneMediator {
    List<String> coordinateSurveillance(String droneIdentifier, String route, List<String> requiredSensors,
            int availableBatteryPercentage, int requiredBatteryPercentage);
}
