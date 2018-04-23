package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Orcamentovoluntariadoseguro;

public class OrcamentoVoluntariadoSeguroDao {

	
	public Orcamentovoluntariadoseguro salvar(Orcamentovoluntariadoseguro orcamentovoluntariadoseguro) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		orcamentovoluntariadoseguro = manager.merge(orcamentovoluntariadoseguro);
        tx.commit();
        manager.close();
        return orcamentovoluntariadoseguro;
    }
    
    public List<Orcamentovoluntariadoseguro> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Orcamentovoluntariadoseguro> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public void excluir(int idOrcamento) throws SQLException {
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select c from Orcamentovoluntariadoseguro c where c.idorcamentovoluntariadoseguro=" + idOrcamento);
        if (q.getResultList().size()>0){
        	Orcamentovoluntariadoseguro orcamentovoluntariadoseguro = (Orcamentovoluntariadoseguro) q.getResultList().get(0);
            manager.remove(orcamentovoluntariadoseguro);
        }
        manager.close();
        tx.commit();
        
    }
}
