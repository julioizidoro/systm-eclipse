package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Acomodacao;

public class AcomodacaoDao {
	
	public Acomodacao salvar(Acomodacao acomodacao) throws SQLException{
		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		acomodacao = manager.merge(acomodacao);
        tx.commit();
        manager.close();
        return acomodacao; 
    } 
	
	public Acomodacao consultar(String sql)  throws SQLException  {
		EntityManager manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql); 
        Acomodacao acomodacao = null; 
        if (q.getResultList().size() > 0) {
        		acomodacao =  (Acomodacao) q.getResultList().get(0);
        } 
        manager.close();
        return acomodacao;
    }
	
	public List<Acomodacao> lista(String sql) throws SQLException {
		EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Acomodacao> lista = q.getResultList();
        manager.close();
        return lista; 
    }
	
	public void excluir(int idAcomodacao) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Acomodacao acomodacao = manager.find(Acomodacao.class, idAcomodacao);
        manager.remove(acomodacao);
        tx.commit();
        manager.close();
    }

}
