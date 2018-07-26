package br.com.travelmate.managerBean.crm.relatorios;

import br.com.travelmate.dao.LeadDao;
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
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
 

@Named
@ViewScoped
public class SituacaoLeadMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private LeadDao leadDao;
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
	
	public int retornarsituacao1(){
		if(unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
			Date data = new Date();
			String sql = "select l from Lead l where l.situacao=1 and l.dataenvio<='" + Formatacao.ConvercaoDataSql(data)
					+ "' and l.unidadenegocio.idunidadeNegocio="+ unidadenegocio.getIdunidadeNegocio();
			if (consultor!=null && consultor.getIdusuario()!=null) {
				sql = sql + " and l.usuario.idusuario=" + consultor.getIdusuario();
			}
			List<Lead> listaLead = leadDao.lista(sql);
			if(listaLead!=null){
				return listaLead.size();
			}
		}
		return 0;
	}
	
	public int retornarsituacao2(){
		if(unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
			Date data = new Date();
			String sql = "select l from Lead l where l.situacao=2 and l.dataenvio<='" + Formatacao.ConvercaoDataSql(data)
					+ "' and l.unidadenegocio.idunidadeNegocio="+ unidadenegocio.getIdunidadeNegocio();
			if (consultor!=null && consultor.getIdusuario()!=null) {
				sql = sql + " and l.usuario.idusuario=" + consultor.getIdusuario();
			}
			List<Lead> listaLead = leadDao.lista(sql);
			if(listaLead!=null){
				return listaLead.size();
			}
		}
		return 0;
	}
	
	public int retornarsituacao3(){
		if(unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
			Date data = new Date();
			String sql = "select l from Lead l where l.situacao=3 and l.dataenvio<='" + Formatacao.ConvercaoDataSql(data)
					+ "' and l.unidadenegocio.idunidadeNegocio="+ unidadenegocio.getIdunidadeNegocio();
			if (consultor!=null && consultor.getIdusuario()!=null) {
				sql = sql + " and l.usuario.idusuario=" + consultor.getIdusuario();
			}
			List<Lead> listaLead = leadDao.lista(sql);
			if(listaLead!=null){
				return listaLead.size();
			}
		}
		return 0;
	}
	
	public int retornarsituacao4(){
		if(unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
			Date data = new Date();
			String sql = "select l from Lead l where l.situacao=4 and l.dataenvio<='" + Formatacao.ConvercaoDataSql(data)
					+ "' and l.unidadenegocio.idunidadeNegocio="+ unidadenegocio.getIdunidadeNegocio();
			if (consultor!=null && consultor.getIdusuario()!=null) {
				sql = sql + " and l.usuario.idusuario=" + consultor.getIdusuario();
			}
			List<Lead> listaLead = leadDao.lista(sql);
			if(listaLead!=null){
				return listaLead.size();
			}
		}
		return 0;
	} 

}
