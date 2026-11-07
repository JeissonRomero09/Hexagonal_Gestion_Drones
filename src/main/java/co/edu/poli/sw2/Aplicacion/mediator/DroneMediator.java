package co.edu.poli.sw2.Aplicacion.mediator;

/** Coordina los sistemas que participan en una misión de vigilancia. */
public interface DroneMediator {
    MissionResult coordinateSurveillance(MissionRequest request);
}
