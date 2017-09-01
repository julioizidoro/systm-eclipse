package br.com.travelmate.managerBean.email;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
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

import br.com.travelmate.facade.FornecedorFinanceiroFacade;
import br.com.travelmate.facade.PacotesFornecedorFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.OrcamentoCurso.DadosEscolaEmailBean;
import br.com.travelmate.managerBean.OrcamentoCurso.comparativo.GerarEmailComparativo;
import br.com.travelmate.managerBean.OrcamentoCurso.comparativo.OrcamentoComparativoBean;
import br.com.travelmate.model.Arquivos;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Fatura;
import br.com.travelmate.model.Fornecedorfinanceiro;
import br.com.travelmate.model.Fornecedorpacotearquivopagamento;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.model.Orcamentocurso;
import br.com.travelmate.model.Orcamentoprojetovoluntariado;
import br.com.travelmate.model.Pacotesfornecedor;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class EnviarEmailMB implements Serializable {

	/**
	 * 
	 */
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private static final long serialVersionUID = 1L;
	private List<Arquivos> listaArquivo;
	private String emailDestinatario = "";
	private String emailConsultor = "";
	private String texto = "";
	private List<String> listaEmails;
	private String assunto;
	private List<DadosEscolaEmailBean> listaDadosEscolas;
	private List<OrcamentoComparativoBean> listaOrcamento;
	private Cliente cliente;
	private String textoRodape;
	private List<Ocurso> listaOcurso;
	private boolean tabelaArquivo;
	private List<Orcamentocurso> listaOrcamentoCurso;
	private List<Orcamentoprojetovoluntariado> listaOrcamentoVoluntariado;
	private Fatura fatura;
	private Fornecedorpacotearquivopagamento pacotesfornecedor;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaDadosEscolas = (List<DadosEscolaEmailBean>) session.getAttribute("listaDadosEscolas");
		cliente = (Cliente) session.getAttribute("cliente");
		listaOrcamento = (List<OrcamentoComparativoBean>) session.getAttribute("listaOrcamento");
		listaArquivo = (List<Arquivos>) session.getAttribute("listaArquivo");
		listaOcurso = (List<Ocurso>) session.getAttribute("listaOcurso");
		listaOrcamentoCurso = (List<Orcamentocurso>) session.getAttribute("listaOrcamentoCurso");
		listaOrcamentoVoluntariado = (List<Orcamentoprojetovoluntariado>) session.getAttribute("listaOrcamentoVoluntariado");
		fatura = (Fatura) session.getAttribute("fatura");
		pacotesfornecedor = (Fornecedorpacotearquivopagamento) session.getAttribute("pacotesfornecedor"); 
		session.removeAttribute("fatura");
		session.removeAttribute("listaOrcamentoVoluntariado");
		session.removeAttribute("listaOrcamentoCurso");
		session.removeAttribute("listaOcurso");
		session.removeAttribute("listaArquivo");
		session.removeAttribute("listaOrcamento");
		session.removeAttribute("listaDadosEscolas");
		session.removeAttribute("pacotesfornecedor");
		carregarDados();
	}

	public List<Arquivos> getListaArquivo() {
		return listaArquivo;
	}

	public void setListaArquivo(List<Arquivos> listaArquivo) {
		this.listaArquivo = listaArquivo;
	}

	public String getEmailDestinatario() {
		return emailDestinatario;
	}

	public void setEmailDestinatario(String emailDestinatario) {
		this.emailDestinatario = emailDestinatario;
	}

	public String getEmailConsultor() {
		return emailConsultor;
	}

	public void setEmailConsultor(String emailConsultor) {
		this.emailConsultor = emailConsultor;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<String> getListaEmails() {
		return listaEmails;
	}

	public void setListaEmails(List<String> listaEmails) {
		this.listaEmails = listaEmails;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public List<DadosEscolaEmailBean> getListaDadosEscolas() {
		return listaDadosEscolas;
	}

	public void setListaDadosEscolas(List<DadosEscolaEmailBean> listaDadosEscolas) {
		this.listaDadosEscolas = listaDadosEscolas;
	}

	public List<OrcamentoComparativoBean> getListaOrcamento() {
		return listaOrcamento;
	}

	public void setListaOrcamento(List<OrcamentoComparativoBean> listaOrcamento) {
		this.listaOrcamento = listaOrcamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getTextoRodape() {
		return textoRodape;
	}

	public void setTextoRodape(String textoRodape) {
		this.textoRodape = textoRodape;
	}

	public List<Ocurso> getListaOcurso() {
		return listaOcurso;
	}

	public void setListaOcurso(List<Ocurso> listaOcurso) {
		this.listaOcurso = listaOcurso;
	}

	public boolean isTabelaArquivo() {
		return tabelaArquivo;
	}

	public void setTabelaArquivo(boolean tabelaArquivo) {
		this.tabelaArquivo = tabelaArquivo;
	}

	public List<Orcamentocurso> getListaOrcamentoCurso() {
		return listaOrcamentoCurso;
	}

	public void setListaOrcamentoCurso(List<Orcamentocurso> listaOrcamentoCurso) {
		this.listaOrcamentoCurso = listaOrcamentoCurso;
	}

	public Fatura getFatura() {
		return fatura;
	}

	public void setFatura(Fatura fatura) {
		this.fatura = fatura;
	}

	public List<Orcamentoprojetovoluntariado> getListaOrcamentoVoluntariado() {
		return listaOrcamentoVoluntariado;
	}

	public void setListaOrcamentoVoluntariado(List<Orcamentoprojetovoluntariado> listaOrcamentoVoluntariado) {
		this.listaOrcamentoVoluntariado = listaOrcamentoVoluntariado;
	}
 
	public Fornecedorpacotearquivopagamento getPacotesfornecedor() {
		return pacotesfornecedor;
	}

	public void setPacotesfornecedor(Fornecedorpacotearquivopagamento pacotesfornecedor) {
		this.pacotesfornecedor = pacotesfornecedor;
	}

	public void cancelar() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}

	public String enviarEmail() throws FileNotFoundException {
		if (listaEmails != null && listaEmails.size() > 0) {
			EnviarEmail enviarEmail = null;
			if (listaOrcamento != null && listaOrcamento.size() > 0) {
				enviarEmail = enviarOrcamentoComparativo(enviarEmail);
			}else if (fatura!=null && fatura.getIdfatura()!=null){
				enviarEmail = new EnviarEmail(null, listaDadosEscolas, listaArquivo, usuarioLogadoMB.getUsuario(),
						emailConsultor, texto, emailConsultor, assunto, fatura.getUnidadenegocio().getNomeFantasia(),
						listaEmails, textoRodape, fatura, pacotesfornecedor);
			} else if (pacotesfornecedor!=null && pacotesfornecedor.getIdfornecedorpacotearquivopagamento()!=null){
				enviarEmail = new EnviarEmail(null, listaDadosEscolas, listaArquivo, usuarioLogadoMB.getUsuario(),
						emailConsultor, texto, emailConsultor, assunto, pacotesfornecedor.getPacotesfornecedor().getFornecedor().getNome(),
						listaEmails, textoRodape, fatura, pacotesfornecedor);
			}else {
				enviarEmail = new EnviarEmail(null, listaDadosEscolas, listaArquivo, usuarioLogadoMB.getUsuario(),
						emailConsultor, texto, usuarioLogadoMB.getUsuario().getNome(), assunto, cliente.getNome(),
						listaEmails, textoRodape, fatura, pacotesfornecedor);
			}
			enviarEmail.enviarEmail();
			if(pacotesfornecedor!=null && pacotesfornecedor.getIdfornecedorpacotearquivopagamento()!=null){
				pacotesfornecedor.getPacotesfornecedor().setEmailenviado(true);
				PacotesFornecedorFacade pacotesFornecedorFacade = new PacotesFornecedorFacade();
				pacotesfornecedor.setPacotesfornecedor(
						pacotesFornecedorFacade.salvar(pacotesfornecedor.getPacotesfornecedor())); 
				Mensagem.lancarMensagemInfo("Salvo com sucesso!", ""); 
			}
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("listaOcurso", listaOcurso);
			session.setAttribute("listaOrcamentoCurso", listaOrcamentoCurso);
			session.setAttribute("listaOrcamentoVoluntariado", listaOrcamentoVoluntariado);
			RequestContext.getCurrentInstance().closeDialog(null);
			return "";
		}
		return "";
	}

	public void removerArquivo(Arquivos arquivo) {
		listaArquivo.remove(arquivo);
	}

	public void removerEmail(String email) {
		listaEmails.remove(email);
	}

	public void adicionarEmail() {
		if (listaEmails == null) {
			listaEmails = new ArrayList<>();
		}
		if (emailDestinatario != null) {
			if (Formatacao.validarEmail(emailDestinatario)) {
				listaEmails.add(emailDestinatario);
				emailDestinatario = "";
			}
		} else
			Mensagem.lancarMensagemErro("", "Destinatario não informado.");
	}

	public EnviarEmail enviarOrcamentoComparativo(EnviarEmail enviarEmail) {
		String nomeArquivo = "Orcamento";
		for (int i = 0; i < listaOrcamento.size(); i++) {
			nomeArquivo = nomeArquivo + "-" + listaOrcamento.get(i).getNumeroorcamento1();
		}
		nomeArquivo = nomeArquivo + ".html";
		GerarEmailComparativo gerarEmailComparativo = new GerarEmailComparativo(listaOrcamento);
		try {
			gerarEmailComparativo.gerarRelatorioDSFile(nomeArquivo);
		} catch (JRException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			return enviarEmail = new EnviarEmail(nomeArquivo, listaDadosEscolas, listaArquivo,
					usuarioLogadoMB.getUsuario(), emailConsultor, texto, usuarioLogadoMB.getUsuario().getNome(),
					assunto, cliente.getNome(), listaEmails, textoRodape, fatura, pacotesfornecedor);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void carregarDados() {
		if (listaArquivo != null && listaArquivo.size() > 0) {
			assunto = "TravelMate - Documentos:" + cliente.getNome();
			tabelaArquivo = true;
			emailDestinatario = cliente.getEmail();
			emailConsultor = usuarioLogadoMB.getUsuario().getEmail();
			texto = "Olá, " + cliente.getNome() + "\r\n"; 
			textoRodape = "TravelMate, é pra vida toda!";
		} else if(fatura!=null && fatura.getIdfatura()!=null){
			assunto = "TravelMate - Fatura disponível";
			emailDestinatario = fatura.getUnidadenegocio().getEmail();
			emailConsultor = usuarioLogadoMB.getUsuario().getEmail();
			texto = "Olá, \r\n";
			texto = texto + "A fatura de "+fatura.getMes()+"/"+fatura.getAno()+" já se encontra disponível para visualização no"
					+ " SysTM. Para acessar a sua fatura basta ir na opção fatura que se encontra no módulo de controle no menu"
					+ " lateral do SysTM." + "\r\n"; 
			textoRodape = "Caso tenham alguma dúvida estamos a disposição."; 
		} else if(pacotesfornecedor!=null && pacotesfornecedor.getIdfornecedorpacotearquivopagamento()!=null){
			assunto = "TravelMate - Comprovante de Pagamento";
			FornecedorFinanceiroFacade fornecedorFinanceiroFacade = new FornecedorFinanceiroFacade();
			Fornecedorfinanceiro fornecedorfinanceiro = fornecedorFinanceiroFacade
					.consultar(pacotesfornecedor.getPacotesfornecedor().getFornecedor().getIdfornecedor());
			if(fornecedorfinanceiro!=null){
				emailDestinatario = fornecedorfinanceiro.getEmailfinanceiro();
				emailConsultor = usuarioLogadoMB.getUsuario().getEmail();
				texto = fornecedorfinanceiro.getFornecedor().getNome()+", \r\n";
				texto = "Caro Parceiro, \r\n";
				texto = texto + "Segue no Link abaixo comprovante de pagamento da reserva Nº " 
						+ pacotesfornecedor.getPacotesfornecedor().getNumeroreserva()+ "."+ "\r\n";  
				textoRodape = "Att,"; 
			}
		} else {
			assunto = "TravelMate - " + usuarioLogadoMB.getUsuario().getNome();
			emailDestinatario = cliente.getEmail();
			emailConsultor = usuarioLogadoMB.getUsuario().getEmail();
			texto = "Olá, " + cliente.getNome() + "\r\n";
			texto = texto + "Agradecemos seu interesse em viajar com a TravelMate" + "\r\n";
			texto = texto + "Segue abaixo informações sobre seu orçamento.";
			textoRodape = " Permaneço à disposição para maiores informações e sugestões, aguardamos seu contato! <br/><br/>TravelMate, é pra vida toda!";
		}  
	}
}
