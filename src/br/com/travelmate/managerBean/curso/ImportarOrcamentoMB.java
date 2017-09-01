package br.com.travelmate.managerBean.curso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.OCursoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class ImportarOrcamentoMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private String nome;
	private List<Ocurso> listaOCurso;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<Ocurso> getListaOCurso() {
		return listaOCurso;
	}
	
	public void setListaOCurso(List<Ocurso> listaOCurso) {
		this.listaOCurso = listaOCurso;
	}
	
	public String carregarListaOrcamento(){
        if (nome==null){
            nome = "";
        }
        OCursoFacade oCursoFacade = new OCursoFacade();
        String sql = "Select o from Ocurso o where o.cliente.nome like '%" + nome + "%' and o.usuario.unidadenegocio.idunidadeNegocio=" + 
        usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio() + " order by o.dataorcamento desc, o.idocurso desc";
        listaOCurso = oCursoFacade.listar(sql);
        if (listaOCurso==null){
        	listaOCurso = new ArrayList<Ocurso>();
        }
        return "";
    }
	
	public void selecionarOrcamento(Ocurso ocurso){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("ocurso", ocurso);
        RequestContext.getCurrentInstance().closeDialog(ocurso);
    }
	
	public String mensagemPromocaoAtrasada(Ocurso ocurso){
		if(ocurso!=null){
			Date hoje = new Date();
			Date ontem = new Date();
			try {
				ontem = Formatacao.SomarDiasDatas(hoje, -1);
			} catch (Exception e) { 
				e.printStackTrace();
			}
			if(ocurso.getDatavalidade()!=null && ocurso.getDatavalidade().before(hoje) && ocurso.getDatavalidade().after(ontem)){
				return "- Orçamento possui promoção encerrando hoje.";
			}else if(ocurso.getDatavalidade()!=null && ocurso.getDatavalidade().before(hoje)){
				return "- Orçamento possui promoção já encerrada.";
			}else return "";
		}return "";
	}
	
	public String corPromocaoAtrasada(Ocurso ocurso){
		if(ocurso!=null){
			Date hoje = new Date();
			Date ontem = new Date();
			try {
				ontem = Formatacao.SomarDiasDatas(hoje, -1);
			} catch (Exception e) { 
				e.printStackTrace();
			}
			if(ocurso.getDatavalidade()!=null && ocurso.getDatavalidade().before(hoje) && ocurso.getDatavalidade().after(ontem)){
				return "color:cornflowerblue;";
			}else if(ocurso.getDatavalidade()!=null && ocurso.getDatavalidade().before(hoje)){
				return "color:red;";
			}else return ""; 
		}return "";
	}

}
