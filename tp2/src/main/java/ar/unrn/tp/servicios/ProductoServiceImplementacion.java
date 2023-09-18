package ar.unrn.tp.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import ar.unrn.tp.api.ConsultaService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Producto;

public class ProductoServiceImplementacion implements ProductoService {

	private ConsultaService consultas;

	@Override
	public void productoService(ConsultaService consulta) {
		// TODO Auto-generated method stub
		this.consultas = consulta;
	}

	@Override
	public void crearProducto(Long codigo, String descripcion, double precio, Long idCategoria, String marca) {

		consultas.inTransactionExecute((em) -> {

			try {

				Categoria categoriaProducto = em.getReference(Categoria.class, idCategoria);
				Producto producto = new Producto(descripcion, codigo, categoriaProducto, precio, marca);

				em.persist(producto);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Override
	public void modificarProducto(Long idProducto, String descripcion, double precio, String marca) {

		consultas.inTransactionExecute((em) -> {

			try {
				Producto producto = em.getReference(Producto.class, idProducto);

				producto.modificarProducto(descripcion, precio, marca);
				em.persist(producto);

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
