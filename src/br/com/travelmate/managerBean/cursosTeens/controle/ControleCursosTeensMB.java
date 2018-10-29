package br.com.travelmate.managerBean.cursosTeens.controle;

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
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.ProgramasTeensFacede;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Controleprogramasteen;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class ControleCursosTeensMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Controleprogramasteen controleprogramasteen;
	private List<Controleprogramasteen> listaControle;
	private String nomeCliente;
	private Date iniDataEmbarque;
	private Date finalDataEmbarque;
	private String motivoCancelamento;
	private Unidadenegocio unidadenegocio;
	private Usuario usuario;
	private String situacao;
	private List<Usuario> listaConsultor;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private int idVenda;
	private String voltar;
	private int nFichasFinalizadas;
	private int nFichasAndamento;
	private int nFichaCancelada;
	private Integer nFichasFinanceiro;
	private String numeroFichas;
	private List<Controleprogramasteen> listaVendasFinalizada;
	private List<Controleprogramasteen> listaVendasAndamento;
	private List<Controleprogramasteen> listaVendasCancelada; 
	private List<Controleprogramasteen> listaVendasFinanceiro;



	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			listaControle = (List<Controleprogramasteen>) session.getAttribute("listaControle");
			session.removeAttribute("listaControle");
			if (listaControle == null || listaControle.size() == 0) {
				listarControle();
			}else {
				numeroFichas =  "" + String.valueOf(listaControle.size());
				gerarQuantidadesFichas();
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

	public Controleprogramasteen getControleprogramasteen() {
		return controleprogramasteen;
	}


	public void setControleprogramasteen(Controleprogramasteen controleprogramasteen) {
		this.controleprogramasteen = controleprogramasteen;
	}


	public List<Controleprogramasteen> getListaControle() {
		return listaControle;
	}


	public void setListaControle(List<Controleprogramasteen> listaControle) {
		this.listaControle = listaControle;
	}


	public String getNomeCliente() {
		return nomeCliente;
	}


	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
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


	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}


	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
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


	public int getIdVenda() {
		return idVenda;
	}


	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}
	
	
	
	public String getVoltar() {
		return voltar;
	}


	public void setVoltar(String voltar) {
		this.voltar = voltar;
	}


	public int getnFichasFinalizadas() {
		return nFichasFinalizadas;
	}


	public void setnFichasFinalizadas(int nFichasFinalizadas) {
		this.nFichasFinalizadas = nFichasFinalizadas;
	}


	public int getnFichasAndamento() {
		return nFichasAndamento;
	}


	public void setnFichasAndamento(int nFichasAndamento) {
		this.nFichasAndamento = nFichasAndamento;
	}


	public int getnFichaCancelada() {
		return nFichaCancelada;
	}


	public void setnFichaCancelada(int nFichaCancelada) {
		this.nFichaCancelada = nFichaCancelada;
	}


	public String getNumeroFichas() {
		return numeroFichas;
	}


	public void setNumeroFichas(String numeroFichas) {
		this.numeroFichas = numeroFichas;
	}


	public Integer getnFichasFinanceiro() {
		return nFichasFinanceiro;
	}


	public void setnFichasFinanceiro(Integer nFichasFinanceiro) {
		this.nFichasFinanceiro = nFichasFinanceiro;
	}


	public List<Controleprogramasteen> getListaVendasFinalizada() {
		return listaVendasFinalizada;
	}


	public void setListaVendasFinalizada(List<Controleprogramasteen> listaVendasFinalizada) {
		this.listaVendasFinalizada = listaVendasFinalizada;
	}


	public List<Controleprogramasteen> getListaVendasAndamento() {
		return listaVendasAndamento;
	}


	public void setListaVendasAndamento(List<Controleprogramasteen> listaVendasAndamento) {
		this.listaVendasAndamento = listaVendasAndamento;
	}


	public List<Controleprogramasteen> getListaVendasCancelada() {
		return listaVendasCancelada;
	}


	public void setListaVendasCancelada(List<Controleprogramasteen> listaVendasCancelada) {
		this.listaVendasCancelada = listaVendasCancelada;
	}


	public List<Controleprogramasteen> getListaVendasFinanceiro() {
		return listaVendasFinanceiro;
	}


	public void setListaVendasFinanceiro(List<Controleprogramasteen> listaVendasFinanceiro) {
		this.listaVendasFinanceiro = listaVendasFinanceiro;
	}


	public void listarControle() {
		ProgramasTeensFacede programasTeensFacede = new ProgramasTeensFacede();
		String sql;
		String data = Formatacao.SubtarirDatas(new Date(), 30, "yyyy/MM/dd");
		sql = "select c from Controleprogramasteen c where c.situacao<>'Finalizado' and c.situacao<>'Cancelado' and c.vendas.dataVenda>='" + data + "' order by c.vendas.dataVenda desc";
		listaControle = programasTeensFacede.listaControle(sql);
		if (listaControle == null) {
			listaControle = new ArrayList<Controleprogramasteen>();
		}
		numeroFichas =  "" + String.valueOf(listaControle.size());
		gerarQuantidadesFichas();
	}
	
	
	
	
	public String invoice(Controleprogramasteen controle) {
		if (controle != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			controle.getVendas().setIdControle(controle.getIdcontroleProgramasTeens());
			session.setAttribute("vendas", controle.getVendas());
			session.setAttribute("voltar", "controleTeens");
			return "consultaInvoice";
		}
		return "";
	}
  
	public void pesquisar() {
		ProgramasTeensFacede programasTeensFacede = new ProgramasTeensFacede();
		String sql;
		sql = "select c from Controleprogramasteen c where c.vendas.cliente.nome like '%" + nomeCliente
				+ "%'  ";
		if(idVenda>0){
			sql= sql+ " and c.vendas.idvendas="+idVenda;
		}
		if (unidadenegocio!=null){ 
			sql = sql + " and  c.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		if (usuario!=null && usuario.getIdusuario()!=null){ 
			sql = sql + " and  c.vendas.usuario.idusuario=" + usuario.getIdusuario();
		}
		if ((iniDataEmbarque != null) && (finalDataEmbarque != null)) {
			sql = sql + " and c.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(iniDataEmbarque) + "'";
			sql = sql + " and c.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(finalDataEmbarque) + "'";
		}
		if(!situacao.equalsIgnoreCase("TODOS")){
			sql = sql + " and c.situacao='"+situacao+"'";
		}
		sql = sql + " order by c.idcontroleProgramasTeens";
		listaControle = programasTeensFacede.listaControle(sql);
		if (listaControle == null) {
			listaControle = new ArrayList<Controleprogramasteen>();
		}
		numeroFichas =  "" + String.valueOf(listaControle.size());
		gerarQuantidadesFichas();
	}

	public void limpar() {
		iniDataEmbarque = null;
		finalDataEmbarque = null;
		nomeCliente = "";
		idVenda=0;
		unidadenegocio=null;
		usuario=null;
		situacao="TODOS";
		listarControle();
	}
	

	public void finalizar(Controleprogramasteen controle) {
		ProgramasTeensFacede programasTeensFacede = new ProgramasTeensFacede();
		controle.setSituacao("Finalizado");;
		programasTeensFacede.salvar(controle);
		listarControle();
	}
	
	public String documentacao(Controleprogramasteen controle) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendas", controle.getVendas());
		voltar = "controleTeens";
		session.setAttribute("voltar", voltar);
		session.setAttribute("listaControle", listaControle);
		return "consArquivos";
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
	
	
	public void gerarListaUnidadeNegocio(){
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
        if (listaUnidadeNegocio==null){
            listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
        }
    }
	
	
	public String atualizarInformacoes(Controleprogramasteen controle) {
		if (controle != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("controle", controle);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 700);
			RequestContext.getCurrentInstance().openDialog("atualizarControleCursosTeens", options, null);
		}
		return "";
	}
	
	public String documentosTeens(Controleprogramasteen controle){
		if (controle != null) {
			if(controle.getVisto().equalsIgnoreCase("Não")){
				return "../../resources/img/bolaVermelha.png";
			}else if(controle.getFamilia().equalsIgnoreCase("Não")){
				return "../../resources/img/bolaVermelha.png";
			}else if(controle.getContrato().equalsIgnoreCase("Não")){
				return "../../resources/img/bolaVermelha.png";
			}else if(controle.getDocumentacao().equalsIgnoreCase("Não")){
				return "../../resources/img/bolaVermelha.png";
			}else{
				return "../../resources/img/bolaVerde.png";
			}
		}
		return "";
	}
	
	public String imagemSituacao(Controleprogramasteen controle){
		if (controle != null) {
			if(controle.getSituacao().equalsIgnoreCase("Processo")){
				return "../../resources/img/bolaAzul.png";
			}else if(controle.getSituacao().equalsIgnoreCase("Cancelado")){
				return "../../resources/img/bolaVermelha.png";
			}else if(controle.getSituacao().equalsIgnoreCase("Finalizado")){
				return "../../resources/img/bolaVerde.png";
			}else{
				return "";
			}
		}
		return "";
	}
	
	public String idadeCliente(Controleprogramasteen controle) {
		String retorno = "";
		if(controle.getVendas().getCliente().getDataNascimento()!=null){
			int idade = Formatacao.calcularIdade(controle.getVendas().getCliente().getDataNascimento());
			retorno = idade+" anos";
			return retorno;
		}
		return retorno;
	}
	
	public void retornoDialogAtualizar(SelectEvent event) {
		Controleprogramasteen controleprogramasteen = (Controleprogramasteen) event.getObject();
		documentosTeens(controleprogramasteen);
	}
	
	public void gerarQuantidadesFichas(){ 
		nFichaCancelada = 0;
		nFichasAndamento = 0;
		nFichasFinalizadas = 0; 
		nFichasFinanceiro = 0;
		listaVendasFinalizada = new ArrayList<>();
		listaVendasAndamento = new ArrayList<>();
		listaVendasCancelada = new ArrayList<>(); 
		listaVendasFinanceiro = new ArrayList<>();
		for (int i = 0; i < listaControle.size(); i++) {
			if (listaControle.get(i).getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
				nFichasFinalizadas = nFichasFinalizadas + 1;
				listaVendasFinalizada.add(listaControle.get(i));
			} else if(listaControle.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")){
				nFichasFinanceiro = nFichasFinanceiro + 1;
				listaVendasFinanceiro.add(listaControle.get(i));
			}else if(listaControle.get(i).getVendas().getSituacao().equalsIgnoreCase("CANCELADA")){
				nFichaCancelada = nFichaCancelada + 1;
				listaVendasCancelada.add(listaControle.get(i));
			}   
		}
	}
	
	public String imagemSituacaoFicha(Controleprogramasteen controleprogramasteen) {
		if (controleprogramasteen.getVendas().getSituacao().equals("FINALIZADA")) { 
			return "../../resources/img/finalizadoFicha.png";
		}else if (controleprogramasteen.getVendas().getSituacao().equals("ANDAMENTO")
       		 && !controleprogramasteen.getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")) { 
			return "../../resources/img/ficharestricao.png";
		}  else if (controleprogramasteen.getVendas().getSituacao().equals("ANDAMENTO")) { 
			return "../../resources/img/amarelaFicha.png";
		} else { 
			return "../../resources/img/fichaCancelada.png";
		} 
	}   
}
