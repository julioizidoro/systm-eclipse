package br.com.travelmate.dao;
 
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Leadresponsavel;  

@SuppressWarnings("unchecked")
public class LeadResponsavelDao implements Serializable{ 
	 
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	@Transactional
	public Leadresponsavel salvar(Leadresponsavel leadresponsavel){
		leadresponsavel = manager.merge(leadresponsavel);
        return leadresponsavel; 
    } 
    
    public Leadresponsavel consultar(String sql)   {
		Query q = manager.createQuery(sql); 
		Leadresponsavel leadresponsavel = null; 
        if (q.getResultList().size() > 0) {
        		leadresponsavel =  (Leadresponsavel) q.getResultList().get(0);
        } 
	    return leadresponsavel;
    }
     
    @Transactional
    public void excluir(int idleadresponsavel)   {
		Leadresponsavel leadresponsavel = manager.find(Leadresponsavel.class, idleadresponsavel);
        manager.remove(leadresponsavel);  
    }
     
    public List<Leadresponsavel> lista(String sql)  {
		Query q = manager.createQuery(sql);
        List<Leadresponsavel> lista = q.getResultList();
		return lista; 
    }
    
    public List<Leadresponsavel> lista(int idunidade)  {
		Query q = manager.createQuery("SELECT l FROM Leadresponsavel l WHERE l.unidadenegocio.idunidadeNegocio="+idunidade + " and l.usuario.situacao='Ativo' "
        		+ " ORDER BY l.usuario.nome");
        List<Leadresponsavel> lista = q.getResultList();
		return lista; 
    }

}
