/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.financeiro.invoices;

import java.util.List;

/**
 *
 * @author Wolverine
 */
public class PerdaGanhoCambialFactory {
    
     private static List<PerdaGanhoCambialBean> listaPerdaGanhoCambia;

    public static List<PerdaGanhoCambialBean> getListaPerdaGanhoCambia() {
        return listaPerdaGanhoCambia;
    }

    public static void setListaPerdaGanhoCambia(List<PerdaGanhoCambialBean> listaPerdaGanhoCambia) {
        PerdaGanhoCambialFactory.listaPerdaGanhoCambia = listaPerdaGanhoCambia;
    }

    
     
    public static List<PerdaGanhoCambialBean> listar(){
        return listaPerdaGanhoCambia;
    }
    
}
