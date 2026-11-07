package co.edu.poli.sw2.Aplicacion.Puerto.Entrada;

import java.util.List;

/** Caso de uso para coordinar una misión de vigilancia. */
public interface ExecuteMissionUseCase {
    List<String> executeMission(String droneIdentifier, String route, List<String> requiredSensors,
            int availableBatteryPercentage, int requiredBatteryPercentage);
}
