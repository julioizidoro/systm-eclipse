/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.travelmate.dao.TraineeDao;
import br.com.travelmate.model.Controletrainee;
import br.com.travelmate.model.Trainee;


/**
 *
 * @author Wolverine
 */
public class TraineeFacade {
    
    TraineeDao traineeDao;
    
    public Trainee salvar(Trainee trainee) {
        traineeDao=new TraineeDao();
        try {
			return traineeDao.salvar(trainee);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public Trainee consultar(int idVenda)  {
        traineeDao = new TraineeDao();
        try {
			return traineeDao.consultar(idVenda);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public void excluir(int idTrainee) {
        traineeDao = new TraineeDao();
        try {
			traineeDao.excluir(idTrainee);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public List<Trainee> lista(String sql) {
    	 traineeDao = new TraineeDao();
        try {
            return traineeDao.lista(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TraineeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
       
    public ResultSet ExportarExcel(String nomeRelatorio, String local, String porta, String senha, String banco, String usuario, String caminhoSalvarExcel, int idUnidade) throws IOException {
        traineeDao = new TraineeDao();
        return traineeDao.ExportarExcel(nomeRelatorio, local, porta, senha, banco, usuario, caminhoSalvarExcel, idUnidade);
    }
    
    
    public Controletrainee salvar(Controletrainee controle){
    	traineeDao = new TraineeDao();
        try {
            return traineeDao.salvar(controle);
        } catch (SQLException ex) {
            Logger.getLogger(TraineeFacade.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Controle Trainee " + ex);
            return null;
        }
    }
    

    public List<Controletrainee> listaControle(String sql) {
    	 traineeDao = new TraineeDao();
        try {
            return traineeDao.listaControle(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TraineeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public Controletrainee consultarControle(int idVenda)  {
        traineeDao = new TraineeDao();
        try {
			return traineeDao.consultarControle(idVenda);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }
}
