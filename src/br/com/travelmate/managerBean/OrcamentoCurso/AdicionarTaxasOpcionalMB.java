package br.com.travelmate.managerBean.OrcamentoCurso;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class AdicionarTaxasOpcionalMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ResultadoOrcamentoBean resultadoOrcamentoBean;
	private List<ProdutosOrcamentoBean> listaOpcionais;
	
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		resultadoOrcamentoBean = (ResultadoOrcamentoBean) session.getAttribute("resultadoOrcamentoBean");
		listaOpcionais = (List<ProdutosOrcamentoBean>) session.getAttribute("listaOpcionais");
		session.removeAttribute("listaOpcionais");
		session.removeAttribute("resultadoOrcamentoBean");
	}
	
	
	
	public ResultadoOrcamentoBean getResultadoOrcamentoBean() {
		return resultadoOrcamentoBean;
	}



	public void setResultadoOrcamentoBean(ResultadoOrcamentoBean resultadoOrcamentoBean) {
		this.resultadoOrcamentoBean = resultadoOrcamentoBean;
	}






	public List<ProdutosOrcamentoBean> getListaOpcionais() {
		return listaOpcionais;
	}



	public void setListaOpcionais(List<ProdutosOrcamentoBean> listaOpcionais) {
		this.listaOpcionais = listaOpcionais;
	}



	public String retornarValorString(Float valor, String sigla) {
		String svalor = "";
		if (valor != null) {
			if (sigla == null || sigla.equalsIgnoreCase("")) {
				sigla = "R$";
			}
			svalor = sigla + " " + Formatacao.formatarFloatString(valor);
		}
		return svalor;
	}
	
	public String adicionar(ProdutosOrcamentoBean po) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaOpcionais.remove(po);
		session.setAttribute("listaOpcionais", listaOpcionais);
		po.setSelecionado(true);
		po.setSelecionadoOpcional(true);
		RequestContext.getCurrentInstance().closeDialog(po);
		return "";
	}
	    
	public String cancelar() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("listaOpcionais", listaOpcionais);
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

}
