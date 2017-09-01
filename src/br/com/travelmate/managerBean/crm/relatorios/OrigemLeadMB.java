package br.com.travelmate.managerBean.crm.relatorios;

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

import br.com.travelmate.bean.RelatorioErroBean;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class OrigemLeadMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario consultor;
	private List<Usuario> listaConsultor;
	private Unidadenegocio unidadenegocio;
	private List<Unidadenegocio> listaUnidade;
	private Date dataInicial;
	private Date dataFinal;
	private Integer idUnidade;

	@PostConstruct
	public void init() {
		listaUnidade = GerarListas.listarUnidade();
	}

	public Usuario getConsultor() {
		return consultor;
	}

	public void setConsultor(Usuario consultor) {
		this.consultor = consultor;
	}

	public List<Usuario> getListaConsultor() {
		return listaConsultor;
	}

	public void setListaConsultor(List<Usuario> listaConsultor) {
		this.listaConsultor = listaConsultor;
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

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public void gerarListaConsultor() {
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			listaConsultor = GerarListas.listarUsuarios(
					"Select u FROM Usuario u where u.situacao='Ativo'" + " and u.unidadenegocio.idunidadeNegocio="
							+ unidadenegocio.getIdunidadeNegocio() + " order by u.nome");
			idUnidade = unidadenegocio.getIdunidadeNegocio();
		}
	}

	public void cancelar() {
		RequestContext.getCurrentInstance().closeDialog("");
	}

	public String gerarRelatorio() throws SQLException, IOException {
		if ((dataInicial != null) && (dataFinal != null)) {
			if(idUnidade!=null){
				UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		        unidadenegocio = unidadeNegocioFacade.consultar(idUnidade);
			}
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
					.getContext();
			String caminhoRelatorio = "";
			Map<String, Object> parameters = new HashMap<String, Object>();
			caminhoRelatorio = "reports/comercial/reportOrigemLeads.jasper"; 
			parameters.put("sql", gerarSql());
			File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
			BufferedImage logo = ImageIO.read(f);
			parameters.put("logo", logo); 
			if (unidadenegocio == null || unidadenegocio.getIdunidadeNegocio() == null) {
				parameters.put("nomeFantasia", "Todos");
			} else {
				parameters.put("nomeFantasia", unidadenegocio.getNomerelatorio());
			}
			if (consultor == null || consultor.getIdusuario() == null) {
				parameters.put("consultor", "Todos");
			} else {
				parameters.put("consultor", consultor.getNome());
			}
			GerarRelatorio gerarRelatorio = new GerarRelatorio();
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Origem leads", "");
			} catch (JRException ex) {
				Logger.getLogger(OrigemLeadMB.class.getName()).log(Level.SEVERE, null, ex);
			}
		}else{
			Mensagem.lancarMensagemInfo("Atenção!", "Informe um período.");
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Informe um período.");
		}
		return "";
	}

	public String gerarSql() {
		String sql = "Select distinct usuario.nome as consultor, unidadenegocio.nomerelatorio as nomeFantasia, "
				+ " publicidade.idpublicidade,publicidade.descricao as publicidade, lead.dataenvio, count(lead.idlead) as numerolead"
				+" from lead join unidadenegocio on lead.unidadenegocio_idunidadenegocio = unidadenegocio.idunidadenegocio"
				+" join usuario on lead.usuario_idusuario = usuario.idusuario"
				+" join publicidade on lead.publicidade_idpublicidade = publicidade.idpublicidade"
				+" where";
		if ((dataInicial != null) && (dataFinal != null)) {
			sql = sql + " lead.dataenvio>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' and lead.dataenvio<='"
					+ Formatacao.ConvercaoDataSql(dataFinal) + "'";
		}
		if(unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
			sql = sql + " and lead.unidadenegocio_idunidadenegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		if (consultor != null && consultor.getIdusuario() != null) {
			sql = sql + " and lead.usuario_idusuario=" + consultor.getIdusuario();
		}
		sql = sql + " Group by publicidade.idpublicidade,usuario.idusuario "
				+ " order by publicidade.idpublicidade, usuario.idusuario, lead.cliente_idcliente;";
		return sql;
	}

}
