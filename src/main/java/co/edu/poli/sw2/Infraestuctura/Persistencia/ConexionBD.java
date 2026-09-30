package co.edu.poli.sw2.Infraestuctura.Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Proveedor Singleton de conexiones a la base de datos.
 *
 * <p>
 * La información de conexión se obtiene desde las variables de entorno
 * definidas en el archivo {@code .env}.
 * </p>
 *
 * @author Jeison Romero
 * @version 1.0
 */
public class ConexionBD {

	/**
	 * Objeto utilizado para cargar las variables de entorno desde el archivo
	 * {@code .env}.
	 */
	private static final Dotenv dotenv = Dotenv.load();

	/**
	 * URL de conexión a la base de datos.
	 */
	private static final String URL = dotenv.get("DB_URL");

	/**
	 * Usuario utilizado para conectarse a la base de datos.
	 */
	private static final String USUARIO = dotenv.get("DB_USER");

	/**
	 * Contraseña utilizada para conectarse a la base de datos.
	 */
	private static final String PASSWORD = dotenv.get("DB_PASSWORD");
	/** Instancia única compartida por todos los adaptadores de persistencia. */
	private static final ConexionBD INSTANCIA = new ConexionBD();

	/**
	 * Constructor privado para evitar la creación de objetos de esta clase.
	 */
	private ConexionBD() {
	}

	/**
	 * Obtiene la instancia única del proveedor de conexiones.
	 *
	 * @return instancia compartida de {@code ConexionBD}
	 */
	public static ConexionBD getInstance() {
		return INSTANCIA;
	}

	/**
	 * Establece una conexión con la base de datos.
	 *
	 * @return objeto {@link Connection} correspondiente a la conexión.
	 * @throws SQLException si ocurre un error durante la conexión.
	 */
	public Connection conectar() throws SQLException {

		return DriverManager.getConnection(
				URL,
				USUARIO,
				PASSWORD
		);
	}
}
