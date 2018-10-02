package br.com.travelmate.managerBean.curso.controle;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.CursoTraducaoFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB; 
import br.com.travelmate.model.Controlecurso;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Cursotraducao;
import br.com.travelmate.model.Ftpdados; 
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class RelatorioApplicationMB implements Serializable {

	/**    
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Controlecurso controlecurso;
	private String nome;
	private String sobrenome;
	private boolean acomodacao;
	private boolean doiscursos;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			controlecurso = (Controlecurso) session.getAttribute("controle");
			session.removeAttribute("controle");
			nome = controlecurso.getVendas().getCliente().getNome().substring(0, controlecurso.getVendas().getCliente().getNome().indexOf(" "));
			sobrenome = controlecurso.getVendas().getCliente().getNome().substring(controlecurso.getVendas().getCliente().getNome().indexOf(" ") + 1,
					controlecurso.getVendas().getCliente().getNome().length());
			if(!controlecurso.getPossuiAcomidacao().equalsIgnoreCase("Sem acomodação")){
				acomodacao=true;
			}
			if (controlecurso.getCurso().getHabilitarSegundoCurso().equalsIgnoreCase("S")) {
				doiscursos=true;
			}
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Controlecurso getControlecurso() {
		return controlecurso;
	}

	public void setControlecurso(Controlecurso controlecurso) {
		this.controlecurso = controlecurso;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public boolean isAcomodacao() {
		return acomodacao;
	}

	public void setAcomodacao(boolean acomodacao) {
		this.acomodacao = acomodacao;
	}

	public boolean isDoiscursos() {
		return doiscursos;
	}

	public void setDoiscursos(boolean doiscursos) {
		this.doiscursos = doiscursos;
	}

	public void cancelar() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}

	public void gerarRelatorioApplication() throws SQLException, IOException {
		CursoFacade cursoFacade = new CursoFacade();
		controlecurso.getCurso().setApplication(true);
		controlecurso.setCurso(cursoFacade.salvar(controlecurso.getCurso()));
		traduzirCamposCurso(controlecurso.getCurso());
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio=null;
		if(doiscursos){
			caminhoRelatorio = ("/reports/curso/reportaplicationec2.jasper");
		}else{
			caminhoRelatorio = ("/reports/curso/reportaplicationec.jasper");
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idvendas", controlecurso.getCurso().getVendas().getIdvendas());
		if(acomodacao){
			parameters.put("acomodacao", 1);
		}else{
			parameters.put("acomodacao", 0);  
		} 
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("nome", nome);
		parameters.put("sobrenome", sobrenome);
		parameters.put("logo", logo);
		f = new File(servletContext.getRealPath("/resources/img/assinaturas/0.png"));
		BufferedImage imgCarimbo = ImageIO.read(f);
		parameters.put("carimbo", imgCarimbo);
		String caminho = servletContext.getRealPath("");
		f = new File(caminho + "/resources/img/assinaturas/" + usuarioLogadoMB.getUsuario().getIdusuario() + ".png");
		BufferedImage assinatura = ImageIO.read(f);  
		parameters.put("assinatura", assinatura);
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(RelatorioApplicationMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		Ftp ftp = new Ftp(dadosFTP.getHostupload(), dadosFTP.getUser(), dadosFTP.getPassword());
		ftp.conectar();
		InputStream is = ftp.receberArquivo("",
				controlecurso.getCurso().getVendas().getFornecedorcidade().getFornecedor().getLogo(),
				"/systm/logoescola/");
		Image logoescola = null;
		if (is != null) {
			try {
				logoescola = ImageIO.read(is);
			} catch (IOException e) {
				  
			}
		}
		ftp.desconectar();
		parameters.put("logoescola", logoescola);
		GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
		try {
			gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
					"Application " + controlecurso.getCurso().getVendas().getCliente().getNome() + ".pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(RelatorioApplicationMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioApplicationMB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void traduzirCamposCurso(Curso curso) {
		CursoTraducaoFacade cursoTraducaoFacade = new CursoTraducaoFacade();
		Cursotraducao cursotraducao = cursoTraducaoFacade.consultar(curso.getIdcursos());
		if(cursotraducao==null){
			cursotraducao = new Cursotraducao();
		} 
		if (curso.getNivelIdiomaEstudar() != null) {
			if (curso.getNivelIdiomaEstudar().equalsIgnoreCase("Básico")) {
				cursotraducao.setNivelidioma("Elementary");
			} else if (curso.getNivelIdiomaEstudar().equalsIgnoreCase("Iniciante")) {
				cursotraducao.setNivelidioma("Beginner");
			} else if (curso.getNivelIdiomaEstudar().equalsIgnoreCase("Pré-Intermediário")) {
				cursotraducao.setNivelidioma("Pre-Intermediate");
			} else if (curso.getNivelIdiomaEstudar().equalsIgnoreCase("Intermediário")) {
				cursotraducao.setNivelidioma("Intermediate");
			} else if (curso.getNivelIdiomaEstudar().equalsIgnoreCase("Avançado")) {
				cursotraducao.setNivelidioma("Advanced");
			}else if(curso.getNivelIdiomaEstudar().equalsIgnoreCase("Alto")) {
				cursotraducao.setNivelidioma("High");
			}else if(curso.getNivelIdioma().equalsIgnoreCase("Fluente")) {
				cursotraducao.setNivelidioma("Fluent");
			}else cursotraducao.setNivelidioma(curso.getNivelIdiomaEstudar());
		}
		if (curso.getVendas().getCliente().getPaisNascimento() != null) {
			if (curso.getVendas().getCliente().getPaisNascimento().equalsIgnoreCase("Brasil")) {
				cursotraducao.setPaisnascimento("Brazil");
			}else if (curso.getVendas().getCliente().getPaisNascimento().equalsIgnoreCase("Brasileiro")) {
				cursotraducao.setPaisnascimento("Brazil");
			}else if (curso.getVendas().getCliente().getPaisNascimento().equalsIgnoreCase("Brasileira")) {
				cursotraducao.setPaisnascimento("Brazil");
			} else cursotraducao.setPaisnascimento(curso.getVendas().getCliente().getPaisNascimento());
		}
		if (curso.getTurno1() != null) {
			if (curso.getTurno1().equalsIgnoreCase("Matutino")) {
				cursotraducao.setTurno1("Morning");
			} else if (curso.getTurno1().equalsIgnoreCase("Vespertino")) {
				cursotraducao.setTurno1("Afternoon");
			} else if (curso.getTurno1().equalsIgnoreCase("Noturno")) {
				cursotraducao.setTurno1("Evening ");
			} else {
				cursotraducao.setTurno1("N/A");
			} 
		} else {
			cursotraducao.setTurno1("N/A");
		} 
		if (curso.getTurno2() != null) {
			if (curso.getTurno2().equalsIgnoreCase("Matutino")) {
				cursotraducao.setTurno2("Morning");
			} else if (curso.getTurno2().equalsIgnoreCase("Vespertino")) {
				cursotraducao.setTurno2("Afternoon");
			} else if (curso.getTurno2().equalsIgnoreCase("Noturno")) {
				cursotraducao.setTurno2("Evening ");
			} else {
				cursotraducao.setTurno2("N/A");
			}  
		} else {
			cursotraducao.setTurno2("N/A");
		}  
		if (curso.getTipoAcomodacao() != null) {
			if (curso.getTipoAcomodacao().equalsIgnoreCase("Casa de família")) {
				cursotraducao.setTipoacomodacao("Homestay");
			} else if (curso.getTipoAcomodacao().equalsIgnoreCase("Alojamento")) {
				cursotraducao.setTipoacomodacao("Student residence");
			} else if (curso.getTipoAcomodacao().equalsIgnoreCase("Apartamento")) {
				cursotraducao.setTipoacomodacao("Apartment");
			} else if (curso.getTipoAcomodacao().equalsIgnoreCase("Residência")) {
				cursotraducao.setTipoacomodacao("Residence");
			} else if (curso.getTipoAcomodacao().equalsIgnoreCase("Outro")) {
				cursotraducao.setTipoacomodacao("Other");
			} else if (curso.getTipoAcomodacao().equalsIgnoreCase("Studio")) {
				cursotraducao.setTipoacomodacao("Studio");
			} else if (curso.getTipoAcomodacao().equalsIgnoreCase("Hotel")) {
				cursotraducao.setTipoacomodacao("Hotel");
			} else if (curso.getTipoAcomodacao().equalsIgnoreCase("Hostel")) {
				cursotraducao.setTipoacomodacao("Hostel");
			} else if (curso.getTipoAcomodacao().equalsIgnoreCase("Flat")) {
				cursotraducao.setTipoacomodacao("Flat");
			} else cursotraducao.setTipoacomodacao(curso.getTipoAcomodacao());
		}
		if (curso.getTipoQuarto() != null) {
			if (curso.getTipoQuarto().equalsIgnoreCase("Quarto individual")) {
				cursotraducao.setTipoquarto("Single Room");
			} else if (curso.getTipoQuarto().equalsIgnoreCase("Quarto duplo")) {
				cursotraducao.setTipoquarto("Twin Room");
			} else if (curso.getTipoQuarto().equalsIgnoreCase("Quarto triplo")) {
				cursotraducao.setTipoquarto("Triple Room");
			} else if (curso.getTipoQuarto().equalsIgnoreCase("Compartilhado")) {
				cursotraducao.setTipoquarto("Shared Room");
			} else if (curso.getTipoQuarto().equalsIgnoreCase("Dois Quartos")) {
				cursotraducao.setTipoquarto("");
			}else cursotraducao.setTipoquarto(curso.getTipoQuarto());
		}
		if (curso.getRefeicoes() != null) {
			if (curso.getRefeicoes().equalsIgnoreCase("Sem Refeição")) {
				cursotraducao.setRefeicao("No Meals");
			} else if (curso.getRefeicoes().equalsIgnoreCase("Café da Manhã")) {
				cursotraducao.setRefeicao("B & B");
			} else if (curso.getRefeicoes().equalsIgnoreCase("Meia Pensão (2 Refeições)")) {
				cursotraducao.setRefeicao("Half Board");
			} else if (curso.getRefeicoes().equalsIgnoreCase("Pensão Completa (3 Refeições)")) {
				cursotraducao.setRefeicao("Full Board");
			}else cursotraducao.setRefeicao(curso.getRefeicoes());
		}
		if (curso.getBanheiroprivativo()!= null) {
			if (curso.getBanheiroprivativo().equalsIgnoreCase("Sim")) {
				cursotraducao.setBanheiro("Private");
			} else {
				cursotraducao.setBanheiro("Shared");
			}  
		}
		if (curso.getIndiomaEstudar() != null) {
			if (curso.getIndiomaEstudar().equalsIgnoreCase("Inglês")) {
				cursotraducao.setIdiomaestudar("English");
			} else if (curso.getIndiomaEstudar().equalsIgnoreCase("Francês")) {
				cursotraducao.setIdiomaestudar("French");
			} else if (curso.getIndiomaEstudar().equalsIgnoreCase("Espanhol")) {
				cursotraducao.setIdiomaestudar("Spanish");
			}  else if (curso.getIndiomaEstudar().equalsIgnoreCase("Alemão")) {
				cursotraducao.setIdiomaestudar("German");
			} else if (curso.getIndiomaEstudar().equalsIgnoreCase("Outro")) {
				cursotraducao.setIdiomaestudar("N/A");
			}else cursotraducao.setIdiomaestudar(curso.getIndiomaEstudar());
		}
		cursotraducao.setCurso(curso); 
		cursotraducao = cursoTraducaoFacade.salvar(cursotraducao);
		ClienteFacade clienteFacade = new ClienteFacade();
		clienteFacade.salvar(curso.getVendas().getCliente());
	}
}
