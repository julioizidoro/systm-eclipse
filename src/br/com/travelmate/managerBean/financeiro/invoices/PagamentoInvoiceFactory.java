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
@SuppressWarnings("unchecked")
public class PagamentoInvoiceFactory {

     private static List<PagamentoInvoiceBean> listaPagamentoInvoice;

    public static List<PagamentoInvoiceBean> getListaPagamentoInvoice() {
        return listaPagamentoInvoice;
    }

    public static void setListaPagamentoInvoice(List<PagamentoInvoiceBean> listaPagamentoInvoice) {
        PagamentoInvoiceFactory.listaPagamentoInvoice = listaPagamentoInvoice;
    }
     
     public static List<PagamentoInvoiceBean> gerarLista(){
        return listaPagamentoInvoice;
    }
    
}
