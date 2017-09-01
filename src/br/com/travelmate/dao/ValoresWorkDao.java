package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Valoreswork;

public class ValoresWorkDao {
    
    public Valoreswork salvar(Valoreswork valor) throws SQLException{
    	EntityManager manager;
       manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
       valor = manager.merge(valor);
       tx.commit();
       manager.close();
       return valor;
   }
   
   public Valoreswork consultar(int idvalor) throws SQLException{
	   EntityManager manager;
      manager = ConectionFactory.getConnection();
      Query q = manager.createQuery("select v from Valoreswork  v where v.idvaloresWork=" + idvalor);
      Valoreswork valor = null;
      if(q.getResultList().size()>0){
          valor =  (Valoreswork) q.getResultList().get(0);
      }
      manager.close();
      return valor;
   }
   
   public List<Valoreswork> listar(String sql) throws SQLException{
	   EntityManager manager;
       manager = ConectionFactory.getConnection();
       Query q = manager.createQuery(sql);
       List<Valoreswork> lista = q.getResultList();
       manager.close();
       return lista;
   }
   

}
