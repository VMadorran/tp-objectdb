package ar.unrn.tp.modelo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public abstract class Promocion {
	@Id
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

}
