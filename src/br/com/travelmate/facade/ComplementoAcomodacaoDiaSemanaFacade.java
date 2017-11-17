package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import br.com.travelmate.dao.ComplementoAcomodacaoDiaSemanaDao; 
import br.com.travelmate.model.Complementoacomodacaodiasemana; 

public class ComplementoAcomodacaoDiaSemanaFacade {
	
private ComplementoAcomodacaoDiaSemanaDao complementoAcomodacaoDao;
	
	public Complementoacomodacaodiasemana salvar(Complementoacomodacaodiasemana complemento) {
		complementoAcomodacaoDao = new ComplementoAcomodacaoDiaSemanaDao();
		try {
			return complementoAcomodacaoDao.salvar(complemento);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Complementoacomodacaodiasemana consultar(int idComplemento) {
		complementoAcomodacaoDao = new  ComplementoAcomodacaoDiaSemanaDao();
		try {
			return complementoAcomodacaoDao.consultar(idComplemento);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Complementoacomodacaodiasemana consultar(String sql) {
		complementoAcomodacaoDao = new ComplementoAcomodacaoDiaSemanaDao();
        try {
            return complementoAcomodacaoDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ComplementoAcomodacaoDiaSemanaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
	
	public void excluir(int id) {
		complementoAcomodacaoDao = new ComplementoAcomodacaoDiaSemanaDao();
       try {
    	   complementoAcomodacaoDao.excluir(id);
       } catch (SQLException ex) {
           Logger.getLogger(ComplementoAcomodacaoDiaSemanaFacade.class.getName()).log(Level.SEVERE, null, ex);
       }
   }

}
