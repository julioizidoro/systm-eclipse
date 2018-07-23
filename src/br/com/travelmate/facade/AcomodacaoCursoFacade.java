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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
