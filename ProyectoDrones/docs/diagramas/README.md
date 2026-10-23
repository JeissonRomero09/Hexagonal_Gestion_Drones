# Diagramas de integración meteorológica

Los diagramas documentan el flujo hexagonal y las responsabilidades de los
patrones Observer y Chain of Responsibility:

- `arquitectura-clima.puml`: puertos, adaptadores, llamadas a Open-Meteo y
  difusión de actualizaciones.
- `clases-observer-chain.puml`: clases y relaciones implementadas en Java.

## Generar imágenes

Con PlantUML instalado, desde esta carpeta:

```powershell
plantuml -tpng arquitectura-clima.puml clases-observer-chain.puml
```

El flujo consulta primero la API de geocodificación para obtener coordenadas y
después la API de pronóstico actual. La respuesta se convierte a `Weather` y se
publica a los drones suscritos. Las medidas se representan en °C, porcentaje,
km/h, hPa y km.

## Correspondencia con el código

El diagrama de clases conserva los nombres y las responsabilidades reales del
proyecto (`Weather`, `WeatherObserver`, `ClimaValidacion`,
`TemperaturaValidacion`, `HumedadValidacion`, `VientoValidacion`,
`PresionValidacion`, `VisibilidadValidacion`, `ClimaServicio`,
`WeatherNotificationService`, `DashboardObserver` y `GeoAPI`).

`GeoAPI` implementa en una sola clase los dos endpoints de Open-Meteo. Por eso
el diagrama no inventa `GeoApiClient`, `OpenWeatherClient` ni adaptadores
separados. Actualmente `DashboardObserver` es el único observador concreto;
`LoggerObserver`, `ConsoleObserver` y `FlightStatusObserver` no están
implementados y no se muestran como clases existentes.