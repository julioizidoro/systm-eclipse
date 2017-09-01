/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.cancelamento;

import java.util.List;

/**
 *
 * @author Wolverine
 */
public class RelatorioCancelamentoFactory {
    
    private static List<RelatorioCancelamentoBean> lista;

    public static List<RelatorioCancelamentoBean> getLista() {
        return lista;
    }

    public static void setLista(List<RelatorioCancelamentoBean> lista) {
        RelatorioCancelamentoFactory.lista = lista;
    }
    
    public static List<RelatorioCancelamentoBean> listar() {
        return lista;
    }
    
    
}
