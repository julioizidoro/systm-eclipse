package br.com.travelmate.managerBean.crm;

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
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import br.com.travelmate.bean.RelatorioErroBean;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.crm.relatorios.OrigemLeadMB;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class RelatorioLeadsMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Date dataUltContatoInicial;
	private Date dataUltContatoFinal;
	private Date dataProxContatoInicial;
	private Date dataProxContatoFinal;
	private List<Usuario> listaConsultor;
	private Usuario consultor;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private String status;
	private Date datarecebimentoInicial;
	private Date datarecebimentoFinal;
	private boolean desabilitarUnidade = false;
	
	
	@PostConstruct
	public void init(){
		gerarListaUnidadeNegocio();
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			desabilitarUnidade = true;
			gerarListaConsultor();
		}
	}


	public Date getDataUltContatoInicial() {
		return dataUltContatoInicial;
	}


	public void setDataUltContatoInicial(Date dataUltContatoInicial) {
		this.dataUltContatoInicial = dataUltContatoInicial;
	}


	public Date getDataUltContatoFinal() {
		return dataUltContatoFinal;
	}


	public void setDataUltContatoFinal(Date dataUltContatoFinal) {
		this.dataUltContatoFinal = dataUltContatoFinal;
	}


	public Date getDataProxContatoInicial() {
		return dataProxContatoInicial;
	}


	public void setDataProxContatoInicial(Date dataProxContatoInicial) {
		this.dataProxContatoInicial = dataProxContatoInicial;
	}


	public Date getDataProxContatoFinal() {
		return dataProxContatoFinal;
	}


	public void setDataProxContatoFinal(Date dataProxContatoFinal) {
		this.dataProxContatoFinal = dataProxContatoFinal;
	}


	public List<Usuario> getListaConsultor() {
		return listaConsultor;
	}


	public void setListaConsultor(List<Usuario> listaConsultor) {
		this.listaConsultor = listaConsultor;
	}


	public Usuario getConsultor() {
		return consultor;
	}


	public void setConsultor(Usuario consultor) {
		this.consultor = consultor;
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
	
	
	
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Date getDatarecebimentoInicial() {
		return datarecebimentoInicial;
	}


	public void setDatarecebimentoInicial(Date datarecebimentoInicial) {
		this.datarecebimentoInicial = datarecebimentoInicial;
	}


	public Date getDatarecebimentoFinal() {
		return datarecebimentoFinal;
	}


	public void setDatarecebimentoFinal(Date datarecebimentoFinal) {
		this.datarecebimentoFinal = datarecebimentoFinal;
	}


	public boolean isDesabilitarUnidade() {
		return desabilitarUnidade;
	}


	public void setDesabilitarUnidade(boolean desabilitarUnidade) {
		this.desabilitarUnidade = desabilitarUnidade;
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
	
	
	public String gerarRelatorio() throws SQLException, IOException {
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
					.getContext();
			String caminhoRelatorio = "";
			Map<String, Object> parameters = new HashMap<String, Object>();
			caminhoRelatorio = "reports/crm/relatorioLead.jasper"; 
			parameters.put("sql", gerarSql());
			File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
			BufferedImage logo = ImageIO.read(f);
			parameters.put("logo", logo); 
			GerarRelatorio gerarRelatorio = new GerarRelatorio();
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "leads", "");
			} catch (JRException ex) {
				Logger.getLogger(OrigemLeadMB.class.getName()).log(Level.SEVERE, null, ex);
			}
		return "";
	}
	
	
	public String gerarSql() {    
		String sql = "Select distinct cliente.nome as cliente, lead.datarecebimento, lead.dataultimocontato, lead.dataproximocontato, lead.situacao, produtos.descricao as programa, lead.situacao as status "
				+" from lead join produtos on lead.produtos_idprodutos=produtos.idprodutos "
				+ " join cliente on lead.cliente_idcliente=cliente.idcliente "
				+" where cliente.nome like '%%' ";
		if ((dataProxContatoInicial != null) && (dataProxContatoFinal != null)) {
			sql = sql + " and lead.dataenvio>='" + Formatacao.ConvercaoDataSql(dataProxContatoInicial) + "' and lead.dataenvio<='"
					+ Formatacao.ConvercaoDataSql(dataProxContatoFinal) + "'";
		}
		if ((datarecebimentoInicial != null) && (datarecebimentoFinal != null)) {
			sql = sql + " and lead.dataenvio>='" + Formatacao.ConvercaoDataSql(datarecebimentoInicial) + "' and lead.dataenvio<='"
					+ Formatacao.ConvercaoDataSql(datarecebimentoFinal) + "'";
		}
		if ((dataUltContatoInicial != null) && (dataUltContatoFinal != null)) {
			sql = sql + " and lead.dataenvio>='" + Formatacao.ConvercaoDataSql(dataUltContatoInicial) + "' and lead.dataenvio<='"
					+ Formatacao.ConvercaoDataSql(dataUltContatoFinal) + "'";
		}
		if(unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
			sql = sql + " and lead.unidadenegocio_idunidadenegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		if (consultor != null && consultor.getIdusuario() != null) {
			sql = sql + " and lead.usuario_idusuario=" + consultor.getIdusuario();
		}
		sql = sql + " order by cliente.nome";
		return sql;
	}

}
