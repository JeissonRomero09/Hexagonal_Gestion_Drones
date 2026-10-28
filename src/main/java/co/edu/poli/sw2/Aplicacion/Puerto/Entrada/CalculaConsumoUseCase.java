package co.edu.poli.sw2.Aplicacion.Puerto.Entrada;

import java.util.Objects;

import co.edu.poli.sw2.Dominio.Strategy.ConsumoStrategy;
import co.edu.poli.sw2.Dominio.Strategy.PuntoGeografico;

/** Contexto que aplica una estrategia de consumo a un dron y una ruta. */
public class CalculaConsumoUseCase {
	private ConsumoStrategy strategy;
	private String dron;
	private double distanciaKm;
	private double bateriaRequerida;

	public CalculaConsumoUseCase(ConsumoStrategy strategy, String dron) {
		this.strategy = Objects.requireNonNull(strategy, "strategy");
		this.dron = Objects.requireNonNull(dron, "dron");
	}

	public void setEstrategia(ConsumoStrategy estrategia) {
		this.strategy = Objects.requireNonNull(estrategia, "estrategia");
	}

	public void ejecutar(PuntoGeografico origen, PuntoGeografico destino) {
		this.distanciaKm = Objects.requireNonNull(origen, "origen").distanciaA(destino);
		this.bateriaRequerida = strategy.calcularBateria(distanciaKm);
	}

	public String getDron() {
		return dron;
	}

	public void setDron(String dron) {
		this.dron = Objects.requireNonNull(dron, "dron");
	}

	public ConsumoStrategy getEstrategia() {
		return strategy;
	}

	public double getDistanciaKm() {
		return distanciaKm;
	}

	public double getBateriaRequerida() {
		return bateriaRequerida;
	}
}
