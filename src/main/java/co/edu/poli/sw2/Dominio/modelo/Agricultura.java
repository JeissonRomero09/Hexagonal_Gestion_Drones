package co.edu.poli.sw2.model;

/**
 * Representa un dron especializado en actividades de agricultura.
 *
 * <p>
 * Esta clase hereda los atributos generales de {@link Dron} y agrega
 * las características específicas de un dron agrícola.
 * </p>
 *
 * @author Jeisson Romero
 * @version 4.0
 */
public class Agricultura extends Dron {

    /**
     * Capacidad del tanque del dron para almacenar sustancias
     * utilizadas en actividades agrícolas.
     */
    private double capacidadTanque;

    /**
     * Constructor por defecto.
     */
    public Agricultura() {
        super();
    }

    /**
     * Constructor de Agricultura.
     *
     * @param id identificador único del dron
     * @param serial número serial del dron
     * @param modelo modelo del dron
     * @param fabricante fabricante del dron
     * @param peso peso del dron
     * @param capacidadTanque capacidad del tanque del dron
     */
    public Agricultura(int id, String serial, String modelo,
                       String fabricante, int peso,
                       double capacidadTanque) {

        super(id, serial, modelo, fabricante, peso);
        this.capacidadTanque = capacidadTanque;
    }

    /**
     * Constructor de copia.
     *
     * @param prototype instancia de Agricultura que se utilizará
     *                  como prototipo
     */
    public Agricultura(Agricultura prototype) {
        super(prototype);

        if (prototype != null) {
            this.capacidadTanque = prototype.capacidadTanque;
        }
    }

    /**
     * Crea una copia de la instancia actual.
     *
     * @return una nueva instancia de Agricultura
     */
    @Override
    public Agricultura clone() {
        return new Agricultura(this);
    }

    /**
     * Obtiene la capacidad del tanque del dron.
     *
     * @return capacidad del tanque
     */
    public double getCapacidadTanque() {
        return capacidadTanque;
    }

    /**
     * Modifica la capacidad del tanque del dron.
     *
     * @param capacidadTanque nueva capacidad del tanque
     */
    public void setCapacidadTanque(double capacidadTanque) {
        this.capacidadTanque = capacidadTanque;
    }

    /**
     * Devuelve una representación textual del objeto Agricultura.
     *
     * @return cadena con los datos del dron agrícola
     */
    @Override
    public String toString() {
        return "Agricultura{" +
                "id=" + getId() +
                ", serial='" + getSerial() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", fabricante='" + getFabricante() + '\'' +
                ", peso=" + getPeso() +
                ", capacidadTanque=" + capacidadTanque +
                '}';
    }
}
Quiero que adaptes mi proyecto JavaFX existente a Arquitectura Hexagonal (Ports and Adapters).

IMPORTANTE:
- Ya hice la estructura inicial de paquetes y clases. NO crees un proyecto nuevo ni reconstruyas todo.
- Revisa primero mi estructura actual y modifica solo lo necesario.
- Respeta los nombres de clases y paquetes que ya existen.
- Usa como referencia EXACTA el diagrama de arquitectura que te proporcioné.
- Quiero más clases pequeñas y responsabilidades separadas, no concentrar toda la lógica en pocas clases.
- Mantén los modelos, FXML, fx:id, patrones y funcionalidades que ya funcionan.

La arquitectura debe quedar conceptualmente así:

Infraestructura / Adaptadores
├── Adaptador IN
│   └── UI
│       └── Controllers JavaFX
└── Persistencia
    └── MySqlDronRepository
        └── ConexionBD

Aplicación
├── Port In
│   ├── CrearDronUseCase
│   ├── BuscarDronUseCase
│   ├── EliminarDronUseCase
│   ├── BuscarListaDronesUseCase
│   └── EditarDronUseCase
│
├── Servicios
│   ├── CrearDronServicio
│   ├── BuscarDronServicio
│   ├── EliminarDronServicio
│   ├── BuscarListaDronesServicio
│   └── EditarDronServicio
│
└── Port Out
    └── RepositoryDron

Dominio
├── Dron
├── Agricultura
├── Vigilancia
├── Piloto
├── Sensores
└── Mision

RESPETA EXACTAMENTE ESTOS NOMBRES cuando correspondan:
- CrearDronUseCase
- BuscarDronUseCase
- EliminarDronUseCase
- BuscarListaDronesUseCase
- EditarDronUseCase
- CrearDronServicio
- BuscarDronServicio
- EliminarDronServicio
- BuscarListaDronesServicio
- EditarDronServicio
- RepositoryDron
- MySqlDronRepository
- ConexionBD

Flujo obligatorio:

Controller/UI
    ↓
Port In / UseCase
    ↓
Servicio
    ↓
RepositoryDron
    ↓
MySqlDronRepository
    ↓
ConexionBD / MariaDB

REGLAS:
1. Los UseCase/Servicios NO deben depender directamente de MySqlDronRepository.
2. RepositoryDron debe ser una interfaz.
3. MySqlDronRepository debe implementar RepositoryDron.
4. SQL, JDBC, Connection, PreparedStatement y ResultSet solo deben estar en persistencia.
5. El Controller no debe contener lógica de negocio ni SQL.
6. No crear modelos duplicados.
7. Conserva Dron, Agricultura, Vigilancia, Piloto, Sensores y Mision existentes.
8. Conserva DronFactory y los demás patrones que ya tenga el proyecto.
9. Conserva ConexionBD/Singleton si ya existe.
10. No cambies FXML, fx:id ni eventos si no es estrictamente necesario.
11. No agregues funcionalidades nuevas.
12. La prioridad es que el CRUD de Dron funcione:
   - Crear
   - Buscar
   - Listar
   - Editar
   - Eliminar

Antes de escribir código:
1. Revisa mi estructura actual.
2. Identifica qué clases ya existen.
3. Identifica cuáles deben modificarse.
4. Identifica cuáles deben crearse.
5. No elimines ni reemplaces clases que ya cumplen su función.
6. Después realiza los cambios progresivamente.

El resultado debe coincidir con el diagrama de arquitectura que te proporcioné, pero adaptado a las clases y estructura que YA tiene mi proyecto.