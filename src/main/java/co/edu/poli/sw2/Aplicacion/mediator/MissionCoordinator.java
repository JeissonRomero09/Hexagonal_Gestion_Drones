package co.edu.poli.sw2.Aplicacion.mediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import co.edu.poli.sw2.Aplicacion.mediator.components.BatterySystem;
import co.edu.poli.sw2.Aplicacion.mediator.components.CommunicationSystem;
import co.edu.poli.sw2.Aplicacion.mediator.components.NavigationSystem;
import co.edu.poli.sw2.Aplicacion.mediator.components.SensorSystem;

/** Mediador que controla las precondiciones y el orden de preparación de la misión. */
public class MissionCoordinator implements DroneMediator {
    private final NavigationSystem navigationSystem;
    private final SensorSystem sensorSystem;
    private final BatterySystem batterySystem;
    private final CommunicationSystem communicationSystem;

    public MissionCoordinator(NavigationSystem navigationSystem, SensorSystem sensorSystem,
            BatterySystem batterySystem, CommunicationSystem communicationSystem) {
        this.navigationSystem = Objects.requireNonNull(navigationSystem, "navigationSystem");
        this.sensorSystem = Objects.requireNonNull(sensorSystem, "sensorSystem");
        this.batterySystem = Objects.requireNonNull(batterySystem, "batterySystem");
        this.communicationSystem = Objects.requireNonNull(communicationSystem, "communicationSystem");
    }

    @Override
    public MissionResult coordinateSurveillance(MissionRequest request) {
        Objects.requireNonNull(request, "request");
        List<String> steps = new ArrayList<>();
        steps.add("Dron seleccionado: " + request.getDroneIdentifier() + ".");

        if (!batterySystem.hasSufficientEnergy(
                request.getAvailableBatteryPercentage(), request.getRequiredBatteryPercentage())) {
            steps.add("Misión cancelada: batería insuficiente ("
                    + request.getAvailableBatteryPercentage() + "% disponible; "
                    + request.getRequiredBatteryPercentage() + "% requerido).");
            return new MissionResult(false, steps);
        }
        steps.add("Batería verificada: " + request.getAvailableBatteryPercentage() + "% disponible.");

        if (!navigationSystem.prepareRoute(request.getRoute())) {
            steps.add("Misión cancelada: no se pudo preparar la ruta.");
            return new MissionResult(false, steps);
        }
        steps.add("Ruta preparada: " + request.getRoute().trim() + ".");

        if (!sensorSystem.activateSensors(request.getRequiredSensors())) {
            steps.add("Misión cancelada: no se pudieron activar los sensores requeridos.");
            return new MissionResult(false, steps);
        }
        steps.add("Sensores activados: " + String.join(", ", request.getRequiredSensors()) + ".");

        if (!communicationSystem.establishLink()) {
            steps.add("Misión cancelada: no se pudo establecer el enlace de comunicación.");
            return new MissionResult(false, steps);
        }
        steps.add("Enlace de comunicación establecido.");
        steps.add("Misión de vigilancia iniciada.");
        return new MissionResult(true, steps);
    }
}
