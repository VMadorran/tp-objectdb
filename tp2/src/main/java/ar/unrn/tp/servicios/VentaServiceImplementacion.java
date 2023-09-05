package ar.unrn.tp.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import ar.unrn.tp.api.ConsultaService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.Carrito;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.modelo.PromocionBancaria;
import ar.unrn.tp.modelo.PromocionMarca;
import ar.unrn.tp.modelo.ProveedorDeFecha;
import ar.unrn.tp.modelo.Sistema;
import ar.unrn.tp.modelo.Tarjeta;
import ar.unrn.tp.modelo.Venta;

public class VentaServiceImplementacion implements VentaService {
	private Sistema sistema;
	private ConsultaService consultas;
	private float monto;
	PromocionBancaria bancaria = null;

	@Override
	public void ventaService(ConsultaService consultas) {
		// TODO Auto-generated method stub
		this.consultas = consultas;

	}

	@Override
	public void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta) {

		ArrayList<Producto> productosCompra = this.productos(productos);
		sistema.actualizarPromocionesVigentes(this.promocionesMarca(), this.promocionBancaria());

		consultas.inTransactionExecute((em) -> {

			Cliente cliente = em.find(Cliente.class, idCliente);

			var carrito = new Carrito(cliente);

			sistema.agregarAlCarrito((ArrayList<Producto>) productosCompra, carrito);// Agrega los productos al carrito
			var tarjeta = em.getReference(Tarjeta.class, idTarjeta);// Recupera la tarjeta del cliente

			try {
				sistema.realizarCompra(carrito, idTarjeta); // realiza la compra
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

	}

	@Override
	public float calcularMonto(List<Long> productos, Long idTarjeta) {

		ArrayList<Producto> productosCompra = this.productos(productos);

		consultas.inTransactionExecute((em) -> {

			TypedQuery<Cliente> clienteTypedQuery = em.createQuery(
					"select c from Cliente c join tarjeta t where t.id_cliente =: idTarjeta ", Cliente.class);
			clienteTypedQuery.setParameter(1, idTarjeta);
			var cliente = clienteTypedQuery.getSingleResult();
			var carrito = new Carrito(cliente);

			sistema.agregarAlCarrito(productosCompra, carrito);
			var tarjeta = em.getReference(Tarjeta.class, idTarjeta);

			sistema.actualizarPromocionesVigentes(this.promocionesMarca(), this.promocionBancaria());
			var monto = (float) sistema.calcularMontoDeCarritoConDescuentosVigentes(carrito, idTarjeta);

		});

		return monto;

	}

	private PromocionBancaria promocionBancaria() {

		consultas.inTransactionExecute((em) -> {

			TypedQuery<PromocionBancaria> promo = em.createQuery("Select p from PromocionBancaria where p.fin >=: hoy",
					PromocionBancaria.class);
			promo.setParameter(1, new ProveedorDeFecha().now());
			bancaria = promo.getSingleResult();

		});

		return bancaria;

	}

	private ArrayList<Producto> productos(List<Long> productos) {

		ArrayList<Producto> productosCompra = new ArrayList<>();
		consultas.inTransactionExecute((em) -> {

			for (Long idProducto : productos) {
				var producto = em.getReference(Producto.class, idProducto);
				productosCompra.add(producto);
			}
		});

		return productosCompra;
	}

	private ArrayList<PromocionMarca> promocionesMarca() {

		ArrayList<PromocionMarca> bancarias = new ArrayList<>();

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
