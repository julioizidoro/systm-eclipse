package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Controleaupair;
import br.com.travelmate.model.Lancamentocartaocredito;

public class LancamentoCartaoCreditoDao {

	
	public Lancamentocartaocredito salvar(Lancamentocartaocredito lancamentocartaocredito) throws SQLException{
		EntityManager manager = ConectionFactory.getConnection(); 
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		lancamentocartaocredito = manager.merge(lancamentocartaocredito); 
		tx.commit();
		manager.close();
		return lancamentocartaocredito;
	}
	    
	public Lancamentocartaocredito consultar(int idlancamentocartaocredito) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
        Lancamentocartaocredito lancamentocartaocredito = manager.find(Lancamentocartaocredito.class, idlancamentocartaocredito);
        manager.close();
        return lancamentocartaocredito;
    }
	    
	public void excluir(int idlancamentocartaocredito) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Lancamentocartaocredito lancamentocartaocredito = manager.find(Lancamentocartaocredito.class, idlancamentocartaocredito);
		manager.remove(lancamentocartaocredito);
		tx.commit();
		manager.close();
	}
	    
	public List<Lancamentocartaocredito> listar(String sql)throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Lancamentocartaocredito> lista = null;
		if (q.getResultList().size()>0){
			lista =  q.getResultList();
		}
		manager.close();
		return lista;
	}
	
	
	public Lancamentocartaocredito consultarVenda(int idVenda) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Lancamentocartaocredito c where c.vendas.idvendas=" + idVenda);
        Lancamentocartaocredito lancamentocartaocredito= null;
        if (q.getResultList().size() > 0) {
        	lancamentocartaocredito = (Lancamentocartaocredito) q.getResultList().get(0);
        } 
        manager.close();
        return lancamentocartaocredito;
    }
}
