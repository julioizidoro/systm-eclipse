package br.com.travelmate.managerBean.financeiro.recebimento;

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
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.RecinternacionalFacade;
import br.com.travelmate.managerBean.curso.RelatorioEmbarqueUnidadeMB;
import br.com.travelmate.model.Recinternacional;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class relatorioInvoiceParceiroMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Recinternacional recinternacional;
	private String studantname;
	private String studantnumber;
	private String  programdate;
	private String information;
	private boolean habilitarInformacao = true;
	private boolean informacoes = true;
	
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        recinternacional = (Recinternacional) session.getAttribute("recinternacional");
        session.removeAttribute("recinternacional");
	}



	public Recinternacional getRecinternacional() {
		return recinternacional;
	}



	public void setRecinternacional(Recinternacional recinternacional) {
		this.recinternacional = recinternacional;
	}



	public String getStudantname() {
		return studantname;
	}



	public void setStudantname(String studantname) {
		this.studantname = studantname;
	}



	public String getStudantnumber() {
		return studantnumber;
	}



	public void setStudantnumber(String studantnumber) {
		this.studantnumber = studantnumber;
	}
	
	


	public String getProgramdate() {
		return programdate;
	}



	public void setProgramdate(String programdate) {
		this.programdate = programdate;
	}



	public String getInformation() {
		return information;
	}



	public void setInformation(String information) {
		this.information = information;
	}
	
	
	
	
	
	public boolean isHabilitarInformacao() {
		return habilitarInformacao;
	}



	public void setHabilitarInformacao(boolean habilitarInformacao) {
		this.habilitarInformacao = habilitarInformacao;
	}
	
	



	public boolean isInformacoes() {
		return informacoes;
	}



	public void setInformacoes(boolean informacoes) {
		this.informacoes = informacoes;
	}



	public String confirmar() throws SQLException, IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = "";
		if (habilitarInformacao) {
			caminhoRelatorio = "/reports/recebimentos/invoiceParceiro.jasper";
		}else{
			caminhoRelatorio = "/reports/recebimentos/invoiceParceiroN.jasper";
		}
		Map<String, Object> parameters = new HashMap();
		parameters.put("sql", gerarSql());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		parameters.put("studentname", studantname);
		parameters.put("studentnumber", studantnumber);   
		parameters.put("programdate", programdate);
		parameters.put("information", information);
		parameters.put("fornecedor", recinternacional.getFornecedor().getNome());
		parameters.put("sigla", recinternacional.getMoedas().getSigla());
		parameters.put("valor", recinternacional.getValor());
		parameters.put("datavencimento", recinternacional.getDatavencimento());   
		parameters.put("idvendas", recinternacional.getVendas().getIdvendas());
		parameters.put("idrecebimento", recinternacional.getIdrecinternacional());
		GerarRelatorio gerarRelatorio = new GerarRelatorio();  

		RecinternacionalFacade recinternacionalFacade = new RecinternacionalFacade();
		recinternacional.setRelatorio(true);
		recinternacionalFacade.salvar(recinternacional);
		RequestContext.getCurrentInstance().closeDialog("Sucesso");
		try {     
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,"Relatório de Invoice Parceiro.pdf", null);
		} catch (JRException ex) {
			Logger.getLogger(RelatorioEmbarqueUnidadeMB.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioEmbarqueUnidadeMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}
	
	
	public String gerarSql(){
		String sql = "Select canaisbancarios.accountwith, canaisbancarios.swiftcode, canaisbancarios.accountname, "
				+ " canaisbancarios.accountnr, canaisbancarios.swiftcodebr From canaisbancarios  Where canaisbancarios.moedas_idmoedas=" + recinternacional.getMoedas().getIdmoedas();
		return sql;
	}
	
	
	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	
	public void habilitarCampos(){
		if (habilitarInformacao) {
			informacoes = true;
		}else{
			informacoes = false;
		}
		
	}
	
	
	
	
	

}
