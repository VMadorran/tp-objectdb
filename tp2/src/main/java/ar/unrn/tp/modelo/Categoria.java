package ar.unrn.tp.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Categoria implements Subcategoria {
	@Id
	@GeneratedValue
	private Long id;
	private String nombre;
	private Long codigoCategoria;

	@Override
	public void agregarSubcategoria(Subcategoria categoria) {
		// TODO Auto-generated method stub
	}

	@Override
	public void agregarNombre(String nombre) {
		// TODO Auto-generated method stub
		this.nombre = nombre;
	}

	public void agregarCodigo(Long codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
	}

	public Long codigoCategoria() {
		return this.codigoCategoria;
	}

	@Override
	public String nombreCategoria() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String subcategorias() {
		// TODO Auto-generated method stub
		return null;
	}

}
