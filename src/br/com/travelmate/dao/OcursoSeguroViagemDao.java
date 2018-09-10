/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Ocursoseguro;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class OcursoSeguroViagemDao implements Serializable{
   
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
    
	@Transactional
    public Ocursoseguro salvar(Ocursoseguro  ocursoseguro) {
        ocursoseguro = manager.merge(ocursoseguro);
        return ocursoseguro;
    }
    
    public Ocursoseguro consultar(int idOcurso) {
        Query q = manager.createQuery("SELECT o FROM Ocursoseguro o where o.ocurso.idocurso=" + idOcurso);
        Ocursoseguro seguro = null;
        if (q.getResultList().size() > 0) {
            seguro =  (Ocursoseguro) q.getResultList().get(0);
        } 
        return seguro;
    }
    
    @Transactional
    public void excluir(int idOcursoseguro)  {
        Query q = manager.createQuery("Select c from Ocursoseguro c where c.idocursoseguro=" + idOcursoseguro);
        if (q.getResultList().size()>0){
        	Ocursoseguro ocursoseguro = (Ocursoseguro) q.getResultList().get(0);   
            manager.remove(ocursoseguro);
        }    
    
    }
}
