package co.edu.poli.sw2.Infraestuctura.UI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.edu.poli.sw2.Aplicacion.Puerto.Entrada.BuscarDronUseCase;
import co.edu.poli.sw2.Aplicacion.Puerto.Entrada.BuscarListaDronesUseCase;
import co.edu.poli.sw2.Aplicacion.Puerto.Entrada.CrearDronUseCase;
import co.edu.poli.sw2.Aplicacion.Puerto.Entrada.EditarDronUseCase;
import co.edu.poli.sw2.Aplicacion.Puerto.Entrada.EliminarDronUseCase;
import co.edu.poli.sw2.Dominio.modelo.Dron;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Controlador encargado de gestionar la interfaz gráfica de los drones.
 *
 * <p>
 * Esta clase permite realizar las operaciones CRUD sobre los drones: crear,
 * buscar, actualizar y eliminar. También se encarga de validar los datos
 * ingresados por el usuario y comunica resultados en el indicador de estado.
 * </p>
 *
 * @author Camilo Vera
 * @version 1.0
 */
public class DroneController {

	/**
	 * Botón utilizado para crear un nuevo dron.
	 */
	@FXML
	private Button btnCrear;

	/**
	 * Botón utilizado para buscar un dron.
	 */
	@FXML
	private Button btnBuscar;

	/**
	 * Botón utilizado para eliminar un dron.
	 */
	@FXML
	private Button btnEliminar;

	/**
	 * Botón utilizado para actualizar la información de un dron.
	 */
	@FXML
	private Button btnActualizar;

	/** Botón que carga todos los drones en la tabla de inventario. */
	@FXML
	private Button btnListar;

	/** Indicador visible de validaciones y resultados de las operaciones. */
	@FXML
	private Label lblEstado;

	/** Resumen de la cantidad de drones mostrados en la tabla. */
	@FXML
	private Label lblConteo;

	/** Tabla que muestra los resultados de búsqueda o el inventario completo. */
	@FXML
	private TableView<Dron> tablaDrones;

	/** Columna con el identificador del dron. */
	@FXML
	private TableColumn<Dron, Number> colId;

	/** Columna con el serial del dron. */
	@FXML
	private TableColumn<Dron, String> colSerial;

	/** Columna con el modelo del dron. */
	@FXML
	private TableColumn<Dron, String> colModelo;

	/** Columna con el fabricante del dron. */
	@FXML
	private TableColumn<Dron, String> colFabricante;

	/** Columna con el peso del dron. */
	@FXML
	private TableColumn<Dron, Number> colPeso;

	/** Columna con el ID del piloto asociado. */
	@FXML
	private TableColumn<Dron, String> colPiloto;

	/** Columna con el ID del sensor asociado. */
	@FXML
	private TableColumn<Dron, String> colSensor;

	/**
	 * Campo de texto utilizado para ingresar el identificador del dron.
	 */
	@FXML
	private TextField txtId;

	/**
	 * Campo de texto utilizado para ingresar el número de serial del dron.
	 */
	@FXML
	private TextField txtSerial;

	/**
	 * Campo de texto utilizado para ingresar el modelo del dron.
	 */
	@FXML
	private TextField txtModelo;

	/**
	 * Campo de texto utilizado para ingresar el fabricante del dron.
	 */
	@FXML
	private TextField txtFabricante;

	/**
	 * Campo de texto utilizado para ingresar el peso del dron.
	 */
	@FXML
	private TextField txtPeso;

	/**
	 * Campo de texto utilizado para ingresar el identificador del piloto.
	 */
	@FXML
	private TextField txtPiloto;

	/**
	 * Campo de texto utilizado para ingresar el identificador del sensor.
	 */
	@FXML
	private TextField txtSensor;

	/** Caso de uso para registrar drones. */
	private final CrearDronUseCase crearDronUseCase;
	/** Caso de uso para buscar un dron por ID. */
	private final BuscarDronUseCase buscarDronUseCase;
	/** Caso de uso para recuperar el inventario completo. */
	private final BuscarListaDronesUseCase buscarListaDronesUseCase;
	/** Caso de uso para actualizar un dron. */
	private final EditarDronUseCase editarDronUseCase;
	/** Caso de uso para eliminar un dron. */
	private final EliminarDronUseCase eliminarDronUseCase;

