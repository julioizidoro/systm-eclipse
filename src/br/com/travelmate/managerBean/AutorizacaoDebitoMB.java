package br.com.travelmate.managerBean;

import br.com.travelmate.managerBean.turismo.relatorios.AutorizacaoCartaoFactory;
import br.com.travelmate.managerBean.turismo.relatorios.AutorizacaoDebitoBean;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@ViewScoped
public class AutorizacaoDebitoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private AutorizacaoDebitoBean autorizacaoDebitoBean;
	private Cambio cambio;

	public AutorizacaoDebitoMB() {
		autorizacaoDebitoBean = new AutorizacaoDebitoBean();
		cambio = new Cambio();
	}

	public Cambio getCambio() {
		return cambio;
	}

	public void setCambio(Cambio cambio) {
		this.cambio = cambio;
	}

	public AutorizacaoDebitoBean getAutorizacaoDebitoBean() {
		return autorizacaoDebitoBean;
	}

	public void setAutorizacaoDebitoBean(AutorizacaoDebitoBean autorizacaoDebitoBean) {
		this.autorizacaoDebitoBean = autorizacaoDebitoBean;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public void gerarRelatorioAutorizacao() throws JRException, IOException {
		if ((cambio != null) && (cambio.getIdcambio() != null)) {
			autorizacaoDebitoBean.setMoeda(cambio.getMoedas().getSigla());
			autorizacaoDebitoBean.setValorcambio(Formatacao.formatarFloatString(cambio.getValor()));
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
					.getContext();
			String caminhoRelatorio = "/reports/relatorios/AutorizacaoCartao.jasper";
			Map parameters = new HashMap();
			String nomeArquivo = "AutorizacaoCartao.pdf";
			parameters.put("cidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getCidade());
			parameters.put("unidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getNomeFantasia());
			File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
			BufferedImage logo = ImageIO.read(f);
			parameters.put("logo", logo);
			List<AutorizacaoDebitoBean> lista = new ArrayList<AutorizacaoDebitoBean>();
			lista.add(autorizacaoDebitoBean);
			AutorizacaoCartaoFactory.setListaAutorizacaoCartao(lista);
			JRDataSource jrds = new JRBeanCollectionDataSource(AutorizacaoCartaoFactory.getListaAutorizacaoCartao());
			GerarRelatorio gerarRelatorio = new GerarRelatorio();
			gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, "AutorizacaoPagamento.pdf");
		} else {
			Mensagem.lancarMensagemErro("Atenção", "Câmbio não selecionado.");
		}
	}

	public String fecahrTela() {
		return "inicial";
	}

}
