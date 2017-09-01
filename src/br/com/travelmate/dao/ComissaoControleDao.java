package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.model.Comissaocontrole;
import br.com.travelmate.model.Contasreceber;

public class ComissaoControleDao {

	
	public Comissaocontrole salvar(Comissaocontrole comissaocontrole) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		comissaocontrole = manager.merge(comissaocontrole);
        tx.commit();
        return comissaocontrole;
    }
	
	public List<Comissaocontrole> listar(String sql)throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getInstance();
       Query q = manager.createQuery(sql);
       List<Comissaocontrole> lista = null;
       if (q.getResultList().size()>0){
           lista =  q.getResultList();
       }
       return lista;
   }
	
	public Comissaocontrole getComissao(String sql)throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getInstance();
       Query q = manager.createQuery(sql);
       Comissaocontrole comissaocontrole = null;
       if (q.getResultList().size()>0){
    	   comissaocontrole =  (Comissaocontrole) q.getResultList().get(0);
       }
       return comissaocontrole;
   }
	
	
	public void excluir(int idComissao) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Comissaocontrole comissaocontrole = manager.find(Comissaocontrole.class, idComissao);
        manager.remove(comissaocontrole);
        tx.commit();
    }
}
