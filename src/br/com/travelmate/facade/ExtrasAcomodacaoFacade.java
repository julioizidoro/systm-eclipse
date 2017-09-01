package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.ExtrasAcomodacaoDao;
import br.com.travelmate.model.Extrasacamodacao;  

/**
 *
 * @author Kamila Rodrigues
 */
public class ExtrasAcomodacaoFacade {

	private ExtrasAcomodacaoDao extrasAcomodacaoDao;

	public Extrasacamodacao salvar(Extrasacamodacao extras) {
		extrasAcomodacaoDao = new ExtrasAcomodacaoDao();
		try {
			return extrasAcomodacaoDao.salvar(extras);
		} catch (SQLException ex) {
			Logger.getLogger(ExtrasAcomodacaoFacade.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public List<Extrasacamodacao> listar(String sql) {
		extrasAcomodacaoDao = new ExtrasAcomodacaoDao();
		try {
			return extrasAcomodacaoDao.listar(sql);
		} catch (SQLException ex) {
			Logger.getLogger(ExtrasAcomodacaoFacade.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public void excluir(int id) {
		extrasAcomodacaoDao = new ExtrasAcomodacaoDao();
		try {
			extrasAcomodacaoDao.excluir(id);
		} catch (SQLException ex) {
			Logger.getLogger(ExtrasAcomodacaoFacade.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public Extrasacamodacao consultar(int id) {
		extrasAcomodacaoDao = new ExtrasAcomodacaoDao();
		try {
			return extrasAcomodacaoDao.consultar(id);
		} catch (SQLException ex) {
			Logger.getLogger(ExtrasAcomodacaoFacade.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
}
