package ar.unrn.tp.api;

import java.time.LocalDateTime;

public interface DescuentoService {

	// validar que las fechas no se superpongan
	// Promocion bancaria

	void descuentoService(ConsultaService consultas);

	void crearDescuentoSobreTotal(String marcaTarjeta, LocalDateTime fechaDesde, LocalDateTime fechaHasta,
			float porcentaje);

	// validar que las fechas no se superpongan
	// Promocion de marca
	void crearDescuento(String marcaProducto, LocalDateTime fechaDesde, LocalDateTime fechaHasta, float porcentaje);

}
