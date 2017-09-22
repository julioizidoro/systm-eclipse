/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package br.com.travelmate.managerBean.financeiro.faturafranquia;

import br.com.travelmate.facade.FaturaFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;  
import br.com.travelmate.model.Fatura;

import br.com.travelmate.model.Faturafaturaajuste;
import br.com.travelmate.model.Faturafaturafraquias;
import br.com.travelmate.model.Faturafranquias;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

/**
 *
 * @author Kamila
 */
@Named
@ViewScoped
public class RelatorioFaturaMB implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Unidadenegocio unidadenegocio;
	private List<GerarFaturaBean> listaFaturaAtual;
	private List<Fatura> listaFaturaAberta;
	private Fatura fatura; 
	private int mes;
	private int ano;
	private boolean mesatual;
	private boolean btnBanco;
	private boolean btnComprovante;

	@PostConstruct()
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		mes = (int) session.getAttribute("mes");
		ano = (int) session.getAttribute("ano");
		unidadenegocio = (Unidadenegocio) session.getAttribute("unidadenegocio");
		session.removeAttribute("unidadenegocio");
		session.removeAttribute("ano");
		session.removeAttribute("mes");
		int anoAtual = Formatacao.getAnoData(new Date());
		int mesAtual = Formatacao.getMesData(new Date()) + 1;
		if(mesAtual>=mes && anoAtual>=ano){
			mesatual=true;
		} 
		gerarListaFaturaAjusteAtual();
		gerarListaFaturaAtual();  
		if(fatura.getSaldodevedor()>0){
			btnBanco=true;
		}else if(fatura.getSaldodevedor()<0){
			btnComprovante=true;    
		}
		if(mesatual){
			gerarListaFaturaAjusteAbertas();
			gerarListaFaturasAbertas();
			if(listaFaturaAberta!=null) { 
				for (int i = 0; i < listaFaturaAberta.size(); i++) {
					if((listaFaturaAberta.get(i).getFaturafaturafraquiasList()==null 
							|| listaFaturaAberta.get(i).getFaturafaturafraquiasList().size()==0)
						&& (listaFaturaAberta.get(i).getFaturafaturaajusteList()==null 
							|| listaFaturaAberta.get(i).getFaturafaturaajusteList().size()==0)) {
						listaFaturaAberta.remove(i);
					}
				}
			}else {
				listaFaturaAberta =  new ArrayList<>();
			}
		} 
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<GerarFaturaBean> getListaFaturaAtual() {
		return listaFaturaAtual;
	}

	public void setListaFaturaAtual(List<GerarFaturaBean> listaFaturaAtual) {
		this.listaFaturaAtual = listaFaturaAtual;
	}

	public List<Fatura> getListaFaturaAberta() {
		return listaFaturaAberta;
	}

	public void setListaFaturaAberta(List<Fatura> listaFaturaAberta) {
		this.listaFaturaAberta = listaFaturaAberta;
	}

	public Fatura getFatura() {
		return fatura;
	}

	public void setFatura(Fatura fatura) {
		this.fatura = fatura;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}
 
	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public boolean isMesatual() {
		return mesatual;
	}

	public void setMesatual(boolean mesatual) {
		this.mesatual = mesatual;
	}

	public boolean isBtnBanco() {
		return btnBanco;
	}

	public void setBtnBanco(boolean btnBanco) {
		this.btnBanco = btnBanco;
	}

	public boolean isBtnComprovante() {
		return btnComprovante;
	}

	public void setBtnComprovante(boolean btnComprovante) {
		this.btnComprovante = btnComprovante;
	}

	public void gerarListaFaturaAtual() { 
		if (fatura != null && fatura.getIdfatura() != null) { 
			List<Faturafaturafraquias> lista = fatura.getFaturafaturafraquiasList();
			for (int i= 0; i < lista.size(); i++) { 
				GerarFaturaBean gerarFaturaBean =adicionarListaFaturaBean(lista.get(i));
				if(gerarFaturaBean!=null){
					listaFaturaAtual.add(gerarFaturaBean); 
				}
			} 
		}
	}
	
	public void gerarListaFaturaAjusteAtual() {
		FaturaFacade faturaFacade = new FaturaFacade(); 
		fatura = faturaFacade.getFatura("select f from Fatura f where f.ano=" + ano + " and f.mes=" + mes
				+ " and f.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio());
		if (fatura == null || fatura.getIdfatura() == null) {
			if (mes == 1) {
				mes = 12;
				ano = ano - 1;
			} else {
				mes = mes - 1;
			}
			fatura = faturaFacade.getFatura("select f from Fatura f where f.ano=" + ano + " and f.mes=" + mes
					+ " and f.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio());
		}
		if (fatura != null && fatura.getIdfatura() != null) {
			listaFaturaAtual = new ArrayList<>(); 
			List<Faturafaturaajuste> lista = fatura.getFaturafaturaajusteList();
			for (int i= 0; i < lista.size(); i++) {
				GerarFaturaBean gerarFaturaBean =adicionarListaFaturaAjusteBean(lista.get(i));
				if(gerarFaturaBean!=null){
					listaFaturaAtual.add(gerarFaturaBean); 
				}
			} 
		}
	}

	public float gerarValorDesagio(Faturafranquias faturafranquias) {
		int idProduto = faturafranquias.getVendascomissao().getVendas().getProdutos().getIdprodutos();
		if ((aplicacaoMB.getParametrosprodutos().getPacotes() == idProduto)
				|| (aplicacaoMB.getParametrosprodutos().getPassagem() == idProduto)) {
			return 0;
		} else
			return faturafranquias.getVendascomissao().getDesagio();
	} 

	public void gerarListaFaturasAbertas() { 
		if (listaFaturaAberta != null && listaFaturaAberta.size() > 0) {
			for (int i = 0; i < listaFaturaAberta.size(); i++) { 
				List<Faturafaturafraquias> lista = listaFaturaAberta.get(i).getFaturafaturafraquiasList();
				List<GerarFaturaBean> listaFaturaItensAberta = listaFaturaAberta.get(i).getListaFaturaItensAberta();
				for (int j = 0; j < lista.size(); j++) {
					GerarFaturaBean gerarFaturaBean = adicionarListaFaturaBean(lista.get(j));
					if(gerarFaturaBean!=null){
						listaFaturaItensAberta.add(gerarFaturaBean);
						
					}
				} 
				listaFaturaAberta.get(i).setListaFaturaItensAberta(listaFaturaItensAberta);
			}
		}
	}
	
	public void gerarListaFaturaAjusteAbertas() {
		FaturaFacade faturaFacade = new FaturaFacade();
		int ano = Formatacao.getAnoData(new Date());
		int mes = Formatacao.getMesData(new Date()) + 1;
		listaFaturaAberta = faturaFacade.listar("select f from Fatura f where ((f.ano=" + ano + " and f.mes>" + mes
				+ ") or f.ano>"+ano+") and f.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio()
				+" order by f.ano,f.mes");
		if (listaFaturaAberta != null && listaFaturaAberta.size() > 0) {
			for (int i = 0; i < listaFaturaAberta.size(); i++) {
				List<GerarFaturaBean> listaGerarFaturaBean = new ArrayList<>();
				List<Faturafaturaajuste> lista = listaFaturaAberta.get(i).getFaturafaturaajusteList();
				for (int j = 0; j < lista.size(); j++) {
					GerarFaturaBean gerarFaturaBean = adicionarListaFaturaAjusteBean(lista.get(j));
					if(gerarFaturaBean!=null){
						listaGerarFaturaBean.add(gerarFaturaBean);
					}
				}
				listaFaturaAberta.get(i).setListaFaturaItensAberta(listaGerarFaturaBean);
			}
		}
	}

	public GerarFaturaBean adicionarListaFaturaBean(Faturafaturafraquias faturafaturafraquias) {
		GerarFaturaBean faturaBean = new GerarFaturaBean();
		faturaBean.setCliente(
				faturafaturafraquias.getFaturafranquias().getVendascomissao().getVendas().getCliente().getNome());
		faturaBean
				.setComissao(faturafaturafraquias.getFaturafranquias().getVendascomissao().getComissaofranquiabruta());
		faturaBean.setDataincio(faturafaturafraquias.getFaturafranquias().getVendascomissao().getDatainicioprograma());
		faturaBean
				.setDatavenda(faturafaturafraquias.getFaturafranquias().getVendascomissao().getVendas().getDataVenda());
		faturaBean.setDesagio(faturafaturafraquias.getFaturafranquias().getVendascomissao().getCustofinanceirofranquia());
		faturaBean.setDescontoloja(faturafaturafraquias.getFaturafranquias().getVendascomissao().getDescontoloja());
		faturaBean.setDescontomatriz(faturafaturafraquias.getFaturafranquias().getVendascomissao().getDescontotm());
		faturaBean.setFaturafranquias(faturafaturafraquias.getFaturafranquias());
		faturaBean.setFornecedor(faturafaturafraquias.getFaturafranquias().getVendascomissao().getVendas()
				.getFornecedorcidade().getFornecedor().getNome());
		faturaBean.setPercentualcomissao(faturafaturafraquias.getFaturafranquias().getPercentualcomissao());
		faturaBean.setPrograma(
				faturafaturafraquias.getFaturafranquias().getVendascomissao().getVendas().getProdutos().getDescricao());
		faturaBean.setRecebidomatiz(faturafaturafraquias.getFaturafranquias().getPagomatriz());
		faturaBean.setTaxatm(faturafaturafraquias.getFaturafranquias().getVendascomissao().getTaxatm());
		faturaBean.setTotal(faturafaturafraquias.getFaturafranquias().getLiquidopagar());
		int id = faturafaturafraquias.getFaturafranquias().getVendascomissao().getVendas().getProdutos().getIdprodutos();
		if(aplicacaoMB.getParametrosprodutos().getPassagem()!=id){
			faturaBean.setValorcomissionavel(faturafaturafraquias.getFaturafranquias().getVendascomissao().getValorcomissionavel());
		}else{
			faturaBean.setValorcomissionavel(faturafaturafraquias.getFaturafranquias().getVendascomissao().getVendas().getValor());
		}
		faturaBean.setValortotal(faturafaturafraquias.getFaturafranquias().getVendascomissao().getVendas().getValor());
		faturaBean.setFatura(faturafaturafraquias.getFaturafranquias().isFatura());
		faturaBean.setOrdenacao(2);
		faturaBean.setAno(faturafaturafraquias.getFaturafranquias().getAno());
		faturaBean.setMes(faturafaturafraquias.getFaturafranquias().getMes());
		return faturaBean;
	}
	
	public GerarFaturaBean adicionarListaFaturaAjusteBean(Faturafaturaajuste faturafaturaajuste) {
		GerarFaturaBean faturaBean = new GerarFaturaBean();
		faturaBean.setCliente(faturafaturaajuste.getFaturaajustes().getItem());
		faturaBean.setComissao(0);
		faturaBean.setDataincio(null);
		faturaBean.setDatavenda(faturafaturaajuste.getFaturaajustes().getDatalancamento());
		faturaBean.setDesagio(0);
		faturaBean.setDescontoloja(0);
		faturaBean.setDescontomatriz(0);
		faturaBean.setFatura(true);
		faturaBean.setFaturaajustes(faturafaturaajuste.getFaturaajustes());
		faturaBean.setFornecedor("");
		faturaBean.setOrdenacao(1);
		faturaBean.setPercentualcomissao(0);
		faturaBean.setPrograma(faturafaturaajuste.getFaturaajustes().getDescricao());
		faturaBean.setRecebidomatiz(0);
		faturaBean.setSelecionado(false);
		faturaBean.setTaxatm(0);
		faturaBean.setValorcomissionavel(0); 
		faturaBean.setValortotal(faturafaturaajuste.getFaturaajustes().getValor()); 
		faturaBean.setTotal(faturaBean.getValortotal());
		return faturaBean;
	}
	
	public String retornarMesAno(int mes, int ano){
		return Formatacao.nomeMes(mes)+"/"+ano; 
	}
	
	public String retornarAnteriorMesAno(int mes, int ano){
		if (mes == 1) {
			mes = 12;
			ano = ano - 1;
		} else {
			mes = mes - 1;
		}
		return Formatacao.nomeMes(mes)+"/"+ano; 
	}
	
	public float saldoLancamento(List<GerarFaturaBean> lista){
		float valor = 0.0f;
		if(lista!=null){
			for (int i = 0; i < lista.size(); i++) {
				valor = valor + lista.get(i).getTotal();
			}
		}
		return valor;
	}
	 
	
	public String retornoCorLetra(float valor){
		if(valor>=0 && valor<0.01){
			return "black";
		}else if(valor<0){
			return "red";
		}else return "blue";
	}
	
	public String corsaldoLancamento(List<GerarFaturaBean> lista){
		float valor = 0.0f;
		if(lista!=null){
			for (int i = 0; i < lista.size(); i++) {
				valor = valor + lista.get(i).getTotal();
			}
		}
		if(valor>=0 && valor<0.01){
			return "black";
		}else if(valor<0){
			return "red";
		}else return "blue";
	}
	
	
	public void visualizarDadosBanco(){
		if(fatura!=null && fatura.getIdfatura()!=null){
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 300); 
			session.setAttribute("fatura", fatura); 
			session.setAttribute("alteracao", false); 
			RequestContext.getCurrentInstance().openDialog("faturaBanco", options, null);
		}else{
			Mensagem.lancarMensagemErro("Nenhuma fatura iníciada!", "");
		}
	}
	
	public void visualizarComprovantes(){
		if(fatura!=null && fatura.getIdfatura()!=null){
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 550); 
			session.setAttribute("fatura", fatura); 
			session.setAttribute("alteracao", true); 
			RequestContext.getCurrentInstance().openDialog("faturaComprovante", options, null);
		}else{
			Mensagem.lancarMensagemErro("Nenhuma fatura iníciada!", "");
		}
	}

}
