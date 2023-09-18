package ar.unrn.tp.jpa.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import ar.unrn.tp.api.ConsultaService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.modelo.Fecha;
import ar.unrn.tp.modelo.PromocionBancaria;
import ar.unrn.tp.modelo.PromocionMarca;
import ar.unrn.tp.modelo.ProveedorDeFecha;
import ar.unrn.tp.servicios.ConsultaServiceImplementacion;
import ar.unrn.tp.servicios.DescuentoServiceImplementacion;

public class DescuentoServiceTest {
	private ConsultaService consultas = new ConsultaServiceImplementacion();

	private DescuentoService descService = new DescuentoServiceImplementacion();
	private Fecha fecha = new ProveedorDeFecha();

	private LocalDateTime desde = fecha.now().plusDays(5);
	private LocalDateTime hasta = fecha.now().plusWeeks(2);

	@Test
	public void crearDescuentoTest() {

		try {

			descService.descuentoService(consultas);
			descService.crearDescuento("Acme", desde, hasta, 0.05f);

			consultas.inTransactionExecute((em) -> {

				PromocionMarca promo = em.getReference(PromocionMarca.class, 1L);
				assertEquals(promo.marca(), "Acme");
				assertEquals(promo.descuento(), 0.05f);

			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void creardescuentoSobreTotalTest() {

		try {

			descService.descuentoService(consultas);
			descService.crearDescuento("Acme", desde, hasta, 0.08f);

			consultas.inTransactionExecute((em) -> {

				PromocionBancaria promo = em.getReference(PromocionBancaria.class, 1L);
				assertEquals(promo.marca(), "Acme");
				assertEquals(promo.descuento(), 0.08f);
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
