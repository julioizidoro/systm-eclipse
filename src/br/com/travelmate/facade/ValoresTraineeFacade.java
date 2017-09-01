package br.com.travelmate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.travelmate.dao.ValoresTraineeDao;
import br.com.travelmate.model.Valorestrainee;

public class ValoresTraineeFacade {
	private ValoresTraineeDao valoresTraineeDao;

    
    public Valorestrainee salvar(Valorestrainee valor) {
    	valoresTraineeDao = new ValoresTraineeDao();
        try {
            return valoresTraineeDao.salvar(valor);
        } catch (SQLException ex) {
            Logger.getLogger(ValoresTraineeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public Valorestrainee consultar(int idvalor)  {
    	valoresTraineeDao = new ValoresTraineeDao();
        try {
            return valoresTraineeDao.consultar(idvalor);
        } catch (SQLException ex) {
            Logger.getLogger(ValoresTraineeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<Valorestrainee> listar(String sql) {
    	valoresTraineeDao = new ValoresTraineeDao();
        try {
            return valoresTraineeDao.listar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ValoresTraineeFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
