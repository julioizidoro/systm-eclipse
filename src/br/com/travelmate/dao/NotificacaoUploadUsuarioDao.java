package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Notificacaouploadusuario;

public class NotificacaoUploadUsuarioDao {

	public Notificacaouploadusuario salvar(Notificacaouploadusuario notificacaouploadusuario) throws SQLException{
		EntityManager manager = ConectionFactory.getConnection(); 
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		notificacaouploadusuario = manager.merge(notificacaouploadusuario); 
		tx.commit();
		return notificacaouploadusuario;
	}
	    
	public Notificacaouploadusuario consultar(int idnotificacao) throws SQLException{
		EntityManager manager;
        manager = ConectionFactory.getConnection();
        Notificacaouploadusuario notificacaouploadusuario = manager.find(Notificacaouploadusuario.class, idnotificacao);
        manager.close();
        return notificacaouploadusuario;
    }
	    
	public void excluir(int idnotificacao) throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Notificacaouploadusuario notificacaouploadusuario = manager.find(Notificacaouploadusuario.class, idnotificacao);
		manager.remove(notificacaouploadusuario);
		tx.commit();
		manager.close();
	}
	    
	public List<Notificacaouploadusuario> listar(String sql)throws SQLException{
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Notificacaouploadusuario> lista = null;
		if (q.getResultList().size()>0){
			lista =  q.getResultList();
		}
		manager.close();
		return lista;
	}
}
