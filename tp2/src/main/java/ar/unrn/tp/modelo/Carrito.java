package ar.unrn.tp.modelo;

import java.util.ArrayList;
import java.util.List;

public class Carrito {

	private ArrayList<Producto> productosAComprar = new ArrayList<Producto>();
	private Cliente cliente;
	private ArrayList<PromocionMarca> promocionesVigentes = new ArrayList<PromocionMarca>();
	private PromocionBancaria promocionBancaria;

	public Carrito(Cliente usuario) {

		this.cliente = usuario;
	}

	protected void actualizarPromociones(ArrayList<PromocionMarca> promociones, PromocionBancaria promocionBancaria) {

		this.promocionesVigentes = promociones;
		this.promocionBancaria = promocionBancaria;
	}

	public void agregarProducto(ArrayList<Producto> productos) {

		this.productosAComprar.addAll(productos);
	}

	public double calcularMontoDeCompra(Long nroTarjeta) {
		return this.calcularMontoDeCompra(promocionesVigentes, promocionBancaria, nroTarjeta);
	}

	public double calcularMontoDeCompra(ArrayList<PromocionMarca> promociones, PromocionBancaria promocionBancaria,
			Long nroTarjeta) {

		double montoTotal = 0;

		for (Producto producto : this.productosAComprar) {

			double precioProducto = producto.precioProducto();
			System.out.println(producto.marcaProducto());
			for (Promocion promocion : promociones) {

				if ((promocion.estaVigente()) && (promocion.seAplicaDescuento(producto.marcaProducto()))) {
					precioProducto = promocion.aplicarDescuento(precioProducto);
				}
			}

			montoTotal = montoTotal + precioProducto;
		}

		montoTotal = montoTotal - this.montoConPromocionBancaria(promocionBancaria, nroTarjeta, montoTotal);
		return montoTotal;
	}

	private double montoConPromocionBancaria(PromocionBancaria promocionBancaria, Long nroTarjeta, double montoCompra) {

		double monto = 0;

		if ((promocionBancaria.seAplicaDescuento(cliente.marcaTarjeta(nroTarjeta)))
				&& (promocionBancaria.estaVigente()))
			return monto = promocionBancaria.aplicarDescuento(montoCompra);
		else
			return 0;
	}

	public Venta calcularMontoDePago(Long nroTarjeta) {

		double montoTotal = 0;

		if (this.cliente.perteneceAlCliente(nroTarjeta)&&(!this.estaVacio())) {

			List<ProductoVendido> detallesDeCompra = new ArrayList<>();

			for (Producto producto : this.productosAComprar) {

				double precioProducto = producto.precioProducto();
				System.out.println(producto.marcaProducto());
				for (Promocion promocion : this.promocionesVigentes) {
					if ((promocion.estaVigente()) && (promocion.seAplicaDescuento(producto.marcaProducto()))) {
						precioProducto = promocion.aplicarDescuento(precioProducto);
					}
				}

				detallesDeCompra.add(new ProductoVendido(producto.idProducto(), precioProducto));
				montoTotal = montoTotal + precioProducto;
				System.out.println("MontoTotal:" + montoTotal);

			}

			double bancaria = this.montoConPromocionBancaria(this.promocionBancaria, nroTarjeta, montoTotal);

			montoTotal = montoTotal - bancaria;

			Venta venta = new Venta(new ProveedorDeFecha().now(), detallesDeCompra, this.dniClienteCarrito(), montoTotal,
					bancaria);

			return venta;
		}
		return null;
	}

	public boolean perteneceAlUsuario(Long dniUsuario) {

		if (cliente.dniUsuario().equals(dniUsuario))
			return true;

		return false;
	}

	public Long dniClienteCarrito() {
		return this.cliente.dniUsuario();
	}

	public boolean estaVacio() {
		return this.productosAComprar.isEmpty();

	}

	public Cliente cliente() {
		return this.cliente;
	}

}
