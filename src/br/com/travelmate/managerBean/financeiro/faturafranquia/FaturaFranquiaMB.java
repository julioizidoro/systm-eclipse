/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package br.com.travelmate.managerBean.financeiro.faturafranquia;

import br.com.travelmate.facade.FaturaAjusteFacade;
import br.com.travelmate.facade.FaturaFacade;
import br.com.travelmate.facade.FaturaFaturaAjusteFacade;
import br.com.travelmate.facade.FaturaFaturaFranquiaFacade;
import br.com.travelmate.facade.FaturaFranquiasFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.VendasComissaoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB; 
import br.com.travelmate.model.Contaspagar;
import br.com.travelmate.model.Fatura;
import br.com.travelmate.model.Faturaajustes;
import br.com.travelmate.model.Faturafaturaajuste;
import br.com.travelmate.model.Faturafaturafraquias;
import br.com.travelmate.model.Faturafranquias;
import br.com.travelmate.model.Orcamentoprodutosorcamento;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
 
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Kamila
 */
@Named
@ViewScoped
public class FaturaFranquiaMB implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Unidadenegocio unidadenegocio;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private List<GerarFaturaBean> listaFatura;
	private Date dataInicial;
	private Date dataFinal;
	private int mes;
	private int ano;
	private boolean selecionarTodas;
	private Fatura fatura;

	@PostConstruct()
	public void init() {
		unidadenegocio =new Unidadenegocio();
		gerarListaUnidadeNegocio();
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}

	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
	}

	public List<GerarFaturaBean> getListaFatura() {
		return listaFatura;
	}

	public void setListaFatura(List<GerarFaturaBean> listaFatura) {
		this.listaFatura = listaFatura;
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

	public boolean isSelecionarTodas() {
		return selecionarTodas;
	}

	public void setSelecionarTodas(boolean selecionarTodas) {
		this.selecionarTodas = selecionarTodas;
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

	public Fatura getFatura() {
		return fatura;
	}

	public void setFatura(Fatura fatura) {
		this.fatura = fatura;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public void gerarListaUnidadeNegocio() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
		if (listaUnidadeNegocio == null) {
			listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
		}
	}

	public String gerarMesPagamento(Faturafranquias faturafranquias) {
		Vendascomissao vendascomissao = faturafranquias.getVendascomissao();
		if (faturafranquias.getLiquidopagar() < 0) {
			if (vendascomissao.getDatainicioprograma() != null) {
				String dataInicio = Formatacao.ConvercaoDataPadrao(vendascomissao.getDatainicioprograma());
				String sAno = dataInicio.substring(6, 10);
				String sMes = dataInicio.substring(4, 5);
				int mes = Integer.parseInt(sMes);
				int ano = Integer.parseInt(sAno);
				if (vendascomissao.getVendas().getUnidadenegocio().getMespagamento() == 1) {
					if (mes == 1) {
						mes = 12;
						ano = ano - 1;
					} else {
						mes = mes - 1;
					}
				} else {
					if (mes == 02) {
						mes = 12;
						ano = ano - 1;
					} else if (mes == 01) {
						mes = 11;
						ano = ano - 1;
					} else {
						mes = mes - 2;
					}
				}
				dataInicio = Formatacao.nomeMes(mes) + "/" + String.valueOf(ano);
				return dataInicio;
			}
			return "";
		} else {
			String dataInicio = Formatacao.ConvercaoDataPadrao(vendascomissao.getVendas().getDataVenda());
			String sAno = dataInicio.substring(6, 10);
			String sMes = dataInicio.substring(4, 5);
			int mes = Integer.parseInt(sMes);
			int ano = Integer.parseInt(sAno);
			if (mes == 12) {
				mes = 1;
				ano = ano + 1;
			} else {
				mes = mes + 1;
			}
			dataInicio = Formatacao.nomeMes(mes) + "/" + String.valueOf(ano);
			return dataInicio;
		}
	}

	public String calcularTotalProdutos() {
		FaturaFranquiasFacade faturaFranquiasFacade = new FaturaFranquiasFacade();
		List<Faturafranquias> lista = null;
		try {
			lista = faturaFranquiasFacade.listar("Select f from Faturafranquias f ");
		} catch (SQLException e) {
			  
		}
		if (lista != null) {
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i).getVendascomissao().getVendas().getOrcamento() != null) {
					if (lista.get(i).getVendascomissao().getVendas().getOrcamento()
							.getOrcamentoprodutosorcamentoList() != null) {
						if (lista.get(i).getTotalprodutos() == 0) {
							lista.get(i).setTotalprodutos(somar(lista.get(i).getVendascomissao().getVendas()
									.getOrcamento().getOrcamentoprodutosorcamentoList()));
						}
						try {
							if (lista.get(i).getTotalprodutos() == 0) {
								lista.get(i).setTotalprodutos(lista.get(i).getVendascomissao().getValorbruto());
							}
							faturaFranquiasFacade.salvar(lista.get(i));
						} catch (SQLException e) {
							  
						}
					}
				}
			}
		}
		return "";
	}

	public Float somar(List<Orcamentoprodutosorcamento> lista) {
		float total = 0.0f;
		int idloja = aplicacaoMB.getParametrosprodutos().getDescontoloja();
		int idmatriz = aplicacaoMB.getParametrosprodutos().getDescontomatriz();
		for (int i = 0; i < lista.size(); i++) {
			int idproduto = lista.get(i).getProdutosorcamento().getIdprodutosOrcamento();
			if (idproduto == idloja) {
				total = total - lista.get(i).getValorMoedaNacional();
			} else if (idproduto == idmatriz) {
				total = total - lista.get(i).getValorMoedaNacional();
			} else
				total = total + lista.get(i).getValorMoedaNacional();
		}
		return total;

	}

	public String produtoVendas(Faturafranquias faturafranquias) {
		if ((faturafranquias.getVendascomissao().getVendas().getOrcamento() != null)) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("venda", faturafranquias.getVendascomissao().getVendas());
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 570);
			RequestContext.getCurrentInstance().openDialog("produtoVendas", options, null);
		} else {
			FacesMessage msg = new FacesMessage("Venda não Possui produtos! ", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "";
	}

	public String visualizarContasReceber(Faturafranquias faturafranquias) {
		if ((faturafranquias.getVendascomissao().getVendas() != null)) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("venda", faturafranquias.getVendascomissao().getVendas());
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 620);
			RequestContext.getCurrentInstance().openDialog("visualizarContasReceber", options, null);
		} else {
			FacesMessage msg = new FacesMessage("Venda não Possui Contas a Receber! ", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "";
	}

	public String gerarValorComissionavel(Faturafranquias faturafranquias) {
		int idProduto = aplicacaoMB.getParametrosprodutos().getPacotes();
		if (faturafranquias.getVendascomissao().getVendas().getProdutos().getIdprodutos() == idProduto) {
			return Formatacao.formatarFloatString(faturafranquias.getVendascomissao().getVendas().getValor());
		} else
			return Formatacao.formatarFloatString(faturafranquias.getVendascomissao().getValorcomissionavel());
	}

	public float gerarValorDesagio(Faturafranquias faturafranquias) {
		int idProduto = faturafranquias.getVendascomissao().getVendas().getProdutos().getIdprodutos();
		if ((aplicacaoMB.getParametrosprodutos().getPacotes() == idProduto)
				|| (aplicacaoMB.getParametrosprodutos().getPassagem() == idProduto)) {
			return 0;
		} else
			return faturafranquias.getVendascomissao().getDesagio();
	}

	public void selecionarTodos() {
		if (listaFatura != null) {
			for (int i = 0; i < listaFatura.size(); i++) {
				if (!listaFatura.get(i).isFatura()) {
					listaFatura.get(i).setSelecionado(selecionarTodas);
				}
			}
		}
	}

	public String retornarImagem(GerarFaturaBean fatura) {
		if (fatura.isFatura()) {
			return "../../resources/img/bolaVerde.png";
		} else
			return "../../resources/img/bolaVermelha.png";
	}

	public String retornarTitle(GerarFaturaBean fatura) {
		if (fatura.isFatura()) {
			return "Fatura Gerada";
		} else
			return "Fatura não Gerada";
	}

	public void editar(RowEditEvent event) {
		GerarFaturaBean fatura = ((GerarFaturaBean) event.getObject());
		if(fatura.getFaturafranquias()!=null){
			if (fatura.getMes() <= 12) {
				fatura.getFaturafranquias().getVendascomissao().setComissaofranquiabruta(fatura.getComissao());
				fatura.getFaturafranquias().getVendascomissao().setDatainicioprograma(fatura.getDataincio());
				fatura.getFaturafranquias().getVendascomissao().setCustofinanceirofranquia(fatura.getDesagio());
				fatura.getFaturafranquias().getVendascomissao().setDescontoloja(fatura.getDescontoloja()); 
				fatura.getFaturafranquias().setPercentualcomissao(fatura.getPercentualcomissao());
				fatura.getFaturafranquias().setPagomatriz(fatura.getRecebidomatiz()); 
				if(fatura.isSomar()){
					this.fatura.setSaldolancamentos(this.fatura.getSaldolancamentos()-fatura.getFaturafranquias().getLiquidopagar());
					this.fatura.setSaldolancamentos(this.fatura.getSaldolancamentos()+fatura.getTotal());
					this.fatura.setValorpagar(this.fatura.getSaldofinalanterior()+this.fatura.getSaldolancamentos());
					this.fatura.setSaldodevedor(this.fatura.getValorpagar());
					FaturaFacade faturaFacade = new FaturaFacade();
					this.fatura = faturaFacade.salvar(this.fatura);
				}
				fatura.getFaturafranquias().setLiquidopagar(fatura.getTotal());
				if(fatura.getFaturafranquias().getVendascomissao().getVendas().getVendaspacote()==null) {
					fatura.getFaturafranquias().getVendascomissao().setValorcomissionavel(fatura.getValorcomissionavel());
					fatura.getFaturafranquias().getVendascomissao().setTaxatm(fatura.getTaxatm());
					fatura.getFaturafranquias().getVendascomissao().setDescontotm(fatura.getDescontomatriz());
				}
				fatura.getFaturafranquias().getVendascomissao().getVendas().setValor(fatura.getValortotal());
				fatura.getFaturafranquias().setAno(fatura.getAno());
				fatura.getFaturafranquias().setMes(fatura.getMes()); 
				FaturaFranquiasFacade faturaFranquiasFacade = new FaturaFranquiasFacade();
				VendasComissaoFacade vendasComissaoFacade = new VendasComissaoFacade();
				fatura.getFaturafranquias()
						.setVendascomissao(vendasComissaoFacade.salvar(fatura.getFaturafranquias().getVendascomissao()));
				try {
					fatura.setFaturafranquias(faturaFranquiasFacade.salvar(fatura.getFaturafranquias()));
				} catch (SQLException e) {
					  
				}
				Mensagem.lancarMensagemInfo("Editado com sucesso!", "");
			}else{
				Mensagem.lancarMensagemInfo("Mês invalido", "");
			}
		}
	}

	public void cancelarEdicao(RowEditEvent event) {
		Mensagem.lancarMensagemInfo("Operação cancelada!", "");
	}

	public boolean desabilitarCheckBox(GerarFaturaBean fatura) {
		if (fatura.isFatura()) {
			return true;
		} else
			return false;
	}

	public String gerarFaturaFaturaFranquia() {
		FaturaFaturaFranquiaFacade faturaFaturaFranquiaFacade = new FaturaFaturaFranquiaFacade();
		FaturaFacade faturaFacade = new FaturaFacade();
		Faturafaturafraquias faturafaturafraquias = null;
		boolean gerado = false;
		if(listaFatura!=null){
			fatura.setSaldolancamentos(0.0f);
			for (int i = 0; i < listaFatura.size(); i++) {
				if (listaFatura.get(i).isSelecionado() && !listaFatura.get(i).isFatura() &&
						(listaFatura.get(i).getMes()<=mes && listaFatura.get(i).getAno()<=ano)) { 
					faturafaturafraquias = new Faturafaturafraquias();
					faturafaturafraquias.setFatura(fatura);   
					faturafaturafraquias.setFaturafranquias(listaFatura.get(i).getFaturafranquias());
					faturaFaturaFranquiaFacade.salvar(faturafaturafraquias);
					listaFatura.get(i).setFatura(true);
					listaFatura.get(i).getFaturafranquias().setFatura(true);  
					fatura.setSaldolancamentos(fatura.getSaldolancamentos()+listaFatura.get(i).getTotal()); 
					FaturaFranquiasFacade faturaFranquiasFacade = new FaturaFranquiasFacade();
					try {
						listaFatura.get(i)
								.setFaturafranquias(faturaFranquiasFacade.salvar(listaFatura.get(i).getFaturafranquias()));
					} catch (SQLException e) {
						  
					}
					gerado = true;
				}else if (listaFatura.get(i).isSelecionado() && !listaFatura.get(i).isFatura()){
					if ((listaFatura.get(i).getMes()>mes && listaFatura.get(i).getAno()>=ano)
							|| (listaFatura.get(i).getMes()<=mes && listaFatura.get(i).getAno()>ano)) { 
						Fatura novafatura = faturaFacade.getFatura("select f from Fatura f where f.ano=" + listaFatura.get(i).getAno()
								+ " and f.mes=" + listaFatura.get(i).getMes() 
								+ " and f.unidadenegocio.idunidadeNegocio="+unidadenegocio.getIdunidadeNegocio());
						if(novafatura==null || novafatura.getIdfatura()==null){
							novafatura = new Fatura();
							novafatura.setAno(listaFatura.get(i).getAno());
							novafatura.setMes(listaFatura.get(i).getMes());
							novafatura.setUnidadenegocio(listaFatura.get(i).getFaturafranquias().getVendascomissao().getVendas().getUnidadenegocio());
							novafatura.setUsuario(usuarioLogadoMB.getUsuario());
							novafatura = faturaFacade.salvar(novafatura);
						}
						faturafaturafraquias = new Faturafaturafraquias();
						faturafaturafraquias.setFatura(novafatura);
						faturafaturafraquias.setFaturafranquias(listaFatura.get(i).getFaturafranquias());
						faturaFaturaFranquiaFacade.salvar(faturafaturafraquias);
						listaFatura.get(i).setFatura(true);
						listaFatura.get(i).getFaturafranquias().setFatura(true);
						if(!listaFatura.get(i).isSomar()){
							novafatura.setSaldolancamentos(novafatura.getSaldolancamentos()+listaFatura.get(i).getTotal()); 
							novafatura.setValorpagar(novafatura.getSaldofinalanterior()+novafatura.getSaldolancamentos());
							novafatura.setSaldodevedor(novafatura.getValorpagar()); 
							novafatura = faturaFacade.salvar(novafatura);
						}
						FaturaFranquiasFacade faturaFranquiasFacade = new FaturaFranquiasFacade();
						try {
							listaFatura.get(i)
									.setFaturafranquias(faturaFranquiasFacade.salvar(listaFatura.get(i).getFaturafranquias()));
						} catch (SQLException e) {
							  
						}
						gerado = true;
					}
				}else if(listaFatura.get(i).isFatura() &&
						(listaFatura.get(i).getMes()<=mes && listaFatura.get(i).getAno()<=ano)){
					fatura.setSaldolancamentos(fatura.getSaldolancamentos()+listaFatura.get(i).getTotal()); 
				}
			}
			if (gerado) {
				Mensagem.lancarMensagemInfo("Faturas geradas com sucesso!", ""); 
			} 
			fatura.setValorpagar(fatura.getSaldofinalanterior()+fatura.getSaldolancamentos());
			fatura.setSaldodevedor(fatura.getValorpagar());
			fatura = faturaFacade.salvar(fatura);
			if(mes>0 && ano>0){
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				session.setAttribute("mes", mes);
				session.setAttribute("ano", ano);
				session.setAttribute("unidadenegocio", unidadenegocio);
				return "relatorioFatura";
			} 
		}
		return "";
	}

	public void excluirFatura(GerarFaturaBean gerarFaturaBean) {
		if (gerarFaturaBean.isFatura()) {
			if(gerarFaturaBean.getFaturafranquias()!=null && gerarFaturaBean.getFaturafranquias().getIdfaturafranquias()!=null){
				FaturaFaturaFranquiaFacade faturaFaturaFranquiaFacade = new FaturaFaturaFranquiaFacade();
				String sql = "select f from Faturafaturafraquias f where f.faturafranquias.idfaturafranquias="
						+ gerarFaturaBean.getFaturafranquias().getIdfaturafranquias();
				Faturafaturafraquias faturafaturafraquias = faturaFaturaFranquiaFacade.getFatura(sql);
				if (faturafaturafraquias != null && faturafaturafraquias.getIdfaturafaturafraquias() != null) {
					faturaFaturaFranquiaFacade.excluir(faturafaturafraquias.getIdfaturafaturafraquias());
					gerarFaturaBean.setFatura(false);
					gerarFaturaBean.getFaturafranquias().setFatura(false); 
					faturafaturafraquias.getFatura().setSaldolancamentos(faturafaturafraquias.getFatura().getSaldolancamentos()-gerarFaturaBean.getTotal());
					faturafaturafraquias.getFatura().setValorpagar(faturafaturafraquias.getFatura().getSaldofinalanterior()+faturafaturafraquias.getFatura().getSaldolancamentos());
					faturafaturafraquias.getFatura().setSaldodevedor(faturafaturafraquias.getFatura().getValorpagar());
					FaturaFacade faturaFacade = new FaturaFacade();
					faturafaturafraquias.setFatura(faturaFacade.salvar(faturafaturafraquias.getFatura())); 
					int idfatura=faturafaturafraquias.getFatura().getIdfatura();
					if(fatura.getIdfatura()==idfatura){
						fatura = faturafaturafraquias.getFatura();
					}
					FaturaFranquiasFacade faturaFranquiasFacade = new FaturaFranquiasFacade();
					try {
						gerarFaturaBean
								.setFaturafranquias(faturaFranquiasFacade.salvar(gerarFaturaBean.getFaturafranquias()));
					} catch (SQLException e) {
						  
					}
					Mensagem.lancarMensagemInfo("Excluído com sucesso!", "");
				}
			}else{
				FaturaFaturaAjusteFacade faturaFaturaAjusteFacade = new FaturaFaturaAjusteFacade();
				FaturaAjusteFacade faturaAjusteFacade = new FaturaAjusteFacade();
				String sql = "select f From Faturafaturaajuste f where f.fatura.idfatura="+fatura.getIdfatura()
					+ " and f.faturaajustes.idFaturaajustes="+gerarFaturaBean.getFaturaajustes().getIdFaturaajustes();
				Faturafaturaajuste faturafaturaajuste = faturaFaturaAjusteFacade.getFatura(sql);
				if(faturafaturaajuste!=null){
					faturaFaturaAjusteFacade.excluir(faturafaturaajuste.getIdFaturafaturaajuste());
					faturafaturaajuste.getFatura().setSaldolancamentos(faturafaturaajuste.getFatura().getSaldolancamentos()-gerarFaturaBean.getTotal());
					faturafaturaajuste.getFatura().setValorpagar(faturafaturaajuste.getFatura().getSaldofinalanterior()+faturafaturaajuste.getFatura().getSaldolancamentos());
					faturafaturaajuste.getFatura().setSaldodevedor(faturafaturaajuste.getFatura().getValorpagar());
					FaturaFacade faturaFacade = new FaturaFacade();
					faturafaturaajuste.setFatura(faturaFacade.salvar(faturafaturaajuste.getFatura())); 
					int idfatura=faturafaturaajuste.getFatura().getIdfatura();
					if(fatura.getIdfatura()==idfatura){
						fatura = faturafaturaajuste.getFatura();
					}
				}     
				faturaAjusteFacade.excluir(gerarFaturaBean.getFaturaajustes().getIdFaturaajustes());
				listaFatura.remove(gerarFaturaBean);
				Mensagem.lancarMensagemInfo("Excluído com sucesso!", "");
			}
		}
	}

	public String adicionarFaturaAjuste() {
		if (fatura != null && fatura.getIdfatura() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("fatura", fatura);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 530);
			RequestContext.getCurrentInstance().openDialog("cadFaturaAjuste", options, null);
		} else {
			FacesMessage msg = new FacesMessage("Nenhuma fatura iníciada! ", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "";
	}

	public void retornoDialogFaturaAjuste(SelectEvent event) {
		if (event != null) {
			Faturafaturaajuste faturafaturaajuste = (Faturafaturaajuste) event.getObject();
			if (faturafaturaajuste != null && faturafaturaajuste.getIdFaturafaturaajuste() != null) {
				int idfatura = fatura.getIdfatura();
				if(faturafaturaajuste.getFatura().getIdfatura()==idfatura){
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
					faturaBean.setAno(faturafaturaajuste.getFaturaajustes().getAno());
					faturaBean.setMes(faturafaturaajuste.getFaturaajustes().getMes());
					faturaBean.setTaxatm(0); 
					faturaBean.setValorcomissionavel(0);  
					faturaBean.setValortotal(faturafaturaajuste.getFaturaajustes().getValor()); 
					faturaBean.setTotal(faturaBean.getValortotal());
					faturafaturaajuste.getFatura().setSaldolancamentos(faturafaturaajuste.getFatura().getSaldolancamentos()+faturaBean.getTotal());
					faturafaturaajuste.getFatura().setValorpagar(faturafaturaajuste.getFatura().getSaldofinalanterior()+faturafaturaajuste.getFatura().getSaldolancamentos());
					faturafaturaajuste.getFatura().setSaldodevedor(faturafaturaajuste.getFatura().getValorpagar());
					FaturaFacade faturaFacade = new FaturaFacade();
					faturafaturaajuste.setFatura(faturaFacade.salvar(faturafaturaajuste.getFatura()));   
					if(faturafaturaajuste.getFatura().getIdfatura()==idfatura){
						fatura = faturafaturaajuste.getFatura();
					}
					unidadenegocio = faturafaturaajuste.getFatura().getUnidadenegocio(); 
					listaFatura.add(faturaBean);
				}
			}
		}
	}

	public void gerarListas() {
		listaFatura = new ArrayList<>();
		if (unidadenegocio != null && dataInicial != null && dataFinal != null && mes > 0 && ano > 0 && mes<=12) {
			FaturaFacade faturaFacade = new FaturaFacade();
			fatura = faturaFacade.getFatura("select f from Fatura f where f.ano=" + ano + " and f.mes=" + mes
					+" and f.unidadenegocio.idunidadeNegocio="+unidadenegocio.getIdunidadeNegocio());
			if (fatura == null || fatura.getIdfatura() == null) {  
				fatura = new Fatura();
				fatura.setAno(ano);
				fatura.setMes(mes);   
				fatura.setUnidadenegocio(unidadenegocio);
				fatura.setUsuario(usuarioLogadoMB.getUsuario()); 
				fatura = faturaFacade.salvar(fatura);
			} 
			if(!fatura.isGerartaxapublicidade() && unidadenegocio.getFundomarketing()>0){
				Fatura faturataxapubli = this.fatura;
				if(mes != unidadenegocio.getMespagamento()){
					faturataxapubli = faturaFacade.getFatura("select f from Fatura f where f.ano=" + fatura.getAno()
							+ " and f.mes=" + unidadenegocio.getMespagamento());
				}
				if(faturataxapubli!=null && !faturataxapubli.isGerartaxapublicidade()){
					Faturaajustes faturaajustes = new Faturaajustes();
					faturaajustes.setAno(fatura.getAno());
					faturaajustes.setCreditodebito("D");
					faturaajustes.setDatalancamento(new Date());
					faturaajustes.setDescricao("Taxa Publicidade");
					faturaajustes.setMes(unidadenegocio.getMespagamento());
					faturaajustes.setUnidadenegocio(unidadenegocio);
					faturaajustes.setUsuario(usuarioLogadoMB.getUsuario());
					faturaajustes.setValor(unidadenegocio.getFundomarketing() * (-1));
					FaturaAjusteFacade faturaAjusteFacade = new FaturaAjusteFacade();
					faturaajustes = faturaAjusteFacade.salvar(faturaajustes);
					Faturafaturaajuste faturafaturaajuste = new Faturafaturaajuste();
					faturafaturaajuste.setFatura(faturataxapubli);
					faturafaturaajuste.setFaturaajustes(faturaajustes);
					FaturaFaturaAjusteFacade faturaFaturaAjusteFacade = new FaturaFaturaAjusteFacade();
					faturaFaturaAjusteFacade.salvar(faturafaturaajuste);
					faturataxapubli.setGerartaxapublicidade(true);
					faturataxapubli.setSaldolancamentos(faturataxapubli.getSaldolancamentos()+faturaajustes.getValor());
					faturataxapubli.setValorpagar(faturataxapubli.getSaldofinalanterior()+faturataxapubli.getSaldolancamentos());
					faturataxapubli.setSaldodevedor(faturataxapubli.getValorpagar());
					faturaFacade.salvar(faturataxapubli);
				}
			}
			int sMes = mes;
			int sAno = ano;
			if (sMes == 1) {
				sMes = 12;
				sAno = sAno - 1;
			} else {
				sMes = sMes - 1;
			}
			Fatura faturaanterior = faturaFacade.getFatura("select f from Fatura f where f.ano=" + sAno + " and f.mes=" + sMes
					+" and f.unidadenegocio.idunidadeNegocio="+unidadenegocio.getIdunidadeNegocio());
			if(faturaanterior!=null && faturaanterior.getIdfatura()!=null){
				fatura.setSaldodebito(faturaanterior.getSaldodevedor());
			}else fatura.setSaldodebito(0.0f);   
			gerarListaFaturaAjuste();
			gerarListaFaturaFranquias();
			fatura.setSaldofinalanterior(fatura.getSaldodebito()+fatura.getValorpago());
			fatura.setValorpagar(fatura.getSaldofinalanterior()+fatura.getSaldolancamentos());
			fatura.setSaldodevedor(fatura.getValorpagar());
			fatura = faturaFacade.salvar(fatura);  
		} else {
			Mensagem.lancarMensagemErro("Atenção!", "Campos Obrigatorios não preenchido");
		}
	}

	public void gerarListaFaturaFranquias() {
		FaturaFranquiasFacade faturaFranquiasFacade = new FaturaFranquiasFacade();
		List<Faturafranquias> lista = null;
		String sql = "Select f from Faturafranquias f where f.vendascomissao.vendas.vendasMatriz<>'N'"
				+ " and f.apagarfatura=FALSE"
				+ " and (f.vendascomissao.vendas.situacao='FINALIZADA' OR f.vendascomissao.vendas.situacao='ANDAMENTO'"
				+ " or (f.vendascomissao.vendas.situacao='CANCELADA' and f.fatura=TRUE))"
				+ " and f.vendascomissao.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio()
				+ " and ((f.vendascomissao.vendas.dataVenda>='"+ Formatacao.ConvercaoDataSql(dataInicial)+ "' and f.vendascomissao.vendas.dataVenda<='"+ Formatacao.ConvercaoDataSql(dataFinal)+"')"
				+ " or (f.ano=" + ano + " and f.mes="+mes+ ")"
				+ " or (f.fatura=false and f.vendascomissao.vendas.dataVenda<='"+ Formatacao.ConvercaoDataSql(dataFinal)
				+ "')) order by f.vendascomissao.vendas.dataVenda, f.vendascomissao.vendas.cliente.nome, f.vendascomissao.vendas.idvendas";
		try {
			lista = faturaFranquiasFacade.listar(sql);  
		} catch (SQLException ex) {
			Logger.getLogger(FaturaFranquiaMB.class.getName()).log(Level.SEVERE, null, ex);
		} 
		if (lista != null && lista.size() > 0) {
			GerarFaturaBean faturaBean;
			for (int i = 0; i < lista.size(); i++) {
				faturaBean = new GerarFaturaBean();
				faturaBean.setCliente(lista.get(i).getVendascomissao().getVendas().getCliente().getNome());
				faturaBean.setComissao(lista.get(i).getVendascomissao().getComissaofranquiabruta());
				faturaBean.setDataincio(lista.get(i).getVendascomissao().getDatainicioprograma());
				faturaBean.setDatavenda(lista.get(i).getVendascomissao().getVendas().getDataVenda());
				faturaBean.setDesagio(lista.get(i).getVendascomissao().getCustofinanceirofranquia());
				faturaBean.setDescontoloja(lista.get(i).getVendascomissao().getDescontoloja()); 
				faturaBean.setFaturafranquias(lista.get(i));
				faturaBean.setFornecedor(
						lista.get(i).getVendascomissao().getVendas().getFornecedorcidade().getFornecedor().getNome());
				faturaBean.setPercentualcomissao(lista.get(i).getPercentualcomissao());
				faturaBean.setPrograma(lista.get(i).getVendascomissao().getVendas().getProdutos().getDescricao());
				faturaBean.setRecebidomatiz(lista.get(i).getPagomatriz());
				if(lista.get(i).getVendascomissao().getVendas().getVendaspacote()!=null) {
					faturaBean.setTaxatm(0);
					faturaBean.setDescontomatriz(0); 
				}else {
					faturaBean.setTaxatm(lista.get(i).getVendascomissao().getTaxatm());
					faturaBean.setDescontomatriz(lista.get(i).getVendascomissao().getDescontotm());
				}
				faturaBean.setTotal(lista.get(i).getLiquidopagar());
				if(lista.get(i).getVendascomissao().getVendas().getVendaspacote()!=null
						&& lista.get(i).getVendascomissao().getVendas().getVendaspacote().getCursospacote().getValoravista()!=null
						&& lista.get(i).getVendascomissao().getVendas().getVendaspacote().getCursospacote().getValoravista()>0) {
					faturaBean.setValorcomissionavel(lista.get(i).getVendascomissao().getVendas().getVendaspacote().getCursospacote().getValoravista());
				}else {
					faturaBean.setValorcomissionavel(lista.get(i).getVendascomissao().getValorcomissionavel());
				}
				faturaBean.setValortotal(lista.get(i).getVendascomissao().getVendas().getValor());
				faturaBean.setFatura(lista.get(i).isFatura());
				faturaBean.setOrdenacao(2);
				if(lista.get(i).getAno()!=null && lista.get(i).getMes()!=null){
					faturaBean.setAno(lista.get(i).getAno());
					faturaBean.setMes(lista.get(i).getMes());
				} 
				listaFatura.add(faturaBean);
			}
		}
	}
   
	public void gerarListaFaturaAjuste() {
		FaturaFaturaAjusteFacade faturaFaturaAjusteFacade = new FaturaFaturaAjusteFacade();
		List<Faturafaturaajuste> listaFaturaFaturaAjuste = null;
		String sql = "Select f from Faturafaturaajuste f where f.fatura.unidadenegocio.idunidadeNegocio=" + fatura.getUnidadenegocio().getIdunidadeNegocio() + "  and (f.fatura.idfatura="+fatura.getIdfatura() 
				+ " or (f.faturaajustes.mes="+mes+" and f.faturaajustes.ano="+ano+"))"
				+ " order by f.faturaajustes.datalancamento";  
		listaFaturaFaturaAjuste = faturaFaturaAjusteFacade.listar(sql); 
		if (listaFaturaFaturaAjuste != null && listaFaturaFaturaAjuste.size() > 0) {
			GerarFaturaBean faturaBean; 
			for (int i = 0; i < listaFaturaFaturaAjuste.size(); i++) {
				faturaBean = new GerarFaturaBean();
				faturaBean.setCliente(listaFaturaFaturaAjuste.get(i).getFaturaajustes().getItem());
				faturaBean.setComissao(0);
				faturaBean.setDataincio(null);
				faturaBean.setDatavenda(listaFaturaFaturaAjuste.get(i).getFaturaajustes().getDatalancamento());
				faturaBean.setDesagio(0);
				faturaBean.setDescontoloja(0);
				faturaBean.setDescontomatriz(0);
				faturaBean.setFatura(true);
				faturaBean.setFaturaajustes(listaFaturaFaturaAjuste.get(i).getFaturaajustes());
				faturaBean.setFornecedor("");
				faturaBean.setOrdenacao(1);
				faturaBean.setPercentualcomissao(0);
				faturaBean.setPrograma(listaFaturaFaturaAjuste.get(i).getFaturaajustes().getDescricao());
				faturaBean.setRecebidomatiz(0);
				faturaBean.setSelecionado(false);
				faturaBean.setTaxatm(0);
				faturaBean.setValorcomissionavel(0); 
				faturaBean.setAno(listaFaturaFaturaAjuste.get(i).getFaturaajustes().getAno());
				faturaBean.setMes(listaFaturaFaturaAjuste.get(i).getFaturaajustes().getMes()); 
				faturaBean.setValortotal(listaFaturaFaturaAjuste.get(i).getFaturaajustes().getValor()); 
				faturaBean.setTotal(faturaBean.getValortotal());
				unidadenegocio = listaFaturaFaturaAjuste.get(i).getFatura().getUnidadenegocio(); 
				listaFatura.add(faturaBean);     
			}
		}   
	}
	

	public String retornoCorLetra(float valor){
		if(valor>=0 && valor<0.01){
			return "black";
		}else if(valor<0){
			return "red";
		}else return "blue";
	}
	
	public String retornarAnteriorMesAno(int mes, int ano){
		if(mes>0 && ano>0){
			if (mes == 1) {
				mes = 12;
				ano = ano - 1;
			} else {
				mes = mes - 1;
			}
			return Formatacao.nomeMes(mes)+"/"+ano; 
		}return "";
	}
	
	public String lancarContasPagar(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("unidade",fatura.getUnidadenegocio());
        session.setAttribute("valorEntrada", fatura.getSaldodevedor());
        String mes;
        if (fatura.getMes()<=9){
        	mes = "0" + String.valueOf(fatura.getMes());
        }else mes = String.valueOf(fatura.getMes());
        String competencia = mes + "/" + String.valueOf(fatura.getAno());
        session.setAttribute("competencia", competencia);
        session.setAttribute("fatura", fatura);
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("contentWidth",570);
        RequestContext.getCurrentInstance().openDialog("faturaContasPagar", options, null);
        return "";
	}
	
	public void retornoDialogNovo(SelectEvent event) {
		if (event != null) {
			Contaspagar contaspagar = (Contaspagar) event.getObject();
			if (contaspagar != null && contaspagar.getValorentrada() != null) {
				if (contaspagar.getValorentrada()>0){
					fatura.setValorpago(fatura.getValorpago() + contaspagar.getValorentrada());
				}else if (contaspagar.getValorsaida()>0){
					fatura.setValorpago(fatura.getValorpago() + (contaspagar.getValorsaida()*-1));
				}
			}
			fatura.setSaldofinalanterior(fatura.getSaldodebito() + fatura.getValorpago());
			fatura.setValorpagar(fatura.getSaldofinalanterior()+fatura.getSaldolancamentos());
			fatura.setSaldodevedor(fatura.getValorpagar());
			FaturaFacade faturaFacade = new FaturaFacade();
			fatura = faturaFacade.salvar(fatura);
		}
	}
	 
	
	public void descartarFaturaFranquia(GerarFaturaBean gerarFaturaBean){
		if(!gerarFaturaBean.isFatura() || gerarFaturaBean.getFaturaajustes()!=null){
			gerarFaturaBean.getFaturafranquias().setApagarfatura(true);
			FaturaFranquiasFacade faturaFranquiasFacade = new FaturaFranquiasFacade();
			try {
				faturaFranquiasFacade.salvar(gerarFaturaBean.getFaturafranquias());
			} catch (SQLException e) {
				  
			}
			listaFatura.remove(gerarFaturaBean);
			Mensagem.lancarMensagemInfo("Fatura descartada com sucesso!", "Função para ativar fatura novamente, "
					+ "se localiza no modulo Back Office.");
		}else Mensagem.lancarMensagemErro("Atenção!", "Fatura já encontrasse gerada.");
	}
	
	public void enviarEmail(){
		if(fatura!=null && fatura.getIdfatura()!=null){
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 500); 
			session.setAttribute("fatura", fatura); 
			RequestContext.getCurrentInstance().openDialog("enviarEmail", options, null);
		}else{
			Mensagem.lancarMensagemErro("Nenhuma fatura iníciada!", "");
		}
	}
	
	public void visualizarDadosBanco(){
		if(fatura!=null && fatura.getIdfatura()!=null){
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 300); 
			session.setAttribute("fatura", fatura); 
			session.setAttribute("alteracao", true); 
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
			options.put("contentWidth", 500); 
			session.setAttribute("fatura", fatura); 
			session.setAttribute("alteracao", false); 
			RequestContext.getCurrentInstance().openDialog("faturaComprovante", options, null);
		}else{
			Mensagem.lancarMensagemErro("Nenhuma fatura iníciada!", "");
		}
	}
	
	
	public void retornarPeriodo(){
		boolean mesok = false;
		boolean anook = false;
		int mesData = 0;
		int anoData = 0;
		if (mes >= 1 && mes <=12) {
			mesok = true;
		}else{
			mes = 0;
			Mensagem.lancarMensagemInfo("Número de mês invalido", "");
		}
		if (ano >=1) {
			anook = true;
		}
		if (mesok && anook) {
			 if (mes == 1) {
				mesData = 12;
				anoData = ano - 1;
			}else{
				if (mes < 10) {
					mesData = 0;
				}
				mesData = mesData + (mes - 1);
				anoData = ano;
			}
			String dataF = Formatacao.retornaDataFinalBrasil(mesData);
			this.dataInicial = Formatacao.ConvercaoStringData(01 + "/" + mesData + "/" + anoData);
			this.dataFinal = Formatacao.ConvercaoStringData(dataF + "/" + anoData);
		}   
	}
}
