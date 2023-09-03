package ar.unrn.tp.modelo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Dni {
	@Column(name = "dni")
	private Long dni;

	public Dni(Long dni) throws Exception {
		if (this.datoNulo(dni))
			throw new Exception("dni no puede ser vacio");
		this.dni = dni;
	}

	public Long dni() {
		return this.dni;
	}

	private boolean datoNulo(Long dato) {
		return dato.equals(0);
	}

}