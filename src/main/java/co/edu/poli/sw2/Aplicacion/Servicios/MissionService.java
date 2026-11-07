package co.edu.poli.sw2.Aplicacion.Servicios;

import java.util.List;
import java.util.Objects;

import co.edu.poli.sw2.Aplicacion.Puerto.Entrada.ExecuteMissionUseCase;
import co.edu.poli.sw2.Aplicacion.mediator.DroneMediator;

/** Servicio de aplicación para ejecutar una misión mediante el mediador. */
public class MissionService implements ExecuteMissionUseCase {
    private final DroneMediator mediator;

    public MissionService(DroneMediator mediator) {
        this.mediator = Objects.requireNonNull(mediator, "mediator");
    }

    @Override
    public List<String> executeMission(String droneIdentifier, String route, List<String> requiredSensors,
            int availableBatteryPercentage, int requiredBatteryPercentage) {
        return mediator.coordinateSurveillance(droneIdentifier, route, requiredSensors,
                availableBatteryPercentage, requiredBatteryPercentage);
    }
}
