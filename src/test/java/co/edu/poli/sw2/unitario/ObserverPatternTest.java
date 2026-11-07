package co.edu.poli.sw2.unitario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import co.edu.poli.sw2.Aplicacion.Servicios.observer.DashboardObserver;
import co.edu.poli.sw2.Dominio.modelo.Dron;
import co.edu.poli.sw2.Dominio.modelo.Wather.Weather;
import co.edu.poli.sw2.Dominio.modelo.Wather.WeatherObserver;

class ObserverPatternTest {
	@Test
	void notificaObservadoresSuscritosYEvitaDuplicados() {
		Dron dron = new Dron(1, "S-1", "M1", "Fabricante", 2);
		Weather weather = new Weather(20, 50, 15, 1013, 10);
		List<Weather> recibidos = new ArrayList<>();
		WeatherObserver observer = recibidos::add;

		dron.subscribe(observer);
		dron.subscribe(observer);
		dron.notifyObservers(weather);

		assertEquals(List.of(weather), recibidos);
	}

	@Test
	void dejaDeNotificarAlCancelarLaSuscripcion() {
		Dron dron = new Dron(1, "S-1", "M1", "Fabricante", 2);
		List<Weather> recibidos = new ArrayList<>();
		WeatherObserver observer = recibidos::add;

		dron.subscribe(observer);
		dron.unsubscribe(observer);
		dron.notifyObservers(new Weather(20, 50, 15, 1013, 10));

		assertEquals(0, recibidos.size());
	}

	@Test
	void dashboardObserverEntregaLaActualizacionAlDashboard() {
		AtomicReference<Weather> actualizacion = new AtomicReference<>();
		Weather weather = new Weather(20, 50, 15, 1013, 10);
		DashboardObserver observer = new DashboardObserver(actualizacion::set);

		observer.update(weather);

		assertSame(weather, actualizacion.get());
	}

	@Test
	void rechazaObservadoresYActualizacionesNulas() {
		Dron dron = new Dron(1, "S-1", "M1", "Fabricante", 2);

		assertThrows(NullPointerException.class, () -> dron.subscribe(null));
		assertThrows(NullPointerException.class, () -> dron.notifyObservers(null));
		assertThrows(NullPointerException.class, () -> new DashboardObserver(null));
	}
}