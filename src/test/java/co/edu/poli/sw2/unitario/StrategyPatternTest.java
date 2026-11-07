package co.edu.poli.sw2.unitario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import co.edu.poli.sw2.Dominio.Strategy.AhorroStrategy;
import co.edu.poli.sw2.Dominio.Strategy.AltoRendimientoStrategy;
import co.edu.poli.sw2.Dominio.Strategy.ConsumoStrategy;
import co.edu.poli.sw2.Dominio.Strategy.NormalStrategy;

class StrategyPatternTest {
	private final List<ConsumoStrategy> estrategias = List.of(
			new AhorroStrategy(), new NormalStrategy(), new AltoRendimientoStrategy());

	@Test
	void calculaElConsumoSegunLaEstrategiaElegida() {
		assertEquals(5.0, new AhorroStrategy().calcularBateria(10), 0.0001);
		assertEquals(10.0, new NormalStrategy().calcularBateria(10), 0.0001);
		assertEquals(15.0, new AltoRendimientoStrategy().calcularBateria(10), 0.0001);
	}

	@Test
	void identificaCadaEstrategia() {
		assertEquals(List.of("Ahorro", "Normal", "Alto rendimiento"),
				estrategias.stream().map(ConsumoStrategy::getNombreEstrategia).toList());
	}

	@Test
	void rechazaDistanciasNegativasONoFinitas() {
		for (ConsumoStrategy estrategia : estrategias) {
			assertThrows(IllegalArgumentException.class, () -> estrategia.calcularBateria(-1));
			assertThrows(IllegalArgumentException.class, () -> estrategia.calcularBateria(Double.NaN));
			assertThrows(IllegalArgumentException.class, () -> estrategia.calcularBateria(Double.POSITIVE_INFINITY));
		}
	}
}