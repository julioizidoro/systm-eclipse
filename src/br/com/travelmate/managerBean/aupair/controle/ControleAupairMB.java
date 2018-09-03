package br.com.travelmate.managerBean.aupair.controle;

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

import br.com.travelmate.bean.UtilBean;
import br.com.travelmate.facade.AupairFacade; 
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade; 
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Controleaupair; 
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario; 
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class ControleAupairMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Controleaupair controle;
	private List<Controleaupair> listaControle;
	private String nomeCliente="";
	private Date iniDataEmbarque;
	private Date finalDataEmbarque; 
	private Date iniDataVenda;
	private Date finalDataVenda; 
	private Unidadenegocio unidadenegocio;
	private Usuario usuario; 
	private String situacao;
	private List<Usuario> listaConsultor;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private int idvenda;
	private String voltar;
	private List<Controleaupair> listaControleProcesso;
	private List<Controleaupair> listaControleOnline;
	private List<Controleaupair> listaControleMatch;
	private List<Controleaupair> listaControleVistoPen;
	private List<Controleaupair> listaControleVistoNeg;
	private List<Controleaupair> listaControleVistoApro;
	private List<Controleaupair> listaControleEmbarcado;
	private List<Controleaupair> listaControleFinalizado;
	private List<Controleaupair> listaControleTransaction;
	private List<Controleaupair> listaControleSuspenso;
	private List<Controleaupair> listaControleCancelado;
	private int numeroProcesso;
	private int numeroOnline;
	private int numeroMatch;
	private int numeroVistoPen;
	private int numeroVistoNeg;
	private int numeroVistoApro;
	private int numeroEmbarcado;
	private int numeroFinalizado;
	private int numeroTransaction;
	private int numeroSuspenso;
	private int numeroCancelado;
	private int numeroTodos;
	private String sql;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			listaControle = (List<Controleaupair>) session.getAttribute("listaControle");
			session.removeAttribute("listaControle");
			if (listaControle == null || listaControle.size() == 0) {
				listarControle();
			}else {
				numeroTodos = listaControle.size();
				numerosStatus();
			}
			gerarListaUnidadeNegocio();
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	} 

	public Controleaupair getControle() {
		return controle;
	}

	public void setControle(Controleaupair controle) {
		this.controle = controle;
	}

	public List<Controleaupair> getListaControle() {
		return listaControle;
	}

	public void setListaControle(List<Controleaupair> listaControle) {
		this.listaControle = listaControle;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
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
 
	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Date getIniDataEmbarque() {
		return iniDataEmbarque;
	}

	public void setIniDataEmbarque(Date iniDataEmbarque) {
		this.iniDataEmbarque = iniDataEmbarque;
	}

	public Date getFinalDataEmbarque() {
		return finalDataEmbarque;
	}

	public void setFinalDataEmbarque(Date finalDataEmbarque) {
		this.finalDataEmbarque = finalDataEmbarque;
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

	public int getIdvenda() {
		return idvenda;
	}

	public void setIdvenda(int idvenda) {
		this.idvenda = idvenda;
	} 

	public String getVoltar() {
		return voltar;
	}

	public void setVoltar(String voltar) {
		this.voltar = voltar;
	}

	public Date getIniDataVenda() {
		return iniDataVenda;
	}

	public void setIniDataVenda(Date iniDataVenda) {
		this.iniDataVenda = iniDataVenda;
	}

	public Date getFinalDataVenda() {
		return finalDataVenda;
	}

	public void setFinalDataVenda(Date finalDataVenda) {
		this.finalDataVenda = finalDataVenda;
	}

	public List<Controleaupair> getListaControleProcesso() {
		return listaControleProcesso;
	}

	public void setListaControleProcesso(List<Controleaupair> listaControleProcesso) {
		this.listaControleProcesso = listaControleProcesso;
	}

	public List<Controleaupair> getListaControleOnline() {
		return listaControleOnline;
	}

	public void setListaControleOnline(List<Controleaupair> listaControleOnline) {
		this.listaControleOnline = listaControleOnline;
	}

	public List<Controleaupair> getListaControleMatch() {
		return listaControleMatch;
	}

	public void setListaControleMatch(List<Controleaupair> listaControleMatch) {
		this.listaControleMatch = listaControleMatch;
	}

	public List<Controleaupair> getListaControleVistoPen() {
		return listaControleVistoPen;
	}

	public void setListaControleVistoPen(List<Controleaupair> listaControleVistoPen) {
		this.listaControleVistoPen = listaControleVistoPen;
	}

	public List<Controleaupair> getListaControleVistoNeg() {
		return listaControleVistoNeg;
	}

	public void setListaControleVistoNeg(List<Controleaupair> listaControleVistoNeg) {
		this.listaControleVistoNeg = listaControleVistoNeg;
	}

	public List<Controleaupair> getListaControleVistoApro() {
		return listaControleVistoApro;
	}

	public void setListaControleVistoApro(List<Controleaupair> listaControleVistoApro) {
		this.listaControleVistoApro = listaControleVistoApro;
	}

	public List<Controleaupair> getListaControleFinalizado() {
		return listaControleFinalizado;
	}

	public void setListaControleFinalizado(List<Controleaupair> listaControleFinalizado) {
		this.listaControleFinalizado = listaControleFinalizado;
	}

	public List<Controleaupair> getListaControleTransaction() {
		return listaControleTransaction;
	}

	public void setListaControleTransaction(List<Controleaupair> listaControleTransaction) {
		this.listaControleTransaction = listaControleTransaction;
	}

	public List<Controleaupair> getListaControleCancelado() {
		return listaControleCancelado;
	}

	public void setListaControleCancelado(List<Controleaupair> listaControleCancelado) {
		this.listaControleCancelado = listaControleCancelado;
	}

	public int getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(int numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public int getNumeroOnline() {
		return numeroOnline;
	}

	public void setNumeroOnline(int numeroOnline) {
		this.numeroOnline = numeroOnline;
	}

	public int getNumeroMatch() {
		return numeroMatch;
	}

	public void setNumeroMatch(int numeroMatch) {
		this.numeroMatch = numeroMatch;
	}

	public int getNumeroVistoPen() {
		return numeroVistoPen;
	}

	public void setNumeroVistoPen(int numeroVistoPen) {
		this.numeroVistoPen = numeroVistoPen;
	}

	public int getNumeroVistoNeg() {
		return numeroVistoNeg;
	}

	public void setNumeroVistoNeg(int numeroVistoNeg) {
		this.numeroVistoNeg = numeroVistoNeg;
	}

	public int getNumeroVistoApro() {
		return numeroVistoApro;
	}

	public void setNumeroVistoApro(int numeroVistoApro) {
		this.numeroVistoApro = numeroVistoApro;
	}

	public int getNumeroFinalizado() {
		return numeroFinalizado;
	}

	public void setNumeroFinalizado(int numeroFinalizado) {
		this.numeroFinalizado = numeroFinalizado;
	}

	public int getNumeroTransaction() {
		return numeroTransaction;
	}

	public void setNumeroTransaction(int numeroTransaction) {
		this.numeroTransaction = numeroTransaction;
	}

	public int getNumeroCancelado() {
		return numeroCancelado;
	}

	public void setNumeroCancelado(int numeroCancelado) {
		this.numeroCancelado = numeroCancelado;
	}

	public List<Controleaupair> getListaControleEmbarcado() {
		return listaControleEmbarcado;
	}

	public void setListaControleEmbarcado(List<Controleaupair> listaControleEmbarcado) {
		this.listaControleEmbarcado = listaControleEmbarcado;
	}

	public int getNumeroEmbarcado() {
		return numeroEmbarcado;
	}

	public void setNumeroEmbarcado(int numeroEmbarcado) {
		this.numeroEmbarcado = numeroEmbarcado;
	}

	public List<Controleaupair> getListaControleSuspenso() {
		return listaControleSuspenso;
	}

	public void setListaControleSuspenso(List<Controleaupair> listaControleSuspenso) {
		this.listaControleSuspenso = listaControleSuspenso;
	}

	public int getNumeroSuspenso() {
		return numeroSuspenso;
	}

	public void setNumeroSuspenso(int numeroSuspenso) {
		this.numeroSuspenso = numeroSuspenso;
	}

	public int getNumeroTodos() {
		return numeroTodos;
	}

	public void setNumeroTodos(int numeroTodos) {
		this.numeroTodos = numeroTodos;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void listarControle() {
		AupairFacade aupairFacade = new AupairFacade();   
		String data = Formatacao.SubtarirDatas(new Date(), 182, "yyyy/MM/dd");
		sql = "select c from Controleaupair c where c.vendas.dataVenda>='" + data + "' and c.vendas.situacao<>'CANCELADA' order by c.vendas.dataVenda desc";
		listaControle = aupairFacade.listaControle(sql);
		if (listaControle == null) {
			listaControle = new ArrayList<Controleaupair>();
		} 
		numeroTodos = listaControle.size();
		numerosStatus();
	}
 
	public void pesquisar() {
		AupairFacade aupairFacade = new AupairFacade(); 
		sql = "select c from Controleaupair c where c.vendas.cliente.nome like '%" + nomeCliente+ "%' and c.vendas.situacao<>'CANCELADA' ";
		if(idvenda>0){
			sql = sql + " and c.vendas.idvendas="+idvenda;
		}
		if (unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){ 
			sql = sql + " and c.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		if (usuario!=null && usuario.getIdusuario()!=null){ 
			sql = sql + " and c.vendas.usuario.idusuario=" + usuario.getIdusuario();
		}
		if ((iniDataEmbarque != null) && (finalDataEmbarque != null)) {
			sql = sql + " and c.dataembarque>='" + Formatacao.ConvercaoDataSql(iniDataEmbarque) + "'";
			sql = sql + " and c.dataembarque<='" + Formatacao.ConvercaoDataSql(finalDataEmbarque) + "'";
		}  
		if ((iniDataVenda != null) && (finalDataVenda != null)) {
			sql = sql + " and c.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(iniDataEmbarque) + "'";
			sql = sql + " and c.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(finalDataEmbarque) + "'";
		}
		if(!situacao.equalsIgnoreCase("TODOS")){
			sql = sql + " and c.statusprocesso='"+situacao+"'";
		}
		sql = sql + " order by c.vendas.dataVenda desc, c.idcontroleAuPair";
		listaControle = aupairFacade.listaControle(sql);
		if (listaControle == null) {
			listaControle = new ArrayList<Controleaupair>();
		} 
		numeroTodos = listaControle.size();
		numerosStatus();
	}

	public void limpar() {
		iniDataEmbarque = null;
		finalDataEmbarque = null;
		iniDataVenda=null;
		finalDataVenda=null;
		nomeCliente = "";
		unidadenegocio=null;
		usuario=null;
		situacao="TODOS";
		listarControle();
	}
 
	public String documentacao(Controleaupair controle) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendas", controle.getVendas());
		voltar = "controleAupair"; 
		session.setAttribute("voltar", voltar);
		session.setAttribute("listaControle", listaControle);
		return "consArquivos";
	}
	 
	public void gerarListaUnidadeNegocio(){
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
        if (listaUnidadeNegocio==null){
            listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
        }
    }
	
	public void gerarListaConsultor(){
		if(unidadenegocio!=null){
	        UsuarioFacade usuarioFacade = new UsuarioFacade();
	        listaConsultor = usuarioFacade.listar("select u from Usuario u where u.situacao='Ativo' and u.unidadenegocio.idunidadeNegocio="+unidadenegocio.getIdunidadeNegocio()+" order by u.nome");
	        if (listaConsultor==null){
	            listaConsultor = new ArrayList<Usuario>();
	        }
		}
    } 
	
	public String atualizarInformacoes(Controleaupair controle) {
		if (controle != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("controle", controle);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 700);
			RequestContext.getCurrentInstance().openDialog("atualizarControleAupair", options, null);
		}
		return "";
	} 
	 
	public void salvarAlteracoes(Controleaupair controle){
		AupairFacade aupairFacade = new AupairFacade(); 
		if(controle.getDataembarque()!=null && controle.getVendas().getFornecedorcidade().getCidade().getPais().getIdpais()==13){
			UtilBean utilBean = new UtilBean();
			controle.setDatapagamento(utilBean.calcularDataPagamentoComissaoAuPairEUA(controle.getDataembarque()));
			controle.setDataminimaretorno(utilBean.calcularDataRetornoMinimo(controle.getDataembarque(), 26));
			if(controle.getDataretormo()!=null){
				if(controle.getDataretormo().after(controle.getDataminimaretorno())){
					controle.setValorcomissao(200f);
				}else{
					controle.setDescontocomissao(controle.getValorcomissao()*-1);
					controle.setDatadescontocomissao(utilBean.calcularDataPagamentoComissaoAuPairEUA(controle.getDataretormo()));
				}
			}
		} 
		controle = aupairFacade.salvar(controle);
	}
	 
	public String imagemSituacao(Controleaupair controle){
		if(controle.getStatusprocesso().equalsIgnoreCase("Processo")){
			return "../../resources/img/bolaAmarela.png";
		}else if(controle.getStatusprocesso().equalsIgnoreCase("On line")){
			return "../../resources/img/bolaAzul.png"; 
		}else if(controle.getStatusprocesso().equalsIgnoreCase("Match")){
			return "../../resources/img/bolaLaranja.png";
		}else if(controle.getStatusprocesso().equalsIgnoreCase("Visto Pendente")){
			return "../../resources/img/bolaRoxa.png";
		}else if(controle.getStatusprocesso().equalsIgnoreCase("Visto Negado")){
			return "../../resources/img/bolaPreta.png";
		}else if(controle.getStatusprocesso().equalsIgnoreCase("Visto Aprovado")){
			return "../../resources/img/bolaRosa.png";
		}else if(controle.getStatusprocesso().equalsIgnoreCase("Cancelado")){
			return "../../resources/img/bolaVermelha.png";
		}else if(controle.getStatusprocesso().equalsIgnoreCase("Embarcado")){
			return "../../resources/img/bolaCinza.png";
		}else if(controle.getStatusprocesso().equalsIgnoreCase("Finalizado")){
			return "../../resources/img/bolaVerde.png";
		}else if(controle.getStatusprocesso().equalsIgnoreCase("Left Program")){
			return "../../resources/img/bolaVerdeMusgo.png";
		}else if(controle.getStatusprocesso().equalsIgnoreCase("Transaction")){
			return "../../resources/img/bolaEsverdiada.png";
		}else{
			return "";
		}
	} 
	
	public String imagemSituacaoComissaoEX(Controleaupair controle){
		if(controle.isComissaopaga()){
			return "../../resources/img/confirmarBola.png";
		}else return "../../resources/img/iconeSApp.png"; 
	}
	
	public void numerosStatus(){ 
		numeroProcesso=0;
		numeroOnline=0;
		numeroMatch=0;
		numeroVistoPen=0;
		numeroVistoNeg=0;
		numeroVistoApro=0;
		numeroEmbarcado=0;
		numeroFinalizado=0;
		numeroTransaction=0;
		numeroSuspenso=0;
		numeroCancelado=0;
		numeroTodos = listaControle.size();
		listaControleProcesso= new ArrayList<>();
		listaControleOnline= new ArrayList<>();
		listaControleMatch= new ArrayList<>();
		listaControleVistoPen= new ArrayList<>();
		listaControleVistoNeg= new ArrayList<>();
		listaControleVistoApro= new ArrayList<>();
		listaControleEmbarcado= new ArrayList<>();
		listaControleFinalizado= new ArrayList<>();
		listaControleTransaction= new ArrayList<>();
		listaControleSuspenso= new ArrayList<>();
		listaControleCancelado= new ArrayList<>();
		for (int i = 0; i < listaControle.size(); i++) {
			if(listaControle.get(i).getStatusprocesso().equalsIgnoreCase("Processo")){
				numeroProcesso = numeroProcesso+1;
				listaControleProcesso.add(listaControle.get(i));
			}else if(listaControle.get(i).getStatusprocesso().equalsIgnoreCase("On line")){
				numeroOnline = numeroOnline+1;
				listaControleOnline.add(listaControle.get(i));
			}else if(listaControle.get(i).getStatusprocesso().equalsIgnoreCase("Match")){
				numeroMatch = numeroMatch+1;
				listaControleMatch.add(listaControle.get(i));
			}else if(listaControle.get(i).getStatusprocesso().equalsIgnoreCase("Visto Pendente")){
				numeroVistoPen = numeroVistoPen+1;
				listaControleVistoPen.add(listaControle.get(i));
			}else if(listaControle.get(i).getStatusprocesso().equalsIgnoreCase("Visto Negado")){
				numeroVistoNeg = numeroVistoNeg+1;
				listaControleVistoNeg.add(listaControle.get(i));
			}else if(listaControle.get(i).getStatusprocesso().equalsIgnoreCase("Visto Aprovado")){
				numeroVistoApro = numeroVistoApro+1;
				listaControleVistoApro.add(listaControle.get(i));
			}else if(listaControle.get(i).getStatusprocesso().equalsIgnoreCase("Cancelado")){
				numeroCancelado = numeroCancelado+1;
				listaControleCancelado.add(listaControle.get(i));
			}else if(listaControle.get(i).getStatusprocesso().equalsIgnoreCase("Embarcado")){
				numeroEmbarcado = numeroEmbarcado+1;
				listaControleEmbarcado.add(listaControle.get(i));
			}else if(listaControle.get(i).getStatusprocesso().equalsIgnoreCase("Finalizado")){
				numeroFinalizado = numeroFinalizado+1;
				listaControleFinalizado.add(listaControle.get(i));
			}else if(listaControle.get(i).getStatusprocesso().equalsIgnoreCase("Left Program")){
				numeroSuspenso = numeroSuspenso+1;
				listaControleSuspenso.add(listaControle.get(i));
			} else if(listaControle.get(i).getStatusprocesso().equalsIgnoreCase("Transaction")){
				numeroTransaction = numeroTransaction+1;
				listaControleTransaction.add(listaControle.get(i));
			} 
		}
	} 
	
	public void receberComissaoEX(Controleaupair controleaupair){
		if(controleaupair.isComissaopaga()){
			controleaupair.setComissaopaga(false);
		}else controleaupair.setComissaopaga(true);
		AupairFacade aupairFacade = new AupairFacade(); 
		controleaupair = aupairFacade.salvar(controleaupair);
	}
	
	public boolean desabilitarComissaoEX(Controleaupair controle){
		if(controle.getVendas().getFornecedorcidade().getCidade().getPais().getIdpais()==13){
			return false;
		}else return true;
	}
	
	public String imagemSituacaoChildTraining(Controleaupair controle){
		if(controle.isChildtraining()){
			return "../../resources/img/treinamentoOK.png";
		}else return "../../resources/img/treinamentoNAO.png"; 
	}
	
	public void efetuarTreinamento(Controleaupair controleaupair){
		if(controleaupair.isChildtraining()){
			controleaupair.setChildtraining(false);
		}else controleaupair.setChildtraining(true);
		AupairFacade aupairFacade = new AupairFacade(); 
		controleaupair = aupairFacade.salvar(controleaupair);
	}
}
