package br.com.travelmate.managerBean.acomodacao;

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

import br.com.travelmate.facade.ExtrasAcomodacaoFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade; 
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ValoresAcomodacaoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Extrasacamodacao;
import br.com.travelmate.model.Fornecedorcidade; 
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Valoresacomodacao;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class ValoresAcomodacaoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private List<Valoresacomodacao> listaValoresAcomodacao;
	private List<Paisproduto> listaPais;
	private Pais pais;
	private Cidade cidade;
	private Fornecedorcidade fornecedor;
	private List<Fornecedorcidade> listaFornecedor;
	private String residencia;

	@PostConstruct
	public void init() {
		gerarListaValoresAcomodacao();
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		int idProduto = aplicacaoMB.getParametrosprodutos().getCursos();
		listaPais = paisProdutoFacade.listar(idProduto);
		cidade = new Cidade();
		gerarListaFornecedor();
		fornecedor = new Fornecedorcidade();
		residencia="";
	}

	public List<Valoresacomodacao> getListaValoresAcomodacao() {
		return listaValoresAcomodacao;
	}

	public void setListaValoresAcomodacao(List<Valoresacomodacao> listaValoresAcomodacao) {
		this.listaValoresAcomodacao = listaValoresAcomodacao;
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

	public String getResidencia() {
		return residencia;
	}

	public void setResidencia(String residencia) {
		this.residencia = residencia;
	}

	public String tipoAcomodacao() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 530);
		RequestContext.getCurrentInstance().openDialog("tipoAcomodacao");
		return "";
	}

	public void gerarListaFornecedor() {
		String sql="";
		if (cidade != null && cidade.getIdcidade() != null) {
			sql = "select f from Fornecedorcidade f where f.cidade.idcidade="
					+ cidade.getIdcidade() + " and f.ativo=1 order by f.fornecedor.nome"; 
		}else{
			sql = "select f from Fornecedorcidade f where f.ativo=1 group by f.fornecedor.idfornecedor order by f.fornecedor.nome"; 
		}
		FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
		listaFornecedor = fornecedorCidadeFacade.listar(sql);
		if (listaFornecedor == null) {
			listaFornecedor = new ArrayList<Fornecedorcidade>();
		} 
	}
	
	
	public void gerarListaValoresAcomodacao(){
		Date data = new Date();
		ValoresAcomodacaoFacade valoresAcomodacaoFacade = new ValoresAcomodacaoFacade();
		String sql="select v from Valoresacomodacao v where v.datavalidadeinicial<='"+Formatacao.ConvercaoDataSql(data)+"' and"
				+ " v.datavalidadefinal>='"+Formatacao.ConvercaoDataSql(data)+"' order by v.residencia";
		listaValoresAcomodacao = valoresAcomodacaoFacade.listar(sql);
	}
	
	
	public void pesquisar(){
		ValoresAcomodacaoFacade valoresAcomodacaoFacade = new ValoresAcomodacaoFacade();
		String sql="select v from Valoresacomodacao v where v.residencia like '%"+residencia+"%'";
		if(pais!=null && pais.getIdpais()!=null){
			sql = sql+" and v.fornecedorcidade.cidade.pais.idpais="+pais.getIdpais();
		}
		if(cidade!=null && cidade.getIdcidade()!=null){
			sql = sql+" and v.fornecedorcidade.cidade.idcidade="+cidade.getIdcidade();
			if(fornecedor!=null && fornecedor.getIdfornecedorcidade()!=null){
				sql = sql+" and v.fornecedorcidade.idfornecedorcidade="+fornecedor.getIdfornecedorcidade();
			}
		}else if(fornecedor!=null && fornecedor.getIdfornecedorcidade()!=null){
			sql = sql+" and v.fornecedorcidade.fornecedor.idfornecedor="+fornecedor.getFornecedor().getIdfornecedor();
		}
		sql=sql+ " order by v.residencia";
		listaValoresAcomodacao = valoresAcomodacaoFacade.listar(sql);
	}
	
	
	public void limpar(){
		pais = null;
		cidade =null;
		fornecedor=null;
		residencia="";
		gerarListaValoresAcomodacao();
	}
	 
	public void excluir(Valoresacomodacao valoresacomodacao) {
		ExtrasAcomodacaoFacade extrasAcomodacaoFacade = new ExtrasAcomodacaoFacade();
		String sql = "select e from Extrasacomodacao e where valoresacomodacao.idvaloresacomodacao="+valoresacomodacao.getIdvaloresacomodacao();
		List<Extrasacamodacao> listaExtras = extrasAcomodacaoFacade.listar(sql);
		if(listaExtras!=null){
			for (int i = 0; i < listaExtras.size(); i++) {
				extrasAcomodacaoFacade.excluir(listaExtras.get(i).getIdextrasacamodacao());
			}
		}
		ValoresAcomodacaoFacade valoresAcomodacaoFacade = new ValoresAcomodacaoFacade();
		valoresAcomodacaoFacade.excluir(valoresacomodacao.getIdvaloresacomodacao());
		Mensagem.lancarMensagemInfo("", "Excluído com sucesso.");
		gerarListaValoresAcomodacao();
	}
	
	
	public String editar(Valoresacomodacao valoresacomodacao) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("valoresacomodacao", valoresacomodacao);
		return "cadValoresAcomodacao";
	}
	
	public String novo() { 
		return "cadValoresAcomodacao";
	}

}
