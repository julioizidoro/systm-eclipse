package br.com.travelmate.managerBean.financeiro.recebimento;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.jfree.data.time.Year;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.facade.RecinternacionalFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.model.Controlecurso;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Recinternacional;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class ConsRecebimentoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Recinternacional recinternacional;
	private List<Recinternacional> listaRecInternacional;
	private String nomeCliente;
	private int nVenda;
	private Date dataEnvInicial;
	private Date dataEnvFinal;
	private Date dataVencInicial;
	private Date dataVencFinal;
	private String situacao;
	private Usuario usuario;
	private Fornecedor fornecedor;
	private List<Fornecedor> listaFornecedor;
	private List<Usuario> listaUsuario;
	
	
	
	@PostConstruct
	public void init(){
		gerarListaFornecedor();
		gerarListaUsuario();
		gerarListaRecInternacional();
	}
	
	
	public Recinternacional getRecinternacional() {
		return recinternacional;
	}

	public void setRecinternacional(Recinternacional recinternacional) {
		this.recinternacional = recinternacional;
	}





	public List<Recinternacional> getListaRecInternacional() {
		return listaRecInternacional;
	}





	public void setListaRecInternacional(List<Recinternacional> listaRecInternacional) {
		this.listaRecInternacional = listaRecInternacional;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}


	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}


	public int getnVenda() {
		return nVenda;
	}


	public void setnVenda(int nVenda) {
		this.nVenda = nVenda;
	}


	public Date getDataEnvInicial() {
		return dataEnvInicial;
	}


	public void setDataEnvInicial(Date dataEnvInicial) {
		this.dataEnvInicial = dataEnvInicial;
	}


	public Date getDataEnvFinal() {
		return dataEnvFinal;
	}


	public void setDataEnvFinal(Date dataEnvFinal) {
		this.dataEnvFinal = dataEnvFinal;
	}


	public Date getDataVencInicial() {
		return dataVencInicial;
	}


	public void setDataVencInicial(Date dataVencInicial) {
		this.dataVencInicial = dataVencInicial;
	}


	public Date getDataVencFinal() {
		return dataVencFinal;
	}


	public void setDataVencFinal(Date dataVencFinal) {
		this.dataVencFinal = dataVencFinal;
	}


	public String getSituacao() {
		return situacao;
	}


	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	
	
	

	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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


	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}


	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}


	public String retornarIconeObs(Recinternacional recinternacional){
		if (recinternacional.getObservacao() == null) {
			return "../../resources/img/iconeObsVerde.png";
		}
		if (recinternacional.getObservacao().length() > 0) {
			return "../../resources/img/iconeObsVermelho.png";
		}else{
			return "../../resources/img/iconeObsVerde.png";
		}
	}
	
	
	
	public String abrirObsRecebimentos(Recinternacional recinternacional) throws SQLException {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("recinternacional", recinternacional);
		Map<String, Object> options = new HashMap<String, Object>();
	    options.put("contentWidth",500); 
	    options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("obsRecebimento", options, null);
		return "";
	}
	
	
	public String cadastroRecebimentos() throws SQLException {
		Map<String, Object> options = new HashMap<String, Object>();
	    options.put("contentWidth",550); 
	    options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("cadRecebimento", options, null);
		return "";
	}
	
	public String editarCadastroRecebimento(Recinternacional recinternacional) throws SQLException {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("recinternacional", recinternacional);
		Map<String, Object> options = new HashMap<String, Object>();
	    options.put("contentWidth",550); 
	    options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("cadRecebimento", options, null);
		return "";
	}
	
	
	public String recebimento(Recinternacional recinternacional) throws SQLException {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("recinternacional", recinternacional);
		Map<String, Object> options = new HashMap<String, Object>();
	    options.put("contentWidth",500); 
	    options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("recebimentoInternacional", options, null);
		return "";
	}
	
	
	public void retornoDialogNovo(SelectEvent event){
		Recinternacional recinternacional = (Recinternacional) event.getObject();
		if (recinternacional.getIdrecinternacional() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			listaRecInternacional.add(recinternacional);
		}
	}
	
	public void retornoDialogEdicao(SelectEvent event){
		Recinternacional recinternacional = (Recinternacional) event.getObject();
		if (recinternacional.getIdrecinternacional() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			gerarListaRecInternacional();
		}
	}
	
	public void retornoDialogRecebimento(SelectEvent event){
		Recinternacional recinternacional = (Recinternacional) event.getObject();
		if (recinternacional.getIdrecinternacional() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			gerarListaRecInternacional();
		}
	}
	
	
	
	public void gerarListaRecInternacional(){
		RecinternacionalFacade recinternacionalFacade = new RecinternacionalFacade();
		listaRecInternacional = recinternacionalFacade.listar("Select r From Recinternacional r Where r.datarecebimento is null");
		if (listaRecInternacional == null) {
			listaRecInternacional = new ArrayList<>();
		}else{
			for (int i = 0; i < listaRecInternacional.size(); i++) {
				if(listaRecInternacional.get(i).getVendas().getIdvendas()==1){
					listaRecInternacional.get(i).getVendas().getCliente().setNome("");
				}  
			}
		}
	} 
	
	
	public String retornarSituacao(Recinternacional recinternacional){
		if ((recinternacional.getDatavencimento().after(new Date()) || recinternacional.getDatavencimento().equals(new Date())) && recinternacional.getDatarecebimento() == null) {
			return "../../resources/img/bolaAmarela.png";
			
		} else if (recinternacional.getDatavencimento().before(new Date()) && recinternacional.getDatarecebimento() == null) {
			return "../../resources/img/bolaVermelha.png";
		}else if(recinternacional.getDatarecebimento() != null){
			return "../../resources/img/bolaVerde.png";
		}
		return "";
	}
	
	
	public void pesquisar(){
		RecinternacionalFacade recinternacionalFacade = new RecinternacionalFacade();
		String sql = "Select r From Recinternacional r Where r.vendas.cliente.nome like '%" + nomeCliente + "%'";
		
		if (nVenda > 0) {
			sql = sql + " and  r.vendas.idvendas=" + nVenda;
		}
		
		if (dataEnvInicial != null && dataEnvFinal != null) {
			sql = sql + " and r.dataenvio>='" + Formatacao.ConvercaoDataSql(dataEnvInicial) + "' and r.dataenvio<='" + Formatacao.ConvercaoDataSql(dataEnvFinal) + "'";
		}
		
		if (dataVencInicial != null && dataVencFinal != null) {
			sql = sql + " and r.datavencimento>='" + Formatacao.ConvercaoDataSql(dataVencInicial) + "' and r.datavencimento<='" + Formatacao.ConvercaoDataSql(dataVencFinal) + "'";
		}
		
		if (usuario != null) {
			if (usuario != null) {
				sql = sql + " and r.usuario.idusuario=" + usuario.getIdusuario();
			}
		}
		
		if (fornecedor != null) {
			if (fornecedor.getIdfornecedor() != null) {
				sql = sql + " and r.fornecedor.idfornecedor=" + fornecedor.getIdfornecedor();
			}
		}
		
		if (situacao != null) {
			if (situacao.equalsIgnoreCase("Vermelho")) {
				sql = sql + " and r.datavencimento<'" + Formatacao.ConvercaoDataSql(new Date()) + "' and r.datarecebimento is null";
			}else if (situacao.equalsIgnoreCase("Verde")){
				sql = sql + " and r.datarecebimento is not null";
			}else if(situacao.equalsIgnoreCase("Amarela")){
				sql = sql + " and r.datavencimento>='" + Formatacao.ConvercaoDataSql(new Date()) +"' and r.datarecebimento is null";
			}
		}
		
		listaRecInternacional = recinternacionalFacade.listar(sql);
	}
	
	public void gerarListaFornecedor(){
		FornecedorFacade fornecedorFacade = new FornecedorFacade();
		listaFornecedor = fornecedorFacade.listar("Select f From Fornecedor f Where f.idfornecedor>=1000 order by f.nome");
		if (listaFornecedor == null) {
			listaFornecedor = new ArrayList<>();
		}
	}
	
	
	public void gerarListaUsuario(){
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		listaUsuario = usuarioFacade.listar("Select u From Usuario u Where u.tipo='Gerencial' and u.situacao='Ativo' order by u.nome");
		if (listaUsuario == null) {
			listaUsuario = new ArrayList<>();
		}
	}
	
	
	public String relatorioInvoiceParceiro(Recinternacional recinternacional) throws SQLException {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("recinternacional", recinternacional);
		Map<String, Object> options = new HashMap<String, Object>();
	    options.put("contentWidth",500); 
	    options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("relatorioInvoiceParceiro", options, null);
		return "";
	}
	
	
	public String retornarIconeRelatorio(Recinternacional recinternacional){
		if (recinternacional.isRelatorio()) {
			return "../../resources/img/imprimirFicha.png";
		}else{
			return "../../resources/img/imprimirRelatorio.png";
		}
	} 
	
	

}
