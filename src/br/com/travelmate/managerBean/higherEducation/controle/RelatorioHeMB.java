package br.com.travelmate.managerBean.higherEducation.controle;

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

import br.com.travelmate.dao.HeControleDao;
import br.com.travelmate.dao.PaisDao;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Hecontrole;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class RelatorioHeMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private PaisDao paisDao;
	@Inject
	private HeControleDao heControleDao;
	private String acomodacao;
	private Unidadenegocio unidadenegocio;
	private Usuario usuario;
	private String pathway;
	private String tipovenda;
	private List<Usuario> listaConsultor;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Date datainivenda;
	private Date datafinalvenda;
	private Date datainiembarque;
	private Date datafinalembarque;
	private Date datainiaplicacao;
	private Date datafinalaplicacao;
	private Date datainiprevisao;
	private Date datafinalprevisao;
	private Date datainipagamento;
	private Date datafinalpagamento;
	private Date datainiprograma;
	private Date datafinalprograma;
	private String sql;
	private Pais pais;
	private List<Pais> listaPais;
	private Cidade cidade;
	private Fornecedorcidade fornecedorcidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private String ordenar;
	private List<Hecontrole> listaHeControle;
	private boolean selecionartodos = true;
	private boolean idvenda = true;
	private boolean unidade = true;
	private boolean consultor = true;
	private boolean tipo = true;
	private boolean datavenda = true;
	private boolean paisVisu = true;
	private boolean cidadeVisu = true;
	private boolean parceiro = true;
	private boolean inicioprograma = true;
	private boolean acomodacaoVisu = true;
	private boolean dataaplicacao = true;
	private boolean previsaopagamento = true;
	private boolean datapagamento = true;
	private boolean pathwayVisu = true;
	private boolean situacaovenda = true;
	private boolean situacaogerencia = true;
	private boolean tipoinvoice = true;
	private boolean valornet = true;
	private boolean credito = true;
	private boolean nomecliente = true;
	private boolean dataembarque = true;
	
	
	
	
	
	@PostConstruct
	public void init() {
		listaPais = paisDao.listar();
		gerarListaFornecedor();
		listaUnidadeNegocio = GerarListas.listarUnidade();
	}


	public String getAcomodacao() {
		return acomodacao;
	}



	public void setAcomodacao(String acomodacao) {
		this.acomodacao = acomodacao;
	}


	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}



	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public String getPathway() {
		return pathway;
	}


	public void setPathway(String pathway) {
		this.pathway = pathway;
	}


	public String getTipovenda() {
		return tipovenda;
	}


	public void setTipovenda(String tipovenda) {
		this.tipovenda = tipovenda;
	}


	public List<Usuario> getListaConsultor() {
		return listaConsultor;
	}



	public void setListaConsultor(List<Usuario> listaConsultor) {
		this.listaConsultor = listaConsultor;
	}
	

	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}


	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
	}



	public Date getDatainivenda() {
		return datainivenda;
	}


	public void setDatainivenda(Date datainivenda) {
		this.datainivenda = datainivenda;
	}

	public Date getDatafinalvenda() {
		return datafinalvenda;
	}


	public void setDatafinalvenda(Date datafinalvenda) {
		this.datafinalvenda = datafinalvenda;
	}


	public Date getDatainiembarque() {
		return datainiembarque;
	}
	
	
	public void setDatainiembarque(Date datainiembarque) {
		this.datainiembarque = datainiembarque;
	}


	public Date getDatafinalembarque() {
		return datafinalembarque;
	}


	public void setDatafinalembarque(Date datafinalembarque) {
		this.datafinalembarque = datafinalembarque;
	}


	public Date getDatainiaplicacao() {
		return datainiaplicacao;
	}


	public void setDatainiaplicacao(Date datainiaplicacao) {
		this.datainiaplicacao = datainiaplicacao;
	}

	public Date getDatafinalaplicacao() {
		return datafinalaplicacao;
	}

	public void setDatafinalaplicacao(Date datafinalaplicacao) {
		this.datafinalaplicacao = datafinalaplicacao;
	}


	public String getSql() {
		return sql;
	}


	public void setSql(String sql) {
		this.sql = sql;
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
	

	public Date getDatainiprevisao() {
		return datainiprevisao;
	}


	public void setDatainiprevisao(Date datainiprevisao) {
		this.datainiprevisao = datainiprevisao;
	}


	public Date getDatafinalprevisao() {
		return datafinalprevisao;
	}


	public void setDatafinalprevisao(Date datafinalprevisao) {
		this.datafinalprevisao = datafinalprevisao;
	}


	public Date getDatainipagamento() {
		return datainipagamento;
	}


	public void setDatainipagamento(Date datainipagamento) {
		this.datainipagamento = datainipagamento;
	}


	public Date getDatafinalpagamento() {
		return datafinalpagamento;
	}


	public void setDatafinalpagamento(Date datafinalpagamento) {
		this.datafinalpagamento = datafinalpagamento;
	}


	public Date getDatainiprograma() {
		return datainiprograma;
	}


	public void setDatainiprograma(Date datainiprograma) {
		this.datainiprograma = datainiprograma;
	}


	public Date getDatafinalprograma() {
		return datafinalprograma;
	}


	public void setDatafinalprograma(Date datafinalprograma) {
		this.datafinalprograma = datafinalprograma;
	}

	
	public String getOrdenar() {
		return ordenar;
	}


	public void setOrdenar(String ordenar) {
		this.ordenar = ordenar;
	}



	public List<Hecontrole> getListaHeControle() {
		return listaHeControle;
	}



	public void setListaHeControle(List<Hecontrole> listaHeControle) {
		this.listaHeControle = listaHeControle;
	}



	public boolean isSelecionartodos() {
		return selecionartodos;
	}


	public void setSelecionartodos(boolean selecionartodos) {
		this.selecionartodos = selecionartodos;
	}

	public boolean isIdvenda() {
		return idvenda;
	}



	public void setIdvenda(boolean idvenda) {
		this.idvenda = idvenda;
	}



	public boolean isUnidade() {
		return unidade;
	}



	public void setUnidade(boolean unidade) {
		this.unidade = unidade;
	}


	public boolean isConsultor() {
		return consultor;
	}


	public void setConsultor(boolean consultor) {
		this.consultor = consultor;
	}


	public boolean isTipo() {
		return tipo;
	}


	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}


	public boolean isDatavenda() {
		return datavenda;
	}


	public void setDatavenda(boolean datavenda) {
		this.datavenda = datavenda;
	}


	public boolean isPaisVisu() {
		return paisVisu;
	}


	public void setPaisVisu(boolean paisVisu) {
		this.paisVisu = paisVisu;
	}

	public boolean isCidadeVisu() {
		return cidadeVisu;
	}


	public void setCidadeVisu(boolean cidadeVisu) {
		this.cidadeVisu = cidadeVisu;
	}


	public boolean isParceiro() {
		return parceiro;
	}

	public void setParceiro(boolean parceiro) {
		this.parceiro = parceiro;
	}


	public boolean isInicioprograma() {
		return inicioprograma;
	}

	public void setInicioprograma(boolean inicioprograma) {
		this.inicioprograma = inicioprograma;
	}


	public boolean isAcomodacaoVisu() {
		return acomodacaoVisu;
	}


	public void setAcomodacaoVisu(boolean acomodacaoVisu) {
		this.acomodacaoVisu = acomodacaoVisu;
	}

	public boolean isDataaplicacao() {
		return dataaplicacao;
	}


	public void setDataaplicacao(boolean dataaplicacao) {
		this.dataaplicacao = dataaplicacao;
	}


	public boolean isPrevisaopagamento() {
		return previsaopagamento;
	}

	public void setPrevisaopagamento(boolean previsaopagamento) {
		this.previsaopagamento = previsaopagamento;
	}


	public boolean isDatapagamento() {
		return datapagamento;
	}

	public void setDatapagamento(boolean datapagamento) {
		this.datapagamento = datapagamento;
	}


	public boolean isPathwayVisu() {
		return pathwayVisu;
	}


	public void setPathwayVisu(boolean pathwayVisu) {
		this.pathwayVisu = pathwayVisu;
	}


	public boolean isSituacaovenda() {
		return situacaovenda;
	}


	public void setSituacaovenda(boolean situacaovenda) {
		this.situacaovenda = situacaovenda;
	}


	public boolean isSituacaogerencia() {
		return situacaogerencia;
	}

	public void setSituacaogerencia(boolean situacaogerencia) {
		this.situacaogerencia = situacaogerencia;
	}

	public boolean isTipoinvoice() {
		return tipoinvoice;
	}


	public void setTipoinvoice(boolean tipoinvoice) {
		this.tipoinvoice = tipoinvoice;
	}


	public boolean isValornet() {
		return valornet;
	}


	public void setValornet(boolean valornet) {
		this.valornet = valornet;
	}


	public boolean isCredito() {
		return credito;
	}


	public void setCredito(boolean credito) {
		this.credito = credito;
	}


	
	public boolean isNomecliente() {
		return nomecliente;
	}


	public void setNomecliente(boolean nomecliente) {
		this.nomecliente = nomecliente;
	}


	public boolean isDataembarque() {
		return dataembarque;
	}


	public void setDataembarque(boolean dataembarque) {
		this.dataembarque = dataembarque;
	}


	public void gerarListaFornecedor() {
		String sql = "select distinct f from Fornecedorcidade f where f.produtos.idprodutos=22 ";
		if (cidade != null && cidade.getIdcidade() != null) {
			sql = sql + " and f.cidade.idcidade=" + cidade.getIdcidade();
		}
		sql = sql + " and f.ativo=1 group by f.fornecedor order by f.fornecedor.nome";
		FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
		listaFornecedorCidade = fornecedorCidadeFacade.listar(sql);
	}
	
	
	public void gerarListaConsultor() {
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			listaConsultor = GerarListas.listarUsuarios(
					"Select u FROM Usuario u where u.situacao='Ativo'" + " and u.unidadenegocio.idunidadeNegocio="
							+ unidadenegocio.getIdunidadeNegocio() + " order by u.nome");
		}else {
			listaConsultor = GerarListas.listarUsuarios(
					"Select u FROM Usuario u where u.situacao='Ativo' order by u.nome");
		}
		usuario = null;
	}
	
	
	
	public void pesquisar() {
		String sql = "SELECT h FROM Hecontrole h WHERE h.he.vendas.cliente.nome like '%%' ";
		
		if (pais != null && pais.getIdpais() != null) {
			sql = sql + " AND h.he.vendas.fornecedorcidade.cidade.pais.idpais=" + pais.getIdpais();
		}
		
		if (cidade != null && cidade.getIdcidade() != null) {
			sql = sql + " AND h.he.vendas.fornecedorcidade.cidade.idcidade=" + cidade.getIdcidade();
		}
		
		if (fornecedorcidade != null && fornecedorcidade.getIdfornecedorcidade() != null) {
			sql = sql + " AND h.he.vendas.fornecedorcidade.fornecedor.idfornecedor=" + fornecedorcidade.getFornecedor().getIdfornecedor();
		}
		
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			sql = sql + " AND h.he.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		
		if (usuario != null && usuario.getIdusuario() != null) {
			sql = sql + " AND h.he.vendas.usuario.idusuario=" + usuario.getIdusuario();
		}
		
		if (tipovenda != null && tipovenda.length() > 0 && !tipovenda.equalsIgnoreCase("Selecione")) {
			if (tipovenda.equalsIgnoreCase("Formulário")) {
				sql = sql + " AND h.he.fichafinal=false ";
			}else {
				sql = sql + " AND h.he.fichafinal=true ";
			}
		}

		if (datainivenda != null && datafinalvenda != null) {
			sql = sql+ " AND h.he.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(datainivenda) + "' AND h.he.vendas.dataVenda<='" 
					+ Formatacao.ConvercaoDataSql(datafinalvenda) + "'"; 
		}
		

		if (datainiaplicacao != null && datafinalaplicacao != null) {
			sql = sql+ " AND h.dataaplicacaoenviada>='" + Formatacao.ConvercaoDataSql(datainiaplicacao) + "' AND h.dataaplicacaoenviada<='" 
					+ Formatacao.ConvercaoDataSql(datafinalaplicacao) + "'"; 
		}
		

		if (datainiembarque != null && datafinalembarque != null) {
			sql = sql+ " AND h.dataembarque>='" + Formatacao.ConvercaoDataSql(datainiembarque) + "' AND h.dataembarque<='" 
					+ Formatacao.ConvercaoDataSql(datafinalembarque) + "'"; 
		}
		
		
		if (datainiprevisao != null && datafinalprevisao != null) {
			sql = sql+ " AND h.he.vendas.invoice.dataPrevistaPagamento>='" + Formatacao.ConvercaoDataSql(datainiprevisao) + "' AND h.he.vendas.invoice.dataPrevistaPagamento<='" 
					+ Formatacao.ConvercaoDataSql(datafinalprevisao) + "'"; 
		}
		

		if (datainipagamento != null && datafinalpagamento != null) {
			sql = sql+ " AND h.he.vendas.invoice.dataPagamentoInvoice>='" + Formatacao.ConvercaoDataSql(datainipagamento) + "' AND h.he.vendas.invoice.dataPagamentoInvoice<='" 
					+ Formatacao.ConvercaoDataSql(datafinalpagamento) + "'"; 
		}
		
		if (acomodacao != null && !acomodacao.equalsIgnoreCase("Selecione")) {
			if (acomodacao.equalsIgnoreCase("Sim")) {
				sql = sql + " AND h.he.tipoAcomodacao<>'Sem acomodação'";
			}else if(acomodacao.equalsIgnoreCase("Não")) {
				sql = sql + " AND h.he.tipoAcomodacao='Sem acomodação'";
			}
		}
		
		//DEIXAR SEMPRE ESTE IF POR ÚLTIMO ANTES DE CONSULTAR NA BASE
		if (ordenar != null && ordenar.length() > 0) {
			sql = sql + " order by " + ordenar;
		}
		
		
		listaHeControle = heControleDao.listar(sql);
		
		if (listaHeControle == null) {
			listaHeControle = new ArrayList<Hecontrole>();
		}
		List<Hecontrole> listaControle = new ArrayList<Hecontrole>();
		boolean inicioPrograma = false;
		boolean path = false;
		for (int i = 0; i < listaHeControle.size(); i++) {
			if (listaHeControle.get(i).getHe().getTipoAcomodacao().equalsIgnoreCase("Sem acomodação")) {
				listaHeControle.get(i).setAcomodacao("Não");
			}else {
				listaHeControle.get(i).setAcomodacao("Sim");
			}
			if (listaHeControle.get(i).getHe().isFichafinal()) {
				listaHeControle.get(i).setTipo("Final");
			} else {
				listaHeControle.get(i).setTipo("Formulário");
			}
			if (listaHeControle.get(i).getHe().getListaHeParceirosList() != null && listaHeControle.get(i).getHe().getListaHeParceirosList().size() > 0) {
				listaHeControle.get(i)
					.setParceiro(listaHeControle.get(i).getHe().getListaHeParceirosList().get(0)
						.getFornecedorcidade().getFornecedor().getNome() + " - "
						+ listaHeControle.get(i).getHe().getListaHeParceirosList().size());
				listaHeControle.get(i).setPais(listaHeControle.get(i).getHe().getListaHeParceirosList().get(0).getFornecedorcidade().getCidade().getPais().getNome());
				listaHeControle.get(i).setCidade(listaHeControle.get(i).getHe().getListaHeParceirosList().get(0).getFornecedorcidade().getCidade().getNome());
				listaHeControle.get(i).setInicioPrograma(listaHeControle.get(i).getHe().getListaHeParceirosList().get(0).getDatainicio());
				if (listaHeControle.get(i).getHe().getListaHeParceirosList().get(0).isPathway()) {
					listaHeControle.get(i).setPathway("Sim");
				}else {
					listaHeControle.get(i).setPathway("Não");
				}
				if (datainiprograma != null && datafinalprograma != null) {
					inicioPrograma = true;
					if ((listaHeControle.get(i).getHe().getListaHeParceirosList().get(0).getDatainicio().after(datainiprograma) && 
							listaHeControle.get(i).getHe().getListaHeParceirosList().get(0).getDatainicio().before(datafinalprograma)) ||
							listaHeControle.get(i).getHe().getListaHeParceirosList().get(0).getDatainicio().equals(datainiprograma) ||
									listaHeControle.get(i).getHe().getListaHeParceirosList().get(0).getDatainicio().equals(datafinalprograma)) {
						listaControle.add(listaHeControle.get(i));
					}
				}else if(pathway != null && !pathway.equalsIgnoreCase("Selecione")) {
					if (pathway.equalsIgnoreCase("Sim")) {
						path = true;
						if (listaHeControle.get(i).getPathway().equalsIgnoreCase("Sim")) {
							listaControle.add(listaHeControle.get(i));
						}
					}else if(pathway.equalsIgnoreCase("Não")) {
						path = true;
						if (listaHeControle.get(i).getPathway().equalsIgnoreCase("Não")) {
							listaControle.add(listaHeControle.get(i));
						}
					}
				}
			}else {
				listaHeControle.get(i).setPathway("Não");
				if (pathway.equalsIgnoreCase("Sim")) {
					path = true;
					if (listaHeControle.get(i).getPathway().equalsIgnoreCase("Sim")) {
						listaControle.add(listaHeControle.get(i));
					}
				}else if(pathway.equalsIgnoreCase("Não")) {
					path = true;
					if (listaHeControle.get(i).getPathway().equalsIgnoreCase("Não")) {
						listaControle.add(listaHeControle.get(i));
					}
				}
			}
		}
		if (inicioPrograma || path) {
			listaHeControle = listaControle;
		}
	}   
	
	public void limpar() {
		datainiaplicacao = null;
		datafinalaplicacao = null;
		datainiembarque = null;
		datafinalembarque = null;
		datainipagamento = null;
		datafinalpagamento = null;
		datainiprevisao = null;
		datafinalprevisao = null;
		datainiprograma = null;
		datafinalprograma = null;
		datainivenda = null;
		datafinalvenda = null;
		unidadenegocio = null;
		usuario = null;
		pais = null;
		cidade = null;
		fornecedorcidade = null;
		acomodacao = "";
		pathway = "";
		tipovenda = "";
		ordenar = "";
		acomodacao = "";
		listaHeControle = new ArrayList<Hecontrole>();
		
	}
	
	
	public void selecionartodos() {
		if (!selecionartodos) {
			selecionartodos = false;
			idvenda = false;
			unidade = false;
			consultor = false;
			tipo = false;
			datavenda = false;
			paisVisu = false;
			cidadeVisu = false;
			parceiro = false;
			inicioprograma = false;
			acomodacaoVisu = false;
			dataaplicacao = false;
			previsaopagamento = false;
			datapagamento = false;
			pathwayVisu = false;
			situacaovenda = false;
			situacaogerencia = false;
			tipoinvoice = false;
			valornet = false;
			credito = false;
			nomecliente = false;
			dataembarque = false;
		}else {
			selecionartodos = true;
			idvenda = true;
			unidade = true;
			consultor = true;
			tipo = true;
			datavenda = true;
			paisVisu = true;
			cidadeVisu = true;
			parceiro = true;
			inicioprograma = true;
			acomodacaoVisu = true;
			dataaplicacao = true;
			previsaopagamento = true;
			datapagamento = true;
			pathwayVisu = true;
			situacaovenda = true;
			situacaogerencia = true;
			tipoinvoice = true;
			valornet = true;
			credito = true;
			nomecliente = true;
			dataembarque = true;
		}
	}
	
	

	public void visualizarParceiros(Hecontrole hecontrole) {
		if (hecontrole.getHe().getListaHeParceirosList() != null
				&& hecontrole.getHe().getListaHeParceirosList().size() > 0) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("listaHeParceiros", hecontrole.getHe().getListaHeParceirosList());
			session.setAttribute("voltar", "consControleHe");
			RequestContext.getCurrentInstance().openDialog("visualizarParceiros");
		} else {
			Mensagem.lancarMensagemInfo("Nenhum parceiro encontrado", "");
		}
	}
	

}