	/**
	 * Construye el adaptador de interfaz con los casos de uso requeridos.
	 *
	 * @param crearDronUseCase caso de uso de creación
	 * @param buscarDronUseCase caso de uso de búsqueda individual
	 * @param buscarListaDronesUseCase caso de uso de listado
	 * @param editarDronUseCase caso de uso de edición
	 * @param eliminarDronUseCase caso de uso de eliminación
	 */
	public DroneController(CrearDronUseCase crearDronUseCase, BuscarDronUseCase buscarDronUseCase,
			BuscarListaDronesUseCase buscarListaDronesUseCase, EditarDronUseCase editarDronUseCase,
			EliminarDronUseCase eliminarDronUseCase) {
		this.crearDronUseCase = crearDronUseCase;
		this.buscarDronUseCase = buscarDronUseCase;
		this.buscarListaDronesUseCase = buscarListaDronesUseCase;
		this.editarDronUseCase = editarDronUseCase;
		this.eliminarDronUseCase = eliminarDronUseCase;
	}

	/**
	 * Inicializa los componentes y eventos de la interfaz.
	 *
	 * <p>
	 * Este método se ejecuta automáticamente cuando se carga el archivo FXML. Se
	 * configuran los efectos visuales de los botones y las acciones
	 * correspondientes a cada operación CRUD.
	 * </p>
	 */
	@FXML
	public void initialize() {

		configurarTabla();
		efectoBoton(btnCrear);
		efectoBoton(btnBuscar);
		efectoBoton(btnEliminar);
		efectoBoton(btnActualizar);
		efectoBoton(btnListar);

		// Acciones

		btnCrear.setOnAction(e -> crear());
		btnBuscar.setOnAction(e -> buscar());
		btnEliminar.setOnAction(e -> eliminar());
		btnActualizar.setOnAction(e -> actualizar());
		btnListar.setOnAction(e -> listar());
		tablaDrones.getSelectionModel().selectedItemProperty().addListener((observable, anterior, seleccionado) -> {
			if (seleccionado != null) {
				cargarFormulario(seleccionado);
			}
		});
	}

