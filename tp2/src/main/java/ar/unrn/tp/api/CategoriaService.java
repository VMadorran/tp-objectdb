package ar.unrn.tp.api;

import java.util.List;

import ar.unrn.tp.modelo.Categoria;

public interface CategoriaService {

	void categoriaService(ConsultaService consultas);

	public void crearCategoria(Long codCategoria, String marca);

	public Categoria categoriaPorId(Long id);

	public List<Categoria> categorias();
}
