/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.travelmate.managerBean.financeiro.relatorios;

import java.util.List;

/**
 *
 * @author Wolverine
 */

public class ConciliacaoFactory {
    
    private static List<ConciliacaoBean> listaConciliacao;

    public static List<ConciliacaoBean> getListaConciliacao() {
        return listaConciliacao;
    }

    public static void setListaConciliacao(List<ConciliacaoBean> listaConciliacao) {
        ConciliacaoFactory.listaConciliacao = listaConciliacao;
    }
    
    public static List<ConciliacaoBean> listar(){
        return listaConciliacao;
    }
    
}
