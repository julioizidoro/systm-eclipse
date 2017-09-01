package br.com.travelmate.dao;

import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Regravisto;

public class RegraVistoDao {
	
	public Regravisto consultar(String sql) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		Regravisto regra = null;
		if (q.getResultList().size()>0){
			regra =  (Regravisto) q.getResultList().get(0);
		}
		manager.close();
		return regra;
    }

}
