package br.com.travelmate.managerBean.higherEducation.orcamento;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.HeorcamentoFacade;
import br.com.travelmate.facade.HeorcamentopaisFacade;
import br.com.travelmate.facade.PublicidadeFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.orcamentoManual.CadOrcamentoManualMB;
import br.com.travelmate.model.Heorcamento;
import br.com.travelmate.model.Heorcamentopais;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Publicidade;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadOrcamentoHeMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Lead lead;
	private Publicidade publicidade;
	private List<Publicidade> listaPublicidades;
	private List<Heorcamentopais> listaHeOrcamentoPais;
	private Heorcamento heorcamento;
	private List<Moedas> listaMoedas;
	private Moedas moeda;
	
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		lead = (Lead) session.getAttribute("lead");
		heorcamento = (Heorcamento) session.getAttribute("heorcamento");
		session.removeAttribute("lead");
		gerarListaPublicidade();
		carregarComboMoedas();
		if (heorcamento == null) {
			heorcamento = new Heorcamento();
		}else {
			HeorcamentopaisFacade heorcamentopaisFacade = new HeorcamentopaisFacade();
			listaHeOrcamentoPais = heorcamentopaisFacade.listar("SELECT h FROM Heorcamentopais h WHERE h.heorcamento.idheorcamento=" + heorcamento.getIdheorcamento());
			if (listaHeOrcamentoPais == null) {
				listaHeOrcamentoPais = new ArrayList<Heorcamentopais>();
			}
			verificarMoeda();
		}
	}


	public Lead getLead() {
		return lead;
	}


	public void setLead(Lead lead) {
		this.lead = lead;
	}


	public Publicidade getPublicidade() {
		return publicidade;
	}


	public void setPublicidade(Publicidade publicidade) {
		this.publicidade = publicidade;
	}


	public List<Publicidade> getListaPublicidades() {
		return listaPublicidades;
	}


	public void setListaPublicidades(List<Publicidade> listaPublicidades) {
		this.listaPublicidades = listaPublicidades;
	}

	
	public List<Heorcamentopais> getListaHeOrcamentoPais() {
		return listaHeOrcamentoPais;
	}


	public void setListaHeOrcamentoPais(List<Heorcamentopais> listaHeOrcamentoPais) {
		this.listaHeOrcamentoPais = listaHeOrcamentoPais;
	}


	public Heorcamento getHeorcamento() {
		return heorcamento;
	}


	public void setHeorcamento(Heorcamento heorcamento) {
		this.heorcamento = heorcamento;
	}


	public List<Moedas> getListaMoedas() {
		return listaMoedas;
	}


	public void setListaMoedas(List<Moedas> listaMoedas) {
		this.listaMoedas = listaMoedas;
	}


	public Moedas getMoeda() {
		return moeda;
	}


	public void setMoeda(Moedas moeda) {
		this.moeda = moeda;
	}


	public void gerarListaPublicidade() {
		PublicidadeFacade publicidadeFacade = new PublicidadeFacade();
		try {
			listaPublicidades = publicidadeFacade.listar();
			if (listaPublicidades == null) {
				listaPublicidades = new ArrayList<Publicidade>();
			}
		} catch (SQLException ex) {
			Logger.getLogger(CadOrcamentoManualMB.class.getName()).log(Level.SEVERE, null, ex);
			Mensagem.lancarMensagemErro("Erro Listar Publicidade", "ERRO");
		}
	}
	
	
	public void adicionarPrograma(){
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("cadProgramaHe", options, null);
	}
	
	public void retornoDialogPrograma(SelectEvent event) {
		Heorcamentopais heorcamentopais = (Heorcamentopais) event.getObject();
		if (heorcamentopais != null) {
			if (listaHeOrcamentoPais == null) {
				listaHeOrcamentoPais = new ArrayList<Heorcamentopais>();
			}
			listaHeOrcamentoPais.add(heorcamentopais);
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
	}
	
	
	public void excluirPrograma(String ilinha) {
		int linha = Integer.parseInt(ilinha);
		Heorcamentopais heorcamentopais = listaHeOrcamentoPais.get(linha);
		listaHeOrcamentoPais.remove(linha);
		if (heorcamento.getIdheorcamento() != null) {
			HeorcamentopaisFacade heorcamentopaisFacade = new HeorcamentopaisFacade();
			heorcamentopaisFacade.remover(heorcamentopais);
		}
	}
	
	
	public String salvar() {
		ClienteFacade clienteFacade = new ClienteFacade();
		clienteFacade.salvar(lead.getCliente());
		heorcamento.setCliente(lead.getCliente());
		heorcamento.setUsuario(usuarioLogadoMB.getUsuario());
		heorcamento.setDataemissao(new Date());
		heorcamento.setHoraemissao(Formatacao.foramtarHoraString());
		if (moeda != null) {
			heorcamento.setSigla(moeda.getSigla());
		}
		HeorcamentoFacade heorcamentoFacade = new HeorcamentoFacade();
		heorcamento = heorcamentoFacade.salvar(heorcamento);
		if (listaHeOrcamentoPais == null) {
			listaHeOrcamentoPais = new ArrayList<Heorcamentopais>();
		}
		for (int i = 0; i < listaHeOrcamentoPais.size(); i++) {
			listaHeOrcamentoPais.get(i).setHeorcamento(heorcamento);
			HeorcamentopaisFacade heorcamentopaisFacade = new HeorcamentopaisFacade();
			heorcamentopaisFacade.salvar(listaHeOrcamentoPais.get(i));
		}
		Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		return "orcamentoHe";
	}
	
	
	public String cancelar() {
		return "orcamentoHe";
	}
	
	
	public String retornaHistoricoLead() {
		if (lead != null) {
			if (lead.getIdlead() != null) {
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
				session.setAttribute("lead", lead);
				session.setAttribute("posicao", 0);
				return "historicoCliente";
			}
		}
		return "";
	}
	
	
	public void carregarComboMoedas() {
		CambioFacade cambioFacade = new CambioFacade();
		listaMoedas = cambioFacade.listaMoedas();
		if (listaMoedas == null) {
			listaMoedas = new ArrayList<Moedas>();
		}
	}
	
	public void verificarMoeda() {
		for (int i = 0; i < listaMoedas.size(); i++) {
			if (listaMoedas.get(i).getSigla().equalsIgnoreCase(heorcamento.getSigla())) {
				moeda = listaMoedas.get(i);
				i = 100000;
			}
		}
	}
	
	
}
