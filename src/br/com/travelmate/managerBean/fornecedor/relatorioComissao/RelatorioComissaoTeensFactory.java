/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.fornecedor.relatorioComissao;

import java.util.List;

/**
 *
 * @author wolverine
 */
public class RelatorioComissaoTeensFactory {
    
    public static List<RelatorioComissaoTeensBean> lista;

    public static List<RelatorioComissaoTeensBean> getLista() {
        return lista;
    }

    public static void setLista(List<RelatorioComissaoTeensBean> lista) {
        RelatorioComissaoTeensFactory.lista = lista;
    }
    
    public static List<RelatorioComissaoTeensBean> listar(){
        return lista;
    }
    
}
