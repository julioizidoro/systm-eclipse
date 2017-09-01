package br.com.travelmate.managerBean.cancelamento;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.managerBean.aupair.AuPairMB;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class RelatorioTermoQuitacaoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Cancelamento cancelamento;
	private String banco;
	private String titular;
	private String agencia;
	private String conta;
	private String cpf;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        cancelamento = (Cancelamento) session.getAttribute("cancelamento");
        session.removeAttribute("cancelamento");
	}

	public Cancelamento getCancelamento() {
		return cancelamento;
	}

	public void setCancelamento(Cancelamento cancelamento) {
		this.cancelamento = cancelamento;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	 
    public String gerarRelatorioTermoQuitacao() throws SQLException, IOException {
    	ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
   	 	String caminhoRelatorio = ("/reports/cancelamento/termoQuitacao.jasper");
        Map parameters = new HashMap();
        parameters.put("idcancelamento", cancelamento.getIdcancelamento());
        File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
        BufferedImage logo = ImageIO.read(f);  
        parameters.put("logo", logo);
        parameters.put("banco", banco);
        parameters.put("agencia", agencia);
        parameters.put("conta", conta);
        parameters.put("titular", titular);
        parameters.put("cpf", cpf);
        parameters.put("valorreembolso", Formatacao.formatarFloatString(cancelamento.getValorreembolso()));
        GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
        try {
        	gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "TermoCancelamento"+cancelamento.getVendas().getIdvendas()+".pdf", null);
        } catch (JRException ex1) {
            Logger.getLogger(RelatorioTermoQuitacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
        } catch (IOException ex) {
            Logger.getLogger(RelatorioTermoQuitacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public String cancelar(){
    	RequestContext.getCurrentInstance().closeDialog(null);
    	return "";
    }
	
}
