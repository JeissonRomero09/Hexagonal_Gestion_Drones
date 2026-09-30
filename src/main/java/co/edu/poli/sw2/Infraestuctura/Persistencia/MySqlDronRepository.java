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
 * Adaptador de persistencia MySQL para el puerto {@link RepositoryDron}.
 *
 * <p>Las operaciones JDBC se ejecutan aquí y obtienen sus conexiones de
 * {@link ConexionBD}; los fallos SQL se exponen como {@link IllegalStateException}.</p>
 */
public class MySqlDronRepository implements RepositoryDron {

    /** Consulta común que recupera los datos del dron y sus asociaciones. */
    private static final String SELECT_DRON =
            "SELECT d.id, d.serial, d.modelo, d.fabricante, d.peso, "
            + "p.id AS piloto_id, p.nombre AS piloto_nombre, "
            + "p.experiencia AS piloto_experiencia, p.telefono AS piloto_telefono, "
            + "s.id AS sensor_id, s.tipo AS sensor_tipo, s.fabricante AS sensor_fabricante "
            + "FROM dron d "
            + "LEFT JOIN piloto p ON p.id = d.piloto_id "
            + "LEFT JOIN (SELECT dron_id, MIN(sensor_id) AS sensor_id FROM dron_sensor GROUP BY dron_id) ds "
            + "ON ds.dron_id = d.id "
            + "LEFT JOIN sensor s ON s.id = ds.sensor_id ";

            /** Crea el adaptador de persistencia. */
            public MySqlDronRepository() {
            }

