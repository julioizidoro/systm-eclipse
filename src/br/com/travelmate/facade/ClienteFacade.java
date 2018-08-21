/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.ClienteDao;
import br.com.travelmate.model.Cliente;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Wolverine
 */
public class ClienteFacade {
    
    ClienteDao clienteDao;
    
    public Cliente salvar(Cliente cliente){
        clienteDao = new ClienteDao();
        try {
            return clienteDao.salvar(cliente);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Cliente consultar(int idCliente) {
        clienteDao = new ClienteDao();
        try {
            return clienteDao.consultar(idCliente);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Cliente consultarEmail(String email) {
        clienteDao = new ClienteDao();
        try {
            return clienteDao.consultarEmail(email);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Cliente consultarEmailSql(String email) {
        clienteDao = new ClienteDao();
        try {
            return clienteDao.consultarEmailSql(email);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Cliente consultarCpf(String cpf) {
    	clienteDao = new ClienteDao();
    	try {
			return clienteDao.consultarCpf(cpf);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public Cliente consultarCpfLista(String cpf) {
        clienteDao = new ClienteDao();
        try { 
            return clienteDao.consultarCpfLista(cpf);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Cliente> consultarNome(String nome) {
        clienteDao = new ClienteDao();
        try {
            return clienteDao.consultarNome(nome);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Cliente> listar(String sql) {
        clienteDao = new ClienteDao();
        try {
            return clienteDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
