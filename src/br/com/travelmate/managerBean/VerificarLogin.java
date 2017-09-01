package br.com.travelmate.managerBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import br.com.travelmate.bean.ListaLogadosBean;

@Named
@ApplicationScoped
public class VerificarLogin implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ListaLogadosBean> lista;
	
	@PostConstruct
	public void init(){
		lista = new ArrayList<ListaLogadosBean>();
	}


	
	public List<ListaLogadosBean> getLista() {
		return lista;
	}

	public void setLista(List<ListaLogadosBean> lista) {
		this.lista = lista;
	}

	public boolean verificarUsuarioLogado(int id, long tempoAdicional, String ip) {
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getIdUsuario() == id) {
				if (!ip.equalsIgnoreCase(lista.get(i).getIp())) {
					if (!verificarTempoLimite(lista.get(i).getTempo(), tempoAdicional)) {
						return true;
					} else {
						lista.get(i).setTempo(System.currentTimeMillis());
						return false;
					}
				} else {
					return false;
				}
			}
		}
		ListaLogadosBean listaLogadosBean = new ListaLogadosBean();
		listaLogadosBean.setIdUsuario(id);
		listaLogadosBean.setTempo(System.currentTimeMillis());
		listaLogadosBean.setIp(ip);
		lista.add(listaLogadosBean);
		return false;
	}

	public void deslogarUsuarioSessao(int id){
		for (int i = 0; i < lista.size(); i++) {
			if(lista.get(i).getIdUsuario()==id){
				lista.remove(i);
			}
		}
	}
	
	public boolean verificarTempoLimite(long tempoLogado, long tempoAdicional){
		long tempoAtual = System.currentTimeMillis();
		tempoLogado = tempoLogado + tempoAdicional;
		if (tempoAtual>tempoLogado){
			return false;
		}
		return true;
	}
	
	
	public void atualizarTempo(int id){
		for (int i = 0; i < lista.size(); i++) {
			if(lista.get(i).getIdUsuario()==id){
				lista.get(i).setTempo(System.currentTimeMillis());
			}
		}	
	}
	
	
	
}
