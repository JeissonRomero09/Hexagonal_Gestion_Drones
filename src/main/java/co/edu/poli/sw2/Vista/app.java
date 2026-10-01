package co.edu.poli.sw2.Vista;

import java.sql.Connection;
import java.sql.SQLException;

import co.edu.poli.sw2.Aplicacion.Puerto.Entrada.BuscarDronUseCase;
import co.edu.poli.sw2.Aplicacion.Puerto.Entrada.BuscarListaDronesUseCase;
import co.edu.poli.sw2.Aplicacion.Puerto.Entrada.CrearDronUseCase;
import co.edu.poli.sw2.Aplicacion.Puerto.Entrada.EditarDronUseCase;
import co.edu.poli.sw2.Aplicacion.Puerto.Entrada.EliminarDronUseCase;
import co.edu.poli.sw2.Aplicacion.Servicios.BuscarDronServicio;
import co.edu.poli.sw2.Aplicacion.Servicios.BuscarListaDronesServicio;
import co.edu.poli.sw2.Aplicacion.Servicios.CrearDronServicio;
import co.edu.poli.sw2.Aplicacion.Servicios.EditarDronServicio;
import co.edu.poli.sw2.Aplicacion.Servicios.EliminarDronServicio;
import co.edu.poli.sw2.Infraestuctura.Persistencia.ConexionBD;
import co.edu.poli.sw2.Infraestuctura.Persistencia.MySqlDronRepository;
import co.edu.poli.sw2.Infraestuctura.UI.DroneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación del sistema de gestión de drones.
 *
 * <p>Esta clase inicia la aplicación JavaFX, carga la interfaz
 * definida en el archivo {@code drone.fxml} y configura la
 * ventana principal del sistema.</p>
 *
 * @author Jeison Romero
 * @version 1.0
 */
public class app extends Application {

    /** Crea el punto de entrada de la aplicación JavaFX. */
    public app() {
    }

    /**
     * Inicia la aplicación JavaFX y configura la ventana principal.
     *
     * <p>Este método carga el archivo FXML correspondiente a la
     * interfaz del sistema de drones, crea la escena y muestra
     * la ventana principal.</p>
     *
     * @param stage ventana principal proporcionada por JavaFX.
     * @throws Exception si ocurre un error al cargar el archivo FXML
     *         o al inicializar la interfaz.
     */
    @Override
    public void start(Stage stage) throws Exception {

        MySqlDronRepository repository = new MySqlDronRepository();
        CrearDronUseCase crear = new CrearDronServicio(repository);
        BuscarDronUseCase buscar = new BuscarDronServicio(repository);
        EliminarDronUseCase eliminar = new EliminarDronServicio(repository);
        BuscarListaDronesUseCase listar = new BuscarListaDronesServicio(repository);
        EditarDronUseCase editar = new EditarDronServicio(repository);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/poli/sw2/Drone.fxml"));
        loader.setControllerFactory(controllerType -> {
            if (controllerType == DroneController.class) {
                return new DroneController(crear, buscar, listar, editar, eliminar);
            }
            throw new IllegalArgumentException("Controlador no configurado: " + controllerType.getName());
        });

        AnchorPane root = loader.load();

        Scene scene = new Scene(root);

        stage.setTitle("Sistema de Drones");
        stage.setScene(scene);
        stage.show();

        comprobarConexion(stage);
    }

    private void comprobarConexion(Stage stage) {
        try (Connection ignored = ConexionBD.getInstance().conectar()) {
        } catch (SQLException exception) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.initOwner(stage);
            alerta.setTitle("Base de datos no disponible");
            alerta.setHeaderText("No se pudo conectar a la base de datos");
            alerta.setContentText(exception.getMessage()
                    + "\n\nVerifique DB_URL, DB_USER y DB_PASSWORD en el archivo .env "
                    + "del proyecto, y confirme que MySQL esté iniciado.");
            alerta.show();
        }
    }

    /**
     * Punto de entrada principal de la aplicación.
     *
     * <p>Este método inicia el ciclo de vida de JavaFX mediante
     * el método {@link #launch(String...)}.</p>
     *
     * @param args argumentos proporcionados al ejecutar la aplicación.
     */
    public static void main(String[] args) {
        launch(args);
    }
}