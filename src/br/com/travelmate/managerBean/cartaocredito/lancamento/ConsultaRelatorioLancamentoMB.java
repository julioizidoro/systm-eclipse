package br.com.travelmate.managerBean.cartaocredito.lancamento;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.model.Cartaocreditolancamento;

@Named
@ViewScoped
public class ConsultaRelatorioLancamentoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Cartaocreditolancamento> listaLancamento;
	private float valorTotal;
	
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaLancamento = (List<Cartaocreditolancamento>) session.getAttribute("listaLancamento");
		session.removeAttribute("listaLancamento");
		for (int i = 0; i < listaLancamento.size(); i++) {
			valorTotal = valorTotal + listaLancamento.get(i).getValorinformado();
		}
	}



	public List<Cartaocreditolancamento> getListaLancamento() {
		return listaLancamento;
	}



	public void setListaLancamento(List<Cartaocreditolancamento> listaLancamento) {
		this.listaLancamento = listaLancamento;
	}



	public float getValorTotal() {
		return valorTotal;
	}



	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}
	

}
