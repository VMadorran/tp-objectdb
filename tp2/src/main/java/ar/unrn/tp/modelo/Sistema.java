package ar.unrn.tp.modelo;

import java.util.ArrayList;
import java.util.List;

public class Sistema {

	private ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	private ArrayList<Producto> productos = new ArrayList<Producto>();
	private ArrayList<PromocionMarca> promocionesVigentes = new ArrayList<>();
	private ArrayList<PromocionMarca> historialPromo = new ArrayList<>();
	private ArrayList<PromocionBancaria> historialBanco = new ArrayList<>();
	private PromocionBancaria promocionBancaria;
	private ArrayList<Carrito> carritos = new ArrayList<Carrito>();
	private ArrayList<Venta> ordenes = new ArrayList<Venta>();

	private ArrayList<Categoria> categorias = new ArrayList<>();
	private ServicioWeb servicio;

	public Sistema(ArrayList<Producto> productos, ServicioWeb servicio) {

		this.productos = productos;
		this.servicio = servicio;
	}

	public void crearProducto(String descripcion, Long codigo, Categoria categoria, double precio, String marca)
			throws Exception {

		if ((!this.existeCategoria(categoria.codigoCategoria())) && (!this.existeproducto(codigo))) {

			try {
				Producto producto = new Producto(descripcion, codigo, categoria, precio, marca);
				this.productos.add(producto);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else
			throw new Exception("Ha ocurrido un error al crear el nuevo producto");

	}

	public void agregarMedioDePago(Long dni, Tarjeta tarjeta) throws Exception {

		if (!this.laTarjetaYaExiste(tarjeta.nroTarjeta())) {

			for (Cliente cliente : clientes) {
				if (cliente.dniUsuario().equals(dni))
					cliente.agregarMetodoDePago(tarjeta);
			}
		} else
			throw new Exception("La tarjeta ingresada no existe");
	}

	public void crearUsuario(String nombre, String apellido, Dni dni, Email email) {

		if (!this.existeDni(dni.dni())) {

			try {

				Cliente cliente = new Cliente(nombre, apellido, dni, email);
				this.clientes.add(cliente);
				this.carritos.add(new Carrito(cliente));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void agregarAlCarrito(ArrayList<Producto> productos, Carrito carrito) {

		carrito.agregarProducto(productos);
	}

	public double calcularMontoDeCarritoConDescuentos(Carrito carrito, ArrayList<PromocionMarca> promociones,
			PromocionBancaria promocionBancaria, Long nroTarjeta) {

		if ((promociones != null) && (carrito.cliente().perteneceAlCliente(nroTarjeta))) {

			double montoTotal;
			return montoTotal = carrito.calcularMontoDeCompra(promociones, promocionBancaria, nroTarjeta);
		}
		return 0;
	}

	public double calcularMontoDeCarritoConDescuentosVigentes(Carrito carrito, Long nroTarjeta) {

		return carrito.calcularMontoDeCompra(nroTarjeta);
	}

	public Producto modificarProducto(String descripcion, Long codigo, Categoria categoria, double precio, String marca)
			throws Exception {

		Producto producto = null;
		if ((this.existeCategoria(categoria.codigoCategoria())) && (this.existeproducto(codigo))) {
			producto = this.recuperarProducto(codigo);
			producto.modificarProducto(descripcion, codigo, categoria, precio, marca);
		} else
			throw new Exception("Ha ocurrido un error al modificar el producto");
		return producto;
	}

	public Producto recuperarProducto(Long codigo) {
		for (Producto producto : this.productos) {
			if (producto.codigoProducto() == codigo)
				return producto;
		}
		return null;
	}

	public boolean existeUsuario(Long dni) {

		for (Cliente cliente : clientes) {
			if (cliente.dniUsuario().equals(dni))
				return true;
		}
		return false;
	}

	public Venta realizarCompra(Carrito carrito, Long nroTarjeta) throws Exception {

		if ((this.existeUsuario(carrito.dniClienteCarrito())) && (!carrito.estaVacio())) {

			if (servicio.fondosSuficientes(nroTarjeta)) {
				double montoTotal = 0;

				Venta venta = carrito.calcularMontoDePago(nroTarjeta);
				return venta;
			} else
				throw new Exception("Ha ocurrido un error al realizar la compra");

		}
		return null;

	}

	public void actualizarPromocionesDeCarritos(ArrayList<PromocionMarca> promocionesVigentes,
			PromocionBancaria promocionBancaria) {

		this.actualizarPromocionesVigentes(promocionesVigentes, promocionBancaria);
		for (Carrito carrito : this.carritos) {
			carrito.actualizarPromociones(this.promocionesVigentes, this.promocionBancaria);
		}

	}

	public Carrito carritoUsuario(Long dni) {

		for (Carrito carrito : carritos) {
			if (carrito.perteneceAlUsuario(dni))
				return carrito;
		}
		return null;
	}

	public List<Venta> ventas() {
		return this.ordenes;
	}

	public boolean existeDni(Long dni) {

		boolean existe = false;
		for (Cliente cliente : this.clientes) {
			if (cliente.dniUsuario().equals(dni))
				return true;
		}
		return existe;
	}

	public Cliente cliente(Long dni) {
		for (Cliente cliente : this.clientes) {
			if (cliente.dniUsuario().equals(dni))
				return cliente;
		}
		return null;
	}

	public boolean existeCategoria(Long codigo) {
		for (Categoria categoria : this.categorias) {
			if (categoria.codigoCategoria().equals(codigo))
				;
			return true;
		}
		return false;
	}

	public boolean existeproducto(Long codProducto) {
		for (Producto producto : this.productos) {
			if (producto.idProducto().equals(codProducto))
				return true;
		}
		return false;
	}

	public void actualizarPromocionesVigentes(ArrayList<PromocionMarca> promosMarca, PromocionBancaria promoBanco) {

		this.actualizacionDePromocionesGuardadas();
		for (PromocionMarca promo : promosMarca) {
			if (promo.estaVigente())
				this.promocionesVigentes.add(promo);
		}
		if (promoBanco.estaVigente())
			this.promocionBancaria = promoBanco;
	}

	private void actualizacionDePromocionesGuardadas() {
		for (PromocionMarca promo : this.promocionesVigentes) {
			if (!promo.estaVigente()) {
				this.historialPromo.add(promo);
				this.promocionesVigentes.remove(promo);
			}
		}
		if (!promocionBancaria.estaVigente()) {
			this.historialBanco.add(this.promocionBancaria);
			this.promocionBancaria = null;
		}
	}

	private boolean laTarjetaYaExiste(Long nroTarjeta) {
		for (Cliente cliente : this.clientes) {
			for (Tarjeta tarjeta : cliente.getMediosDePago()) {
				if (tarjeta.nroTarjeta().equals(nroTarjeta))
					return true;
			}
		}
		return false;
	}

}
