package br.com.travelmate.managerBean.higherEducation.orcamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.model.Heorcamento;
import br.com.travelmate.model.Heorcamentopais;

@Named
@ViewScoped
public class OrcamentoHePdfMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Heorcamentopais heorcamentopais;
	private Heorcamento heorcamento;
	private List<Heorcamentopais> listaHeorcamentopais;
	private int pais = 0;
	private int contador;
	
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		heorcamento = (Heorcamento) session.getAttribute("heorcamento");
		session.removeAttribute("heorcamento");
		if (heorcamento.getHeorcamentopaisList() == null) {
			heorcamento.setHeorcamentopaisList(new ArrayList<Heorcamentopais>());
		}
		listaHeorcamentopais = heorcamento.getHeorcamentopaisList();
		gerarDescricaoPais();
		contador = 0;
	}


	public Heorcamentopais getHeorcamentopais() {
		return heorcamentopais;
	}


	public void setHeorcamentopais(Heorcamentopais heorcamentopais) {
		this.heorcamentopais = heorcamentopais;
	}


	public Heorcamento getHeorcamento() {
		return heorcamento;
	}


	public void setHeorcamento(Heorcamento heorcamento) {
		this.heorcamento = heorcamento;
	}


	public List<Heorcamentopais> getListaHeorcamentopais() {
		return listaHeorcamentopais;
	}


	public void setListaHeorcamentopais(List<Heorcamentopais> listaHeorcamentopais) {
		this.listaHeorcamentopais = listaHeorcamentopais;
	}
	
	
	public boolean gerarLista(Heorcamentopais he) {
		int contadorLista = listaHeorcamentopais.size();
		if (contador==contadorLista - 1) {   
			return true;
		}
		if (pais == 0) {
			pais = he.getCidade().getPais().getIdpais();
		}
		int idPaisProximo = listaHeorcamentopais.get(contador + 1).getCidade().getPais().getIdpais();
		if (pais == idPaisProximo) {
			contador++;
			return false;
		} else {
			pais = listaHeorcamentopais.get(contador + 1).getCidade().getPais().getIdpais();
			contador++;
			return true;
		}
	}
	
	
	public boolean descricaoPais(Heorcamentopais he) {
		int contadorLista = listaHeorcamentopais.size();
		if (contador==contadorLista - 1) {   
			return true;
		}
		if (pais == 0) {
			pais = he.getCidade().getPais().getIdpais();
		}
		int idPaisProximo = listaHeorcamentopais.get(contador + 1).getCidade().getPais().getIdpais();
		if (pais == idPaisProximo) {
			contador++;
			return false;
		} else {
			pais = listaHeorcamentopais.get(contador + 1).getCidade().getPais().getIdpais();
			contador++;
			return true;
		}
	}
	   
	
	public void gerarDescricaoPais() {
		int contadorLista = listaHeorcamentopais.size();
		for (int i = 0; i < listaHeorcamentopais.size(); i++) {
			if (pais == 0) {
				pais = listaHeorcamentopais.get(i).getCidade().getPais().getIdpais();
			}
			if (contador==contadorLista - 1) {   
				i = 10000;
			}else {
				int idPaisProximo = listaHeorcamentopais.get(contador + 1).getCidade().getPais().getIdpais();
				if (pais != idPaisProximo) {
					String descricao = "";
					for (int j = 0; j < listaHeorcamentopais.get(i).getCidade().getPais().getPaisprodutoList().size(); j++) {
						if (listaHeorcamentopais.get(i).getCidade().getPais().getPaisprodutoList().get(j).getProdutos().getIdprodutos() == 22) {
							descricao = listaHeorcamentopais.get(i).getCidade().getPais().getPaisprodutoList().get(j).getDescricao();
							j = 100000;
						}
					}
					listaHeorcamentopais.get(i).setListar(true);
					listaHeorcamentopais.get(i).setDescricaoPais(descricao);
					pais = idPaisProximo;
				}else {
					listaHeorcamentopais.get(i).setListar(false);
				}
				if (contador==contadorLista - 1) {   
					i = 10000;
				}else {
					contador++;
				}
			}
			
		}
	}
	

}
