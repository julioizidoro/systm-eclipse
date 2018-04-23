package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Cobranca;
import br.com.travelmate.model.Historicocobranca;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
/**
 *
 * @author Wolverine
 */
public class CobrancaDao {
    
    public Cobranca salvar(Cobranca cobranca)throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        cobranca = manager.merge(cobranca);
        tx.commit();
        manager.close();
        return cobranca;
    }
    
    public Historicocobranca salvar(Historicocobranca historicocobranca) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        historicocobranca = manager.merge(historicocobranca);
        tx.commit();
        manager.close();
        return historicocobranca;
    }
    
    public Cobranca consultar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Cobranca cobranca= null;
        if (q.getResultList().size()>0){
            cobranca =  (Cobranca) q.getSingleResult();
        } 
        manager.close();
        return cobranca;
    }
    
    public List<Cobranca> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Cobranca> listaCobranca= null;
        if (q.getResultList().size()>0){
            listaCobranca = q.getResultList();
        } 
        manager.close();
        return listaCobranca;
    }
    
    
    
}
