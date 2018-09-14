package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Vendamotivopendencia;

public class VendaMotivoPendenciaDao {
	
	public Vendamotivopendencia salvar(Vendamotivopendencia vendamotivopendencia) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		vendamotivopendencia = manager.merge(vendamotivopendencia);
        tx.commit();
        manager.close();
        return vendamotivopendencia;
    }
    
    public Vendamotivopendencia consultarVendas(int idVenda) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Vendamotivopendencia venda = manager.find(Vendamotivopendencia.class, idVenda);
        manager.close();
        return venda;
    }
    
    public void excluir(int idVenda) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Vendamotivopendencia venda = manager.find(Vendamotivopendencia.class, idVenda);
        manager.remove(venda);
        tx.commit();
        manager.close();
    }

    @SuppressWarnings("unchecked")
    public List<Vendamotivopendencia> lista(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Vendamotivopendencia> lista = q.getResultList();
        manager.close();
        return lista;
    }

}
