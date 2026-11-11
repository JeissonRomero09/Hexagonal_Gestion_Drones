package co.edu.poli.sw2.Dominio.Strategy;

/** Define la política usada para estimar el consumo energético de un recorrido. */
public interface ConsumoStrategy {
	double calcularBateria(double distanciaKm);

	String getNombreEstrategia();

	default void validarDistancia(double distanciaKm) {
		if (!Double.isFinite(distanciaKm) || distanciaKm < 0) {
			throw new IllegalArgumentException("La distancia debe ser un valor finito no negativo.");
		}
	}

	default String getNombrestrategy() {
		return getNombreEstrategia();
	}
}
