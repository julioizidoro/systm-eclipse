package br.com.travelmate.managerBean.voluntariadoprojeto;

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

import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.VoluntariadoProjetoCursoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Voluntariadoprojeto;
import br.com.travelmate.model.Voluntariadoprojetocurso;

@Named
@ViewScoped
public class VoluntariadoProjetoCursoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Voluntariadoprojetocurso voluntariadoprojetocurso;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private List<Filtroorcamentoproduto> listaFiltroorcamentoproduto;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Voluntariadoprojeto voluntariadoprojeto = (Voluntariadoprojeto) session.getAttribute("voluntariadoprojeto");
		session.removeAttribute("voluntariadoprojeto");
		if (voluntariadoprojeto != null) {
			gerarListaProdutosOrcamento();
			VoluntariadoProjetoCursoFacade voluntariadoProjetoCursoFacade = new VoluntariadoProjetoCursoFacade();
			voluntariadoprojetocurso = voluntariadoProjetoCursoFacade.consultar(
					"select v from Voluntariadoprojetocurso v where" + " v.voluntariadoprojeto.idvoluntariadoprojeto="
							+ voluntariadoprojeto.getIdvoluntariadoprojeto());
			if (voluntariadoprojetocurso == null) {
				voluntariadoprojetocurso = new Voluntariadoprojetocurso();
				voluntariadoprojetocurso.setVoluntariadoprojeto(voluntariadoprojeto);
			}
		}
	}

	public Voluntariadoprojetocurso getVoluntariadoprojetocurso() {
		return voluntariadoprojetocurso;
	}

	public void setVoluntariadoprojetocurso(Voluntariadoprojetocurso voluntariadoprojetocurso) {
		this.voluntariadoprojetocurso = voluntariadoprojetocurso;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public List<Filtroorcamentoproduto> getListaFiltroorcamentoproduto() {
		return listaFiltroorcamentoproduto;
	}

	public void setListaFiltroorcamentoproduto(List<Filtroorcamentoproduto> listaFiltroorcamentoproduto) {
		this.listaFiltroorcamentoproduto = listaFiltroorcamentoproduto;
	}

	public String salvar() {
		VoluntariadoProjetoCursoFacade voluntariadoProjetoCursoFacade = new VoluntariadoProjetoCursoFacade();
		voluntariadoprojetocurso = voluntariadoProjetoCursoFacade.salvar(voluntariadoprojetocurso);
		RequestContext.getCurrentInstance().closeDialog(voluntariadoprojetocurso);
		return null;
	}

	public String cancelar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return null;
	}

	public void gerarListaProdutosOrcamento() {
		FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
		String sql = "select f from Filtroorcamentoproduto f where f.produtos.idprodutos="
				+ aplicacaoMB.getParametrosprodutos().getCursos()
				+ " and f.produtosorcamento.tipo='O' or f.produtosorcamento.tipo='C' order by f.produtosorcamento.descricao";
		listaFiltroorcamentoproduto = filtroOrcamentoProdutoFacade.pesquisar(sql);
		if (listaFiltroorcamentoproduto == null) {
			listaFiltroorcamentoproduto = new ArrayList<Filtroorcamentoproduto>();
		}
	}

}
