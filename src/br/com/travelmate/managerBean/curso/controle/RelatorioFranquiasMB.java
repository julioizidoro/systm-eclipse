package br.com.travelmate.managerBean.curso.controle;

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
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class RelatorioFranquiasMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pais pais;
	private List<Pais> listaPais;
	private Cidade cidade;
	private Fornecedorcidade fornecedorcidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Date dataInicio;
	private Date dataTermino;
	private Date dataInicioCurso;
	private Date dataTerminoCurso;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private List<Usuario> listaConsultor;
	private Usuario usuario;

	@PostConstruct()
	public void init() {
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar();
		gerarListaUnidadeNegocio();
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

	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}

	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public List<Usuario> getListaConsultor() {
		return listaConsultor;
	}

	public void setListaConsultor(List<Usuario> listaConsultor) {
		this.listaConsultor = listaConsultor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void gerarListaFornecedor() {
		String sql = "select f from Fornecedorcidade f where f.cidade.idcidade=" + cidade.getIdcidade()
				+ " and f.ativo=1 order by f.fornecedor.nome";
		FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
		listaFornecedorCidade = fornecedorCidadeFacade.listar(sql);
	}

	public void gerarListaUnidadeNegocio() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
		if (listaUnidadeNegocio == null) {
			listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
		}
	}

	public void gerarListaConsultor() {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		listaConsultor = usuarioFacade
				.listar("select u from Usuario u where u.situacao='Ativo' and u.unidadenegocio.idunidadeNegocio="
						+ unidadenegocio.getIdunidadeNegocio() + " order by u.nome");
		if (listaConsultor == null) {
			listaConsultor = new ArrayList<Usuario>();
		}
	}

	public String fechar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	public String confirmar() throws SQLException, IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = "/reports/controlecurso/franquias.jasper";
		Map<String, Object> parameters = new HashMap();
		parameters.put("sql", gerarSql());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,"Relatório Franquias.pdf", null);
		} catch (JRException ex) {
			Logger.getLogger(RelatorioFranquiasMB.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioFranquiasMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarSql() {
		String sql = "select distinct vendas.idvendas, cliente.nome as cliente, vendas.dataVenda, cursos.dataInicio,fornecedor.nome as fornecedor, unidadenegocio.nomerelatorio, "
				+ " usuario.nome as consultor, cursos.numeroSenamas as nSemanas, vendas.valor as valorTotal,cambio.valor as valorCambio, moedas.sigla from vendas "
				+ " join cliente on  vendas.cliente_idcliente=cliente.idcliente"
				+ " join cursos on cursos.vendas_idvendas = vendas.idvendas"
				+ " join fornecedor on vendas.fornecedor_idfornecedor=fornecedor.idfornecedor"
				+ " join UnidadeNegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadeNegocio.idUnidadeNegocio"
				+ " join cambio on vendas.cambio_idcambio = cambio.idcambio"
				+ " join moedas on cambio.moedas_idmoedas = moedas.idmoedas"
			    + " join usuario on vendas.usuario_idusuario = usuario.idusuario" 
				+ " join fornecedorcidade on vendas.fornecedorcidade_idfornecedorcidade=fornecedorcidade.idfornecedorcidade"
				+ " join cidade on fornecedorcidade.cidade_idcidade= cidade.idcidade"
				+ " join produtos on vendas.produtos_idprodutos=produtos.idprodutos where produtos.idprodutos=1";
		if ((dataInicio != null) && (dataTermino != null)) {
			sql = sql + " and vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio)
					+ "' and vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataTermino) + "' ";
		}
		if ((dataInicioCurso != null) && (dataTerminoCurso != null)) {
			sql = sql + " and cursos.dataInicio>='" + Formatacao.ConvercaoDataSql(dataInicioCurso)
					+ "' and cursos.dataInicio<='" + Formatacao.ConvercaoDataSql(dataTerminoCurso) + "' ";
		}
		if (unidadenegocio != null) {
			if (unidadenegocio.getIdunidadeNegocio() != null) {
				sql = sql + " and vendas.unidadeNegocio_idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
			}
		}
		if (usuario != null && usuario.getIdusuario() != null) {
			sql = sql + " and vendas.usuario_idusuario=" + usuario.getIdusuario();
		}
		if (fornecedorcidade != null) {
			if (fornecedorcidade.getIdfornecedorcidade() != null) {
				sql = sql + " and fornecedor.idfornecedor=" + fornecedorcidade.getFornecedor().getIdfornecedor();
			}
		}
		if (pais != null) {
			sql = sql + " and cidade.pais_idpais=" + pais.getIdpais();
		}
		sql = sql + " order by vendas.dataVenda, cliente.nome;";
		return sql;
	}   

}
