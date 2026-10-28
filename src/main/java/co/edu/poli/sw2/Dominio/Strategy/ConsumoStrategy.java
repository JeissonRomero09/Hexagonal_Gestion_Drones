package co.edu.poli.sw2.Dominio.Strategy;

/** Define la política usada para estimar el consumo energético de un recorrido. */
public interface ConsumoStrategy {
	double calcularBateria(double distanciaKm);

	String getNombreEstrategia();

	default String getNombrestrategy() {
		return getNombreEstrategia();
	}
}
