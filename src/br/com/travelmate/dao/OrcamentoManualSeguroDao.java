package br.com.travelmate.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Orcamentomanualseguro;

public class OrcamentoManualSeguroDao {
    
    public Orcamentomanualseguro salvar(Orcamentomanualseguro  orcamentomanualseguro) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        orcamentomanualseguro = manager.merge(orcamentomanualseguro);
        tx.commit();
        manager.close();
        return orcamentomanualseguro;
    }
    
    public Orcamentomanualseguro consultar(int idOrcamento) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("SELECT o FROM Orcamentomanualseguro o where o.orcamentocurso.idorcamentoCurso=" + idOrcamento);
        Orcamentomanualseguro seguro = null;
        if (q.getResultList().size() > 0) {
            seguro =  (Orcamentomanualseguro) q.getResultList().get(0);
        } 
        manager.close();
        return seguro;
    }

}
