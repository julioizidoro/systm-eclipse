/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Ocursoformapagamento;

/**
 *
 * @author Wolverine
 */
public class OCursoFormaPagamentoDao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
    
	@Transactional
    public Ocursoformapagamento salvar(Ocursoformapagamento formaPagamento) {
        formaPagamento = manager.merge(formaPagamento);
        return formaPagamento;
    }
    
	@Transactional
    public void excluir(int idOcurso) {
        Query q = manager.createNativeQuery("delete from ocursoformapagamento  where ocurso_idocurso=" + idOcurso);
        q.executeUpdate();
     }
    
    public List<Ocursoformapagamento> listar(int idOcurso) {
        Query q = manager.createQuery("select p from Ocursoformapagamento p where p.ocurso.idocurso=" + idOcurso);
        List<Ocursoformapagamento> lista = q.getResultList();
        return lista;
    }
    
    public Ocursoformapagamento consultar(int idOcurso) {
    	Query q = manager.createQuery("select p from ocursoformapagamento p where p.ocurso.idocurso=" + idOcurso);
        Ocursoformapagamento forma = null;
        if (q.getResultList().size() > 0) {
            forma = (Ocursoformapagamento) q.getResultList().get(0);
        }
        return forma;
    }
}
