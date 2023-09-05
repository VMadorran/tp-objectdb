package ar.unrn.tp.main;

import java.util.List;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.ConsultaService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.Tarjeta;
import ar.unrn.tp.servicios.ClienteServiceImplementacion;
import ar.unrn.tp.servicios.ConsultaServiceImplementacion;
import ar.unrn.tp.servicios.DescuentoServiceImplementacion;
import ar.unrn.tp.servicios.ProductoServiceImplementacion;
import ar.unrn.tp.servicios.VentaServiceImplementacion;

public class MainSistemaService {

	public static void main(String[] args) {

		// TODO Auto-generated method stub

		String bd = "jpa-objectdb";

		ConsultaService consService = new ConsultaServiceImplementacion();
		consService.setUp(bd);

		ClienteService clientService = new ClienteServiceImplementacion();
		clientService.clienteService(consService);

		DescuentoService descService = new DescuentoServiceImplementacion();
		descService.descuentoService(consService);

		ProductoService productService = new ProductoServiceImplementacion();
		productService.productoService(consService);

		VentaService ventaService = new VentaServiceImplementacion();
		ventaService.ventaService(consService);

//		List<Venta> ventas = ventaService.ventas();
//		for (Venta venta : ventas) {
//			System.out.println(venta.toString());
//		}

		// clientService.crearCliente("Ana", "Diaz", "41765098", "ana@acdc.com");

		// Long idCliente, String nro, String marca)
		clientService.agregarTarjeta(24L, "987987", "Memecard");

		List<Tarjeta> tarjetas = clientService.listarTarjetas(24L);
		for (Tarjeta tarjeta : tarjetas) {
			System.out.println(tarjeta.toString());
		}

	}

}
