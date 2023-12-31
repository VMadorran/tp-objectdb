package ar.unrn.tp.modelo;

import javax.persistence.Embeddable;

@Embeddable
public class Dni {

	private Long dni;

	public Dni(Long dni) throws Exception {
		if (this.datoNulo(dni))
			throw new Exception("dni no puede ser vacio");
		this.dni = dni;
	}

	private Dni() {

	}

	public Long dni() {
		return this.dni;
	}

	private boolean datoNulo(Long dato) {
		return dato.equals(0);
	}

	private Long getDni() {
		return dni;
	}

	private void setDni(Long dni) {
		this.dni = dni;
	}

}