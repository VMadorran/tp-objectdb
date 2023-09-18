package ar.unrn.tp.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Venta {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "fecha_y_hora")
	private LocalDateTime fechaYHora;

	@Embedded
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_venta")
	private List<ProductoVendido> productosVendidos = new ArrayList<>();
	private Long dniCliente;
	private double precioFinal;
	private double descuentoBancario;

	public Venta(LocalDateTime fechaYHora, List<ProductoVendido> productosVendidos, Long dniCliente, double precioFinal,
			double descuentoBancario) {

		this.fechaYHora = fechaYHora;
		this.productosVendidos = productosVendidos;
		this.dniCliente = dniCliente;
		this.precioFinal = precioFinal;
		this.descuentoBancario = descuentoBancario;
	}

	private Venta() {
	}

	public Long clienteComprador() {
		return this.dniCliente;
	}

	public double precioFinal() {
		return this.precioFinal;
	}

	public List<ProductoVendido> productos() {
		return this.productosVendidos;
	}

	private LocalDateTime getFechaYHora() {
		return fechaYHora;
	}

	private void setFechaYHora(LocalDateTime fechaYHora) {
		this.fechaYHora = fechaYHora;
	}

	private List<ProductoVendido> getItemsDeCompra() {
		return this.productosVendidos;
	}

	private void setItemsDeCompra(List<ProductoVendido> productos) {
		this.productosVendidos = productos;
	}

	@Override
	public String toString() {
		String venta = "id=" + id + ", fechaYHora=" + fechaYHora + " dniCliente=" + dniCliente + ", precioFinal="
				+ precioFinal + ", descuentoBancario=" + descuentoBancario + "Cantidad de productos:"
				+ this.productosVendidos.size();

		return venta;
	}
}
