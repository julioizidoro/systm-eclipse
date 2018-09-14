package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Valoresaupair;

public class ValoresAupairDao {
    
    public Valoresaupair salvar(Valoresaupair valor) throws SQLException{
    	EntityManager manager;
       manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
       valor = manager.merge(valor);
       tx.commit();
       manager.close();
       return valor;
   }
   
   public Valoresaupair consultar(int idvalor) throws SQLException{
	   EntityManager manager;
      manager = ConectionFactory.getConnection();
      Query q = manager.createQuery("select v from Valoresaupair  v where v.idvaloresAupair=" + idvalor);
      Valoresaupair valor = null;
      if(q.getResultList().size()>0){
          valor =  (Valoresaupair) q.getResultList().get(0);
      }
      manager.close();
      return valor;
   }

   @SuppressWarnings("unchecked")
   public List<Valoresaupair> listar(String sql) throws SQLException{
	   EntityManager manager;
       manager = ConectionFactory.getConnection();
       Query q = manager.createQuery(sql);
       List<Valoresaupair> lista = q.getResultList();
       manager.close();
       return lista;
   }
   

}
