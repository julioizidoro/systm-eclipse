package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.NotificacaoDao;
import br.com.travelmate.model.Notificacao;

public class NotificacaoFacade {
	
	NotificacaoDao notificacaoDao;
	
	public Notificacao salvar(Notificacao notificacao){
		notificacaoDao = new NotificacaoDao();
		try {
			return notificacaoDao.salvar(notificacao);
		} catch (SQLException e) {
			  
			return  null;
		}
	}
	
	public List<Notificacao> listar(String sql){
		notificacaoDao = new NotificacaoDao();
		try {
			return notificacaoDao.listar(sql);
		} catch (SQLException e) {
			  
			return null;
		}
	}

}
