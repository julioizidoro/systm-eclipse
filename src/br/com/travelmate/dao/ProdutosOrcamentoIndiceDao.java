package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Produtosorcamentoindice;

public class ProdutosOrcamentoIndiceDao {

	 public Produtosorcamentoindice salvar(Produtosorcamentoindice produtosorcamentogrupo) throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getInstance();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
			produtosorcamentogrupo = manager.merge(produtosorcamentogrupo);
	        tx.commit();
	        
	        return produtosorcamentogrupo;
	    }

	    @SuppressWarnings("unchecked")
	    public List<Produtosorcamentoindice> listar(String sql)throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getInstance();
	        Query q = manager.createQuery(sql);
	        List<Produtosorcamentoindice> lista = q.getResultList();
	        return lista;
	    }
	    
	    public void excluir(int idProdutoOrcamentoIndice) throws SQLException {
	    	EntityManager manager;
	    	manager = ConectionFactory.getInstance();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
	        Query q = manager.createQuery("Select c from Produtosorcamentoindice c where c.idProdutosorcamentoindice=" + idProdutoOrcamentoIndice);
	        if (q.getResultList().size()>0){
	        	Produtosorcamentoindice produtosorcamentoindice = (Produtosorcamentoindice) q.getResultList().get(0);
	            manager.remove(produtosorcamentoindice);
	        }
	        tx.commit();
	        
	    }
}
