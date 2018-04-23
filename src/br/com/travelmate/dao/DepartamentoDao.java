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
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        departamento = manager.merge(departamento);
        tx.commit();
        manager.close();
        return departamento;
    }
    
    public Departamento consultar(int idDepartamento) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Departamento departamento = manager.find(Departamento.class, idDepartamento);
        manager.close();
        return departamento;
    }
    
    public List<Departamento> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Departamento> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public void excluir(int idDepartamento) throws SQLException {
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        Query q = manager.createQuery("Select d from Departamento d where d.iddepartamento=" + idDepartamento);
        if (q.getResultList().size()>0){
        	Departamento departamento = (Departamento) q.getResultList().get(0);
            manager.remove(departamento);
        }
        tx.commit();
        manager.close();
    }
    
   
	
}
