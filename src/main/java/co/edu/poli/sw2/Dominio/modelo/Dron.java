package co.edu.poli.sw2.Dominio.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa un dron dentro del sistema de gestión.
 *
 * <p>
 * Esta clase contiene la información básica de un dron, incluyendo su
 * identificador, serial, modelo, fabricante, peso, piloto y sensores asociados.
 * </p>
 *
 * @author Jeison Romero
 * @version 1.0
 */
public class Dron {

	/**
	 * Identificador único del dron.
	 */
	private int id;

	/**
	 * Número de serie del dron.
	 */
	private String serial;

	/**
	 * Modelo del dron.
	 */
	private String modelo;

	/**
	 * Fabricante del dron.
	 */
	private String fabricante;

	/**
	 * Peso del dron.
	 */
	private int peso;


	/**
	 * Pilotos asociados al dron.
	 */
	private Piloto piloto;

	/**
	 * Sensores asociados mediante la columna {@code sensores.dron_id}.
	 */
	private List<Sensores> sensores = new ArrayList<>();

	/**
	 * Constructor vacío de la clase Dron.
	 */
	public Dron() {
	}

	/**
	 * Crea un dron con sus datos básicos.
	 *
	 * @param id identificador del dron
	 * @param serial número de serie
	 * @param modelo modelo del dron
	 * @param fabricante fabricante del dron
	 * @param peso peso del dron
	 */
	public Dron(int id, String serial, String modelo, String fabricante, int peso) {
		this.id = id;
		this.serial = serial;
		this.modelo = modelo;
		this.fabricante = fabricante;
		this.peso = peso;
	}

	/**
	 * Crea una copia de los datos de otro dron.
	 *
	 * @param prototype dron cuyos datos se copiarán; si es {@code null}, la instancia queda vacía
	 */
	public Dron(Dron prototype) {
		if (prototype != null) {
			this.id = prototype.id;
			this.serial = prototype.serial;
			this.modelo = prototype.modelo;
			this.fabricante = prototype.fabricante;
			this.peso = prototype.peso;
			this.piloto = prototype.piloto;
			this.sensores = new ArrayList<>(prototype.sensores);
		}
	}

	/**
	 * Constructor que permite crear un dron con todos sus atributos.
	 *
	 * @param id identificador único del dron.
	 * @param serial número de serie del dron.
	 * @param modelo modelo del dron.
	 * @param fabricante fabricante del dron.
	 * @param peso peso del dron.
	 * @param piloto piloto asociado al dron.
	 * @param sensores sensores asociados al dron.
	 */
	public Dron(int id, String serial, String modelo, String fabricante,
			int peso, Piloto piloto, List<Sensores> sensores) {

		this.id = id;
		this.serial = serial;
		this.modelo = modelo;
		this.fabricante = fabricante;
		this.peso = peso;
		this.piloto = piloto;
		setSensores(sensores);
	}

	/** @return identificador único del dron */
	public int getId() {
		return id;
	}

	/** @param id nuevo identificador del dron */
	public void setId(int id) {
		this.id = id;
	}

	/** @return número de serie del dron */
	public String getSerial() {
		return serial;
	}

	/** @param serial nuevo número de serie */
	public void setSerial(String serial) {
		this.serial = serial;
	}

	/** @return modelo del dron */
	public String getModelo() {
		return modelo;
	}

	/** @param modelo nuevo modelo del dron */
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	/** @return fabricante del dron */
	public String getFabricante() {
		return fabricante;
	}

	/** @param fabricante nuevo fabricante del dron */
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	/** @return peso del dron */
	public int getPeso() {
		return peso;
	}

	/** @param peso nuevo peso del dron */
	public void setPeso(int peso) {
		this.peso = peso;
	}

	/** @return piloto asociado o {@code null} si no tiene asociación */
	public Piloto getPiloto() {
		return piloto;
	}

	/** @param piloto piloto que se asociará al dron */
	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}
	/** @return sensores asociados (lista vacía si no hay asociaciones) */
	public List<Sensores> getSensores() {
		return sensores;
	}

	/** @param sensores sensores que se asociarán al dron */
	public void setSensores(List<Sensores> sensores) {
		this.sensores = new ArrayList<>(Objects.requireNonNull(sensores, "sensores"));
	}

	/**
	 * Devuelve una representación textual del objeto Dron.
	 *
	 * @return cadena de texto con los datos del dron.
	 */
	@Override
	public String toString() {
		return "Dron{" +
				"id=" + id +
				", serial='" + serial + '\'' +
				", modelo='" + modelo + '\'' +
				", fabricante='" + fabricante + '\'' +
				", peso=" + peso +
				", piloto=" + piloto +
				", sensores=" + sensores +
				'}';
	}
}