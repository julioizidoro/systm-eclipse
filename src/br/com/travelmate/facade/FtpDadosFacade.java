/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;


import java.sql.SQLException;

import br.com.travelmate.dao.FtpDadosDao;
import br.com.travelmate.model.Ftpdados;


/**
 *
 * @author Wolverine
 */
public class FtpDadosFacade {
    
    FtpDadosDao ftpDadosDao;
    
    public Ftpdados getFTPDados() throws SQLException{
        ftpDadosDao = new FtpDadosDao();
        try {
			return ftpDadosDao.getFTPDados();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
        
    }
    
}
