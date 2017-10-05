package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Leadsituacao;

public class LeadSituacaoDao {
	
	
	public List<Leadsituacao> lista(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		List<Leadsituacao> lista = q.getResultList();

		return lista;
	}

	public Leadsituacao consultar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		Leadsituacao lead = null;
		if (q.getResultList().size() > 0) {
			lead = (Leadsituacao) q.getResultList().get(0);
		}
		return lead;
	}

	public Leadsituacao salvar(Leadsituacao lead) throws SQLException {
		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		lead = manager.merge(lead);
		tx.commit();

		return lead;
	}

	public void excluir(int idlead) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Leadsituacao lead = manager.find(Leadsituacao.class, idlead);
        manager.remove(lead);
        tx.commit();
    }

}
