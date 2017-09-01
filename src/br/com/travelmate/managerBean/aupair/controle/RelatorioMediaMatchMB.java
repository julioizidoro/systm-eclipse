/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.aupair.controle;

import br.com.travelmate.bean.UtilBean;
import br.com.travelmate.facade.AupairFacade;
import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.facade.ContasPagarFacade;
import br.com.travelmate.facade.PlanoContaFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Contaspagar;
import br.com.travelmate.model.Controleaupair;
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

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.context.RequestContext;

/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class RelatorioMediaMatchMB implements Serializable{
    
	private static final long serialVersionUID = 1L; 
    private Date dataInicio;
    private Date dataTermino;     
    private double media=0;
    
    @PostConstruct()
    public void init(){ 
    	int ano = Formatacao.getAnoData(new Date());
    	String dataFormatada = "01/01/"+ano;
    	dataInicio = Formatacao.ConvercaoStringData(dataFormatada); 
    	dataFormatada = "31/12/"+ano;
    	dataTermino = Formatacao.ConvercaoStringData(dataFormatada); 
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
    
    public String gerarRelatorio() throws SQLException, IOException {
        String caminhoRelatorio = "/reports/controleaupair/mediaMatch.jasper";  
        Map<String, Object> parameters = new HashMap<String, Object>();
        List<MediaMatchBean> lista = gerarListaMedias(); 
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
        BufferedImage logo = ImageIO.read(f);  
        parameters.put("logo", logo);
        parameters.put("media", media);
        JRDataSource jrds = new JRBeanCollectionDataSource(lista);
        GerarRelatorio gerarRelatorio = new GerarRelatorio();
        try {
            gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, "Média Match.pdf");
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
    
    public List<MediaMatchBean> gerarListaMedias(){ 
    	media = 0;
    	String sql = "Select c From Controleaupair c where c.vendas.dataVenda>='" 
    			+ Formatacao.ConvercaoDataSql(dataInicio)
    			+"' and c.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'"
    			+" and c.datamatch>'2010-01-01' and c.dataonline>'2010-01-01'"
    			+ " order by c.vendas.dataVenda";
    	AupairFacade aupairFacade = new AupairFacade();
    	List<Controleaupair> lista = aupairFacade.listaControle(sql);
    	List<MediaMatchBean> listaMatch = new ArrayList<MediaMatchBean>();
    	if (lista!=null){  
    		UtilBean utilBean = new UtilBean();
    		for(int i=0;i<lista.size();i++){
    			MediaMatchBean match = new MediaMatchBean(); 
    			match.setIdvenda(lista.get(i).getVendas().getIdvendas());  
    			match.setNomeCliente(lista.get(i).getVendas().getCliente().getNome());
    			match.setDataOnline(lista.get(i).getDataonline());
    			match.setDataMatch(lista.get(i).getDatamatch()); 
    			int nsemanas= utilBean.calcularNumeroSemanas(lista.get(i).getDataonline(), lista.get(i).getDatamatch());
    			match.setNumerosemanas(nsemanas+" semana(s)");
    			media = media+nsemanas;
    			listaMatch.add(match);
    		}
    	}
    	media = media / lista.size();
    	return listaMatch;
    }
     
}
    

