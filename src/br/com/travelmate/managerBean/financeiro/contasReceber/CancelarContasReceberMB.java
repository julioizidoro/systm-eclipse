package br.com.travelmate.managerBean.financeiro.contasReceber;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.dao.CrmCobrancaContaDao;
import br.com.travelmate.dao.CrmCobrancaDao;
import br.com.travelmate.dao.CrmCobrancaHistoricoDao;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.financeiro.crmcobranca.CrmCobrancaBean;
import br.com.travelmate.model.Contasreceber;

@Named
@ViewScoped
public class CancelarContasReceberMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioLogadoMB usuarioLogadoMB; 
	@Inject
	private CrmCobrancaDao crmCobrancaDao; 
	@Inject
	private CrmCobrancaContaDao crmCobrancaContaDao;
	@Inject
	private CrmCobrancaHistoricoDao crmCobrancaHistoricoDao; 
	private List<Contasreceber> listaContas;
	private String observacao;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaContas = (List<Contasreceber>) session.getAttribute("contareceber");
		session.removeAttribute("contareceber");
	}

	public List<Contasreceber> getListaContas() {
		return listaContas;
	}

	public void setListaContas(List<Contasreceber> listaContas) {
		this.listaContas = listaContas;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String confirmarCancelamento() {
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		for (int i = 0; i < listaContas.size(); i++) {
			listaContas.get(i).setSituacao("cc");
			listaContas.get(i).setMotivocancelamento(observacao);
			contasReceberFacade.salvar(listaContas.get(i));
			if (listaContas.get(i).getCrmcobrancaconta() != null) {
				if (listaContas.get(i).getCrmcobrancaconta().getPaga() == false) {
					CrmCobrancaBean crmCobrancaBean = new CrmCobrancaBean(crmCobrancaDao, crmCobrancaContaDao, crmCobrancaHistoricoDao);
					crmCobrancaBean.baixar(listaContas.get(i), usuarioLogadoMB.getUsuario());
				}
			}
			EventoContasReceberBean eventoContasReceberBean = new EventoContasReceberBean("Cancelada pelo usuário",
					listaContas.get(i), usuarioLogadoMB.getUsuario());
		}
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	public String cancelar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

}
