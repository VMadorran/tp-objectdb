package ar.unrn.tp.modelo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Email {

	@Column(name = "email")
	private String direccion;

	public Email(String direccion) throws Exception {
		if (!this.checkEmail(direccion))
			throw new Exception("El campo email debe ser valido");
		this.direccion = direccion;
	}

	private Email() {

	}

	private boolean checkEmail(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

	private String getDireccion() {
		return direccion;
	}

	private void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	protected String direccionDeEmail() {
		return this.direccion;
	}

}
