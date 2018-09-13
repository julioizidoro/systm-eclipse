package br.com.travelmate.managerBean.financeiro.contasReceber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.bean.BolinhasBean;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Contasreceber;

@Named
@ViewScoped
public class EditarSituacaoTipoDocMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Contasreceber> listaContas;
	private String tipodocumento;
	private List<BolinhasBean> listaBolinhas;
	private BolinhasBean bolinhas;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaContas = (List<Contasreceber>) session.getAttribute("listaContas");
		session.removeAttribute("listaContas");
		gerarBolinhasBean();
		tipodocumento = listaContas.get(0).getTipodocumento();
	}

	public List<Contasreceber> getListaContas() {
		return listaContas;
	}

	public void setListaContas(List<Contasreceber> listaContas) {
		this.listaContas = listaContas;
	}

	public String getTipodocumento() {
		return tipodocumento;
	}

	public void setTipodocumento(String tipodocumento) {
		this.tipodocumento = tipodocumento;
	}

	public List<BolinhasBean> getListaBolinhas() {
		return listaBolinhas;
	}

	public void setListaBolinhas(List<BolinhasBean> listaBolinhas) {
		this.listaBolinhas = listaBolinhas;
	}

	public BolinhasBean getBolinhas() {
		return bolinhas;
	}

	public void setBolinhas(BolinhasBean bolinhas) {
		this.bolinhas = bolinhas;
	}
	
	public void gerarBolinhasBean() {
		listaBolinhas = new ArrayList<BolinhasBean>();
		BolinhasBean bolinhasBean = new BolinhasBean();
		bolinhasBean.setCor("Verde");
		bolinhasBean.setCaminho("../../resources/img/bolaVerde.png");
		listaBolinhas.add(bolinhasBean);
		bolinhasBean = new BolinhasBean();
		bolinhasBean.setCor("Amarela");
		bolinhasBean.setCaminho("../../resources/img/bolaAmarela.png");
		listaBolinhas.add(bolinhasBean);
		bolinhasBean = new BolinhasBean();
		bolinhasBean.setCor("Vermelha");
		bolinhasBean.setCaminho("../../resources/img/bolaVermelha.png");
		listaBolinhas.add(bolinhasBean);
	}
	
	public String cancelar(){
		listaContas = new ArrayList<Contasreceber>();
		RequestContext.getCurrentInstance().closeDialog(listaContas);
		return "";
	}
	
	
	public String salvar(){
		String situacao;
		if (bolinhas.getCor().equalsIgnoreCase("Vermelha")) {
			situacao ="vm";
		} else if (bolinhas.getCor().equalsIgnoreCase("Verde")) {
			situacao ="vd";
		} else {
			situacao ="am";
		}
		for (int i = 0; i < listaContas.size(); i++) {
			listaContas.get(i).setTipodocumento(tipodocumento);
			listaContas.get(i).setBolinhas(bolinhas);
			listaContas.get(i).setSituacao(situacao);
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			contasReceberFacade.salvar(listaContas.get(i));
			new EventoContasReceberBean("Status alterado para " + situacao, listaContas.get(i), usuarioLogadoMB.getUsuario());
		}
		RequestContext.getCurrentInstance().closeDialog(listaContas);
		return "";
	}
	

}
