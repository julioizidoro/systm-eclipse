package br.com.travelmate.managerBean.financeiro.revisaoFinanceiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.LancamentoCartaoCreditoFacade;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.model.Lancamentocartaocredito;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class RevisaoFinanceiroMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Vendas> listaVendaNova;
	private List<Vendas> listaVendaPendente;
	private int nPendentes = 0;
	private int nVendaNova = 0;
	private Date dataInicial;
	private Date dataFinal;
	private List<Produtos> listaProdutos;
	private Produtos produtos;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private int nCartaoCredito = 0;
	private List<Lancamentocartaocredito> listaCartaoCredito;
	private int idVenda;
	private Usuario usuario;
	private List<Usuario> listaUsuario;
	
	
	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}






	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}






	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaVendaNova = (List<Vendas>) session.getAttribute("listaVendaNova");
		listaVendaPendente = (List<Vendas>) session.getAttribute("listaVendaPendente");
		session.removeAttribute("listaVendaNova");
		session.removeAttribute("listaVendaPendente");
		if (listaVendaNova != null && listaVendaPendente != null) {
			nPendentes = listaVendaPendente.size();
			nVendaNova = listaVendaNova.size();
		}else{
			gerarListaVendas();
		}
		gerarListaCartaoCredito();
		listaProdutos = GerarListas.listarProdutos("");
		listaUnidadeNegocio = GerarListas.listarUnidade();
	}
	
	




	public List<Vendas> getListaVendaNova() {
		return listaVendaNova;
	}






	public void setListaVendaNova(List<Vendas> listaVendaNova) {
		this.listaVendaNova = listaVendaNova;
	}






	public List<Vendas> getListaVendaPendente() {
		return listaVendaPendente;
	}






	public void setListaVendaPendente(List<Vendas> listaVendaPendente) {
		this.listaVendaPendente = listaVendaPendente;
	}






	public int getnPendentes() {
		return nPendentes;
	}






	public Usuario getUsuario() {
		return usuario;
	}






	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}






	public void setnPendentes(int nPendentes) {
		this.nPendentes = nPendentes;
	}






	public int getnVendaNova() {
		return nVendaNova;
	}






	public void setnVendaNova(int nVendaNova) {
		this.nVendaNova = nVendaNova;
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






	public List<Produtos> getListaProdutos() {
		return listaProdutos;
	}






	public void setListaProdutos(List<Produtos> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}






	public Produtos getProdutos() {
		return produtos;
	}






	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
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






	public int getnCartaoCredito() {
		return nCartaoCredito;
	}






	public void setnCartaoCredito(int nCartaoCredito) {
		this.nCartaoCredito = nCartaoCredito;
	}






	public List<Lancamentocartaocredito> getListaCartaoCredito() {
		return listaCartaoCredito;
	}






	public void setListaCartaoCredito(List<Lancamentocartaocredito> listaCartaoCredito) {
		this.listaCartaoCredito = listaCartaoCredito;
	}






	public int getIdVenda() {
		return idVenda;
	}






	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}


	public void limparPesquisa() {
		listaVendaNova = new ArrayList<Vendas>();
		listaVendaPendente = new ArrayList<Vendas>();
		listaCartaoCredito = new ArrayList<>();
		gerarListaVendas();
		idVenda = 0;
		unidadenegocio = null;
		produtos = null;
		dataFinal = null;
		dataInicial = null;
	}



	public String cadastroRevisaoFinanceiro(Vendas venda) {
		boolean revisar = true;
		if (venda.getProdutos().getIdprodutos() == 2) {
			SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
			Seguroviagem seguroviagem = seguroViagemFacade.consultar(venda.getIdvendas());
			if (seguroviagem != null) {
				if (seguroviagem.getIdvendacurso() > 0) {
					revisar = false;
				}
			}
		}
		if (revisar) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("venda", venda);
			session.setAttribute("listaVendaNova", listaVendaNova);
			session.setAttribute("listaVendaPendente", listaVendaPendente);
			return "cadRevisaoFinanceiro";
		}
		Mensagem.lancarMensagemInfo("", "");
		return "";
	}
	
	
	public void gerarListaVendas(){
		VendasFacade vendasFacade = new VendasFacade();
		listaVendaNova = vendasFacade.lista("SELECT v FROM Vendas v WHERE v.situacaofinanceiro='N'"+
				" and v.situacaogerencia<>'P' and v.situacao<>'CANCELADA' and v.vendasMatriz='S' ORDER BY v.dataVenda DESC");
		if (listaVendaNova == null) {
			listaVendaNova = new ArrayList<>();
		}
		listaVendaPendente = vendasFacade.lista("SELECT v FROM Vendas v WHERE v.situacaofinanceiro='P'"+
				" and v.situacaogerencia<>'P' and v.situacao<>'CANCELADA' and v.vendasMatriz='S' ORDER BY v.vendapendencia.dataproximocontato ");
		if (listaVendaPendente == null) {
			listaVendaPendente = new ArrayList<>();
		}
		nPendentes = listaVendaPendente.size();
		nVendaNova = listaVendaNova.size();
	}
	  
	
	public void pesquisar(){
		VendasFacade vendasFacade = new VendasFacade();
		String sql = "SELECT v FROM Vendas v WHERE v.cliente.nome like '%%' ";
		
		if (unidadenegocio != null) {
			sql = sql + " and v.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		
		if (produtos != null) {
			sql = sql + " and v.produtos.idprodutos=" + produtos.getIdprodutos();
		}
		if (dataInicial != null && dataFinal != null) {
			sql = sql + " and v.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' and v.dataVenda<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
		}
		if (idVenda > 0) {
			sql = sql + " and v.idvendas=" + idVenda;
		}
		listaVendaNova = vendasFacade.lista(sql +  " and v.situacaofinanceiro='N'"+
				" and v.situacaogerencia<>'P' and v.situacao<>'CANCELADA' order by v.dataVenda DESC");
		if (listaVendaNova == null) {
			listaVendaNova = new ArrayList<>();
		}
		listaVendaPendente = vendasFacade.lista(sql + " and v.situacaofinanceiro='P'"+
				" and v.situacaogerencia<>'P' and v.situacao<>'CANCELADA' order by v.dataVenda DESC");
		if (listaVendaPendente == null) {
			listaVendaPendente = new ArrayList<>();
		}
		nPendentes = listaVendaPendente.size();
		nVendaNova = listaVendaNova.size();
	}
	
	
	public String retonarCorVenda(Vendas vendas){
    	if (vendas.getVendaspacote() != null) {
			return "color:red;";
		}
    	return "";
    }
	
	public void gerarListaCartaoCredito(){
		LancamentoCartaoCreditoFacade lancamentoCartaoCreditoFacade = new LancamentoCartaoCreditoFacade();
		listaCartaoCredito = lancamentoCartaoCreditoFacade.listar("Select l From Lancamentocartaocredito l Where l.lancado=false");
		if (listaCartaoCredito == null) {
			listaCartaoCredito = new ArrayList<>();
		}
		nCartaoCredito = listaCartaoCredito.size();
	}
	
	public void confirmarLancamentoCartao(Lancamentocartaocredito lancamentocartaocredito){
		LancamentoCartaoCreditoFacade lancamentoCartaoCreditoFacade = new LancamentoCartaoCreditoFacade();
		lancamentocartaocredito.setLancado(true);
		lancamentoCartaoCreditoFacade.salvar(lancamentocartaocredito);
		listaCartaoCredito.remove(lancamentocartaocredito);
		nCartaoCredito = nCartaoCredito - 1;
		Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
	}
	
	
	public void salvarCartao(Lancamentocartaocredito lancamento) {
		LancamentoCartaoCreditoFacade lancamentoCartaoCreditoFacade = new LancamentoCartaoCreditoFacade();
		lancamentoCartaoCreditoFacade.salvar(lancamento);
		Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
	}
	
	public void gerarListaConsultor() {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			listaUsuario = usuarioFacade
					.listar("select u from Usuario u where u.situacao='Ativo' and u.unidadenegocio.idunidadeNegocio="
							+ unidadenegocio.getIdunidadeNegocio() + " order by u.nome");
		}
		if (listaUsuario == null) {
			listaUsuario = new ArrayList<Usuario>();
		}
	}
	

}
