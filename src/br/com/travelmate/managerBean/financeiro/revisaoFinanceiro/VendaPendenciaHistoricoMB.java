package br.com.travelmate.managerBean.financeiro.revisaoFinanceiro;

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

import br.com.travelmate.facade.VendaMotivoPendenciaFacade;
import br.com.travelmate.facade.VendaPendenciaFacade;
import br.com.travelmate.facade.VendaPendenciaHistoricoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Vendamotivopendencia;
import br.com.travelmate.model.Vendapendenciahistorico;
import br.com.travelmate.model.Vendas;


@Named
@ViewScoped
public class VendaPendenciaHistoricoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Vendas venda;
	private String tipo;
	private boolean habilitarCadastro = false;
    private List<Vendas> listaVendaPendente;
    private List<Vendas> listaVendaNova;
    private Vendapendenciahistorico vendapendenciahistorico;
    private List<Vendamotivopendencia> listaVendaMotivoPencencia;
    private Vendamotivopendencia vendamotivopendencia;
    private Date dataProximoContato;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		venda = (Vendas) session.getAttribute("venda");
		listaVendaPendente = (List<Vendas>) session.getAttribute("listaVendaPendente");
		listaVendaNova = (List<Vendas>) session.getAttribute("listaVendaNova");
		session.removeAttribute("venda");
		session.removeAttribute("listaVendaPendente");
		session.removeAttribute("listaVendaNova");
		gerarListaMotivoPendencia();
	}


	public Vendas getVenda() {
		return venda;
	}


	public void setVenda(Vendas venda) {
		this.venda = venda;
	}
	
	
	
	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public boolean isHabilitarCadastro() {
		return habilitarCadastro;
	}


	public void setHabilitarCadastro(boolean habilitarCadastro) {
		this.habilitarCadastro = habilitarCadastro;
	}


	public List<Vendas> getListaVendaPendente() {
		return listaVendaPendente;
	}


	public void setListaVendaPendente(List<Vendas> listaVendaPendente) {
		this.listaVendaPendente = listaVendaPendente;
	}


	public List<Vendas> getListaVendaNova() {
		return listaVendaNova;
	}


	public void setListaVendaNova(List<Vendas> listaVendaNova) {
		this.listaVendaNova = listaVendaNova;
	}


	public Vendapendenciahistorico getVendapendenciahistorico() {
		return vendapendenciahistorico;
	}


	public void setVendapendenciahistorico(Vendapendenciahistorico vendapendenciahistorico) {
		this.vendapendenciahistorico = vendapendenciahistorico;
	}


	public List<Vendamotivopendencia> getListaVendaMotivoPencencia() {
		return listaVendaMotivoPencencia;
	}


	public void setListaVendaMotivoPencencia(List<Vendamotivopendencia> listaVendaMotivoPencencia) {
		this.listaVendaMotivoPencencia = listaVendaMotivoPencencia;
	}


	public Vendamotivopendencia getVendamotivopendencia() {
		return vendamotivopendencia;
	}


	public void setVendamotivopendencia(Vendamotivopendencia vendamotivopendencia) {
		this.vendamotivopendencia = vendamotivopendencia;
	}


	public Date getDataProximoContato() {
		return dataProximoContato;
	}


	public void setDataProximoContato(Date dataProximoContato) {
		this.dataProximoContato = dataProximoContato;
	}


	public String voltarCadastroRF() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", venda);
		return "cadRevisaoFinanceiro";
	}
	
	public void cadastrarHistorico(){
		habilitarCadastro = true;
		vendapendenciahistorico = new Vendapendenciahistorico();
		
	}
	
	public void salvarHistorico(){
		VendaPendenciaHistoricoFacade vendaPendenciaHistoricoFacade = new VendaPendenciaHistoricoFacade();
		VendaPendenciaFacade vendaPendenciaFacade = new VendaPendenciaFacade();
		venda.getVendapendencia().setDataproximocontato(dataProximoContato);
		vendaPendenciaFacade.salvar(venda.getVendapendencia());
		vendapendenciahistorico.setVendapendencia(venda.getVendapendencia());
		vendapendenciahistorico.setUsuario(usuarioLogadoMB.getUsuario());
		vendapendenciahistorico.setDatahistorico(new Date());
		vendapendenciahistorico = vendaPendenciaHistoricoFacade.salvar(vendapendenciahistorico);
		if (venda.getVendapendencia().getVendapendenciahistoricoList() != null) {
			venda.getVendapendencia().getVendapendenciahistoricoList().add(vendapendenciahistorico);
		}
		habilitarCadastro = false;
	}  
	
	public void cancelarHistorico(){
		habilitarCadastro = false;
	}
	
	
	public void gerarListaMotivoPendencia(){
		VendaMotivoPendenciaFacade vendaMotivoPendenciaFacade = new VendaMotivoPendenciaFacade();
		listaVendaMotivoPencencia = vendaMotivoPendenciaFacade.lista("Select v From Vendamotivopendencia v");
		if (listaVendaMotivoPencencia == null) {
			listaVendaMotivoPencencia = new ArrayList<>();
		}
		
	}
	

}
