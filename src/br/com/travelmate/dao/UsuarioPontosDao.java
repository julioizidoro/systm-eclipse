package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;

import br.com.travelmate.model.Usuariopontos;

public class UsuarioPontosDao {
    
    public Usuariopontos salvar(Usuariopontos usuariopontos) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        usuariopontos = manager.merge(usuariopontos);
        tx.commit();
        manager.close();
        return usuariopontos;
    }
    
    public List<Usuariopontos> listar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Usuariopontos> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public Usuariopontos consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Usuariopontos usuariopontos = null;
        if (q.getResultList().size()>0){
        	usuariopontos = (Usuariopontos) q.getResultList().get(0);
        }
        manager.close();
        return usuariopontos;
    }
}
