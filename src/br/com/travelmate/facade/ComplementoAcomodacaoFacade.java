package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.ComplementoAcomodacaoDao;
import br.com.travelmate.model.Complementoacomodacao;

public class ComplementoAcomodacaoFacade {
	
private ComplementoAcomodacaoDao complementoAcomodacaoDao;
	
	public Complementoacomodacao salvar(Complementoacomodacao complemento) {
		complementoAcomodacaoDao = new ComplementoAcomodacaoDao();
		try {
			return complementoAcomodacaoDao.salvar(complemento);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Complementoacomodacao consultar(int idComplemento) {
		complementoAcomodacaoDao = new  ComplementoAcomodacaoDao();
		try {
			return complementoAcomodacaoDao.consultar(idComplemento);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Complementoacomodacao consultar(String sql) {
		complementoAcomodacaoDao = new ComplementoAcomodacaoDao();
        try {
            return complementoAcomodacaoDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ComplementoAcomodacaoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
	
	public void excluir(int id) {
		complementoAcomodacaoDao = new ComplementoAcomodacaoDao();
       try {
    	   complementoAcomodacaoDao.excluir(id);
       } catch (SQLException ex) {
           Logger.getLogger(ComplementoAcomodacaoFacade.class.getName()).log(Level.SEVERE, null, ex);
       }
   }

}
