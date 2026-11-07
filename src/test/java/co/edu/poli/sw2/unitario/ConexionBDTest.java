package co.edu.poli.sw2.unitario;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import co.edu.poli.sw2.Infraestuctura.Persistencia.ConexionBD;

class ConexionBDTest {
	@Test
	void comparteUnaUnicaInstanciaDeConexionBD() {
		assertSame(ConexionBD.getInstance(), ConexionBD.getInstance());
	}
}