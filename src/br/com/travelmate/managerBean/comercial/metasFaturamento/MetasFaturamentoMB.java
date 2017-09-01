package br.com.travelmate.managerBean.comercial.metasFaturamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.MetaFaturamentoMensalFacade;
import br.com.travelmate.model.Metasfaturamentomensal;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class MetasFaturamentoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Metasfaturamentomensal> listaMetaFaturamentoMensal;
	private String mes;
	private String ano;
	private List<Unidadenegocio> listaUnidade;
	private Unidadenegocio unidade;

	@PostConstruct
	public void init() {
		listarUnidades();
	}

	public List<Metasfaturamentomensal> getListaMetaFaturamentoMensal() {
		return listaMetaFaturamentoMensal;
	}

	public void setListaMetaFaturamentoMensal(List<Metasfaturamentomensal> listaMetaFaturamentoMensal) {
		this.listaMetaFaturamentoMensal = listaMetaFaturamentoMensal;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	public Unidadenegocio getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidadenegocio unidade) {
		this.unidade = unidade;
	}

	public String editarMeta(Metasfaturamentomensal metasfaturamentomensal) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("metasfaturamentomensal", metasfaturamentomensal);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("cadMetasFaturamento", options, null);
		return "";
	}

	public String novaMeta() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("cadMetasFaturamento", options, null);
		return "";
	}

	public void listarMetasFaturmentoMensal() {
		MetaFaturamentoMensalFacade metaFaturamentoMensalFacade = new MetaFaturamentoMensalFacade();
		listaMetaFaturamentoMensal = metaFaturamentoMensalFacade.listar("Select m From Metasfaturamentomensal m");
	}

	public void returnDialogNovo(SelectEvent event) {
		Metasfaturamentomensal metasfaturamentomensal = (Metasfaturamentomensal) event.getObject();
		if (metasfaturamentomensal.getIdmetasfaturamentoemensal() != null) {
			if (listaMetaFaturamentoMensal == null) {
				listaMetaFaturamentoMensal = new ArrayList<>();
			}
			listaMetaFaturamentoMensal.add(metasfaturamentomensal);
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
	}
	
	
	public void returnDialogEdicao(SelectEvent event) {
		Metasfaturamentomensal metasfaturamentomensal = (Metasfaturamentomensal) event.getObject();
		if (metasfaturamentomensal.getIdmetasfaturamentoemensal() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
	}

	public void listarUnidades() {
		listaUnidade = GerarListas.listarUnidade();
		if (listaUnidade == null) {
			listaUnidade = new ArrayList<Unidadenegocio>();
		}
	}

	public void pesquisar() {
		String sql = " Select m From Metasfaturamentomensal m ";

		if (unidade != null || mes.length() > 0 || ano.length() > 0) {
			sql = sql + " Where ";
		}

		if (unidade != null) {
			sql = sql + " m.unidadenegocio.idunidadeNegocio=" + unidade.getIdunidadeNegocio();
			if (mes.length() > 0 || ano.length() > 0) {
				sql = sql + " and ";
			}
		}

		if (mes.length() > 0) {
			sql = sql + " m.mes=" + mes;
			if (ano.length() > 0) {
				sql = sql + " and ";
			}
		}

		if (ano.length() > 0) {
			sql = sql + " m.ano=" + ano;
		}

		MetaFaturamentoMensalFacade mensalFacade = new MetaFaturamentoMensalFacade();
		listaMetaFaturamentoMensal = mensalFacade.listar(sql);
	}

	public void limparPesquisa() {
		listaMetaFaturamentoMensal = new ArrayList<Metasfaturamentomensal>();
		mes = "";
		ano = "";
		unidade = null;
	}

}
