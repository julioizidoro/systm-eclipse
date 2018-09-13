package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Alteracaofinanceiro;



public class AlteracaofinanceiroDao {
	
	public Alteracaofinanceiro salvar(Alteracaofinanceiro alteracaofinanceiro) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		alteracaofinanceiro = manager.merge(alteracaofinanceiro);
		tx.commit();
		manager.close();
		return alteracaofinanceiro;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Alteracaofinanceiro> listar(String sql)throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Alteracaofinanceiro> lista = null;
		if (q.getResultList().size()>0){
			lista =  q.getResultList();
		}
		manager.close();
		return lista;
	}

}
