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
public class RelatorioHighSchoolAnualMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Unidadenegocio> listaUnidadeNegocio;
    private Unidadenegocio unidadenegocio;
    private Valoreshighschool valoreshighschool;
    private List<Valoreshighschool> listaValores;
	
	
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
	
	
	
	public void gerarListaUnidadeNegocio(){
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
        if (listaUnidadeNegocio==null){
            listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
        }
    } 
	
	
	
	public void carregarValores() {
			String sql = "select v From Valoreshighschool v Where v.situacao='Ativo' and v.anoinicio>0 Group By v.anoinicio";
			ValoresHighSchoolFacade valoresHighSchoolFacade = new ValoresHighSchoolFacade();
			listaValores = valoresHighSchoolFacade.listar(sql);
			if (listaValores == null) {
				listaValores = new ArrayList<Valoreshighschool>();
			}
	}
	
	public String gerarSql(){
        String sql = "SELECT distinct cliente.nome as cliente, cliente.fonemae as fonemae, unidadenegocio.nomerelatorio as unidade"
                + ", highschool.escolaIntercambio as programa, valoreshighschool.inicio as inicio, valoreshighschool.anoinicio as anoinicio"
            	+ " FROM Highschool JOIN vendas on highschool.vendas_idvendas = vendas.idvendas"
                + " JOIN cliente on vendas.cliente_idcliente = cliente.idcliente"
                + " JOIN unidadenegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadenegocio.idunidadeNegocio"
                + " JOIN valoreshighschool on highschool.valoreshighschool_idvaloreshighschool=valoreshighschool.idvaloreshighschool"
                + " WHERE vendas.situacao<>'CANCELADA' and vendas.situacao<>'PROCESSO' ";
        if(valoreshighschool!=null){
        	sql = sql + " and valoreshighschool.anoinicio='"+ valoreshighschool.getAnoinicio() +"'";
        }  
        if (unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
            sql = sql + " and vendas.unidadeNegocio_idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
        } 
        sql = sql + " order by cliente.nome ";
        return sql;
    }  
    
    public String gerarRelatorio() throws SQLException, IOException {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "/reports/controlehighschool/relatorioGeralAno.jasper";  
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
