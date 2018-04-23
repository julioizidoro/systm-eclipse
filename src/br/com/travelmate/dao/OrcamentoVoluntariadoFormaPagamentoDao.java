package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Orcamentovoluntariadoformapagamento; 

public class OrcamentoVoluntariadoFormaPagamentoDao {

	
	public Orcamentovoluntariadoformapagamento salvar(Orcamentovoluntariadoformapagamento orcamentovoluntariadoformapagamento) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		orcamentovoluntariadoformapagamento = manager.merge(orcamentovoluntariadoformapagamento);
        tx.commit(); 
        manager.close();
        return orcamentovoluntariadoformapagamento;
    }
    
    public List<Orcamentovoluntariadoformapagamento> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Orcamentovoluntariadoformapagamento> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public void excluir(int idOrcamento) throws SQLException {
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select c from Orcamentovoluntariadoformapagamento c where c.idorcamentovoluntariadoformapagamento=" + idOrcamento);
        if (q.getResultList().size()>0){
        	Orcamentovoluntariadoformapagamento orcamentovoluntariadoformapagamento = (Orcamentovoluntariadoformapagamento) q.getResultList().get(0);
            manager.remove(orcamentovoluntariadoformapagamento);
        }
        manager.close();
        tx.commit();
        
    }
}
