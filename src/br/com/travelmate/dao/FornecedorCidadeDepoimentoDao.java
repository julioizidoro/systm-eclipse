package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Fornecedorcidadedepoimento;  

public class FornecedorCidadeDepoimentoDao {

	public List<Fornecedorcidadedepoimento> lista(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Fornecedorcidadedepoimento> lista = q.getResultList();
		manager.close();
		return lista;
	}

	public Fornecedorcidadedepoimento consultar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		Fornecedorcidadedepoimento depoimento = null;
		if (q.getResultList().size() > 0) {
			depoimento = (Fornecedorcidadedepoimento) q.getResultList().get(0);
		}
		manager.close();
		return depoimento;
	}

	public Fornecedorcidadedepoimento salvar(Fornecedorcidadedepoimento depoimento) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		depoimento = manager.merge(depoimento);
		tx.commit(); 
		manager.close();
		return depoimento;
	}
	
	public void excluir(int iddepoimento) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Fornecedorcidadedepoimento depoimento = manager.find(Fornecedorcidadedepoimento.class, iddepoimento);
        manager.remove(depoimento);
        tx.commit();
        manager.close();
    }

}
