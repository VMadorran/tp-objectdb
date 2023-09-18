package ar.unrn.tp.jpa.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DecimalFormat;
import java.util.List;

import org.junit.jupiter.api.Test;

import ar.unrn.tp.api.CategoriaService;
import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.ConsultaService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.ProductoVendido;
import ar.unrn.tp.modelo.ProveedorDeFecha;
import ar.unrn.tp.modelo.Venta;
import ar.unrn.tp.servicios.CategoriaServiceImplementacion;
import ar.unrn.tp.servicios.ClienteServiceImplementacion;
import ar.unrn.tp.servicios.ConsultaServiceImplementacion;
import ar.unrn.tp.servicios.DescuentoServiceImplementacion;
import ar.unrn.tp.servicios.ProductoServiceImplementacion;
import ar.unrn.tp.servicios.VentaServiceImplementacion;

public class VentaServiceTest {
	private ConsultaService consService = new ConsultaServiceImplementacion();
	private VentaService implementacion = new VentaServiceImplementacion();

	@Test
	public void calcularMontoTest() {

		consService.inTransactionExecute((em) -> {
			implementacion.ventaService(consService);

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
			descService.crearDescuentoSobreTotal("Memecard", fecha.now().minusWeeks(1), fecha.now().plusMonths(2),
					0.08f);

			clientService.crearCliente("Jose", "Perez", "12345678", "jose@acdc.com");
			clientService.agregarTarjeta(6L, "123123", "Visa");

			implementacion.ventaService(consService);

			var monto = implementacion.calcularMonto(List.of(3L, 2L), 123123L, 6L);

			assertEquals(1475.0f, monto);
		});
	}

	@Test
	public void realizarVentaTest() {

		consService.inTransactionExecute((em) -> {
			implementacion.ventaService(consService);

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
			descService.crearDescuentoSobreTotal("Memecard", fecha.now().minusWeeks(1), fecha.now().plusMonths(2),
					0.08f);

			clientService.crearCliente("Jose", "Perez", "12345678", "jose@acdc.com");
			clientService.agregarTarjeta(6L, "123123", "Visa");

			implementacion.ventaService(consService);

			implementacion.realizarVenta(6L, List.of(3L, 2L), 123123L);
			var venta = em.getReference(Venta.class, 8L);
			List<ProductoVendido> productos = venta.productos();
			DecimalFormat df = new DecimalFormat("#.00");
			String valor = df.format(venta.precioFinal());

			assertEquals("1475,00", valor);
			assertEquals(12345678L, venta.clienteComprador());

			assertEquals(3L, productos.get(0).codProducto());
			assertEquals(1L, productos.get(1).codProducto());

		});

	}

	@Test
	public void ventasTest() {

		consService.inTransactionExecute((em) -> {

			implementacion.ventaService(consService);

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
			descService.crearDescuentoSobreTotal("Memecard", fecha.now().minusWeeks(1), fecha.now().plusMonths(2),
					0.08f);

			clientService.crearCliente("Jose", "Perez", "12345678", "jose@acdc.com");
			clientService.agregarTarjeta(6L, "123123", "Visa");

			implementacion.ventaService(consService);

			implementacion.realizarVenta(6L, List.of(3L, 2L), 123123L);
			implementacion.realizarVenta(6L, List.of(2L), 123123L);
			var venta = em.getReference(Venta.class, 8L);
			var venta2 = em.getReference(Venta.class, 9);
			List<ProductoVendido> productosVenta = venta.productos();
			DecimalFormat df = new DecimalFormat("#.00");
			String valor = df.format(venta.precioFinal());

			assertEquals("1475,00", valor);
			assertEquals(12345678L, venta.clienteComprador());

			assertEquals(3L, productosVenta.get(0).codProducto());
			assertEquals(1L, productosVenta.get(1).codProducto());
			productosVenta = venta2.productos();

			assertEquals(1000.00, venta2.precioFinal());
			assertEquals(12345678L, venta2.clienteComprador());
			assertEquals(1L, productosVenta.get(0).codProducto());

		});
	}
}
