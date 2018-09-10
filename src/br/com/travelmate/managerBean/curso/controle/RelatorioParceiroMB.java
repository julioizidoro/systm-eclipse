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

import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Pais;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class RelatorioParceiroMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private Pais pais;
	private List<Pais> listaPais;
	private Cidade cidade;
	private Fornecedorcidade fornecedorcidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Date dataInicio;
	private Date dataTermino;
	private Date dataChegadaInicio;
	private Date dataChegadaTermino;
	private Date dataEmbarqueInicio;
	private Date dataEmbarqueTermino;

	@PostConstruct()
	public void init() {
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar();

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}

	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
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

	public Date getDataChegadaInicio() {
		return dataChegadaInicio;
	}

	public void setDataChegadaInicio(Date dataChegadaInicio) {
		this.dataChegadaInicio = dataChegadaInicio;
	}

	public Date getDataChegadaTermino() {
		return dataChegadaTermino;
	}

	public void setDataChegadaTermino(Date dataChegadaTermino) {
		this.dataChegadaTermino = dataChegadaTermino;
	}

	public Date getDataEmbarqueInicio() {
		return dataEmbarqueInicio;
	}

	public void setDataEmbarqueInicio(Date dataEmbarqueInicio) {
		this.dataEmbarqueInicio = dataEmbarqueInicio;
	}

	public Date getDataEmbarqueTermino() {
		return dataEmbarqueTermino;
	}

	public void setDataEmbarqueTermino(Date dataEmbarqueTermino) {
		this.dataEmbarqueTermino = dataEmbarqueTermino;
	}

	public void gerarListaFornecedor() {
		String sql = "select f from Fornecedorcidade f where f.cidade.idcidade=" + cidade.getIdcidade()
				+ " and f.ativo=1 order by f.fornecedor.nome";
		FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
		listaFornecedorCidade = fornecedorCidadeFacade.listar(sql);
	}

	public String fechar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	public String confirmar() throws SQLException, IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = "/reports/controlecurso/parceiros.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>(); 
		parameters.put("sql", gerarSql());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Relatorio Parceiro.pdf", null);
		} catch (JRException ex) {
			Logger.getLogger(RelatorioParceiroMB.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) { 
			Logger.getLogger(RelatorioParceiroMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarSql() {
		String sql = "select distinct vendas.idvendas, cliente.nome, cliente.email, cursos.dataChegada, controlecursos.dataEmbarque,"
				+ "fornecedor.nome as fornecedor from controlecursos" 
				+ " join vendas on controlecursos.vendas_idvendas=vendas.idvendas"
				+ " join cliente on  vendas.cliente_idcliente=cliente.idcliente"
				+ " join cursos on  controlecursos.cursos_idcursos=cursos.idcursos"
				+ " join fornecedorcidade on  vendas.fornecedorcidade_idfornecedorcidade=fornecedorcidade.idfornecedorcidade"
				+ " join cidade on  fornecedorcidade.cidade_idcidade=cidade.idcidade"
				+ " join pais on  cidade.pais_idpais=pais.idpais" 
				+ " join fornecedor on vendas.fornecedor_idfornecedor=fornecedor.idfornecedor"
				+ " join produtos on vendas.produtos_idprodutos=produtos.idprodutos where produtos.idprodutos=1";
		if ((dataInicio != null) && (dataTermino != null)) {
			sql = sql + " and vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio)
					+ "' and vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataTermino) + "' ";
		}
		if ((dataChegadaInicio != null) && (dataChegadaTermino != null)) {
			sql = sql + " and cursos.dataChegada>='" + Formatacao.ConvercaoDataSql(dataChegadaInicio)
					+ "' and cursos.dataChegada<='" + Formatacao.ConvercaoDataSql(dataChegadaTermino) + "' ";
		}
		if ((dataEmbarqueInicio != null) && (dataEmbarqueTermino != null)) {
			sql = sql + " and controlecursos.dataEmbarque>='" + Formatacao.ConvercaoDataSql(dataEmbarqueInicio)
					+ "' and controlecursos.dataEmbarque<='" + Formatacao.ConvercaoDataSql(dataEmbarqueTermino) + "' ";
		}
		if (nome != null) {
			if (nome.length()>0) {
				sql = sql + " and cliente.nome like '" + nome +"'";
			}
		}
		if (pais != null) {
			if (pais.getIdpais()!=null) {
				sql = sql + " and pais.idpais=" + pais.getIdpais();
			}
		}
		if (pais == null && cidade == null) {
			if (fornecedorcidade != null) {
				if (fornecedorcidade.getIdfornecedorcidade()!=null) {
					sql = sql + " and fornecedor.idfornecedor=" + fornecedorcidade.getFornecedor().getIdfornecedor();
				}
			}
		}else{
			if (pais != null) {
				sql = sql + " and pais.idpais=" + pais.getIdpais();
			}
			
			if (cidade != null) {
				sql = sql + " and cidade.idcidade=" + cidade.getIdcidade();
			}
			if (fornecedorcidade != null) {
				if (fornecedorcidade.getIdfornecedorcidade()!=null) {
					sql = sql + " and fornecedor.idfornecedor=" + fornecedorcidade.getFornecedor().getIdfornecedor();
				}
			}
		}
		sql = sql
				+ " order by cliente.nome;";
		return sql;
	}

}
