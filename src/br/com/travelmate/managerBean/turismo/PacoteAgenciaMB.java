/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.turismo;

import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Pacotes;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class PacoteAgenciaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Pacotes> listaPacotes;

	public PacoteAgenciaMB() {
		listaPacotes = new ArrayList<Pacotes>();
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<Pacotes> getListaPacotes() {
		if (listaPacotes == null) {
			gerarListaPacotes();
		}
		return listaPacotes;
	}

	public void setListaPacotes(List<Pacotes> listaPacotes) {
		this.listaPacotes = listaPacotes;
	}

	public void gerarListaPacotes() {
		String dataInicial = Formatacao.SubtarirDatas(new Date(), 30, "yyyy/MM/dd");
		String sql = "Select p from Pacotes p where p.operacao='Agencia' and p.unidadenegocio.idunidadeNegocio="
				+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio()
				+ "  and p. order by p.vendas.dataVenda";
		listaPacotes = GerarListas.listarPacotes(sql);

	}

	public String novoPacotes() {
		return "cadpacote";
	}

	public String editarPacote(Pacotes pacote) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("pacote", pacote);
		return "cadpacote";
	}

}
