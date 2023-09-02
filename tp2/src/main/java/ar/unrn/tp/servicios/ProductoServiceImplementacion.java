package ar.unrn.tp.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import ar.unrn.tp.api.CategoriaService;
import ar.unrn.tp.api.ConsultaService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.modelo.Sistema;

public class ProductoServiceImplementacion implements ProductoService {

	Sistema sistema;
	private ConsultaService consultas;
	private CategoriaService categorias;

	@Override
	public void productoService(ConsultaService consulta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void crearProducto(Long codigo, String descripcion, double precio, Long idCategoria, String marca) {

		Categoria categoria = categorias.categoriaPorId(idCategoria);
		consultas.inTransactionExecute((em) -> {

			try {
				sistema.crearProducto(descripcion, codigo, categoria, precio, marca);
				Producto producto = sistema.recuperarProducto(codigo);
				em.persist(producto);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Override
	public void modificarProducto(Long idProducto, String descripcion, long idCategoria, double precio, String marca) {
		Categoria categoria = this.categorias.categoriaPorId(idCategoria);

		consultas.inTransactionExecute((em) -> {
			Producto producto = em.getReference(Producto.class, idProducto);
			try {
				sistema.modificarProducto(descripcion, producto.codigoProducto(), categoria, precio, marca);
				em.persist(sistema.recuperarProducto(producto.codigoProducto()));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Override
	public List<Producto> listarProductos() {

		List<Producto> productos = new ArrayList<>();

		consultas.inTransactionExecute((em) -> {

			TypedQuery<Producto> productoTypedQuery = em.createQuery("select p from Producto p", Producto.class);
			productos.addAll(productoTypedQuery.getResultList());

		});
		return productos;
	}

}
