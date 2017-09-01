package br.com.travelmate.managerBean.cancelamento;

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

import br.com.travelmate.facade.CancelamentoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;

@Named
@ViewScoped
public class ConsultaCancelamentoMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private int idVenda;
	private Date dataInicio;
	private Date dataTermino;
	private String nomeCliente="";
	private Usuario usuario;
	private Unidadenegocio unidadeNegocio;
	private Produtos produto;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private List<Produtos> listaProduto;
	private List<Usuario> listaUsuario;
	private List<Cancelamento> listaCancelamento;
	private boolean habilitarUnidade = true;
	private String situacao;
	
	
	@PostConstruct
	public void init(){
		situacao = "TODAS";
		listaUnidadeNegocio=GerarListas.listarUnidade();
    	if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")){
    		habilitarUnidade = false;
    	}else {
    		habilitarUnidade = true;
    		unidadeNegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
    	}
    	listaProduto = GerarListas.listarProdutos("");
    	gerarListaConsultaInicial();
	}


	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}


	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}


	public int getIdVenda() {
		return idVenda;
	}


	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}

	public Date getDataInicio() {
		return dataInicio;
	}


	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}


	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}


	public String getNomeCliente() {
		return nomeCliente;
	}


	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Unidadenegocio getUnidadeNegocio() {
		return unidadeNegocio;
	}


	public void setUnidadeNegocio(Unidadenegocio unidadeNegocio) {
		this.unidadeNegocio = unidadeNegocio;
	}


	public Produtos getProduto() {
		return produto;
	}


	public void setProduto(Produtos produto) {
		this.produto = produto;
	}


	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}


	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
	}


	public List<Produtos> getListaProduto() {
		return listaProduto;
	}


	public void setListaProduto(List<Produtos> listaProduto) {
		this.listaProduto = listaProduto;
	}


	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}


	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}


	public List<Cancelamento> getListaCancelamento() {
		return listaCancelamento;
	}


	public void setListaCancelamento(List<Cancelamento> listaCancelamento) {
		this.listaCancelamento = listaCancelamento;
	}


	public boolean isHabilitarUnidade() {
		return habilitarUnidade;
	}


	public void setHabilitarUnidade(boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
	}


	public String getSituacao() {
		return situacao;
	}


	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public void gerarListaConsultaInicial(){
		String sql = "Select c from Cancelamento c where c.situacao<>'FINALIZADO' ";
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")){
	    	if(unidadeNegocio!=null)	{
	    		sql = sql + " and c.vendas.unidadenegocio.idunidadeNegocio=" + unidadeNegocio.getIdunidadeNegocio();
	    	}
	    }else {
	    	sql = sql + " and c.vendas.unidadenegocio.idunidadeNegocio=" + usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
	    }
		sql= sql + " order by c.datasolicitacao DESC, c.vendas.cliente.nome DESC";
		CancelamentoFacade cancelamentoFacade = new CancelamentoFacade();
        listaCancelamento = cancelamentoFacade.listar(sql);
        if (listaCancelamento==null){
        	listaCancelamento = new ArrayList<Cancelamento>();
        }
	}
	
	public void pesquisarListaCancelamento(){
		String sql = "Select c from Cancelamento c where c.vendas.cliente.nome like '%" + nomeCliente + "%' ";
	    if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")){
	    	if(unidadeNegocio!=null)	{
	    		sql = sql + " and c.vendas.unidadenegocio.idunidadeNegocio=" + unidadeNegocio.getIdunidadeNegocio();
	    	}
	    }else {
	    	sql = sql + " and c.vendas.unidadenegocio.idunidadeNegocio=" + usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
	    }
	    if (idVenda>0){
	    	sql = sql + " and c.vendas.idvendas=" + idVenda;
	    }
	    if((dataInicio!=null) && (dataTermino!=null)){
    		sql = sql + " and c.datasolicitacao='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
    		sql = sql + " and c.datasolicitacao<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
    	}else {
    		if (nomeCliente.length()==0){
    			String dataConsulta = Formatacao.SubtarirDatas(new Date(), 365, "yyyy-MM-dd");
        		sql = sql + " and c.datasolicitacao>='" + dataConsulta + "'";
    		}
    	}
    	if(!situacao.equalsIgnoreCase("TODAS")){
    		sql = sql + " and c.situacao='" + situacao +"'";
    	}
    	if (produto!=null){
    		sql = sql + " and c.vendas.produtos.idprodutos=" + produto.getIdprodutos();
    	}
    	sql= sql + " order by c.datasolicitacao DESC, c.vendas.cliente.nome DESC";
        CancelamentoFacade cancelamentoFacade = new CancelamentoFacade();
        listaCancelamento = cancelamentoFacade.listar(sql);
        if (listaCancelamento==null){
        	listaCancelamento = new ArrayList<Cancelamento>();
        }
	}
    
    public void limparPesquisa(){
    	unidadeNegocio = null;
    	dataInicio = null;
    	dataTermino = null;
    	situacao = "TODAS";
    	nomeCliente = "";
    	idVenda=0;
    	produto=null;
    	gerarListaConsultaInicial();
    }
    
    public String emissaoCancelamento(Cancelamento cancelamento) {
    	FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("cancelamento", cancelamento);
        if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("gerencial")){
        	if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()==3){
        		return "emissaocancelamento";
        	}else{
        		return "emissaocancelamentounidade";
        	}
        }else {
        	Map<String, Object> options = new HashMap<String, Object>();
	    	options.put("contentWidth", 400);
			RequestContext.getCurrentInstance().openDialog("cancelarVenda", options, null);
        	session.setAttribute("venda", cancelamento.getVendas());
        	return "";
        }

    }
    
	public void finalizarCancelamento(Cancelamento cancelamento) {
		if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 3) {
			CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
			calcularMultaCancelamentoBean.cancelarContasReceber(cancelamento, usuario);
			cancelamento = calcularMultaCancelamentoBean.concluirSituacaoCancelamento(cancelamento);
			calcularMultaCancelamentoBean.verifcarSituacaoInvoice(cancelamento);
		}
	}
	
    public String relatorioTermoQuitacao(Cancelamento cancelamento) {
    	FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("cancelamento", cancelamento);
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("contentWidth",450);
        RequestContext.getCurrentInstance().openDialog("reciboTermoQuitacao", options, null);
        return "";
    }
    
    public String documentacao(Cancelamento cancelamento) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendas", cancelamento.getVendas());
		return "consArquivos";
	}

}
