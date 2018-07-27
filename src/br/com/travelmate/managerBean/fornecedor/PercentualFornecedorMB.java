package br.com.travelmate.managerBean.fornecedor;
 
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.FornecedorFacade;

import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;


import javax.annotation.PostConstruct; 
import javax.faces.view.ViewScoped; 
import javax.inject.Inject;
import javax.inject.Named; 

import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.PieChartModel;

@Named
@ViewScoped
public class PercentualFornecedorMB implements Serializable{
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	@Inject 
	private AplicacaoMB aplicacaoMB;
    private Date dataInicio;
	private Date dataFinal;
	private Fornecedor fornecedor;
    private List<Fornecedor> listaFornecedor;
    private boolean mostrarFiltro=true;
    private boolean mostrarGrafico=false;
    private PieChartModel pieModel2;
    private Produtos produtos;
	private List<Produtos> listaProdutos;
 
    @PostConstruct()
    public void init(){ 
		gerarListaFornecedor();
		listaProdutos = GerarListas.listarProdutos("");
	}
    
    public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
 
    public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Fornecedor> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public boolean isMostrarFiltro() {
		return mostrarFiltro;
	}

	public void setMostrarFiltro(boolean mostrarFiltro) {
		this.mostrarFiltro = mostrarFiltro;
	}

	public boolean isMostrarGrafico() {
		return mostrarGrafico;
	}

	public void setMostrarGrafico(boolean mostrarGrafico) {
		this.mostrarGrafico = mostrarGrafico;
	}

	public PieChartModel getPieModel2() {
		return pieModel2;
	}

	public void setPieModel2(PieChartModel pieModel2) {
		this.pieModel2 = pieModel2;
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

	public String fechar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
	
	public void gerarListaFornecedor(){
        FornecedorFacade forncedorFacade = new FornecedorFacade();
        listaFornecedor = forncedorFacade.listar("SELECT f From Fornecedor f where f.idfornecedor>1000 order by f.nome");
        if(listaFornecedor==null){
            listaFornecedor = new ArrayList<Fornecedor>();
        }
    }
      
    public void gerarGrafico(){
    	if(fornecedor!=null && fornecedor.getIdfornecedor()!=null){
	    	//numero vendas
	    	
			String sqlVendasTotal = "SELECT distinct count(vendas.idvendas)"
					+ " FROM vendas"
					+ " where (vendas.situacao='FINALIZADA' or vendas.situacao='ANDAMENTO')";
			if (produtos != null && produtos.getIdprodutos() != null) {
				sqlVendasTotal = sqlVendasTotal + " AND vendas.produtos_idprodutos=" + produtos.getIdprodutos();
			}
			sqlVendasTotal = sqlVendasTotal + " AND vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'"
					+ " AND vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
			Long vendastotal = vendasDao.numVendas(sqlVendasTotal);  
			 
			//percentual fornecedor
			String sqlPercentual = "SELECT distinct (count(vendas.idvendas) / "+vendastotal+") * 100"
					+ " FROM vendas"
					+ " join fornecedorcidade on vendas.fornecedorcidade_idfornecedorcidade = fornecedorcidade.idfornecedorcidade"
					+ " join fornecedor on fornecedorcidade.fornecedor_idfornecedor =fornecedor.idfornecedor "
					+ " where (vendas.situacao='FINALIZADA' or vendas.situacao='ANDAMENTO') AND fornecedor.idfornecedor="+fornecedor.getIdfornecedor();
			if (produtos != null && produtos.getIdprodutos() != null) {
				sqlPercentual = sqlPercentual + " AND vendas.produtos_idprodutos=" + produtos.getIdprodutos();
			}
			sqlPercentual = sqlPercentual + " AND vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'"
					+ " AND vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
			BigDecimal percentual = vendasDao.percentualVendas(sqlPercentual);  
			
			int restante = 100 - percentual.intValue();
	    	
	    	mostrarFiltro=false;
	    	mostrarGrafico=true; 
	    	
	        pieModel2 = new PieChartModel();
	         
	        pieModel2.set(fornecedor.getNome()+" - "+percentual.intValue()+"%", percentual);
	        pieModel2.set("Outros - "+restante+"%", restante); 
	          
	        pieModel2.setLegendPosition("e"); 
	        pieModel2.setFill(false);
	        pieModel2.setShowDataLabels(true);
	        pieModel2.setDiameter(150); 
    	}else Mensagem.lancarMensagemErro("Atenção!", "Selecione um fornecedor.");
    }
    
    public void voltar(){
    	mostrarFiltro=true;
    	mostrarGrafico=false;
    }
    
}
