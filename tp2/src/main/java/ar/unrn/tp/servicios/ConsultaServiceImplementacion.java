package ar.unrn.tp.servicios;

import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import ar.unrn.tp.api.ConsultaService;

public class ConsultaServiceImplementacion implements ConsultaService {

	private String bd;

	private EntityManagerFactory emf;

	private void setUp() {
		// TODO Auto-generated method stub

		emf = Persistence
				// drop create for testing pursposes
				.createEntityManagerFactory(this.bd);
	}

	@Override
	public void setUp(String bd) {
		// TODO Auto-generated method stub
		this.bd = bd;

	}

	private void tearDown() {
		// TODO Auto-generated method stub

		emf.close();
	}

	@Override
	public void inTransactionExecute(Consumer<EntityManager> bloqueDeCodigo) {
		this.setUp();
		// TODO Auto-generated method stub
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			bloqueDeCodigo.accept(em);

			tx.commit();

		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
		this.tearDown();

	}

}
