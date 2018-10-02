package br.com.travelmate.managerBean.cancelamento;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.primefaces.context.RequestContext;

import br.com.travelmate.bean.RelatorioErroBean;
import br.com.travelmate.facade.CancelamentoFacade;
import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@ViewScoped
public class RelatorioCancelamentoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Produtos> listaProdutos;
	private Produtos produtos;
	private int idvenda;
	private String cliente;
	private float multaCliente;
	private float multaEscola;
	private float saldoCancelamento;
	private List<Fornecedor> listaFornecedor;
	private Fornecedor fornecedor;
	private Date dataInicial;
	private Date dataFinal;
	private List<Unidadenegocio> listaUnidade;
	private Unidadenegocio unidade;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private boolean habilitarUnidade = true;

	@PostConstruct()
	public void init() {
		produtos = new Produtos();
		gerarListaProduto();
		gerarListaFornecedor();
		gerarListaUnidadeNegocio();
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")){
    		habilitarUnidade = false;
    	}else {
    		habilitarUnidade = true;
    		unidade = usuarioLogadoMB.getUsuario().getUnidadenegocio();
    	}
	}

	public List<Produtos> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produtos> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public Produtos getProdutos() {
		return produtos;
	}

	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
	}

	

	public int getIdvenda() {
		return idvenda;
	}

	public void setIdvenda(int idvenda) {
		this.idvenda = idvenda;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public float getMultaCliente() {
		return multaCliente;
	}

	public void setMultaCliente(float multaCliente) {
		this.multaCliente = multaCliente;
	}

	public float getMultaEscola() {
		return multaEscola;
	}

	public void setMultaEscola(float multaEscola) {
		this.multaEscola = multaEscola;
	}

	public float getSaldoCancelamento() {
		return saldoCancelamento;
	}

	public void setSaldoCancelamento(float saldoCancelamento) {
		this.saldoCancelamento = saldoCancelamento;
	}

	public List<Fornecedor> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	public Unidadenegocio getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidadenegocio unidade) {
		this.unidade = unidade;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public boolean isHabilitarUnidade() {
		return habilitarUnidade;
	}

	public void setHabilitarUnidade(boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
	}

	public void gerarListaProduto() {
		ProdutoFacade produtosFacade = new ProdutoFacade();
		listaProdutos = produtosFacade.listarProdutos("");
		if (listaProdutos == null) {
			listaProdutos = new ArrayList<Produtos>();
		}
	}
	
	public void gerarListaFornecedor(){
		FornecedorFacade fornecedorFacade = new FornecedorFacade();
		listaFornecedor = fornecedorFacade.listar("SELECT f FROM Fornecedor f where f.idfornecedor>=1000 order by f.nome");
		if(listaFornecedor==null){
			listaFornecedor = new ArrayList<Fornecedor>();
		}
	}
	
	public void gerarListaUnidadeNegocio(){
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidade = unidadeNegocioFacade.listar(true);
        if (listaUnidade==null){
        	listaUnidade = new ArrayList<Unidadenegocio>();
        }
	}
	
	
	private String gerarSql(){
		String sql = "SELECT c FROM Cancelamento c where c.vendas.cliente.nome Like '%" + cliente + "%' ";
		if (idvenda>0){
			sql =sql + " and c.vendas.idvendas=" + idvenda + " ";
		}
		if ((dataInicial!=null) && (dataFinal!=null)){
			String data= Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
			sql = sql + " and c.datasolicitacao>='" + data +"' ";
		}else {
			sql = sql + " and c.datasolicitacao>='" + Formatacao.ConvercaoDataSql(dataInicial) +"' and c.datasolicitacao<='" + dataFinal + "' ";
		}
		if (fornecedor!=null){
			sql = sql + " and c.vendas.fornecedorcidade.fornecedor.idfornecedor=" + fornecedor.getIdfornecedor();
		}
		if (unidade!=null){
			sql = sql + " and c.vendas.unidadenegocio.idunidadeNegocio=" + unidade.getIdunidadeNegocio();
		}
		sql = sql + " order by c.datasolicitacao ASC, c.vendas.cliente.nome";
		return sql;
	}

	private void gerarLista(){
		String sql = gerarSql();
		CancelamentoFacade cancelamentoFacade = new CancelamentoFacade();
		List<Cancelamento> lista = cancelamentoFacade.listar(sql);
		if (lista!=null){
			iniciarRelatorio(lista);
		}else {
			RelatorioErroBean relErro = new RelatorioErroBean();
			relErro.iniciarRelatorioErro("SEM DADOS PARA IMPRIMIR");
		}
	}
	
	public String fechar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
	
	
	 public String gerarRelatorio() throws SQLException, IOException {
		  gerarLista();
		  return "";
	 }
	 
	 public void iniciarRelatorio(List<Cancelamento> lista){
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
					.getContext();
			String caminhoRelatorio = "/reports/cancelamento/reportcancelamento.jrxml";
			// Parametros do seguro
			Map<String, Object> parameters = new HashMap<String, Object>();
			File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
			BufferedImage logo = null;
			try {
				logo = ImageIO.read(f);
			} catch (IOException e) {
				
				  
			}
			parameters.put("logo", logo);
			JRDataSource jrds = new JRBeanCollectionDataSource(gerarListaCancelamentoFactory(lista));
			GerarRelatorio gerarRelatorio = new GerarRelatorio();
			try {
				gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, "RelatorioCancelamento");
			} catch (JRException | IOException e) {
				  
			}
	 }
	 
	 public List<RelatorioCancelamentoBean> gerarListaCancelamentoFactory(List<Cancelamento> lista){
		List<RelatorioCancelamentoBean> listaBean = new ArrayList<RelatorioCancelamentoBean>();
		for(int i=0;i<lista.size();i++){
			RelatorioCancelamentoBean rc = new RelatorioCancelamentoBean();
			rc.setCliente(lista.get(i).getVendas().getCliente().getNome());
			rc.setEscola(lista.get(i).getVendas().getFornecedorcidade().getFornecedor().getNome());
			rc.setIdVenda(String.valueOf(lista.get(i).getVendas().getIdvendas()));
			rc.setMultaCliente(Formatacao.formatarFloatString(lista.get(i).getMultacliente()));
			rc.setMultaEscola(Formatacao.formatarFloatString(lista.get(i).getMultafornecedor()));
			rc.setProduto(lista.get(i).getVendas().getProdutos().getDescricao());
			rc.setSaldoCancelamento(Formatacao.formatarFloatString(lista.get(i).getSaldocancelamento()));
			listaBean.add(rc);
		}
		return listaBean;
	 }
	 
	
	 
}
