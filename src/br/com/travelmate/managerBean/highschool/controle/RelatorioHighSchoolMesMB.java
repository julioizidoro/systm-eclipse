package br.com.travelmate.managerBean.highschool.controle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
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

import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.ValoresHighSchoolFacade;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Valoreshighschool;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class RelatorioHighSchoolMesMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Unidadenegocio> listaUnidadeNegocio;
    private Unidadenegocio unidadenegocio;
    private Valoreshighschool valoreshighschool;
    private List<Valoreshighschool> listaValores;
    private Valoreshighschool valoreshighschoolAno;
    private List<Valoreshighschool> listaValoresAno;
    private String valoresAnoInicio;
    private String valoresInicio;
    
    
    @PostConstruct
	public void init(){
		carregarValores();
		gerarListaUnidadeNegocio(); 
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


	public Valoreshighschool getValoreshighschool() {
		return valoreshighschool;
	}


	public void setValoreshighschool(Valoreshighschool valoreshighschool) {
		this.valoreshighschool = valoreshighschool;
	}


	public List<Valoreshighschool> getListaValores() {
		return listaValores;
	}


	public void setListaValores(List<Valoreshighschool> listaValores) {
		this.listaValores = listaValores;
	}
	
	
	
	public Valoreshighschool getValoreshighschoolAno() {
		return valoreshighschoolAno;
	}


	public void setValoreshighschoolAno(Valoreshighschool valoreshighschoolAno) {
		this.valoreshighschoolAno = valoreshighschoolAno;
	}


	public List<Valoreshighschool> getListaValoresAno() {
		return listaValoresAno;
	}


	public void setListaValoresAno(List<Valoreshighschool> listaValoresAno) {
		this.listaValoresAno = listaValoresAno;
	}


	public String getValoresAnoInicio() {
		return valoresAnoInicio;
	}


	public void setValoresAnoInicio(String valoresAnoInicio) {
		this.valoresAnoInicio = valoresAnoInicio;
	}


	public String getValoresInicio() {
		return valoresInicio;
	}


	public void setValoresInicio(String valoresInicio) {
		this.valoresInicio = valoresInicio;
	}


	public void gerarListaUnidadeNegocio(){
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
        if (listaUnidadeNegocio==null){
            listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
        }
    } 
	
	
	
	public void carregarValores() {
			String sql = "select v From Valoreshighschool v Where v.situacao='Ativo' and v.anoinicio>0 Group By v.inicio";
			String sqlAno = "select v From Valoreshighschool v Where v.situacao='Ativo' and v.anoinicio>0 Group By v.anoinicio";
			ValoresHighSchoolFacade valoresHighSchoolFacade = new ValoresHighSchoolFacade();
			listaValores = valoresHighSchoolFacade.listar(sql);
			listaValoresAno = valoresHighSchoolFacade.listar(sqlAno);
			if (listaValores == null) {
				listaValores = new ArrayList<Valoreshighschool>();
			}
			if (listaValoresAno == null) {
				listaValoresAno = new ArrayList<Valoreshighschool>();
			}
	}
	
	public String gerarSql(){
        String sql = "SELECT distinct cliente.nome as cliente, cliente.datanascimento as datanascimento, unidadenegocio.nomerelatorio as unidade"
                + ", highschool.escolaIntercambio as programa, valoreshighschool.inicio as inicio, valoreshighschool.anoinicio as anoinicio, controlehighschool.visto, "
        		+ " controlehighschool.familia, controlehighschool.passagem, controlehighschool.datadocumentacaovisto"
            	+ " FROM Controlehighschool "
                + " JOIN highschool on controlehighschool.highschool_idhighschool = highschool.idhighschool"
            	+ " JOIN vendas on highschool.vendas_idvendas = vendas.idvendas"
                + " JOIN cliente on vendas.cliente_idcliente = cliente.idcliente"
                + " JOIN unidadenegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadenegocio.idunidadeNegocio"
                + " JOIN valoreshighschool on highschool.valoreshighschool_idvaloreshighschool=valoreshighschool.idvaloreshighschool"
                + " WHERE vendas.situacao<>'CANCELADA' and vendas.situacao<>'PROCESSO' ";
		if (valoresAnoInicio != null && !valoresAnoInicio.equalsIgnoreCase("TODOS")) {
			sql = sql + " and valoreshighschool.anoinicio='" + valoresAnoInicio + "'";
		}
		if (valoresInicio != null && !valoresInicio.equalsIgnoreCase("TODOS")) {
			sql = sql + " and valoreshighschool.inicio='" + valoresInicio + "'";
		}
        if (unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
            sql = sql + " and vendas.unidadeNegocio_idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
        } 
        sql = sql + " order by cliente.nome ";
        return sql;   
    }  
       
    public String gerarRelatorio() throws SQLException, IOException {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "/reports/controlehighschool/relatorioGeralMes.jasper";  
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("sql", gerarSql()); 
        File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
        BufferedImage logo = ImageIO.read(f);  
        parameters.put("logo", logo);
        GerarRelatorio gerarRelatorio = new GerarRelatorio();
        try {
            gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Relatório de Visto.pdf", null);
        } catch (JRException ex) {
            Logger.getLogger(RelatorioVistoMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RelatorioVistoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public void fechar(){
    	RequestContext.getCurrentInstance().closeDialog(null);
    }
	    

}
