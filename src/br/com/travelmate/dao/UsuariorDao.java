/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;
import br.com.travelmate.connection.pool.Transactional;
import br.com.travelmate.model.Pincambio;
import br.com.travelmate.model.Usuario;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;		

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class UsuariorDao implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	@Transactional
    public Usuario salvar(Usuario usuario){
    	usuario = manager.merge(usuario);
        return usuario;
    }
    
	@Transactional
    public Usuario consultar(int idUsuario) {
    	Usuario usuario = manager.find(Usuario.class, idUsuario);
        return usuario; 
    }
    
    public Usuario consultar(String login, String senha) {
    	Query q = manager.createQuery("select u from Usuario u where u.login='" + login + "' and u.senha='" + senha + "'  order by u.nome");
        Usuario usuario = null;
        if (q.getResultList().size()>0){
            usuario = (Usuario) q.getResultList().get(0);
        }
        return usuario;
    }
    
    public List<Usuario> listaUsuario(){
    	Query q = manager.createQuery("select u from Usuario u order by u.nome");
        List<Usuario> lista = q.getResultList();
        return lista;
    }
    
    public List<Usuario> listaUsuarioUnidade(int idUnidade) {
        Query q = manager.createQuery("select u from Usuario u where u.unidadenegocio.idunidadeNegocio=" + idUnidade + " order by u.nome");
        List<Usuario> lista = q.getResultList();
        return lista;
    }
    
     public List<Usuario> listar(String sql) {
        Query q = manager.createQuery(sql);
        List<Usuario> lista = q.getResultList();
        return lista;
    }
    
    public List<Usuario> listaUsuario(String nome) {
        Query q = manager.createQuery("select u from Usuario u where u.nome like '%" + nome + "%' order by u.nome" );
        List<Usuario> lista = q.getResultList();
        return lista;
    }
    
    @Transactional
    public Pincambio salvar(Pincambio pincambio){
        pincambio = manager.merge(pincambio);
        return pincambio;
    }
    
    public Pincambio consultar(String pin, int idUsuario) {
       Query q = manager.createQuery("select p from Pincambio p where p.numero='" + pin + "' and p.consultor=" + idUsuario +
                " and p.utilizado='N'");
       Pincambio pincambio = null;
       if (q.getResultList().size()>0){
            pincambio = (Pincambio) q.getResultList().get(0);
       }
       return pincambio;
    }
    
    public List<Usuario> consultar(String sql) {
        Query q = manager.createQuery(sql);
        List<Usuario> lista = q.getResultList();
        return lista;
    }
    
}
