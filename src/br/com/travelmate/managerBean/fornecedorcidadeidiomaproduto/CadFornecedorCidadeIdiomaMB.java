package br.com.travelmate.managerBean.fornecedorcidadeidiomaproduto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.CidadePaisProdutosFacade;
import br.com.travelmate.facade.CoProdutosFacade;
import br.com.travelmate.facade.FornecedorCidadeIdiomaFacade;
import br.com.travelmate.facade.IdiomaFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cidadepaisproduto;
import br.com.travelmate.model.Coprodutos;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcidadeidioma;
import br.com.travelmate.model.Idioma;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadFornecedorCidadeIdiomaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Fornecedorcidadeidioma fornecedorCidadeIdioma;
	private List<Fornecedorcidadeidioma> listaFornecedorCidadeIdioma;
	private List<Paisproduto> listaPais;
	private Paisproduto pais;
	private Cidadepaisproduto cidadeproduto;
	private List<Cidadepaisproduto> listaCidade;
	private Idioma idioma;
	private List<Idioma> listaIdiomas;
	private Fornecedorcidade fornecedorCidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Produtos produtos;
	private List<Produtos> listaProdutos;

	@PostConstruct
	public void init() {
		listaProdutos = GerarListas.listarProdutos("");
		IdiomaFacade idiomaFacade = new IdiomaFacade();
		listaIdiomas = idiomaFacade.listar("Select i from Idioma i order by i.descricao");
		fornecedorCidadeIdioma = new Fornecedorcidadeidioma();
	}

	public Fornecedorcidadeidioma getFornecedorCidadeIdioma() {
		return fornecedorCidadeIdioma;
	}

	public void setFornecedorCidadeIdioma(Fornecedorcidadeidioma fornecedorCidadeIdioma) {
		this.fornecedorCidadeIdioma = fornecedorCidadeIdioma;
	}

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

	public List<Fornecedorcidadeidioma> getListaFornecedorCidadeIdioma() {
		return listaFornecedorCidadeIdioma;
	}

	public void setListaFornecedorCidadeIdioma(List<Fornecedorcidadeidioma> listaFornecedorCidadeIdioma) {
		this.listaFornecedorCidadeIdioma = listaFornecedorCidadeIdioma;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public Fornecedorcidade getFornecedorCidade() {
		return fornecedorCidade;
	}

	public void setFornecedorCidade(Fornecedorcidade fornecedorCidade) {
		this.fornecedorCidade = fornecedorCidade;
	}

	public Produtos getProdutos() {
		return produtos;
	}

	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
	}

	public List<Produtos> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produtos> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public List<Paisproduto> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Paisproduto> listaPais) {
		this.listaPais = listaPais;
	}

	public Paisproduto getPais() {
		return pais;
	}

	public void setPais(Paisproduto pais) {
		this.pais = pais;
	}

	public Cidadepaisproduto getCidadeproduto() {
		return cidadeproduto;
	}

	public void setCidadeproduto(Cidadepaisproduto cidadeproduto) {
		this.cidadeproduto = cidadeproduto;
	}

	public List<Cidadepaisproduto> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidadepaisproduto> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public List<Idioma> getListaIdiomas() {
		return listaIdiomas;
	}

	public void setListaIdiomas(List<Idioma> listaIdiomas) {
		this.listaIdiomas = listaIdiomas;
	}

	public void listarForCidadeIdioma() {
		if (cidadeproduto != null && idioma != null && produtos != null) {
			String sql = "select f from Fornecedorcidadeidioma f where f.fornecedorcidade.cidade.idcidade="
					+ cidadeproduto.getCidade().getIdcidade() + " and f.idioma.ididioma =" + idioma.getIdidioma()
					+ " and f.fornecedorcidade.produtos.idprodutos=" + produtos.getIdprodutos()
					+ " order by f.fornecedorcidade.fornecedor.nome";
			FornecedorCidadeIdiomaFacade fornecedorCidadeIdiomaFacade = new FornecedorCidadeIdiomaFacade();
			listaFornecedorCidadeIdioma = fornecedorCidadeIdiomaFacade.listar(sql);
			if (listaFornecedorCidadeIdioma == null) {
				listaFornecedorCidadeIdioma = new ArrayList<Fornecedorcidadeidioma>();
			}
		}
	}

	public String excluir(Fornecedorcidadeidioma fornecedorcidadeidioma) {
		CoProdutosFacade coProdutosFacade = new CoProdutosFacade();
		List<Coprodutos> listaCoprodutos = coProdutosFacade
				.listar("Select c From Coprodutos c Where c.fornecedorcidadeidioma.idfornecedorcidadeidioma="
						+ fornecedorcidadeidioma.getIdfornecedorcidadeidioma());
		if (listaCoprodutos == null || listaCoprodutos.isEmpty()) {
			FornecedorCidadeIdiomaFacade fornecedorCidadeIdiomaFacade = new FornecedorCidadeIdiomaFacade();
			fornecedorCidadeIdiomaFacade.excluir(fornecedorcidadeidioma.getIdfornecedorcidadeidioma());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Excluído com Sucesso", ""));
			listarForCidadeIdioma();
		} else {
			Mensagem.lancarMensagemInfo("Esse fornecedor não pode ser excluido", "");
		}
		return "";
	}

	public void listarFornecedorCidade() {
		if ((produtos != null) && (cidadeproduto != null)) {
			listaFornecedorCidade = GerarListas.listarFornecedorCidade(produtos.getIdprodutos(),
					cidadeproduto.getCidade().getIdcidade());
		}
	}

	public void salvar() {
		if(fornecedorCidade!=null && idioma!=null) {
			fornecedorCidadeIdioma.setFornecedorcidade(fornecedorCidade);
			fornecedorCidadeIdioma.setIdioma(idioma);
			fornecedorCidadeIdioma.setHabilitada(true);
			fornecedorCidadeIdioma.setAno(Formatacao.getAnoData(new Date()));
			FornecedorCidadeIdiomaFacade fornecedorCidadeIdiomaFacade = new FornecedorCidadeIdiomaFacade();
			fornecedorCidadeIdioma = fornecedorCidadeIdiomaFacade.salvar(fornecedorCidadeIdioma);
			listarForCidadeIdioma();
			Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
		}else Mensagem.lancarMensagemErro("Atenção!", "Campos obrigatórios não preenchidos.");
	}

	public String fechar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	public void salvarHabilidar(Fornecedorcidadeidioma fornecedorcidadeidioma) {
		FornecedorCidadeIdiomaFacade fornecedorCidadeIdiomaFacade = new FornecedorCidadeIdiomaFacade();
		fornecedorcidadeidioma = fornecedorCidadeIdiomaFacade.salvar(fornecedorcidadeidioma);
	}

	public void listarPaises() {
		if (produtos != null) {
			PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
			listaPais = paisProdutoFacade.listar(produtos.getIdprodutos());
		}
	}

	public void listarCidade() {
		if (produtos != null && pais != null) {
			CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
			listaCidade = cidadePaisProdutosFacade.listar(
					"SELECT c FROM Cidadepaisproduto c WHERE c.paisproduto.idpaisproduto=" + pais.getIdpaisproduto());
		}
	}

}
