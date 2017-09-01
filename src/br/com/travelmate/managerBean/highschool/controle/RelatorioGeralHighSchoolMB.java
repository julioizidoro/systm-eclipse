package br.com.travelmate.managerBean.highschool.controle;
 
import br.com.travelmate.facade.UnidadeNegocioFacade;  
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

@Named
@ViewScoped
public class RelatorioGeralHighSchoolMB implements Serializable{
    
    /**
	 *      
	 */
	private static final long serialVersionUID = 1L;
	private List<Unidadenegocio> listaUnidadeNegocio;
    private Unidadenegocio unidadenegocio;
    private Date dataInicio;
    private Date dataTermino;     
    
    @PostConstruct()
    public void init(){
    	unidadenegocio = new Unidadenegocio(); 
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
 
	public void gerarListaUnidadeNegocio(){
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
        if (listaUnidadeNegocio==null){
            listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
        }
    } 
    
    public String gerarSql(){
        String sql = "SELECT distinct cliente.nome, cliente.dataNascimento, vendas.dataVenda, cliente.foneCelular,"
                + " cliente.email, cliente.fonePai, cliente.foneMae, unidadenegocio.nomerelatorio, highschool.duracaoIntercambio,"
                + " highschool.escolaIntercambio as nomeEscola, controlehighschool.dataEnvioApp, controlehighschool.datadocumentacaovisto,"
                + " controlehighschool.datacomprovantepagamento, controlehighschool.dataenviopassagem, controlehighschool.familia"
            	+ " FROM Controlehighschool JOIN vendas on controlehighschool.vendas_idvendas = vendas.idvendas"
                + " JOIN cliente on vendas.cliente_idcliente = cliente.idcliente"
                + " JOIN highschool on controlehighschool.highschool_idhighschool = highschool.idhighschool"
                + " JOIN unidadenegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadenegocio.idunidadeNegocio"
                + " where  (vendas.situacao='FINALIZADA' OR vendas.situacao='ANDAMENTO')";
        if(dataInicio!=null && dataTermino!=null){
        	sql = sql + " and vendas.dataVenda >='"+Formatacao.ConvercaoDataSql(dataInicio)+"' and vendas.dataVenda<='"
                      + Formatacao.ConvercaoDataSql(dataTermino) + "'";
        }  
        if (unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
            sql = sql + " and vendas.unidadeNegocio_idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
        } 
        sql = sql + " order by vendas.dataVenda ";
        return sql;
    }
    
    public String gerarRelatorio() throws SQLException, IOException {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "/reports/controlehighschool/relatorioGeral.jasper";  
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("sql", gerarSql()); 
        File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
        BufferedImage logo = ImageIO.read(f);  
        parameters.put("logo", logo);
        GerarRelatorio gerarRelatorio = new GerarRelatorio();
        try {
            gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Relatório Geral.pdf", null);
        } catch (JRException ex) {
            Logger.getLogger(RelatorioGeralHighSchoolMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RelatorioGeralHighSchoolMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public String fechar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    } 
}
