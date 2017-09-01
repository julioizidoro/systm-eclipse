package br.com.travelmate.managerBean.coprodutos;

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
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.CoProdutosFacade;
import br.com.travelmate.facade.FornecedorCidadeIdiomaFacade;
import br.com.travelmate.managerBean.AplicacaoMB; 
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Coprodutos;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcidadeidioma;
import br.com.travelmate.model.Idioma;
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
	private Cidade cidade;
	private Idioma idioma;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Fornecedorcidade fornecedorCidade;
	private Produtos produtos;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		cidade = (Cidade) session.getAttribute("cidade");
		idioma = (Idioma) session.getAttribute("idioma");
		produtos = (Produtos) session.getAttribute("produtos");
		session.removeAttribute("produtos");
		session.removeAttribute("cidade");
		session.removeAttribute("idioma");
		listarForCidadeIdioma();
		fornecedorCidadeIdioma = new Fornecedorcidadeidioma();
	}

	public Fornecedorcidadeidioma getFornecedorCidadeIdioma() {
		return fornecedorCidadeIdioma;
	}

	public void setFornecedorCidadeIdioma(Fornecedorcidadeidioma fornecedorCidadeIdioma) {
		this.fornecedorCidadeIdioma = fornecedorCidadeIdioma;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
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

	public void listarForCidadeIdioma() {
		if (cidade != null && idioma != null) {
			int idProduto;
			if(produtos!=null && produtos.getIdprodutos()!=null) {
				idProduto = produtos.getIdprodutos();
			}else {
				idProduto = aplicacaoMB.getParametrosprodutos().getCursos();
			}
			String sql = "select f from Fornecedorcidadeidioma f where f.fornecedorcidade.cidade.idcidade="
					+ cidade.getIdcidade() + " and f.idioma.ididioma =" + idioma.getIdidioma()
					+ " and f.fornecedorcidade.produtos.idprodutos="+idProduto
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
		List<Coprodutos> listaCoprodutos = coProdutosFacade.listar("Select c From Coprodutos c Where c.fornecedorcidadeidioma.idfornecedorcidadeidioma=" 
									+ fornecedorcidadeidioma.getIdfornecedorcidadeidioma());
		if (listaCoprodutos == null || listaCoprodutos.isEmpty()) {
			FornecedorCidadeIdiomaFacade fornecedorCidadeIdiomaFacade = new FornecedorCidadeIdiomaFacade();
			fornecedorCidadeIdiomaFacade.excluir(fornecedorcidadeidioma.getIdfornecedorcidadeidioma());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Excluído com Sucesso", ""));
			listarForCidadeIdioma();
		}else{
			Mensagem.lancarMensagemInfo("Esse fornecedor não pode ser excluido", "");
		}
		return "";
	}  
	
	
	public void listarFornecedorCidade(){ 
		int idProduto;
		if(produtos!=null && produtos.getIdprodutos()!=null) {
			idProduto = produtos.getIdprodutos();
		}else {
			idProduto = aplicacaoMB.getParametrosprodutos().getCursos();
		}
        if ((idProduto>0) && (cidade!=null)){
            listaFornecedorCidade = GerarListas.listarFornecedorCidade(idProduto, cidade.getIdcidade());
        }
    }
	
	public void salvar(){
		fornecedorCidadeIdioma.setFornecedorcidade(fornecedorCidade);
		fornecedorCidadeIdioma.setIdioma(idioma);
		fornecedorCidadeIdioma.setAno(Formatacao.getAnoData(new Date()));
		FornecedorCidadeIdiomaFacade fornecedorCidadeIdiomaFacade = new FornecedorCidadeIdiomaFacade();
		fornecedorCidadeIdioma =fornecedorCidadeIdiomaFacade.salvar(fornecedorCidadeIdioma);
		listarForCidadeIdioma();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Salvo com Sucesso", ""));
	}
	
	
	public String fechar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
	
	public void salvarHabilidar(Fornecedorcidadeidioma fornecedorcidadeidioma){
		FornecedorCidadeIdiomaFacade fornecedorCidadeIdiomaFacade = new FornecedorCidadeIdiomaFacade();
		fornecedorcidadeidioma =fornecedorCidadeIdiomaFacade.salvar(fornecedorcidadeidioma);
	}
	
}
