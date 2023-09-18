package ar.unrn.tp.modelo;

import javax.persistence.Embeddable;

@Embeddable
public class ProductoVendido {

	private Long codProducto;
	private double precio;

	public ProductoVendido(Long codProducto, double precio) {
		this.codProducto = codProducto;
		this.precio = precio;

	}

	public Long codProducto() {
		return this.codProducto;
	}

	private Long getCodProducto() {
		return codProducto;
	}

	private void setCodProducto(Long codProducto) {
		this.codProducto = codProducto;
	}

	private double getPrecio() {
		return precio;
	}

	private void setPrecio(double precio) {
		this.precio = precio;
	}

	private ProductoVendido() {

	}

	@Override
	public String toString() {
		return "ProductoVendido [codProducto=" + codProducto + ", precio=" + precio + "]";
	}

}
