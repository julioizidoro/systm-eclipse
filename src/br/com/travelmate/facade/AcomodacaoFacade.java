package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.AcomodacaoDao;
import br.com.travelmate.model.Acomodacao;

public class AcomodacaoFacade {
	
	private AcomodacaoDao acomodacaoDao;
	
	public Acomodacao salvar(Acomodacao acomodacao) {
		acomodacaoDao = new AcomodacaoDao();
		try {
			return acomodacaoDao.salvar(acomodacao);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Acomodacao consultar(String sql) {
		acomodacaoDao = new AcomodacaoDao();
		try {
			return acomodacaoDao.consultar(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Acomodacao> lista(String sql){
		acomodacaoDao = new AcomodacaoDao();
		try {
			return acomodacaoDao.lista(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
