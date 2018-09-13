package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;



@SuppressWarnings("unchecked")
public class AvisosDao {
	
	public List<Avisos> listar(String sql) throws SQLException{
		EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Avisos> lista = q.getResultList();
        return lista;
    }
	
	public Avisos salvar(Avisos aviso) throws SQLException{
		EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        aviso = manager.merge(aviso);
        tx.commit();
        return aviso;
    }
    
    public void excluir(Avisos aviso) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        aviso = manager.find(Avisos.class, aviso.getIdavisos());
        manager.remove(aviso);
        tx.commit();
    }
    
    public List<Avisousuario> listarAvisoUsuario(String sql) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        List<Avisousuario> lista = q.getResultList();
        return lista;
    }
    
    public Avisousuario salvar(Avisousuario avisoUsuario) throws SQLException{
    	EntityManager manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        avisoUsuario = manager.merge(avisoUsuario);
        tx.commit();
        return avisoUsuario;
    }

}
