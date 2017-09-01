/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;
import br.com.travelmate.dao.ContasPagarDao;
import br.com.travelmate.model.Contaspagar;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julio
 */
public class ContasPagarFacade {
     
    ContasPagarDao contasPagarDao;
    
    public Contaspagar salvar(Contaspagar conta) {
        contasPagarDao = new ContasPagarDao();
        try {
            return contasPagarDao.salvar(conta);
        } catch (SQLException ex) {
            Logger.getLogger(ContasPagarFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Contaspagar> listar(String sql){
        contasPagarDao = new ContasPagarDao();
        try {
            return contasPagarDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ContasReceberFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void excluir(int idContapagar) {
        contasPagarDao = new ContasPagarDao();
        try {
            contasPagarDao.excluir(idContapagar);
        } catch (SQLException ex) {
            Logger.getLogger(ContasReceberFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public float gerarSaldoInicial(Date dataInicio, int idbanco){
    	contasPagarDao = new ContasPagarDao();
    	try {
			return contasPagarDao.gerarSaldoInicial(dataInicio, idbanco);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0.0f;
		}
    }
}
