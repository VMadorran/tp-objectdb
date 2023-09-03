package ar.unrn.tp.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.ConsultaService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.Carrito;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.modelo.PromocionBancaria;
import ar.unrn.tp.modelo.PromocionMarca;
import ar.unrn.tp.modelo.Sistema;
import ar.unrn.tp.modelo.Tarjeta;
import ar.unrn.tp.modelo.Venta;

public class VentaServiceImplementacion implements VentaService {
	private Sistema sistema;
	private ConsultaService consultas;
	private ProductoService productoImple = new ProductoServiceImplementacion();
	private ClienteService clienteImple = new ClienteServiceImplementacion();
	private float monto;

	@Override
	public void ventaService(ConsultaService consultas) {
		// TODO Auto-generated method stub
		this.consultas = consultas;
		this.productoImple.productoService(consultas);
		this.clienteImple.clienteService(consultas);
	}

	@Override
	public void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta) {

		List<Producto> productosCompra = new ArrayList<>();

		consultas.inTransactionExecute((em) -> {

			Cliente cliente = em.find(Cliente.class, idCliente);

			for (Long idProducto : productos) {
				var producto = em.getReference(Producto.class, idProducto);
				productosCompra.add(producto);
			}

			var carrito = new Carrito(cliente);

			sistema.agregarAlCarrito((ArrayList<Producto>) productosCompra, carrito);
			var tarjeta = em.getReference(Tarjeta.class, idTarjeta);

			try {
				sistema.realizarCompra(carrito, idTarjeta);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

	}

	@Override
	public float calcularMonto(List<Long> productos, Long idTarjeta) {

		List<Producto> productosCompra = new ArrayList<>();

		consultas.inTransactionExecute((em) -> {

			for (Long idProducto : productos) {
				var producto = em.getReference(Producto.class, idProducto);
				productosCompra.add(producto);
			}

			TypedQuery<Cliente> clienteTypedQuery = em.createQuery("select c from Cliente c join tarjeta t where t.",
					Cliente.class);
			var cliente = clienteTypedQuery.getSingleResult();
			var carrito = new Carrito(cliente);

			sistema.agregarAlCarrito((ArrayList<Producto>) productosCompra, carrito);
			var tarjeta = em.getReference(Tarjeta.class, idTarjeta);

			var monto = (float) sistema.calcularMontoDeCarritoConDescuentosVigentes(carrito, idTarjeta);

		});

		return monto;

	}

	private List<PromocionBancaria> promocionesBancarias() {

		List<PromocionBancaria> bancarias = new ArrayList<>();

		consultas.inTransactionExecute((em) -> {
			TypedQuery<PromocionBancaria> ventasTypedQuery = em.createQuery("select p from PromocionBancaria p",
					PromocionBancaria.class);
			bancarias.addAll(ventasTypedQuery.getResultList());
		});

		return bancarias;

	}

	private List<PromocionMarca> promocionesMarca() {

		List<PromocionMarca> bancarias = new ArrayList<>();

		consultas.inTransactionExecute((em) -> {
			TypedQuery<PromocionMarca> ventasTypedQuery = em.createQuery("select p from PromocionMarca p",
					PromocionMarca.class);
			bancarias.addAll(ventasTypedQuery.getResultList());
		});

		return bancarias;
	}

	@Override
	public List<Venta> ventas() {
		List<Venta> ventas = new ArrayList<>();
		consultas.inTransactionExecute((em) -> {
			TypedQuery<Venta> ventasTypedQuery = em.createQuery("select v from Venta v", Venta.class);
			ventas.addAll(ventasTypedQuery.getResultList());
		});

		return ventas;
	}

}
