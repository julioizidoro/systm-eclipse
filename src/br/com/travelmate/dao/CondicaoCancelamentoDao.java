package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Condicaocancelamento;

public class CondicaoCancelamentoDao {
	
	public Condicaocancelamento salvar(Condicaocancelamento condicaocancelamento)throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		condicaocancelamento= manager.merge(condicaocancelamento);
		tx.commit();
		manager.close();
		return condicaocancelamento;
	}
	
	public List<Condicaocancelamento> listar(String sql)throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Condicaocancelamento> lista = null;
		if (q.getResultList().size()>0){
			lista =  q.getResultList();
		}
		manager.close();
		return lista;
	}

	public Condicaocancelamento consultar(int idCondicaocancelamento) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
        Condicaocancelamento condicaocancelamento = manager.find(Condicaocancelamento.class, idCondicaocancelamento);
        manager.close();
        return condicaocancelamento;
    }
}
