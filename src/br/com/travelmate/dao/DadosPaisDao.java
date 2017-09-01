package br.com.travelmate.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Dadospais;

public class DadosPaisDao {
	
	public Dadospais salvar(Dadospais dadosPais) throws SQLException{
		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
        manager.getTransaction().begin();
        dadosPais = manager.merge(dadosPais);
        tx.commit();
        manager.close();
        return dadosPais;
    }
	
	public Dadospais consultar(int idDadosPais){
		EntityManager manager = ConectionFactory.getConnection();
        Dadospais dadosPais = manager.find(Dadospais.class, idDadosPais);
        manager.close();
        return dadosPais;
	}
	
	public Dadospais consultarVenda(int idVenda) throws SQLException{
		EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select d from Dadospais d where d.vendas.idvendas=" + idVenda);
        Dadospais dadospais = null;
        if (q.getResultList().size() > 0) {
        	dadospais = (Dadospais) q.getResultList().get(0);
        }
        manager.close();
        return dadospais;
    }
}
