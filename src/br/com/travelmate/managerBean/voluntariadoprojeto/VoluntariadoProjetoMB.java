package br.com.travelmate.managerBean.voluntariadoprojeto;

import br.com.travelmate.facade.AvisosFacade; 
import br.com.travelmate.facade.FornecedorCidadeFacade; 
import br.com.travelmate.facade.FornecedorFacade; 
import br.com.travelmate.facade.PaisFacade; 
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.VoluntariadoProjetoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Cidade; 
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidade;  
import br.com.travelmate.model.Idioma; 
import br.com.travelmate.model.Pais; 
import br.com.travelmate.model.Usuario; 
import br.com.travelmate.model.Voluntariadoprojeto;
import br.com.travelmate.model.Voluntariadoprojetovalor;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

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

@Named
@ViewScoped
public class VoluntariadoProjetoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Voluntariadoprojeto voluntariadoprojeto;
	private Voluntariadoprojetovalor voluntariadoprojetovalor;
	private List<Voluntariadoprojeto> listaVoluntariadoProjeto;
	private List<Pais> listaPais;
	private Cidade cidade;
	private Pais pais; 
	private Fornecedorcidade fornecedorcidade;
	private List<Fornecedorcidade> listafornecedorcidade;
	private List<Idioma> listaIdiomas;
	private boolean selecionado;
	private int ano; 
	private boolean habilitarorcamento;

	@PostConstruct
	public void init() { 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		fornecedorcidade = (Fornecedorcidade) session.getAttribute("fornecedorcidade");
		session.removeAttribute("fornecedorcidade");
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar();  
		habilitarorcamento = false;
		if (fornecedorcidade == null) {
			fornecedorcidade = new Fornecedorcidade();
			ano = Formatacao.getAnoData(new Date());
			pais = new Pais();
			cidade = new Cidade(); 
		} else { 
			pais = fornecedorcidade.getCidade().getPais();
			cidade = fornecedorcidade.getCidade();
			listarFornecedorCidade();
			if(fornecedorcidade.getFornecedor().getAnotarifario()>0){
				ano = fornecedorcidade.getFornecedor().getAnotarifario();
			}
			habilitarorcamento = fornecedorcidade.getFornecedor().isHabilitarorcamento();
			listarVoluntariadoProjeto(); 
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	} 

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
 
	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}
  
	
	public boolean isHabilitarorcamento() {
		return habilitarorcamento;
	}

	public void setHabilitarorcamento(boolean habilitarorcamento) {
		this.habilitarorcamento = habilitarorcamento;
	}

	public List<Idioma> getListaIdiomas() {
		return listaIdiomas;
	}

	public void setListaIdiomas(List<Idioma> listaIdiomas) {
		this.listaIdiomas = listaIdiomas;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public Voluntariadoprojeto getVoluntariadoprojeto() {
		return voluntariadoprojeto;
	}

	public void setVoluntariadoprojeto(Voluntariadoprojeto voluntariadoprojeto) {
		this.voluntariadoprojeto = voluntariadoprojeto;
	}

	public Voluntariadoprojetovalor getVoluntariadoprojetovalor() {
		return voluntariadoprojetovalor;
	}

	public void setVoluntariadoprojetovalor(Voluntariadoprojetovalor voluntariadoprojetovalor) {
		this.voluntariadoprojetovalor = voluntariadoprojetovalor;
	}

	public List<Voluntariadoprojeto> getListaVoluntariadoProjeto() {
		return listaVoluntariadoProjeto;
	}

	public void setListaVoluntariadoProjeto(List<Voluntariadoprojeto> listaVoluntariadoProjeto) {
		this.listaVoluntariadoProjeto = listaVoluntariadoProjeto;
	}

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}

	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}

	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}

	public List<Fornecedorcidade> getListafornecedorcidade() {
		return listafornecedorcidade;
	}

	public void setListafornecedorcidade(List<Fornecedorcidade> listafornecedorcidade) {
		this.listafornecedorcidade = listafornecedorcidade;
	}

	public void listarVoluntariadoProjeto() {
		if (fornecedorcidade != null) { 
			String sql = "Select v from Voluntariadoprojeto v where v.fornecedorcidade.idfornecedorcidade="
					+ fornecedorcidade.getIdfornecedorcidade() + "  order by v.descricao";
			VoluntariadoProjetoFacade voluntariadoProjetoFacade = new VoluntariadoProjetoFacade();
			listaVoluntariadoProjeto = voluntariadoProjetoFacade.listar(sql); 
			ano = fornecedorcidade.getFornecedor().getAnotarifario();
			if (listaVoluntariadoProjeto == null) {
				listaVoluntariadoProjeto = new ArrayList<>();
			}
		}
	} 
 
	public void listarFornecedorCidade() { 
		if (cidade != null && cidade.getIdcidade()!=null) {
			String sql = "select f from Fornecedorcidade f where f.cidade.idcidade=" + cidade.getIdcidade()
					+ " and f.produtos.idprodutos="+aplicacaoMB.getParametrosprodutos().getVoluntariado()
					+ " and f.ativo=1 order by f.fornecedor.nome";
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			listafornecedorcidade = fornecedorCidadeFacade.listar(sql);
			if (listafornecedorcidade == null) {
				listafornecedorcidade = new ArrayList<Fornecedorcidade>();
			}
			listaVoluntariadoProjeto = new ArrayList<>(); 
		}
	}

	public void cadVoluntariadoProjeto() {
		if (cidade!=null && cidade.getIdcidade()!=null 
				&& fornecedorcidade != null && fornecedorcidade.getIdfornecedorcidade()!=null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("fornecedorcidade", fornecedorcidade);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 500);
			RequestContext.getCurrentInstance().openDialog("cadVoluntariadoProjeto", options, null); 
		} else {
			Mensagem.lancarMensagemErro("Atenção!", "Campos obrigatórios não preenchidos."); 
		}
	}   
	
	public void retornoDialogoNovo(SelectEvent event) {
		Voluntariadoprojeto voluntariadoprojeto = (Voluntariadoprojeto) event.getObject();
		listaVoluntariadoProjeto.add(voluntariadoprojeto);
	}
	
	public void editar(Voluntariadoprojeto voluntariadoprojeto) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("voluntariadoprojeto", voluntariadoprojeto);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("cadVoluntariadoProjeto", options, null); 
	}

	public void retornoDialogoEditar() {
		listarVoluntariadoProjeto();
	} 
 
	public String habilitarFornecedorOrcamento() {
		FornecedorFacade fornecedorFacade = new FornecedorFacade();
		Fornecedor fornecedor = fornecedorcidade.getFornecedor();
		fornecedor = fornecedorFacade.salvar(fornecedor);
		gerarAvisoNovaEscolaCadastrada();
		return "";
	}

	public void salvarAno() {
		if (fornecedorcidade != null) { 
			Fornecedor fornecedor = fornecedorcidade.getFornecedor();
			fornecedor.setAnotarifario(ano);
			FornecedorFacade fornecedorFacade = new FornecedorFacade();
			fornecedorFacade.salvar(fornecedorcidade.getFornecedor()); 
			Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
		}
	}

	public void gerarAvisoNovaEscolaCadastrada() {
		Avisos aviso = new Avisos();
		aviso.setData(new Date());
		aviso.setTexto("Tarifario da escola " + fornecedorcidade.getFornecedor().getNome() 
				+ " está disponivel para orçamento de Voluntariado.");
		aviso.setImagem("aviso");
		aviso.setIdunidade(0);
		aviso.setLiberar(true);
		aviso.setDepartamento("outros");
		aviso.setUsuario(usuarioLogadoMB.getUsuario());
		AvisosFacade avisosFacade = new AvisosFacade();
		aviso.setAvisousuarioList(salvarAvisoUsuario(aviso));
		aviso = avisosFacade.salvar(aviso);

	}

	public List<Avisousuario> salvarAvisoUsuario(Avisos aviso) {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		String sql = "select u from Usuario u where u.situacao='Ativo'";
		List<Usuario> listaUsuario = usuarioFacade.listar(sql);
		List<Avisousuario> lista = new ArrayList<Avisousuario>();
		if (listaUsuario != null) {
			for (int i = 0; i < listaUsuario.size(); i++) {
				Avisousuario avisousuario = new Avisousuario();
				avisousuario.setAvisos(aviso);
				avisousuario.setUsuario(listaUsuario.get(i));
				avisousuario.setVisto(false);
				lista.add(avisousuario);
			}
		}
		return lista;
	}
 
	public String produtosOrcamento() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("produtosOrcamento");
		return "";
	}
	
	public String consVoluntariadoProjetoValor(Voluntariadoprojeto voluntariadoprojeto) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("voluntariadoprojeto", voluntariadoprojeto); 
		return "voluntariadoProjetoValor";
	}  
 
	public void voluntariadoProjetoCurso(Voluntariadoprojeto voluntariadoprojeto) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("voluntariadoprojeto", voluntariadoprojeto); 
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 440);
		RequestContext.getCurrentInstance().openDialog("voluntariadoProjetoCurso", options, null);  
	}

	public void voluntariadoProjetoAcomodacao(Voluntariadoprojeto voluntariadoprojeto) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("voluntariadoprojeto", voluntariadoprojeto); 
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("voluntariadoProjetoAcomodacao");  
	} 
}
