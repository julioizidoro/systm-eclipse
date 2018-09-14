package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Metafaturamentoanual;



public class MetaFaturamentoAnualDao {
	
	public Metafaturamentoanual salvar(Metafaturamentoanual meta) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        meta = manager.merge(meta);
        tx.commit();
        return meta;
    }

	@SuppressWarnings("unchecked")
	public List<Metafaturamentoanual> listar(String sql)throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getInstance();
       Query q = manager.createQuery(sql);
       List<Metafaturamentoanual> lista = null;
       if (q.getResultList().size()>0){
           lista =  q.getResultList();
       }
       return lista;
   }
	
	public Metafaturamentoanual getMeta(String sql)throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getInstance();
       Query q = manager.createQuery(sql);
       Metafaturamentoanual meta = null;
       if (q.getResultList().size()>0){
           meta=  (Metafaturamentoanual) q.getResultList().get(0);
       }
       return meta;
   }
	
}
