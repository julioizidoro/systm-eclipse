package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Valoresacomodacao;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila Rodrigues
 */
public class ValoresAcomodacaoDao {
    
    public Valoresacomodacao salvar(Valoresacomodacao valores) throws SQLException{
    	EntityManager manager;
    	manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        valores = manager.merge(valores);
        tx.commit();
        manager.close();
        return valores;
    }
    
    
    public List<Valoresacomodacao> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Valoresacomodacao> lista = null;
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
        Valoresacomodacao valores = manager.find(Valoresacomodacao.class, id);
        manager.remove(valores);
        tx.commit();
        manager.close();
    }
    
    public Valoresacomodacao consultar(int id) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("Select v From Valoresacomodacao v where v.idvaloresacomodacao=" + id);
        Valoresacomodacao valoresacomodacao =null;
        if (q.getResultList().size()>0){
        	valoresacomodacao = (Valoresacomodacao) q.getSingleResult();
        }
        manager.close();
        return valoresacomodacao;
    } 
}
