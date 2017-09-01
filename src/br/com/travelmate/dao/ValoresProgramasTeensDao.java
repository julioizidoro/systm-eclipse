package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Valoresprogramasteens;

public class ValoresProgramasTeensDao {
    
    public Valoresprogramasteens salvar(Valoresprogramasteens valor) throws SQLException{
    	EntityManager manager;
       manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
       valor = manager.merge(valor);
       tx.commit();
       manager.close();
       return valor;
   }
   
   public Valoresprogramasteens consultar(int idvalor) throws SQLException{
	   EntityManager manager;
      manager = ConectionFactory.getConnection();
      Query q = manager.createQuery("select v from Valoresprogramasteens  v where v.idvaloresprogramasteens=" + idvalor);
      Valoresprogramasteens valor = null;
      if(q.getResultList().size()>0){
          valor =  (Valoresprogramasteens) q.getResultList().get(0);
      }
      manager.close();
      return valor;
   }
   
   public List<Valoresprogramasteens> listar(String sql) throws SQLException{
	   EntityManager manager;
       manager = ConectionFactory.getConnection();
       Query q = manager.createQuery(sql);
       List<Valoresprogramasteens> lista = q.getResultList();
       manager.close();
       return lista;
   }
   

}
