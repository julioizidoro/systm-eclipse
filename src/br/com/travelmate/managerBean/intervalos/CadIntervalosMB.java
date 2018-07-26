package br.com.travelmate.managerBean.intervalos;

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

import br.com.travelmate.dao.OcursoFeriadoDao;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Ocursoferiado;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;

@Named
@ViewScoped
public class CadIntervalosMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Ocursoferiado intervalos;
	private List<Paisproduto> listaPais;
	private Pais pais;
	private List<Cidade> listaCidadeSelecionada;
	private Cidade cidade;
	private Fornecedorcidade fornecedor;
	private List<Fornecedorcidade> listaFornecedor;
	private boolean aparecercidade;
	private boolean aparecerlistacidade;
	
	@Inject
	private OcursoFeriadoDao oCursoFeriadoDao;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		intervalos = (Ocursoferiado) session.getAttribute("ocursoferiado");
		session.removeAttribute("ocursoferiado");
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		int idProduto = aplicacaoMB.getParametrosprodutos().getCursos();
		listaPais = paisProdutoFacade.listar(idProduto);
		if (intervalos != null) {
			pais = intervalos.getFornecedorcidade().getCidade().getPais();
			cidade = intervalos.getFornecedorcidade().getCidade();
			gerarListaFornecedor();
			fornecedor = intervalos.getFornecedorcidade();
			aparecercidade = true;
			aparecerlistacidade = false;
		} else {
			intervalos = new Ocursoferiado();
			fornecedor = new Fornecedorcidade();
			listaCidadeSelecionada = new ArrayList<Cidade>();
			aparecercidade = false;
			aparecerlistacidade = true;
		}
	}

	public List<Paisproduto> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Paisproduto> listaPais) {
		this.listaPais = listaPais;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Fornecedorcidade getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedorcidade fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Fornecedorcidade> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedorcidade> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public Ocursoferiado getIntervalos() {
		return intervalos;
	}

	public void setIntervalos(Ocursoferiado intervalos) {
		this.intervalos = intervalos;
	}

	public List<Cidade> getListaCidadeSelecionada() {
		return listaCidadeSelecionada;
	}

	public void setListaCidadeSelecionada(List<Cidade> listaCidadeSelecionada) {
		this.listaCidadeSelecionada = listaCidadeSelecionada;
	}

	public boolean isAparecercidade() {
		return aparecercidade;
	}

	public void setAparecercidade(boolean aparecercidade) {
		this.aparecercidade = aparecercidade;
	}

	public boolean isAparecerlistacidade() {
		return aparecerlistacidade;
	}

	public void setAparecerlistacidade(boolean aparecerlistacidade) {
		this.aparecerlistacidade = aparecerlistacidade;
	}

	public void gerarListaFornecedor() {
		String sql = "";
		if (intervalos.getIdocursoferiado() != null) {
			if (cidade != null && cidade.getIdcidade() != null) {
				sql = "select f from Fornecedorcidade f where f.cidade.idcidade=" + cidade.getIdcidade()
						+ " order by f.fornecedor.nome";
			}
		} else {
			if (listaCidadeSelecionada != null && listaCidadeSelecionada.size() > 0) {
				sql = "select f from Fornecedorcidade f where f.cidade.idcidade="
						+ listaCidadeSelecionada.get(0).getIdcidade() + " and f.ativo=1";
				for (int i = 1; i < listaCidadeSelecionada.size(); i++) {
					sql = sql + " or f.cidade.idcidade=" + listaCidadeSelecionada.get(i).getIdcidade();
				}
				sql = sql + " group by f.fornecedor.idfornecedor order by f.fornecedor.nome";
			}
		}
		FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
		listaFornecedor = fornecedorCidadeFacade.listar(sql);
		if (listaFornecedor == null) {
			listaFornecedor = new ArrayList<Fornecedorcidade>();
		}
	}

	public String salvar() {
		if (intervalos.getIdocursoferiado() != null) {
			intervalos.setFornecedorcidade(fornecedor);
			intervalos = oCursoFeriadoDao.salvar(intervalos);
			RequestContext.getCurrentInstance().closeDialog(null);
		} else {
			if (listaCidadeSelecionada != null) {
				FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
				Fornecedorcidade fornecedorcidade = new Fornecedorcidade();
				for (int i = 0; i < listaCidadeSelecionada.size(); i++) {
					Ocursoferiado ocursoferiado = intervalos;
					fornecedorcidade = fornecedorCidadeFacade.getFonecedorCidade(
							fornecedor.getFornecedor().getIdfornecedor(), listaCidadeSelecionada.get(i).getIdcidade());
					if(fornecedorcidade!=null){
						ocursoferiado.setFornecedorcidade(fornecedorcidade); 
						ocursoferiado = oCursoFeriadoDao.salvar(ocursoferiado);
					}
				} 
				RequestContext.getCurrentInstance().closeDialog(null);
			}
		} 
		return "";
	}

	public String cancelar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

}
