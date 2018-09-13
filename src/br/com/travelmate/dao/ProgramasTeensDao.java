/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Controleprogramasteen;
import br.com.travelmate.model.Programasteens;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Greicieli
 */
public class ProgramasTeensDao {
       public Programasteens salvar(Programasteens programasteens) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        programasteens = manager.merge(programasteens);
        tx.commit();
        manager.close();
        return programasteens;
    }
    
    @SuppressWarnings("unchecked")
	public List<Programasteens> listar(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Programasteens> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public Programasteens find(int idVenda) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select p from Programasteens p where p.vendas.idvendas=" + idVenda + " order by p.vendas.dataVenda");
        Programasteens programa = null;
        if (q.getResultList().size()>0){
        	programa = (Programasteens) q.getResultList().get(0);
        }
        manager.close();
        return programa;
    }
    
    public Controleprogramasteen salvar(Controleprogramasteen controle) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        controle = manager.merge(controle);
        tx.commit();
        manager.close();
        return controle;
    }
    
   
    public Controleprogramasteen consultarControle(int idVendas, int idControle) throws SQLException {
    	EntityManager manager = ConectionFactory.getConnection();
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Controleprogramasteen c where c.vendas.idvendas=" + idVendas + "  and c.idcontroleProgramasTeens=" + idControle);
        Controleprogramasteen cotrolehighschool = null;
        if (q.getResultList().size() > 0) {
        	cotrolehighschool =  (Controleprogramasteen) q.getResultList().get(0);
        }
        manager.close();
        return cotrolehighschool;
    }
    
    public Controleprogramasteen consultarControle(int idVendas) throws SQLException {
    	EntityManager manager = ConectionFactory.getConnection();
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Controleprogramasteen c where c.vendas.idvendas=" + idVendas);
        Controleprogramasteen cotrolehighschool = null;
        if (q.getResultList().size() > 0) {
        	cotrolehighschool =  (Controleprogramasteen) q.getResultList().get(0);
        }
        manager.close();
        return cotrolehighschool;
    }
    
    @SuppressWarnings("unchecked")
	public List<Controleprogramasteen> listaControle(String sql) throws SQLException {
    	EntityManager manager = ConectionFactory.getConnection();
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Controleprogramasteen> lista = q.getResultList();
        manager.close();
        return lista;
    }
}
