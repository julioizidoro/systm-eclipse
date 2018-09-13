package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Avisodocsusuario;

public class AvisoDocsUsuarioDao {

	@SuppressWarnings("unchecked")
	public List<Avisodocsusuario> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		List<Avisodocsusuario> lista = q.getResultList();
		
		return lista;
	}

	public Avisodocsusuario salvar(Avisodocsusuario aviso) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		aviso = manager.merge(aviso);
		tx.commit();
		
		return aviso;
	}

	public void excluir(Avisodocsusuario aviso) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		aviso = manager.find(Avisodocsusuario.class, aviso.getIdavisodocsusuario());
		manager.remove(aviso);
		tx.commit();
		
	}
}
