package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Vendapendencia;

public class VendaPendenciaDao {
	
	public Vendapendencia salvar(Vendapendencia vendapendencia) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		vendapendencia = manager.merge(vendapendencia);
        tx.commit();
        manager.close();
        return vendapendencia;
    }
    
    public Vendapendencia consultarVendas(int idVenda) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Vendapendencia venda = manager.find(Vendapendencia.class, idVenda);
        manager.close();
        return venda;
    }
    
    public void excluir(int idVenda) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Vendapendencia venda = manager.find(Vendapendencia.class, idVenda);
        manager.remove(venda);
        tx.commit();
        manager.close();
    }
    
    @SuppressWarnings("unchecked")
	public List<Vendapendencia> lista(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Vendapendencia> lista = q.getResultList();
        manager.close();
        return lista;
    }
    

}
