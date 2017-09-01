package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Parametrosprodutos;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class ParametrosProdutosDao {
    
    public Parametrosprodutos salvar(Parametrosprodutos parametrosprodutos) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        parametrosprodutos = manager.merge(parametrosprodutos);
        tx.commit();
        manager.close();
        return parametrosprodutos;
    }
    
    public Parametrosprodutos consultar() throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Parametrosprodutos  p");
        Parametrosprodutos paramentros = null;
        if (q.getResultList().size()>0){
            paramentros =  (Parametrosprodutos) q.getSingleResult();
        }
        manager.close();
        return paramentros;
    }
}
