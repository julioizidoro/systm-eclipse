package br.com.travelmate.managerBean.curso;

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

import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.PaisFacade; 
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class RelatorioEmbarqueUnidadeMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Pais pais;
	private List<Pais> listaPais;
	private Cidade cidade;
	private Fornecedorcidade fornecedorcidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Date dataInicioEmbarque;
	private Date dataTerminoEmbarque;
	private Date dataInicioCurso;
	private Date dataTerminoCurso; 
	private Unidadenegocio unidadenegocio;
	private String situacao;
	private String nome;
	private String ordenar;

	@PostConstruct()
	public void init() {
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar();
		unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
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
  
	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public Date getDataInicioEmbarque() {
		return dataInicioEmbarque;
	}

	public void setDataInicioEmbarque(Date dataInicioEmbarque) {
		this.dataInicioEmbarque = dataInicioEmbarque;
	}

	public Date getDataTerminoEmbarque() {
		return dataTerminoEmbarque;
	}

	public void setDataTerminoEmbarque(Date dataTerminoEmbarque) {
		this.dataTerminoEmbarque = dataTerminoEmbarque;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getOrdenar() {
		return ordenar;
	}

	public void setOrdenar(String ordenar) {
		this.ordenar = ordenar;
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
		String caminhoRelatorio = "/reports/controlecurso/embarque.jasper";
		Map<String, Object> parameters = new HashMap();
		parameters.put("sql", gerarSql());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,"Relatório de Embarque.pdf", null);
		} catch (JRException ex) {
			Logger.getLogger(RelatorioEmbarqueUnidadeMB.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioEmbarqueUnidadeMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarSql() {
		String sql = "select distinct vendas.idvendas,controlecursos.situacao, cliente.nome as cliente,controlecursos.dataEmbarque,fornecedor.nome as fornecedor,"
				+ " unidadenegocio.nomerelatorio as unidade, controlecursos.LoasObs as obs,cursos.dataInicio, cidade.nome as cidade, pais.nome, cursos.numeroSenamas from controlecursos "
				+ " join vendas on controlecursos.vendas_idvendas=vendas.idvendas "
				+ " join cursos on cursos.vendas_idvendas = vendas.idvendas"
				+ " join cliente on  vendas.cliente_idcliente=cliente.idcliente "
				+ " join fornecedor on vendas.fornecedor_idfornecedor=fornecedor.idfornecedor"
				+ " join fornecedorcidade on vendas.fornecedorcidade_idfornecedorcidade=fornecedorcidade.idfornecedorcidade"
				+ " join cidade on fornecedorcidade.cidade_idcidade=cidade.idcidade"
				+ " join pais on cidade.pais_idpais=pais.idpais "
				+ " join unidadenegocio on vendas.unidadeNegocio_idunidadeNegocio=unidadenegocio.idunidadeNegocio"
				+ " join produtos on vendas.produtos_idprodutos=produtos.idprodutos where produtos.idprodutos=1"
				+ " and vendas.situacao<>'CANCELADA' and vendas.unidadeNegocio_idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		if ((dataInicioEmbarque != null) && (dataTerminoEmbarque != null)) {
			sql = sql + " and controlecursos.dataEmbarque>='" + Formatacao.ConvercaoDataSql(dataInicioEmbarque)
					+ "' and controlecursos.dataEmbarque<='" + Formatacao.ConvercaoDataSql(dataTerminoEmbarque) + "' ";
		}
		if ((dataInicioCurso != null) && (dataTerminoCurso != null)) {
			sql = sql + " and cursos.dataInicio>='" + Formatacao.ConvercaoDataSql(dataInicioCurso)
					+ "' and cursos.dataInicio<='" + Formatacao.ConvercaoDataSql(dataTerminoCurso) + "' ";
		} 
		if (fornecedorcidade != null) {
			if (fornecedorcidade.getIdfornecedorcidade() != null) {
				sql = sql + " and fornecedor.idfornecedor=" + fornecedorcidade.getFornecedor().getIdfornecedor();
			}
		}
		if (pais != null) {
			if (pais.getIdpais() != null) {
				sql = sql + " and pais.idpais=" + pais.getIdpais();
			}
		}
		if (cidade != null) {
			if (cidade.getIdcidade() != null) {
				sql = sql + " and cidade.idcidade=" + cidade.getIdcidade();
			}
		}
		if(ordenar.equalsIgnoreCase("Data inicio")){
			sql = sql + " order by cursos.dataInicio, cliente.nome;";
		}else{
			sql = sql + " order by controlecursos.dataEmbarque, cliente.nome;";
		}
		return sql;
	}

}
