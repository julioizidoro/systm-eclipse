package br.com.travelmate.dao;
 
import java.sql.SQLException;
import java.util.List;
 
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Acessounidade; 

public class AcessoUnidadeDao {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	 
	public Acessounidade salvar(Acessounidade acessounidade) throws SQLException{
		EntityManager manager = ConectionFactory.getConnection();
		//EntityTransaction tx = manager.getTransaction();
		//tx.begin();
		manager.getTransaction().begin();
		acessounidade = manager.merge(acessounidade);
       // tx.commit();
		manager.getTransaction().commit();
        manager.close();
        return acessounidade; 
    } 
    
    public Acessounidade consultar(String sql)  throws SQLException  {
		EntityManager manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql); 
        Acessounidade acessounidade = null; 
        if (q.getResultList().size() > 0) {
        		acessounidade =  (Acessounidade) q.getResultList().get(0);
        } 
        manager.close();
        return acessounidade;
    }
     
    public void excluir(int idacessounidade) throws SQLException  {
		EntityManager manager = ConectionFactory.getConnection();
//		EntityTransaction tx = manager.getTransaction();
//		tx.begin();
		manager.getTransaction().begin();
		Acessounidade acessounidade = manager.find(Acessounidade.class, idacessounidade);
        manager.remove(acessounidade);  
       // tx.commit();
        manager.getTransaction().commit();
        manager.close();
    }
     
    public List<Acessounidade> lista(String sql) throws SQLException {
		EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Acessounidade> lista = q.getResultList();
        manager.close();
        return lista; 
    }

}
