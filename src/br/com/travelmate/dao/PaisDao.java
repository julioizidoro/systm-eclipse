package br.com.travelmate.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Pais;



public class PaisDao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
    
	@Transactional
    public Pais salvar(Pais pais) {
    	pais = manager.merge(pais);
         return pais;
    }
    
	@SuppressWarnings("unchecked")
    public List<Pais> listar(String nome) {
    	Query q = manager.createQuery("select p from Pais p where p.nome like '%" + nome + "%' order by p.nome");
		List<Pais> listaPais = q.getResultList();
        return listaPais;
    }
    
	 @SuppressWarnings("unchecked")
    public List<Pais> listar() {
    	Query q = manager.createQuery("select p from Pais p order by p.nome");
        List<Pais> listaPais = q.getResultList();
        return listaPais;
    }
    
    public Pais consultarNome(String nome){
    	Query q = manager.createQuery("select p from Pais p where p.nome like '%" + nome + "%'" );
        Pais pais = null;
        if (q.getResultList().size() > 0) {
        	pais = (Pais) q.getResultList().get(0);
        } 
        return pais;
     }
    
    
    public Pais consultar(int id) {
    	Query q = manager.createQuery("select p from Pais p where p.idpais="+id );
        Pais pais = null;
        if (q.getResultList().size() > 0) {
        	pais = (Pais) q.getResultList().get(0);
        } 
        return pais;
     }
    
    @SuppressWarnings("unchecked")
    public List<Pais> listarModelo(String sql) {
    	Query q = manager.createQuery(sql);
        List<Pais> listaPais = q.getResultList();
        return listaPais;
    }
    
    
    
}
