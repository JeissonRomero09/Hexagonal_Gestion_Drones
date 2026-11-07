package co.edu.poli.sw2.Aplicacion.mediator.components;

/** Simula el establecimiento del enlace de comunicación con el dron. */
public class CommunicationSystem {
    private final boolean linkAvailable;

    public CommunicationSystem() {
        this(true);
    }

    public CommunicationSystem(boolean linkAvailable) {
        this.linkAvailable = linkAvailable;
    }

    public boolean establishLink() {
        return linkAvailable;
    }
}
