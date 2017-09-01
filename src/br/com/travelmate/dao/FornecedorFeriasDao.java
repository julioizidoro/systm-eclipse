package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Fornecedorferias;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila Rodrigues
 */
public class FornecedorFeriasDao {
    
    public Fornecedorferias salvar(Fornecedorferias fornecedorferias) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        fornecedorferias = manager.merge(fornecedorferias);
        tx.commit();
        manager.close();
        return fornecedorferias;
    }
    
    
    public List<Fornecedorferias> listar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Fornecedorferias> lista = null;
        if (q.getResultList().size()>0){
            lista =  q.getResultList();
        }
        manager.close();
        return lista;
    }
    
}
