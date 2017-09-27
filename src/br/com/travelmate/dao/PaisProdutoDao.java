/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;
import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Paisproduto;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class PaisProdutoDao {
    
    public Paisproduto salvar(Paisproduto paisProduto) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        paisProduto = manager.merge(paisProduto);
        tx.commit();
        manager.close();
        return paisProduto;
    }
    
    public List<Paisproduto> listar(int idProduto) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Paisproduto p where p.produtos.idprodutos=" + idProduto + " order by p.pais.nome");
        List<Paisproduto> listaPaisProduto = q.getResultList();
        manager.close();
        return listaPaisProduto;
    }
    
    public List<Paisproduto> listar() throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Paisproduto p order by p.pais.nome");
        List<Paisproduto> listaPaisProduto = q.getResultList();
        manager.close();
        return listaPaisProduto;
    }
    
    public List<Paisproduto> listar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Paisproduto> listaPaisProduto = q.getResultList();
        manager.close();
        return listaPaisProduto;
    }
    
    
    public Paisproduto consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Paisproduto paisproduto = null;
        if (q.getResultList().size()>0){
        	paisproduto =  (Paisproduto) q.getResultList().get(0);
        }
        manager.close();
        return paisproduto;
    }
}
