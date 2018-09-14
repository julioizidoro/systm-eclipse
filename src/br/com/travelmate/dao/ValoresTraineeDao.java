package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Valorestrainee;

public class ValoresTraineeDao {
    public Valorestrainee salvar(Valorestrainee valor) throws SQLException{
    	EntityManager manager;
       manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
       valor = manager.merge(valor);
       tx.commit();
       manager.close();
       return valor;
   }
   
   public Valorestrainee consultar(int idvalor) throws SQLException{
	   EntityManager manager;
      manager = ConectionFactory.getConnection();
      Query q = manager.createQuery("select v from Valorestrainee  v where v.idvalorestrainee=" + idvalor);
      Valorestrainee valor = null;
      if(q.getResultList().size()>0){
          valor =  (Valorestrainee) q.getResultList().get(0);
      }
      manager.close();
      return valor;
   }

   @SuppressWarnings("unchecked")
   public List<Valorestrainee> listar(String sql) throws SQLException{
	   EntityManager manager;
       manager = ConectionFactory.getConnection();
       Query q = manager.createQuery(sql);
       List<Valorestrainee> lista = q.getResultList();
       manager.close();
       return lista;
   }
   

}
