package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.ValoresAcomodacaoDao;
import br.com.travelmate.model.Valoresacomodacao;

/**
 *
 * @author Kamila Rodrigues
 */
public class ValoresAcomodacaoFacade {

	private ValoresAcomodacaoDao valoresAcomodacaoDao;

	public Valoresacomodacao salvar(Valoresacomodacao valoresacomodacao) {
		valoresAcomodacaoDao = new ValoresAcomodacaoDao();
		try {
			return valoresAcomodacaoDao.salvar(valoresacomodacao);
		} catch (SQLException ex) {
			Logger.getLogger(ValoresAcomodacaoFacade.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public List<Valoresacomodacao> listar(String sql) {
		valoresAcomodacaoDao = new ValoresAcomodacaoDao();
		try {
			return valoresAcomodacaoDao.listar(sql);
		} catch (SQLException ex) {
			Logger.getLogger(ValoresAcomodacaoFacade.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public void excluir(int idValorCoProduto) {
		valoresAcomodacaoDao = new ValoresAcomodacaoDao();
		try {
			valoresAcomodacaoDao.excluir(idValorCoProduto);
		} catch (SQLException ex) {
			Logger.getLogger(ValoresAcomodacaoFacade.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public Valoresacomodacao consultar(int idValorCoProduto) {
		valoresAcomodacaoDao = new ValoresAcomodacaoDao();
		try {
			return valoresAcomodacaoDao.consultar(idValorCoProduto);
		} catch (SQLException ex) {
			Logger.getLogger(ValoresAcomodacaoFacade.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
}
