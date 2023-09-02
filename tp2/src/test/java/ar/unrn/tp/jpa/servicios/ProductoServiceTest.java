package ar.unrn.tp.jpa.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.jupiter.api.Test;

import ar.unrn.tp.api.ConsultaService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.servicios.ConsultaServiceImplementacion;
import ar.unrn.tp.servicios.ProductoServiceImplementacion;

public class ProductoServiceTest {

	private ConsultaService consultas = new ConsultaServiceImplementacion();
	private ProductoService implementacion = new ProductoServiceImplementacion();
	private List<Producto> productos = new ArrayList<>();
	private Producto productoEsperado;

	@Test
	public void crearProductoTest() {

		Categoria categoria = new Categoria();
		categoria.agregarNombre("Indumentaria");

		try {
			productoEsperado = new Producto("Remera", 1L, categoria, 1500, "Acme", 1L);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		consultas.setUp("");
		consultas.inTransactionExecute((em) -> {
			TypedQuery<Producto> productoTypedQuery = em.createQuery("select p from Producto p where p.id = 1",
					Producto.class);
			var producto = em.find(Producto.class, 1L);
			assertEquals(productoEsperado, producto);
		});
	}

	@Test
	public void modificarProductoTest() {

		consultas.setUp("");
		consultas.inTransactionExecute((em) -> {
		});
	}

	@Test
	public void listarProductosTest() {
		Categoria categoria = new Categoria();
		categoria.agregarNombre("Indumentaria");

		List<Producto> productosEsperados = new ArrayList<>();

		try {
			var producto1 = new Producto("Zapatillas", 1L, categoria, 25000, "Comarca", 1L);
			var producto2 = new Producto("Remera", 2L, categoria, 1500, "Acme", 2L);
			var producto3 = new Producto("Medias", 3L, categoria, 500, "Acme", 3L);
			var producto4 = new Producto("Pantalon", 4L, categoria, 3000, "Comarca", 4L);
			productosEsperados.add(producto1);
			productosEsperados.add(producto2);
			productosEsperados.add(producto3);
			productosEsperados.add(producto4);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		consultas.setUp("");
		consultas.inTransactionExecute((em) -> {

			TypedQuery<Producto> productoTypedQuery = em.createQuery("select p from Producto p", Producto.class);
			productos.addAll(productoTypedQuery.getResultList());
			assertEquals(productosEsperados, productos);

		});

	}
}
