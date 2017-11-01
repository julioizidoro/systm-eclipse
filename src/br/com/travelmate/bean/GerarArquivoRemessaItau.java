/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.bean;

import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.financeiro.boleto.ArquivoRemessaAtualizar;
import br.com.travelmate.managerBean.financeiro.boleto.ArquivoRemessaCancelar;
import br.com.travelmate.managerBean.financeiro.boleto.ArquivoRemessaEnviar;
import br.com.travelmate.managerBean.financeiro.contasReceber.EventoContasReceberBean;
import br.com.travelmate.managerBean.financeiro.relatorios.RetornoBean;
import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Ftp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;


/**
 *
 * @author Wolverine
 */
public class GerarArquivoRemessaItau {
    
    private List<Contasreceber> listaContas;
    private FileWriter remessa;
    private UsuarioLogadoMB usuarioLogadoMB;
    private int numeroSequencial=0;
    private String nomeArquivo;
    private String nomeFTP;
    private Unidadenegocio unidadeMatriz;
    private List<RetornoBean> listaEnvidas;
    private Banco bancoFranquia;
    

    public GerarArquivoRemessaItau(List<Contasreceber> lista, UsuarioLogadoMB usuarioLogadoMB, String nomeArquivo, String nomeFTP, Unidadenegocio unidade, Banco bancoFranquia) {
        this.listaContas = lista;
        this.usuarioLogadoMB = usuarioLogadoMB;
        this.nomeArquivo = nomeArquivo;
        this.nomeFTP = nomeFTP;
        this.unidadeMatriz = unidade;
        listaEnvidas = new ArrayList<RetornoBean>();
        this.bancoFranquia = bancoFranquia;
        nomeFTP = nomeFTP;
        iniciarRemessa();
    }
    
    
    
    public List<RetornoBean> getListaEnvidas() {
		return listaEnvidas;
	}



	public void setListaEnvidas(List<RetornoBean> listaEnvidas) {
		this.listaEnvidas = listaEnvidas;
	}



