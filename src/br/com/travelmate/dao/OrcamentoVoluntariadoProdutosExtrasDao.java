package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Orcamentovoluntariadoprodutosextras;
import br.com.travelmate.model.Orcamentovoluntariadoseguro;

public class OrcamentoVoluntariadoProdutosExtrasDao {

	
	public Orcamentovoluntariadoprodutosextras salvar(Orcamentovoluntariadoprodutosextras orcamentovoluntariadoprodutosextras) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		orcamentovoluntariadoprodutosextras = manager.merge(orcamentovoluntariadoprodutosextras);
        tx.commit();
        
        return orcamentovoluntariadoprodutosextras;
    }
    
    public List<Orcamentovoluntariadoprodutosextras> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Orcamentovoluntariadoprodutosextras> lista = q.getResultList();
        return lista;
    }
    
    public void excluir(int idOrcamento) throws SQLException {
    	EntityManager manager;
    	manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select c from Orcamentovoluntariadoprodutosextras c where c.idorcamentovoluntariadoprodutosextras=" + idOrcamento);
        if (q.getResultList().size()>0){
        	Orcamentovoluntariadoprodutosextras orcamentovoluntariadoprodutosextras = (Orcamentovoluntariadoprodutosextras) q.getResultList().get(0);
            manager.remove(orcamentovoluntariadoprodutosextras);
        }
        tx.commit();
        
    }
}
