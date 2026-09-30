package co.edu.poli.sw2.Infraestuctura.Persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import co.edu.poli.sw2.Aplicacion.Puerto.Salida.RepositoryDron;
import co.edu.poli.sw2.Dominio.modelo.Dron;
import co.edu.poli.sw2.Dominio.modelo.Piloto;
import co.edu.poli.sw2.Dominio.modelo.Sensores;

/**
 * Adaptador de persistencia para la entidad {@link Dron}.
 *
 * <p>
 * Implementa el puerto {@link RepositoryDron} y utiliza JDBC para realizar las
 * operaciones de persistencia sobre la base de datos.
 * </p>
 *
 * <p>
 * Las conexiones son obtenidas mediante {@link ConexionBD}.
 * </p>
 *
 * @author Jeisson Romero
 * @version 1.0
 */
public class MySqlDronRepository implements RepositoryDron {

	/**
	 * Consulta utilizada para recuperar un dron junto con su piloto.
	 *
	 * <p>
	 * La base de datos actual no contiene una columna sensor_id en la tabla dron,
	 * por lo que el sensor no puede recuperarse mediante una relación persistente.
	 * </p>
	 */
	private static final String SELECT_DRON = "SELECT d.id, d.serial, d.modelo, d.fabricante, d.peso, "
			+ "p.id AS piloto_id, " + "p.nombre AS piloto_nombre, " + "p.experiencia AS piloto_experiencia, "
			+ "p.telefono AS piloto_telefono " + "FROM dron d " + "LEFT JOIN piloto p ON p.id = d.piloto_id ";

