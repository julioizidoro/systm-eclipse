package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Orcamentovoluntariadodesconto;

public class OrcamentoVoluntariadoDescontoDao {

	
	 public Orcamentovoluntariadodesconto salvar(Orcamentovoluntariadodesconto orcamentovoluntariadodesconto) throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getInstance();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
			orcamentovoluntariadodesconto = manager.merge(orcamentovoluntariadodesconto);
	        tx.commit();
	        
	        return orcamentovoluntariadodesconto;
	    }
	    
	    public List<Orcamentovoluntariadodesconto> listar(String sql)throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getInstance();
	        Query q = manager.createQuery(sql);
	        List<Orcamentovoluntariadodesconto> lista = q.getResultList();
	        return lista;
	    }
	    
	    public void excluir(int idOrcamento) throws SQLException {
	    	EntityManager manager;
	    	manager = ConectionFactory.getInstance();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
	        Query q = manager.createQuery("Select c from Orcamentovoluntariadodesconto c where c.idorcamentovoluntariadodesconto=" + idOrcamento);
	        if (q.getResultList().size()>0){
	        	Orcamentovoluntariadodesconto orcamentovoluntariadodesconto = (Orcamentovoluntariadodesconto) q.getResultList().get(0);
	            manager.remove(orcamentovoluntariadodesconto);
	        }
	        tx.commit();
	        
	    }
}
