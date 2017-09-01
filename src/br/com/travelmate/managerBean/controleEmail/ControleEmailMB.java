package br.com.travelmate.managerBean.controleEmail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date; 
import java.util.List; 

import javax.annotation.PostConstruct; 
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext; 

import br.com.travelmate.facade.ControleEmailFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Controleemail;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class ControleEmailMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Controleemail controleemail;
	private List<Controleemail> listaControleEmail;
	private Date dataInicial;
	private Date dataFinal;
	private String destinatario;
	private String hora;
	private String assunto;
	private FileWriter email;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			gerarListaEmail();
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Controleemail getControleemail() {
		return controleemail;
	}

	public void setControleemail(Controleemail controleemail) {
		this.controleemail = controleemail;
	}

	public List<Controleemail> getListaControleEmail() {
		return listaControleEmail;
	}

	public void setListaControleEmail(List<Controleemail> listaControleEmail) {
		this.listaControleEmail = listaControleEmail;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public void gerarListaEmail() {
		ControleEmailFacade controleEmailFacade = new ControleEmailFacade();
		String sql = "Select c From Controleemail c Where c.usuario.idusuario="
					+ usuarioLogadoMB.getUsuario().getIdusuario(); 
		listaControleEmail = controleEmailFacade.listar(sql);
		if (listaControleEmail == null) {
			listaControleEmail = new ArrayList<>();
		}
	}

	public void pegarEmail(Controleemail controleemail) {
		this.controleemail = controleemail;
	}

	public void pesquisar() {
		ControleEmailFacade controleEmailFacade = new ControleEmailFacade();
		String sql = "Select c From Controleemail c Where c.destinatarios like '%" + destinatario
				+ "%' and c.assunto like '%" + assunto + "%'"
				+ " and c.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario();   
		if (dataInicial != null && dataFinal != null) {
			sql = sql + " and c.data>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' and c.data<='"
					+ Formatacao.ConvercaoDataSql(dataFinal) + "'";

		} 
		listaControleEmail = controleEmailFacade.listar(sql); 
		if (listaControleEmail == null) {
			listaControleEmail = new ArrayList<>();
		}
	}

	public void limpar() {
		destinatario = "";
		assunto = "";
		dataFinal = null;
		dataInicial = null;
		gerarListaEmail();
	}

	public String visualizarEmail(Controleemail controleemail) {
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
    	ServletContext request = (ServletContext) facesContext.getExternalContext().getContext();
    	String pasta = request.getRealPath("");
    	pasta = pasta + "\\email\\" + usuarioLogadoMB.getUsuario().getIdusuario()+".html";
    	File arquivo = new File(pasta);
        try {
			email = new FileWriter(arquivo);
			String html = "<html style=\"width:75%;margin-left:10%;\">"+
			"<head>"+
			"	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" /> "+
			"</head>"+
			"<body style=\"font-family: arial;\"> "+
			"	<h3 style=\"color: #1E8871; margin-left: 2.5%;\"></h3>"+ 
			"	<strong style=\"font-size: 12px;\"> "+
			"		<div style=\"width:850px;text-align: center;\"> "+
			"			<label style=\"color: #4C816D; font-size:19px;\">Dados do E-mail</label><br /> <br /> "+
			"		</div>"+
			"		<div style=\"width:420px;float:left;\"> "+
			"			<label style=\"color: #000000; font-size: 12px;\">Data: "+Formatacao.ConvercaoDataPadrao(controleemail.getData())+"</label> <br /> "+
			"			<label style=\"color: #000000; font-size: 12px;\">Assunto: "+controleemail.getAssunto()+"</label><br />"+ 
			"			<label style=\"color: #000000; font-size: 12px;\">Destinatários: "+controleemail.getDestinatarios()+ "</label> "+
			"		</div>"+
			"		<div style=\"width:380px;float:left;\">"+
			"			<label style=\"color: #000000; font-size: 12px;\">Hora: "+controleemail.getHora()+"</label><br /> "+ 
			"		</div>"+
			"		<br /> <br /><br /> <br />  "+
			"		<hr style=\"color: #A6CE39;\" />"+
			"	</strong><br/>"+ 
			"</body>"+
			"<meta charset=\"UTF-8\">";
			email.write(html + controleemail.getCorpo()+"</html>");  
			email.close();
			FacesContext.getCurrentInstance().getExternalContext().redirect("/inicio/email/"+usuarioLogadoMB.getUsuario().getIdusuario()+".html");
		} catch (IOException e) { 
			e.printStackTrace();  
		}
		return "";  
	}

}
