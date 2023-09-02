package ar.unrn.tp.main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Dni;
import ar.unrn.tp.modelo.Email;

public class MainSistema {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-objectdb");

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

//			Dni dni = new Dni(123123L);
//			Email email = new Email("angus@acdc.com");

//			Cliente cliente = new Cliente(1L, "Jose", "Perez", dni, email);

//			em.persist(cliente);

//			tx.commit();
//			em.clear();

			Cliente c = em.find(Cliente.class, 1L);
			System.out.println(c);

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
