package br.com.travelmate.managerBean.OrcamentoCurso;

import java.io.Serializable;
import java.util.ArrayList;
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

import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB; 
import br.com.travelmate.model.Filtroorcamentoproduto; 
import br.com.travelmate.model.Produtosorcamento;

@Named
@ViewScoped
public class AdicionarProdutosExtrasMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject 
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject 
	private AplicacaoMB aplicacaoMB;
	private ResultadoOrcamentoBean resultadoOrcamentoBean;
	private List<Filtroorcamentoproduto> listaFiltroorcamentoproduto;
	private Produtosorcamento prdutoOrcamento;
	private float valorCambio;
	private ProdutosExtrasBean produtosExtrasBean;
	
	@PostConstruct
    public void init(){
		gerarListaProdutosOrcamento();
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        valorCambio =  (Float) session.getAttribute("valorCambio");
        resultadoOrcamentoBean =  (ResultadoOrcamentoBean) session.getAttribute("resultadoOrcamentoBean");
        session.removeAttribute("resultadoOrcamentoBean");
        session.removeAttribute("valorCambio");
		produtosExtrasBean = new ProdutosExtrasBean();
	}
	
	public ResultadoOrcamentoBean getResultadoOrcamentoBean() {
		return resultadoOrcamentoBean;
	}
	public void setResultadoOrcamentoBean(ResultadoOrcamentoBean resultadoOrcamentoBean) {
		this.resultadoOrcamentoBean = resultadoOrcamentoBean;
	}
	public List<Filtroorcamentoproduto> getListaFiltroorcamentoproduto() {
		return listaFiltroorcamentoproduto;
	}
	public void setListaFiltroorcamentoproduto(List<Filtroorcamentoproduto> listaFiltroorcamentoproduto) {
		this.listaFiltroorcamentoproduto = listaFiltroorcamentoproduto;
	}
	public Produtosorcamento getPrdutoOrcamento() {
		return prdutoOrcamento;
	}
	public void setPrdutoOrcamento(Produtosorcamento prdutoOrcamento) {
		this.prdutoOrcamento = prdutoOrcamento;
	}
	 
	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public float getValorCambio() {
		return valorCambio;
	}

	public void setValorCambio(float valorCambio) {
		this.valorCambio = valorCambio;
	}

	public ProdutosExtrasBean getProdutosExtrasBean() {
		return produtosExtrasBean;
	}

	public void setProdutosExtrasBean(ProdutosExtrasBean produtosExtrasBean) {
		this.produtosExtrasBean = produtosExtrasBean;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}
	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}
	
	public void gerarListaProdutosOrcamento(){
        FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
        String sql="";
        sql = "select f from Filtroorcamentoproduto f where f.produtos.idprodutos=" + 
            aplicacaoMB.getParametrosprodutos().getCursos() + " order by f.produtosorcamento.descricao";
        listaFiltroorcamentoproduto = filtroOrcamentoProdutoFacade.pesquisar(sql);
        if (listaFiltroorcamentoproduto==null){
            listaFiltroorcamentoproduto = new ArrayList<Filtroorcamentoproduto>();
        }
    }
	
	public String salvar(){
		if(produtosExtrasBean.getDescricao()!=null && prdutoOrcamento.getIdprodutosOrcamento()!=null){ 
			produtosExtrasBean.setProdutosorcamento(prdutoOrcamento);
			ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
			produtosExtrasBean.setValorcoprodutos(valorCoProdutosFacade.consultar(aplicacaoMB.getParametrosprodutos().getProdutoextra()));
			RequestContext.getCurrentInstance().closeDialog(produtosExtrasBean); 
		}
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public void calcularMoedaReal(){
		produtosExtrasBean.setValorOriginalRS(produtosExtrasBean.getValorOrigianl()*valorCambio);
	}
	
	public void calcularMoedaEstrangeira(){
		produtosExtrasBean.setValorOrigianl(produtosExtrasBean.getValorOriginalRS()/valorCambio);
	}
	
	public void incluirDescricao(){
		if(prdutoOrcamento!=null && prdutoOrcamento.getIdprodutosOrcamento()!=null){
			produtosExtrasBean.setDescricao(prdutoOrcamento.getDescricao());
		}
	}
	
	public boolean mostrarBtnConfirmar() {
		if(prdutoOrcamento!=null && prdutoOrcamento.getIdprodutosOrcamento()==85 &&
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(0)
				.getValorcoprodutos().getCoprodutos().getAdvertenciaseguro()!=null &&
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(0)
				.getValorcoprodutos().getCoprodutos().getAdvertenciaseguro().length()>1) {
			return false;
		}return true;
	}
	
	public boolean mostrarBtnMensagemSeguro() {
		if(prdutoOrcamento!=null && prdutoOrcamento.getIdprodutosOrcamento()==85 &&
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(0)
				.getValorcoprodutos().getCoprodutos().getAdvertenciaseguro()!=null &&
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(0)
				.getValorcoprodutos().getCoprodutos().getAdvertenciaseguro().length()>1) {
			return true;
		}return false;
	}
	
	public String retornarMensagem() {
		if(resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(0)
				.getValorcoprodutos().getCoprodutos().getAdvertenciaseguro()!=null &&
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(0)
				.getValorcoprodutos().getCoprodutos().getAdvertenciaseguro().length()>1) {
			return resultadoOrcamentoBean.getProdutoFornecedorBean().getListaCursoPrincipal().get(0)
					.getValorcoprodutos().getCoprodutos().getAdvertenciaseguro();
		}return "";
	}
}
