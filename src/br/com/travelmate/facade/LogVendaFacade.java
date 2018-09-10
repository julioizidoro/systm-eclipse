package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.travelmate.dao.LogVendaDao;
import br.com.travelmate.model.Logvenda;

public class LogVendaFacade {
	
	private LogVendaDao logVendaDao;
	
	
	public Logvenda salvar(Logvenda logVenda) {
		logVendaDao = new LogVendaDao();
		try {
			return logVendaDao.salvar(logVenda);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Logvenda> listar(String sql){
		logVendaDao = new LogVendaDao();
		try {
			return logVendaDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Logvenda consultar(int idVenda){
		logVendaDao = new LogVendaDao();
        try {
            return logVendaDao.consultar(idVenda);
        } catch (SQLException ex) {
            Logger.getLogger(LogVendaFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Consultar Log venda" + ex);
            return null;
        }
    } 

}
