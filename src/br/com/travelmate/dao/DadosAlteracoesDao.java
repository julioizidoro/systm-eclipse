package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Dadosalteracoes;

public class DadosAlteracoesDao {
	
	public Dadosalteracoes salvar(Dadosalteracoes dados) throws SQLException{
		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		dados = manager.merge(dados);
		tx.commit();
		manager.close();
		return dados;
	}
	    
	@SuppressWarnings("unchecked")
	public List<Dadosalteracoes> listar(String sql)throws SQLException{
		EntityManager manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Dadosalteracoes> lista = null;
		if (q.getResultList().size()>0){
			lista =  q.getResultList();
		}
		manager.close();
		return lista;
	}

}
