package br.com.travelmate.dao;
import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Cartaocreditolancamento;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class CartaoCreditoLancamentoDao {
    
    public List<Cartaocreditolancamento> listar() throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery("select c from Cartaocreditolancamento c order by c.descricao");
        List<Cartaocreditolancamento> lista = q.getResultList();
        return lista;
    }
    
    
    public List<Cartaocreditolancamento> listar(String sql) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Cartaocreditolancamento> lista = q.getResultList();
        return lista;
    }
    
    public Cartaocreditolancamento consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        Cartaocreditolancamento cartaocredito = null;
        if (q.getResultList().size()>0){
        	cartaocredito = (Cartaocreditolancamento) q.getResultList().get(0);
        }
        return cartaocredito;
    }
    
    
    
    public Cartaocreditolancamento salvar(Cartaocreditolancamento cartaocredito) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		cartaocredito = manager.merge(cartaocredito);
        tx.commit();
        return cartaocredito;
    }
    
    
    public void excluir(int idCartaoCreditoLancamento) throws SQLException  {
		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Cartaocreditolancamento cartaocreditolancamento = manager.find(Cartaocreditolancamento.class, idCartaoCreditoLancamento);
        manager.remove(cartaocreditolancamento);  
        tx.commit();
        manager.close();
    }
    
}
