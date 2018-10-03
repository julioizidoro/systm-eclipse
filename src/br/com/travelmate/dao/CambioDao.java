/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;


import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Pais;

/**
 *
 * @author Wolverine
 */
public class CambioDao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
    
	@Transactional
    public Cambio salvar(Cambio cambio) {
        cambio = manager.merge(cambio);
        return cambio;
    }
    
	@Transactional
    public void excluir(Cambio cambio) {
        cambio = manager.find(Cambio.class, cambio.getIdcambio());
        manager.remove(cambio);
    }
    
    public Cambio consultar(int idCambio) {
        Cambio cambio = manager.find(Cambio.class, idCambio);
        return cambio;
    }
    
    public Cambio consultarSigla(String data,String sigla) {
        Query q = manager.createQuery("select c from Cambio c where c.moedas.sigla='" + sigla + "' and c.data='" + data + "'");
        Cambio cambio = null;
        if (q.getResultList().size()>0){
            cambio =  (Cambio) q.getResultList().get(0);
        }
        return cambio;
    }
    
    public Cambio consultarSiglaPais(String data,String sigla, Pais pais) {
        Query q = manager.createQuery("select c from Cambio c where c.moedas.sigla='" + sigla + "' and c.data='" + data + "' and c.pais.idpais=" + pais.getIdpais());
        Cambio cambio = null;
        if (q.getResultList().size()>0){
            cambio =  (Cambio) q.getResultList().get(0);
        }
        return cambio;
    }
    
 	public List<Moedas> listaMoedas()  {
        Query q = manager.createQuery("select m from Moedas m");
        List<Moedas> lista = q.getResultList();
        return lista;
    }
    
    public Cambio consultarCambioMoeda(String data, int idMoeda) {
        Query q = manager.createQuery("select c from Cambio c where c.moedas.idmoedas=" + idMoeda + " and c.data='" + data + "'");
        Cambio cambio = null;
        if (q.getResultList().size()>0){
            cambio =  (Cambio) q.getResultList().get(0);
        }
        return cambio;
    }
    
    public Cambio consultarCambioMoedaPais(String data, int idMoeda, Pais pais) {
        Query q = manager.createQuery("select c from Cambio c where c.moedas.idmoedas=" + idMoeda + " and c.data='" + data + "' and c.pais.idpais=" + pais.getIdpais());
        Cambio cambio = null;
        if (q.getResultList().size()>0){
            cambio =  (Cambio) q.getResultList().get(0);
        }
        return cambio;
    }
    
    public Moedas consultarMoeda(int idMoeda) {
        Moedas moeda = manager.find(Moedas.class, idMoeda);
        return moeda;
    }
    
 	public List<Cambio> listar(String data) {
        Query q = manager.createQuery("select c from Cambio c where c.data='" + data + "'");
        @SuppressWarnings("unchecked")
		List<Cambio> lista = q.getResultList();
        return lista;
    }
    
 	public List<Cambio> listarCambio(String sql){
        Query q = manager.createQuery(sql);
        @SuppressWarnings("unchecked")
		List<Cambio> lista = q.getResultList();
        return lista;
    }
    
    
 	public List<Cambio> listarCambioPais(String data, Pais pais){
        Query q = manager.createQuery("select c from Cambio c where c.data='" + data + "' and c.pais.idpais=" + pais.getIdpais());
        @SuppressWarnings("unchecked")
		List<Cambio> lista = q.getResultList();
        return lista;
    }
    
}
