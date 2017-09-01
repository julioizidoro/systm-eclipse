package br.com.travelmate.managerBean.logvenda;
 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 

import javax.annotation.PostConstruct; 
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped; 
import javax.inject.Named;
import javax.servlet.http.HttpSession;  
 
import br.com.travelmate.facade.LogVendaFacade;  
import br.com.travelmate.model.Logvenda; 
import br.com.travelmate.model.Vendas; 

@Named
@ViewScoped
public class LogVendaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Logvenda> listaLogVenda;
	private Vendas vendas;
	private String voltar;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		vendas = (Vendas) session.getAttribute("vendas");
		voltar = (String) session.getAttribute("voltar");
		if (vendas != null) {
			gerarListaLogVenda();
		}
	}

	public List<Logvenda> getListaLogVenda() {
		return listaLogVenda;
	}

	public void setListaLogVenda(List<Logvenda> listaLogVenda) {
		this.listaLogVenda = listaLogVenda;
	}

	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public String getVoltar() {
		return voltar;
	}

	public void setVoltar(String voltar) {
		this.voltar = voltar;
	}

	public void gerarListaLogVenda() {
		LogVendaFacade logVendaFacade = new LogVendaFacade();
		listaLogVenda = logVendaFacade
					.listar("Select l from Logvenda l where l.vendas.idvendas=" + vendas.getIdvendas()+" order by l.data");
		if (listaLogVenda == null) {
			listaLogVenda = new ArrayList<Logvenda>();
		} 
	} 

	public String voltarTela() {
		return voltar;
	}
 
}
