/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;
import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.model.Pincambio;
import br.com.travelmate.model.Usuario;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
 
/**
 *
 * @author Wolverine
 */
public class UsuarioDao {
    
    
    public Usuario salvar(Usuario usuario) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        usuario = manager.merge(usuario);
        tx.commit();
        manager.close();
        return usuario;
    }
    
    public Usuario consultar(int idUsuario) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Usuario usuario = manager.find(Usuario.class, idUsuario);
        manager.close();
        return usuario; 
    }
    
    public Usuario consultar(String login, String senha) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
	    Query q = manager.createQuery("select u from Usuario u where u.login='" + login + "' and u.senha='" + senha + "'  order by u.nome");
        Usuario usuario = null;
        if (q.getResultList().size()>0){
            usuario = (Usuario) q.getResultList().get(0);
        }
        manager.close();
        return usuario;
    }
    
    public List<Usuario> listaUsuario() throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select u from Usuario u order by u.nome");
        List<Usuario> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public List<Usuario> listaUsuarioUnidade(int idUnidade) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select u from Usuario u where u.unidadenegocio.idunidadeNegocio=" + idUnidade + " order by u.nome");
        List<Usuario> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
     public List<Usuario> listar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Usuario> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public List<Usuario> listaUsuario(String nome) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery("select u from Usuario u where u.nome like '%" + nome + "%' order by u.nome" );
        List<Usuario> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public Pincambio salvar(Pincambio pincambio) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
        pincambio = manager.merge(pincambio);
        tx.commit();
        manager.close();
        return pincambio;
    }
    
    public Pincambio consultar(String pin, int idUsuario) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
       Query q = manager.createQuery("select p from Pincambio p where p.numero='" + pin + "' and p.consultor=" + idUsuario +
                " and p.utilizado='N'");
       Pincambio pincambio = null;
       if (q.getResultList().size()>0){
            pincambio = (Pincambio) q.getResultList().get(0);
       }
       manager.close();
       return pincambio;
    }
    
    public List<Usuario> consultar(String sql) throws SQLException{
    	EntityManager manager;
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Usuario> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
}
