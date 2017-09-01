package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Versaousuario;

public class VersaoUsuarioDao {

	
	public Versaousuario salvar(Versaousuario versoesusuario) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		versoesusuario = manager.merge(versoesusuario);
		tx.commit();
		manager.close();
		return versoesusuario;
	}

	public Versaousuario consultar(int idversoes) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery("select v from Versaousuario  v where v.tdversaousuario=" + idversoes);
		Versaousuario versoes = null;
		if (q.getResultList().size() > 0) {
			versoes = (Versaousuario) q.getResultList().get(0);
		}
		manager.close();
		return versoes;
	}

	public List<Versaousuario> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Versaousuario> lista = q.getResultList();
		manager.close();
		return lista;
	}
}
