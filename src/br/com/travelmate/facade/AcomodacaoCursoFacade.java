package br.com.travelmate.facade;

import java.sql.SQLException;

import br.com.travelmate.dao.AcomodacaoCursoDao;
import br.com.travelmate.model.Acomodacaocurso;

public class AcomodacaoCursoFacade {
	
	private AcomodacaoCursoDao acomodacaoCursoDao;
	
	public Acomodacaocurso salvar(Acomodacaocurso acomodacaoCurso) {
		acomodacaoCursoDao = new AcomodacaoCursoDao();
		try {
			return acomodacaoCursoDao.salvar(acomodacaoCurso);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void excluir(int idacomdoacao) {
		acomodacaoCursoDao = new AcomodacaoCursoDao();
		try {
			 acomodacaoCursoDao.excluir(idacomdoacao);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public Acomodacaocurso consultar(String sql) {
		acomodacaoCursoDao = new AcomodacaoCursoDao();
		try {
			return acomodacaoCursoDao.consultar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
