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
    public List<String> coordinateSurveillance(String droneIdentifier, String route,
            List<String> requiredSensors, int availableBatteryPercentage, int requiredBatteryPercentage) {
        Objects.requireNonNull(droneIdentifier, "droneIdentifier");
        if (droneIdentifier.isBlank()) {
            throw new IllegalArgumentException("droneIdentifier must not be blank");
        }
        List<String> sensors = List.copyOf(Objects.requireNonNull(requiredSensors, "requiredSensors"));
        validatePercentage(availableBatteryPercentage, "availableBatteryPercentage");
        validatePercentage(requiredBatteryPercentage, "requiredBatteryPercentage");

        List<String> steps = new ArrayList<>();
        steps.add("Dron seleccionado: " + droneIdentifier + ".");

        if (!batterySystem.hasSufficientEnergy(
                availableBatteryPercentage, requiredBatteryPercentage)) {
            steps.add("Misión cancelada: batería insuficiente ("
                    + availableBatteryPercentage + "% disponible; "
                    + requiredBatteryPercentage + "% requerido).");
            return List.copyOf(steps);
        }
        steps.add("Batería verificada: " + availableBatteryPercentage + "% disponible.");

        if (!navigationSystem.prepareRoute(route)) {
            steps.add("Misión cancelada: no se pudo preparar la ruta.");
            return List.copyOf(steps);
        }
        steps.add("Ruta preparada: " + route.trim() + ".");

        if (!sensorSystem.activateSensors(sensors)) {
            steps.add("Misión cancelada: no se pudieron activar los sensores requeridos.");
            return List.copyOf(steps);
        }
        steps.add("Sensores activados: " + String.join(", ", sensors) + ".");

        if (!communicationSystem.establishLink()) {
            steps.add("Misión cancelada: no se pudo establecer el enlace de comunicación.");
            return List.copyOf(steps);
        }
        steps.add("Enlace de comunicación establecido.");
        steps.add("Misión de vigilancia iniciada.");
        return List.copyOf(steps);
    }

    private static void validatePercentage(int percentage, String fieldName) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException(fieldName + " must be between 0 and 100");
        }
    }
}
