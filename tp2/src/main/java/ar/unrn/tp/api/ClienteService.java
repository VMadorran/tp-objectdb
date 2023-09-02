package ar.unrn.tp.api;

import java.util.List;

import ar.unrn.tp.modelo.Tarjeta;

public interface ClienteService {

	void clienteService(ConsultaService consultas);

	// validar que el dni no se repita
	void crearCliente(String nombre, String apellido, String dni, String email);

	// validar que sea un cliente existente
	void modificarCliente(Long idCliente, String nombre); // Datos necesarios para la funcion

	// validar que sea un cliente existente
	void agregarTarjeta(Long idCliente, String nro, String marca);

	// Devuelve las tarjetas de un cliente específico
	List<Tarjeta> listarTarjetas(Long idCliente);

}
