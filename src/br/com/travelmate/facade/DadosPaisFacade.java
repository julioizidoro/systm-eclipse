package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.DadosPaisDao;
import br.com.travelmate.model.Dadospais;

public class DadosPaisFacade {
	
	private DadosPaisDao dadosPaisDao;
	
	public Dadospais salvar(Dadospais dadosPais) {
		dadosPaisDao = new DadosPaisDao();
		try {
			return dadosPaisDao.salvar(dadosPais);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Dadospais consultar(int idDadosPais){
		dadosPaisDao = new DadosPaisDao();
		try {
			return dadosPaisDao.consultar(idDadosPais);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Dadospais consultarVendas(int idVenda) {
		dadosPaisDao = new DadosPaisDao();
        try {
            return dadosPaisDao.consultarVenda(idVenda);
        } catch (SQLException ex) {
        	 Logger.getLogger(DadosPaisFacade.class.getName()).log(Level.SEVERE, null, ex);
             return null;
        }
    }
}
