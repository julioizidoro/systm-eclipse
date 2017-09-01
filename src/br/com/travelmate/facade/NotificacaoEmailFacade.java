/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.NotificacaoEmailDao;
import br.com.travelmate.model.Notificacaoemail;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kamila
 */
public class NotificacaoEmailFacade {
    
    NotificacaoEmailDao notificacaoEmailDao;
    
    public Notificacaoemail salvar(Notificacaoemail notificacaoemail){
        notificacaoEmailDao = new NotificacaoEmailDao();
        try {
            return notificacaoEmailDao.salvar(notificacaoemail);
        } catch (SQLException ex) {
            Logger.getLogger(NotificacaoEmailFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Notificacaoemail> listarPorProduto(int idProduto) {
        notificacaoEmailDao = new NotificacaoEmailDao();
        try {
            return notificacaoEmailDao.listarPorProduto(idProduto);
        } catch (SQLException ex) {
            Logger.getLogger(NotificacaoEmailFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public List<Notificacaoemail> listarPorProdutoTipo(int idProduto, String tipoAviso) {
        notificacaoEmailDao = new NotificacaoEmailDao();
        try {
            return notificacaoEmailDao.listarPorProdutoTipo(idProduto, tipoAviso);
        } catch (SQLException ex) {
            Logger.getLogger(NotificacaoEmailFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void excluir(int idNotificacaoemail) {
        notificacaoEmailDao = new NotificacaoEmailDao();
        try {
            notificacaoEmailDao.excluir(idNotificacaoemail);
        } catch (SQLException ex) {
            Logger.getLogger(NotificacaoEmailFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
