package br.com.travelmate.managerBean.unidadenegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.bean.UnidadeBean;
import br.com.travelmate.bean.UsuarioBean;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class UnidadeNegocioMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private String nome = "";
	private List<Unidadenegocio> listaUnidade;
	private String imagemEditar = "";
	private String tituloEditar = "";

	@PostConstruct
	public void init() {
		gerarlistaUnidade();
		if (usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isEditarunidade()) {
			tituloEditar = "Editar (Alterar informações da unidade)";
			imagemEditar = "../../resources/img/editaricon.png";
		}else{
			tituloEditar = "Visualizar (Informações sobre a unidade)";
			imagemEditar = "../../resources/img/verOrcamento.png";
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	public String novaUnidade() {
		return "cadUnidadeNegocio";
	}

	public String getImagemEditar() {
		return imagemEditar;
	}

	public void setImagemEditar(String imagemEditar) {
		this.imagemEditar = imagemEditar;
	}

	public String getTituloEditar() {
		return tituloEditar;
	}

	public void setTituloEditar(String tituloEditar) {
		this.tituloEditar = tituloEditar;
	}

	public void gerarlistaUnidade() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidade = unidadeNegocioFacade.listar(true);
		if (listaUnidade == null) {
			listaUnidade = new ArrayList<Unidadenegocio>();
		}
	}

	public String editarUnidade(Unidadenegocio unidadenegocio) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("unidadenegocio", unidadenegocio);
		return "cadUnidadeNegocio";
	}

	public void pesquisar() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidade = unidadeNegocioFacade.listar(nome);
	}

	public void limpar() {
		nome = "";
		gerarlistaUnidade();
	}
	
	
	public void visualizarUsuarios(Unidadenegocio unidadenegocio){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("unidadenegocio", unidadenegocio);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 650);
		RequestContext.getCurrentInstance().openDialog("consUsuariosUnidade");
	}
	
	
	public void cadastroUnidadeAPI(Unidadenegocio unidadenegocio) throws org.eclipse.persistence.exceptions.JAXBException {
        try {

            com.google.gson.Gson gson = new com.google.gson.Gson();
            UnidadeBean unidadeBean = new UnidadeBean();
            unidadeBean.setCodigosystm(unidadenegocio.getIdunidadeNegocio());
            unidadeBean.setBairro(unidadenegocio.getBairro());
            unidadeBean.setCep(unidadenegocio.getCep());
            unidadeBean.setCidade(unidadenegocio.getCidade());
            unidadeBean.setDocumento("");
            unidadeBean.setDocumentotipo("");
            unidadeBean.setEmail(unidadenegocio.getEmail());
            unidadeBean.setEndereco(unidadenegocio.getTipoLogradouro() + " " + unidadenegocio.getLogradouro());
            unidadeBean.setEnderecocpl("");
            unidadeBean.setEstado(unidadenegocio.getEstado());
            unidadeBean.setIe("");
            unidadeBean.setIm("");
            unidadeBean.setNome(unidadenegocio.getNomerelatorio());
            unidadeBean.setRazaosocial(unidadenegocio.getRazaoSocial());
            unidadeBean.setTelefone(unidadenegocio.getFone());
            String unidade = gson.toJson(unidadeBean);

            try {
                java.net.URL url = new java.net.URL("http://www.tmtur.com.br/systm/cadastraunidade");
                java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(connection.getOutputStream());
                out.write(unidade);
                out.close();
                int http_status = connection.getResponseCode();
                if (http_status / 100 != 2) {
                    Mensagem.lancarMensagemInfo("Ocorreu algum erro. Codigo de reposta: {0}", ""+ http_status);
                }
                try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            } catch (java.io.IOException e) {
                Mensagem.lancarMensagemInfo("", "" + e);
            }
        } catch (Exception e) {
        		Mensagem.lancarMensagemInfo("", "" + e);
        }
	} 
	
	
	public void ativarUnidadeAPI(Unidadenegocio unidadenegocio) throws org.eclipse.persistence.exceptions.JAXBException {
        try {

            com.google.gson.Gson gson = new com.google.gson.Gson();
            UnidadeBean unidadeBean = new UnidadeBean();
            unidadeBean.setCodigosystm(unidadenegocio.getIdunidadeNegocio());
            String usuario = gson.toJson(unidadeBean);

            try {
                java.net.URL url = new java.net.URL("http://www.tmtur.com.br/systm/ativaunidade");
                java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(connection.getOutputStream());
                out.write(usuario);
                out.close();
                int http_status = connection.getResponseCode();
                if (http_status / 100 != 2) {
                    Mensagem.lancarMensagemInfo("Ocorreu algum erro. Codigo de reposta: {0}", ""+ http_status);
                }
                try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            } catch (java.io.IOException e) {
                Mensagem.lancarMensagemInfo("", "" + e);
            }
        } catch (Exception e) {
        		Mensagem.lancarMensagemInfo("", "" + e);
        }
	} 
	
	
	public boolean habilitarAPI() {
		if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1) {
			return true;
		}else {
			return false;
		}
	}
}
