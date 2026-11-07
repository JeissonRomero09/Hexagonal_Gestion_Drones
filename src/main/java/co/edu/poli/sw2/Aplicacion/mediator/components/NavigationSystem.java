package co.edu.poli.sw2.Aplicacion.mediator.components;

import java.util.Arrays;

/** Simula la preparación de la ruta de una misión. */
public class NavigationSystem {
    public boolean prepareRoute(String route) {
        if (route == null || route.isBlank()) {
            return false;
        }
        String[] segments = route.split("-", -1);
        return segments.length >= 2
                && Arrays.stream(segments).allMatch(segment -> !segment.isBlank());
    }
}
