package br.com.travelmate.managerBean.OrcamentoCurso;

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

import br.com.travelmate.facade.CoProdutosFacade;
import br.com.travelmate.facade.GrupoObrigatorioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Coprodutos;
import br.com.travelmate.model.Grupoobrigatorio;

@Named
@ViewScoped
public class VincularCoProdutoMB implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
	private Coprodutos coprodutos;
	private List<Coprodutos> listaCoProdutos;
	private Coprodutos produtoVincular;
	private List<Grupoobrigatorio> listaGrupoObrigatorio;
	private boolean menoridade;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        coprodutos = (Coprodutos) session.getAttribute("coprodutos");
        session.removeAttribute("coprodutos");
        gerarListaCoProdutosVincular();
        gerarListaGrupoObrigatorios();
	}


	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}


	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}


	public Coprodutos getCoprodutos() {
		return coprodutos;
	}


	public void setCoprodutos(Coprodutos coprodutos) {
		this.coprodutos = coprodutos;
	}


	public List<Coprodutos> getListaCoProdutos() {
		return listaCoProdutos;
	}


	public void setListaCoProdutos(List<Coprodutos> listaCoProdutos) {
		this.listaCoProdutos = listaCoProdutos;
	}


	public List<Grupoobrigatorio> getListaGrupoObrigatorio() {
		return listaGrupoObrigatorio;
	}


	public void setListaGrupoObrigatorio(List<Grupoobrigatorio> listaGrupoObrigatorio) {
		this.listaGrupoObrigatorio = listaGrupoObrigatorio;
	}
	
	
	public Coprodutos getProdutoVincular() {
		return produtoVincular;
	}


	public void setProdutoVincular(Coprodutos produtoVincular) {
		this.produtoVincular = produtoVincular;
	}


	public boolean isMenoridade() {
		return menoridade;
	}


	public void setMenoridade(boolean menoridade) {
		this.menoridade = menoridade;
	}


	public void gerarListaGrupoObrigatorios(){
		GrupoObrigatorioFacade grupoObrigatorioFacade = new GrupoObrigatorioFacade();
		String sql = "Select g from Grupoobrigatorio g where g.coprodutos.idcoprodutos=" + coprodutos.getIdcoprodutos();
		listaGrupoObrigatorio = grupoObrigatorioFacade.listar(sql);
		if (listaGrupoObrigatorio==null){
			listaGrupoObrigatorio = new ArrayList<Grupoobrigatorio>();
		}
	}
	
	public void gerarListaCoProdutosVincular(){
		String sql = "Select c from Coprodutos c  where c.fornecedorcidadeidioma.idfornecedorcidadeidioma=" + coprodutos.getFornecedorcidadeidioma().getIdfornecedorcidadeidioma();
		CoProdutosFacade coProdutosFacade = new CoProdutosFacade();
		listaCoProdutos = coProdutosFacade.listar(sql);
		if (listaCoProdutos==null){
			listaCoProdutos = new ArrayList<Coprodutos>();
    	}
    }
	
	public String salvar(){
		GrupoObrigatorioFacade grupoObrigatorioFacade = new GrupoObrigatorioFacade();
		for(int i=0;i<listaGrupoObrigatorio.size();i++){
			grupoObrigatorioFacade.salvar(listaGrupoObrigatorio.get(i));
		}
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public void adicionar(){
		Grupoobrigatorio grupoobrigatorio = new Grupoobrigatorio();
		grupoobrigatorio.setCoprodutos(coprodutos);
		grupoobrigatorio.setProduto(produtoVincular);
		grupoobrigatorio.setMenorobrigatorio(menoridade);
		listaGrupoObrigatorio.add(grupoobrigatorio);
	}
	
	public void excluir(Grupoobrigatorio grupoobrigatorio){
		if (grupoobrigatorio.getIdgrupoobrigatorio()==null){
			listaGrupoObrigatorio.remove(grupoobrigatorio);
		}else {
			GrupoObrigatorioFacade grupoObrigatorioFacade = new GrupoObrigatorioFacade();
			grupoObrigatorioFacade.excluir(grupoobrigatorio);
			listaGrupoObrigatorio.remove(grupoobrigatorio);
		}
	}
		

}
