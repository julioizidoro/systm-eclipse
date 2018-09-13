package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Fornecedorcidadeguia;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
/**
 *
 * @author Wolverine
 */
public class FornecedorCidadeGuiaDao {
    
    @SuppressWarnings("unchecked")
	public List<Fornecedorcidadeguia> listar(String sql)throws SQLException{
    	EntityManager manager; 
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Fornecedorcidadeguia> lista = null;
		if (q.getResultList().size()>0){
			lista =  q.getResultList();
		}
		manager.close();
		return lista;
	}
    
}
