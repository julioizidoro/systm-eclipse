package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Cidadepaisproduto;

public class CidadePaisProdutoDao {

	public Cidadepaisproduto salvar(Cidadepaisproduto cidade) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		cidade = manager.merge(cidade);
		tx.commit();
		return cidade;
	}

	public List<Cidadepaisproduto> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		List<Cidadepaisproduto> lista = q.getResultList();
		if (lista != null) {
			if (lista.size() == 0) {
				return null;
			}
		}
		return lista;
	}

	public void excluir(int idcidade) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Query q = manager.createQuery("Select c from Cidadepaisproduto c where c.idcidadepaisproduto=" + idcidade);
		if (q.getResultList().size() > 0) {
			Cidadepaisproduto cidade = (Cidadepaisproduto) q.getResultList().get(0);
			manager.remove(cidade);
		}
		tx.commit();

	}

	public Cidadepaisproduto consultar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getConnection();
		Query q = manager.createQuery(sql);
		Cidadepaisproduto cidadepaisproduto = null;
		if (q.getResultList().size() > 0) {
			cidadepaisproduto = (Cidadepaisproduto) q.getResultList().get(0);
		}
		manager.close();
		return cidadepaisproduto;
	}

}
