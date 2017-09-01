package br.com.travelmate.managerBean.crm.relatorios;

import br.com.travelmate.facade.LeadFacade;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;


@Named
@ViewScoped
public class SituacaoContatoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Unidadenegocio unidadenegocio;
	private List<Unidadenegocio> listaUnidade;
	private Usuario consultor;
	private List<Usuario> listaConsultor;

	@PostConstruct
	public void init() {
		listaUnidade = GerarListas.listarUnidade();
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

	public String fechar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	public void gerarListaConsultor() {
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			listaConsultor = GerarListas.listarUsuarios(
					"Select u FROM Usuario u where u.situacao='Ativo'" + " and u.unidadenegocio.idunidadeNegocio="
							+ unidadenegocio.getIdunidadeNegocio() + " order by u.nome");
		} 
	}
	
	public void pesquisar(){
		if(unidadenegocio==null){
			Mensagem.lancarMensagemErro("Atenção!", "Selecione uma unidade.");
		}
	}
	
	public int retornarNumeroNovos(){
		if(unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
			String sql="select l from Lead l where l.situacao<>0 and l.tipocontato.tipo='Novo'"
					+ " and l.dataultimocontato is null and l.unidadenegocio.idunidadeNegocio="
					+ unidadenegocio.getIdunidadeNegocio();
			if (consultor!=null && consultor.getIdusuario()!=null) {
				sql = sql + " and l.usuario.idusuario=" + consultor.getIdusuario();
			}
			LeadFacade leadFacade = new LeadFacade();
			List<Lead> listaLead = leadFacade.lista(sql);
			if(listaLead!=null){
				return listaLead.size();
			}
		}
		return 0;
	}
	
	public int retornarNumeroHoje(){
		if(unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
			Date data = new Date();
			String sql="select l from Lead l where l.situacao<>0 and l.tipocontato.tipo<>'Novo' and"
					+ " l.dataproximocontato='" + Formatacao.ConvercaoDataSql(data)
					+ "' and l.unidadenegocio.idunidadeNegocio="+ unidadenegocio.getIdunidadeNegocio();
			if (consultor!=null && consultor.getIdusuario()!=null) {
				sql = sql + " and l.usuario.idusuario=" + consultor.getIdusuario();
			}
			LeadFacade leadFacade = new LeadFacade();
			List<Lead> listaLead = leadFacade.lista(sql);
			if(listaLead!=null){
				return listaLead.size();
			}
		}
		return 0;
	}
	
	public int retornarNumeroAtrasados(){
		if(unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
			Date data = new Date();
			String sql = "select l from Lead l where l.situacao<>0 and l.tipocontato.tipo<>'Novo' and"
					+ " l.dataproximocontato < '" + Formatacao.ConvercaoDataSql(data)
					+ "' and l.unidadenegocio.idunidadeNegocio="+ unidadenegocio.getIdunidadeNegocio();
			if (consultor!=null && consultor.getIdusuario()!=null) {
				sql = sql + " and l.usuario.idusuario=" + consultor.getIdusuario();
			}
			LeadFacade leadFacade = new LeadFacade();
			List<Lead> listaLead = leadFacade.lista(sql);
			if(listaLead!=null){
				return listaLead.size();
			}
		}
		return 0;
	}
	
	public int retornarNumero7dias(){
		if(unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
			Date data;
			try {
				data = Formatacao.SomarDiasDatas(new Date(), 7);
			} catch (Exception e) {
				data = null;
			}
			String sql = "select l from Lead l where l.situacao<>0 and l.tipocontato.tipo<>'Novo' and"
					+ " l.dataproximocontato>'" + Formatacao.ConvercaoDataSql(new Date())
					+ "' and l.dataproximocontato<'" + Formatacao.ConvercaoDataSql(data)
					+ "' and l.unidadenegocio.idunidadeNegocio="+ unidadenegocio.getIdunidadeNegocio();
			if (consultor!=null && consultor.getIdusuario()!=null) {
				sql = sql + " and l.usuario.idusuario=" + consultor.getIdusuario();
			}
			LeadFacade leadFacade = new LeadFacade();
			List<Lead> listaLead = leadFacade.lista(sql);
			if(listaLead!=null){
				return listaLead.size();
			}
		}
		return 0;
	}
	
	public int retornarNumeroTodos(){
		if(unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
			Date data = new Date();
			String sql = "select l from Lead l where l.situacao<>0 and l.dataenvio<='" + Formatacao.ConvercaoDataSql(data)
					+ "' and l.unidadenegocio.idunidadeNegocio="+ unidadenegocio.getIdunidadeNegocio();
			if (consultor!=null && consultor.getIdusuario()!=null) {
				sql = sql + " and l.usuario.idusuario=" + consultor.getIdusuario();
			}
			LeadFacade leadFacade = new LeadFacade();
			List<Lead> listaLead = leadFacade.lista(sql);
			if(listaLead!=null){
				return listaLead.size();
			}
		}
		return 0;   
	}

}
