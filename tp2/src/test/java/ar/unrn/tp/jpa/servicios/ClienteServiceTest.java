package ar.unrn.tp.jpa.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.jupiter.api.Test;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.ConsultaService;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Dni;
import ar.unrn.tp.modelo.Email;
import ar.unrn.tp.modelo.Tarjeta;
import ar.unrn.tp.servicios.ClienteServiceImplementacion;
import ar.unrn.tp.servicios.ConsultaServiceImplementacion;

public class ClienteServiceTest {

	private Cliente cliente, clienteEsperado;
	private ConsultaService consultas = new ConsultaServiceImplementacion();
	private ClienteService implementacion = new ClienteServiceImplementacion();
	private Tarjeta tarjeta;
	private List<Tarjeta> tarjetas = new ArrayList<>();

	@Test
	public void crearCliente() {
		// String nombre, String apellido, String dni, String email

		try {
			var dni = new Dni(123123L);
			var email = new Email("angus@acdc.com");
			var clienteEsperado = new Cliente("Jose", "Perez", dni, email, 1L);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		implementacion.crearCliente("Jose", "Perez", "angus@acdc.com", "123123");
		consultas.inTransactionExecute((em) -> {

			cliente = em.find(Cliente.class, 1L);
		});
		assertEquals(clienteEsperado, cliente);

	}

	@Test
	public void agregarTarjetaTest() {

		this.implementacion.agregarTarjeta(1L, "234234", "MemeCard");

		Tarjeta tarjetaEsperada = new Tarjeta(234234L, "MemeCard");
		consultas.inTransactionExecute((em) -> {
			TypedQuery<Tarjeta> tarjetaQuery = em.createQuery("select t from Tarjeta where t.nroTarjeta = 234234 ",
					Tarjeta.class);
			tarjeta = tarjetaQuery.getSingleResult();
		});

		assertEquals(tarjetaEsperada, tarjeta);
	}

	@Test
	public void modificarCliente() {

		try {
			var dni = new Dni(123123L);
			var email = new Email("angus@acdc.com");
			var clienteEsperado = new Cliente("Jose", "Perez", dni, email, 1L);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		implementacion.crearCliente("Jose", "Perez", "angus@acdc.com", "123123");
		consultas.inTransactionExecute((em) -> {

			cliente = em.find(Cliente.class, 1L);
		});
		assertEquals(clienteEsperado, cliente);

	}

	@Test
	public void listarTarjeta() {

		Tarjeta tarjeta1 = new Tarjeta(123456L, "MemeCard");
		Tarjeta tarjeta2 = new Tarjeta(234234L, "MemeCard");
		Tarjeta tarjeta3 = new Tarjeta(194758L, "MemeCard");
		List<Tarjeta> tarjetasEsperadas = new ArrayList<>();
		tarjetasEsperadas.add(tarjeta1);
		tarjetasEsperadas.add(tarjeta2);
		tarjetasEsperadas.add(tarjeta3);

		implementacion.listarTarjetas(1L);
		consultas.inTransactionExecute((em) -> {

			TypedQuery<Tarjeta> tarjetasQuery = em.createQuery(
					"Select t from tarjeta join cliente c.id= t.idCliente" + "where c.id=: 1", Tarjeta.class);
			tarjetas.addAll(tarjetasQuery.getResultList());
		});
		assertEquals(tarjetasEsperadas, tarjetas);
	}

}
