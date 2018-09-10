package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.travelmate.dao.CancelamentoDao;
import br.com.travelmate.model.Cancelamento;

public class CancelamentoFacade {
	
	CancelamentoDao cancelamentoDao;
	
	public Cancelamento salvar(Cancelamento cancelamento){
		cancelamentoDao = new CancelamentoDao();
		try {
			return cancelamentoDao.salvar(cancelamento);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Cancelamento> listar(String sql){
		cancelamentoDao = new CancelamentoDao();
		try {
			return cancelamentoDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public Cancelamento consultar(int idcancelamento) {
		cancelamentoDao = new CancelamentoDao();
		try {
			return cancelamentoDao.consultar(idcancelamento);
		} catch (SQLException ex) {
			Logger.getLogger(CrmCobrancaFacade.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null, "Erro consultar Curso " + ex);
			return null;
		}
	}

}
