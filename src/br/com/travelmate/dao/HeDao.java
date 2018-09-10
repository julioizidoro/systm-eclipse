package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.He;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class HeDao {
    
    public He salvar(He he) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		he = manager.merge(he);
        tx.commit();
        manager.close();
        return he;
    }
    
    public List<He> listar(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<He> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public He consultar(int idhe) throws SQLException {
    	EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select h from He h where h.idhe=" + idhe);
        He he = null;
        if (q.getResultList().size()>0){
        	he = (He) q.getResultList().get(0);
        }
        manager.close();
        return he;
    } 
    
    
    public He consultarVenda(int idVenda) throws SQLException {
    	EntityManager manager;
       manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select h from He h where h.vendas.idvendas=" + idVenda);
        He he = null;
        if (q.getResultList().size() > 0) {
        	he =  (He) q.getResultList().get(0);
        }
        manager.close();
        return he;
    }
}
