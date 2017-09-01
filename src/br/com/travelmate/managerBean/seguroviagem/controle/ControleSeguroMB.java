package br.com.travelmate.managerBean.seguroviagem.controle;

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

import br.com.travelmate.facade.ControleSeguroFacade;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Controleseguro;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class ControleSeguroMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Controleseguro controleSeguro;
	private List<Controleseguro> listaControleSeguro;
	private String nomeCliente;
	private Date dataInicio;
	private Date dataFim;
	private int idVenda=0;
	private String tipoData;
	private Date dataEmissao; 
	private String numeroDiasString;
	private String motivoCancelamento;
	private List<Usuario> listaConsultor;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private Usuario usuario;
	private String tipoEmissao;
	private String numeroSeguroString;
	
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

	public Controleseguro getControleCurso() {
		return controleSeguro;
	}

	public void setControleCurso(Controleseguro controleCurso) {
		this.controleSeguro = controleCurso;
	}
	
	public List<Controleseguro> getListaControleSeguro() {
		return listaControleSeguro;
	}

	public void setListaControleSeguro(List<Controleseguro> listaControleSeguro) {
		this.listaControleSeguro = listaControleSeguro;
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

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}


	public Controleseguro getControleSeguro() {
		return controleSeguro;
	}

	public void setControleSeguro(Controleseguro controleSeguro) {
		this.controleSeguro = controleSeguro;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public String getNumeroDiasString() {
		return numeroDiasString;
	}

	public void setNumeroDiasString(String numeroDiasString) {
		this.numeroDiasString = numeroDiasString;
	}
 
	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}

	public String getTipoData() {
		return tipoData;
	}

	public void setTipoData(String tipoData) {
		this.tipoData = tipoData;
	}

	public String getTipoEmissao() {
		return tipoEmissao;
	}

	public void setTipoEmissao(String tipoEmissao) {
		this.tipoEmissao = tipoEmissao;
	}

	public String getNumeroSeguroString() {
		return numeroSeguroString;
	}

	public void setNumeroSeguroString(String numeroSeguroString) {
		this.numeroSeguroString = numeroSeguroString;
	}

	public void listarControle() {
		ControleSeguroFacade controleSeguroFacade =new ControleSeguroFacade();
		String sql = "select c from Controleseguro c where c.seguroviagem.possuiSeguro='Sim' "
				+ " and c.seguroviagem.dataInicio>='"+Formatacao.ConvercaoDataSql(new Date())
				+ "' and c.seguroviagem.vendas.situacao<>'CANCELADA' order by c.seguroviagem.dataInicio, c.idcontroleSeguro desc";
		listaControleSeguro = controleSeguroFacade.listar(sql);
		if (listaControleSeguro==null){
			listaControleSeguro = new ArrayList<Controleseguro>();
        }
		/*for(int i=0;i<listaControleSeguro.size();i++){
			CursoFacade cursoFacade = new CursoFacade();
			SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
			if (listaControleSeguro.get(i).getSeguroviagem().getControle()!=null){
			if (listaControleSeguro.get(i).getSeguroviagem().getControle().equalsIgnoreCase("Curso")){
				Curso curso = cursoFacade.consultarCursos(listaControleSeguro.get(i).getSeguroviagem().getVendas().getIdvendas());
				if (curso!=null){
					Seguroviagem seguro = listaControleSeguro.get(i).getSeguroviagem();
					seguro.setNomeContatoEmergencia(curso.getNomeContatoEmergencia());
					seguro.setFoneContatoEmergencia(curso.getFoneContatoEmergencia());
					seguro.setPaisDestino(curso.getPais());
					seguroViagemFacade.salvar(seguro);
				}
			}
		}
		}*/
		numeroDias();
	}
	
	
	public void pesquisar(){
		ControleSeguroFacade controleSeguroFacade =new ControleSeguroFacade();
		String sql;
		sql= "select c from Controleseguro c where c.seguroviagem.vendas.cliente.nome like '%" + nomeCliente + "%' and c.seguroviagem.vendas.situacao<>'CANCELADA' ";
		if(idVenda>0){
			sql = sql + " and c.seguroviagem.vendas.idvendas="+idVenda;
		}
		if (unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){ 
			sql = sql + " and c.seguroviagem.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		if (usuario!=null && usuario.getIdusuario()!=null){ 
			sql = sql + " and c.seguroviagem.vendas.usuario.idusuario=" + usuario.getIdusuario();
		}
		if ((dataInicio != null) && (dataFim != null)) {
			sql = sql + " and c.seguroviagem.dataInicio>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
			sql = sql + " and c.seguroviagem.dataInicio<='" + Formatacao.ConvercaoDataSql(dataFim) + "'";
		}
		if (tipoEmissao!=null && !tipoEmissao.equals("Todos")){
			sql = sql + " and c.seguroviagem.controle='" + tipoEmissao + "' ";
		}
		sql=sql+ " order by c.seguroviagem.dataInicio, c.idcontroleSeguro desc";
		listaControleSeguro = controleSeguroFacade.listar(sql);
		if (listaControleSeguro==null){
			listaControleSeguro = new ArrayList<Controleseguro>();
        }
		numeroDias();    
	}
	
	public void limpar(){
		nomeCliente= "";
		idVenda=0;
		dataFim=null;
		dataInicio=null;
		unidadenegocio=null;
		usuario=null;
		tipoEmissao="Todos";
		listarControle();
	}
	
	
	public void emitirSeguro(){
		ControleSeguroFacade controleSeguroFacade =new ControleSeguroFacade();
		if(controleSeguro!=null){
			controleSeguro.setDataemissao(dataEmissao);
			controleSeguro.setSituacao("Finalizada");
			controleSeguroFacade.salvarControle(controleSeguro);
		}
		listarControle();
	}
	
	public void numeroDias(){
		int numeroDias=0;
		int numeroseguro = 0;
		for (int i = 0; i < listaControleSeguro.size(); i++) {
			if(listaControleSeguro.get(i).getSeguroviagem().getNumeroSemanas()!=null){
				numeroDias = numeroDias + listaControleSeguro.get(i).getSeguroviagem().getNumeroSemanas();
				numeroseguro = numeroseguro +1;
			}
		}
		numeroDiasString = "Número de dias vendido: "+numeroDias;
		numeroSeguroString = "Número de Seguros:"+numeroseguro;
	}
	
	
	public void cancelar(){
		ControleSeguroFacade controleSeguroFacade =new ControleSeguroFacade();
		for (int i = 0; i < listaControleSeguro.size(); i++) {
			if(listaControleSeguro.get(i).isSelecionado()){
				listaControleSeguro.get(i).setSituacao("CANCELADA");
				controleSeguroFacade.salvarControle(listaControleSeguro.get(i));
				Vendas vendas= new Vendas();
				vendas = listaControleSeguro.get(i).getSeguroviagem().getVendas();
				vendas.setSituacao("CANCELADA");
				vendas.setObsCancelar(motivoCancelamento);
				vendas.setDatacancelamento(new Date());
				vendas.setUsuariocancelamento(usuarioLogadoMB.getUsuario().getIdusuario());
				VendasFacade vendasFacade = new VendasFacade();
				vendas = vendasFacade.salvar(vendas);
				Seguroviagem seguroviagem = new Seguroviagem();
				seguroviagem = listaControleSeguro.get(i).getSeguroviagem();
				seguroviagem.setControle("CANCELADA");
				SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
				seguroviagem = seguroViagemFacade.salvar(seguroviagem);
			}
		}
		listarControle();
	}
	
	public void finalizar(){
		ControleSeguroFacade controleSeguroFacade =new ControleSeguroFacade();
		for (int i = 0; i < listaControleSeguro.size(); i++) {
			if(listaControleSeguro.get(i).isSelecionado()){
				listaControleSeguro.get(i).setSituacao("FINALIZADA");
				controleSeguroFacade.salvarControle(listaControleSeguro.get(i));
				Vendas vendas= new Vendas();
				vendas = listaControleSeguro.get(i).getSeguroviagem().getVendas();
				vendas.setSituacao("CANCELADA");
				vendas.setObsCancelar(motivoCancelamento);
				vendas.setDatacancelamento(new Date());
				vendas.setUsuariocancelamento(usuarioLogadoMB.getUsuario().getIdusuario());
				VendasFacade vendasFacade = new VendasFacade();
				vendas = vendasFacade.salvar(vendas);
				Seguroviagem seguroviagem = new Seguroviagem();
				seguroviagem = listaControleSeguro.get(i).getSeguroviagem();
				seguroviagem.setControle("CANCELADA");
				SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
				seguroviagem = seguroViagemFacade.salvar(seguroviagem);
			}
		}
		listarControle();
	}
	
	public String tipoSeguro(Controleseguro controleseguro){
		if(controleseguro.getSeguroviagem().getIdvendacurso()>0){
			return "Curso";
		}else{
			return "Avulso";
		}
	}
	
	public void buscarSeguro(Controleseguro controleseguro){
		this.controleSeguro = controleseguro;
	}
	
	public String cancelarVenda(Controleseguro controleseguro){
		if (controleseguro.getSeguroviagem().getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")){
			Map<String, Object> options = new HashMap<String, Object>();
	    	options.put("contentWidth", 400);
	    	FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("venda", controleseguro.getSeguroviagem().getVendas());
			RequestContext.getCurrentInstance().openDialog("cancelarVenda", options, null);
		}else if (controleseguro.getSeguroviagem().getVendas().getSituacao().equalsIgnoreCase("PROCESSO")){
			VendasFacade vendasFacade = new VendasFacade();
			controleseguro.getSeguroviagem().getVendas().setSituacao("CANCELADA");
			vendasFacade.salvar(controleseguro.getSeguroviagem().getVendas());
			listarControle();
		}
		return "";
	}
	
	public void gerarListaUnidadeNegocio(){
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        listaUnidadeNegocio = unidadeNegocioFacade.listar();
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
}
