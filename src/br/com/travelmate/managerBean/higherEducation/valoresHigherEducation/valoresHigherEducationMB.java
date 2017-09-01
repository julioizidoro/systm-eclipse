package br.com.travelmate.managerBean.higherEducation.valoresHigherEducation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.ValoresHeFacade; 
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Pais; 
import br.com.travelmate.model.Valoreshe; 
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class valoresHigherEducationMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private List<Valoreshe> listaValores;
	private Fornecedorcidade fornecedorcidade;
	private List<Pais> listaPais;
	private Cidade cidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Pais pais;
	private String sql; 

	@PostConstruct()
	public void init() {
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar();
		sql = "select v from Valoreshe v where v.situacao=true";
		carregarValores();
	}

	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}

	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}
 
	public List<Valoreshe> getListaValores() {
		return listaValores;
	}

	public void setListaValores(List<Valoreshe> listaValores) {
		this.listaValores = listaValores;
	}

	public void carregarValores() {
		ValoresHeFacade ValoresHeFacade = new ValoresHeFacade();
		listaValores = ValoresHeFacade.listar(sql);
	}

	public void desativar(Valoreshe valores) {
		if (valores.isSituacao()) {
			valores.setSituacao(false); 
			Mensagem.lancarMensagemInfo("Valor Desativado.", "");
		}else{
			valores.setSituacao(true); 
			Mensagem.lancarMensagemInfo("Valor Ativo.", "");
		}
		ValoresHeFacade ValoresHeFacade = new ValoresHeFacade();
		valores = ValoresHeFacade.salvar(valores);
		carregarValores();
	}

	public String novo() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("sql", sql);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 600);
		RequestContext.getCurrentInstance().openDialog("cadValoresHigherEducation", options, null);
		return "";
	}

	public void retornoDialog() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		sql = (String) session.getAttribute("sql");
		session.removeAttribute("sql");
		if ((sql == null) || (sql.length() <= 10)) {
			limpar();
		}
		carregarValores();
	}

	public void pesquisar() {
		sql = "select v from Valoreshe v where v.valor>0";
		if ((fornecedorcidade != null) && (fornecedorcidade.getIdfornecedorcidade() != null)) {
			sql = sql + " and v.fornecedorcidade.idfornecedorcidade='" + fornecedorcidade.getIdfornecedorcidade() + "'";
		} else {
			if (cidade != null) {
				sql = sql + " and v.fornecedorcidade.cidade.idcidade='" + cidade.getIdcidade() + "'";
			} else if (pais != null) {
				sql = sql + " and v.fornecedorcidade.cidade.pais.idpais='" + pais.getIdpais() + "'";
			}
		}
		carregarValores();
	}

	public void limpar() {
		pais = null;
		cidade = null;
		fornecedorcidade = null;
		sql = "select v from Valoreshe v where v.situacao=true";
		carregarValores();
	}

	public void listarFornecedorCidade() {
		int idProduto = aplicacaoMB.getParametrosprodutos().getHighSchool();
		if ((idProduto > 0) && (cidade != null)) {
			listaFornecedorCidade = GerarListas.listarFornecedorCidade(idProduto, cidade.getIdcidade());
		}
	}

	public String bolaStatus(boolean situacao) {
		if (situacao) {
			return "../../resources/img/bolaVerde.png";
		} else
			return "../../resources/img/bolaVermelha.png";
	}
	
	public String titleStatus(boolean situacao) {
		if (situacao) {
			return "Desativar";
		} else
			return "Ativar";
	}

	public String editarValores(Valoreshe valor) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("sql", sql);
		session.setAttribute("valor", valor);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 600);
		RequestContext.getCurrentInstance().openDialog("cadValoresHigherEducation", options, null);
		return "";
	}

}
