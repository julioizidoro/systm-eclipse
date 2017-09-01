/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.facade;

import br.com.travelmate.dao.FaturaFranquiasDao;
import br.com.travelmate.model.Faturafranquias;
import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Wolverine
 */
public class FaturaFranquiasFacade {
    
    private FaturaFranquiasDao faturaFranquiasDao;
    
    public Faturafranquias salvar(Faturafranquias faturafranquias) throws SQLException{
        faturaFranquiasDao = new FaturaFranquiasDao();
        return faturaFranquiasDao.salvar(faturafranquias);
    }
    
    public Faturafranquias getFaturaFranquia(int idVendasComissao)throws SQLException{
        faturaFranquiasDao = new FaturaFranquiasDao();
        return faturaFranquiasDao.getFaturaFranquia(idVendasComissao);
    }
    
    public void excluir(int idFaturaFranquias) throws SQLException{
        faturaFranquiasDao = new FaturaFranquiasDao();
        faturaFranquiasDao.excluir(idFaturaFranquias);
    }
    
    public List<Faturafranquias> listar(String sql)throws SQLException{
        faturaFranquiasDao = new FaturaFranquiasDao();
        return faturaFranquiasDao.listar(sql);
    }
}
