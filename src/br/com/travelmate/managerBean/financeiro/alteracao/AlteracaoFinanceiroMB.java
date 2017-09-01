package br.com.travelmate.managerBean.financeiro.alteracao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.AlteracaofinanceiroFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.model.Alteracaofinanceiro;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class AlteracaoFinanceiroMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Unidadenegocio> listaUnidadeNegocio;
    private Unidadenegocio unidadenegocio;
    private Date dataInicio;
    private Date dataTermino;
    private List<Usuario> listaConsultor;
    private Usuario usuario;
    private String situacao="false";
    private List<Alteracaofinanceiro> listaAlteracao;
    private String idvenda;
    
    @PostConstruct()
    public void init(){
    	unidadenegocio = new Unidadenegocio();
    	usuario = new Usuario();
        gerarListaUnidadeNegocio();
        gerarListaAlteracao();
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


	public String getSituacao() {
		return situacao;
	}


	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}


	public List<Alteracaofinanceiro> getListaAlteracao() {
		return listaAlteracao;
	}


	public void setListaAlteracao(List<Alteracaofinanceiro> listaAlteracao) {
		this.listaAlteracao = listaAlteracao;
	}


	public String getIdvenda() {
		return idvenda;
	}


	public void setIdvenda(String idvenda) {
		this.idvenda = idvenda;
	}


	public void gerarListaUnidadeNegocio(){
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
        if (listaUnidadeNegocio==null){
            listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
        }
    }
	
	public void gerarListaAlteracao(){
		AlteracaofinanceiroFacade alteracaofinanceiroFacade = new AlteracaofinanceiroFacade();
		String sql = "select a from Alteracaofinanceiro a where a.situacao=false order by a.data desc";
		listaAlteracao = alteracaofinanceiroFacade.listar(sql);
		if(listaAlteracao==null){
			listaAlteracao = new ArrayList<Alteracaofinanceiro>();
		} 
	}
	
	
	public void pesquisar(){
		AlteracaofinanceiroFacade alteracaofinanceiroFacade = new AlteracaofinanceiroFacade();
		String sql = "select a from Alteracaofinanceiro a where a.situacao="+situacao;
		if(unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
			sql = sql + " and a.usuario.unidadenegocio.idunidadeNegocio="+unidadenegocio.getIdunidadeNegocio();
		}
		if (usuario!=null && usuario.getIdusuario()!=null){
            sql = sql + " and a.usuario.idusuario=" + usuario.getIdusuario();
        }
		if(dataInicio!=null && dataTermino!=null){
			sql= sql+" and a.data >='"  + Formatacao.ConvercaoDataSql(dataInicio) + "' and a.data<='"
                        + Formatacao.ConvercaoDataSql(dataTermino) + "'";
		}
		if(idvenda!=null && idvenda.length()>0){
			sql = sql + " and a.vendas.idvendas="+idvenda;
		}
		sql = sql + " order by a.data desc"; 
		listaAlteracao = alteracaofinanceiroFacade.listar(sql);
		if(listaAlteracao==null){
			listaAlteracao = new ArrayList<Alteracaofinanceiro>();
		}
	}
	
	public void limpar(){
		situacao= "false";
		idvenda="";
		usuario=null;
		unidadenegocio=null;
		dataInicio=null;
		dataTermino=null;
		gerarListaAlteracao();
	}
	
	
	public String visualizar(Alteracaofinanceiro alteracaofinanceiro){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("alteracaofinanceiro", alteracaofinanceiro);
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("contentWidth",800);
        RequestContext.getCurrentInstance().openDialog("alteracaoFinanceiroParcelas", options, null);
		return "";
	}
	
	
	public void salvarSituacao(Alteracaofinanceiro alteracaofinanceiro){
		listaAlteracao.remove(alteracaofinanceiro);
		AlteracaofinanceiroFacade alteracaofinanceiroFacade = new AlteracaofinanceiroFacade();
		alteracaofinanceiro.setSituacao(true);
		alteracaofinanceiro = alteracaofinanceiroFacade.salvar(alteracaofinanceiro);
	}
	
	public void gerarListaConsultor(){
        UsuarioFacade usuarioFacade = new UsuarioFacade();
        listaConsultor = usuarioFacade.listar("select u from Usuario u where u.situacao='Ativo' and u.unidadenegocio.idunidadeNegocio="+unidadenegocio.getIdunidadeNegocio()+" order by u.nome");
        if (listaConsultor==null){
            listaConsultor = new ArrayList<Usuario>();
        }
    }
}
