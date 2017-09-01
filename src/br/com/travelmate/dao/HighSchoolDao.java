package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Controlehighschool;
import br.com.travelmate.model.Highschool;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class HighSchoolDao {
    
    public Highschool salvar(Highschool highschool) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        highschool = manager.merge(highschool);
        tx.commit();
        manager.close();
        return highschool;
    }
    
    public List<Highschool> listar(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Highschool> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public Highschool consultarHighschool(int idVenda) throws SQLException {
    	EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select h from Highschool h where h.vendas.idvendas=" + idVenda);
        Highschool highschool = null;
        if (q.getResultList().size()>0){
        	highschool = (Highschool) q.getResultList().get(0);
        }
        manager.close();
        return highschool;
    }
    
    public Controlehighschool salvar(Controlehighschool controle) throws SQLException{
    	EntityManager manager = ConectionFactory.getConnection();
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        controle = manager.merge(controle);
        tx.commit();
        manager.close();
        return controle;
    }
    
   
    public Controlehighschool consultarControle(int idVendas, int idControle) throws SQLException {
    	EntityManager manager = ConectionFactory.getConnection();
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Controlehighschool c where c.vendas.idvendas=" + idVendas + "  and c.idcontroleHighSchool=" + idControle);
        Controlehighschool cotrolehighschool = null;
        if (q.getResultList().size() > 0) {
        	cotrolehighschool =  (Controlehighschool) q.getResultList().get(0);
        }
        manager.close();
        return cotrolehighschool;
    }
    
    public Controlehighschool consultarControle(int idVendas) throws SQLException {
    	EntityManager manager = ConectionFactory.getConnection();
    	manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select c from Controlehighschool c where c.vendas.idvendas=" + idVendas);
        Controlehighschool cotrolehighschool = null;
        if (q.getResultList().size() > 0) {
        	cotrolehighschool =  (Controlehighschool) q.getResultList().get(0);
        }
        manager.close();
        return cotrolehighschool;
    }
    
    public List<Controlehighschool> listaControle(String sql) throws SQLException {
    	EntityManager manager = ConectionFactory.getConnection();
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Controlehighschool> lista = q.getResultList();
        manager.close();
        return lista;
    }
}
