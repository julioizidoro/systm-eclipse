package br.com.travelmate.managerBean.higherEducation.valoresHigherEducation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.dao.CambioDao;
import br.com.travelmate.dao.PaisDao;
 
import br.com.travelmate.facade.ValoresHeFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cidade; 
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Pais; 
import br.com.travelmate.model.Valoreshe; 
import br.com.travelmate.util.GerarListas;

@Named
@ViewScoped
public class CadValoresHigherEducationMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	
	@Inject
	private PaisDao paisDao;
	@Inject
	private CambioDao cambioDao;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Valoreshe valoreshe;
	private Fornecedorcidade fornecedorcidade;
	private List<Pais> listaPais;
	private Cidade cidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Pais pais;
	private List<Moedas> listaMoedas;
	private Moedas moedas;   

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		valoreshe = (Valoreshe) session.getAttribute("valor");
		session.removeAttribute("valor"); 
		 
		listaPais = paisDao.listar();
		carregarComboMoedas(); 
		if (valoreshe==null){
			valoreshe = new Valoreshe();
		}else {
			pais = valoreshe.getFornecedorcidade().getCidade().getPais();
			fornecedorcidade = valoreshe.getFornecedorcidade();
			cidade = valoreshe.getFornecedorcidade().getCidade();
			moedas = valoreshe.getMoedas();  
			listarFornecedorCidade();
		}
	} 
	
	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}


	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}


	public Valoreshe getValoreshe() {
		return valoreshe;
	}


	public void setValoreshe(Valoreshe valoreshe) {
		this.valoreshe = valoreshe;
	}


	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}


	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}


	public List<Pais> getListaPais() {
		return listaPais;
	}


	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}


	public Cidade getCidade() {
		return cidade;
	}


	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}


	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}


	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}


	public Pais getPais() {
		return pais;
	}


	public void setPais(Pais pais) {
		this.pais = pais;
	}


	public List<Moedas> getListaMoedas() {
		return listaMoedas;
	}


	public void setListaMoedas(List<Moedas> listaMoedas) {
		this.listaMoedas = listaMoedas;
	}


	public Moedas getMoedas() {
		return moedas;
	}


	public void setMoedas(Moedas moedas) {
		this.moedas = moedas;
	}


	public void listarFornecedorCidade() {
		int idProduto = aplicacaoMB.getParametrosprodutos().getHighereducation();
		if ((idProduto > 0) && (cidade != null)) {
			listaFornecedorCidade = GerarListas.listarFornecedorCidade(idProduto, cidade.getIdcidade());
		}
	}

	public void carregarComboMoedas() {
		listaMoedas = cambioDao.listaMoedas();
		if (listaMoedas == null) {
			listaMoedas = new ArrayList<Moedas>();
		}
	} 
	
	public String salvar(){
		valoreshe.setSituacao(true);
		valoreshe.setFornecedorcidade(fornecedorcidade);
		valoreshe.setMoedas(moedas);   
		ValoresHeFacade valoresHeFacade = new ValoresHeFacade();
		valoreshe=valoresHeFacade.salvar(valoreshe);
		RequestContext.getCurrentInstance().closeDialog(valoreshe);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
}
