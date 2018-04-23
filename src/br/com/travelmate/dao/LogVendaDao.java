package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Logvenda; 

public class LogVendaDao {
	
	
	public Logvenda salvar(Logvenda logVenda) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		logVenda = manager.merge(logVenda);
		tx.commit();
		manager.close();
		return logVenda;
	}
	
	
	public List<Logvenda> listar(String sql)throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Logvenda> lista = null;
		if (q.getResultList().size()>0){
			lista =  q.getResultList();
		}
		manager.close();
		return lista;
	}
	
	public Logvenda consultar(int idVenda) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select l from Logvenda l where l.vendas.idvendas=" + idVenda);
        Logvenda logvenda = null;
        if (q.getResultList().size() > 0) {
        	logvenda =  (Logvenda) q.getResultList().get(0);
        }
        manager.close();
        return logvenda;
    }

}
