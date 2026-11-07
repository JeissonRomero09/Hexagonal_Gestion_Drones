package co.edu.poli.sw2.Aplicacion.mediator;

import java.util.List;
import java.util.Objects;

/** Resultado y pasos registrados durante la coordinación de una misión. */
public final class MissionResult {
    private final boolean started;
    private final List<String> steps;

    public MissionResult(boolean started, List<String> steps) {
        this.started = started;
        this.steps = List.copyOf(Objects.requireNonNull(steps, "steps"));
    }

    public boolean isStarted() {
        return started;
    }

    public List<String> getSteps() {
        return steps;
    }
}
