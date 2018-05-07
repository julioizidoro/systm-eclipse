package br.com.travelmate.managerBean.cancelamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.CancelamentoFacade;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Vendas;



@Named
@ViewScoped
public class SelecionarCreditoCancelamentoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Cancelamento> listaCancelamento;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        String idcliente = (String) session.getAttribute("idcliente");
        session.removeAttribute("idcliente");
		String sql = "SELECT c FROM Cancelamento c where c.idvendacredito=0 and c.vendas.cliente.idcliente=" + Integer.parseInt(idcliente) + " and c.situacao='FINALIZADA' and c.formapagamento<>'Reembolso' " +
				" order by c.datasolicitacao";
		CancelamentoFacade cancelamentoFacade = new CancelamentoFacade();
		listaCancelamento = cancelamentoFacade.listar(sql);
		if (listaCancelamento==null){
			listaCancelamento = new ArrayList<Cancelamento>();
		}
	}

	public List<Cancelamento> getListaCancelamento() {
		return listaCancelamento;
	}

	public void setListaCancelamento(List<Cancelamento> listaCancelamento) {
		this.listaCancelamento = listaCancelamento;
	}
	
	public String selecioneCancelamento(Cancelamento cancelamento){
		CancelamentoFacade cancelamentoFacade = new CancelamentoFacade();
		cancelamento.setIdvendacredito(cancelamento.getVendas().getIdvendas());
		cancelamento = cancelamentoFacade.salvar(cancelamento);
		RequestContext.getCurrentInstance().closeDialog(cancelamento);
		return "";
	}
	
	public String fechar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	

}
