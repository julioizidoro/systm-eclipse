package br.com.travelmate.managerBean.cursospacotes;

import br.com.travelmate.facade.CursosPacotesFacade;
import br.com.travelmate.facade.FornecedorCidadeIdiomaFacade;
import br.com.travelmate.facade.IdiomaFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Cursospacote;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcidadeidioma;
import br.com.travelmate.model.Idioma;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

@Named
@ViewScoped
public class CursosPacotesMB implements Serializable {
 
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Cursospacote> listaCursosPacotes;
	private List<Paisproduto> listaPais;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Pais pais;
	private Cidade cidade;
	private Idioma idioma;
	private Fornecedorcidadeidioma fornecedorCidadeIdioma;
	private List<Fornecedorcidadeidioma> listaFornecedorIdioma;
	private List<Idioma> listaIdiomas;
	private boolean vencidos;
	private String sql;
	private Produtos produtos;
	private List<Produtos> listaProdutos;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) { 
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			sql = (String) session.getAttribute("sql");
			fornecedorCidadeIdioma = (Fornecedorcidadeidioma) session.getAttribute("fornecedorcidadeidioma");
			session.removeAttribute("fornecedorcidadeidioma"); 
			session.removeAttribute("sql");
			if(fornecedorCidadeIdioma!=null && fornecedorCidadeIdioma.getIdfornecedorcidadeidioma()!=null){
				produtos = fornecedorCidadeIdioma.getFornecedorcidade().getProdutos();
				gerarListaPais();
				pais = fornecedorCidadeIdioma.getFornecedorcidade().getCidade().getPais();
				cidade = fornecedorCidadeIdioma.getFornecedorcidade().getCidade();
				listarIdiomas();
				idioma = fornecedorCidadeIdioma.getIdioma(); 
				listarFornecedorCidadeIdioma();
				listarCursosPacotes();
			}
			getAplicacaoMB();
			listaProdutos = GerarListas.listarProdutos(""); 
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
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

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

	public Fornecedorcidadeidioma getFornecedorCidadeIdioma() {
		return fornecedorCidadeIdioma;
	}

	public void setFornecedorCidadeIdioma(Fornecedorcidadeidioma fornecedorCidadeIdioma) {
		this.fornecedorCidadeIdioma = fornecedorCidadeIdioma;
	}

	public List<Fornecedorcidadeidioma> getListaFornecedorIdioma() {
		return listaFornecedorIdioma;
	}

	public void setListaFornecedorIdioma(List<Fornecedorcidadeidioma> listaFornecedorIdioma) {
		this.listaFornecedorIdioma = listaFornecedorIdioma;
	}

	public List<Idioma> getListaIdiomas() {
		return listaIdiomas;
	}

	public void setListaIdiomas(List<Idioma> listaIdiomas) {
		this.listaIdiomas = listaIdiomas;
	}

	public List<Cursospacote> getListaCursosPacotes() {
		return listaCursosPacotes;
	}

	public void setListaCursosPacotes(List<Cursospacote> listaCursosPacotes) {
		this.listaCursosPacotes = listaCursosPacotes;
	}

	public boolean isVencidos() {
		return vencidos;
	}

	public void setVencidos(boolean vencidos) {
		this.vencidos = vencidos;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
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

	public void listarIdiomas() {
		IdiomaFacade idiomaFacade = new IdiomaFacade();
		listaIdiomas = idiomaFacade.listar("Select i from Idioma i order by i.descricao");
		if (listaIdiomas == null) {
			listaIdiomas = new ArrayList<Idioma>();
		}
		idioma = null;
		fornecedorCidadeIdioma=null;
		listaFornecedorIdioma = new ArrayList<>();
		listaCursosPacotes = new ArrayList<>();
	}

	public void listarFornecedorCidadeIdioma() {
		if (cidade != null && idioma != null) {
			String sql = "select f from Fornecedorcidadeidioma f where f.fornecedorcidade.cidade.idcidade="
					+ cidade.getIdcidade() + " and f.idioma.ididioma =" + idioma.getIdidioma()
					+ " and f.fornecedorcidade.produtos.idprodutos="+produtos.getIdprodutos()
					+ " order by f.fornecedorcidade.fornecedor.nome";
			FornecedorCidadeIdiomaFacade fornecedorCidadeIdiomaFacade = new FornecedorCidadeIdiomaFacade();
			listaFornecedorIdioma = fornecedorCidadeIdiomaFacade.listar(sql);
			if (listaFornecedorIdioma == null) {
				listaFornecedorIdioma = new ArrayList<Fornecedorcidadeidioma>();
			}
		}
	}
	

	public String cadCursosPacotes() {
		if (fornecedorCidadeIdioma != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("fornecedorcidadeidioma", fornecedorCidadeIdioma);
			session.setAttribute("produtos", produtos);
			session.setAttribute("sql", sql);
			return "cadcursospacote";
		} else {
			Mensagem.lancarMensagemErro("Atenção! ", "Campos obrigatórios não preenchidos.");
		}
		return "";
	}

	public String editar(Cursospacote cursospacote) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("cursospacote", cursospacote);
		session.setAttribute("sql", sql);
		return "cadcursospacote"; 
	}   
	
	public String voltar(){
		return "orcamentocurso";
	}
	
	
	public void listarCursosPacotes(){
		if(fornecedorCidadeIdioma!=null && fornecedorCidadeIdioma.getIdfornecedorcidadeidioma()!=null){
			CursosPacotesFacade cursosPacotesFacade = new CursosPacotesFacade();
			sql = "Select c From Cursospacote c Where c.fornecedorcidadeidioma.idfornecedorcidadeidioma="
					+fornecedorCidadeIdioma.getIdfornecedorcidadeidioma();
			if(!vencidos){
				sql = sql + " and c.dataterminovenda>='"+Formatacao.ConvercaoDataSql(new Date())+"'";
			}
			sql = sql + " order by c.valorcoprodutos_curso.coprodutos.produtosorcamento.descricao, c.datainicovenda";
			listaCursosPacotes = cursosPacotesFacade.listar(sql); 
		}              
	}
	
	public String retornarImagemAcomodação(Cursospacote cursospacote){
		if(cursospacote.getValorcoprodutos_acomodacao()!=null){
			return "../../resources/img/crm/confirmar.png";
		}
		return "../../resources/img/crm/cancelar.png";
	}
	
	public String retornarTitleAcomodação(Cursospacote cursospacote){
		if(cursospacote.getValorcoprodutos_acomodacao()!=null){
			return "Pacote possui acomodação.";
		}
		return "Pacote não possui acomodação.";
	}
	
	public String adicionarFornecedorIdioma() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("idioma", idioma);
		session.setAttribute("cidade", cidade); 
		session.setAttribute("produtos", produtos);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("cadFornecedorCidadeIdioma");
		return "";
	}
    
	public void gerarListaPais() {
		if(produtos!=null && produtos.getIdprodutos()!=null) {
			PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
			listaPais = paisProdutoFacade.listar(produtos.getIdprodutos());  
		}
	}
}
