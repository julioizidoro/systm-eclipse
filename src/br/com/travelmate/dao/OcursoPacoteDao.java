package br.com.travelmate.dao;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Ocursopacote;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Kamila
 */
public class OcursoPacoteDao implements Serializable{

	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	@Transactional
	public Ocursopacote salvar(Ocursopacote ocursopacote)  {
		ocursopacote = manager.merge(ocursopacote);
		return ocursopacote;
	}

	
	public Ocursopacote consultar(String sql)  {
		Query q = manager.createQuery(sql);
		Ocursopacote ocursopacote = null;
		if (q.getResultList().size() > 0) {
			ocursopacote = (Ocursopacote) q.getResultList().get(0);
		}
		return ocursopacote;
	}  

	@Transactional
	public void excluir(int idOcursopacote)  {
		Ocursopacote ocursopacote = manager.find(Ocursopacote.class, idOcursopacote);
		manager.remove(ocursopacote);
	}
	

	@SuppressWarnings("unchecked")
	public List<Ocursopacote> listar(String sql) {
		Query q = manager.createQuery(sql);
		List<Ocursopacote> ocursopacote = null;
		if (q.getResultList().size() > 0) {
			ocursopacote = q.getResultList();
		}
		return ocursopacote;
	}

}
