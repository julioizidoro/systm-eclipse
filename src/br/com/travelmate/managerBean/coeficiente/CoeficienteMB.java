package br.com.travelmate.managerBean.coeficiente;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.CoeficienteJurosFacade;
import br.com.travelmate.model.Coeficientejuros;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CoeficienteMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Coeficientejuros> listaCoeficiente;
	private String tipo;

	@PostConstruct
	public void init() throws SQLException {
	}

	public List<Coeficientejuros> getListaCoeficiente() {
		return listaCoeficiente;
	}

	public void setListaCoeficiente(List<Coeficientejuros> listaCoeficiente) {
		this.listaCoeficiente = listaCoeficiente;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String novo() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 290);
		RequestContext.getCurrentInstance().openDialog("cadCoeficiente", options, null);
		return "";
	}

	public void gerarlistaCoeficiente() throws SQLException {
		CoeficienteJurosFacade coeficienteJurosFacade = new CoeficienteJurosFacade();
		String sql = " Select c From Coeficientejuros c ";
		if (tipo != null && !tipo.equalsIgnoreCase("sn")) {
			sql = sql + " where c.tipo='" + tipo + "'";
		}
		sql = sql + " order by c.numeroParcelas";
		listaCoeficiente = coeficienteJurosFacade.listar(sql);
		if (listaCoeficiente == null) {
			listaCoeficiente = new ArrayList<Coeficientejuros>();
		}
	}

	public String editar(Coeficientejuros coeficientejuros) {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 290);
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("coeficientejuros", coeficientejuros);
		RequestContext.getCurrentInstance().openDialog("cadCoeficiente", options, null);
		return "";
	}
	
	public void retornoDialogNovo(SelectEvent event) throws SQLException{
		Coeficientejuros coeficientejuros = (Coeficientejuros) event.getObject();
		if (coeficientejuros.getIdcoeficienteJuros() != null) {
			Mensagem.lancarMensagemInfo("Salvo com Sucesso", "");
			gerarlistaCoeficiente();
		}
	}

}
