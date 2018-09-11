package br.com.travelmate.managerBean.higherEducation.orcamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.CidadePaisProdutosFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Cidadepaisproduto;
import br.com.travelmate.model.Heorcamentopais;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadProgramaHeMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Pais pais;
	private Cidade cidade;
	private List<Paisproduto> listaPais;
	private List<Cidadepaisproduto> listaCidade;
	private Moedas moeda;
	private List<Moedas> listaMoedas;
	private Cambio cambio;
	private float valorCambio = 0;
	private Heorcamentopais heorcamentopais;
	private String sigla = "";
	private String moedaNacional;

	@PostConstruct
	public void init() {
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		listaPais = paisProdutoFacade.listar(22);
		heorcamentopais = new Heorcamentopais();
		moedaNacional = usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla();
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Paisproduto> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Paisproduto> listaPais) {
		this.listaPais = listaPais;
	}

	public List<Cidadepaisproduto> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidadepaisproduto> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public Moedas getMoeda() {
		return moeda;
	}

	public void setMoeda(Moedas moeda) {
		this.moeda = moeda;
	}

	public List<Moedas> getListaMoedas() {
		return listaMoedas;
	}

	public void setListaMoedas(List<Moedas> listaMoedas) {
		this.listaMoedas = listaMoedas;
	}

	public Cambio getCambio() {
		return cambio;
	}

	public void setCambio(Cambio cambio) {
		this.cambio = cambio;
	}

	public float getValorCambio() {
		return valorCambio;
	}

	public void setValorCambio(float valorCambio) {
		this.valorCambio = valorCambio;
	}

	public Heorcamentopais getHeorcamentopais() {
		return heorcamentopais;
	}

	public void setHeorcamentopais(Heorcamentopais heorcamentopais) {
		this.heorcamentopais = heorcamentopais;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getMoedaNacional() {
		return moedaNacional;
	}

	public void setMoedaNacional(String moedaNacional) {
		this.moedaNacional = moedaNacional;
	}

	public void listarCidades() {
		CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
		String sql = "SELECT c FROM Cidadepaisproduto c WHERE c.paisproduto.pais.idpais=" + pais.getIdpais();
		sql = sql + " and c.paisproduto.produtos.idprodutos=" + 22;
		sql = sql + " ORDER BY c.cidade.nome";
		listaCidade = cidadePaisProdutosFacade.listar(sql);
		if (listaCidade == null) {
			listaCidade = new ArrayList<Cidadepaisproduto>();
		}
	}

	public void selecionarCambio() {
		if (pais != null && pais.getIdpais() != null) {
			moeda = pais.getMoedas();
			cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), moeda, usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
			valorCambio = cambio.getValor();
			sigla = moeda.getSigla();
		}
	}

	public void salvar() {
		if (validarDados()) {
			heorcamentopais.setCambio(cambio);
			heorcamentopais.setCidade(cidade);
			RequestContext.getCurrentInstance().closeDialog(heorcamentopais);
		}
	}

	public void cancelar() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}

	public boolean validarDados() {
		if (pais == null || pais.getIdpais() == null) {
			Mensagem.lancarMensagemInfo("Informe o pais", "");
			return false;
		}

		if (cidade == null || cidade.getIdcidade() == null) {
			Mensagem.lancarMensagemInfo("Informe a cidade", "");
			return false;
		}

		if (heorcamentopais.getCurso() == null || heorcamentopais.getCurso().length() <= 0) {
			Mensagem.lancarMensagemInfo("Informe o programa", "");
			return false;
		}

		if (heorcamentopais.getDatainicio() == null) {
			Mensagem.lancarMensagemInfo("Informe a Data Inicio", "");
			return false;
		}

		if (heorcamentopais.getDuracao() == null) {
			Mensagem.lancarMensagemInfo("Informe o número de Semanas", "");
		}

		return true;
	}

	public void calcularValores() {
		if (valorCambio > 0 && (heorcamentopais.getValormoedaestrageira() != null
				&& heorcamentopais.getValormoedaestrageira() > 0)) {
			heorcamentopais.setValorreais(heorcamentopais.getValormoedaestrageira() * valorCambio);
			Mensagem.lancarMensagemInfo("Valor do câmbio alterado com sucesso", "");
		} else if (valorCambio > 0
				&& (heorcamentopais.getValorreais() != null && heorcamentopais.getValorreais() > 0)) {
			heorcamentopais.setValormoedaestrageira(heorcamentopais.getValorreais() / valorCambio);
			Mensagem.lancarMensagemInfo("Valor do câmbio alterado com sucesso", "");
		}
	}

	public void calcularValoresReais() {
		if (valorCambio > 0 && (heorcamentopais.getValorreais() != null && heorcamentopais.getValorreais() > 0)) {
			heorcamentopais.setValormoedaestrageira(heorcamentopais.getValorreais() / valorCambio);
			Mensagem.lancarMensagemInfo("Valor do câmbio alterado com sucesso", "");
		}
	}

}
