/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Wolverine
 */
public class Ftp {
    
	private FTPClient ftpClient;
    private String host;
    private String user;
    private String password;
    
   
    public Ftp(String host, String user, String password)  {
    	ftpClient = new FTPClient();
    	
        //this.host = "tmftp.systm.com.br";
    	this.host=host;
        this.user = user;
        this.password = password;
    }

    public boolean conectar() throws IOException{
    	ftpClient.connect(host);
        ftpClient.login(user, password);
        if (ftpClient.isConnected()){
        	ftpClient.setControlKeepAliveTimeout(300);
            return true;
        }else return false;
    }
    
	public String enviarArquivo(File file, String arquivoFTP, String pasta) throws IOException{
        ftpClient.setControlEncoding("UTF-8");
        try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
			ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
		} catch (IOException e) {
			
			  
		} 
        ftpClient.changeWorkingDirectory(pasta);
        FileInputStream arqEnviar = new FileInputStream(file.getAbsolutePath());
        arquivoFTP = new String(arquivoFTP.getBytes("ISO-8859-1"), "UTF-8");
        
        if (ftpClient.storeFile(arquivoFTP, arqEnviar)) {
        	arqEnviar.close();
            return "Arquivo Salvo com Sucesso";
        } else {
        	arqEnviar.close();
            return "Erro Salvar Arquivo";
        }
	}
    
    public void desconectar() throws IOException{
        ftpClient.logout();
        ftpClient.disconnect();  
    }
    
         
    public String enviarArquivo(UploadedFile uploadedFile, String arquivoFTP, String pasta) throws IOException{
        ftpClient.setControlEncoding("UTF-8");
        try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
			ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
		} catch (IOException e) {
			
			  
		} 
        ftpClient.changeWorkingDirectory(pasta);
        FileInputStream arqEnviar = (FileInputStream) uploadedFile.getInputstream();
        arquivoFTP = new String(arquivoFTP.getBytes("ISO-8859-1"), "UTF-8");
        if (ftpClient.storeFile(arquivoFTP, arqEnviar)) {
        	arqEnviar.close();
            return "Arquivo: "+ arquivoFTP + " salvo com Sucesso";
        } else {
        	arqEnviar.close();
            return "Erro Salvar Arquivo";
        }
    }
    
    public boolean enviarArquivoDOCS(UploadedFile uploadedFile, String arquivoFTP, String pasta) throws IOException{
        ftpClient.setControlEncoding("UTF-8");
        try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
			ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
		} catch (IOException e) {
			
			  
		} 
        ftpClient.changeWorkingDirectory(pasta);
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        FileInputStream arqEnviar = (FileInputStream) uploadedFile.getInputstream();
        String nomeArquivo = arquivoFTP + "_" +  new String(uploadedFile.getFileName().trim().getBytes("ISO-8859-1"), "UTF-8");
        ftpClient.enterLocalPassiveMode();
        if (ftpClient.storeFile(nomeArquivo, arqEnviar)) {
        	arqEnviar.close();
            return true;
        } else {
        	arqEnviar.close();   
            return false;
        }
    }
    
    public boolean enviarArquivoOrcamento(UploadedFile uploadedFile, String arquivoFTP, String pasta) throws IOException{
        ftpClient.setControlEncoding("UTF-8");
        try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
			ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
		} catch (IOException e) {
			
			  
		} 
        ftpClient.changeWorkingDirectory(pasta);
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        FileInputStream arqEnviar = (FileInputStream) uploadedFile.getInputstream();
        String nomeArquivo =  new String(uploadedFile.getFileName().trim().getBytes(Charset.defaultCharset()), "UTF-8");
        if (ftpClient.storeFile(nomeArquivo, arqEnviar)) {
        	arqEnviar.close();
            return true;
        } else {
        	arqEnviar.close();   
            return false;
        }
    }
    
    
    public String enviarVideo(UploadedFile uploadedFile, String arquivoFTP, String pasta) throws IOException{
        ftpClient.setControlEncoding("UTF-8");
        try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
			ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
		} catch (IOException e) {
			
			  
		} 
        ftpClient.changeWorkingDirectory(pasta);
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        FileInputStream arqEnviar = (FileInputStream) uploadedFile.getInputstream();
        String nomeArquivo = arquivoFTP + "_" +  new String(uploadedFile.getFileName().trim().getBytes(Charset.defaultCharset()), "UTF-8");
        String videoConverter =  String.format(nomeArquivo +".mov", uploadedFile);
        if (ftpClient.storeFile(videoConverter, arqEnviar)) {
        	arqEnviar.close();
            return "Arquivo: "+ videoConverter + " salvo com Sucesso";
        } else {
        	arqEnviar.close();
            return "Erro Salvar Arquivo";
        }
    }
    
    
    public String excluirArquivo(String arquivoFTP, String pasta) throws IOException{
    	if (ftpClient.deleteFile(pasta + arquivoFTP)) {
    		ftpClient.cwd(pasta);
			return "Excluido com sucesso";
		}else{
			return "Erro ao excluir";
		}
        
    }
    
    public InputStream receberArquivo(String arquivoSalvar, String arquivoFTP, String pasta) throws IOException{
        ftpClient.setControlEncoding("UTF-8");
        try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
			ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
		} catch (IOException e) {
			  
		} 
        ftpClient.changeWorkingDirectory(pasta);
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
        InputStream is = new BufferedInputStream(ftpClient.retrieveFileStream(arquivoFTP));
        return is;
    }
    
    
    
    public String enviarArquivoRemessa(File file, String arquivoFTP, String pasta) throws IOException{
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
    	ServletContext request = (ServletContext) facesContext.getExternalContext().getContext();
    	String destino = request.getRealPath("");
    	destino = destino + "\\remessa\\" + file.getName();
    	File f = new File(destino);
        ftpClient.setControlEncoding("UTF-8");
        try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
			ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
		} catch (IOException e) {
			  
		} 
		ftpClient.changeWorkingDirectory(pasta);
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        FileInputStream arqEnviar = new FileInputStream(f);
        arquivoFTP = new String(arquivoFTP.getBytes("ISO-8859-1"), "UTF-8");
        //arquivoFTP =  String.format("mov", file);
        if (ftpClient.storeFile(arquivoFTP, arqEnviar)) {
        	arqEnviar.close();
            return "Arquivo Salvo com Sucesso";
        } else {
        	arqEnviar.close();
            return "Erro Salvar Arquivo";
        }
	}
    
    
  
}  
