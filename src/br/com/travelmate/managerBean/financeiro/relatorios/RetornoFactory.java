package br.com.travelmate.managerBean.financeiro.relatorios;

import java.util.List;

public class RetornoFactory {
	
	public static List<RetornoBean> lista;

	public static List<RetornoBean> getLista() {
		return lista;
	}

	public static void setLista(List<RetornoBean> lista) {
		RetornoFactory.lista = lista;
	}
	
	public static List<RetornoBean> listar(){
        return lista;
    }

}
