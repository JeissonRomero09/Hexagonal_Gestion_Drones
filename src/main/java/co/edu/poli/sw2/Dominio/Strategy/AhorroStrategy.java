package co.edu.poli.sw2.Dominio.Strategy;

/** Estima el consumo reducido para un vuelo en modo ahorro. */
public class AhorroStrategy implements ConsumoStrategy {
	@Override
	public double calcularBateria(double distanciaKm) {
		validarDistancia(distanciaKm);
		return distanciaKm * 0.5;
	}

	@Override
	public String getNombreEstrategia() {
		return "Ahorro";
	}

	private void validarDistancia(double distanciaKm) {
		if (!Double.isFinite(distanciaKm) || distanciaKm < 0) {
			throw new IllegalArgumentException("La distancia debe ser un valor finito no negativo.");
		}
	}
}
