package ar.unrn.tp.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import ar.unrn.tp.api.ConsultaService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.exception.CarritoVacioException;
import ar.unrn.tp.modelo.Carrito;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.modelo.PromocionBancaria;
import ar.unrn.tp.modelo.PromocionMarca;
import ar.unrn.tp.modelo.ServicioWeb;
import ar.unrn.tp.modelo.ServicioWebImplementacion;
import ar.unrn.tp.modelo.Venta;

public class VentaServiceImplementacion implements VentaService {
	private ConsultaService consultas;
	private float monto;
	PromocionBancaria bancaria = null;
	ServicioWeb servicio = new ServicioWebImplementacion();

	@Override
	public void ventaService(ConsultaService consultas) {
		// TODO Auto-generated method stub
		this.consultas = consultas;

	}

	@Override
	public void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta) {

		List<Producto> productosCompra = this.productos(productos);

		consultas.inTransactionExecute((em) -> {

			try {
				Cliente cliente = em.find(Cliente.class, idCliente);

				var carrito = new Carrito(cliente, new ServicioWebImplementacion());

				carrito.agregarProductos(productosCompra);
				carrito.actualizarPromociones(this.promocionesMarca(), this.promocionBancaria());
				var venta = carrito.realizarCompra(idTarjeta);
				em.persist(venta);
			} catch (CarritoVacioException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

	}

	@Override
	public float calcularMonto(List<Long> productos, Long idTarjeta, Long idCliente) {

		ArrayList<Producto> productosCompra = new ArrayList<>();

		consultas.inTransactionExecute((em) -> {
			try {
				for (Long idProducto : productos) {
					var producto = em.getReference(Producto.class, idProducto);
					productosCompra.add(producto);
				}
				var cliente = em.getReference(Cliente.class, idCliente);
				var carrito = new Carrito(cliente, servicio);

				carrito.agregarProductos(productosCompra);

				carrito.actualizarPromociones(this.promocionesMarca(), this.promocionBancaria());
				monto = (float) carrito.calcularMontoDeCompra(idTarjeta);
			} catch (CarritoVacioException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		return monto;

	}

	private List<PromocionBancaria> promocionBancaria() {

		List<PromocionBancaria> bancarias = new ArrayList<>();

		consultas.inTransactionExecute((em) -> {
			TypedQuery<PromocionBancaria> ventasTypedQuery = em.createQuery("select p from PromocionBancaria p",
					PromocionBancaria.class);
			bancarias.addAll(ventasTypedQuery.getResultList());
		});

		return bancarias;

	}

	private List<Producto> productos(List<Long> productos) {

		List<Producto> productosCompra = new ArrayList<>();
		consultas.inTransactionExecute((em) -> {

			for (Long idProducto : productos) {
				var producto = em.getReference(Producto.class, idProducto);
				productosCompra.add(producto);
			}
		});

		return productosCompra;
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