	/** Asocia cada columna de la tabla con su propiedad del modelo de dominio. */
	private void configurarTabla() {
		colId.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<Number>(celda.getValue().getId()));
		colSerial.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<String>(celda.getValue().getSerial()));
		colModelo.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<String>(celda.getValue().getModelo()));
		colFabricante.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<String>(celda.getValue().getFabricante()));
		colPeso.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<Number>(celda.getValue().getPeso()));
		colPiloto.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<String>(
				celda.getValue().getPiloto() == null ? "-" : String.valueOf(celda.getValue().getPiloto().getId())));
		colSensor.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<String>(
				celda.getValue().getSensores() == null ? "-" : String.valueOf(celda.getValue().getSensores().getId())));
	}

	/** Carga el inventario completo y presenta el resultado en la tabla. */
	private void listar() {
		try {
			refrescarTabla();
			mostrarAlerta(Alert.AlertType.INFORMATION, "Inventario actualizado",
					datosContados() + " drones cargados.");
		} catch (RuntimeException e) {
			mostrarAlerta(Alert.AlertType.ERROR, "Error de Base de Datos",
					"No se pudo listar los drones: " + e.getMessage());
		}
	}

	/** Reemplaza las filas de la tabla por el inventario más reciente. */
	private void refrescarTabla() {
		List<Dron> drones = buscarListaDronesUseCase.buscarTodos();
		tablaDrones.setItems(FXCollections.observableArrayList(drones));
		lblConteo.setText(drones.size() + (drones.size() == 1 ? " dron" : " drones"));
	}

	/** @return cantidad de filas que muestra actualmente la tabla */
	private String datosContados() {
		return String.valueOf(tablaDrones.getItems().size());
	}

	/** Copia al formulario los datos del dron seleccionado o encontrado.
	 *
	 * @param dron dron cuyos datos se mostrarán en los campos
	 */
	private void cargarFormulario(Dron dron) {
		txtId.setText(String.valueOf(dron.getId()));
		txtSerial.setText(dron.getSerial());
		txtModelo.setText(dron.getModelo());
		txtFabricante.setText(dron.getFabricante());
		txtPeso.setText(String.valueOf(dron.getPeso()));
		txtPiloto.setText(dron.getPiloto() == null ? "" : String.valueOf(dron.getPiloto().getId()));
		txtSensor.setText(dron.getSensores() == null ? "" : String.valueOf(dron.getSensores().getId()));
		lblEstado.setText("Dron " + dron.getId() + " cargado en la ficha.");
	}

	/**
	 * Configura los efectos visuales de interacción de un botón.
	 *
	 * @param boton botón al que se le aplicarán los efectos visuales.
	 */
	private void efectoBoton(Button boton) {

		boton.setOnMouseEntered(e -> {
			boton.setScaleX(1.10);
			boton.setScaleY(1.10);
		});

		boton.setOnMouseExited(e -> {
			boton.setScaleX(1.0);
			boton.setScaleY(1.0);
		});

		boton.setOnMousePressed(e -> {
			boton.setScaleX(0.85);
			boton.setScaleY(0.85);
		});

		boton.setOnMouseReleased(e -> {
			boton.setScaleX(1.10);
			boton.setScaleY(1.10);
		});
	}

	/**
	 * Crea un nuevo dron mediante el caso de uso de entrada.
	 *
	 * <p>
	 * Valida que todos los campos requeridos estén diligenciados, obtiene los
	 * identificadores del piloto y del sensor y registra el dron junto con sus
	 * asociaciones correspondientes.
	 * </p>
	 *
	 * <p>
	 * El identificador del dron es generado automáticamente por la base de datos y
	 * posteriormente se muestra en el campo ID.
	 * </p>
	 *
	 */
	private void crear() {

		try {

			// Validar campos obligatorios
			if (txtSerial.getText().isEmpty() || txtModelo.getText().isEmpty() || txtFabricante.getText().isEmpty()
					|| txtPeso.getText().isEmpty() || txtPiloto.getText().isEmpty() || txtSensor.getText().isEmpty()) {

				mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Por favor, complete todos los campos.");

				return;
			}

			// Obtener los identificadores
			int pilotoId = Integer.parseInt(txtPiloto.getText());
			int sensorId = Integer.parseInt(txtSensor.getText());

			// Crear objeto Dron
			Dron drone = new Dron();

			drone.setSerial(txtSerial.getText());
			drone.setModelo(txtModelo.getText());
			drone.setFabricante(txtFabricante.getText());
			drone.setPeso(Integer.parseInt(txtPeso.getText()));

			List<Integer> sensorIds = new ArrayList<>();
			sensorIds.add(sensorId);

			int idGenerado = crearDronUseCase.crear(
			    drone,
			    pilotoId,
			    sensorIds
			);
			limpiarCampos();
			txtId.setText(String.valueOf(idGenerado));
			refrescarTabla();
			mostrarAlerta(Alert.AlertType.INFORMATION, "Dron guardado",
					"Registro creado con ID " + idGenerado + ".");

		} catch (NumberFormatException e) {

			mostrarAlerta(Alert.AlertType.ERROR, "Datos inválidos",
					"Peso, piloto y sensor deben ser valores numéricos.");

		} catch (RuntimeException e) {

			mostrarAlerta(Alert.AlertType.ERROR, "Error de Base de Datos",
					"No se pudo guardar el dron: " + e.getMessage());
		}
	}

	/**
	 * Busca un dron en la base de datos utilizando su identificador.
	 *
	 * <p>
	 * Si el dron existe, se muestran sus datos en los campos correspondientes,
	 * incluyendo el identificador del piloto y del sensor asociados.
	 * </p>
	 *
	 */
	private void buscar() {

		try {

			if (txtId.getText().trim().isEmpty()) {
				mostrarAlerta(Alert.AlertType.WARNING, "ID requerido", "Ingresa el ID que deseas buscar.");
				return;
			}

			int id = Integer.parseInt(txtId.getText().trim());

			Dron drone = buscarDronUseCase.buscar(id);

			if (drone != null) {
				cargarFormulario(drone);
				tablaDrones.setItems(FXCollections.observableArrayList(drone));
				tablaDrones.getSelectionModel().selectFirst();
				lblConteo.setText("1 dron encontrado");
				mostrarAlerta(Alert.AlertType.INFORMATION, "Dron encontrado", "Coincidencia cargada en la ficha.");
			} else {
				tablaDrones.getItems().clear();
				lblConteo.setText("Sin resultados");
				mostrarAlerta(Alert.AlertType.WARNING, "Dron no encontrado", "No existe un dron con el ID ingresado.");
			}

		} catch (NumberFormatException e) {

			mostrarAlerta(Alert.AlertType.ERROR, "ID inválido", "El ID debe ser un número entero.");

		} catch (RuntimeException e) {

			mostrarAlerta(Alert.AlertType.ERROR, "Error de Base de Datos",
					"No se pudo consultar el dron: " + e.getMessage());
		}
	}

	/**
	 * Elimina un dron mediante su identificador y el caso de uso correspondiente.
	 *
	 * <p>
	 * Primero valida que el usuario haya ingresado un identificador y
	 * posteriormente solicita su eliminación al caso de uso.
	 * </p>
	 *
	 */
	private void eliminar() {

		try {

			if (txtId.getText().isEmpty()) {

				mostrarAlerta(Alert.AlertType.WARNING, "ID requerido", "Ingrese el ID del dron que desea eliminar.");

				return;
			}

			int id = Integer.parseInt(txtId.getText());

			if (eliminarDronUseCase.eliminar(id)) {
				limpiarCampos();
				refrescarTabla();
				mostrarAlerta(Alert.AlertType.INFORMATION, "Dron eliminado", "El registro fue eliminado.");
			} else {
				mostrarAlerta(Alert.AlertType.WARNING, "Dron no encontrado", "No existe un dron con el ID ingresado.");
			}

		} catch (NumberFormatException e) {

			mostrarAlerta(Alert.AlertType.ERROR, "ID inválido", "El ID debe ser un número entero.");

		} catch (RuntimeException e) {

			mostrarAlerta(Alert.AlertType.ERROR, "Error de Base de Datos",
					"No se pudo eliminar el dron: " + e.getMessage());
		}
	}

	/**
	 * Actualiza la información de un dron existente.
	 *
	 * <p>
	 * Valida los datos ingresados, actualiza la información básica del dron y
	 * actualiza las asociaciones correspondientes con el piloto y el sensor.
	 * </p>
	 *
	 */
	private void actualizar() {

		try {

			// Validar ID
			if (txtId.getText().isEmpty()) {

				mostrarAlerta(Alert.AlertType.WARNING, "ID requerido", "Ingrese el ID del dron que desea actualizar.");

				return;
			}

			// Validar campos
			if (txtSerial.getText().isEmpty() || txtModelo.getText().isEmpty() || txtFabricante.getText().isEmpty()
					|| txtPeso.getText().isEmpty() || txtPiloto.getText().isEmpty() || txtSensor.getText().isEmpty()) {

				mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos",
						"Complete todos los campos antes de actualizar.");

				return;
			}

			// Obtener valores numéricos
			int id = Integer.parseInt(txtId.getText());
			int peso = Integer.parseInt(txtPeso.getText());
			int pilotoId = Integer.parseInt(txtPiloto.getText());
			int sensorId = Integer.parseInt(txtSensor.getText());

			// Crear objeto Dron
			Dron drone = new Dron();

			drone.setId(id);
			drone.setSerial(txtSerial.getText());
			drone.setModelo(txtModelo.getText());
			drone.setFabricante(txtFabricante.getText());
			drone.setPeso(peso);

			List<Integer> sensorIds = Arrays.asList(sensorId);

			if (editarDronUseCase.actualizar(drone, pilotoId, sensorIds)) {

			    refrescarTabla();

			    mostrarAlerta(
			        Alert.AlertType.INFORMATION,
			        "Dron actualizado",
			        "Los cambios se guardaron correctamente."
			    );

			} else {

			    mostrarAlerta(
			        Alert.AlertType.WARNING,
			        "Dron no encontrado",
			        "No existe un dron con el ID ingresado."
			    );
			}

		} catch (NumberFormatException e) {

			mostrarAlerta(Alert.AlertType.ERROR, "Datos inválidos",
					"ID, peso, piloto y sensor deben contener valores numéricos.");

		} catch (RuntimeException e) {

			mostrarAlerta(Alert.AlertType.ERROR, "Error de Base de Datos",
					"No se pudo actualizar el dron: " + e.getMessage());
		}
	}

	/**
	 * Limpia todos los campos del formulario.
	 */
	private void limpiarCampos() {

		txtId.clear();
		txtSerial.clear();
		txtModelo.clear();
		txtFabricante.clear();
		txtPeso.clear();
		txtPiloto.clear();
		txtSensor.clear();
	}

	/**
	 * Actualiza el indicador de estado con el resultado de una operación.
	 *
	 * @param tipo    tipo de alerta que se desea mostrar.
	 * @param titulo  título de la ventana de alerta.
	 * @param mensaje mensaje que se mostrará al usuario.
	 */
	private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
		String color = tipo == Alert.AlertType.ERROR ? "#ff9b9f"
				: tipo == Alert.AlertType.WARNING ? "#f4cf83" : "#81e4d0";
		lblEstado.setText(titulo + " · " + mensaje);
		lblEstado.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 13px;");
	}
}
