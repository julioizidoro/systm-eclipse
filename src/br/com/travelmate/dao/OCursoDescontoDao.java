package br.com.travelmate.dao;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Ocursodesconto;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class OCursoDescontoDao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
    
	@Transactional
    public Ocursodesconto salvar(Ocursodesconto  ocursodesconto) {
        ocursodesconto = manager.merge(ocursodesconto);
        return ocursodesconto;
    }
    
    public List<Ocursodesconto> listar(String sql){
        Query q = manager.createQuery(sql);
        List<Ocursodesconto> lista = q.getResultList();
        return lista;
    }
    
    public List<Ocursodesconto> listar(int idOcurso) {
        Query q = manager.createQuery("select p from Ocursodesconto p where p.ocurso.idocurso=" + idOcurso);
        List<Ocursodesconto> lista = q.getResultList();
        return lista;
    }
    
    @Transactional
    public void excluir(int idOcursodesconto) {
        Query q = manager.createQuery("Select c from Ocursodesconto c where c.idocursodesconto=" + idOcursodesconto);
        if (q.getResultList().size()>0){
        	Ocursodesconto ocursodesconto = (Ocursodesconto) q.getResultList().get(0);   
            manager.remove(ocursodesconto);
        }    
    }
    
}
