package ar.unrn.tp.api;

import java.util.List;

import ar.unrn.tp.modelo.Tarjeta;

public interface ClienteService {

	void clienteService(ConsultaService consultas);

	void crearCliente(String nombre, String apellido, String dni, String email);

	void modificarCliente(Long idCliente, String nombre, String apellido);

	void agregarTarjeta(Long idCliente, String nro, String marca);

	List<Tarjeta> listarTarjetas(Long idCliente);

}
