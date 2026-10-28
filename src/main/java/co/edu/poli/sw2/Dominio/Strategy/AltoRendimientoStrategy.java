package co.edu.poli.sw2.Dominio.Strategy;

/** Estima el consumo elevado para un vuelo de alto rendimiento. */
public class AltoRendimientoStrategy implements ConsumoStrategy {
	@Override
	public double calcularBateria(double distanciaKm) {
		validarDistancia(distanciaKm);
		return distanciaKm * 1.5;
	}

	@Override
	public String getNombreEstrategia() {
		return "Alto rendimiento";
	}

	private void validarDistancia(double distanciaKm) {
		if (!Double.isFinite(distanciaKm) || distanciaKm < 0) {
			throw new IllegalArgumentException("La distancia debe ser un valor finito no negativo.");
		}
	}
}