	private void iniciarRemessa(){
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
    	ServletContext request = (ServletContext) facesContext.getExternalContext().getContext();
    	String pasta = request.getRealPath("");
        if (this.listaContas==null){
            String sql = "Select c from Contasreceber c where c.situacao='am' and c.boletoenviado=0";
            ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
            this.listaContas = contasReceberFacade.listar(sql);
        }
        if (this.listaContas!=null){  
            try {
            	pasta = pasta + "\\remessa\\" + nomeArquivo;
            	File arquivo = new File(pasta);
                remessa = new FileWriter(arquivo);
                try { 
                    lerConta();  
                } catch (Exception ex) {
                    Logger.getLogger(ArquivoRemessaEnviar.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(ArquivoRemessaEnviar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
    private void lerConta() throws IOException, Exception{
        for(int i=0;i<listaContas.size();i++){
            if (listaContas.get(i).getDataalterada()){
                atualizarBoleto(listaContas.get(i));
            }else if (listaContas.get(i).getBoletocancelado()){
                 cancelarBoleto(listaContas.get(i));
            }else {
                enviarBoleto(listaContas.get(i));
            }
        }
        remessa.close();
        confirmarContas();
       // enviarArquivoFTP();
    }
    
    private void atualizarBoleto(Contasreceber conta) throws IOException, Exception{
    	Unidadenegocio unidadePassar = null;
		Banco bancoPassar = null;    
    		numeroSequencial++;
        ArquivoRemessaAtualizar arquivoRemessaAtualizar = new ArquivoRemessaAtualizar();
        if (conta.getVendas().getUnidadenegocio().getIdunidadeNegocio()>2){
        		unidadePassar = unidadeMatriz;
        		bancoPassar = bancoFranquia;
        }else {
    			bancoPassar = conta.getVendas().getUnidadenegocio().getBanco();
    			unidadePassar =conta.getVendas().getUnidadenegocio();	
        }
        remessa.write(arquivoRemessaAtualizar.gerarHeader(conta, numeroSequencial, unidadePassar, bancoPassar.getAgencia(), bancoPassar.getConta(), bancoPassar.getDigitoconta()));
        numeroSequencial++;
        remessa.write(arquivoRemessaAtualizar.gerarDetalhe(conta, numeroSequencial, unidadePassar, bancoPassar.getAgencia(), bancoPassar.getConta(), bancoPassar.getDigitoconta()));
        numeroSequencial++;
        remessa.write(arquivoRemessaAtualizar.gerarTrailer(numeroSequencial));
    }
    
    private void cancelarBoleto(Contasreceber conta) throws IOException, Exception {
    		Unidadenegocio unidadePassar = null;
    		Banco bancoPassar = null;
        numeroSequencial++;
        ArquivoRemessaCancelar arquivoRemessaCancelar = new ArquivoRemessaCancelar();
        if (conta.getVendas().getUnidadenegocio().getIdunidadeNegocio()>2){
        		unidadePassar = unidadeMatriz;
        		bancoPassar = bancoFranquia;
        }else {
    			bancoPassar = conta.getVendas().getUnidadenegocio().getBanco();
    			unidadePassar =conta.getVendas().getUnidadenegocio();	
        }
        remessa.write(arquivoRemessaCancelar.gerarHeader(conta, numeroSequencial));
        numeroSequencial++;
        remessa.write(arquivoRemessaCancelar.gerarDetalhe(conta, numeroSequencial));
        numeroSequencial++;
        remessa.write(arquivoRemessaCancelar.gerarTrailer(numeroSequencial));
    }
    
    private void enviarBoleto(Contasreceber conta) throws IOException, Exception{
    	Unidadenegocio unidadePassar = null;
    	Banco bancoPassar = null;
        numeroSequencial++;
        ArquivoRemessaEnviar arquivoRemessaNormal = new ArquivoRemessaEnviar();
        if (conta.getVendas().getUnidadenegocio().getIdunidadeNegocio()>2){
        	unidadePassar = unidadeMatriz;
        	bancoPassar = bancoFranquia;
        }else {
    		bancoPassar = conta.getVendas().getUnidadenegocio().getBanco();
    		unidadePassar =conta.getVendas().getUnidadenegocio();	
    	}
        remessa.write(arquivoRemessaNormal.gerarHeader(conta, numeroSequencial, unidadePassar, bancoPassar.getAgencia(), bancoPassar.getConta(), bancoPassar.getDigitoconta()));
        numeroSequencial++;
        remessa.write(arquivoRemessaNormal.gerarDetalhe(conta, numeroSequencial, unidadePassar, bancoPassar.getAgencia(), bancoPassar.getConta(), bancoPassar.getDigitoconta()));
        numeroSequencial++;
        remessa.write(arquivoRemessaNormal.gerarMulta(conta, numeroSequencial, unidadePassar));
        numeroSequencial++;
        remessa.write(arquivoRemessaNormal.gerarTrailer(numeroSequencial));
        RetornoBean r = new RetornoBean();
        r.setCodigoOcorrencia("01");
        r.setNossoNumero(conta.getNossonumero());
        r.setDataPagamento(Formatacao.ConvercaoDataPadrao(conta.getDatavencimento()));
        r.setNomePagador(conta.getVendas().getCliente().getNome());
        r.setValorJuros(0.0f);
        r.setValorTitulo(conta.getValorparcela());
        listaEnvidas.add(r);
    }
    
    private void confirmarContas(){
        ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
        for(int i=0;i<listaContas.size();i++){
            Contasreceber conta = listaContas.get(i);
            conta.setBoletoenviado(true);
            conta.setDataalterada(false);
            conta.setDataenvio(new Date());
            conta.setSituacao("am");
            contasReceberFacade.salvar(conta);
        }
    }
    
    public void enviarArquivoFTP(){
    	FacesContext facesContext = FacesContext.getCurrentInstance(); 
    	ServletContext request = (ServletContext) facesContext.getExternalContext().getContext();
    	String pasta = request.getRealPath("");
		 FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
			Ftpdados dadosFTP = null;
			try {
				dadosFTP = ftpDadosFacade.getFTPDados();
				Ftp ftp = new Ftp(dadosFTP.getHostupload(), dadosFTP.getUser(), dadosFTP.getPassword());
				ftp.conectar();
				pasta = pasta + "\\remessa\\" + nomeArquivo;
				File file = new File(pasta);
				ftp.enviarArquivoRemessa(file, nomeArquivo, "/systm/remessa/");
				ftp.desconectar();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
    
    
}
