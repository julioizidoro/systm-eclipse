package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Fornecedorapplication;

@SuppressWarnings("unchecked")
public class FornecedorApplicationDao {
	
	
	public Fornecedorapplication salvar(Fornecedorapplication Fornecedorapplication) throws SQLException{
		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Fornecedorapplication = manager.merge(Fornecedorapplication);
        tx.commit();
        manager.close();
        return Fornecedorapplication;
    }
    
	public void excluir(int idFornecedorApplication) throws SQLException{
		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Fornecedorapplication Fornecedorapplication = manager.find(Fornecedorapplication.class, idFornecedorApplication);
        manager.remove(Fornecedorapplication);
        tx.commit();
        manager.close();
    }
    
	public List<Fornecedorapplication> listar(String sql) throws SQLException{
		EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Fornecedorapplication> lista = q.getResultList();
        manager.close();
        return lista;
    } 
    
    public Fornecedorapplication consultar(String sql) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Fornecedorapplication fornecedorapplication = null;
        if (q.getResultList().size() > 0) {
        	fornecedorapplication = (Fornecedorapplication) q.getResultList().get(0);
        }
        manager.close();
        return fornecedorapplication;
    }

}
