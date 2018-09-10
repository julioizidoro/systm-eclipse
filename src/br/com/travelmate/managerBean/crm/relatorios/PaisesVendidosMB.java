package br.com.travelmate.managerBean.crm.relatorios;


import br.com.travelmate.dao.VendasDao;
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
public class PaisesVendidosMB implements Serializable {

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
	private int numeroPaises;
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

	public int getNumeroPaises() {
		return numeroPaises;
	}

	public void setNumeroPaises(int numeroPaises) {
		this.numeroPaises = numeroPaises;
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
		String caminhoRelatorio = ("/reports/fornecedor/paisesTop3.jasper");
		Map<String, Object> parameters = new HashMap<String, Object>();
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		
		String sqlVendasTotal = "SELECT distinct count(vendas.idvendas) as contador from vendas"
				+ " where (vendas.situacao='FINALIZADA' or vendas.situacao='ANDAMENTO')";
		if (produtos != null && produtos.getIdprodutos() != null) {
			sqlVendasTotal = sqlVendasTotal + " AND vendas.produtos_idprodutos=" + produtos.getIdprodutos();
		}
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			sqlVendasTotal = sqlVendasTotal + " and vendas.unidadenegocio_idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		sqlVendasTotal = sqlVendasTotal + " AND vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'"
				+ " AND vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
		Long vendastotal = vendasDao.numVendas(sqlVendasTotal);  

		String sql = "SELECT distinct pais.nome, (count(vendas.idvendas) / " + vendastotal + ") as percentual"
				+ " FROM vendas"
				+ " join fornecedorcidade on vendas.fornecedorcidade_idfornecedorcidade = fornecedorcidade.idfornecedorcidade"
				+ " join cidade on fornecedorcidade.cidade_idcidade = cidade.idcidade"
				+ " join pais on cidade.pais_idpais=pais.idpais"
				+ " where (vendas.situacao='FINALIZADA' or vendas.situacao='ANDAMENTO')";
		if (produtos != null && produtos.getIdprodutos() != null) {
			sql = sql + " AND vendas.produtos_idprodutos=" + produtos.getIdprodutos();
		}
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			sql = sql + " and vendas.unidadenegocio_idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		sql = sql + " and vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio) + "' and vendas.dataVenda<='"
				+ Formatacao.ConvercaoDataSql(dataFinal) + "' "
				+ " group by pais.idpais order by count(vendas.idvendas) DESC";
		if (numeroPaises > 0) {
			sql = sql + " limit " + numeroPaises;
		}
		parameters.put("sql", sql);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Paises Vendidos.pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(PaisesVendidosMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(PaisesVendidosMB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
