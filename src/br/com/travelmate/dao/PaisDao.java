package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Pais;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


@SuppressWarnings("unchecked")
public class PaisDao {
    
    public Pais salvar(Pais pais) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        pais = manager.merge(pais);
        tx.commit();
        return pais;
    }
    
    public List<Pais> listar(String nome) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery("select p from Pais p where p.nome like '%" + nome + "%' order by p.nome");
        List<Pais> listaPais = q.getResultList();
        return listaPais;
    }
    
    public List<Pais> listar() throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery("select p from Pais p order by p.nome");
        List<Pais> listaPais = q.getResultList();
        return listaPais;
    }
    
    public Pais consultarNome(String nome) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery("select p from Pais p where p.nome like '%" + nome + "%'" );
        Pais pais = null;
        if (q.getResultList().size() > 0) {
        	pais = (Pais) q.getResultList().get(0);
        } 
        return pais;
     }
    
    
    public Pais consultar(int id) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery("select p from Pais p where p.idpais="+id );
        Pais pais = null;
        if (q.getResultList().size() > 0) {
        	pais = (Pais) q.getResultList().get(0);
        } 
        return pais;
     }
    
    
    public List<Pais> listarModelo(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Pais> listaPais = q.getResultList();
        return listaPais;
    }
    
    
    
}
