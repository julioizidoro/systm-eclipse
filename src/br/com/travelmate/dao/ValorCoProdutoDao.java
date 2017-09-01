package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Valorcoprodutos;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila Rodrigues
 */
public class ValorCoProdutoDao {
    
    public Valorcoprodutos salvar(Valorcoprodutos valorcoproduto) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        valorcoproduto = manager.merge(valorcoproduto);
        tx.commit();
        manager.close();
        return valorcoproduto;
    }
    
    
    public List<Valorcoprodutos> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Valorcoprodutos> lista = null;
        if (q.getResultList().size()>0){
            lista =  q.getResultList();
        }
        manager.close();
        return lista;
    }
    
    public void excluir(int idValorCoProduto) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Valorcoprodutos valorcoprodutos = manager.find(Valorcoprodutos.class, idValorCoProduto);
        manager.remove(valorcoprodutos);
        tx.commit();
        manager.close();
    }
    
    public Valorcoprodutos consultar(int idValorCoProduto) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("Select v From Valorcoprodutos v where v.idvalorcoprodutos=" + idValorCoProduto);
        Valorcoprodutos valorcoprodutos =null;
        if (q.getResultList().size()>0){
        	valorcoprodutos = (Valorcoprodutos) q.getSingleResult();
        }
        manager.close();
        return valorcoprodutos;
    } 
}
