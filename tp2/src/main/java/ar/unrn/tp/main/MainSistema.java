package ar.unrn.tp.main;

import ar.unrn.tp.api.CategoriaService;
import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.ConsultaService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.ProveedorDeFecha;
import ar.unrn.tp.servicios.CategoriaServiceImplementacion;
import ar.unrn.tp.servicios.ClienteServiceImplementacion;
import ar.unrn.tp.servicios.ConsultaServiceImplementacion;
import ar.unrn.tp.servicios.DescuentoServiceImplementacion;
import ar.unrn.tp.servicios.ProductoServiceImplementacion;
import ar.unrn.tp.servicios.VentaServiceImplementacion;

public class MainSistema {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConsultaService consService = new ConsultaServiceImplementacion();

		// _________________________________________________________________________________________________________________________________________

//		CategoriaService catService = new CategoriaServiceImplementacion();
//		catService.categoriaService(consService);
//
//		ProductoService productService = new ProductoServiceImplementacion();
//		productService.productoService(consService);
//
//		DescuentoService descService = new DescuentoServiceImplementacion();
//		descService.descuentoService(consService);
//		catService.crearCategoria(1L, "Indumentaria");
//		ClienteService clientService = new ClienteServiceImplementacion();
//		clientService.clienteService(consService);
		VentaService ventaService = new VentaServiceImplementacion();
		ventaService.ventaService(consService);
//
//		productService.crearProducto(1L, "Remera corta", 1000, 1L, "Nope");
//		productService.crearProducto(3L, "Medias", 500, 1L, "Acme");
//
//		var fecha = new ProveedorDeFecha();
//
//		descService.crearDescuento("Acme", fecha.now().minusMonths(1), fecha.now().plusMonths(1), 0.05f);
//		descService.crearDescuentoSobreTotal("Memecard", fecha.now().minusWeeks(1), fecha.now().plusMonths(2), 0.08f);

//		clientService.agregarTarjeta(6L, "123123", "Visa");
		CategoriaService catService = new CategoriaServiceImplementacion();
		catService.categoriaService(consService);

		ProductoService productService = new ProductoServiceImplementacion();
		productService.productoService(consService);

		DescuentoService descService = new DescuentoServiceImplementacion();
		descService.descuentoService(consService);

		ClienteService clientService = new ClienteServiceImplementacion();
		clientService.clienteService(consService);

		catService.crearCategoria(1L, "Indumentaria");

		productService.crearProducto(1L, "Remera corta", 1000, 1L, "Nope");
		productService.crearProducto(3L, "Medias", 500, 1L, "Acme");

		var fecha = new ProveedorDeFecha();
		descService.crearDescuento("Acme", fecha.now().minusMonths(1), fecha.now().plusMonths(1), 0.05f);
		descService.crearDescuentoSobreTotal("Memecard", fecha.now().minusWeeks(1), fecha.now().plusMonths(2), 0.08f);

		clientService.crearCliente("Jose", "Perez", "12345678", "jose@acdc.com");
		// clientService.agregarTarjeta(6L, "123123", "Visa");

//		ventaService.realizarVenta(6L,List.of(3L, 2L), 123123L);

	}

}
