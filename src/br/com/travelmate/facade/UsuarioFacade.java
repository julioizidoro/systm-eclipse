/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.UsuarioDao;
import br.com.travelmate.model.Pincambio;
import br.com.travelmate.model.Usuario;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioFacade {
    
    UsuarioDao usuarioDao;
    
    public Usuario salvar(Usuario usuario) {
        usuarioDao = new UsuarioDao();
        try {
            return usuarioDao.salvar(usuario);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Usuario consultar(int idUsuario) {
        usuarioDao = new UsuarioDao();
        try {
            return usuarioDao.consultar(idUsuario);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Usuario consultar(String login, String senha) {
        usuarioDao = new UsuarioDao();
        try {
            return usuarioDao.consultar(login, senha);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public List<Usuario> listaUsuario() throws SQLException{
        usuarioDao = new UsuarioDao();
        return usuarioDao.listaUsuario();
    }
    
    public List<Usuario> listaUsuarioUnidade(int idUnidade) {
        usuarioDao = new UsuarioDao();
        try {
            return usuarioDao.listaUsuarioUnidade(idUnidade);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Usuario> listar(String sql) {
        usuarioDao = new UsuarioDao();
        try {
            return usuarioDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Usuario> listaUsuario(String nome) {
        usuarioDao = new UsuarioDao();
        try {
            return usuarioDao.listaUsuario(nome);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
  
    
    public Pincambio salvar(Pincambio pincambio) {
        usuarioDao = new UsuarioDao();
        try {
            return usuarioDao.salvar(pincambio);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Usuario> consultar(String sql) {
        usuarioDao = new UsuarioDao();
        try {
            return usuarioDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Pincambio consultar(String pin, int idUsuario)  {
        usuarioDao = new UsuarioDao();
        try {
            return usuarioDao.consultar(pin, idUsuario);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
