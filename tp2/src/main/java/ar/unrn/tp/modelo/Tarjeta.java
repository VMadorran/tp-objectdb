package ar.unrn.tp.modelo;

import javax.persistence.Embeddable;

@Embeddable
public class Tarjeta {

	private Long nroTarjeta;
	private String marca;
	private boolean activa;

	public Tarjeta(Long nroTarjeta, String marca) {

		this.nroTarjeta = nroTarjeta;
		this.activa = true;
		this.marca = marca;
	}

	private Tarjeta() {

	}

	public Long nroTarjeta() {

		return this.nroTarjeta;
	}

	public String marcaTarjeta() {

		return this.marca;
	}

	public boolean estaActiva() {
		return this.activa;
	}

	public boolean saldoSuficiente(double monto) {
		return true;
	}

	private Long getNroTarjeta() {
		return nroTarjeta;
	}

	private void setNroTarjeta(Long nroTarjeta) {
		this.nroTarjeta = nroTarjeta;
	}

	private String getMarca() {
		return marca;
	}

	private void setMarca(String marca) {
		this.marca = marca;
	}

	private boolean isActiva() {
		return activa;
	}

	private void setActiva(boolean activa) {
		this.activa = activa;
	}

	@Override
	public String toString() {
		return "Tarjeta [nroTarjeta=" + nroTarjeta + ", marca=" + marca + ", activa=" + activa + "]";
	}

}
