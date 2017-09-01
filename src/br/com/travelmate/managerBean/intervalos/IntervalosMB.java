package br.com.travelmate.managerBean.intervalos;

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

import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.OcursoFeriadoFacade;
import br.com.travelmate.facade.PaisProdutoFacade; 
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cidade; 
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Ocursoferiado;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto; 
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class IntervalosMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private List<Ocursoferiado> listaIntervalos;
	private List<Paisproduto> listaPais;
	private Pais pais;
	private Cidade cidade;
	private Fornecedorcidade fornecedor;
	private List<Fornecedorcidade> listaFornecedor; 
	private String nome;

	@PostConstruct
	public void init() {
		gerarListaIntervalos();
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		int idProduto = aplicacaoMB.getParametrosprodutos().getCursos();
		listaPais = paisProdutoFacade.listar(idProduto);
		cidade = new Cidade();
		gerarListaFornecedor();
		fornecedor = new Fornecedorcidade(); 
		nome ="";
	} 

	public List<Paisproduto> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Paisproduto> listaPais) {
		this.listaPais = listaPais;
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

	public Fornecedorcidade getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedorcidade fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Fornecedorcidade> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedorcidade> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	} 

	
	public List<Ocursoferiado> getListaIntervalos() {
		return listaIntervalos;
	}

	public void setListaIntervalos(List<Ocursoferiado> listaIntervalos) {
		this.listaIntervalos = listaIntervalos;
	} 
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void gerarListaFornecedor() {
		String sql="";
		if (cidade != null && cidade.getIdcidade() != null) {
			sql = "select f from Fornecedorcidade f where f.cidade.idcidade="
					+ cidade.getIdcidade() + " and f.ativo=1 order by f.fornecedor.nome"; 
		}else{
			sql = "select f from Fornecedorcidade f group by f.fornecedor.idfornecedor order by f.fornecedor.nome"; 
		}
		FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
		listaFornecedor = fornecedorCidadeFacade.listar(sql);
		if (listaFornecedor == null) {
			listaFornecedor = new ArrayList<Fornecedorcidade>();
		} 
	}
	
	
	public void gerarListaIntervalos(){
		Date data = new Date();
		OcursoFeriadoFacade ccursoFeriadoFacade = new OcursoFeriadoFacade();
		String sql="select o from Ocursoferiado o where o.datainicial>='"+Formatacao.ConvercaoDataSql(data)+"' and"
				+ " o.datafinal>='"+Formatacao.ConvercaoDataSql(data)+"' order by o.nome";
		listaIntervalos = ccursoFeriadoFacade.listar(sql);
	}
	
	
	public void pesquisar(){
		OcursoFeriadoFacade ccursoFeriadoFacade = new OcursoFeriadoFacade();
		String sql="select  o from Ocursoferiado o where o.nome like '%"+nome+"%'";
		if(pais!=null && pais.getIdpais()!=null){
			sql = sql+" and o.fornecedorcidade.cidade.pais.idpais="+pais.getIdpais();
		}
		if(cidade!=null && cidade.getIdcidade()!=null){
			sql = sql+" and o.fornecedorcidade.cidade.idcidade="+cidade.getIdcidade();
			if(fornecedor!=null && fornecedor.getIdfornecedorcidade()!=null){
				sql = sql+" and o.fornecedorcidade.idfornecedorcidade="+fornecedor.getIdfornecedorcidade();
			}
		}else if(fornecedor!=null && fornecedor.getIdfornecedorcidade()!=null){
			sql = sql+" and o.fornecedorcidade.fornecedor.idfornecedor="+fornecedor.getFornecedor().getIdfornecedor();
		}
		sql=sql+ " order by o.nome";
		listaIntervalos = ccursoFeriadoFacade.listar(sql);
	}
	
	
	public void limpar(){
		pais = null;
		cidade =null;
		fornecedor=null;
		nome=""; 
		gerarListaIntervalos();
	}
	 
	public void excluir(Ocursoferiado ocursoferiado) { 
		OcursoFeriadoFacade ccursoFeriadoFacade = new OcursoFeriadoFacade();
		ccursoFeriadoFacade.excluir(ocursoferiado.getIdocursoferiado());
		Mensagem.lancarMensagemInfo("", "Excluído com sucesso.");
		gerarListaIntervalos();;
	}
	
	
	public String editar(Ocursoferiado ocursoferiado) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("ocursoferiado", ocursoferiado);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("cadIntervalos");
		return "cadIntervalos";
	}
	
	public String novo() { 
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("cadIntervalos");
		return "";
	}

}
