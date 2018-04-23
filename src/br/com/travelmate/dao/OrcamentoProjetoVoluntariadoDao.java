package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Orcamentoprojetovoluntariado;

public class OrcamentoProjetoVoluntariadoDao {

	
	 public Orcamentoprojetovoluntariado salvar(Orcamentoprojetovoluntariado orcamentoprojetovoluntariado) throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getConnection();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
			orcamentoprojetovoluntariado = manager.merge(orcamentoprojetovoluntariado);
	        tx.commit();
	        manager.close();
	        return orcamentoprojetovoluntariado;
	    }
	    
	    public List<Orcamentoprojetovoluntariado> listar(String sql)throws SQLException{
	    	EntityManager manager;
	        manager = ConectionFactory.getConnection();
	        Query q = manager.createQuery(sql);
	        List<Orcamentoprojetovoluntariado> lista = q.getResultList();
	        manager.close();
	        return lista;
	    }
	    
	    public void excluir(int idOrcamento) throws SQLException {
	    	EntityManager manager;
	    	manager = ConectionFactory.getConnection();
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
	        Query q = manager.createQuery("Select c from Orcamentoprojetovoluntariado c where c.idorcamentoprojetovoluntariado=" + idOrcamento);
	        if (q.getResultList().size()>0){
	        	Orcamentoprojetovoluntariado orcamentoprojetovoluntariado = (Orcamentoprojetovoluntariado) q.getResultList().get(0);
	            manager.remove(orcamentoprojetovoluntariado);
	        }
	       tx.commit();
	        manager.close();
	    }
}
