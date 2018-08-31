/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.util;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.primefaces.context.RequestContext;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.sun.org.apache.xerces.internal.impl.dv.dtd.NMTOKENDatatypeValidator;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.model.Ftpdados;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author Wolverine
 */
public class GerarRelatorio {
    
    public void gerarRelatorioDSPDF(String caminhoRelatorio, Map parameters, JRDataSource jrds, String nomeArquivo) throws JRException, IOException{
    	FacesContext facesContext = FacesContext.getCurrentInstance();  
        ServletContext servletContext = (ServletContext)facesContext.getExternalContext().getContext();
        InputStream reportStream = facesContext.getExternalContext()  
                .getResourceAsStream(caminhoRelatorio);  
        JasperPrint arquivoPrint=null;
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.reset();
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline; filename=\"" + nomeArquivo +"\"");
        ServletOutputStream servletOutputStream = response.getOutputStream();  
        RequestContext.getCurrentInstance().closeDialog(null);
      
        // envia para o navegador o PDF gerado  
        JasperRunManager.runReportToPdfStream(reportStream,  
                servletOutputStream, parameters,jrds);  
          
        servletOutputStream.flush();  
        response.getOutputStream();
        servletOutputStream.close();  
  
        facesContext.responseComplete(); 
    }
    
    public void gerarRelatorioDSFile(String caminhoRelatorio, Map parameters, JRDataSource jrds, String nomeArquivo) throws JRException, IOException, SQLException{
    	FacesContext facesContext = FacesContext.getCurrentInstance();  
        ServletContext servletContext = (ServletContext)facesContext.getExternalContext().getContext();
        String nomeFtp = nomeArquivo;
        nomeArquivo = servletContext.getRealPath("/orcamento/"+nomeArquivo);
        InputStream reportStream = facesContext.getExternalContext()  
                .getResourceAsStream(caminhoRelatorio);  
        File file = new File(nomeArquivo);
        file.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(file);
        JasperRunManager.runReportToPdfStream(reportStream, outputStream, parameters, jrds);
        UploadAWSS3 s3 = new UploadAWSS3("orcamento");
        s3.uploadFile(file);
    }
    
    public void gerarRelatorioSqlPDF(String caminhoRelatorio, Map<String, Object> parameters, String nomeArquivo, String subDir) throws JRException, IOException, SQLException{
        FacesContext facesContext = FacesContext.getCurrentInstance();  
        ServletContext servletContext = (ServletContext)facesContext.getExternalContext().getContext();
        InputStream reportStream = facesContext.getExternalContext()  
                .getResourceAsStream(caminhoRelatorio);  
        Connection conn = ConectionFactory.getConexao();
	    

        if (subDir!=null){
            subDir = servletContext.getRealPath(subDir);
            subDir = subDir + File.separator + "a";
            subDir = subDir.substring(0, (subDir.length()-1));
            parameters.put("SUBREPORT_DIR", subDir);
        }
        JasperPrint arquivoPrint=null;
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.reset();
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline; filename=\"" + nomeArquivo +"\"");
        ServletOutputStream servletOutputStream = response.getOutputStream();  
        //RequestContext.getCurrentInstance().closeDialog(null);
      
        // envia para o navegador o PDF gerado  
        JasperRunManager.runReportToPdfStream(reportStream,  
                servletOutputStream, parameters, conn);  
         
        servletOutputStream.flush();  
        servletOutputStream.close();  
  
        facesContext.responseComplete();  
        
    }
    
   
}
