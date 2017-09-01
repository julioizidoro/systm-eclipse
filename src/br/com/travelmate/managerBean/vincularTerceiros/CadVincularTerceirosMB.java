package br.com.travelmate.managerBean.vincularTerceiros;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.bean.comissao.CalcularComissaoManualBean;
import br.com.travelmate.facade.TerceirosFacade;
import br.com.travelmate.facade.VendasComissaoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Terceiros;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadVincularTerceirosMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private AplicacaoMB aplicacaoMB;
	private List<Terceiros> listaTerceiros;
	private Terceiros terceiros;
	private Vendas vendas;
	private Vendascomissao vendacomissao;
	private Float comissaoterceiros;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        vendas = (Vendas) session.getAttribute("venda");
        vendacomissao = (Vendascomissao) session.getAttribute("vendacomissao");
        session.removeAttribute("vendacomissao");
        session.removeAttribute("vendas");
		gerarListaTerceiros();
		if (vendacomissao.getTerceiros()!=null){
			terceiros = vendacomissao.getTerceiros();
			comissaoterceiros = vendacomissao.getComissaoterceiros();
		}
	}

	
	

	public Float getComissaoterceiros() {
		return comissaoterceiros;
	}




	public void setComissaoterceiros(Float comissaoterceiros) {
		this.comissaoterceiros = comissaoterceiros;
	}




	public Vendascomissao getVendacomissao() {
		return vendacomissao;
	}




	public void setVendacomissao(Vendascomissao vendacomissao) {
		this.vendacomissao = vendacomissao;
	}





	public List<Terceiros> getListaTerceiros() {
		return listaTerceiros;
	}


	public void setListaTerceiros(List<Terceiros> listaTerceiros) {
		this.listaTerceiros = listaTerceiros;
	}


	public Terceiros getTerceiros() {
		return terceiros;
	}


	public void setTerceiros(Terceiros terceiros) {
		this.terceiros = terceiros;
	}


	public Vendas getVendas() {
		return vendas;
	}


	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}
	
	
	
	public void gerarListaTerceiros(){
		TerceirosFacade terceirosFacade = new TerceirosFacade();
		
		listaTerceiros = terceirosFacade.lista();
		if (listaTerceiros == null) {
			listaTerceiros = new  ArrayList<Terceiros>();
		}
	}
	
	public void salvarTerceiros(){
		VendasComissaoFacade vendasComissaoFacade = new VendasComissaoFacade();
		Vendascomissao vendasComissao = new Vendascomissao();
		vendas.getVendascomissao().setTerceiros(terceiros);
		vendasComissao = vendas.getVendascomissao();
		vendasComissao.setComissaoterceiros(comissaoterceiros);
		vendasComissao = vendasComissaoFacade.salvar(vendasComissao);
		CalcularComissaoManualBean cc = new CalcularComissaoManualBean(aplicacaoMB);
		try {
			cc.recalcular(vendasComissao);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().closeDialog(vendas);
	}
	
	public void cancelar(){
		Mensagem.lancarMensagemInfo("Cancelado", "Venda sem comissão");
		RequestContext.getCurrentInstance().closeDialog(new Vendas());
	}
	
	
}
