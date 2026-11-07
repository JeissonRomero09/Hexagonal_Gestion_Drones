package co.edu.poli.sw2.Aplicacion.Servicios;

import java.util.Objects;

import co.edu.poli.sw2.Aplicacion.Puerto.Entrada.ExecuteMissionUseCase;
import co.edu.poli.sw2.Aplicacion.mediator.DroneMediator;
import co.edu.poli.sw2.Aplicacion.mediator.MissionRequest;
import co.edu.poli.sw2.Aplicacion.mediator.MissionResult;

/** Servicio de aplicación para ejecutar una misión mediante el mediador. */
public class MissionService implements ExecuteMissionUseCase {
    private final DroneMediator mediator;

    public MissionService(DroneMediator mediator) {
        this.mediator = Objects.requireNonNull(mediator, "mediator");
    }

    @Override
    public MissionResult executeMission(MissionRequest request) {
        return mediator.coordinateSurveillance(Objects.requireNonNull(request, "request"));
    }
}
