package co.edu.poli.sw2.Dominio.Strategy;

/** Estima el consumo estándar para un vuelo. */
public class NormalStrategy implements ConsumoStrategy {
	@Override
	public double calcularBateria(double distanciaKm) {
		validarDistancia(distanciaKm);
		return distanciaKm;
	}

	@Override
	public String getNombreEstrategia() {
		return "Normal";
	}

	private void validarDistancia(double distanciaKm) {
		if (!Double.isFinite(distanciaKm) || distanciaKm < 0) {
			throw new IllegalArgumentException("La distancia debe ser un valor finito no negativo.");
		}
	}
}
