package ar.unrn.tp.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.ConsultaService;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Dni;
import ar.unrn.tp.modelo.Email;
import ar.unrn.tp.modelo.Sistema;
import ar.unrn.tp.modelo.Tarjeta;

public class ClienteServiceImplementacion implements ClienteService {

	Cliente cliente;
	private Sistema sistema = new Sistema(null, null);
	private ConsultaService consultas = new ConsultaServiceImplementacion();

	@Override
	public void clienteService(ConsultaService consultas) {
		// TODO Auto-generated method stub

	}

	@Override
	public void crearCliente(String nombre, String apellido, String dni, String email) {

		Long numero = Long.parseLong(dni);

		consultas.inTransactionExecute((em) -> {

			try {
				// ver de agregar a sistema
				em.persist(new Cliente(nombre, apellido, new Dni(numero), new Email(email)));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Override
	public void modificarCliente(Long idCliente, String nombre) {

		consultas.inTransactionExecute((em) -> {

		});
	}

	@Override
	public void agregarTarjeta(Long idCliente, String nro, String marca) {

		Cliente cliente = this.cliente(idCliente);
		Long nroTarjeta = Long.valueOf(nro);

		consultas.inTransactionExecute((em) -> {
			try {
				sistema.agregarMedioDePago(cliente.dniUsuario(), new Tarjeta(nroTarjeta, marca));
				Tarjeta tarjeta = cliente.tarjetaPorNro(nroTarjeta);
				em.persist(tarjeta);
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

			TypedQuery<Tarjeta> tarjetasQuery = em.createQuery(
					"Select t from tarjeta join cliente c.id= t.idCliente" + "where c.id=: idCliente", Tarjeta.class);
			tarjetasQuery.setParameter(1, idCliente);

			tarjetas.addAll(tarjetasQuery.getResultList());

		});

		return tarjetas;
	}

	private Cliente cliente(Long id) {

		consultas.inTransactionExecute((em) -> {

			cliente = em.find(Cliente.class, id);

		});
		return cliente;
	}

}
