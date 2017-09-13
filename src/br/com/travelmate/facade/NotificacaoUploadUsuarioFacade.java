package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.NotificacaoUploadUsuarioDao;
import br.com.travelmate.model.Notificacaouploadusuario;

public class NotificacaoUploadUsuarioFacade {

	NotificacaoUploadUsuarioDao notificacaoUploadUsuarioDao;
	
	public Notificacaouploadusuario salvar(Notificacaouploadusuario notificacaouploadusuario) {
		notificacaoUploadUsuarioDao = new NotificacaoUploadUsuarioDao();
        try {
            return notificacaoUploadUsuarioDao.salvar(notificacaouploadusuario);
        } catch (SQLException ex) {
            Logger.getLogger(NotificacaoUploadUsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Notificacaouploadusuario> listar(String sql) throws SQLException{
    	notificacaoUploadUsuarioDao = new NotificacaoUploadUsuarioDao();
        return notificacaoUploadUsuarioDao.listar(sql);
    }
   
    public Notificacaouploadusuario consultar(int idnotificacao) throws SQLException{
    	notificacaoUploadUsuarioDao = new NotificacaoUploadUsuarioDao();
        return notificacaoUploadUsuarioDao.consultar(idnotificacao);
    }
    
    public void excluir(int idnotificacao) {
    	notificacaoUploadUsuarioDao = new NotificacaoUploadUsuarioDao();
        try {
        	notificacaoUploadUsuarioDao.excluir(idnotificacao);
        } catch (SQLException ex) {
            Logger.getLogger(NotificacaoUploadUsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
