/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.financeiro.relatorios;

import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.facade.PlanoContaFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Planoconta;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;

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


import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class RelaorioPagamentos implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Unidadenegocio> listaUnidadeNegocio;
    private Date dataInicio;
    private Date dataTermino;
    private Unidadenegocio unidadenegocio;
    private String competencia;
    private Banco banco;
    private List<Banco> listaBanco;
    private List<Planoconta> listaPlanoConta;
    private Planoconta planoConta;
    
    @PostConstruct()
    public void init(){
        gerarListaUnidadeNegocio();
        carregarPlanoConta();
        gerarListaBanco();
    }

    public List<Unidadenegocio> getListaUnidadeNegocio() {
        return listaUnidadeNegocio;
    }

    public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
        this.listaUnidadeNegocio = listaUnidadeNegocio;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public Unidadenegocio getUnidadenegocio() {
        return unidadenegocio;
    }

    public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
        this.unidadenegocio = unidadenegocio;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }
    
    public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public List<Banco> getListaBanco() {
		return listaBanco;
	}

	public void setListaBanco(List<Banco> listaBanco) {
		this.listaBanco = listaBanco;
	}

	public List<Planoconta> getListaPlanoConta() {
		return listaPlanoConta;
	}

	public void setListaPlanoConta(List<Planoconta> listaPlanoConta) {
		this.listaPlanoConta = listaPlanoConta;
	}

	public Planoconta getPlanoConta() {
		return planoConta;
	}

	public void setPlanoConta(Planoconta planoConta) {
		this.planoConta = planoConta;
	}

	public void gerarListaUnidadeNegocio(){
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
        if (listaUnidadeNegocio==null){
            listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
        }
    }
    
    public String gerarSql(){
        String sql = "SELECT distinct contaspagar.datacompensacao, planoconta.descricao as planoconta, contaspagar.descricao,"
                 + " contaspagar.valorentrada, contaspagar.valorsaida, contaspagar.competencia, planoconta.idplanoconta"
                + " From "
            	+ " contaspagar join unidadenegocio on contaspagar.unidadeNegocio_idunidadeNegocio = unidadenegocio.idunidadeNegocio"
                + " join planoconta on contaspagar.planoconta_idplanoconta = planoconta.idplanoconta"
                + " where ";
        if ((dataInicio!=null) && (dataTermino!=null)){
            sql = sql + " contaspagar.datacompensacao >='"  + Formatacao.ConvercaoDataSql(dataInicio) + " ' and contaspagar.datacompensacao<='"
                        + Formatacao.ConvercaoDataSql(dataTermino) + "' ";
        }else {
            sql = sql + " contaspagar.competencia='" + competencia + "' ";
        }
        if (banco!=null){
        	if(banco.getIdbanco()!=null){
        		sql = sql + " and contaspagar.banco_idbanco=" + banco.getIdbanco();
        	}
        }
        if (planoConta!=null){
        	if(planoConta.getIdplanoconta()!=null){
        		sql = sql + " and planoconta.idplanoconta=" + planoConta.getIdplanoconta();
        	}
        }
        if (unidadenegocio!=null){
        	if (unidadenegocio.getIdunidadeNegocio()!=null){
        		sql = sql + " and contaspagar.unidadeNegocio_idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
        	}
        }
        sql = sql + "  Group by contaspagar.planoConta_idplanoConta, contaspagar.dataCompensacao, contaspagar.descricao, contaspagar.valorEntrada, contaspagar.valorSaida, planoconta.descricao, unidadenegocio.nomefantasia, contaspagar.competencia ";
        sql = sql + " order by contaspagar.dataCompensacao, planoconta.idplanoconta, contaspagar.descricao, contaspagar.valorEntrada, contaspagar.valorSaida, planoconta.descricao, unidadenegocio.nomefantasia, contaspagar.competencia ";
        return sql;
    }
    
    
    
    public String gerarRelatorio() throws SQLException, IOException {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "/reports/financeiro/Pagamentos.jasper";  
        Map<String, Object> parameters = new HashMap();
        parameters.put("sql", gerarSql());
        if(unidadenegocio!=null){
        	parameters.put("unidade", unidadenegocio.getNomeFantasia());
        }else{
        	parameters.put("unidade", "Todas");
        }
        File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
        BufferedImage logo = ImageIO.read(f);  
        parameters.put("logo", logo);
        String periodo= "";
        if ((dataInicio!=null) && (dataTermino!=null)){
                periodo = "Periodo : " + Formatacao.ConvercaoDataPadrao(dataInicio) 
                        + "    " + Formatacao.ConvercaoDataPadrao(dataTermino);
            }else periodo = "Competência : " + competencia;
        parameters.put("periodo", periodo);
        GerarRelatorio gerarRelatorio = new GerarRelatorio();
        try {
            gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Pagamentos.pdf", null);
        } catch (JRException ex) {
            Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public String fechar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
    
    
    public void gerarListaBanco() {
        BancoFacade bancoFacade = new BancoFacade();
        listaBanco = bancoFacade.listar();
        if (listaBanco == null) {
            listaBanco = new ArrayList<Banco>();
        }
    }
    
    public void carregarPlanoConta(){
        PlanoContaFacade planoContaFacade = new PlanoContaFacade();
        listaPlanoConta = planoContaFacade.listar("");
        if (listaPlanoConta==null){
            listaPlanoConta = new ArrayList<Planoconta>();
        }
    }
}
