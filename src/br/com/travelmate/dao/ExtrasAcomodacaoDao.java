package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Extrasacamodacao;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila Rodrigues
 */
public class ExtrasAcomodacaoDao {
    
    public Extrasacamodacao salvar(Extrasacamodacao extras) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        extras = manager.merge(extras);
        tx.commit();
        manager.close();
        return extras;
    }
    
    
    public List<Extrasacamodacao> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Extrasacamodacao> lista = null;
        if (q.getResultList().size()>0){
            lista =  q.getResultList();
        }
        manager.close();
        return lista;
    }
    
    public void excluir(int id) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Extrasacamodacao extras = manager.find(Extrasacamodacao.class, id);
        manager.remove(extras);
        tx.commit();
        manager.close();
    }
    
    public Extrasacamodacao consultar(int id) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("Select e From Extrasacamodacao e where e.idextrasacomodacao=" + id);
        Extrasacamodacao extras =null;
        if (q.getResultList().size()>0){
        	extras = (Extrasacamodacao) q.getSingleResult();
        }
        manager.close();
        return extras;
    } 
}
