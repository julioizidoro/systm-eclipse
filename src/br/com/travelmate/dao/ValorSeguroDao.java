package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Valoresseguro;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class ValorSeguroDao {
    
     public Valoresseguro salvar(Valoresseguro valor) throws SQLException{
    	 EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        valor = manager.merge(valor);
        tx.commit();
        manager.close();
        return valor;
    }
    
    public Valoresseguro consultar(int idvalor) throws SQLException{
    	EntityManager manager;
       manager = ConectionFactory.getConnection();
       Query q = manager.createQuery("select v from Valoresseguro  v where v.idvaloresseguro=" + idvalor);
       Valoresseguro valor = null;
       if(q.getResultList().size()>0){
           valor =  (Valoresseguro) q.getResultList().get(0);
       }
       manager.close();
       return valor;
    }
    
    
    public Valoresseguro consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Valoresseguro valor = null;
        if(q.getResultList().size()>0){
            valor =  (Valoresseguro) q.getResultList().get(0);
        }
        manager.close();
        return valor;
     }
    
    public List<Valoresseguro> listar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Valoresseguro> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public List<Valoresseguro> listar(int idFornecedor, String situacao) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select v from Valoresseguro  v where v.fornecedorcidade.idfornecedorcidade=" + idFornecedor + 
        		" and v.situacao='" + situacao + "'");
        List<Valoresseguro> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    
}