            /** {@inheritDoc} */
    @Override
    public int crear(Dron dron, int pilotoId, int sensorId) {
        String insertDron = "INSERT INTO dron (serial, modelo, fabricante, peso, piloto_id) VALUES (?, ?, ?, ?, ?)";
        String insertSensor = "INSERT INTO dron_sensor (dron_id, sensor_id) VALUES (?, ?)";

        try (Connection connection = ConexionBD.getInstance().conectar()) {
            connection.setAutoCommit(false);
            try {
                int id;
                try (PreparedStatement statement = connection.prepareStatement(insertDron, Statement.RETURN_GENERATED_KEYS)) {
                    statement.setString(1, dron.getSerial());
                    statement.setString(2, dron.getModelo());
                    statement.setString(3, dron.getFabricante());
                    statement.setInt(4, dron.getPeso());
                    statement.setInt(5, pilotoId);
                    statement.executeUpdate();
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (!generatedKeys.next()) {
                            throw new SQLException("La base de datos no generó el identificador del dron.");
                        }
                        id = generatedKeys.getInt(1);
                    }
                }
                try (PreparedStatement statement = connection.prepareStatement(insertSensor)) {
                    statement.setInt(1, id);
                    statement.setInt(2, sensorId);
                    statement.executeUpdate();
                }
                connection.commit();
                return id;
            } catch (SQLException exception) {
                rollback(connection, exception);
                throw persistenceError("No se pudo crear el dron.", exception);
            }
        } catch (SQLException exception) {
            throw persistenceError("No se pudo conectar o guardar el dron.", exception);
        }
    }

    /** {@inheritDoc} */
    @Override
    public Dron buscar(int id) {
        try (Connection connection = ConexionBD.getInstance().conectar();
                PreparedStatement statement = connection.prepareStatement(SELECT_DRON + "WHERE d.id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? mapearDron(resultSet) : null;
            }
        } catch (SQLException exception) {
            throw persistenceError("No se pudo consultar el dron.", exception);
        }
    }

    /** {@inheritDoc} */
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
            throw persistenceError("No se pudo listar los drones.", exception);
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean actualizar(Dron dron, int pilotoId, int sensorId) {
        String updateDron = "UPDATE dron SET serial = ?, modelo = ?, fabricante = ?, peso = ?, piloto_id = ? WHERE id = ?";
        String deleteSensor = "DELETE FROM dron_sensor WHERE dron_id = ?";
        String insertSensor = "INSERT INTO dron_sensor (dron_id, sensor_id) VALUES (?, ?)";

        try (Connection connection = ConexionBD.getInstance().conectar()) {
            connection.setAutoCommit(false);
            try {
                int updated;
                try (PreparedStatement statement = connection.prepareStatement(updateDron)) {
                    statement.setString(1, dron.getSerial());
                    statement.setString(2, dron.getModelo());
                    statement.setString(3, dron.getFabricante());
                    statement.setInt(4, dron.getPeso());
                    statement.setInt(5, pilotoId);
                    statement.setInt(6, dron.getId());
                    updated = statement.executeUpdate();
                }
                if (updated == 0) {
                    connection.rollback();
                    return false;
                }
                try (PreparedStatement statement = connection.prepareStatement(deleteSensor)) {
                    statement.setInt(1, dron.getId());
                    statement.executeUpdate();
                }
                try (PreparedStatement statement = connection.prepareStatement(insertSensor)) {
                    statement.setInt(1, dron.getId());
                    statement.setInt(2, sensorId);
                    statement.executeUpdate();
                }
                connection.commit();
                return true;
            } catch (SQLException exception) {
                rollback(connection, exception);
                throw persistenceError("No se pudo actualizar el dron.", exception);
            }
        } catch (SQLException exception) {
            throw persistenceError("No se pudo conectar o actualizar el dron.", exception);
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean eliminar(int id) {
        try (Connection connection = ConexionBD.getInstance().conectar()) {
            connection.setAutoCommit(false);
            try {
                try (PreparedStatement statement = connection.prepareStatement("DELETE FROM dron_sensor WHERE dron_id = ?")) {
                    statement.setInt(1, id);
                    statement.executeUpdate();
                }
                int deleted;
                try (PreparedStatement statement = connection.prepareStatement("DELETE FROM dron WHERE id = ?")) {
                    statement.setInt(1, id);
                    deleted = statement.executeUpdate();
                }
                connection.commit();
                return deleted > 0;
            } catch (SQLException exception) {
                rollback(connection, exception);
                throw persistenceError("No se pudo eliminar el dron.", exception);
            }
        } catch (SQLException exception) {
            throw persistenceError("No se pudo conectar o eliminar el dron.", exception);
        }
    }

    /**
     * Convierte la fila actual de la consulta en un modelo de dominio.
     *
     * @param resultSet fila con los datos del dron y sus relaciones
     * @return dron construido desde la fila actual
     * @throws SQLException si no se pueden leer las columnas
     */
    private Dron mapearDron(ResultSet resultSet) throws SQLException {
        Dron dron = new Dron(resultSet.getInt("id"), resultSet.getString("serial"),
                resultSet.getString("modelo"), resultSet.getString("fabricante"), resultSet.getInt("peso"));

        int pilotoId = resultSet.getInt("piloto_id");
        if (!resultSet.wasNull()) {
            dron.setPiloto(new Piloto(pilotoId, resultSet.getString("piloto_nombre"),
                    resultSet.getString("piloto_experiencia"), resultSet.getInt("piloto_telefono")));
        }

        int sensorId = resultSet.getInt("sensor_id");
        if (!resultSet.wasNull()) {
            dron.setSensores(new Sensores(sensorId, resultSet.getString("sensor_tipo"),
                    resultSet.getString("sensor_fabricante"), dron.getId()));
        }
        return dron;
    }

    /**
     * Intenta deshacer una transacción y conserva cualquier fallo secundario.
     *
     * @param connection conexión cuya transacción se revierte
     * @param originalException excepción que causó el rollback
     */
    private void rollback(Connection connection, SQLException originalException) {
        try {
            connection.rollback();
        } catch (SQLException rollbackException) {
            originalException.addSuppressed(rollbackException);
        }
    }

    /**
     * Construye la excepción no comprobada usada para informar fallos de persistencia.
     *
     * @param message descripción de la operación que falló
     * @param cause excepción SQL original
     * @return excepción de persistencia con su causa original
     */
    private IllegalStateException persistenceError(String message, SQLException cause) {
        return new IllegalStateException(message, cause);
    }
}