package br.com.travelmate.managerBean.cartaocredito.lancamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.CartaoCreditoFacade;
import br.com.travelmate.facade.CartaoCreditoLancamentoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cartaocredito;
import br.com.travelmate.model.Cartaocreditolancamento;


@Named
@ViewScoped
public class PesquisarCartaoCreditoLancamentoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Cartaocredito> listaCartaoCredito;
	private Cartaocredito cartaocredito;
	private String mes;
	private String ano;
	private List<Cartaocreditolancamento> listaLancamento;
	
	
	
	@PostConstruct
	public void init(){
		gerarlistaCartaoCredito();
	}
	
	
	
	public List<Cartaocredito> getListaCartaoCredito() {
		return listaCartaoCredito;
	}



	public void setListaCartaoCredito(List<Cartaocredito> listaCartaoCredito) {
		this.listaCartaoCredito = listaCartaoCredito;
	}



	public Cartaocredito getCartaocredito() {
		return cartaocredito;
	}



	public void setCartaocredito(Cartaocredito cartaocredito) {
		this.cartaocredito = cartaocredito;
	}



	public String getMes() {
		return mes;
	}



	public void setMes(String mes) {
		this.mes = mes;
	}



	public String getAno() {
		return ano;
	}



	public void setAno(String ano) {
		this.ano = ano;
	}



	public void gerarlistaCartaoCredito() {
		String sql = "select c from Cartaocredito c where c.situacao=1";
		if ((usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()!=1) && (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()!=3)) {
			sql = sql + " and c.unidadenegocio.idunidadeNegocio=" + usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		}
		sql = sql + " order by c.nome";
		CartaoCreditoFacade cartaoCreditoFacade = new CartaoCreditoFacade();
		listaCartaoCredito = cartaoCreditoFacade.listar(sql);
		if (listaCartaoCredito == null) {
			listaCartaoCredito = new ArrayList<Cartaocredito>();
		}
	}
	
	
	public void pesquisar(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		gerarListaLancamentos();
		session.setAttribute("listaLancamento", listaLancamento);
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	
	public void gerarListaLancamentos(){
		CartaoCreditoLancamentoFacade cartaoCreditoLancamentoFacade = new CartaoCreditoLancamentoFacade();
		String diaFinal = "30";
		if (mes.equalsIgnoreCase("2")) {
			diaFinal = "30";
		}
		String sql="select c from Cartaocreditolancamento c where c.data>='"+ano+"-" + mes + "-01" +"' and c.data<='"+ ano + "-" + mes + "-"+ diaFinal+"' and"
				+ " c.cartaocredito.idcartaocredito="+ cartaocredito.getIdcartaocredito() +" order by c.data desc";
		listaLancamento = cartaoCreditoLancamentoFacade.listar(sql);
		if (listaLancamento == null) {
			listaLancamento = new ArrayList<Cartaocreditolancamento>();
		}
	}

}
