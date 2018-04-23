package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Produtosorcamentogrupo;

public class ProdutosOrcamentoGrupoDao {
	
	
	 public Produtosorcamentogrupo salvar(Produtosorcamentogrupo produtosorcamentogrupo) throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getConnection();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
			produtosorcamentogrupo = manager.merge(produtosorcamentogrupo);
	        tx.commit();
	        manager.close();
	        return produtosorcamentogrupo;
	    }
	    
	    public List<Produtosorcamentogrupo> listar(String sql)throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getConnection();
	        Query q = manager.createQuery(sql);
	        List<Produtosorcamentogrupo> lista = q.getResultList();
	        manager.close();
	        return lista;
	    }
	    
	    public void excluir(int idProdutoOrcamentoGrupo) throws SQLException {
	    	EntityManager manager;
	    	manager = ConectionFactory.getConnection();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
	        Query q = manager.createQuery("Select c from Produtosorcamentogrupo c where c.idprodutosorcamentogrupo=" + idProdutoOrcamentoGrupo);
	        if (q.getResultList().size()>0){
	        	Produtosorcamentogrupo produtosorcamentogrupo = (Produtosorcamentogrupo) q.getResultList().get(0);
	            manager.remove(produtosorcamentogrupo);
	        }
	        tx.commit();
	        manager.close();
	    }

}
