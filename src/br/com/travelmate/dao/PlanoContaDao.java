/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;
import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Planoconta;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class PlanoContaDao {
    
    public Planoconta salvar(Planoconta planoconta) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        planoconta = manager.merge(planoconta);
        tx.commit();
        manager.close();
        return planoconta;
    }

    @SuppressWarnings("unchecked")
    public List<Planoconta> listar(String descricao) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Planoconta p where p.descricao like '%" + descricao + "%'  order by p.descricao" );
        List<Planoconta> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public Planoconta consultar(int idPlanoConta) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Planoconta planoconta = manager.find(Planoconta.class, idPlanoConta);
        manager.close();
        return planoconta;
    }
    
    public int gerarNumeroConta(){
    	EntityManager manager;
    	int numero =0;
    	manager = ConectionFactory.getConnection();
        Query q = manager.createNativeQuery("select MAX(idplanoconta) from planoconta");
        if(q.getResultList().size()>0){
        	numero = (int) q.getResultList().get(0);
        	numero = numero+1;
        }
        manager.close();
    	return numero;
    }
}
