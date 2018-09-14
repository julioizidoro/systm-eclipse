package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Produtostrainee;

public class ProdutosTraineeDao {
	
	public Produtostrainee salvar(Produtostrainee produto) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
        manager.getTransaction().begin();
        produto= manager.merge(produto);
        tx.commit();
        manager.close();
        return produto;
    }
	
	@SuppressWarnings("unchecked")
	public List<Produtostrainee> listar(String sql) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Produtostrainee> lista = q.getResultList();
        manager.close();
        return lista;
    }
	
	public Produtostrainee consultar(String sql) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Produtostrainee produtostrainee = null;
        if (q.getResultList().size()>0){
        	produtostrainee =  (Produtostrainee) q.getResultList().get(0);
        }
        manager.close();
        return produtostrainee;
    }
}
