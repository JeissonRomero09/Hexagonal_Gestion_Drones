package co.edu.poli.sw2.Dominio.state;

import java.util.Objects;

import co.edu.poli.sw2.Dominio.modelo.Dron;

/** Contexto del patrón State; mantiene el estado operativo fuera del modelo Dron. */
public class DronStateWrapper {
	private final Dron dron;
	private DroneState estado;

	public DronStateWrapper(Dron dron) {
		this(dron, new OffState());
	}

	public DronStateWrapper(Dron dron, DroneState estadoInicial) {
		this.dron = Objects.requireNonNull(dron, "dron");
		if (dron.getId() <= 0) {
			throw new IllegalArgumentException("El ID del dron debe ser positivo.");
		}
		this.estado = Objects.requireNonNull(estadoInicial, "estadoInicial");
	}

	public Dron getDron() {
		return dron;
	}

	public String getNombreEstado() {
		return estado.getNombre();
	}

	public void encender() {
		estado.encender(this);
	}

	public void despegar() {
		estado.despegar(this);
	}

	public void iniciarAterrizaje() {
		estado.iniciarAterrizaje(this);
	}

	public void finalizarAterrizaje() {
		estado.finalizarAterrizaje(this);
	}

	public void apagar() {
		estado.apagar(this);
	}

	void cambiarEstado(DroneState nuevoEstado) {
		this.estado = nuevoEstado;
	}
}
