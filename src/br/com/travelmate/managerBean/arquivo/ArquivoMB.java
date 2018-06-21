package br.com.travelmate.managerBean.arquivo;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.bean.ListaHeBean;
import br.com.travelmate.facade.ArquivosFacade;
import br.com.travelmate.facade.ArquivosKitViagemFacade;
import br.com.travelmate.facade.AvisosFacade;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.facade.TipoArquivoProdutoFacade;
import br.com.travelmate.facade.UsuarioDepartamentoUnidadeFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Arquivos;
import br.com.travelmate.model.Arquivoskitviagem;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Tipoarquivo;
import br.com.travelmate.model.Tipoarquivoproduto;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Usuariodepartamentounidade;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class ArquivoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Arquivos> listarArquivos;
	private Arquivos arquivos; 
	private Vendas vendas;
	private Ftpdados ftpdados;
	private boolean habilitadoExclusao = false;
	private String voltar;
	private Tipoarquivoproduto tipoarquivo;
	private List<Tipoarquivoproduto> listaTipoArquivo;
	private boolean desabilitarEdicaoArquivo = true;
	private List<Tipoarquivo> listaArquivosS;
	private Tipoarquivo tipoarquivoS;
	private List<ListaHeBean> listaProcesso;
	private List<ListaHeBean> listaFinanceiro;
	private List<ListaHeBean> listaAndamento;
	private List<ListaHeBean> listaFinalizar;
	private List<ListaHeBean> listaCancelada;
	private String pesquisar;
	private String nomePrograma;
	private String chamadaTela = "";
	private List<ListaHeBean> listaHe;
	private Cliente cliente;
	private String dadosCliente;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			vendas = (Vendas) session.getAttribute("vendas");
			voltar = (String) session.getAttribute("voltar");
			cliente = (Cliente) session.getAttribute("cliente");
			gerarListaTipoArquivo();
			desabilitarEdicao();
			if (vendas != null) {
				gerarListaArquivos();
				FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
				ftpdados = new Ftpdados();
				try {
					ftpdados = ftpDadosFacade.getFTPDados();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (vendas.getArquivoskitviagem()==null){
					Arquivoskitviagem kitViagem = new Arquivoskitviagem();
					kitViagem.setCompleto(false);
					kitViagem.setVendas(vendas);
					ArquivosKitViagemFacade arquivosKitViagemFacade = new ArquivosKitViagemFacade();
					kitViagem = arquivosKitViagemFacade.salvar(kitViagem);
					vendas.setArquivoskitviagem(kitViagem);
				}
				if (vendas.getProdutos().getIdprodutos() == 22) {
					pesquisar = (String) session.getAttribute("pesquisar");
					listaProcesso = (List<ListaHeBean>) session.getAttribute("listaProcesso");
					listaFinanceiro = (List<ListaHeBean>) session.getAttribute("listaFinanceiro");
					listaAndamento = (List<ListaHeBean>) session.getAttribute("listaAndamento");
					listaFinalizar = (List<ListaHeBean>) session.getAttribute("listaFinalizar");
					listaCancelada = (List<ListaHeBean>) session.getAttribute("listaCancelada");
					nomePrograma = (String) session.getAttribute("nomePrograma");
					chamadaTela = (String) session.getAttribute("chamadaTela");
					listaHe = (List<ListaHeBean>) session.getAttribute("listaHe");
					session.removeAttribute("listaAndamento");
					session.removeAttribute("listaFinalizar");
					session.removeAttribute("listaProcesso");
					session.removeAttribute("listaFinanceiro");
					session.removeAttribute("listaCancelada");
					session.removeAttribute("pesquisar");
					session.removeAttribute("nomePrograma");
					session.removeAttribute("chamadaTela");
					session.removeAttribute("listaHe");
				}
			} else {
				listarArquivos = new ArrayList<Arquivos>();
			}
		}
	}

	public boolean isHabilitadoExclusao() {
		return habilitadoExclusao;
	}

	public void setHabilitadoExclusao(boolean habilitadoExclusao) {
		this.habilitadoExclusao = habilitadoExclusao;
	}

	public List<Arquivos> getListarArquivos() {
		return listarArquivos;
	}

	public void setListarArquivos(List<Arquivos> listarArquivos) {
		this.listarArquivos = listarArquivos;
	}

	public Arquivos getArquivos() {
		return arquivos;
	}

	public void setArquivos(Arquivos arquivos) {
		this.arquivos = arquivos;
	} 

	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public Ftpdados getFtpdados() {
		return ftpdados;
	}

	public void setFtpdados(Ftpdados ftpdados) {
		this.ftpdados = ftpdados;
	}

	public String getVoltar() {
		return voltar;
	}

	public void setVoltar(String voltar) {
		this.voltar = voltar;
	}

	public Tipoarquivoproduto getTipoarquivo() {
		return tipoarquivo;
	}

	public void setTipoarquivo(Tipoarquivoproduto tipoarquivo) {
		this.tipoarquivo = tipoarquivo;
	}

	public List<Tipoarquivoproduto> getListaTipoArquivo() {
		return listaTipoArquivo;
	}

	public void setListaTipoArquivo(List<Tipoarquivoproduto> listaTipoArquivo) {
		this.listaTipoArquivo = listaTipoArquivo;
	}

	public boolean isDesabilitarEdicaoArquivo() {
		return desabilitarEdicaoArquivo;
	}

	public void setDesabilitarEdicaoArquivo(boolean desabilitarEdicaoArquivo) {
		this.desabilitarEdicaoArquivo = desabilitarEdicaoArquivo;
	}

	public List<Tipoarquivo> getListaArquivosS() {
		return listaArquivosS;
	}

	public void setListaArquivosS(List<Tipoarquivo> listaArquivosS) {
		this.listaArquivosS = listaArquivosS;
	}

	public Tipoarquivo getTipoarquivoS() {
		return tipoarquivoS;
	}

	public void setTipoarquivoS(Tipoarquivo tipoarquivoS) {
		this.tipoarquivoS = tipoarquivoS;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<ListaHeBean> getListaProcesso() {
		return listaProcesso;
	}

	public void setListaProcesso(List<ListaHeBean> listaProcesso) {
		this.listaProcesso = listaProcesso;
	}

	public List<ListaHeBean> getListaFinanceiro() {
		return listaFinanceiro;
	}

	public void setListaFinanceiro(List<ListaHeBean> listaFinanceiro) {
		this.listaFinanceiro = listaFinanceiro;
	}

	public List<ListaHeBean> getListaAndamento() {
		return listaAndamento;
	}

	public void setListaAndamento(List<ListaHeBean> listaAndamento) {
		this.listaAndamento = listaAndamento;
	}

	public List<ListaHeBean> getListaFinalizar() {
		return listaFinalizar;
	}

	public void setListaFinalizar(List<ListaHeBean> listaFinalizar) {
		this.listaFinalizar = listaFinalizar;
	}

	public List<ListaHeBean> getListaCancelada() {
		return listaCancelada;
	}

	public void setListaCancelada(List<ListaHeBean> listaCancelada) {
		this.listaCancelada = listaCancelada;
	}

	public String getPesquisar() {
		return pesquisar;
	}

	public void setPesquisar(String pesquisar) {
		this.pesquisar = pesquisar;
	}

	public String getNomePrograma() {
		return nomePrograma;
	}

	public void setNomePrograma(String nomePrograma) {
		this.nomePrograma = nomePrograma;
	}

	public List<ListaHeBean> getListaHe() {
		return listaHe;
	}

	public void setListaHe(List<ListaHeBean> listaHe) {
		this.listaHe = listaHe;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getDadosCliente() {
		return dadosCliente;
	}

	public void setDadosCliente(String dadosCliente) {
		this.dadosCliente = dadosCliente;
	}

	public void gerarListaArquivos() {
		ArquivosFacade arquivosFacade = new ArquivosFacade();
		try {
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				if (vendas.getProdutos().getIdprodutos()==22) {
					listarArquivos = arquivosFacade
							.listar("Select a from Arquivos a where a.cliente.idcliente="
									+ cliente.getIdcliente() + " and a.tipoarquivo.unidade='Sim'");
					dadosCliente = cliente.getNome();
				}else {
					listarArquivos = arquivosFacade
							.listar("Select a from Arquivos a where a.vendas.idvendas="
									+ vendas.getIdvendas() + " and a.tipoarquivo.unidade='Sim'");
					dadosCliente = vendas.getIdvendas() + " - " + vendas.getCliente().getNome();
				}
			} else {
				if (vendas.getProdutos().getIdprodutos()==22) {
					listarArquivos = arquivosFacade
							.listar("Select a from Arquivos a where a.cliente.idcliente=" + cliente.getIdcliente());
					dadosCliente = cliente.getNome();
				}else {
					listarArquivos = arquivosFacade
							.listar("Select a from Arquivos a where a.vendas.idvendas=" + vendas.getIdvendas());
					dadosCliente = vendas.getIdvendas() + " - " + vendas.getCliente().getNome();
				}
				
			}

			if (listarArquivos == null) {
				listarArquivos = new ArrayList<Arquivos>();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public String excluir(Arquivos arquivos) {
		ArquivosFacade arquivosFacade = new ArquivosFacade();
		if (habilitarExclusaoArquivo()) {
			excluirArquivoFTP(arquivos);
			arquivosFacade.excluir(arquivos.getIdarquivos());
			listarArquivos.remove(arquivos);
		} else {
			arquivos.setSe(true);
			arquivosFacade.salvar(arquivos);
			AvisosFacade avisosFacade = new AvisosFacade();
			Avisos avisos = new Avisos();
			avisos.setData(new Date());
			avisos.setUsuario(usuarioLogadoMB.getUsuario());
			avisos.setImagem("Upload");
			avisos.setLiberar(true);
			avisos.setTexto("Solicitação de Exclusão - Upload " + arquivos.getTipoarquivo().getDescricao() + " "
					+ vendas.getCliente().getNome() + " - " + vendas.getProdutos().getDescricao());
			avisos.setIdunidade(0);
			avisos.setIdvenda(vendas.getIdvendas());
			avisos.setAvisousuarioList(salvarAvisoUsuario(avisos));
			avisos = avisosFacade.salvar(avisos);
			Mensagem.lancarMensagemInfo("Solicitação de exclusão enviada.", "");
		}
		return "";
	}

	public List<Avisousuario> salvarAvisoUsuario(Avisos aviso) {
		List<Avisousuario> lista = new ArrayList<Avisousuario>(); 
		String sql = "";
		if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 2
				|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 5
				|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 4) {
			sql = "select u from Usuario u where u.situacao='Ativo' and u.unidadenegocio.idunidadeNegocio="
					+ vendas.getUnidadenegocio().getIdunidadeNegocio() + " or u.idusuario=1";
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			List<Usuario> listaUsuario = usuarioFacade.listar(sql);
			if (listaUsuario != null) {
				for (int i = 0; i < listaUsuario.size(); i++) {
					Avisousuario avisousuario = new Avisousuario();
					avisousuario.setAvisos(aviso);
					avisousuario.setUsuario(listaUsuario.get(i));
					avisousuario.setVisto(false);
					lista.add(avisousuario);
				}
			}
		} else {
			DepartamentoFacade departamentoFacade = new DepartamentoFacade();
			List<Departamento> departamento = departamentoFacade.listar("select d From Departamento d where d.usuario.idusuario="
							+ vendas.getProdutos().getIdgerente());
			if(departamento!=null && departamento.size()>0){
				sql = "select u From Usuariodepartamentounidade u where u.unidadenegocio.idunidadeNegocio="+vendas.getUnidadenegocio().getIdunidadeNegocio()
				+ " and u.departamento.iddepartamento=" + departamento.get(0).getIddepartamento();
				UsuarioDepartamentoUnidadeFacade usuarioDepartamentoUnidadeFacade = new UsuarioDepartamentoUnidadeFacade();
				List<Usuariodepartamentounidade> listaNoficacao = usuarioDepartamentoUnidadeFacade.listar(sql); 
				if(listaNoficacao!=null){
					for (int i = 0; i < listaNoficacao.size(); i++) {
						Avisousuario avisousuario = new Avisousuario();
						avisousuario.setAvisos(aviso);
						avisousuario.setUsuario(listaNoficacao.get(i).getUsuario());
						avisousuario.setVisto(false);
						lista.add(avisousuario);
					}
				}
			}
		} 
		return lista;
	}

	public boolean habilitarExclusaoArquivo() {
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			habilitadoExclusao = true;
			return habilitadoExclusao;
		} else {
			habilitadoExclusao = false;
			return habilitadoExclusao;
		}
	}

	public String novoArquivo() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendas", vendas);
		session.setAttribute("listaArquivos", listarArquivos);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("cadArquivo", options, null);
		return "";
	}

	public void retornoDialog(SelectEvent event) {
		if (event.getObject() instanceof Arquivos) {
			Arquivos arquivos = (Arquivos) event.getObject();
			if (arquivos.getIdarquivos() != null) {
				
				Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				listarArquivos = (List<Arquivos>) session.getAttribute("listaArquivos");
				session.removeAttribute("listaArquivos");
				String msg = (String) session.getAttribute("msgBilhete");
				session.removeAttribute("msgBilehte");
				if (msg.length()>0) {
					Mensagem.lancarMensagemInfo("Atenção","Data de vigencia do SEGURO VIAGEM esta em desacordo com data do Bilhete aéreo");
				}
			}
		}
	}

	public boolean excluirArquivoFTP(Arquivos arquivos) {
		String msg = "";
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(CadArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro", "");
		}
		if (dadosFTP == null) {
			return false;
		}
		Ftp ftp = new Ftp(dadosFTP.getHostupload(), dadosFTP.getUser(), dadosFTP.getPassword());
		try {
			if (!ftp.conectar()) {
				mostrarMensagem(null, "Erro conectar FTP", "");
				return false;
			}
		} catch (IOException ex) {
			Logger.getLogger(CadArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
		}
		try {
			String nomeArquivoFTP = arquivos.getNomesalvos();
			msg = ftp.excluirArquivo(nomeArquivoFTP, "/systm/arquivos/");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
			ftp.desconectar();
			return true;
		} catch (IOException ex) {
			Logger.getLogger(CadArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
		}
		try {
			ftp.desconectar();
		} catch (IOException ex) {
			Logger.getLogger(CadArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro desconectar FTP", "Erro");
		}
		return false;
	}

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
	}

	public String voltarTela() {
		if (vendas.getProdutos().getIdprodutos() == 22) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("pesquisar", "Sim");
			session.setAttribute("nomePrograma", "He");
			session.setAttribute("listaAndamento", listaAndamento);
			session.setAttribute("listaCancelada", listaCancelada);
			session.setAttribute("listaFinalizar", listaFinalizar);
			session.setAttribute("listaFinanceiro", listaFinanceiro);
			session.setAttribute("listaProcesso", listaProcesso);
			session.setAttribute("chamadaTela", chamadaTela);
			session.setAttribute("listaHe", listaHe);
		}
		return voltar;
	}

	public String corArquivo(Arquivos arquivos) {
		String cor = "";
		if (arquivos.isSe()) {
			cor = "color:red;";
		} else {
			cor = "color:black;";
		}
		return cor;
	}

	public void gerarListaSelecionada() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		List<Arquivos> listaArquivoSelecionado = new ArrayList<Arquivos>();
		for (int i = 0; i < listarArquivos.size(); i++) {
			if (listarArquivos.get(i).isSelecionado()) {
				listaArquivoSelecionado.add(listarArquivos.get(i));
			}
		}
		session.setAttribute("listaArquivo", listaArquivoSelecionado);
		session.setAttribute("cliente", vendas.getCliente());
		RequestContext.getCurrentInstance().openDialog("enviarEmail", options, null);
	}
	
	public void salvarKitViagem(){
		if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 2) {
			ArquivosKitViagemFacade arquivosKitViagemFacade = new ArquivosKitViagemFacade();
			Arquivoskitviagem kitViagem = new Arquivoskitviagem();
			kitViagem = vendas.getArquivoskitviagem();
			kitViagem = arquivosKitViagemFacade.salvar(kitViagem);
			vendas.setArquivoskitviagem(kitViagem);
			AvisosFacade avisosFacade = new AvisosFacade();
			Avisos avisos = new Avisos();
			avisos.setData(new Date());
			avisos.setUsuario(usuarioLogadoMB.getUsuario());
			avisos.setImagem("aviso");
			avisos.setLiberar(true);
			avisos.setTexto("Kit Viagem do cliente " + vendas.getCliente().getNome() + ", Nº da venda "
					+ vendas.getIdvendas() + " está completo.");
			avisos.setIdunidade(0);
			avisos = avisosFacade.salvar(avisos);
			if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 2
					|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 5
					|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 4
					|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 7) {
				String sql = "select u from Usuario u where u.situacao='Ativo' and u.unidadenegocio.idunidadeNegocio="
						+ vendas.getUnidadenegocio().getIdunidadeNegocio() + " and u.vende=true or u.idusuario=1";
				UsuarioFacade usuarioFacade = new UsuarioFacade();
				List<Usuario> listaUsuario = usuarioFacade.listar(sql);
				if (listaUsuario != null) { 
					for (int i = 0; i < listaUsuario.size(); i++) {
						Avisousuario avisousuario = new Avisousuario();
						avisousuario.setAvisos(avisos);
						avisousuario.setUsuario(listaUsuario.get(i));
						avisousuario.setVisto(false);
						avisousuario = avisosFacade.salvar(avisousuario); 
					}
				}
			}
		}
	}
	
	public String botaoKitViagem(){
		if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()==2){
			return "false";
		}else return "true";
	}
	
	public String rendererBotaoKitiViagem(){
		int idproduto = aplicacaoMB.getParametrosprodutos().getCursos();
		if (vendas.getProdutos().getIdprodutos()==idproduto){
			return "true";
		}else return "false";
	}
	
	
	public void gerarListaTipoArquivo() {
		TipoArquivoProdutoFacade tipoArquivoFacade = new TipoArquivoProdutoFacade();
		try {
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				listaTipoArquivo = tipoArquivoFacade.listar("Select t from Tipoarquivoproduto t"
						+ " where t.produtos.idprodutos="+vendas.getProdutos().getIdprodutos());
			} else
				listaTipoArquivo = tipoArquivoFacade
						.listar("Select t from Tipoarquivoproduto t where t.tipoarquivo.unidade='Sim'"
								+ " and t.produtos.idprodutos="+vendas.getProdutos().getIdprodutos()); 
			if (listaTipoArquivo == null) {
				listaTipoArquivo = new ArrayList<Tipoarquivoproduto>();
			}
			listaArquivosS = new ArrayList<>();
			for (int i = 0; i < listaTipoArquivo.size(); i++) {
				listaArquivosS.add(listaTipoArquivo.get(i).getTipoarquivo());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void editar(RowEditEvent event) {
		Arquivos arquivos = ((Arquivos) event.getObject());
		if (arquivos != null) {
			ArquivosFacade arquivosFacade = new ArquivosFacade();
			arquivosFacade.salvar(arquivos);
			Mensagem.lancarMensagemInfo("Editado com sucesso!", "");
		}
	}
	
	
	public void cancelarEdicao(RowEditEvent event) {
		Mensagem.lancarMensagemInfo("Operação cancelada!", "");
	}
	
	public void desabilitarEdicao(){
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			desabilitarEdicaoArquivo = false;
		}
	}
	
	

}
