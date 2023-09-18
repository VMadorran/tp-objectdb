package ar.unrn.tp.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.ConsultaService;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Dni;
import ar.unrn.tp.modelo.Email;
import ar.unrn.tp.modelo.Tarjeta;

public class ClienteServiceImplementacion implements ClienteService {

	private Cliente cliente;
	private ConsultaService consultas = new ConsultaServiceImplementacion();

	@Override
	public void clienteService(ConsultaService consultas) {
		// TODO Auto-generated method stub
		this.consultas = consultas;
	}

	@Override
	public void crearCliente(String nombre, String apellido, String dni, String email) {

		Long numero = Long.parseLong(dni);

		consultas.inTransactionExecute((em) -> {
			try {

				em.persist(new Cliente(nombre, apellido, new Dni(numero), new Email(email)));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Override
	public void modificarCliente(Long idCliente, String nombre, String apellido) {

		consultas.inTransactionExecute((em) -> {
			Cliente cliente = em.getReference(Cliente.class, idCliente);

			try {
				cliente.modificarCliente(nombre, apellido);

				em.persist(cliente);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Override
	public void agregarTarjeta(Long idCliente, String nro, String marca) {

		Long nroTarjeta = Long.valueOf(nro);

		consultas.inTransactionExecute((em) -> {

			Cliente cliente = em.getReference(Cliente.class, idCliente);

			TypedQuery<Cliente> clientesQuery = em.createQuery("select c from Cliente c", Cliente.class);
			try {
				cliente.agregarMetodoDePago(new Tarjeta(nroTarjeta, marca));
				em.persist(cliente);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
	}

	@Override
	public List<Tarjeta> listarTarjetas(Long idCliente) {

		List<Tarjeta> tarjetas = new ArrayList<>();

		consultas.inTransactionExecute((em) -> {

			Cliente cliente = em.getReference(Cliente.class, idCliente);
			tarjetas.addAll(cliente.mediosDePago());
		});
		return tarjetas;
	}

}
