package br.com.travelmate.managerBean.email;

import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.managerBean.OrcamentoCurso.DadosEscolaEmailBean;
import br.com.travelmate.managerBean.controleEmail.ControleEmailBean;
import br.com.travelmate.model.Arquivos;
import br.com.travelmate.model.Fatura;
import br.com.travelmate.model.Fornecedorpacotearquivopagamento;
import br.com.travelmate.model.Ftpdados; 
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class EnviarEmail {

	private List<DadosEscolaEmailBean> listaDadosEscolas;
	private String nomeArquivo;
	private List<Arquivos> listaArquivos; 
	private HtmlEmail email;
	private String remetente;
	private String texto;
	private String assunto;
	private float totalObrigatorio;
	private float totalOpcional;
	private float totalAcomodacao;
	private float totalAcOpcional;
	private float totalTransfer;
	private float totalAdicionais;
	private String corpoEmail;
	private Usuario usuario;
	private Ftpdados ftpDados;
	private String nomeCliente;
	private String nomeConsultor;
	private String textoRodape;
	private List<String> listaDestinos;
	private Fatura fatura;
	private Fornecedorpacotearquivopagamento pacotesfornecedor;

	public EnviarEmail(String nomeArquivo, List<DadosEscolaEmailBean> listaDadosEscolas, List<Arquivos> listaArquivos,
			Usuario usuario, String remetente, String texto, String nomeConsultor, String assunto, String nomeCliente,
			List<String> listaDestinos, String textoRodape, Fatura fatura, Fornecedorpacotearquivopagamento pacotesfornecedor) throws FileNotFoundException {
		this.listaDadosEscolas = listaDadosEscolas;
		this.nomeArquivo = nomeArquivo;
		this.listaArquivos = listaArquivos;
		this.remetente = remetente;
		this.texto = texto;
		this.nomeCliente = nomeCliente;
		this.nomeConsultor = nomeConsultor;
		this.assunto = assunto;
		this.listaArquivos = listaArquivos;
		this.textoRodape = textoRodape;
		this.usuario = usuario;
		this.listaDestinos = listaDestinos;
		this.fatura = fatura;
		this.pacotesfornecedor = pacotesfornecedor;
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		try {
			ftpDados = ftpDadosFacade.getFTPDados();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		criarEmail();
	}

	public String getCorpoEmail() {
		return corpoEmail;
	}

	public void setCorpoEmail(String corpoEmail) {
		this.corpoEmail = corpoEmail;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	public float getTotalObrigatorio() {
		return totalObrigatorio;
	}

	public void setTotalObrigatorio(float totalObrigatorio) {
		this.totalObrigatorio = totalObrigatorio;
	}

	public float getTotalOpcional() {
		return totalOpcional;
	}

	public void setTotalOpcional(float totalOpcional) {
		this.totalOpcional = totalOpcional;
	}

	public float getTotalAcomodacao() {
		return totalAcomodacao;
	}

	public void setTotalAcomodacao(float totalAcomodacao) {
		this.totalAcomodacao = totalAcomodacao;
	}

	public float getTotalAcOpcional() {
		return totalAcOpcional;
	}

	public void setTotalAcOpcional(float totalAcOpcional) {
		this.totalAcOpcional = totalAcOpcional;
	}

	public float getTotalTransfer() {
		return totalTransfer;
	}

	public void setTotalTransfer(float totalTransfer) {
		this.totalTransfer = totalTransfer;
	}

	public float getTotalAdicionais() {
		return totalAdicionais;
	}

	public void setTotalAdicionais(float totalAdicionais) {
		this.totalAdicionais = totalAdicionais;
	} 
	public HtmlEmail getEmail() {
		return email;
	}

	public void setEmail(HtmlEmail email) {
		this.email = email;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public List<DadosEscolaEmailBean> getListaDadosEscolas() {
		return listaDadosEscolas;
	}

	public void setListaDadosEscolas(List<DadosEscolaEmailBean> listaDadosEscolas) {
		this.listaDadosEscolas = listaDadosEscolas;
	}

	public Ftpdados getFtpDados() {
		return ftpDados;
	}

	public void setFtpDados(Ftpdados ftpDados) {
		this.ftpDados = ftpDados;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public List<Arquivos> getListaArquivos() {
		return listaArquivos;
	}

	public void setListaArquivos(List<Arquivos> listaArquivos) {
		this.listaArquivos = listaArquivos;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getNomeConsultor() {
		return nomeConsultor;
	}

	public void setNomeConsultor(String nomeConsultor) {
		this.nomeConsultor = nomeConsultor;
	}

	public String getTextoRodape() {
		return textoRodape;
	}

	public void setTextoRodape(String textoRodape) {
		this.textoRodape = textoRodape;
	}

	public List<String> getListaDestinos() {
		return listaDestinos;
	}

	public void setListaDestinos(List<String> listaDestinos) {
		this.listaDestinos = listaDestinos;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public Fatura getFatura() {
		return fatura;
	}

	public void setFatura(Fatura fatura) {
		this.fatura = fatura;
	} 

	public Fornecedorpacotearquivopagamento getPacotesfornecedor() {
		return pacotesfornecedor;
	}

	public void setPacotesfornecedor(Fornecedorpacotearquivopagamento pacotesfornecedor) {
		this.pacotesfornecedor = pacotesfornecedor;
	}

	public void enviarEmail() {
		email = new HtmlEmail();
		email.setHostName("email-ssl.com.br");
		email.setSSL(true);
		email.setSslSmtpPort("465");
		// email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("ti@travelmate.com.br", "20SimpleS78"));
		try {
			email.setFrom(remetente, nomeConsultor);
			listarDestinos();
		} catch (EmailException ex) {
			Logger.getLogger(EnviarEmail.class.getName()).log(Level.SEVERE, null, ex);
		}

		email.setSubject(assunto);
		try {
			email.setHtmlMsg(corpoEmail);
			email.setContent(corpoEmail, "text/html");
		} catch (EmailException ex) {
			Logger.getLogger(EnviarEmail.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			email.send();
			ControleEmailBean controleEmail = new ControleEmailBean();
			controleEmail.salvar(new Date(), Formatacao.foramtarHoraString(), assunto, listaDestinos, corpoEmail, usuario);
		} catch (EmailException ex) {
			Logger.getLogger(EnviarEmail.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void listarDestinos() {
		for (int i = 0; i < listaDestinos.size(); i++) {
			try {
				email.addTo(listaDestinos.get(i),listaDestinos.get(i));
			} catch (EmailException ex) {
				Logger.getLogger(EnviarEmail.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public void criarEmail() {
		String br = "";
		corpoEmail = "<html>\n" + "<head>\n"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />\n"
				+ "    <STYLE type=\"text/css\">TD {margin: 0px;padding: 0px;}IMG {margin: 0px;padding: 0px;}A.headline {TEXT-DECORATION: none;margin:;}A.headline:link {TEXT-DECORATION: none;}A.headline:visited {TEXT-DECORATION: none}A.headline:hover {TEXT-DECORATION: underline;}a: {margin: 0px;padding: 0px;} .menu{float:left;}</STYLE>\n"
				+ "</head>\n" + "<body style=\"font-family: arial;width:70%;\">\n"  
				+ " <div align=\"center\" style=\"width:100%;\">\n";
		corpoEmail = corpoEmail + " <img src=\"http://www.systm.com.br:82/ftproot/systm/elementosOrcamento/logoRelatorio.jpg\" width=\"400\">\n"; 
		corpoEmail = corpoEmail + " <br></br>\n";
		if (listaDadosEscolas != null && listaDadosEscolas.size() > 0) {
			corpoEmail = corpoEmail + "	<label style=\"font-size:18px;\">Olá, "+ nomeCliente +"</label><br></br><br></br>";
			corpoEmail = corpoEmail + "	<label style=\"font-size:13px;\">"+ texto +"</label><br></br><br></br>";
			gerarCorpoOrcamento();
		} else if (nomeArquivo != null && nomeArquivo.length() > 0) {
			corpoEmail = corpoEmail + "	<label style=\"font-size:18px;\">"+ texto +"</label><br></br><br></br>";
			gerarCorpoOrcamentoComparativo();
		} else if (listaArquivos != null && listaArquivos.size() > 0) {
			corpoEmail = corpoEmail + "	<label style=\"font-size:18px;\">"+ texto +"</label><br></br><br></br>";
			gerarCorpoDocumentos();
		}  else if (pacotesfornecedor != null && pacotesfornecedor.getIdfornecedorpacotearquivopagamento()!=null) {
			corpoEmail = corpoEmail + "	<label style=\"font-size:18px;\">"+ texto +"</label><br></br><br></br>";
			gerarCorpoComprovanteTurismo();
		} else{
			corpoEmail = corpoEmail + "	<label style=\"font-size:18px;\">"+ texto +"</label><br></br><br></br>";
		}
		
		corpoEmail = corpoEmail + " <div align=\"center\" style=\"margin-top:25px;\">\n"
				+ " <div style=\"width:850px;\">\n"
				+ br
				+ " <p style=\"font-size:13px;LINE-HEIGHT:18px;width:380px;\">"+textoRodape+"</p>\n"
				+ " <p style=\"font-size:13px;\">TravelMate, é pra vida toda!</p>\n "
				+ " </div></div></div>\n"; 
		corpoEmail = corpoEmail + "</p>"
				+ "     	 <div style=\"float:left;background-color:#ffffff; margin:20px 0 40px 0; padding-left:10px; padding-top:5px; font-family:Arial, Helvetica, sans-serif; font-size:12px;\">\n"
				+ "    	    	<table style=\"margin-left:6px\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n"
				+ "    	    	  <tr>\n"
				+ "    	    	    <td align=\"left\" valign=\"middle\"><div align=\"left\"><a href=\"http://www.travelmate.com.br\" target=\"_blank\"><img style=\"display:block\" src=\"http://travelmate.com.br/wp-content/uploads/2017/01/logo_tm_assinatura.jpg\" alt=\"Visite o nosso site\" border=\"0\"/></a></div></td>\n"
				+ "     	    	    <td align=\"left\" valign=\"middle\" style=\"padding-left:5px\"><table style=\"margin-left:0px\" border=\"0\" cellspacing=\"1\">\n"
				+ "    	    	      <tr>\n"
				+ "    	    	        <td><span style=\"DISPLAY: block; FONT-SIZE: 12px; COLOR: #333; FONT-FAMILY: tahoma; font-weight:bold\">"
				+ usuario.getNome() + "</span></td>\n" + "    	    	      </tr>\n" + "    	    	      <tr>\n"
				+ "    	    	        <td><span style=\"font-family: arial; font-size: 11px; color: #333\">"
				+ usuario.getFuncao() + "</span></td>\n" + "     	    	      </tr>\n"
				+ "    	    	      <tr>\n" + "     	    	        <td><A href=\"mailto:" + usuario.getEmail()
				+ "\" target=\"_blank\" class=headline style=\"DISPLAY: block; FONT-SIZE: 11px; COLOR: #333; FONT-FAMILY:arial;\">"
				+ usuario.getEmail() + "</A></td></tr>\n" + "     	    	        <tr>\n"
				+ "     	    	        <td>\n"
				+ "    	    	        <A href=\"http://www.travelmate.com.br\" target=\"_blank\" class=headline style=\"DISPLAY: block; FONT-SIZE: 11px; COLOR: #333; FONT-FAMILY:arial;\">travelmate.com.br</A></td>\n"
				+ "    	    	      </tr>\n" + "    	    	      <tr>\n"
				+ "     	    	        <td><span style=\"DISPLAY: block; FONT-SIZE: 11px; COLOR: #333; FONT-FAMILY: arial;\">+55 "
				+ usuario.getUnidadenegocio().getFone() + "</span></td>\n" + "     	    	        </tr>\n"
				+ "     	    	        <tr>\n"
				+ "   	    	         <td><span style=\"DISPLAY: block; FONT-SIZE: 11px; COLOR: #333; FONT-FAMILY: arial;\">"
				+ usuario.getUnidadenegocio().getTipoLogradouro() + " " + usuario.getUnidadenegocio().getLogradouro()
				+ ", " + usuario.getUnidadenegocio().getNumero() + ", " + usuario.getUnidadenegocio().getBairro()
				+ " - " + usuario.getUnidadenegocio().getCidade() + "</span></td></tr>\n"
				+ "    	    	         <tr><td><a href=\"https://www.facebook.com/TravelMate.Intercambio\" target=\"_blank\"><img src=\"http://travelmate.com.br/wp-content/uploads/2017/01/facebook_assinaturaemail.png\" alt=\"Facebook\" border=\"0\"/></a><a href=\"https://br.linkedin.com/company/travelmate-interc-mbio-e-turismo\" target=\"_blank\"><img src=\"http://travelmate.com.br/wp-content/uploads/2017/01/linkedin_assinaturaemail.png\" alt=\"IN\" border=\"0\"/></a><a href=\"http://www.twitter.com/travelmate_\" target=\"_blank\"><img src=\"http://travelmate.com.br/wp-content/uploads/2017/01/twitter_assinaturaemail.png\" alt=\"IN\" border=\"0\"/></a><a href=\"http://instagram.com/mytravelmate\" target=\"_blank\"><img src=\"http://travelmate.com.br/wp-content/uploads/2017/01/instagram_assinaturaemail.png\" alt=\"IN\" border=\"0\"/></a><a href=\"http://travelmate.com.br/a-travelmate/selo-belta\" target=\"_blank\"><img src=\"http://travelmate.com.br/wp-content/uploads/2017/01/belta_assinaturaemail.png\" alt=\"IN\" border=\"0\"/></a></td>\n"
				+ "    	    	      </tr>\n" + "    	    	    </table></td>\n"
				+ "    	    	    <td align=\"left\" valign=\"bottom\" style=\"padding-left:5px\">&nbsp;</td>\n"
				+ "   	    	  </tr>\n" + "   	    	</table>\n" + "</body>\n" + "</html>";
	}

	public void gerarCorpoOrcamento() {
		for (int j = 0; j < listaDadosEscolas.size(); j++) {
			corpoEmail = corpoEmail 
					+ "<table><tr><td> <div style=\"width:90%;\" class=\"menu\">\n" + " <img src=\"http://" + ftpDados.getHost()
					+ ":82/ftproot/systm//paisemail/" + listaDadosEscolas.get(j).getIdpais() + ".png"
					+ "\" width=\"185\" style=\"float:right;\"/>\n" + " </div></td><td>"
					+ " <div  class=\"menu\" style=\"width:100%;margin-left:4%;text-align:left;LINE-HEIGHT:8px;\">  \n"
					+ " <p style=\"font-size:13px;\">Destino: " + listaDadosEscolas.get(j).getLocal() + "</p>\n"
					+ " <p style=\"font-size:13px;\">Instituição: " + listaDadosEscolas.get(j).getEscola() + "</p>\n"
					+ " <p style=\"font-size:13px;\">Curso: " + listaDadosEscolas.get(j).getTipoCurso() + "</p>\n"
					+ " <p style=\"font-size:13px;\">Data de Início: " + listaDadosEscolas.get(j).getDataInicio()
					+ "</p>\n" + " <p style=\"font-size:13px;\">Duração do Curso: "
					+ listaDadosEscolas.get(j).getDuracao() + " Semanas" + "</p>\n"
					+ " <p style=\"font-size:13px;\">Turno do Curso: " + listaDadosEscolas.get(j).getTurno() + "</p/>\n"
					+ " <a href=\"http://" + ftpDados.getHost() + ":82/ftproot/systm/orcamento/"
					+ listaDadosEscolas.get(j).getNomeArquivo()
					+ "\" target=\"blanck\" style=\"text-decoration:none;\">\n"
					+ " <img src=\"http://www.systm.com.br:82/ftproot/systm/paisemail/btnorcamento.png\"></img></a> <br></br><br></br><br></br></div></td></tr></table>\n";
		}
	}

	public void gerarCorpoOrcamentoComparativo() {
		corpoEmail = corpoEmail + " 	</p>\n" + "	    <hr style=\"color:#A6CE39; \"/>\n" + "	<section>\n"
				+ "	    <article>\n" + "	        <header>\n" + " <a href=\"http://" + ftpDados.getHost()
				+ ":82/ftproot/systm/orcamento/" + nomeArquivo
				+ "\" target=\"blanck\" style=\"font-size:16px;color:#4C816D;\">Clique para visualizar orçamentos.</a>\n"
				+ "	        </header>\n" + "	        <hr style=\"color:#A6CE39;\"/>\n" + "	      </article>\n"
				+ "	    </section>\n"
				+ " 	<p style=\"margin-left:2.5%;color:#3C4A58;font-family:Arial, Helvetica, sans-serif; font-size:12px;\">\n"
				+ " <br/>";
	}

	public void gerarCorpoDocumentos() {
		for (int j = 0; j < listaArquivos.size(); j++) {
			corpoEmail = corpoEmail + "	            <h3 style=\"color: #1E8871;margin-left:2.5%;\"></h2>\n"
					+ "</strong>\n" + "             <strong style=\"font-size:12px;\">"
					+ "	        	<label style=\"font-size:15px;\">Documento: "
					+ listaArquivos.get(j).getTipoarquivo().getDescricao() + "</a><br/>\n"
					+ "	        	<a href=\"http://" + ftpDados.getHost() + ":82/ftproot/systm/arquivos/"
					+ listaArquivos.get(j).getNomesalvos()
					+ "\" target=\"blanck\" style=\"font-size:12px;color:#424242;\">Clique para ver o documento </a>\n"
					+ "	        </header>\n" + "	        <hr style=\"color:#424242;\"/>\n";
		}
	} 
	
	public void gerarCorpoComprovanteTurismo() { 
		corpoEmail = corpoEmail + "	            <h3 style=\"color: #1E8871;margin-left:2.5%;\"></h2>\n"
				+ "</strong>\n" + "             <strong style=\"font-size:12px;\">"
				+ "	        	<label style=\"font-size:15px;\">Comprovante: "
				+ "</a><br/>\n"
				+ "	        	<a href=\"http://" + ftpDados.getHost() + ":82/ftproot/systm/turismo/comprovantes/"
				+ pacotesfornecedor.getNomeftp()
				+ "\" target=\"blanck\" style=\"font-size:12px;color:#424242;\">Clique para abrir o documento.</a>\n"
				+ "	        </header>\n" + "	        <hr style=\"color:#424242;\"/>\n"; 
	} 
}
