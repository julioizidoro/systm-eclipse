package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.ControleSeguroDao;
import br.com.travelmate.dao.SeguroViagemDao;
import br.com.travelmate.model.Controleseguro;
import br.com.travelmate.model.Seguroviagem;

public class ControleSeguroFacade {

	private ControleSeguroDao controleSeguroDao;

	public Controleseguro controleSeguro(int idVendas) {
		controleSeguroDao = new ControleSeguroDao();
		try {
			return controleSeguroDao.controleSeguro(idVendas);
		} catch (SQLException ex) {
			Logger.getLogger(ControleSeguroFacade.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public List<Controleseguro> listar(String sql) {
		controleSeguroDao = new ControleSeguroDao();
		try {
			return controleSeguroDao.listar(sql);
		} catch (SQLException ex) {
			Logger.getLogger(ControleSeguroFacade.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public Controleseguro salvarControle(Controleseguro controle) {
		controleSeguroDao = new ControleSeguroDao();
		try {
			return controleSeguroDao.salvarControle(controle);
		} catch (SQLException ex) {
			Logger.getLogger(ControleSeguroFacade.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

}
