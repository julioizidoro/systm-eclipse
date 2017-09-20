package br.com.travelmate.managerBean.highschool.controle;

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

import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.HighSchoolFacade;
import br.com.travelmate.facade.InvoiceFacade; 
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Controlehighschool;
import br.com.travelmate.model.Controleprogramasteen;
import br.com.travelmate.model.Invoice; 
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class ControleHighSchoolMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Controlehighschool controlehighschool;
	private List<Controlehighschool> listaControle;
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
	private List<Controlehighschool> listaVendasFinalizada;
	private List<Controlehighschool> listaVendasAndamento;
	private List<Controlehighschool> listaVendasCancelada; 
	private List<Controlehighschool> listaVendasFinanceiro;
	private String ano;
	private String semestre;
	 
	@PostConstruct
	public void init() {
		listarControle();
		gerarListaUnidadeNegocio();
	}


	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}


	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}


	public Controlehighschool getControlehighschool() {
		return controlehighschool;
	}


	public void setControlehighschool(Controlehighschool controlehighschool) {
		this.controlehighschool = controlehighschool;
	}


	public List<Controlehighschool> getListaControle() {
		return listaControle;
	}


	public void setListaControle(List<Controlehighschool> listaControle) {
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


	public String getAno() {
		return ano;
	}


	public void setAno(String ano) {
		this.ano = ano;
	}


	public String getSemestre() {
		return semestre;
	}


	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}


	public Integer getnFichasFinanceiro() {
		return nFichasFinanceiro;
	}


	public void setnFichasFinanceiro(Integer nFichasFinanceiro) {
		this.nFichasFinanceiro = nFichasFinanceiro;
	}


	public List<Controlehighschool> getListaVendasFinalizada() {
		return listaVendasFinalizada;
	}


	public void setListaVendasFinalizada(List<Controlehighschool> listaVendasFinalizada) {
		this.listaVendasFinalizada = listaVendasFinalizada;
	}


	public List<Controlehighschool> getListaVendasAndamento() {
		return listaVendasAndamento;
	}


	public void setListaVendasAndamento(List<Controlehighschool> listaVendasAndamento) {
		this.listaVendasAndamento = listaVendasAndamento;
	}


	public List<Controlehighschool> getListaVendasCancelada() {
		return listaVendasCancelada;
	}


	public void setListaVendasCancelada(List<Controlehighschool> listaVendasCancelada) {
		this.listaVendasCancelada = listaVendasCancelada;
	}


	public List<Controlehighschool> getListaVendasFinanceiro() {
		return listaVendasFinanceiro;
	}


	public void setListaVendasFinanceiro(List<Controlehighschool> listaVendasFinanceiro) {
		this.listaVendasFinanceiro = listaVendasFinanceiro;
	}


	public void listarControle() {
		HighSchoolFacade highSchoolFacade = new HighSchoolFacade();
		String sql;
		String data = Formatacao.SubtarirDatas(new Date(), 30, "yyyy/MM/dd");
		sql = "select c from Controlehighschool c where c.situacao<>'Finalizado' and c.situacao<>'Cancelado' and c.vendas.dataVenda>='" + data + "' order by c.vendas.dataVenda desc";
		listaControle = highSchoolFacade.listaControle(sql);
		if (listaControle == null) {
			listaControle = new ArrayList<Controlehighschool>();
		}
		numeroFichas =  "" + String.valueOf(listaControle.size());
		gerarQuantidadesFichas();
	}
	
	
	public String invoice(Controlehighschool controle) {
		if (controle != null) {
			InvoiceFacade invoiceFacade = new InvoiceFacade();
			Invoice invoice = invoiceFacade.consultarVenda(controle.getVendas().getIdvendas(),
					controle.getVendas().getProdutos().getIdprodutos(), controle.getIdcontroleHighSchool());
			if (invoice == null) {
				invoice = new Invoice();
				invoice.setControle(controle.getIdcontroleHighSchool());
				invoice.setProdutos(controle.getVendas().getProdutos());
				invoice.setVendas(controle.getVendas());
			}
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("invoice", invoice);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 600);
			RequestContext.getCurrentInstance().openDialog("invoiceControle", options, null);
		}
		return "";
	}
  
	public void pesquisar() {
		HighSchoolFacade highSchoolFacade = new HighSchoolFacade();
		String sql;
		sql = "select c from Controlehighschool c where c.vendas.cliente.nome like '%" + nomeCliente
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
			sql = sql + " and c.vendas.situacao='"+situacao+"'";
		}
		if(!ano.equalsIgnoreCase("sn")){
			sql = sql + " and c.highschool.valoreshighschool.anoinicio='"+ano+"'";
		}
		if(!semestre.equalsIgnoreCase("sn")){
			sql = sql + " and c.highschool.dataInicio='"+semestre+"'";
		}
		sql = sql + " order by c.idcontroleHighSchool";
		listaControle = highSchoolFacade.listaControle(sql);
		if (listaControle == null) {
			listaControle = new ArrayList<Controlehighschool>();
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
	

	public void salvarMudancas(Controlehighschool controle) {
		HighSchoolFacade highSchoolFacade = new HighSchoolFacade(); 
		highSchoolFacade.salvar(controle);
		highSchoolFacade.salvar(controle.getHighschool());
		ClienteFacade clienteFacade = new ClienteFacade();
		clienteFacade.salvar(controle.getVendas().getCliente());
		Mensagem.lancarMensagemInfo("Alterado com sucesso!", "");
		listarControle();
	}
	
	public String documentacao(Controlehighschool controle) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendas", controle.getVendas());
		voltar = "controleHighSchool";
		session.setAttribute("voltar", voltar);
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
        listaUnidadeNegocio = unidadeNegocioFacade.listar();
        if (listaUnidadeNegocio==null){
            listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
        }
    }
	
	
	public String atualizarInformacoes(Controlehighschool controle) {
		if (controle != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("controle", controle);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 700);
			RequestContext.getCurrentInstance().openDialog("atualizarControleHighSchool", options, null);
		}
		return "";
	}
	
	public String documentosHighSchool(Controlehighschool controle){
		if(controle.getVisto().equalsIgnoreCase("Não")){
			return "../../resources/img/bolaVermelha.png";
		}else if(controle.getFamilia().equalsIgnoreCase("Não")){
			return "../../resources/img/bolaVermelha.png"; 
		}else{
			return "../../resources/img/bolaVerde.png";
		}
	}
	
	public String imagemSituacao(Controlehighschool controle){
		if(controle.getSituacao().equalsIgnoreCase("Processo")){
			return "../../resources/img/bolaAzul.png";
		}else if(controle.getSituacao().equalsIgnoreCase("Cancelado")){
			return "../../resources/img/bolaVermelha.png";
		}else if(controle.getSituacao().equalsIgnoreCase("Finalizado")){
			return "../../resources/img/bolaVerde.png";
		}else{
			return null;
		}
	}
	
	public String idadeCliente(Controlehighschool controle) {
		String retorno = "";
		if(controle.getVendas().getCliente().getDataNascimento()!=null){
			int idade = Formatacao.calcularIdade(controle.getVendas().getCliente().getDataNascimento());
			retorno = idade+" anos";
			return retorno;
		}
		return retorno;
	}
	
	public void retornoDialogAtualizar(SelectEvent event) {
		Controlehighschool controlehighschool = (Controlehighschool) event.getObject();
		documentosHighSchool(controlehighschool);
	}
	
	public void gerarQuantidadesFichas(){ 
		nFichasAndamento = 0;
		nFichasFinalizadas = 0; 
		nFichaCancelada=0;
		nFichasFinanceiro = 0;
		listaVendasFinalizada = new ArrayList<>();
		listaVendasAndamento = new ArrayList<>();
		listaVendasCancelada = new ArrayList<>(); 
		listaVendasFinanceiro = new ArrayList<>();
		for (int i = 0; i < listaControle.size(); i++) {
			if (listaControle.get(i).getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
				nFichasFinalizadas = nFichasFinalizadas + 1;
				listaVendasFinalizada.add(listaControle.get(i));
			} else if(listaControle.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")
					&& !listaControle.get(i).getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")){
				nFichasFinanceiro = nFichasFinanceiro + 1;
				listaVendasFinanceiro.add(listaControle.get(i));
			}else if(listaControle.get(i).getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")){
				nFichasAndamento = nFichasAndamento + 1;
				listaVendasAndamento.add(listaControle.get(i));
			}else if(listaControle.get(i).getVendas().getSituacao().equalsIgnoreCase("CANCELADA")){
				nFichaCancelada = nFichaCancelada + 1;
				listaVendasCancelada.add(listaControle.get(i));
			}   
		}
	}
	
	public String imagemSituacaoFicha(Controlehighschool controlehighschool) {
		if (controlehighschool.getVendas().getSituacao().equals("FINALIZADA")) { 
			return "../../resources/img/finalizadoFicha.png";
		}else if (controlehighschool.getVendas().getSituacao().equals("ANDAMENTO")
       		 && !controlehighschool.getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")) { 
			return "../../resources/img/ficharestricao.png";
		} else if (controlehighschool.getVendas().getSituacao().equals("ANDAMENTO")) { 
			return "../../resources/img/amarelaFicha.png";
		} else { 
			return "../../resources/img/fichaCancelada.png";
		} 
	}   
	
	
}
