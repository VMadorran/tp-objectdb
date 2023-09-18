package ar.unrn.tp.jpa.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.jupiter.api.Test;

import ar.unrn.tp.api.CategoriaService;
import ar.unrn.tp.api.ConsultaService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.servicios.CategoriaServiceImplementacion;
import ar.unrn.tp.servicios.ConsultaServiceImplementacion;
import ar.unrn.tp.servicios.ProductoServiceImplementacion;

public class ProductoServiceTest {

	private ConsultaService consultas = new ConsultaServiceImplementacion();
	private ProductoService implementacion = new ProductoServiceImplementacion();
	private CategoriaService catService = new CategoriaServiceImplementacion();
	private List<Producto> productos = new ArrayList<>();

	@Test
	public void crearProductoTest() {

		try {
			Categoria categoria = new Categoria();
			categoria.agregarNombre("Indumentaria");
			categoria.agregarCodigo(1L);
			implementacion.productoService(consultas);
			catService.categoriaService(consultas);

			catService.crearCategoria(1L, "Indumentaria");
			implementacion.crearProducto(1L, "Zapatillas", 10000, 1L, "Acme");

			consultas.inTransactionExecute((em) -> {

				var producto = em.find(Producto.class, 2L);

				assertEquals("Zapatillas", producto.descripcion());
				assertEquals(1L, producto.codigoProducto());
				assertEquals("Acme", producto.marcaProducto());
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void modificarProductoTest() {

		try {
			Categoria categoria = new Categoria();
			categoria.agregarNombre("Indumentaria");
			categoria.agregarCodigo(1L);

			implementacion.productoService(consultas);
			catService.categoriaService(consultas);

			catService.crearCategoria(1L, "Indumentaria");
			implementacion.crearProducto(1L, "Zapatillas", 10000, 1L, "Comarca");

			implementacion.modificarProducto(1L, "Camiseta", 1500, "Nope");
			consultas.inTransactionExecute((em) -> {
				var producto = em.find(Producto.class, 2L);

				assertEquals("Camiseta", producto.descripcion());
				assertEquals(1L, producto.codigoProducto());
				assertEquals("Nope", producto.marcaProducto());
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void listarProductosTest() {
		Categoria categoria = new Categoria();
		categoria.agregarNombre("Indumentaria");
		categoria.agregarCodigo(1L);

		try {

			implementacion.productoService(consultas);
			catService.categoriaService(consultas);

			catService.crearCategoria(1L, "Indumentaria");
			implementacion.crearProducto(1L, "Remera", 1000, 1L, "Acme");
			implementacion.crearProducto(2L, "Remera", 1000, 1L, "Nope");

			consultas.inTransactionExecute((em) -> {

				TypedQuery<Producto> productoTypedQuery = em.createQuery("select p from Producto p", Producto.class);
				productos.addAll(productoTypedQuery.getResultList());

				assertEquals("Remera", productos.get(0).descripcion());
				assertEquals(1L, productos.get(0).codigoProducto());
				assertEquals("Acme", productos.get(0).marcaProducto());

				assertEquals("Remera", productos.get(1).descripcion());
				assertEquals(2L, productos.get(1).codigoProducto());
				assertEquals("Nope", productos.get(1).marcaProducto());
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
