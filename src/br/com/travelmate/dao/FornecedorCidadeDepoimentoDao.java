package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Fornecedorcidadedepoimento;  

public class FornecedorCidadeDepoimentoDao {

    @SuppressWarnings("unchecked")
	public List<Fornecedorcidadedepoimento> lista(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		List<Fornecedorcidadedepoimento> lista = q.getResultList();

		return lista;
	}

	public Fornecedorcidadedepoimento consultar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		Fornecedorcidadedepoimento depoimento = null;
		if (q.getResultList().size() > 0) {
			depoimento = (Fornecedorcidadedepoimento) q.getResultList().get(0);
		}
		return depoimento;
	}

	public Fornecedorcidadedepoimento salvar(Fornecedorcidadedepoimento depoimento) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		depoimento = manager.merge(depoimento);
		tx.commit(); 
		return depoimento;
	}
	
	public void excluir(int iddepoimento) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Fornecedorcidadedepoimento depoimento = manager.find(Fornecedorcidadedepoimento.class, iddepoimento);
        manager.remove(depoimento);
        tx.commit();
    }

}
