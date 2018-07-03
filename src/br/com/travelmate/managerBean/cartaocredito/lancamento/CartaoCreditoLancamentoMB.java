package br.com.travelmate.managerBean.cartaocredito.lancamento;

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

import br.com.travelmate.facade.CartaoCreditoFacade;
import br.com.travelmate.facade.CartaoCreditoLancamentoFacade;
import br.com.travelmate.facade.ContasPagarFacade;
import br.com.travelmate.facade.PlanoContaFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cartaocredito;
import br.com.travelmate.model.Cartaocreditolancamento;
import br.com.travelmate.model.Contaspagar; 
import br.com.travelmate.model.Planoconta;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem; 

@Named
@ViewScoped
public class CartaoCreditoLancamentoMB implements Serializable{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private String descricao=""; 
	private List<Cartaocredito> listaCartaoCredito; 
	private Cartaocredito cartaocredito; 
	private Date dataLancamento;
	private Usuario usuario;
	private List<Usuario> listaUsuario;
	private Planoconta planoconta;
	private List<Planoconta> listaPlanoConta; 
	private List<Cartaocreditolancamento> listaLancamento;
	private float valorTotal;
	private boolean confirmado;
	private String mes;
	private String ano;
	private boolean recorrente;
	
	@PostConstruct
	public void init() { 
		gerarlistaCartaoCredito(); 
		gerarListaPlanoConta();
		gerarListaUsuario();
		gerarListaLancamentos();
	}
	 
	
	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public Cartaocredito getCartaocredito() {
		return cartaocredito;
	}


	public void setCartaocredito(Cartaocredito cartaocredito) {
		this.cartaocredito = cartaocredito;
	}


