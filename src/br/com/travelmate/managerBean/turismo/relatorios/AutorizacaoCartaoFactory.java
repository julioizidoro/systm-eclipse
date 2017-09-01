/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.turismo.relatorios;

import java.util.List;

/**
 *
 * @author Wolverine
 */

public class AutorizacaoCartaoFactory {
    
    public static List<AutorizacaoDebitoBean> listaAutorizacaoCartao;

    public static List<AutorizacaoDebitoBean> getListaAutorizacaoCartao() {
        return listaAutorizacaoCartao;
    }

    public static void setListaAutorizacaoCartao(List<AutorizacaoDebitoBean> listaAutorizacaoCartao) {
        AutorizacaoCartaoFactory.listaAutorizacaoCartao = listaAutorizacaoCartao;
    }
    
    public static List<AutorizacaoDebitoBean> gerarLista(){
        return listaAutorizacaoCartao;
    }
    
}
