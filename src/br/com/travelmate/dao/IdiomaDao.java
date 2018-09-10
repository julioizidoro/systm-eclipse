package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Idioma;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class IdiomaDao {
    
    
    public Idioma salvar(Idioma idioma) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        idioma = manager.merge(idioma);
        tx.commit();
        manager.close();
        return idioma;
    }
    
    
    public List<Idioma> listar(String sql)throws SQLException{
    	EntityManager manager;
         manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Idioma> lista = null;
        if (q.getResultList().size()>0){
            lista =  q.getResultList();
        }
        manager.close();
        return lista;
    }
    
    public Idioma consultar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		Idioma idioma = null;
		if (q.getResultList().size() > 0) {
			idioma = (Idioma) q.getResultList().get(0);
		}
		return idioma;
	}
}
