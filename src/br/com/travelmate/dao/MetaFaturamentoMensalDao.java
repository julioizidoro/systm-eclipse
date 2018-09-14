package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Metasfaturamentomensal;

public class MetaFaturamentoMensalDao {
	
	public Metasfaturamentomensal salvar(Metasfaturamentomensal meta) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        meta = manager.merge(meta);
        tx.commit();
        return meta;
    }

	@SuppressWarnings("unchecked")
	public List<Metasfaturamentomensal> listar(String sql)throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getInstance();
       Query q = manager.createQuery(sql);
       List<Metasfaturamentomensal> lista = null;
       if (q.getResultList().size()>0){
           lista =  q.getResultList();
       }
       return lista;
   }
	
	public Metasfaturamentomensal getMeta(String sql)throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getInstance();
       Query q = manager.createQuery(sql);
       Metasfaturamentomensal meta = null;
       if (q.getResultList().size()>0){
           meta=  (Metasfaturamentomensal) q.getResultList().get(0);
       }
       return meta;
   }

}
