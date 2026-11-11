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
}
