
package co.edu.poli.sw2.Infraestuctura.Persistencia;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotEnvException;

/**
 * Proveedor Singleton de conexiones a la base de datos.
 *
 * <p>
 * La información de conexión se obtiene desde las variables de entorno
 * definidas en el archivo {@code .env}.
 * </p>
 *
 * @author Jeisson Romero
 * @version 1.0
 */
public class ConexionBD {

    /**
     * Instancia única compartida por todos los adaptadores de persistencia.
     */
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
     * @return objeto {@link Connection} correspondiente a la conexión
     * @throws SQLException si ocurre un error durante la conexión
     */
    public Connection conectar() throws SQLException {
        Dotenv dotenv = cargarConfiguracion();

        String url = obtenerConfiguracion(dotenv, "DB_URL");
        String usuario = obtenerConfiguracion(dotenv, "DB_USER");
        String password = obtenerConfiguracion(dotenv, "DB_PASSWORD");
        return DriverManager.getConnection(url, usuario, password);
    }

    private Dotenv cargarConfiguracion() throws SQLException {
        Path directorioActual = Paths.get(System.getProperty("user.dir")).toAbsolutePath().normalize();
        Path archivoEnv = buscarArchivoEnv(directorioActual);

        try {
            Path ubicacionClases = Paths.get(
                    ConexionBD.class.getProtectionDomain().getCodeSource().getLocation().toURI())
                    .toAbsolutePath().normalize();
            Path directorioClases = Files.isDirectory(ubicacionClases)
                    ? ubicacionClases
                    : ubicacionClases.getParent();
            if (archivoEnv == null && directorioClases != null) {
                archivoEnv = buscarArchivoEnv(directorioClases);
            }
        } catch (URISyntaxException exception) {
            throw new SQLException("No se pudo localizar la configuración de la base de datos.", exception);
        }

        Path directorioEnv = archivoEnv == null ? directorioActual : archivoEnv.getParent();
        try {
            return cargarDotenv(directorioEnv);
        } catch (DotEnvException exception) {
            throw new SQLException("No se pudo leer el archivo .env del proyecto.", exception);
        }
    }

    private Dotenv cargarDotenv(Path directorioEnv) throws DotEnvException {
        return Dotenv.configure()
                .directory(directorioEnv.toString())
                .ignoreIfMissing()
                .load();
    }

    private Path buscarArchivoEnv(Path directorio) {
        Path actual = directorio;
        while (actual != null) {
            Path candidato = actual.resolve(".env");
            if (Files.isRegularFile(candidato)) {
                return candidato;
            }
            actual = actual.getParent();
        }
        return null;
    }

    private String obtenerConfiguracion(Dotenv dotenv, String clave) throws SQLException {
        String valor = System.getenv(clave);
        if (valor == null) {
            valor = dotenv.get(clave);
        }
        if (valor == null || (!"DB_PASSWORD".equals(clave) && valor.trim().isEmpty())) {
            throw new SQLException("Falta configurar " + clave
                    + ". Defínala como variable de entorno o en el archivo .env del proyecto.");
        }
        return valor;
    }
}
