package br.com.travelmate.managerBean.controleAlteracoes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.travelmate.facade.ControleAlteracoesFacade;
import br.com.travelmate.facade.DadosAlteracoesFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Controlealteracoes;
import br.com.travelmate.model.Dadosalteracoes;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class ControleAlteracoesMB implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Controlealteracoes> listaVendasAlteradas;
	private Controlealteracoes controlealteracoes;
	private String nomeCliente="";
	private Date dataInicio;
	private Date dataFinal;
	private Unidadenegocio unidade;
	private int idVenda;
	private List<Unidadenegocio> listaUnidade;
	private List<Usuario> listaUsuario;
	private Usuario usuario;
	private boolean librarComboUsuairo = true;
	private List<Dadosalteracoes> listaDadosAlterado;
	private String situacao="Em aberto";
	
	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			listarUnidade();
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	
	public List<Controlealteracoes> getListaAlteracoes() {
		return listaVendasAlteradas;
	}

	public void setListaAlteracoes(List<Controlealteracoes> listaAlteracoes) {
		this.listaVendasAlteradas = listaAlteracoes;
	}

	public Controlealteracoes getControlealteracoes() {
		return controlealteracoes;
	}

	public void setControlealteracoes(Controlealteracoes controlealteracoes) {
		this.controlealteracoes = controlealteracoes;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Unidadenegocio getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidadenegocio unidade) {
		this.unidade = unidade;
	}

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}

	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}
	
	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isLibrarComboUsuairo() {
		return librarComboUsuairo;
	}

	public void setLibrarComboUsuairo(boolean librarComboUsuairo) {
		this.librarComboUsuairo = librarComboUsuairo;
	}

	public List<Dadosalteracoes> getListaDadosAlterado() {
		return listaDadosAlterado;
	}

	public void setListaDadosAlterado(List<Dadosalteracoes> listaDadosAlterado) {
		this.listaDadosAlterado = listaDadosAlterado;
	}

	public List<Controlealteracoes> getListaVendasAlteradas() {
		return listaVendasAlteradas;
	}

	public void setListaVendasAlteradas(List<Controlealteracoes> listaVendasAlteradas) {
		this.listaVendasAlteradas = listaVendasAlteradas;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public void listarUnidade() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidade = unidadeNegocioFacade.listar();
	}
	
	public void listarUsuario() {
		if (unidade != null) {
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			listaUsuario = usuarioFacade.listaUsuarioUnidade(unidade.getIdunidadeNegocio());
		} else {
			listaUsuario = new ArrayList<Usuario>();
		}
	}

	public void liberarComboUsuario() {
		if (unidade != null) {
			librarComboUsuairo = false;
			listarUsuario();
		} else {
			librarComboUsuairo = true;
			listaUsuario = new ArrayList<Usuario>();
		}
	}
	
	public void listarAlteracoes(){
		String sql;
		ControleAlteracoesFacade controleAlteracoesFacade = new ControleAlteracoesFacade();
		sql = "Select c from Controlealteracoes c where c.vendas.situacao='FINALIZADA' and c.vendas.cliente.nome like '%" + nomeCliente + "%'";
		if(!situacao.equalsIgnoreCase("Em aberto")){
			sql = sql+" and c.verificado=true";
		}else{
			sql = sql+" and c.verificado=false";
		}
		if(usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()!=3 && usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()!=1){
			sql = sql+" and c.departamentoproduto.departamento.iddepartamento="+usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento();
		}
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")){
	    	if(unidade!=null && unidade.getIdunidadeNegocio()!=null)	{
	    		sql = sql + " and c.vendas.unidadenegocio.idunidadeNegocio=" + unidade.getIdunidadeNegocio();
	    	}
	    	if(usuario!=null && usuario.getIdusuario()!=null)	{
	    		sql = sql + " and c.vendas.usuario.idusuario=" + usuario.getIdusuario();
	    	}
	    }else {
	    	sql = sql + " and c.vendas.unidadenegocio.idunidadeNegocio=" + usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
	    }
		if (idVenda>0){
	    	sql = sql + " and c.vendas.idvendas=" + idVenda;
	    }
		if((dataInicio!=null) && (dataFinal!=null)){
    		sql = sql + " and c.dataalteracao>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
    		sql = sql + " and c.dataalteracao<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
    	}else {
			String dataConsulta = Formatacao.SubtarirDatas(new Date(), 365, "yyyy-MM-dd");
    		sql = sql + " and c.dataalteracao>='" + dataConsulta + "'";
    	}
		sql= sql + " order by c.dataalteracao desc, c.vendas.cliente.nome";
		listaVendasAlteradas = controleAlteracoesFacade.listar(sql);
		if(listaVendasAlteradas==null){
			listaVendasAlteradas= new ArrayList<Controlealteracoes>();
		}else{
			for (int i = 0; i < listaVendasAlteradas.size(); i++) {
				DadosAlteracoesFacade dadosAlteracoesFacade = new DadosAlteracoesFacade();
				sql ="Select d from Dadosalteracoes d where d.verificado=false and d.controlealteracoes.idcontrolealteracoes="+listaVendasAlteradas.get(i).getIdcontrolealteracoes();
				List<Dadosalteracoes> lista = dadosAlteracoesFacade.listar(sql);
				if(lista==null || lista.size()==0){
					listaVendasAlteradas.remove(i);
				}
			} 
		}
	}	
	
	public void limpar(){
		nomeCliente="";
		unidade=null;
		usuario=null;
		dataFinal=null;
		dataInicio=null;
		idVenda=0;
		listaVendasAlteradas = new ArrayList<>();
	}
	
	public void listarDadosAlterado(){
		String sql;
		DadosAlteracoesFacade dadosAlteracoesFacade = new DadosAlteracoesFacade();
		sql ="Select d from Dadosalteracoes d where d.verificado=false and d.controlealteracoes.idcontrolealteracoes="+controlealteracoes.getIdcontrolealteracoes();
		listaDadosAlterado = dadosAlteracoesFacade.listar(sql);
	}
	
	
	public void verificarDados(Dadosalteracoes dadosalteracoes){
		if(dadosalteracoes.getDepartamento().getIddepartamento()==usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()){
			dadosalteracoes.setVerificado(true);
			DadosAlteracoesFacade dadosAlteracoesFacade = new DadosAlteracoesFacade();
			dadosalteracoes = dadosAlteracoesFacade.salvar(dadosalteracoes);
			listaDadosAlterado.remove(dadosalteracoes);
			if(verificarControleAlteracao()){
				ControleAlteracoesFacade controleAlteracoesFacade = new ControleAlteracoesFacade();
				Controlealteracoes controlealteracoes = dadosalteracoes.getControlealteracoes();
				controlealteracoes.setVerificado(true);
				controlealteracoes = controleAlteracoesFacade.salvar(controlealteracoes);
				listaVendasAlteradas.remove(dadosalteracoes.getControlealteracoes());
			}
		}else{
			Mensagem.lancarMensagemWarn("Atenção!", "Está alteração não pertence ao seu departamento.");
		}
	}
	
	public boolean verificarControleAlteracao(){
		for (int i = 0; i < listaDadosAlterado.size(); i++) {
			if(!listaDadosAlterado.get(i).isVerificado()){
				return false;
			}
		}
		return true;
	}
}
