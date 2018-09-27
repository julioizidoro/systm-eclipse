package br.com.travelmate.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;



@SuppressWarnings("unchecked")
public class AvisosDao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	
	public List<Avisos> listar(String sql) {
	    Query q = manager.createQuery(sql);
        List<Avisos> lista = q.getResultList();
        return lista;
    }
	
	@Transactional
	public Avisos salvar(Avisos aviso) {
	    aviso = manager.merge(aviso);
        return aviso;
    }
    
	@Transactional
	public void excluir(Avisos aviso) {
        aviso = manager.find(Avisos.class, aviso.getIdavisos());
        manager.remove(aviso);
    }
    
    public List<Avisousuario> listarAvisoUsuario(String sql) {
        Query q = manager.createQuery(sql);
        List<Avisousuario> lista = q.getResultList();
        return lista;
    }
    
    @Transactional
    public Avisousuario salvar(Avisousuario avisoUsuario) {
       avisoUsuario = manager.merge(avisoUsuario);
        return avisoUsuario;
    }

}
