package br.com.travelmate.managerBean.financeiro.vendas;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.travelmate.bean.DashBoardBean;
import br.com.travelmate.dao.PaisDao;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;

import br.com.travelmate.facade.PublicidadeFacade;
import br.com.travelmate.facade.UsuarioFacade;

import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.DashBoardMB;
import br.com.travelmate.managerBean.MateRunnersMB;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Publicidade;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;

@Named
@ViewScoped
public class VendaAvulsaMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	
	@Inject
	private PaisDao paisDao;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private DashBoardMB dashBoardMB;
	@Inject
	private MateRunnersMB metaRunnersMB;
	private Pais pais;
	private List<Pais> listaPais;
	private Cidade cidade;
	private List<Cidade> listaCidade;
	private Fornecedorcidade fornecedorCidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Cliente cliente;
	private Vendas vendas;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Usuario usuario;
	private List<Usuario> listaUsuario;
	private Produtos produtos;
	private List<Produtos> listaProdutos;
	private Unidadenegocio unidadeNegocio;
	
	@PostConstruct()
	public void init() {
		vendas = new  Vendas();
		cliente = new Cliente();
		unidadeNegocio = new Unidadenegocio();
		usuario = new Usuario();
		produtos = new Produtos();
		pais = new Pais();
		fornecedorCidade =  new Fornecedorcidade();
		cidade  =new Cidade();
		
		listaPais = paisDao.listar();
		listaUnidadeNegocio = GerarListas.listarUnidade();
		listaProdutos = GerarListas.listarProdutos("");
		carregarListaUsuario();
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
	public List<Cidade> getListaCidade() {
		return listaCidade;
	}
	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}
	public Fornecedorcidade getFornecedorCidade() {
		return fornecedorCidade;
	}
	public void setFornecedorCidade(Fornecedorcidade fornecedorCidade) {
		this.fornecedorCidade = fornecedorCidade;
	}
	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}
	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}

	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
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

	public Produtos getProdutos() {
		return produtos;
	}

	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
	}

	public List<Produtos> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produtos> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public Unidadenegocio getUnidadeNegocio() {
		return unidadeNegocio;
	}

	public void setUnidadeNegocio(Unidadenegocio unidadeNegocio) {
		this.unidadeNegocio = unidadeNegocio;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public DashBoardMB getDashBoardMB() {
		return dashBoardMB;
	}

	public void setDashBoardMB(DashBoardMB dashBoardMB) {
		this.dashBoardMB = dashBoardMB;
	}

	public MateRunnersMB getMetaRunnersMB() {
		return metaRunnersMB;
	}

	public void setMetaRunnersMB(MateRunnersMB metaRunnersMB) {
		this.metaRunnersMB = metaRunnersMB;
	}

	public void listarFornecedorCidade() {
		if (cidade != null) {
			String sql = "select f from Fornecedorcidade f where f.cidade.idcidade=" + cidade.getIdcidade() 
			+" and f.ativo=1";
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			listaFornecedorCidade = fornecedorCidadeFacade.listar(sql);
			if (listaFornecedorCidade == null) {
				listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
			}
		}
	}
	
	public void carregarListaUsuario(){
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		listaUsuario = usuarioFacade.consultar("Select u from Usuario  u where u.situacao='Ativo' order by u.nome");
		if (listaUsuario == null) {
			listaUsuario = new ArrayList<Usuario>();
		}
	}
	
	
	public String salvar() throws SQLException{
		ClienteFacade clienteFacade = new ClienteFacade();
		cliente.setUnidadenegocio(unidadeNegocio);
		cliente.setAvulsa(1);
		PublicidadeFacade publicidadeFacade = new PublicidadeFacade();
		Publicidade publicidade = new Publicidade();
		publicidade = publicidadeFacade.consultar(1);
		cliente.setPublicidade(publicidade);
		cliente = clienteFacade.salvar(cliente);
		vendas.setCambio(aplicacaoMB.getListaCambio().get(0));
		vendas.setCliente(cliente);
		vendas.setUsuario(usuario);
		vendas.setProdutos(produtos);
		vendas.setFornecedor(fornecedorCidade.getFornecedor());
		vendas.setFornecedorcidade(fornecedorCidade);
		vendas.setSituacao("FINALIZADA");
		vendas.setUsuariocancelamento(0);
		vendas.setVendasMatriz("S");
		vendas.setUnidadenegocio(unidadeNegocio);
		
		vendas = vendasDao.salvar(vendas);
		
		if (vendas.getProdutos().getIdprodutos() == 1 || vendas.getProdutos().getIdprodutos() == 4
				|| vendas.getProdutos().getIdprodutos() == 5 || vendas.getProdutos().getIdprodutos() == 9
				|| vendas.getProdutos().getIdprodutos() == 10 || vendas.getProdutos().getIdprodutos() == 22
				|| vendas.getProdutos().getIdprodutos() == 13 || vendas.getProdutos().getIdprodutos() == 16
				|| vendas.getProdutos().getIdprodutos() == 20) {
			dashBoardMB.getVendaproduto()
			.setIntercambio(dashBoardMB.getVendaproduto().getIntercambio() + 1);
		} else if (vendas.getProdutos().getIdprodutos() == 7) {
			dashBoardMB.getVendaproduto()
			.setTurismo(dashBoardMB.getVendaproduto().getTurismo() + 1);
		} else if (vendas.getProdutos().getIdprodutos() == 2 || vendas.getProdutos().getIdprodutos() == 3
				|| vendas.getProdutos().getIdprodutos() == 6) {
			dashBoardMB.getVendaproduto()
			.setProduto(dashBoardMB.getVendaproduto().getProduto() + 1);
		}
		
		dashBoardMB.getMetamensal()
				.setValoralcancado(dashBoardMB.getMetamensal().getValoralcancado() + vendas.getValor());
		dashBoardMB.getMetamensal()
				.setPercentualalcancado((dashBoardMB.getMetamensal().getValoralcancado()
						/ dashBoardMB.getMetamensal().getValormeta()) * 100);
		
		dashBoardMB.getMetaAnual()
				.setMetaalcancada(dashBoardMB.getMetaAnual().getMetaalcancada() + vendas.getValor());
		dashBoardMB.getMetaAnual().setPercentualalcancado((dashBoardMB.getMetaAnual().getMetaalcancada()
				/ dashBoardMB.getMetaAnual().getValormeta()) * 100);
		
		dashBoardMB.setMetaparcialsemana(dashBoardMB.getMetaparcialsemana() + vendas.getValor());
		dashBoardMB.setPercsemana(
				(dashBoardMB.getMetaparcialsemana() / dashBoardMB.getMetamensal().getValormetasemana())
						* 100);
		
		float valor=dashBoardMB.getMetamensal().getValoralcancado();
		dashBoardMB.setValorFaturamento(Formatacao.formatarFloatString(valor)); 
		
//		new Thread() {
//			@Override
//			public void run() {
				DashBoardBean dashBoardBean = new DashBoardBean();
				dashBoardBean.calcularNumeroVendasProdutos(vendas, false);
				dashBoardBean.calcularMetaMensal(vendas,0, false);
				dashBoardBean.calcularMetaAnual(vendas,0, false);
				dashBoardBean.calcularPontuacao(vendas, 0, "", false, vendas.getUsuario());
				metaRunnersMB.carregarListaRunners(); 
//			}
//		}.start();
		RequestContext.getCurrentInstance().closeDialog(vendas);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	
	
}
