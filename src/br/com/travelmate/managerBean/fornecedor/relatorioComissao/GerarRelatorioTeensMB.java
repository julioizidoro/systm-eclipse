package br.com.travelmate.managerBean.fornecedor.relatorioComissao;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.ValoresHighSchoolFacade;
import br.com.travelmate.facade.ValoresProgramasTeensFacade;
import br.com.travelmate.managerBean.aupair.controle.RelatorioMediaMatchMB;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Valoreshighschool;
import br.com.travelmate.model.Valoresprogramasteens;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@Named
@ViewScoped
public class GerarRelatorioTeensMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Valoreshighschool valoreshighschool;
	private Valoresprogramasteens valoresprogramasteens;
	private List<RelatorioComissaoTeensBean> listaComissaoTeens;
	private Pais pais;
	private List<Pais> listaPais;
	
	
	@PostConstruct
	public void init(){
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar();
	}


	public Valoreshighschool getValoreshighschool() {
		return valoreshighschool;
	}


	public void setValoreshighschool(Valoreshighschool valoreshighschool) {
		this.valoreshighschool = valoreshighschool;
	}


	public Valoresprogramasteens getValoresprogramasteens() {
		return valoresprogramasteens;
	}


	public void setValoresprogramasteens(Valoresprogramasteens valoresprogramasteens) {
		this.valoresprogramasteens = valoresprogramasteens;
	}


	public List<RelatorioComissaoTeensBean> getListaComissaoTeens() {
		return listaComissaoTeens;
	}


	public void setListaComissaoTeens(List<RelatorioComissaoTeensBean> listaComissaoTeens) {
		this.listaComissaoTeens = listaComissaoTeens;
	}
	
	
	
	public Pais getPais() {
		return pais;
	}


	public void setPais(Pais pais) {
		this.pais = pais;
	}


	public List<Pais> getListaPais() {
		return listaPais;
	}


	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}


	public void gerarValoresHighSchool(){
		String sql = "SELECT h FROM Valoreshighschool h WHERE h.situacao='Ativo' and h.datavalidade>='"+
				Formatacao.ConvercaoDataSql(new Date()) + "'";
		ValoresHighSchoolFacade valoresHighSchoolFacade = new ValoresHighSchoolFacade();
		if (listaComissaoTeens == null) {
			listaComissaoTeens = new ArrayList<>();
		}
		if (pais != null) {
			sql = sql + " and h.fornecedorcidade.cidade.pais.idpais=" + pais.getIdpais();
		}
		List<Valoreshighschool> listaValoresHighSchool = valoresHighSchoolFacade.listar(sql);
		for (int i = 0; i < listaValoresHighSchool.size(); i++) {
			RelatorioComissaoTeensBean comissaoTeens = new RelatorioComissaoTeensBean();
			comissaoTeens.setCidade(listaValoresHighSchool.get(i).getFornecedorcidade().getCidade().getNome());
			comissaoTeens.setPais(listaValoresHighSchool.get(i).getFornecedorcidade().getCidade().getPais().getNome());
			comissaoTeens.setComissaoExpress(listaValoresHighSchool.get(i).getComissaoexpress());
			comissaoTeens.setComissaoPremium(listaValoresHighSchool.get(i).getComissaopremium());
			comissaoTeens.setMoeda(listaValoresHighSchool.get(i).getMoedas1().getSigla());
			comissaoTeens.setPrograma("High School");
			listaComissaoTeens.add(comissaoTeens);
		}
	}
	
	
	public void gerarValoresTeens(){
		String sql = "SELECT t FROM Valoresprogramasteens t WHERE t.situacao='Ativo' ";
		ValoresProgramasTeensFacade valoresProgramasTeensFacade = new ValoresProgramasTeensFacade();
		if (listaComissaoTeens == null) {
			listaComissaoTeens = new ArrayList<>();
		}
		if (pais != null) {
			sql = sql + " and t.fornecedorcidade.cidade.pais.idpais=" + pais.getIdpais();
		}
		List<Valoresprogramasteens> listaValoresprogramasteens = valoresProgramasTeensFacade.listar(sql);
		for (int i = 0; i < listaValoresprogramasteens.size(); i++) {
			RelatorioComissaoTeensBean comissaoTeens = new RelatorioComissaoTeensBean();
			comissaoTeens.setCidade(listaValoresprogramasteens.get(i).getFornecedorcidade().getCidade().getNome());
			comissaoTeens.setPais(listaValoresprogramasteens.get(i).getFornecedorcidade().getCidade().getPais().getNome());
			comissaoTeens.setComissaoExpress(listaValoresprogramasteens.get(i).getComissaoexpress());
			comissaoTeens.setComissaoPremium(listaValoresprogramasteens.get(i).getComissaopremium());
			comissaoTeens.setMoeda(listaValoresprogramasteens.get(i).getMoedas().getSigla());
			comissaoTeens.setPrograma("Teens");
			listaComissaoTeens.add(comissaoTeens);
		}
	}
	
	public String gerarRelatorio() throws SQLException, IOException {
		gerarValoresHighSchool();
		gerarValoresTeens();
        String caminhoRelatorio = "/reports/relatorios/comissaoParceirosTeens.jasper";  
        Map<String, Object> parameters = new HashMap<String, Object>();
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
        BufferedImage logo = ImageIO.read(f);  
		parameters.put("logo", logo);
        RelatorioComissaoTeensFactory.setLista(listaComissaoTeens);
        JRDataSource jrds = new JRBeanCollectionDataSource(RelatorioComissaoTeensFactory.getLista());
        GerarRelatorio gerarRelatorio = new GerarRelatorio();
        try {
            gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, "Relatório Comissão Parceiros Teens.pdf");
        } catch (JRException ex) {
            Logger.getLogger(RelatorioMediaMatchMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RelatorioMediaMatchMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
	
	public String fechar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
	
	

}
