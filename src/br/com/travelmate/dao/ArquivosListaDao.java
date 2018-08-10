package br.com.travelmate.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Arquvioslista;


public class ArquivosListaDao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	public List<Arquvioslista> lista(String sql) {
        Query q = manager.createQuery(sql);
        List<Arquvioslista> lista = q.getResultList();
        return lista;
    }
	
	
	@Transactional
    public Arquvioslista salvar(Arquvioslista arquvioslista) {
		arquvioslista = manager.merge(arquvioslista);
        return arquvioslista;
    }
    
    public Arquvioslista consultarArquvioslista(int idArquivos) {
        Arquvioslista arquvioslista = manager.find(Arquvioslista.class, idArquivos);
        return arquvioslista;
    }
    
    @Transactional
    public void excluir(int idArquivos) {
        Arquvioslista arquvioslista = manager.find(Arquvioslista.class, idArquivos);
        manager.remove(arquvioslista);
    }

}
