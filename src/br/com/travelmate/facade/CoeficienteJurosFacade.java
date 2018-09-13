/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.travelmate.facade;

import br.com.travelmate.dao.CoeficienteJurosDao;
import br.com.travelmate.model.Coeficientejuros;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Wolverine
 */
public class CoeficienteJurosFacade {
    
    CoeficienteJurosDao coeficienteJurosDao;
    
    public Coeficientejuros salvar(Coeficientejuros coeficientejuros) {
        coeficienteJurosDao = new CoeficienteJurosDao();
        try {
			return coeficienteJurosDao.salvar(coeficientejuros);
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
    }
    
    public Coeficientejuros consultar(int numeroParcelas, String tipo) {
        coeficienteJurosDao = new CoeficienteJurosDao();
        try {
			return coeficienteJurosDao.consultar(numeroParcelas, tipo);
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
    }
    
    public List<Coeficientejuros> listar() {
        coeficienteJurosDao = new CoeficienteJurosDao();
        try {
			return coeficienteJurosDao.listar();
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
    }
    
    public List<Coeficientejuros> listar(String sql) {
        coeficienteJurosDao = new CoeficienteJurosDao();
        try {
			return coeficienteJurosDao.listar(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
    
}
