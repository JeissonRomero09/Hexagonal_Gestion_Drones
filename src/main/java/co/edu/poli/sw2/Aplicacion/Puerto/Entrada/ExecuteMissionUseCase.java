package co.edu.poli.sw2.Aplicacion.Puerto.Entrada;

import co.edu.poli.sw2.Aplicacion.mediator.MissionRequest;
import co.edu.poli.sw2.Aplicacion.mediator.MissionResult;

/** Caso de uso para coordinar una misión de vigilancia. */
public interface ExecuteMissionUseCase {
    MissionResult executeMission(MissionRequest request);
}
