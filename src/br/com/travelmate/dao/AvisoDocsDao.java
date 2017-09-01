package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Avisodocs;

public class AvisoDocsDao{
	
	public List<Avisodocs> listar(String sql) throws SQLException{
		EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Avisodocs> lista = q.getResultList();
        return lista;
    }
	
	public Avisodocs salvar(Avisodocs aviso) throws SQLException{
		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        aviso = manager.merge(aviso);
        tx.commit();
        return aviso;
    }
    
    public void excluir(Avisodocs aviso) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        aviso = manager.find(Avisodocs.class, aviso.getIdavisodocs());
        manager.remove(aviso);
        tx.commit();
    }
    
    
}
