package co.edu.poli.sw2.Aplicacion.Servicios;

import java.util.Map;
import java.util.Objects;

import co.edu.poli.sw2.Aplicacion.Puerto.Entrada.ClimaUseCase;
import co.edu.poli.sw2.Aplicacion.Puerto.Salida.ProveedorClima;
import co.edu.poli.sw2.Dominio.modelo.Wather.Weather;

/** Implementa los casos de uso meteorológicos y publica cada clima consultado. */
public class ClimaServicio implements ClimaUseCase {
	private final ProveedorClima proveedorClima;
	private final WeatherNotificationService notificador;

	/**
	 * Crea el servicio meteorológico y configura la publicación de cada consulta exitosa.
	 *
	 * @param proveedorClima adaptador que obtiene datos meteorológicos
	 * @param notificador componente que difunde las actualizaciones a los drones suscritos
	 */
	public ClimaServicio(ProveedorClima proveedorClima, WeatherNotificationService notificador) {
		this.proveedorClima = Objects.requireNonNull(proveedorClima, "proveedorClima");
		this.notificador = Objects.requireNonNull(notificador, "notificador");
	}

	/** {@inheritDoc} */
	@Override
	public Map<String, double[]> buscarCiudades(String consulta) {
		return proveedorClima.buscarCiudades(consulta);
	}

	/** {@inheritDoc} */
	@Override
	public Weather consultarClima(double latitud, double longitud) {
		Weather weather = proveedorClima.consultarClima(latitud, longitud);
		notificador.notificar(weather);
		return weather;
	}
}
