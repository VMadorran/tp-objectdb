package ar.unrn.tp.jpa.servicios;

import org.junit.jupiter.api.Test;

import ar.unrn.tp.api.ConsultaService;
import ar.unrn.tp.modelo.Fecha;
import ar.unrn.tp.modelo.Promocion;
import ar.unrn.tp.modelo.PromocionMarca;
import ar.unrn.tp.modelo.ProveedorDeFecha;
import ar.unrn.tp.servicios.ConsultaServiceImplementacion;

public class DescuentoServiceTest {
	private ConsultaService consultas = new ConsultaServiceImplementacion();
	private Fecha fecha = new ProveedorDeFecha();

	@Test
	public void crearDescuentoTest() {

		var desde = fecha.now().plusDays(5);
		var hasta = fecha.now().plusWeeks(2);
		try {
			Promocion promocionEsperada = new PromocionMarca(desde, hasta, "Acme", 0.05f);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		consultas.inTransactionExecute((em) -> {
			// LocalDateTime inicio, LocalDateTime fin, String nombre, float descuento

		});
	}

	@Test
	public void creardescuentoSobreTotalTest() {
		consultas.inTransactionExecute((em) -> {
		});

	}
}
/*
 * public class ClienteService {
 * 
 * private EntityManagerFactory emf
 * 
 * public ClienteService(EntityManagerFactory emf) {
 * 
 * this.emf = emf; }
 * 
 * public void nuevoCliente(...) { EntityManager em = emf.createEntityManager();
 * EntityTransaction tx = em.getTransaction(); try {...} ... ... } }
 */