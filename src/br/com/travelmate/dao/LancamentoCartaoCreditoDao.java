package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Lancamentocartaocredito;

public class LancamentoCartaoCreditoDao {

	
	public Lancamentocartaocredito salvar(Lancamentocartaocredito lancamentocartaocredito) throws SQLException{
		EntityManager manager = ConectionFactory.getInstance(); 
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		lancamentocartaocredito = manager.merge(lancamentocartaocredito); 
		tx.commit();
		return lancamentocartaocredito;
	}
	    
	public Lancamentocartaocredito consultar(int idlancamentocartaocredito) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getInstance();
        Lancamentocartaocredito lancamentocartaocredito = manager.find(Lancamentocartaocredito.class, idlancamentocartaocredito);
        
        return lancamentocartaocredito;
    }
	    
	public void excluir(int idlancamentocartaocredito) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Lancamentocartaocredito lancamentocartaocredito = manager.find(Lancamentocartaocredito.class, idlancamentocartaocredito);
		manager.remove(lancamentocartaocredito);
		tx.commit();
		
	}
	    
	public List<Lancamentocartaocredito> listar(String sql)throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		List<Lancamentocartaocredito> lista = null;
		if (q.getResultList().size()>0){
			lista =  q.getResultList();
		}
		
		return lista;
	}
	
	
	public Lancamentocartaocredito consultarVenda(int idVenda) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
        Query q = manager.createQuery("select c from Lancamentocartaocredito c where c.vendas.idvendas=" + idVenda);
        Lancamentocartaocredito lancamentocartaocredito= null;
        if (q.getResultList().size() > 0) {
        	lancamentocartaocredito = (Lancamentocartaocredito) q.getResultList().get(0);
        } 
        return lancamentocartaocredito;
    }
}