	/**
	 * Crea el adaptador de persistencia.
	 */
	public MySqlDronRepository() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int crear(Dron dron, int pilotoId, List<Integer> sensorIds) {

		String insertDron = "INSERT INTO dron " + "(serial, modelo, fabricante, peso, piloto_id) "
				+ "VALUES (?, ?, ?, ?, ?)";

		String validarSensor = "SELECT id " + "FROM sensor " + "WHERE id = ?";

		String insertDronSensor = "INSERT INTO dron_sensor (dron_id, sensor_id) " + "VALUES (?, ?)";

		try (Connection connection = ConexionBD.getInstance().conectar()) {

			connection.setAutoCommit(false);

			try {

				/*
				 * 1. Verificar que los sensores existan.
				 */
				for (Integer sensorId : sensorIds) {

					try (PreparedStatement statement = connection.prepareStatement(validarSensor)) {

						statement.setInt(1, sensorId);

						try (ResultSet resultSet = statement.executeQuery()) {

							if (!resultSet.next()) {
								throw new SQLException("El sensor con ID " + sensorId + " no existe.");
							}
						}
					}
				}

				/*
				 * 2. Crear el dron.
				 */
				int id;

				try (PreparedStatement statement = connection.prepareStatement(insertDron,
						Statement.RETURN_GENERATED_KEYS)) {

					statement.setString(1, dron.getSerial());
					statement.setString(2, dron.getModelo());
					statement.setString(3, dron.getFabricante());
					statement.setInt(4, dron.getPeso());
					statement.setInt(5, pilotoId);

					statement.executeUpdate();

					try (ResultSet generatedKeys = statement.getGeneratedKeys()) {

						if (!generatedKeys.next()) {
							throw new SQLException("La base de datos no generó " + "el identificador del dron.");
						}

						id = generatedKeys.getInt(1);
					}
				}

				/*
				 * 3. Asociar los sensores al dron.
				 */
				try (PreparedStatement statement = connection.prepareStatement(insertDronSensor)) {

					for (Integer sensorId : sensorIds) {

						statement.setInt(1, id);
						statement.setInt(2, sensorId);

						statement.executeUpdate();
					}
				}

				/*
				 * 4. Confirmar toda la operación.
				 */
				connection.commit();

				return id;

			} catch (SQLException exception) {

				rollback(connection, exception);

				throw persistenceError("No se pudo crear el dron: " + exception.getMessage(), exception);
			}

		} catch (SQLException exception) {

			throw persistenceError("No se pudo conectar o guardar el dron: " + exception.getMessage(), exception);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dron buscar(int id) {

		try (Connection connection = ConexionBD.getInstance().conectar();

				PreparedStatement statement = connection.prepareStatement(SELECT_DRON + "WHERE d.id = ?")) {

			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {

				if (resultSet.next()) {
					return mapearDron(resultSet);
				}

				return null;
			}

		} catch (SQLException exception) {

			throw persistenceError("No se pudo consultar el dron: " + exception.getMessage(), exception);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Dron> buscarTodos() {

		List<Dron> drones = new ArrayList<>();

		try (Connection connection = ConexionBD.getInstance().conectar();

				PreparedStatement statement = connection.prepareStatement(SELECT_DRON + "ORDER BY d.id")) {

			try (ResultSet resultSet = statement.executeQuery()) {

				while (resultSet.next()) {
					drones.add(mapearDron(resultSet));
				}
			}

			return drones;

		} catch (SQLException exception) {

			throw persistenceError("No se pudo listar los drones: " + exception.getMessage(), exception);
		}
	}

	/** * {@inheritDoc} */
	@Override
	public boolean actualizar(Dron dron, int pilotoId, List<Integer> sensorIds) {
		String updateDron = "UPDATE dron SET " + "serial = ?, " + "modelo = ?, " + "fabricante = ?, " + "peso = ?, "
				+ "piloto_id = ? " + "WHERE id = ?";
		String validarSensor = "SELECT id " + "FROM sensor " + "WHERE id = ?";
		String deleteSensores = "DELETE FROM dron_sensor " + "WHERE dron_id = ?";
		String insertSensor = "INSERT INTO dron_sensor (dron_id, sensor_id) " + "VALUES (?, ?)";
		try (Connection connection = ConexionBD.getInstance().conectar()) {
			connection.setAutoCommit(false);
			try {
				/* * 1. Verifica que los sensores existan. */ for (Integer sensorId : sensorIds) {
					try (PreparedStatement statement = connection.prepareStatement(validarSensor)) {
						statement.setInt(1, sensorId);
						try (ResultSet resultSet = statement.executeQuery()) {
							if (!resultSet.next()) {
								throw new SQLException("El sensor con ID " + sensorId + " no existe.");
							}
						}
					}
				}
				/* * 2. Actualiza la información principal del dron. */ int updated;
				try (PreparedStatement statement = connection.prepareStatement(updateDron)) {
					statement.setString(1, dron.getSerial());
					statement.setString(2, dron.getModelo());
					statement.setString(3, dron.getFabricante());
					statement.setInt(4, dron.getPeso());
					statement.setInt(5, pilotoId);
					statement.setInt(6, dron.getId());
					updated = statement.executeUpdate();
				}
				/* * Si el dron no existe, no continúa. */ if (updated == 0) {
					connection.rollback();
					return false;
				}
				/* * 3. Elimina los sensores que tenía * anteriormente el dron. */ try (
						PreparedStatement statement = connection.prepareStatement(deleteSensores)) {
					statement.setInt(1, dron.getId());
					statement.executeUpdate();
				}
				/* * 4. Inserta nuevamente los sensores * enviados en sensorIds. */ try (
						PreparedStatement statement = connection.prepareStatement(insertSensor)) {
					for (Integer sensorId : sensorIds) {
						statement.setInt(1, dron.getId());
						statement.setInt(2, sensorId);
						statement.executeUpdate();
					}
				}
				/* * 5. Confirma toda la transacción. */ connection.commit();
				return true;
			} catch (SQLException exception) {
				rollback(connection, exception);
				throw persistenceError("No se pudo actualizar el dron: " + exception.getMessage(), exception);
			}
		} catch (SQLException exception) {
			throw persistenceError("No se pudo conectar o actualizar el dron: " + exception.getMessage(), exception);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean eliminar(int id) {

	    String deleteSensores =
	            "DELETE FROM dron_sensor "
	            + "WHERE dron_id = ?";

	    String deleteDron =
	            "DELETE FROM dron "
	            + "WHERE id = ?";

	    try (Connection connection =
	                 ConexionBD.getInstance().conectar()) {

	        connection.setAutoCommit(false);

	        try {

	            /*
	             * Elimina las relaciones del dron con sus sensores.
	             */
	            try (PreparedStatement statement =
	                         connection.prepareStatement(deleteSensores)) {

	                statement.setInt(1, id);
	                statement.executeUpdate();
	            }

	            /*
	             * Elimina el dron.
	             */
	            int deleted;

	            try (PreparedStatement statement =
	                         connection.prepareStatement(deleteDron)) {

	                statement.setInt(1, id);
	                deleted = statement.executeUpdate();
	            }

	            connection.commit();

	            return deleted > 0;

	        } catch (SQLException exception) {

	            rollback(connection, exception);

	            throw persistenceError(
	                    "No se pudo eliminar el dron: "
	                    + exception.getMessage(),
	                    exception
	            );
	        }

	    } catch (SQLException exception) {

	        throw persistenceError(
	                "No se pudo conectar o eliminar el dron: "
	                + exception.getMessage(),
	                exception
	        );
	    }
	}
	/**
	 * Convierte una fila del resultado SQL en un objeto de dominio.
	 *
	 * @param resultSet resultado de la consulta
	 * @return objeto {@link Dron} construido a partir del resultado
	 * @throws SQLException si ocurre un error al leer los datos
	 */
	private Dron mapearDron(ResultSet resultSet) throws SQLException {

		Dron dron = new Dron(resultSet.getInt("id"), resultSet.getString("serial"), resultSet.getString("modelo"),
				resultSet.getString("fabricante"), resultSet.getInt("peso"));

		/*
		 * Recupera el piloto asociado.
		 */
		int pilotoId = resultSet.getInt("piloto_id");

		if (!resultSet.wasNull()) {

			Piloto piloto = new Piloto(pilotoId, resultSet.getString("piloto_nombre"),
					resultSet.getString("piloto_experiencia"), resultSet.getInt("piloto_telefono"));

			dron.setPiloto(piloto);
		}

		/*
		 * El sensor no se asigna aquí porque la tabla dron no posee sensor_id y no
		 * existe una relación persistente disponible para determinar qué sensor
		 * pertenece al dron.
		 */

		return dron;
	}

	/**
	 * Revierte una transacción cuando ocurre un error.
	 *
	 * @param connection        conexión utilizada para la transacción
	 * @param originalException excepción que provocó el rollback
	 */
	private void rollback(Connection connection, SQLException originalException) {

		try {

			connection.rollback();

		} catch (SQLException rollbackException) {

			originalException.addSuppressed(rollbackException);
		}
	}

	/**
	 * Construye la excepción utilizada para informar errores relacionados con la
	 * persistencia.
	 *
	 * @param message mensaje descriptivo del error
	 * @param cause   excepción SQL original
	 * @return excepción de persistencia
	 */
	private IllegalStateException persistenceError(String message, SQLException cause) {

		return new IllegalStateException(message, cause);
	}
}
