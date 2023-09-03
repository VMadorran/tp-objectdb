package ar.unrn.tp.main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import ar.unrn.tp.modelo.Promocion;
import ar.unrn.tp.modelo.PromocionBancaria;
import ar.unrn.tp.modelo.PromocionMarca;
import ar.unrn.tp.modelo.ProveedorDeFecha;

public class MainSistema {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-objectdb");

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

//			Sistema sistema = new Sistema(null, null);
//
//			var tarjeta1 = new Tarjeta(123123L, "Visa");
//			var tarjeta2 = new Tarjeta(234345L, "Visa");
//			var tarjeta3 = new Tarjeta(987065L, "Mastercard");
//			var tarjeta4 = new Tarjeta(675345L, "Memecard");
//
//			sistema.crearUsuario("Jose", "Perez", new Dni(12345678L), new Email("jose@acdc.com"));
//			Cliente c1 = sistema.cliente(12345678L);
//			sistema.agregarMedioDePago(12345678L, tarjeta1);
//			sistema.agregarMedioDePago(12345678L, tarjeta2);
//
//			sistema.crearUsuario("Rosa", "Gomez", new Dni(12345890L), new Email("rosa@acdc.com"));
//			Cliente c2 = sistema.cliente(12345890L);
//			sistema.agregarMedioDePago(12345890L, tarjeta3);
//			sistema.agregarMedioDePago(12345890L, tarjeta4);
//
//			em.persist(c1);
//			em.persist(c2);

//			String descripcion, Long codigo, Categoria categoria, double precio, String marca

//			Categoria categoria = new Categoria();
//			categoria.agregarNombre("Indumentaria");
//			var cate1 = new Categoria();
//			cate1.agregarNombre("Remera");
//			cate1.agregarCodigo(1L);
//
//			var produc1 = new Producto("Remera Larga", 1L, cate1, 1500, "Acme");
//			var produc2 = new Producto("Remera Corta", 2L, cate1, 2000, "Nope");
//
//			var cate2 = new Categoria();
//			cate2.agregarCodigo(2L);
//			cate2.agregarNombre("Pantalon");
//			var produc3 = new Producto("Pantalon Rojo", 3L, cate2, 1500, "Acme");
//			var produc4 = new Producto("Pantalon Verde", 4L, cate2, 1500, "Nope");
//
//			var cate3 = new Categoria();
//			cate3.agregarNombre("Medias");
//			cate3.agregarCodigo(3L);
//			var produc5 = new Producto("Medias Rosas", 5L, cate3, 500, "Acme");
//			var produc6 = new Producto("Medias Blancas", 6L, cate3, 500, "Nope");
//
//			em.persist(produc1);
//			em.persist(produc2);
//			em.persist(produc3);
//			em.persist(produc4);
//			em.persist(produc5);
//			em.persist(produc6);

//			LocalDateTime inicio, LocalDateTime fin, String nombre, float descuento

			var fecha = new ProveedorDeFecha();
			Promocion promo1 = new PromocionMarca(fecha.now(), fecha.now().plusWeeks(1), "Acme", 0.05f);
			Promocion promo2 = new PromocionMarca(fecha.now().plusDays(10), fecha.now().plusMonths(1), "Nope", 0.05f);
			Promocion promo3 = new PromocionMarca(fecha.now().plusDays(15), fecha.now().plusMonths(1), "Marca Tres",
					0.05f);
			Promocion promo4 = new PromocionMarca(fecha.now().plusDays(12), fecha.now().plusMonths(2), "Marca", 0.05f);

			Promocion promo5 = new PromocionBancaria(fecha.now().minusWeeks(1), fecha.now().plusMonths(2), "Memecard",
					0.08f);
			em.persist(promo1);
			em.persist(promo2);
			em.persist(promo3);
			em.persist(promo4);
			em.persist(promo5);

			tx.commit();
			em.clear();

		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			if (em != null && em.isOpen())
				em.close();
			if (emf != null)
				emf.close();
		}
	}

}
