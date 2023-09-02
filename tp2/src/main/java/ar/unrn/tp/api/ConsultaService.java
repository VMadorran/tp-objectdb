package ar.unrn.tp.api;

import java.util.function.Consumer;

import javax.persistence.EntityManager;

public interface ConsultaService {

	public void setUp(String bd);

	public void inTransactionExecute(Consumer<EntityManager> bloqueDeCodigo);

}
