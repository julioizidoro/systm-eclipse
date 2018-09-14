package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Vendapendenciahistorico;

public class VendaPendenciaHistoricoDao {

	
	public Vendapendenciahistorico salvar(Vendapendenciahistorico vendapendenciahistorico) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		vendapendenciahistorico = manager.merge(vendapendenciahistorico);
        tx.commit();
        manager.close();
        return vendapendenciahistorico;
    }
    
    public Vendapendenciahistorico consultarVendas(int idVenda) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Vendapendenciahistorico venda = manager.find(Vendapendenciahistorico.class, idVenda);
        manager.close();
        return venda;
    }
    
    public void excluir(int idVenda) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Vendapendenciahistorico venda = manager.find(Vendapendenciahistorico.class, idVenda);
        manager.remove(venda);
        tx.commit();
        manager.close();
    }
    
    @SuppressWarnings("unchecked")
	public List<Vendapendenciahistorico> lista(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Vendapendenciahistorico> lista = q.getResultList();
        manager.close();
        return lista;
    }
}
