package br.com.travelmate.managerBean.crm.relatorios;

import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class EscolasVendidasMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Date dataInicio;
	private Date dataFinal;
	private int numeroEscolas;
	private Produtos produtos;
	private List<Produtos> listaProdutos;
	private Unidadenegocio unidadenegocio;
	private List<Unidadenegocio> listaUnidade;

	@PostConstruct
	public void init() {
		listaProdutos = GerarListas.listarProdutos("");
		listaUnidade = GerarListas.listarUnidade();
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

	public int getNumeroEscolas() {
		return numeroEscolas;
	}

	public void setNumeroEscolas(int numeroEscolas) {
		this.numeroEscolas = numeroEscolas;
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

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	public String fechar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	public void gerarRelatorio() throws IOException, SQLException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/fornecedor/top5.jasper");
		Map parameters = new HashMap();
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		String sql = "SELECT distinct fornecedor.nome, count(vendas.idvendas) as numerovendas" + " FROM vendas"
				+ " join fornecedorcidade on vendas.fornecedorcidade_idfornecedorcidade = fornecedorcidade.idfornecedorcidade"
				+ " join fornecedor on fornecedorcidade.fornecedor_idfornecedor = fornecedor.idfornecedor" 
				+ " where (vendas.situacao='FINALIZADA' or vendas.situacao='ANDAMENTO') ";
		if (produtos != null && produtos.getIdprodutos() != null) {
			sql = sql + " and vendas.produtos_idprodutos=" + produtos.getIdprodutos();
		}
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			sql = sql + " and vendas.unidadenegocio_idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		sql = sql + " and vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio) + "' and vendas.dataVenda<='"
				+ Formatacao.ConvercaoDataSql(dataFinal) + "'"
				+ " group by fornecedor.idfornecedor order by count(vendas.idvendas) DESC";
		if(numeroEscolas>0){
			sql = sql + " limit "+numeroEscolas;
		}
		parameters.put("sql", sql);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Escolas Vendidas.pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(EscolasVendidasMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(EscolasVendidasMB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
