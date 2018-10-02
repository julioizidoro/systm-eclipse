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
			  
			return null;
		}
	}
	
	public Acomodacao consultar(String sql) {
		acomodacaoDao = new AcomodacaoDao();
		try {
			return acomodacaoDao.consultar(sql);
		} catch (SQLException e) {
			  
			return null;
		}
	}
	
	public List<Acomodacao> lista(String sql){
		acomodacaoDao = new AcomodacaoDao();
		try {
			return acomodacaoDao.lista(sql);
		} catch (SQLException e) {
			  
			return null;
		}
	}
	public void excluir(int idacomdoacao) {
		acomodacaoDao = new AcomodacaoDao();
		try {
			acomodacaoDao.excluir(idacomdoacao);
		} catch (SQLException e) {
			  
		}
	}

}
