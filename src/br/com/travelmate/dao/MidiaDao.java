package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Midias;

public class MidiaDao {

	public List<Midias> listarSql(String sql) throws SQLException {
		EntityManager manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Midias> lista = q.getResultList();
		manager.close();
		return lista;
	}

	public List<Midias> listar(String tipo) throws SQLException {
		EntityManager manager = ConectionFactory.getConnection();
		Query q = manager.createQuery("select m from Midias m where m.situacao='Ativo' and m.tipo='" + tipo + "'");
		List<Midias> lista = q.getResultList();
		manager.close();
		return lista;
	}
}
