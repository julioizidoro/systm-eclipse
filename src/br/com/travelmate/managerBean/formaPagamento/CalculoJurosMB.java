package br.com.travelmate.managerBean.formaPagamento;

import java.io.Serializable;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.CoeficienteJurosFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Coeficientejuros;

@Named
@ViewScoped
public class CalculoJurosMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Float total;
	private boolean habilitaFormaPagamento03 = true;
	private boolean habilitaFormaPagamento04 = true;
	private boolean formaPagamento03 = false;
	private boolean formaPagamento04 = false;
	private float percentualEntrada3;
	private float valorEntrada3;
	private int numParcelas3;
	private float percentualSaldo3;
	private float valorSaldo3;
	private float valorParcela3;
	private float valorParcela4;
	private int numParcelas4;
	private float valoJuros;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        total = (Float) session.getAttribute("total");
        session.removeAttribute("total");
	}
	
	

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public boolean isHabilitaFormaPagamento03() {
		return habilitaFormaPagamento03;
	}

	public void setHabilitaFormaPagamento03(boolean habilitaFormaPagamento03) {
		this.habilitaFormaPagamento03 = habilitaFormaPagamento03;
	}

	public boolean isHabilitaFormaPagamento04() {
		return habilitaFormaPagamento04;
	}

	public void setHabilitaFormaPagamento04(boolean habilitaFormaPagamento04) {
		this.habilitaFormaPagamento04 = habilitaFormaPagamento04;
	}

	public float getPercentualEntrada3() {
		return percentualEntrada3;
	}

	public void setPercentualEntrada3(float percentualEntrada3) {
		this.percentualEntrada3 = percentualEntrada3;
	}

	public float getValorEntrada3() {
		return valorEntrada3;
	}

	public void setValorEntrada3(float valorEntrada3) {
		this.valorEntrada3 = valorEntrada3;
	}

	public int getNumParcelas3() {
		return numParcelas3;
	}

	public void setNumParcelas3(int numParcelas3) {
		this.numParcelas3 = numParcelas3;
	}

	public float getPercentualSaldo3() {
		return percentualSaldo3;
	}

	public void setPercentualSaldo3(float percentualSaldo3) {
		this.percentualSaldo3 = percentualSaldo3;
	}

	public float getValorSaldo3() {
		return valorSaldo3;
	}

	public void setValorSaldo3(float valorSaldo3) {
		this.valorSaldo3 = valorSaldo3;
	}

	public float getValorParcela3() {
		return valorParcela3;
	}

	public void setValorParcela3(float valorParcela3) {
		this.valorParcela3 = valorParcela3;
	}

	public float getValorParcela4() {
		return valorParcela4;
	}

	public void setValorParcela4(float valorParcela4) {
		this.valorParcela4 = valorParcela4;
	}

	public int getNumParcelas4() {
		return numParcelas4;
	}

	public void setNumParcelas4(int numParcelas4) {
		this.numParcelas4 = numParcelas4;
	}

	public float getValoJuros() {
		return valoJuros;
	}

	public void setValoJuros(float valoJuros) {
		this.valoJuros = valoJuros;
	}

	public boolean isFormaPagamento03() {
		return formaPagamento03;
	}

	public void setFormaPagamento03(boolean formaPagamento03) {
		this.formaPagamento03 = formaPagamento03;
	}

	public boolean isFormaPagamento04() {
		return formaPagamento04;
	}

	public void setFormaPagamento04(boolean formaPagamento04) {
		this.formaPagamento04 = formaPagamento04;
	}

	public void calcularJuros3() throws SQLException {
		Float valorSaldo = 0.0f;
		Float saldo = 0.0f;
		Float valorEntrada = 0.0f;
		if (formaPagamento03) {
			habilitaFormaPagamento03 = false;
			habilitaFormaPagamento04 = true;
			formaPagamento04=false;
			valorSaldo = 0.0f;
			saldo = 0.0f;
			valorEntrada = 0.0f;
			valoJuros=total;
			if (percentualEntrada3 > 0) {
				valorEntrada = total * (percentualEntrada3 / 100);
				saldo = 100 - percentualEntrada3;
				valorSaldo = total - valorEntrada;
				valorEntrada3 = valorEntrada.floatValue();
				valorSaldo3 = valorSaldo.floatValue();
				percentualSaldo3 = saldo.floatValue();
			}
			valorSaldo = 0.0f;
			if (numParcelas3 > 0) {
				if (valorSaldo3 > 0) {
					valorSaldo = valorSaldo3;
					if (valorSaldo > 0) {
						CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
						Coeficientejuros cf = coneficienteJurosFacade.consultar(numParcelas3, "Juros Cliente");
	                    valorSaldo = (float) (valorSaldo * cf.getCoeficiente());
	                    valorParcela3 = valorSaldo.floatValue();
	                    valoJuros = (valorSaldo * numParcelas3)-valorSaldo3; 
					}
				}
			}
			
		}
	}

	public void calcularJuros4() throws SQLException {
		if (this.formaPagamento04) {
			habilitaFormaPagamento04=false;
			habilitaFormaPagamento03 = true;
			formaPagamento03=false;
			if (numParcelas4 > 0) {
				valoJuros = total;
				CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
				Coeficientejuros cf = coneficienteJurosFacade.consultar(numParcelas4, "Juros Banco");
				Float valor = (float) (total * cf.getCoeficiente());
				valorParcela4 = valor;
				valoJuros = (valor*numParcelas4)-valoJuros;
			}
		}
	}

	public String fechar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	

	public void verificarFormaPgamento03() {
		try {
			calcularJuros3();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (formaPagamento03) {
			habilitaFormaPagamento03 = false;
			habilitaFormaPagamento04=true;
			formaPagamento04=false;
		} else{
			habilitaFormaPagamento03 = true;
			habilitaFormaPagamento04=false;
			formaPagamento03=false;
		}
	}

	public void verificarFormaPgamento04() {
		try {
			calcularJuros4();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (formaPagamento04) {
			habilitaFormaPagamento04 = false;
			habilitaFormaPagamento03=true;
			formaPagamento03=false;
		} else{
			habilitaFormaPagamento04 = true;
			habilitaFormaPagamento03=false;
			formaPagamento04=false; 
		}
	}
	
	
	public String confirmar(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("valorJuros", valoJuros);
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
	}
}
