package br.com.travelmate.managerBean.curso.controle;

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
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.primefaces.context.RequestContext;

import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class RelatorioVendaInvoiceMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	private Fornecedor fornecedor;
	private List<Fornecedor> listaFornecedor;
	private Date dataInicio;
	private Date dataTermino;  
	private Date dataInicioCurso;
	private Date dataTerminoCurso;  

	@PostConstruct()
	public void init() {
		listaFornecedor = GerarListas.listarFornecedor();
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

	public Date getDataInicioCurso() {
		return dataInicioCurso;
	}


	public void setDataInicioCurso(Date dataInicioCurso) {
		this.dataInicioCurso = dataInicioCurso;
	}


	public Date getDataTerminoCurso() {
		return dataTerminoCurso;
	}


	public void setDataTerminoCurso(Date dataTerminoCurso) {
		this.dataTerminoCurso = dataTerminoCurso;
	}


	public String fechar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	public String confirmar() throws SQLException, IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = "/reports/controlecurso/vendainvoice.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("sql", gerarSql());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,"Relatório de Vendas.pdf", null);
		} catch (JRException ex) {
			Logger.getLogger(RelatorioVendaInvoiceMB.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioVendaInvoiceMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarSql() {
		String sql = "select distinct cliente.nome as cliente, fornecedor.nome as fornecedor, cursos.dataInicio,"
			+" unidadenegocio.nomerelatorio as unidade, controlecursos.situacao, controlecursos.dataEmbarque,"
			+" controlecursos.LoasObs, pais.nome as pais"
			+" from invoices"
			+" join vendas on  invoices.vendas_idvendas=vendas.idvendas"
			+" join cliente on  vendas.cliente_idcliente=cliente.idcliente"
			+" join cursos on cursos.vendas_idvendas = vendas.idvendas"
			+" join controlecursos on controlecursos.vendas_idvendas = vendas.idvendas"
			+" join fornecedor on vendas.fornecedor_idfornecedor=fornecedor.idfornecedor"
			+" join fornecedorcidade on vendas.fornecedorcidade_idfornecedorcidade=fornecedorcidade.idfornecedorcidade"
			+" join cidade on fornecedorcidade.cidade_idcidade = cidade.idcidade"
			+" join pais on cidade.pais_idpais = pais.idpais"
			+" join UnidadeNegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadeNegocio.idUnidadeNegocio"
			+" join usuario on vendas.usuario_idusuario = usuario.idusuario"
			+" join produtos on vendas.produtos_idprodutos=produtos.idprodutos where produtos.idprodutos=1";
		if ((dataInicio != null) && (dataTermino != null)) {
			sql = sql + " and vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio)
					+ "' and vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataTermino) + "' ";
		} 
		if ((dataInicioCurso != null) && (dataTerminoCurso != null)) {
			sql = sql + " and cursos.dataInicio>='" + Formatacao.ConvercaoDataSql(dataInicioCurso)
					+ "' and cursos.dataTermino<='" + Formatacao.ConvercaoDataSql(dataTerminoCurso) + "' ";
		}
		if (fornecedor != null) {
			if (fornecedor.getIdfornecedor() != null) {
				sql = sql + " and fornecedor.idfornecedor=" + fornecedor.getIdfornecedor();
			}
		}
		sql = sql + " order by cursos.dataInicio, cliente.nome;";
		return sql;
	}

}
