package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Treinamentorespostausuario;

public class TreinamentoRespostaUsuarioDao {
    
    public Treinamentorespostausuario salvar(Treinamentorespostausuario treinamentorespostausuario) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		treinamentorespostausuario = manager.merge(treinamentorespostausuario);
        tx.commit();
        manager.close();
        return treinamentorespostausuario;
    }
    
    public List<Treinamentorespostausuario> listar(String sql)throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Treinamentorespostausuario> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public Treinamentorespostausuario consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Treinamentorespostausuario treinamentorespostausuario = null;
        if (q.getResultList().size() > 0) {
        	treinamentorespostausuario =   (Treinamentorespostausuario) q.getResultList().get(0);
        }
        manager.close();
        return treinamentorespostausuario;
    }
     
}
