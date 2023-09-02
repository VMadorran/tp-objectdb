package ar.unrn.tp.servicios;

import java.util.List;

import ar.unrn.tp.api.CategoriaService;
import ar.unrn.tp.api.ConsultaService;
import ar.unrn.tp.modelo.Categoria;

public class CategoriaServiceImplementacion implements CategoriaService {

	private Categoria categoria;
	private ConsultaService consultas;

	@Override
	public void categoriaService(ConsultaService consultas) {
		// TODO Auto-generated method stub

		this.consultas = consultas;
	}

	@Override
	public void crearCategoria(Long codCategoria, String marca) {
		// TODO Auto-generated method stub

		consultas.inTransactionExecute((em) -> {
			var categoria = new Categoria();
			categoria.agregarCodigo(codCategoria);
			categoria.agregarNombre(marca);
			em.persist(categoria);
		});

	}

	@Override
	public Categoria categoriaPorId(Long id) {
		// TODO Auto-generated method stub

		consultas.inTransactionExecute((em) -> {
			categoria = em.find(Categoria.class, id);

		});
		return categoria;
	}

	@Override
	public List<Categoria> categorias() {
		// TODO Auto-generated method stub
//		consultas.inTransactionExecute((em) -> {
//
//		});
		return null;
	}

}
