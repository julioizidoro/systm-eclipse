package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.CondicaoCancelamentoDao;
import br.com.travelmate.model.Condicaocancelamento;

public class CondicaoCancelamentoFacade {
	
	CondicaoCancelamentoDao condicaoCancelamentoDao;
	
	public Condicaocancelamento salvar(Condicaocancelamento condicaocancelamento){
		condicaoCancelamentoDao = new CondicaoCancelamentoDao();
		try {
			return condicaoCancelamentoDao.salvar(condicaocancelamento);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Condicaocancelamento> listar(String sql){
		condicaoCancelamentoDao = new CondicaoCancelamentoDao();
		try {
			return condicaoCancelamentoDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Condicaocancelamento consultar(int idCondicaocancelamento) {
		condicaoCancelamentoDao = new CondicaoCancelamentoDao();
        try {
            return condicaoCancelamentoDao.consultar(idCondicaocancelamento);
        } catch (SQLException ex) {
            Logger.getLogger(CondicaoCancelamentoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    

}
