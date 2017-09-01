package br.com.travelmate.managerBean.cursospacotes;
  
import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB; 
import br.com.travelmate.model.Cursospacote;  
import br.com.travelmate.model.Valorcoprodutos;
import br.com.travelmate.util.Formatacao; 

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class SelecionarProdutosMB implements Serializable {
 
	private static final long serialVersionUID = 1L; 
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Cursospacote cursospacote;   
	private List<Valorcoprodutos> listaProdutos;
	private String tipo;
	private String nome;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) { 
			FacesContext fc = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
	        cursospacote = (Cursospacote) session.getAttribute("cursospacote");
	        tipo = (String) session.getAttribute("tipoproduto");
	        session.removeAttribute("tipoproduto");  
	        session.removeAttribute("cursospacote");  
	        gerarListaProdutos();
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}  

	public Cursospacote getCursospacote() {
		return cursospacote;
	}

	public void setCursospacote(Cursospacote cursospacote) {
		this.cursospacote = cursospacote;
	} 

	public List<Valorcoprodutos> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Valorcoprodutos> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String cancelar() { 
		RequestContext.getCurrentInstance().closeDialog(null); 
		return "";
	} 
	
	public String selecionar(Valorcoprodutos valorcoprodutos){
		if(tipo.equalsIgnoreCase("curso")){
			cursospacote.setValorcoprodutos_curso(valorcoprodutos);
		}else cursospacote.setValorcoprodutos_acomodacao(valorcoprodutos);
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("tipoproduto", tipo);
		session.setAttribute("cursospacote", cursospacote);
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}          
	
	public void gerarListaProdutos(){
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		String sql = "Select v From Valorcoprodutos v Where v.coprodutos.fornecedorcidadeidioma.idfornecedorcidadeidioma="
					+cursospacote.getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
					+" and v.datafinal>='"+Formatacao.ConvercaoDataSql(new Date())
					+"' and v.coprodutos.situacao=TRUE";
		if(tipo.equalsIgnoreCase("acomodacao")){
			sql = sql + " and v.coprodutos.produtosorcamento.tipo='A'";
		}else if(tipo.equalsIgnoreCase("curso")){
			sql = sql + " and v.coprodutos.produtosorcamento.tipo='O' and v.coprodutos.produtosorcamento.tipoproduto='C'";
		}
		if(nome!=null && nome.length()>0){
			sql = sql + " and v.coprodutos.nome like '%"+nome+"%'";
		}
		sql = sql + " order by v.coprodutos.nome";
		listaProdutos = valorCoProdutosFacade.listar(sql);
	}
}
