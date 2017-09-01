package br.com.travelmate.managerBean.curso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.OrcamentoCursoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Orcamentocurso;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class ImportarOrcamentoManualMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private String nome="";
	private List<Orcamentocurso> listaOrcamento;
	
	@PostConstruct()
	public void init() {
		gerarListaOrcamento();
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

	public List<Orcamentocurso> getListaOrcamento() {
		return listaOrcamento;
	}

	public void setListaOrcamento(List<Orcamentocurso> listaOrcamento) {
		this.listaOrcamento = listaOrcamento;
	}

	public void gerarListaOrcamento() {
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
		String sql = null;
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sql = "Select o from Orcamentocurso o where o.cliente.nome like '%" + nome
					+ "%' and o.situacao='Processo' and o.data>='" + dataConsulta + "' order by o.data desc";
		} else {
			sql = "Select o from Orcamentocurso o where o.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio()
					+ " and o.cliente.nome like '%" + nome + "%' and o.situacao='Processo' and o.data>='" + dataConsulta + "' order by o.data";
		}
		OrcamentoCursoFacade orcamentoCursoFacade = new OrcamentoCursoFacade();
		listaOrcamento = orcamentoCursoFacade.listarOrcamento(sql);
		if (listaOrcamento == null) {
			listaOrcamento = new ArrayList<Orcamentocurso>();
		}
	}
	
	public void selecionarOrcamento(Orcamentocurso orcamentocurso){
        RequestContext.getCurrentInstance().closeDialog(orcamentocurso);
    }

}
