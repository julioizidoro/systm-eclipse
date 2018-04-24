package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Departamento;

public class DepartamentoDao {
    
    public Departamento salvar(Departamento departamento) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        departamento = manager.merge(departamento);
        tx.commit();
        
        return departamento;
    }
    
    public Departamento consultar(int idDepartamento) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Departamento departamento = manager.find(Departamento.class, idDepartamento);
        
        return departamento;
    }
    
    public List<Departamento> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Departamento> lista = q.getResultList();
        
        return lista;
    }
    
    public void excluir(int idDepartamento) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select d from Departamento d where d.iddepartamento=" + idDepartamento);
        if (q.getResultList().size()>0){
        	Departamento departamento = (Departamento) q.getResultList().get(0);
            manager.remove(departamento);
        }
        tx.commit();
        
    }
    
   
	
}
