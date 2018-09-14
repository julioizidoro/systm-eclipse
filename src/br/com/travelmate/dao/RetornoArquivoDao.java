package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Retornoarquivo;

public class RetornoArquivoDao {

	
	public Retornoarquivo salvar(Retornoarquivo retornoarquivo) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		retornoarquivo = manager.merge(retornoarquivo);
		tx.commit();
		
		return retornoarquivo;
	}

    @SuppressWarnings("unchecked")
	public List<Retornoarquivo> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		List<Retornoarquivo> lista = q.getResultList();
		
		return lista;
	}

	public void excluir(int idretornoarquivo) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		manager.getTransaction().begin();
		Query q = manager.createQuery("Select r from Retornoarquivo r where r.idretornoarquivo=" + idretornoarquivo);
		if (q.getResultList().size() > 0) {
			Retornoarquivo retornoarquivo = (Retornoarquivo) q.getResultList().get(0);
			manager.remove(retornoarquivo);
		}
		tx.commit();
		
	}
}
