package br.com.travelmate.managerBean.financeiro.recebimento;

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

import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.facade.MotivoRecInternacionalFacade;
import br.com.travelmate.facade.RecinternacionalFacade;
import br.com.travelmate.facade.UsuarioFacade;

import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Motivorecinternacional;
import br.com.travelmate.model.Recinternacional;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadRecebimentosMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	private Vendas vendas;
	private int nVenda;
	private Usuario usuario;
	private List<Usuario> listaUsuario;
	private Fornecedor fornecedor;
	private List<Fornecedor> listaFornecedor;
	private Moedas moeda;
	private List<Moedas> listaMoedas;
	private Recinternacional recInternacional;
	private Motivorecinternacional motivorecinternacional;
	private List<Motivorecinternacional> listaMotivoRecInternacional;
	private String nomeCliente;
	private String nomeProduto;
	
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        recInternacional = (Recinternacional) session.getAttribute("recinternacional");
        session.removeAttribute("recinternacional");
		gerarListaUsuario();
		gerarListaFornecedor();
		gerarListaMoedas();
		gerarListaMotivoRecInternacional();
		if (recInternacional == null) {
			recInternacional = new Recinternacional();
			recInternacional.setDataenvio(new Date());
		}else{
			moeda = recInternacional.getMoedas();
			fornecedor = recInternacional.getFornecedor();
			usuario = recInternacional.getUsuario();
			vendas = recInternacional.getVendas();
			nVenda = vendas.getIdvendas();
			motivorecinternacional = recInternacional.getMotivorecinternacional();
			nomeCliente = recInternacional.getVendas().getCliente().getNome();
			nomeProduto = recInternacional.getVendas().getProdutos().getDescricao();
		}
	}



	public Vendas getVendas() {
		return vendas;
	}



	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}



	public int getnVenda() {
		return nVenda;
	}



	public void setnVenda(int nVenda) {
		this.nVenda = nVenda;
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



	public Fornecedor getFornecedor() {
		return fornecedor;
	}



	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}



	public List<Fornecedor> getListaFornecedor() {
		return listaFornecedor;
	}



	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}



	public Moedas getMoeda() {
		return moeda;
	}



	public void setMoeda(Moedas moeda) {
		this.moeda = moeda;
	}



	public List<Moedas> getListaMoedas() {
		return listaMoedas;
	}



	public void setListaMoedas(List<Moedas> listaMoedas) {
		this.listaMoedas = listaMoedas;
	}
	
	
	
	public Recinternacional getRecInternacional() {
		return recInternacional;
	}



	public void setRecInternacional(Recinternacional recInternacional) {
		this.recInternacional = recInternacional;
	}



	public Motivorecinternacional getMotivorecinternacional() {
		return motivorecinternacional;
	}



	public void setMotivorecinternacional(Motivorecinternacional motivorecinternacional) {
		this.motivorecinternacional = motivorecinternacional;
	}

	

	public List<Motivorecinternacional> getListaMotivoRecInternacional() {
		return listaMotivoRecInternacional;
	}



	public void setListaMotivoRecInternacional(List<Motivorecinternacional> listaMotivoRecInternacional) {
		this.listaMotivoRecInternacional = listaMotivoRecInternacional;
	}


	public String getNomeCliente() {
		return nomeCliente;
	}



	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}



	public String getNomeProduto() {
		return nomeProduto;
	}



	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}



	public void gerarListaUsuario(){
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		listaUsuario = usuarioFacade.listar("Select u From Usuario u Where u.tipo='Gerencial' and u.situacao='Ativo'");
		if (listaUsuario == null) {
			listaUsuario = new ArrayList<>();
		}
	}
	
	
	
	public void gerarListaMoedas(){
		CambioFacade cambioFacade = new CambioFacade();
		listaMoedas = cambioFacade.listaMoedas();
		if (listaMoedas == null) {
			listaMoedas = new ArrayList<>();
		}
	}
	
	
	public void gerarListaFornecedor(){
		FornecedorFacade fornecedorFacade = new FornecedorFacade();
		listaFornecedor = fornecedorFacade.listar("Select f From Fornecedor f Where f.idfornecedor>=1000 order by f.nome");
		if (listaFornecedor == null) {
			listaFornecedor = new ArrayList<>();
		}
	}

	
	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Recinternacional());
	}
	
	
	public boolean validarDados(){
		if (moeda == null) {
			Mensagem.lancarMensagemInfo("Informe a moeda", "");
			return false;
		}
		if (fornecedor == null) {
			Mensagem.lancarMensagemInfo("Informe o fornecedor", "");
			return false;
		}
		if (usuario == null) {
			Mensagem.lancarMensagemInfo("Informe o responsável", "");
			return false;
		}
		if (vendas == null) {
			Mensagem.lancarMensagemInfo("Informe a venda", "");
			return false;
		}
		if (motivorecinternacional == null) {
			Mensagem.lancarMensagemInfo("Informe o motivo", "");
			return false;
		}
		if (recInternacional.getDatavencimento() == null) {
			Mensagem.lancarMensagemInfo("Informe a data de vencimento", "");
			return false;
		}
		return true;
	}
	
	
	public void salvar(){
		RecinternacionalFacade recinternacionalFacade = new RecinternacionalFacade();
		BancoFacade bancoFacade = new BancoFacade();
		Banco banco = bancoFacade.getBanco("SELECT b FROM Banco b where b.idbanco=12");
		recInternacional.setBanco(banco);
		recInternacional.setFornecedor(fornecedor);
		recInternacional.setUsuario(usuario);
		recInternacional.setMoedas(moeda);
		recInternacional.setMotivorecinternacional(motivorecinternacional);
		recInternacional.setVendas(vendas);
		if (validarDados()) {
			recInternacional = recinternacionalFacade.salvar(recInternacional);
			RequestContext.getCurrentInstance().closeDialog(recInternacional);
		}
	}
	
	
	public void buscarVenda(){
		
		vendas = vendasDao.consultarVendas(nVenda);
		if (vendas != null) {
			nomeCliente = vendas.getCliente().getNome();
			nomeProduto = vendas.getProdutos().getDescricao();
		}else{
			nomeCliente = "";
			nomeProduto = "";
		}
	}
	
	
	public void gerarListaMotivoRecInternacional(){
		MotivoRecInternacionalFacade motivoRecInternacionalFacade = new MotivoRecInternacionalFacade();
		listaMotivoRecInternacional = motivoRecInternacionalFacade.listar("Select m From Motivorecinternacional m");
		if (listaMotivoRecInternacional == null) {
			listaMotivoRecInternacional = new ArrayList<>();
		}
	}
	
	
	
}
