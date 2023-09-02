package ar.unrn.tp.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.ConsultaService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.modelo.Venta;

public class VentaServiceImplementacion implements VentaService {
	private ConsultaService consultas;
	private ProductoService productoImple = new ProductoServiceImplementacion();
	private ClienteService clienteImple = new ClienteServiceImplementacion();

	@Override
	public void ventaService(ConsultaService consultas) {
		// TODO Auto-generated method stub
		this.consultas = consultas;

	}

	@Override
	public void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta) {

	}

	@Override
	public float calcularMonto(List<Long> productos, Long idTarjeta) {

		List<Producto> productosCompra = new ArrayList<>();
		List<Categoria> categorias = new ArrayList<>();
		return 0;
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
