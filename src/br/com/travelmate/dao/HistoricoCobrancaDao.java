package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Historicocobranca;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class HistoricoCobrancaDao {
    
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
    
    
    public List<Historicocobranca> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Historicocobranca> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public Historicocobranca consultar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Historicocobranca historico= null;
        if (q.getResultList().size()>0){
            historico =  (Historicocobranca) q.getSingleResult();
        } 
        manager.close();
        return historico;
    }
}
