package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.travelmate.dao.UsuarioPontosDao;
import br.com.travelmate.model.Usuariopontos;

public class UsuarioPontosFacade {
	
	private UsuarioPontosDao usuarioPontosDao; 
	
	public Usuariopontos salvar(Usuariopontos usuariopontos) {
		usuarioPontosDao = new UsuarioPontosDao();
		try {
			return usuarioPontosDao.salvar(usuariopontos);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Usuariopontos> listar(String sql) {
		usuarioPontosDao = new UsuarioPontosDao();
		try {
			return usuarioPontosDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Usuariopontos consultar(String sql) {
		usuarioPontosDao = new UsuarioPontosDao();
		try {
			return usuarioPontosDao.consultar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
