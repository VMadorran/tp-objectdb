package ar.unrn.tp.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductoVendido {
	@Id
	private Long idProducto;
	private double precio;

	public ProductoVendido(Long idProducto, double precio) {
		this.idProducto = idProducto;
		this.precio = precio;

	}

	private Long getIdProducto() {
		return idProducto;
	}

	private void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	private double getPrecio() {
		return precio;
	}

	private void setPrecio(double precio) {
		this.precio = precio;
	}

	private ProductoVendido() {

	}

}
