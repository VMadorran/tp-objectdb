package ar.unrn.tp.main;

import ar.unrn.tp.api.CategoriaService;
import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.ConsultaService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.servicios.CategoriaServiceImplementacion;
import ar.unrn.tp.servicios.ClienteServiceImplementacion;
import ar.unrn.tp.servicios.ConsultaServiceImplementacion;
import ar.unrn.tp.servicios.DescuentoServiceImplementacion;
import ar.unrn.tp.servicios.ProductoServiceImplementacion;
import ar.unrn.tp.servicios.VentaServiceImplementacion;

public class MainSistemaService {

	public static void main(String[] args) {

		// TODO Auto-generated method stub

		ConsultaService consService = new ConsultaServiceImplementacion();

		// _________________________________________________________________________________________________________________________________________

		CategoriaService catService = new CategoriaServiceImplementacion();
		catService.categoriaService(consService);

		catService.crearCategoria(1L, "Remeras");
		catService.crearCategoria(2L, "Pantalones");
		catService.crearCategoria(3L, "Medias");

		// _________________________________________________________________________________________________________________________________________

		ClienteService clientService = new ClienteServiceImplementacion();
		clientService.clienteService(consService);

//		clientService.crearCliente("Martin", "Diaz", "41709098", "ana@acdc.com");
//		clientService.crearCliente("Jose", "Perez", "12345678", "jose@acdc.com");

		clientService.agregarTarjeta(13L, "123123", "Visa");
//		clientService.modificarCliente(13L, "Luis", "Martinez", "42876345", "luis@acdc.com");

		// _________________________________________________________________________________________________________________________________________

		DescuentoService descService = new DescuentoServiceImplementacion();
		descService.descuentoService(consService);

//		var fecha = new ProveedorDeFecha();
//
//		descService.crearDescuento("Acme", fecha.now().minusMonths(1), fecha.now().plusMonths(1), 0.05f);
//		descService.crearDescuento("Nope", fecha.now().minusMonths(1), fecha.now().plusMonths(1), 0.05f);
//
//		descService.crearDescuentoSobreTotal("Memecard", fecha.now().minusWeeks(1), fecha.now().plusMonths(2), 0.08f);

		// _________________________________________________________________________________________________________________________________________

		ProductoService productService = new ProductoServiceImplementacion();
		productService.productoService(consService);
//		(Long codigo, String descripcion, double precio, Long idCategoria, String marca
//		productService.crearProducto(1L, "Remera corta", 1000, 10L, "Nope");
//		productService.crearProducto(2L, "Pantalon", 2000, 11L, "Nope");
//		productService.crearProducto(3L, "Medias", 500, 12L, "Acme");

//		var produc2 = new Producto("Remera Corta", 2L, cate1, 2000, "Nope");
//
//		var cate2 = new Categoria();
//		cate2.agregarCodigo(2L);
//		cate2.agregarNombre("Pantalon");
//		var produc3 = new Producto("Pantalon Rojo", 3L, cate2, 1500, "Acme");

		// _________________________________________________________________________________________________________________________________________

		VentaService ventaService = new VentaServiceImplementacion();
		ventaService.ventaService(consService);

	}

}
