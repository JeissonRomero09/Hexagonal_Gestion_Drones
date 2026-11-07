package co.edu.poli.sw2.integracion;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import co.edu.poli.sw2.Dominio.modelo.Dron;
import co.edu.poli.sw2.Infraestuctura.Persistencia.ConexionBD;
import co.edu.poli.sw2.Infraestuctura.Persistencia.MySqlDronRepository;

class DatabaseIntegrationTest {
	@Test
	void conectaYConsultaLosDronesSinModificarLaBaseDeDatos() throws SQLException {
		Assumptions.assumeTrue(Boolean.getBoolean("db.integration"),
				"Active esta prueba con -Ddb.integration=true y configure una base MySQL disponible.");

		Connection connection = ConexionBD.getInstance().conectar();

		try (connection) {
			assertTrue(connection.isValid(2), "La conexión con la base de datos debe estar activa.");
		}

		List<Dron> drones = new MySqlDronRepository().buscarTodos();
		assertNotNull(drones, "El repositorio debe devolver una lista, incluso cuando no hay drones.");
	}
}