	public Date getDataLancamento() {
		return dataLancamento;
	}


	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}


	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}


	public Planoconta getPlanoconta() {
		return planoconta;
	}


	public void setPlanoconta(Planoconta planoconta) {
		this.planoconta = planoconta;
	}


	public List<Planoconta> getListaPlanoConta() {
		return listaPlanoConta;
	}


	public void setListaPlanoConta(List<Planoconta> listaPlanoConta) {
		this.listaPlanoConta = listaPlanoConta;
	}


	public List<Cartaocreditolancamento> getListaLancamento() {
		return listaLancamento;
	}


	public void setListaLancamento(List<Cartaocreditolancamento> listaLancamento) {
		this.listaLancamento = listaLancamento;
	}


	public List<Cartaocredito> getListaCartaoCredito() {
		return listaCartaoCredito;
	} 

	public void setListaCartaoCredito(List<Cartaocredito> listaCartaoCredito) {
		this.listaCartaoCredito = listaCartaoCredito;
	}  
 
	public float getValorTotal() {
		return valorTotal;
	}


	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}


	public boolean isConfirmado() {
		return confirmado;
	}


	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
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


	public boolean isRecorrente() {
		return recorrente;
	}


	public void setRecorrente(boolean recorrente) {
		this.recorrente = recorrente;
	}


	public void gerarlistaCartaoCredito(){
		String sql = "select c from Cartaocredito c where c.situacao=1";
		if ((usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()!=1) && (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()!=3)) {
			sql = sql + " and c.unidadenegocio.idunidadeNegocio=" + usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		}
		sql = sql + " order by c.nome";
		CartaoCreditoFacade cartaoCreditoFacade = new CartaoCreditoFacade();
        listaCartaoCredito = cartaoCreditoFacade.listar(sql);
        if (listaCartaoCredito==null){
        	listaCartaoCredito = new ArrayList<Cartaocredito>();
        }
    } 
	
	public void pesquisar(){  
		CartaoCreditoLancamentoFacade cartaoCreditoLancamentoFacade = new CartaoCreditoLancamentoFacade(); 
		String sql="select c from Cartaocreditolancamento c where c.idcartaocreditolancamento>0";
		if(descricao!=null && descricao.length()>0){
			sql = sql + " and c.descricao like '%"+descricao+"%'";
		}
		if(cartaocredito!=null && cartaocredito.getIdcartaocredito()!=null){
			sql = sql + " and c.cartaocredito.idcartaocredito="+cartaocredito.getIdcartaocredito();
		}
		if(usuario!=null && usuario.getIdusuario()!=null){
			sql = sql + " and c.usuario.idusuario="+usuario.getIdusuario();
		}
		if(planoconta!=null && planoconta.getIdplanoconta()!=null){
			sql = sql + " and c.planoconta.idplanoconta="+planoconta.getIdplanoconta();
		}
		if(dataLancamento!=null){
			sql = sql + " and c.data>='"+Formatacao.ConvercaoDataSql(dataLancamento)+"'";
		}
		if (confirmado) {
			sql = sql + " and c.lancado=1 ";
		}else{
			sql = sql + " and c.lancado=0 ";
		}
		if ((mes != null && !mes.equalsIgnoreCase("")) && (ano != null && !ano.equalsIgnoreCase(""))) {
			String diaFinal = "30";
			if (mes.equalsIgnoreCase("2")) {
				diaFinal = "28";
			}
			if (recorrente) {
				sql = sql + " and c.valorrecorrente=1 "; 
			}
			sql = sql + " and c.data>='" + ano + "-" + mes + "-01' and c.data<='" + ano + "-" + mes + "-" + diaFinal + "' "; 
		}
		sql = sql + " order by c.data desc"; 
		listaLancamento = cartaoCreditoLancamentoFacade.listar(sql);
		valorTotal = 0.0f;
		for (int i = 0; i < listaLancamento.size(); i++) {
			valorTotal = valorTotal + listaLancamento.get(i).getValorlancado();
		}
	}
	
	public void limpar(){ 
		descricao =""; 
		cartaocredito=null;
		dataLancamento=null;
		usuario=null;
		planoconta=null;
		valorTotal = 0.0f;
		mes = "";
		ano = "";
		confirmado = false;
		recorrente = false;
		gerarListaLancamentos();
	}
	
	public void gerarListaPlanoConta(){
        PlanoContaFacade planoContaFacade = new PlanoContaFacade();
        listaPlanoConta = planoContaFacade.listar("");
        if (listaPlanoConta==null){
            listaPlanoConta = new ArrayList<Planoconta>();
        }
    }
	
	public void gerarListaUsuario(){
        UsuarioFacade usuarioFacade = new UsuarioFacade();
        String sql ="select u from Usuario u where u.situacao='Ativo' order by u.nome";
        listaUsuario = usuarioFacade.listar(sql);
        if (listaUsuario==null){
        	listaUsuario = new ArrayList<Usuario>();
        }
    }
	
	public void gerarListaLancamentos(){
		List<Cartaocreditolancamento> listaFatura = new ArrayList<>();
		CartaoCreditoLancamentoFacade cartaoCreditoLancamentoFacade = new CartaoCreditoLancamentoFacade();
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 60, "yyyy-MM-dd");
		String sql="select c from Cartaocreditolancamento c where c.data>='"+dataConsulta+"' and c.lancado=0 ";
		if ((usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()!=1) && (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()!=3)) {
			sql = sql + " and c.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario();
		}
		sql = sql + " order by c.data desc";
		listaLancamento = cartaoCreditoLancamentoFacade.listar(sql);
		if (listaLancamento == null) {
			listaLancamento = new ArrayList<Cartaocreditolancamento>();
		}
		valorTotal = 0.0f;
		for (int i = 0; i < listaLancamento.size(); i++) {
			int diaLancamento = Formatacao.getDiaData(listaLancamento.get(i).getData());
			int mesLancamento = Formatacao.getMesData(listaLancamento.get(i).getData()) + 1;
			int mesHoje= Formatacao.getMesData(new Date());
			mesHoje = mesHoje + 2;
			if (mesHoje > 12) {
				mesHoje = 1;
			}
			if (listaLancamento.get(i).getCartaocredito().getDatafechamento() >= diaLancamento) {
				valorTotal = valorTotal + listaLancamento.get(i).getValorlancado();
				listaFatura.add(listaLancamento.get(i));
			}else if(mesHoje == mesLancamento) {
				valorTotal = valorTotal + listaLancamento.get(i).getValorlancado();
				listaFatura.add(listaLancamento.get(i));
			}
		}
		listaLancamento = listaFatura;
		
	}
	
	public String imagemConferencia(Cartaocreditolancamento lancamento){
		if(lancamento.getConferido()){
			return "bolaVerde.png";
		}else return "bolaVermelha.png";
	}
	
	public String imagemLancado(Cartaocreditolancamento lancamento){
		if(lancamento.getLancado()){
			return "bolaVerde.png";
		}else return "bolaVermelha.png";
	}
	
	public void lancarContasPagar(Cartaocreditolancamento lancamento){
		if(lancamento.getLancado()){
			Mensagem.lancarMensagemInfo("Contas a Pagar já está lançado!", "");
		}else{
			Contaspagar contaspagar = new Contaspagar();
			contaspagar.setBanco(lancamento.getCartaocredito().getBanco());
			String comp;
			int mes = Formatacao.getMesData(lancamento.getData()); 
			if(mes<10){
				comp="0"+mes+"/"+Formatacao.getAnoData(lancamento.getData());
			}else comp = mes+"/"+Formatacao.getAnoData(lancamento.getData());
			contaspagar.setCompetencia(comp); 
			contaspagar.setDataEmissao(new Date());
			contaspagar.setDescricao(lancamento.getDescricao());
			contaspagar.setPlanoconta(lancamento.getPlanoconta());
			contaspagar.setUnidadenegocio(lancamento.getUsuario().getUnidadenegocio());
			contaspagar.setValorentrada(0.0f);
			contaspagar.setValorsaida(lancamento.getValorlancado());
			ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
			contaspagar = contasPagarFacade.salvar(contaspagar);
			lancamento.setLancado(true);
			CartaoCreditoLancamentoFacade cartaoCreditoLancamentoFacade = new CartaoCreditoLancamentoFacade();
			lancamento = cartaoCreditoLancamentoFacade.salvar(lancamento);
			if (lancamento.isValorrecorrente()) {
				lancamentoRecorrente(lancamento);
			}
			gerarListaLancamentos();
			Mensagem.lancarMensagemInfo("Contas a Pagar lançado com sucesso!", "");
		}
	}
	
	public void lancamentoRecorrente(Cartaocreditolancamento cartaocreditolancamento) {
		CartaoCreditoLancamentoFacade cartaoCreditoLancamentoFacade = new CartaoCreditoLancamentoFacade();
		Cartaocreditolancamento cartao = new Cartaocreditolancamento();
		cartao.setCartaocredito(cartaocreditolancamento.getCartaocredito());
		cartao.setConferido(false);
		cartao.setDescricao(cartaocreditolancamento.getDescricao());
		cartao.setEstabelecimento(cartaocreditolancamento.getEstabelecimento());
		cartao.setHabilitarmoeda(cartaocreditolancamento.isHabilitarmoeda());
		cartao.setLancado(false);
		cartao.setMoedas(cartaocreditolancamento.getMoedas());
		cartao.setNumeroparcelas(cartaocreditolancamento.getNumeroparcelas());
		cartao.setObservacao(cartaocreditolancamento.getObservacao());
		cartao.setPlanoconta(cartaocreditolancamento.getPlanoconta());
		cartao.setUsuario(cartaocreditolancamento.getUsuario());
		cartao.setValorcambio(cartaocreditolancamento.getValorcambio());
		cartao.setValorinformado(cartaocreditolancamento.getValorinformado());
		cartao.setValorlancado(cartaocreditolancamento.getValorlancado());
		cartao.setValorrecorrente(cartaocreditolancamento.isValorrecorrente());
		try {
			cartao.setData(Formatacao.SomarDiasDatas(cartaocreditolancamento.getData(), 30));
		} catch (Exception e) {
			e.printStackTrace();
		}
		cartaoCreditoLancamentoFacade.salvar(cartao);
	}
	
	public String conferencia(Cartaocreditolancamento lancamento) {
		if(lancamento.getConferido()==true){
			Mensagem.lancarMensagemInfo("Está conferência já foi realizada!", "");
		}else{
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("lancamento", lancamento);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 600);
			RequestContext.getCurrentInstance().openDialog("conferenciaLancamentoCartao", options, null);
		}
		return "";
	}
	
	public String editar(Cartaocreditolancamento lancamento) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("lancamento", lancamento);
		session.setAttribute("confirmar", false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 600);
		RequestContext.getCurrentInstance().openDialog("cadLancamentoCartao", options, null);
		return "";
	}
	
	public String confirmarLancamentos(Cartaocreditolancamento lancamento) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("lancamento", lancamento);
		session.setAttribute("confirmar", true);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 600);
		RequestContext.getCurrentInstance().openDialog("cadLancamentoCartao", options, null);
		return "";
	}
	
	public String novo() {   
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("confirmar", false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 600);
		RequestContext.getCurrentInstance().openDialog("cadLancamentoCartao", options, null);
		return "";
	}
	
	public boolean vefirificarBotaoEditar(Cartaocreditolancamento lancamento){
		if(lancamento.getConferido()==true){
			return true;
		}
		return false;
	}
	
	
	public void excluirLancamento(Cartaocreditolancamento lancamento){
		CartaoCreditoLancamentoFacade cartaoCreditoLancamentoFacade = new CartaoCreditoLancamentoFacade();
		cartaoCreditoLancamentoFacade.excluir(lancamento.getIdcartaocreditolancamento());
		listaLancamento.remove(lancamento);
		Mensagem.lancarMensagemInfo("Excluido com sucesso", "");
	}
	
	
}
