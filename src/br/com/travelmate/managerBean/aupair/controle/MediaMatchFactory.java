package br.com.travelmate.managerBean.aupair.controle;

import java.util.List;

/**
 *
 * @author Kamila
 */
public class MediaMatchFactory {

	private static List<MediaMatchBean> listaMatch;

	public static List<MediaMatchBean> getListaMatch() {
		return listaMatch;
	}

	public static void setListaMatch(List<MediaMatchBean> listaMatch) {
		MediaMatchFactory.listaMatch = listaMatch;
	}

	public static List<MediaMatchBean> listar() {
		return listaMatch;
	}

}
