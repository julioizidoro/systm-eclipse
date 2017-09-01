package br.com.travelmate.managerBean.comercial.relatorio;
   
import br.com.travelmate.managerBean.AplicacaoMB; 
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;  
import java.util.Date;
import java.util.HashMap; 
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class RelatorioMateRunnersMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private String mes;
	private String ano; 
	private int numero;

	@PostConstruct()
	public void init() { 
		mes = Formatacao.numeroMes(); 
		ano = ""+Formatacao.getAnoData(new Date());
		numero = 10;
	}
	
	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}
 
	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String gerarRelatorio() throws SQLException, IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = "/reports/comercial/reportrunners.jasper";
		Map parameters = new HashMap();
		parameters.put("mes", mes);
		parameters.put("ano", ano);
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		File fl = new File(servletContext.getRealPath("/resources/img/paginainicial/materunners.png"));
		BufferedImage materunners = ImageIO.read(fl);
		parameters.put("materunners", materunners);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "MateRunners" + mes + ".pdf", null);
		} catch (JRException ex) {
			Logger.getLogger(RelatorioMateRunnersMB.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioMateRunnersMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String fechar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	} 

	public String carregarListaRunners(){   
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("numero", numero);
        session.setAttribute("ano", ano);
        session.setAttribute("mes", mes);
	    return "graficoMateRunners";
	}  
	
	public void retorno(SelectEvent event) { 
        RequestContext.getCurrentInstance().closeDialog(event.getObject());
    }
 
}
