package ar.unrn.tp.jpa.servicios;

import org.junit.jupiter.api.Test;

import ar.unrn.tp.api.ConsultaService;
import ar.unrn.tp.servicios.ConsultaServiceImplementacion;

public class VentaServiceTest {
	private ConsultaService consultas = new ConsultaServiceImplementacion();

	@Test
	public void calcularMontoTest() {
		consultas.inTransactionExecute((em) -> {
		});
	}

	@Test
	public void realizarVentaTest() {
		consultas.inTransactionExecute((em) -> {
		});
	}

	@Test
	public void ventasTest() {
		consultas.inTransactionExecute((em) -> {
		});
	}
}
