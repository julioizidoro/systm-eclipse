package br.com.travelmate.managerBean.cloud.midia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.travelmate.facade.AvisosDocsFacade;
import br.com.travelmate.facade.AvisosDocsUsuarioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Avisodocs;
import br.com.travelmate.model.Avisodocsusuario;
import br.com.travelmate.model.Usuario;

@Named
public class AvisoArquivoBean {

	private Avisodocs avisodocs;
	private Avisodocsusuario avisodocsusuario;
	private List<Usuario> listaUsuario;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;

	public AvisoArquivoBean(List<GerarAvisosDocsBean> listaGerarAviso) {
		super();
		AvisosDocsUsuarioFacade avisosDocsUsuarioFacade = new AvisosDocsUsuarioFacade();
		gerarListaUsuario();
		for (int i = 0; i < listaGerarAviso.size(); i++) {
			AvisosDocsFacade avisosDocsFacade = new AvisosDocsFacade();
			avisodocs = new Avisodocs();
			avisodocs.setCaminhoarquivo(listaGerarAviso.get(i).getCaminhoArquivoAviso());
			avisodocs.setNomearquivo(listaGerarAviso.get(i).getNomeArquivoAviso());
			avisodocs.setNomesalvo(listaGerarAviso.get(i).getNomeSalvoArquivoAviso());
			avisodocs = avisosDocsFacade.salvar(avisodocs);
			for (int j = 0; j < listaUsuario.size(); j++) {
				if (listaGerarAviso.get(i).isRestrito()) {
					if (listaUsuario.get(j).getTipo().equalsIgnoreCase("Gerencial")) {
						avisodocsusuario = new Avisodocsusuario();
						avisodocsusuario.setVisualizou(false);
						avisodocsusuario.setUsuario(listaUsuario.get(j));
						avisodocsusuario.setAvisodocs(avisodocs);
						avisodocsusuario.setData(new Date());
						avisosDocsUsuarioFacade.salvar(avisodocsusuario);
					}
				} else {
					avisodocsusuario = new Avisodocsusuario();
					avisodocsusuario.setVisualizou(false);
					avisodocsusuario.setUsuario(listaUsuario.get(j));
					avisodocsusuario.setAvisodocs(avisodocs);
					avisodocsusuario.setData(new Date());
					avisosDocsUsuarioFacade.salvar(avisodocsusuario);

				}

			}
		}
	}

	public Avisodocs getAvisodocs() {
		return avisodocs;
	}

	public void setAvisodocs(Avisodocs avisodocs) {
		this.avisodocs = avisodocs;
	}

	public Avisodocsusuario getAvisodocsusuario() {
		return avisodocsusuario;
	}

	public void setAvisodocsusuario(Avisodocsusuario avisodocsusuario) {
		this.avisodocsusuario = avisodocsusuario;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public void salvarAvisoDocsUsuario(Avisodocs aviso) {
		AvisosDocsUsuarioFacade avisosDocsUsuarioFacade = new AvisosDocsUsuarioFacade();
		if (listaUsuario == null || listaUsuario.isEmpty()) {
			gerarListaUsuario();
		}
		for (int i = 0; i < listaUsuario.size(); i++) {
			avisodocsusuario = new Avisodocsusuario();
			avisodocsusuario.setVisualizou(false);
			avisodocsusuario.setUsuario(listaUsuario.get(i));
			avisodocsusuario.setAvisodocs(aviso);
			avisodocsusuario = avisosDocsUsuarioFacade.salvar(avisodocsusuario);
		}
	}

	public void gerarListaUsuario() {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		listaUsuario = usuarioFacade.listar("Select u from Usuario u where u.situacao='Ativo'");
		if (listaUsuario == null || listaUsuario.isEmpty()) {
			listaUsuario = new ArrayList<>();
		}
	}

}
