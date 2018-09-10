package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Alteracaofinanceiroparcelas;

public class AlteracaofinanceiroparcelasDao {
	
	public Alteracaofinanceiroparcelas salvar(Alteracaofinanceiroparcelas alteracaofinanceiroparcelas) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		manager.getTransaction().begin();
		alteracaofinanceiroparcelas = manager.merge(alteracaofinanceiroparcelas);
		manager.getTransaction().commit();
		manager.close();
		return alteracaofinanceiroparcelas;
	}
	
	
	public List<Alteracaofinanceiroparcelas> listar(String sql)throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Alteracaofinanceiroparcelas> lista = null;
		if (q.getResultList().size()>0){
			lista =  q.getResultList();
		}
		manager.close();
		return lista;
	}
	
	

}
