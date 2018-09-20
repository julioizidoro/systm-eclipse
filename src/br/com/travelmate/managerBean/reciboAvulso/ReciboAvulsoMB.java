package br.com.travelmate.managerBean.reciboAvulso;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
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

import org.primefaces.context.RequestContext;

import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class ReciboAvulsoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private String nome;
	private float valor;
	private String produto;
	private String formaPagamento;
	private Date data;
	
	@PostConstruct()
	public void init() {
		data = new Date();
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String gerarRelatorioRecibo() throws SQLException, IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/recibo/reciboAvulso.jasper");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nome", nome);
		String valorExtenso = Formatacao.valorPorExtenso(valor, usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla());
		parameters.put("valorExtenso", valorExtenso);
		parameters.put("valorRecibo", valor);
		parameters.put("produto", produto);
		parameters.put("data", data);
		parameters.put("idusuario", usuarioLogadoMB.getUsuario().getIdusuario());
		parameters.put("formaPagamento", formaPagamento);
		parameters.put("consultor", usuarioLogadoMB.getUsuario().getNome());
		String moedaNacional = usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla();
		parameters.put("moedaNacional", moedaNacional);
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
		try {
			gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "recibo.pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(ReciboAvulsoMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(ReciboAvulsoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

}
