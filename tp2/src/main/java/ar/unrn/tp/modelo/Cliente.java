package ar.unrn.tp.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import ar.unrn.tp.exception.DatoVacioException;

@Entity
public class Cliente {
	@Id
	@GeneratedValue
	private Long id;
	private String nombre;
	private String apellido;

	@Embedded
	private Dni dni;

	@Embedded
	private Email email;
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_venta")
	private ArrayList<Tarjeta> mediosDePago = new ArrayList<Tarjeta>();

	public Cliente(String nombre, String apellido, Dni dni, Email email) throws DatoVacioException {

		if (this.datoNulo(nombre))
			throw new DatoVacioException("El campo nombre no puede ser vacio");
		this.nombre = nombre;

		if (this.datoNulo(apellido))
			throw new DatoVacioException("El campo apellido no puede ser vacio");
		this.apellido = apellido;

		this.dni = dni;

		this.email = email;
	}

	public Cliente(String nombre, String apellido, Dni dni, Email email, Long id) throws Exception {

		if (this.datoNulo(nombre))
			throw new Exception("El campo nombre no puede ser vacio");
		this.nombre = nombre;

		if (this.datoNulo(apellido))
			throw new Exception("El campo apellido no puede ser vacio");
		this.apellido = apellido;

		this.dni = dni;

		this.email = email;

		this.id = id;
	}

	public Long clienteId() {
		return this.id;
	}

	public boolean perteneceAlCliente(Long nroTarjeta) {

		boolean existe = false;
		for (Tarjeta tarjeta : this.mediosDePago) {
			if (tarjeta.nroTarjeta().equals(nroTarjeta))
				return true;
		}
		return existe;

	}

	public String marcaTarjeta(Long nroTarjeta) {

		for (Tarjeta tarjeta : this.mediosDePago) {
			if (tarjeta.nroTarjeta().equals(nroTarjeta))
				return tarjeta.marcaTarjeta();
		}
		return null;
	}

	public boolean nombreYApellido(String nombre, String apellido) {
		if ((this.apellido.equals(apellido)) && (this.nombre.equals(nombre)))
			return true;
		return false;
	}

	public Long dniUsuario() {
		return this.dni.dni();
	}

	public void agregarMetodoDePago(Tarjeta tarjeta) {

		this.mediosDePago.add(tarjeta);

	}

	public void modificarCliente(String nombre, String apellido) {
		this.nombre = nombre;
		this.apellido = apellido;

	}

	public Tarjeta tarjetaPorNro(Long nro) {
		for (Tarjeta tarjeta : this.mediosDePago) {
			if (tarjeta.nroTarjeta().equals(nro))
				return tarjeta;
		}
		return null;
	}

	public List<Tarjeta> mediosDePago() {
		return this.mediosDePago;
	}

	private boolean datoNulo(String dato) {
		return dato.equals("");
	}

	// Constructor vacio y getters y setters
	protected Cliente() {
	}

	protected String getNombre() {
		return nombre;
	}

	protected void setNombre(String nombre) {
		this.nombre = nombre;
	}

	protected String getApellido() {
		return apellido;
	}

	protected void setApellido(String apellido) {
		this.apellido = apellido;
	}

	protected Dni getDni() {
		return dni;
	}

	protected void setDni(Dni dni) {
		this.dni = dni;
	}

	protected Email getEmail() {
		return email;
	}

	protected void setEmail(Email email) {
		this.email = email;
	}

	protected ArrayList<Tarjeta> getMediosDePago() {
		return mediosDePago;
	}

	protected void setMediosDePago(ArrayList<Tarjeta> mediosDePago) {
		this.mediosDePago = mediosDePago;
	}

}
