package ar.unrn.tp.jpa.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.jupiter.api.Test;

import ar.unrn.tp.api.ConsultaService;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Tarjeta;
import ar.unrn.tp.servicios.ClienteServiceImplementacion;
import ar.unrn.tp.servicios.ConsultaServiceImplementacion;

public class ClienteServiceTest {

	private ConsultaService consultas = new ConsultaServiceImplementacion();
	private ClienteServiceImplementacion implementacion = new ClienteServiceImplementacion();
	private Tarjeta tarjeta;

	@Test
	public void crearCliente() {

		consultas.inTransactionExecute((em) -> {
			implementacion.clienteService(consultas);
			implementacion.crearCliente("Jose", "Perez", "123123", "angus@acdc.com");

			var cliente = em.find(Cliente.class, 1L);

			assertEquals(cliente.dniUsuario(), 123123L);
			assertEquals(cliente.nombreYApellido("Jose", "Perez"), true);
		});

	}

	@Test
	public void agregarTarjetaTest() {

		consultas.inTransactionExecute((em) -> {

			implementacion.clienteService(consultas);

			implementacion.crearCliente("Jose", "Perez", "123123", "angus@acdc.com");
			implementacion.agregarTarjeta(1L, "234234", "MemeCard");

			TypedQuery<Tarjeta> tarjetaQuery = em.createQuery("select t from Tarjeta t where t.nroTarjeta = 234234 ",
					Tarjeta.class);
			tarjeta = tarjetaQuery.getSingleResult();
		});

		assertEquals(234234L, tarjeta.nroTarjeta());
	}

	@Test
	public void modificarCliente() {

		consultas.inTransactionExecute((em) -> {

			implementacion.crearCliente("Jose", "Perez", "123123", "angus@acdc.com");
			implementacion.modificarCliente(1L, "Pedro", "Juarez");

			var cliente = em.find(Cliente.class, 1L);

			assertEquals(cliente.nombreYApellido("Pedro", "Juarez"), true);
			assertEquals(cliente.dniUsuario(), 123123L);

		});

	}

	@Test
	public void listarTarjeta() {

		consultas.inTransactionExecute((em) -> {

			implementacion.crearCliente("Jose", "Perez", "123123", "angus@acdc.com");

			implementacion.agregarTarjeta(1L, "123456", "MemeCard");
			implementacion.agregarTarjeta(1L, "234234", "MemeCard");

			List<Tarjeta> tarjetas = implementacion.listarTarjetas(1L);

			assertEquals(123456L, tarjetas.get(0).nroTarjeta());
			assertEquals(234234L, tarjetas.get(1).nroTarjeta());
		});

	}

}
