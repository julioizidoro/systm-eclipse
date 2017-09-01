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
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.model.Tipocontato;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class MotivoCancelamentoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario consultor;
	private List<Usuario> listaConsultor;
	private Unidadenegocio unidadenegocio;
	private List<Unidadenegocio> listaUnidade;
	private Tipocontato tipocontato;
	private List<Tipocontato> listaTipocontato;
	private Date cancelamentoInicial;
	private Date cancelamentoFinal;
	private Date contatoInicial;
	private Date contatoFinal;
	private Integer idUnidade;

	@PostConstruct
	public void init() {
		listaUnidade = GerarListas.listarUnidade();
		listaTipocontato = GerarListas.listarTipoContato("Select t from Tipocontato t");
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

	public Tipocontato getTipocontato() {
		return tipocontato;
	}

	public void setTipocontato(Tipocontato tipocontato) {
		this.tipocontato = tipocontato;
	}

	public List<Tipocontato> getListaTipocontato() {
		return listaTipocontato;
	}

	public void setListaTipocontato(List<Tipocontato> listaTipocontato) {
		this.listaTipocontato = listaTipocontato;
	}

	public Date getCancelamentoInicial() {
		return cancelamentoInicial;
	}

	public void setCancelamentoInicial(Date cancelamentoInicial) {
		this.cancelamentoInicial = cancelamentoInicial;
	}

	public Date getCancelamentoFinal() {
		return cancelamentoFinal;
	}

	public void setCancelamentoFinal(Date cancelamentoFinal) {
		this.cancelamentoFinal = cancelamentoFinal;
	}

	public Date getContatoInicial() {
		return contatoInicial;
	}

	public void setContatoInicial(Date contatoInicial) {
		this.contatoInicial = contatoInicial;
	}

	public Date getContatoFinal() {
		return contatoFinal;
	}

	public void setContatoFinal(Date contatoFinal) {
		this.contatoFinal = contatoFinal;
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
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		caminhoRelatorio = "reports/comercial/reportMotivoCancelamento.jasper";
		if(idUnidade!=null){
			UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
			unidadenegocio = unidadeNegocioFacade.consultar(idUnidade); 
		} 
		parameters.put("sql", gerarSql());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		if(unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
			parameters.put("nomeFantasia", unidadenegocio.getNomerelatorio()); 
		}else{
			parameters.put("nomeFantasia", "Todos"); 
		}
		parameters.put("logo", logo); 
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "motivocancelamento", "");
		} catch (JRException ex) {
			Logger.getLogger(MotivoCancelamentoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarSql() {
		String sql = "Select distinct  usuario.nome as consultor, unidadenegocio.nomerelatorio as unidade,"
				+ " motivocancelamento.idmotivocancelamento, motivocancelamento.motivo as titulomotivo,"
				+ " cliente.nome as cliente,lead.motivocancelamento as motivocancelamento" + " from lead"
				+ " join unidadenegocio on lead.unidadenegocio_idunidadenegocio = unidadenegocio.idunidadenegocio"
				+ " join usuario on lead.usuario_idusuario = usuario.idusuario"
				+ " join tipocontato on lead.tipocontato_idtipocontato = tipocontato.idtipocontato"
				+ " join cliente on lead.cliente_idcliente= cliente.idcliente"
				+ " join motivocancelamento on lead.motivocancelamento_idmotivocancelamento = motivocancelamento.idmotivocancelamento"
				+ " where motivocancelamento.idmotivocancelamento<>1";
		if ((cancelamentoInicial != null) && (cancelamentoFinal != null)) {
			sql = sql + " and lead.dataenvio>='" + Formatacao.ConvercaoDataSql(cancelamentoInicial)
					+ "' and lead.dataenvio<='" + Formatacao.ConvercaoDataSql(cancelamentoFinal) + "' ";
		}
		if ((contatoInicial != null) && (contatoFinal != null)) {
			sql = sql + " and lead.dataultimocontato>='" + Formatacao.ConvercaoDataSql(contatoInicial)
					+ "' and lead.dataultimocontato<='" + Formatacao.ConvercaoDataSql(contatoFinal) + "' ";
		}
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio()!=null) {
			sql = sql + " and lead.unidadenegocio_idunidadenegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		if (consultor != null && consultor.getIdusuario()!=null) {
			sql = sql + " and lead.usuario_idusuario=" + consultor.getIdusuario();
		}
		if (tipocontato != null && tipocontato.getIdtipocontato()!=null) {
			sql = sql + " and lead.tipocontato_idtipocontato=" + tipocontato.getIdtipocontato();
		}
		sql = sql + " Group by motivocancelamento.idmotivocancelamento, unidadenegocio.idunidadeNegocio, "+
				"usuario.idusuario, cliente.idcliente order by motivocancelamento.idmotivocancelamento"; 
		return sql;
	}

}
