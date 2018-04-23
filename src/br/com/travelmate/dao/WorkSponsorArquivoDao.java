/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Arquivos;
import br.com.travelmate.model.Worksponsorarquivos;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class WorkSponsorArquivoDao {
    
    public Worksponsorarquivos salvar(Worksponsorarquivos worksponsor) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		worksponsor = manager.merge(worksponsor);
        tx.commit();
        manager.close();
        return worksponsor;
    }
    
    public List<Worksponsorarquivos> listar(String sql) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Worksponsorarquivos> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public Worksponsorarquivos consulta(String sql) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Worksponsorarquivos worksponsor = null;
        if (q.getResultList().size()>0){
        	worksponsor = (Worksponsorarquivos) q.getResultList().get(0);
        }
        manager.close();
        return worksponsor;
    } 
    

	public void excluir(int idArquivos) throws SQLException{
		EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Worksponsorarquivos arquivos = manager.find(Worksponsorarquivos.class, idArquivos);
		manager.remove(arquivos);
		tx.commit();
		
	}
}
