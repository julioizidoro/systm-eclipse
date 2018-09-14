package br.com.travelmate.managerBean.voluntariadoprojeto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.VoluntariadoProjetoValorFacade;
import br.com.travelmate.model.Voluntariadoprojeto;
import br.com.travelmate.model.Voluntariadoprojetovalor;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class EditarValoresVoluntariadoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date dataInicialAntiga;
    private Date dataFinalAntiga; 
    private Date dataInicialNova;
    private Date dataFinalNova; 
    private List<Voluntariadoprojeto> listaVoluntariadoProjeto;
    
    @SuppressWarnings("unchecked")
	@PostConstruct
    public void init(){ 
    	 FacesContext fc = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
         listaVoluntariadoProjeto =  (List<Voluntariadoprojeto>) session.getAttribute("listaVoluntariadoProjeto");
         session.removeAttribute("listaVoluntariadoProjeto");
    }

    public Date getDataInicialAntiga() {
		return dataInicialAntiga;
	}

	public void setDataInicialAntiga(Date dataInicialAntiga) {
		this.dataInicialAntiga = dataInicialAntiga;
	}

	public Date getDataFinalAntiga() {
		return dataFinalAntiga;
	}

	public void setDataFinalAntiga(Date dataFinalAntiga) {
		this.dataFinalAntiga = dataFinalAntiga;
	}

	public Date getDataInicialNova() {
		return dataInicialNova;
	}

	public void setDataInicialNova(Date dataInicialNova) {
		this.dataInicialNova = dataInicialNova;
	}

	public Date getDataFinalNova() {
		return dataFinalNova;
	}

	public void setDataFinalNova(Date dataFinalNova) {
		this.dataFinalNova = dataFinalNova;
	}

	public List<Voluntariadoprojeto> getListaVoluntariadoProjeto() {
		return listaVoluntariadoProjeto;
	}

	public void setListaVoluntariadoProjeto(List<Voluntariadoprojeto> listaVoluntariadoProjeto) {
		this.listaVoluntariadoProjeto = listaVoluntariadoProjeto;
	}

	public String salvar() {
		VoluntariadoProjetoValorFacade voluntariadoProjetoValorFacade = new VoluntariadoProjetoValorFacade();
		for (int i = 0; i < listaVoluntariadoProjeto.size(); i++) {
			for (int j = 0; j < listaVoluntariadoProjeto.get(i).getVoluntariadoprojetovalorList().size(); j++) {
				Voluntariadoprojetovalor voluntariadoprojetovalor = listaVoluntariadoProjeto.get(i)
						.getVoluntariadoprojetovalorList().get(j);
				if (voluntariadoprojetovalor.getDatainicial() != null && voluntariadoprojetovalor.getDatafinal() != null) {
					String dataIniVoluntariado = Formatacao.ConvercaoDataPadrao(voluntariadoprojetovalor.getDatainicial());
					String dataFinVoluntariado = Formatacao.ConvercaoDataPadrao(voluntariadoprojetovalor.getDatafinal());
					String dataIniAntiga = Formatacao.ConvercaoDataPadrao(dataInicialAntiga);
					String dataFinAntiga = Formatacao.ConvercaoDataPadrao(dataFinalAntiga);
					if (dataIniVoluntariado.equalsIgnoreCase(dataIniAntiga) && dataFinVoluntariado.equalsIgnoreCase(dataFinAntiga)) {
						voluntariadoprojetovalor.setDatainicial(dataInicialNova);
						voluntariadoprojetovalor.setDatafinal(dataFinalNova);
						voluntariadoProjetoValorFacade.salvar(voluntariadoprojetovalor);
					}
				}
			}
		}
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
    
    public String cancelar() {
    	RequestContext.getCurrentInstance().closeDialog(null);
		return "";
    }

}
