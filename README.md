# Sistema de Gestión de Drones

Aplicación de escritorio para administrar una flota de drones. Permite registrar y consultar drones, asociarles pilotos y sensores, estimar el consumo de batería de una ruta y consultar las condiciones meteorológicas para evaluar si son aptas para operar.

## Funcionalidades

- **Gestión de drones:** crear, buscar, listar, editar y eliminar drones.
- **Asociaciones:** registrar un piloto y uno o varios sensores para cada dron.
- **Consulta meteorológica:** buscar ciudades y consultar temperatura, humedad, viento, presión y visibilidad mediante [Open-Meteo](https://open-meteo.com/).
- **Evaluación de vuelo:** validar las condiciones meteorológicas mediante una cadena de reglas.
- **Estimación de consumo:** calcular un consumo estimado según los perfiles Ahorro, Normal y Alto rendimiento.
- **Programación de tareas:** programar y ejecutar operaciones de fotografía, escaneo de zona y medición de temperatura.

> **Nota:** las tareas de fotografía, escaneo y medición se simulan; la aplicación no se conecta a hardware real. La consulta meteorológica requiere conexión a Internet. Open-Meteo se consulta directamente y no requiere una clave API en esta implementación.

## Tecnologías y versiones

| Tecnología | Versión o uso |
|---|---|
| Java | 17 |
| Maven | Gestión y ejecución del proyecto |
| JavaFX | 17.0.10 |
| MySQL Connector/J | 8.4.0 |
| MySQL / MariaDB | Base de datos relacional |
| Gson | 2.11.0 |
| JUnit Jupiter | 5.10.2, pruebas |

El archivo SQL incluido indica que fue exportado desde MariaDB 10.4.32. La aplicación usa MySQL Connector/J 8.4.0 para conectarse a la base de datos.

## Requisitos

Antes de ejecutar el proyecto, instala y configura:

1. **JDK 17**.
2. **Apache Maven**.
3. **MySQL o MariaDB** iniciado localmente.
4. **Internet**, para las funciones meteorológicas.
5. Una terminal o un IDE compatible con Maven, como IntelliJ IDEA, Eclipse o Visual Studio Code.

Verifica las instalaciones con:

```bash
java -version
mvn -version
```

## Instalación de la base de datos

El repositorio incluye el archivo `sistema_drones (1).sql`, con la estructura y algunos registros de ejemplo. El archivo SQL no crea la base de datos; créala antes de importarlo.

### Opción 1: terminal

Desde la carpeta raíz del proyecto, crea la base de datos:

```bash
mysql -u root -p -e "CREATE DATABASE sistema_drones CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;"
```

Importa el archivo SQL:

```bash
mysql -u root -p sistema_drones < "sistema_drones (1).sql"
```

Cuando se solicite, ingresa la contraseña del usuario de MySQL.

### Opción 2: phpMyAdmin

1. Inicia MySQL desde tu entorno local.
2. Abre phpMyAdmin y crea una base de datos llamada `sistema_drones`.
3. Selecciona la base de datos recién creada.
4. Ve a **Importar**, selecciona `sistema_drones (1).sql` y ejecuta la importación.

El esquema contiene las tablas `dron`, `piloto` y `sensores`, con relaciones entre drones, pilotos y sensores. El SQL incluido también carga registros de ejemplo; puedes agregar o modificar los datos según tus necesidades.

## Configuración de conexión

La aplicación obtiene las credenciales desde variables de entorno o desde un archivo `.env` situado en la carpeta raíz del proyecto.

Crea o edita `.env` en la raíz y configura los datos de tu instalación:

```dotenv
DB_URL=jdbc:mysql://localhost:3306/sistema_drones
DB_USER=root
DB_PASSWORD=tu_contraseña
```

Si el usuario de MySQL no tiene contraseña, deja el valor vacío:

```dotenv
DB_PASSWORD=
```

Asegúrate de que `DB_URL` apunte al host, puerto y nombre de base de datos correctos. No compartas ni publiques credenciales reales.

## Ejecutar la aplicación

Desde la carpeta que contiene `pom.xml`, ejecuta:

```bash
mvn clean javafx:run
```

El punto de entrada configurado en Maven es `co.edu.poli.sw2.Vista.app`. Al iniciar, la aplicación abre su interfaz de escritorio y verifica la conexión a la base de datos.

Para compilar el proyecto:

```bash
mvn clean package
```

Para ejecutar las pruebas:

```bash
mvn test
```

## Estructura del proyecto

```text
src/
├── main/
│   ├── java/co/edu/poli/sw2/
│   │   ├── Aplicacion/
│   │   │   ├── Puerto/       # Casos de uso y contratos de entrada/salida
│   │   │   └── Servicios/    # Servicios de aplicación
│   │   ├── Dominio/          # Modelos, reglas y patrones de dominio
│   │   ├── Infraestuctura/   # Persistencia MySQL, API y controlador JavaFX
│   │   └── Vista/            # Punto de entrada de la aplicación
│   └── resources/
│       └── co/edu/poli/sw2/  # Interfaz JavaFX (FXML) e imágenes
└── test/
    └── java/                 # Pruebas automatizadas
```

## Arquitectura y patrones

El proyecto organiza el código con una **arquitectura hexagonal**:

- **Dominio:** modelos y reglas centrales, como `Dron`, `Piloto`, `Sensores` y las validaciones meteorológicas.
- **Aplicación:** puertos y casos de uso para las operaciones disponibles.
- **Infraestructura:** adaptadores para MySQL, consultas a Open-Meteo y conexión con la interfaz gráfica.
- **Vista:** aplicación JavaFX y archivos FXML.

Entre los patrones implementados están:

- **Repository:** `RepositoryDron` define el puerto de persistencia y `MySqlDronRepository` lo implementa usando JDBC.
- **Strategy:** perfiles intercambiables de estimación de consumo: ahorro, normal y alto rendimiento.
- **Command:** operaciones de drones que se programan y ejecutan en orden. Las acciones actuales son simuladas.
- **Observer:** notificación a drones suscritos cuando se consulta el clima.
- **Chain of Responsibility:** evaluación secuencial de temperatura, humedad, viento, presión y visibilidad.
- **Mediator:** `MissionCoordinator` coordina batería, ruta, sensores y comunicación para iniciar misiones de vigilancia. La vista permite seleccionar un dron existente, una ruta como `sur-norte` y sensores del catálogo; el dron se consulta, pero no se modifica ni se persiste.

## Creadores

- **Jeisson Romero**
- **Camilo Vera**

## Pruebas

El proyecto utiliza JUnit Jupiter. Para ejecutar la suite disponible:

```bash
mvn test
```
