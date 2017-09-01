package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Valoreshe; 

public class ValoresHeDao {

	public Valoreshe salvar(Valoreshe valor) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		valor = manager.merge(valor);
		tx.commit();
		manager.close();
		return valor;
	}

	public Valoreshe consultar(int idvalor) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery("select v from Valoreshe  v where v.idvaloreshe=" + idvalor);
		Valoreshe valor = null;
		if (q.getResultList().size() > 0) {
			valor = (Valoreshe) q.getResultList().get(0);
		}
		manager.close();
		return valor;
	}

	public List<Valoreshe> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		List<Valoreshe> lista = q.getResultList();
		manager.close();
		return lista;
	}

}
