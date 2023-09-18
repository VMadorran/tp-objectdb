package ar.unrn.tp.modelo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public abstract class Promocion {
	@Id
	@GeneratedValue
	protected Long id;
	protected float descuento;
	private String nombre;
	@Column(name = "estado")
	public boolean activo;
	private LocalDateTime inicio;
	private LocalDateTime fin;
	protected String tipo;

	public Promocion(LocalDateTime inicio, LocalDateTime fin, String nombre, float descuento) throws Exception {

		if (this.fechasNoValidas(inicio, fin))
			throw new Exception("Las fechas son incorrectas");
		this.inicio = inicio;
		this.fin = fin;
		this.nombre = nombre;
		this.activo = true;
		this.descuento = descuento;
	}

	public Promocion(LocalDateTime inicio, LocalDateTime fin, String nombre, float descuento, Long id)
			throws Exception {

		if (this.fechasNoValidas(inicio, fin))
			throw new Exception("Las fechas son incorrectas");
		this.inicio = inicio;
		this.fin = fin;
		this.nombre = nombre;
		this.activo = true;
		this.descuento = descuento;
		this.id = id;
	}

	public Promocion() {

	}

	public abstract double aplicarDescuento(double monto);

	public boolean seAplicaDescuento(String marca) {
		return nombre.equals(marca);
	}

	public boolean estaVigente() {
		return this.fin.isAfter(new ProveedorDeFecha().now());
	}

	public void desactivar() {

		this.activo = false;
	}

	public abstract boolean esBancaria();

	protected boolean fechasNoValidas(LocalDateTime inicio, LocalDateTime fin) {

		if ((inicio.equals(fin)) || inicio.isAfter(fin))
			return true;

		return false;
	}

	public String marca() {
		return this.nombre;
	}

	public float descuento() {
		return this.descuento;
	}

	private Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	private float getDescuento() {
		return descuento;
	}

	private void setDescuento(float descuento) {
		this.descuento = descuento;
	}

	private String getNombre() {
		return nombre;
	}

	private void setNombre(String nombre) {
		this.nombre = nombre;
	}

	private boolean isActivo() {
		return activo;
	}

	private void setActivo(boolean activo) {
		this.activo = activo;
	}

	private LocalDateTime getInicio() {
		return inicio;
	}

	private void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	private LocalDateTime getFin() {
		return fin;
	}

	private void setFin(LocalDateTime fin) {
		this.fin = fin;
	}

	private String getTipo() {
		return tipo;
	}

	private void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
