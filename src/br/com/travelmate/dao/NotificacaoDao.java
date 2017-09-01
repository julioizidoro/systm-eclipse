package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Notificacao;

public class NotificacaoDao {
	
	public Notificacao salvar(Notificacao notificacao)throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		notificacao = manager.merge(notificacao);
		tx.commit();
		return notificacao; 
	}
	
	public List<Notificacao> listar(String sql)throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		List<Notificacao> lista = null;
		if (q.getResultList().size()>0){
			lista =  q.getResultList();
		}
		return lista;
	}

}
