package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Crmcobrancahistorico;

public class CrmCobrancaHistoricoDao {
	
	
	public Crmcobrancahistorico salvar(Crmcobrancahistorico crmcobranca) throws SQLException{
		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		crmcobranca = manager.merge(crmcobranca);
        tx.commit();
        manager.close();
        return crmcobranca;
    }
    
   
    
    public Crmcobrancahistorico consultar(int idcrm) throws SQLException {
    		EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery("SELECT c FROM Crmcobrancahistorico c WHERE c.idcrmcobrancahistorico=" + idcrm);
        Crmcobrancahistorico crmcobranca = null; 
        if (q.getResultList().size() > 0) {
        	crmcobranca =  (Crmcobrancahistorico) q.getResultList().get(0);
        } 
        manager.close();
        return crmcobranca;
    }
    
    public void excluir(int idcrm) throws SQLException{
    		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Crmcobrancahistorico crmcobranca = manager.find(Crmcobrancahistorico.class, idcrm);
        manager.remove(crmcobranca);
        tx.commit();
        manager.close();
    }
    
    
    @SuppressWarnings("unchecked")
	public List<Crmcobrancahistorico> lista(String sql) throws SQLException{
    		EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Crmcobrancahistorico> lista = q.getResultList();
        manager.close();
        return lista;
    }

}
