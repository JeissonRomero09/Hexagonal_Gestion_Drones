package co.edu.poli.sw2.Aplicacion.Servicios.observer;

import java.util.Objects;
import java.util.function.Consumer;

import co.edu.poli.sw2.Dominio.modelo.Wather.Weather;
import co.edu.poli.sw2.Dominio.modelo.Wather.WeatherObserver;

/** Adapta las actualizaciones del clima a una acción de refresco del dashboard. */
public final class DashboardObserver implements WeatherObserver {
	private final Consumer<Weather> refrescarDashboard;

	/**
	 * @param refrescarDashboard acción que actualiza la vista con los cinco datos meteorológicos
	 */
	public DashboardObserver(Consumer<Weather> refrescarDashboard) {
		this.refrescarDashboard = Objects.requireNonNull(refrescarDashboard, "refrescarDashboard");
	}

	@Override
	public void update(Weather weather) {
		refrescarDashboard.accept(Objects.requireNonNull(weather, "weather"));
	}
}
