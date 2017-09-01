/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.OrcamentoCurso.pdf;

import java.util.List;

/**
 *
 * @author Wolverine
 */
public class OrcamentoPDFFactory {
    
    private static List<OrcamentoPDFBean> lista;

    public static List<OrcamentoPDFBean> getLista() {
        return lista;
    }

    public static void setLista(List<OrcamentoPDFBean> lista) {
        OrcamentoPDFFactory.lista = lista;
    }
    
    public static List<OrcamentoPDFBean> gerarLista(){
        return lista;
    }
    
}
