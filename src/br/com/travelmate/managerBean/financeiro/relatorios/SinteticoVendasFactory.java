package br.com.travelmate.managerBean.financeiro.relatorios;

import java.util.List;

public class SinteticoVendasFactory {
	
	private static List<SinteticoVendasBean> lista;

	public static List<SinteticoVendasBean> getLista() {
		return lista;
	}

	public static void setLista(List<SinteticoVendasBean> lista) {
		SinteticoVendasFactory.lista = lista;
	}
	
	public static List<SinteticoVendasBean> listar(){
		return lista;
	}

}
