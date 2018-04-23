package br.com.travelmate.dao;
 
import java.sql.SQLException;
import java.util.List;
 
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Leadresponsavel;  

public class LeadResponsavelDao { 
	 
	
	
	
	public Leadresponsavel salvar(Leadresponsavel leadresponsavel) throws SQLException{
		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		leadresponsavel = manager.merge(leadresponsavel);
        tx.commit();
        manager.close();
        return leadresponsavel; 
    } 
    
    public Leadresponsavel consultar(String sql)  throws SQLException  {
		EntityManager manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql); 
		Leadresponsavel leadresponsavel = null; 
        if (q.getResultList().size() > 0) {
        		leadresponsavel =  (Leadresponsavel) q.getResultList().get(0);
        } 
        manager.close();
        return leadresponsavel;
    }
     
    public void excluir(int idleadresponsavel) throws SQLException  {
		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Leadresponsavel leadresponsavel = manager.find(Leadresponsavel.class, idleadresponsavel);
        manager.remove(leadresponsavel);  
        tx.commit();
        manager.close();
    }
     
    public List<Leadresponsavel> lista(String sql) throws SQLException {
		EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Leadresponsavel> lista = q.getResultList();
        manager.close();
        return lista; 
    }
    
    public List<Leadresponsavel> lista(int idunidade) throws SQLException {
		EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("SELECT l FROM Leadresponsavel l WHERE l.unidadenegocio.idunidadeNegocio="+idunidade + " and l.usuario.situacao='Ativo' "
        		+ " ORDER BY l.usuario.nome");
        List<Leadresponsavel> lista = q.getResultList();
        manager.close();
        return lista; 
    }

}
