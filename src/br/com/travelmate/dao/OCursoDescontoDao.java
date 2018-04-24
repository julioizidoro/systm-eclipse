package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Ocursodesconto; 

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class OCursoDescontoDao {
    
    public Ocursodesconto salvar(Ocursodesconto  ocursodesconto) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        ocursodesconto = manager.merge(ocursodesconto);
        manager.getTransaction().commit();
        manager.close();
        return ocursodesconto;
    }
    
    public List<Ocursodesconto> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Ocursodesconto> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public List<Ocursodesconto> lista(int idOcurso) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Ocursodesconto p where p.ocurso.idocurso=" + idOcurso);
        List<Ocursodesconto> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public void excluir(int idOcursodesconto) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
        tx.begin();
        Query q = manager.createQuery("Select c from Ocursodesconto c where c.idocursodesconto=" + idOcursodesconto);
        if (q.getResultList().size()>0){
        	Ocursodesconto ocursodesconto = (Ocursodesconto) q.getResultList().get(0);   
            manager.remove(ocursodesconto);
        }    
        tx.commit();    
    }
    
}
