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
}
