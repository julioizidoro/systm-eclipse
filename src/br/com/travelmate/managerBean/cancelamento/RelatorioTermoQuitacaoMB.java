package br.com.travelmate.managerBean.cancelamento;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class RelatorioTermoQuitacaoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Cancelamento cancelamento;
	private String banco;
	private String titular;
	private String agencia;
	private String conta;
	private String cpf;
	private String nomeCpf;
	private boolean mascara = true;
	private boolean semmascara = false;
	private String moedaNacional;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        cancelamento = (Cancelamento) session.getAttribute("cancelamento");
        session.removeAttribute("cancelamento");
        if (!usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getNome().equalsIgnoreCase("Paraguai")) {
			nomeCpf = "CPF";
			mascara = true;
			semmascara = false;
		}else {
			nomeCpf = "RUC";
			semmascara = true;
			mascara = false;
		}
        moedaNacional = usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla();
	}

	public Cancelamento getCancelamento() {
		return cancelamento;
	}

	public void setCancelamento(Cancelamento cancelamento) {
		this.cancelamento = cancelamento;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	 
    public String getNomeCpf() {
		return nomeCpf;
	}

	public void setNomeCpf(String nomeCpf) {
		this.nomeCpf = nomeCpf;
	}

	public boolean isMascara() {
		return mascara;
	}

	public void setMascara(boolean mascara) {
		this.mascara = mascara;
	}

	public boolean isSemmascara() {
		return semmascara;
	}

	public void setSemmascara(boolean semmascara) {
		this.semmascara = semmascara;
	}

	public String gerarRelatorioTermoQuitacao() throws SQLException, IOException {
    	ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
   	 	String caminhoRelatorio = ("/reports/cancelamento/termoQuitacao.jasper");
        Map<String, Object> parameters = new HashMap<String, Object>();
        File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
        BufferedImage logo = ImageIO.read(f);  
        parameters.put("logo", logo);
        banco =   new String(banco.getBytes("ISO-8859-1"), "UTF-8");
        parameters.put("banco", banco);
        parameters.put("agencia", agencia);
        parameters.put("conta", conta);
        parameters.put("titular", titular);
        parameters.put("texto", gerarTexto());
        parameters.put("cpfcliente", cancelamento.getVendas().getCliente().getCpf());
        parameters.put("cpf", cpf);
        if (usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getNome().equalsIgnoreCase("Paraguai")) {
			parameters.put("nomecpf", "RUC:");
		}else {
			parameters.put("nomecpf", "CPF:");
		}
        parameters.put("nome", cancelamento.getVendas().getCliente().getNome());
        parameters.put("sql", "SELECT idcancelamento  FROM cancelamento WHERE idcancelamento=1");
        GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
        try {
        	gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "TermoCancelamento"+cancelamento.getVendas().getIdvendas()+".pdf", null);
        } catch (JRException ex1) {
            Logger.getLogger(RelatorioTermoQuitacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
        } catch (IOException ex) {
            Logger.getLogger(RelatorioTermoQuitacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public String cancelar(){
    	RequestContext.getCurrentInstance().closeDialog(null);
    	return "";
    }
    
    
    public String gerarTexto() {
    		String texto = "Eu, "  +  cancelamento.getVendas().getCliente().getNome()  + ", " + cancelamento.getVendas().getCliente().getPaisNascimento() + ", portador do CPF/MF n.º  " +
    				  cpf + ", residente e domiciliado na " + cancelamento.getVendas().getCliente().getTipologradouro() + 
    				  cancelamento.getVendas().getCliente().getLogradouro();
    		if (!usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getNome().equalsIgnoreCase("Paraguai")) {
        		texto = texto +  " CEP " + cancelamento.getVendas().getCliente().getCep();
			}
    		texto = texto + ", " + cancelamento.getVendas().getCliente().getCidade() + ", " + cancelamento.getVendas().getCliente().getEstado() +
    				 ", declaro a quem possa interessar que recebi da empresa TRAVELMATE INTERCÂMBIO E TURISMO LTDA, pessoa jurídica de direito privado, "
    				 + "inscrita sob o CNPJ/MF n.º 05.138.734/0001-55, a quantia de " + moedaNacional +
    				 Formatacao.formatarFloatString(cancelamento.getValorreembolso()) + ", referente a reembolso em decorrência do cancelamento do programa de " +
    				 "" + cancelamento.getVendas().getProdutos().getDescricao() + " no " + cancelamento.getVendas().getFornecedorcidade().getCidade().getPais().getNome() + " .";
    				 
    		return texto;
    }
    
    
    
    
    
	
}
