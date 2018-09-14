package br.com.travelmate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory; 
import br.com.travelmate.model.Usuariodepartamentounidade;

public class UsuarioDepartamentoUnidadeDao {

	public Usuariodepartamentounidade salvar(Usuariodepartamentounidade usuariodepartamentounidade)
			throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		usuariodepartamentounidade = manager.merge(usuariodepartamentounidade);
		tx.commit();
		return usuariodepartamentounidade;
	}

	public Usuariodepartamentounidade consultar(String sql) throws SQLException {
		Usuariodepartamentounidade usuariodepartamentounidade = null;
    	EntityManager manager;
        manager = ConectionFactory.getInstance();
        Query q = manager.createQuery(sql);
        if (q.getResultList().size()>0){
        	usuariodepartamentounidade = (Usuariodepartamentounidade) q.getResultList().get(0);
        }
        return usuariodepartamentounidade;
    }

    @SuppressWarnings("unchecked")
	public List<Usuariodepartamentounidade> listar(String sql) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		Query q = manager.createQuery(sql);
		List<Usuariodepartamentounidade> lista = q.getResultList();

		return lista;
	}

	public void excluir(int idUsuariodepartamentounidade) throws SQLException {
		EntityManager manager;
		manager = ConectionFactory.getInstance();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Query q = manager.createQuery("Select u from Usuariodepartamentounidade u where u.idusuariodepartamentounidade="
				+ idUsuariodepartamentounidade);
		if (q.getResultList().size() > 0) {
			Usuariodepartamentounidade csuariodepartamentounidade = (Usuariodepartamentounidade) q.getResultList()
					.get(0);
			manager.remove(csuariodepartamentounidade);
		}
		tx.commit();

	}
}
