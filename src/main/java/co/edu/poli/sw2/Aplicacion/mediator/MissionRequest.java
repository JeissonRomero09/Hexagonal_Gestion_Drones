package co.edu.poli.sw2.Aplicacion.mediator;

import java.util.List;
import java.util.Objects;

/** Datos necesarios para preparar una misión sin acceder al CRUD de drones. */
public final class MissionRequest {
    private final String droneIdentifier;
    private final String route;
    private final List<String> requiredSensors;
    private final int availableBatteryPercentage;
    private final int requiredBatteryPercentage;

    public MissionRequest(String route, List<String> requiredSensors,
            int availableBatteryPercentage, int requiredBatteryPercentage) {
        this("Dron no especificado", route, requiredSensors,
                availableBatteryPercentage, requiredBatteryPercentage);
    }

    public MissionRequest(String droneIdentifier, String route, List<String> requiredSensors,
            int availableBatteryPercentage, int requiredBatteryPercentage) {
        this.droneIdentifier = Objects.requireNonNull(droneIdentifier, "droneIdentifier");
        if (droneIdentifier.isBlank()) {
            throw new IllegalArgumentException("droneIdentifier must not be blank");
        }
        this.route = Objects.requireNonNull(route, "route");
        this.requiredSensors = List.copyOf(
                Objects.requireNonNull(requiredSensors, "requiredSensors"));
        validatePercentage(availableBatteryPercentage, "availableBatteryPercentage");
        validatePercentage(requiredBatteryPercentage, "requiredBatteryPercentage");
        this.availableBatteryPercentage = availableBatteryPercentage;
        this.requiredBatteryPercentage = requiredBatteryPercentage;
    }

    public String getDroneIdentifier() {
        return droneIdentifier;
    }

    public String getRoute() {
        return route;
    }

    public List<String> getRequiredSensors() {
        return requiredSensors;
    }

    public int getAvailableBatteryPercentage() {
        return availableBatteryPercentage;
    }

    public int getRequiredBatteryPercentage() {
        return requiredBatteryPercentage;
    }

    private static void validatePercentage(int percentage, String fieldName) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException(fieldName + " must be between 0 and 100");
        }
    }
}
