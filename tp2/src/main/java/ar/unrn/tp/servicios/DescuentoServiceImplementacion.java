package ar.unrn.tp.servicios;

import java.time.LocalDateTime;

import ar.unrn.tp.api.ConsultaService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.modelo.Promocion;
import ar.unrn.tp.modelo.PromocionBancaria;
import ar.unrn.tp.modelo.PromocionMarca;

public class DescuentoServiceImplementacion implements DescuentoService {

	private ConsultaService consultas = new ConsultaServiceImplementacion();

	@Override
	public void descuentoService(ConsultaService consultas) {
		// TODO Auto-generated method stub

		this.consultas = consultas;

	}

	@Override
	public void crearDescuentoSobreTotal(String marcaTarjeta, LocalDateTime fechaDesde, LocalDateTime fechaHasta,
			float porcentaje) {

		consultas.inTransactionExecute((em) -> {
			Promocion promo;
			try {
				promo = new PromocionBancaria(fechaDesde, fechaHasta, marcaTarjeta, porcentaje);
				em.persist(promo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

	}

	@Override

	public void crearDescuento(String marcaProducto, LocalDateTime fechaDesde, LocalDateTime fechaHasta,
			float porcentaje) {

		consultas.inTransactionExecute((em) -> {
			Promocion promo;
			try {
				promo = new PromocionMarca(fechaDesde, fechaHasta, marcaProducto, porcentaje);
				em.persist(promo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

	}

}
