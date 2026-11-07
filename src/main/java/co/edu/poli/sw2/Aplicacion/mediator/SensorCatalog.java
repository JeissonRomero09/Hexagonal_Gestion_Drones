package co.edu.poli.sw2.Aplicacion.mediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Catálogo inmutable de sensores disponibles para las misiones. */
public final class SensorCatalog {
    private static final List<SensorDefinition> HIERARCHY = List.of(
            new SensorDefinition("Sensor Temperatura", List.of(
                    new SensorDefinition("Sensor Infrarrojo"),
                    new SensorDefinition("RTD"))),
            new SensorDefinition("Sensor Cámara", List.of(
                    new SensorDefinition("Sensor CMOS"),
                    new SensorDefinition("Sensor CCD"))),
            new SensorDefinition("Sensor Sonido", List.of(
                    new SensorDefinition("Sensor Analógico"),
                    new SensorDefinition("Sensor Digital", List.of(
                            new SensorDefinition("SPI"),
                            new SensorDefinition("UART"))))),
            new SensorDefinition("Sensor inteligente"));

    private SensorCatalog() {
    }

    public static List<SensorDefinition> getHierarchy() {
        return HIERARCHY;
    }

    public static List<String> getSelectableSensorNames() {
        List<String> names = new ArrayList<>();
        collectSelectableNames(HIERARCHY, names);
        return List.copyOf(names);
    }

    private static void collectSelectableNames(List<SensorDefinition> definitions, List<String> names) {
        for (SensorDefinition definition : definitions) {
            if (definition.getChildren().isEmpty()) {
                names.add(definition.getName());
            } else {
                collectSelectableNames(definition.getChildren(), names);
            }
        }
    }

    /** Nodo inmutable del árbol de sensores. */
    public static final class SensorDefinition {
        private final String name;
        private final List<SensorDefinition> children;

        private SensorDefinition(String name) {
            this(name, List.of());
        }

        private SensorDefinition(String name, List<SensorDefinition> children) {
            this.name = Objects.requireNonNull(name, "name");
            this.children = List.copyOf(Objects.requireNonNull(children, "children"));
        }

        public String getName() {
            return name;
        }

        public List<SensorDefinition> getChildren() {
            return children;
        }
    }
}
