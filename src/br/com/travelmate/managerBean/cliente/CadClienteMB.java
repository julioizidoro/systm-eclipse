package br.com.travelmate.managerBean.cliente;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.bean.wsCep.ControladorCEPBean;
import br.com.travelmate.bean.wsCep.EnderecoBean;
import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.PublicidadeFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Publicidade;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadClienteMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject 
	private AplicacaoMB aplicacaoMB;
	private Cliente cliente;
	private Publicidade publicidade;
	private List<Publicidade> listaPublicidades;
	private boolean digitosTelefoneCelular;
	private boolean digitosTelefoneResi;
	private boolean digitosTelefoneComercio;
	private boolean digitosTelefoneMae;
	private boolean digitosTelefonePai;
	private Lead lead;
	private List<Cliente> listaCliente;
	private String tipo;
	private String voltar = "";

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			cliente = (Cliente) session.getAttribute("cliente");
			lead = (Lead) session.getAttribute("lead");
			tipo = (String) session.getAttribute("tipo");
			listaCliente =  (List<Cliente>) session.getAttribute("listaCliente");
			voltar = (String) session.getAttribute("voltar");
			session.removeAttribute("listaCliente");
			session.removeAttribute("lead");
			session.removeAttribute("cliente");
			session.removeAttribute("tipo");
			session.removeAttribute("voltar");
			if (cliente == null) {
				cliente = new Cliente();
				publicidade = new Publicidade();
			} else {
				publicidade = cliente.getPublicidade();
			}
			gerarListaPublicidade();
			digitosTelefoneCelular = aplicacaoMB.checkBoxTelefone(usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(),
					cliente.getFoneCelular());
			digitosTelefoneResi = aplicacaoMB.checkBoxTelefone(usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(),
					cliente.getFoneResidencial());
			digitosTelefoneComercio = aplicacaoMB.checkBoxTelefone(usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(),
					cliente.getFoneComercial());
			digitosTelefoneMae = aplicacaoMB.checkBoxTelefone(usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(),
					cliente.getFoneMae());
			digitosTelefonePai = aplicacaoMB.checkBoxTelefone(usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(),
					cliente.getFonePai());
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Publicidade getPublicidade() {
		return publicidade;
	}

	public void setPublicidade(Publicidade publicidade) {
		this.publicidade = publicidade;
	}

	public List<Publicidade> getListaPublicidades() {
		return listaPublicidades;
	}

	public void setListaPublicidades(List<Publicidade> listaPublicidades) {
		this.listaPublicidades = listaPublicidades;
	}

	public boolean isDigitosTelefoneCelular() {
		return digitosTelefoneCelular;
	}

	public void setDigitosTelefoneCelular(boolean digitosTelefoneCelular) {
		this.digitosTelefoneCelular = digitosTelefoneCelular;
	}

	public boolean isDigitosTelefoneResi() {
		return digitosTelefoneResi;
	}

	public void setDigitosTelefoneResi(boolean digitosTelefoneResi) {
		this.digitosTelefoneResi = digitosTelefoneResi;
	}

	public boolean isDigitosTelefoneComercio() {
		return digitosTelefoneComercio;
	}

	public void setDigitosTelefoneComercio(boolean digitosTelefoneComercio) {
		this.digitosTelefoneComercio = digitosTelefoneComercio;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public boolean isDigitosTelefoneMae() {
		return digitosTelefoneMae;
	}

	public void setDigitosTelefoneMae(boolean digitosTelefoneMae) {
		this.digitosTelefoneMae = digitosTelefoneMae;
	}

	public boolean isDigitosTelefonePai() {
		return digitosTelefonePai;
	}

	public void setDigitosTelefonePai(boolean digitosTelefonePai) {
		this.digitosTelefonePai = digitosTelefonePai;
	}

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	public String salvar() {
		if (publicidade != null) {
			if (cliente.getNome() != null && cliente.getNome().length() > 0) {
				if (cliente.getEmail() != null && cliente.getEmail().length() > 0) {
					if (Formatacao.validarEmail(cliente.getEmail())) {
						if (cliente.getRg() != null && cliente.getRg().length() > 0) {
							if (cliente.getCpf() != null && cliente.getCpf().length() > 0) {
								if (cliente.getDataNascimento() != null) {
									if (cliente.getIdcliente() == null) {
										cliente.setUnidadenegocio(usuarioLogadoMB.getUsuario().getUnidadenegocio());
									}
									cliente.setPublicidade(publicidade);
									cliente.setDataCadastro(new Date());
									ClienteFacade clienteFacade = new ClienteFacade();
									cliente = clienteFacade.salvar(cliente);
									FacesMessage mensagem = new FacesMessage("Salvo com Sucesso! ",
											"Cliente cadastrado.");
									FacesContext.getCurrentInstance().addMessage(null, mensagem);
									if(lead!=null && lead.getIdlead()!=null){
										FacesContext fc = FacesContext.getCurrentInstance();
										HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
										session.setAttribute("cliente", lead.getCliente());
										session.setAttribute("lead", lead); 
										if (lead.getProdutos().getIdprodutos() == aplicacaoMB.getParametrosprodutos().getAupair()) {
											return "cadAuPair";
										} else if (lead.getProdutos().getIdprodutos() == aplicacaoMB.getParametrosprodutos().getCursos()) {
											return "cadFichaCurso";
										} else if (lead.getProdutos().getIdprodutos() == aplicacaoMB.getParametrosprodutos().getDemipair()) {
											return "cadDemiPair";
										} else if (lead.getProdutos().getIdprodutos() == aplicacaoMB.getParametrosprodutos().getProgramasTeens()) {
											return "cadCursosTeens";
										} else if (lead.getProdutos().getIdprodutos() == aplicacaoMB.getParametrosprodutos().getHighSchool()) {
											return "cadHighSchool";
										} else if (lead.getProdutos().getIdprodutos() == aplicacaoMB.getParametrosprodutos().getTrainee()) {
											if (tipo != null) {
												session.setAttribute("tipo", tipo); 
												if (tipo.equalsIgnoreCase("Australia")) {
													return "cadEstagioAustralia";
												}
												return "cadTrainee";
											}
										} else if (lead.getProdutos().getIdprodutos() == aplicacaoMB.getParametrosprodutos().getVoluntariado()) {
											return "cadVoluntariado";
										} else if (lead.getProdutos().getIdprodutos() == aplicacaoMB.getParametrosprodutos().getWork()) {
											return "cadWorkandTravel";
										} else if (lead.getProdutos().getIdprodutos() == aplicacaoMB.getParametrosprodutos().getPacotes()) {
											return "cadpacote";
										} else if (lead.getProdutos().getIdprodutos() == aplicacaoMB.getParametrosprodutos().getPassagem()) {
											return "cadPassagem";
										} else if (lead.getProdutos().getIdprodutos() == aplicacaoMB.getParametrosprodutos().getVisto()) {
											return "cadVistos";
										} else if (lead.getProdutos().getIdprodutos() == aplicacaoMB.getParametrosprodutos().getSeguroPrivado()) {
											return "fichaSeguroViagem";
										}else if(lead.getProdutos().getIdprodutos() == aplicacaoMB.getParametrosprodutos().getHighereducation()){
											String faseHe = (String) session.getAttribute("faseHe");
											if (faseHe  != null && faseHe.equalsIgnoreCase("questionario")) {
												session.setAttribute("cliente", cliente);
												return "questionarioHe";
											}else if(faseHe != null && faseHe.equalsIgnoreCase("formulario")) {
												session.setAttribute("cliente", cliente);
												return "cadFichaHe1";
											}else {
												session.setAttribute("cliente", cliente);
												return "cadFichaHe2";
											}
										}else if(lead.getProdutos().getIdprodutos() == 24) {
											return "cadAcomodacao";
										}
									}    
									FacesContext fc = FacesContext.getCurrentInstance();
									HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
									session.setAttribute("listaCliente", listaCliente);
									return "consCliente";
								} else
									Mensagem.lancarMensagemFatal("Atenção! Preencha o campo data de nascimento.", "");
							} else
								Mensagem.lancarMensagemFatal("Atenção! Preencha o campo CPF.", "");
						} else
							Mensagem.lancarMensagemFatal("Atenção! Preencha o campo RG.", "");
					}
				} else
					Mensagem.lancarMensagemFatal("Atenção! Preencha o campo email.", "");
			} else
				Mensagem.lancarMensagemFatal("Atenção! Preencha o campo nome.", "");
		} else
			Mensagem.lancarMensagemFatal("Atenção! Preencha o campo publicidade.", "");
		return "";
	}

	public String cancelarCadastro() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("listaCliente", listaCliente);
		if (voltar != null && voltar.length() > 0) {
			return voltar;
		}
		return "consCliente";
	}

	public void gerarListaPublicidade() {
		PublicidadeFacade publicidadeFacade = new PublicidadeFacade();
		try {
			listaPublicidades = publicidadeFacade.listar();
			if (listaPublicidades == null) {
				listaPublicidades = new ArrayList<Publicidade>();
			}
		} catch (SQLException ex) {
			FacesMessage mensagem = new FacesMessage("Erro! ", "Erro ao listar publicidade.");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
		}
	}
	
	public String validarMascaraFoneCelular(){
		return aplicacaoMB.validarMascaraTelefone(digitosTelefoneCelular);
	}
	
	public String validarMascaraFoneResidencial(){
		return aplicacaoMB.validarMascaraTelefone(digitosTelefoneResi);
	}
	
	public String validarMascaraFoneComercial(){
		return aplicacaoMB.validarMascaraTelefone(digitosTelefoneComercio);
	}
	
	public String validarMascaraFoneMae(){
		return aplicacaoMB.validarMascaraTelefone(digitosTelefoneMae);
	}
	
	public String validarMascaraFonePai(){
		return aplicacaoMB.validarMascaraTelefone(digitosTelefonePai);
	}
	
	public void verificarCPFCadastrado(){    
		if (cliente.getCpf()!=null){
			ClienteFacade clienteFacade = new ClienteFacade();
			Cliente novoCliente = clienteFacade.consultarCpfLista(cliente.getCpf());
			if (novoCliente!=null){
				Mensagem.lancarMensagemInfo("Cliente", "CPF já cadastrado");
			}
		}
	}
	
	public void buscarendereco(){
		ControladorCEPBean cep = new ControladorCEPBean();
		cep.setCep(cliente.getCep());
		EnderecoBean endereco = cep.carregarEndereco();
		if (endereco.getLogradouro() != null) {
			cliente.setBairro(endereco.getBairro());
			cliente.setEstado(endereco.getUf());
			cliente.setCidade(endereco.getLocalidade());
			cliente.setComplemento(endereco.getComplemento());
			String logradouro = endereco.getLogradouro().substring(endereco.getLogradouro().indexOf(" "), endereco.getLogradouro().length());
	        int posicao = endereco.getLogradouro().length();
	        for (int i = 0; i <= logradouro.length(); i++) {
	            posicao = posicao - 1;
	        }
	        String tipo = endereco.getLogradouro().substring(0, posicao +1);
	        cliente.setLogradouro(logradouro);
	        cliente.setTipologradouro(tipo);
		}else{
			Mensagem.lancarMensagemInfo("Endereço não encontrado!!", "");
		}
	}
}
