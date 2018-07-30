package br.com.travelmate.managerBean.financeiro.relatorios;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import br.com.travelmate.bean.RelatorioErroBean;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@ViewScoped
public class RelatorioVendasSinteticoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private List<SinteticoVendasBean> listaSintetico;
	private List<Unidadenegocio> listaUnidade;
	private Date dataInicial;
	private Date dataFinal;
	private Unidadenegocio unidadenegocio;
	private boolean habilitarPdf = false;
	private SinteticoVendasBean sintetico;
	
	
	@PostConstruct
	public void init() {
		listaUnidade = GerarListas.listarUnidade();
	}


	public List<SinteticoVendasBean> getListaSintetico() {
		return listaSintetico;
	}


	public void setListaSintetico(List<SinteticoVendasBean> listaSintetico) {
		this.listaSintetico = listaSintetico;
	}


	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}


	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}
	
	
	
	public Date getDataInicial() {
		return dataInicial;
	}


	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}


	public Date getDataFinal() {
		return dataFinal;
	}


	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}


	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}


	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}


	public boolean isHabilitarPdf() {
		return habilitarPdf;
	}


	public void setHabilitarPdf(boolean habilitarPdf) {
		this.habilitarPdf = habilitarPdf;
	}


	public SinteticoVendasBean getSintetico() {
		return sintetico;
	}


	public void setSintetico(SinteticoVendasBean sintetico) {
		this.sintetico = sintetico;
	}


	public void gerarListaUnidade() {
		listaSintetico = new ArrayList<SinteticoVendasBean>();
		if (listaUnidade != null) {
			Float totalcurso = 0.0f;
			Float totalteens = 0.0f;
			Float totalpacotes = 0.0f;
			Float totalpassagem = 0.0f;
			Float totalseguro = 0.0f;
			Float totalhe = 0.0f;
			Float totaltrabalho = 0.0f;
			Float totalvistos = 0.0f;
			Float total = 0.0f;
			if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
				if (unidadenegocio.getIdunidadeNegocio() != 6) {
					SinteticoVendasBean sinteticoVendasBean = new SinteticoVendasBean();
					sinteticoVendasBean.setIdUnidade(unidadenegocio.getIdunidadeNegocio());
					sinteticoVendasBean.setUnidade(unidadenegocio.getNomerelatorio());
					Float curso = gerarListaProdutos(unidadenegocio.getIdunidadeNegocio(),
							aplicacaoMB.getParametrosprodutos().getCursos());
					sinteticoVendasBean.setCurso(curso);
					Float teens = gerarListaProdutos(unidadenegocio.getIdunidadeNegocio(),
							aplicacaoMB.getParametrosprodutos().getProgramasTeens());
					teens = teens + gerarListaProdutos(unidadenegocio.getIdunidadeNegocio(),
							aplicacaoMB.getParametrosprodutos().getHighSchool());
					sinteticoVendasBean.setHighschool(teens);
					Float pacotes = gerarListaProdutos(unidadenegocio.getIdunidadeNegocio(),
							aplicacaoMB.getParametrosprodutos().getPacotes());
					sinteticoVendasBean.setPacotes(pacotes);
					Float passagem = gerarListaProdutos(unidadenegocio.getIdunidadeNegocio(),
							aplicacaoMB.getParametrosprodutos().getPassagem());
					sinteticoVendasBean.setPassagem(passagem);
					Float seguro = gerarListaProdutos(unidadenegocio.getIdunidadeNegocio(),
							aplicacaoMB.getParametrosprodutos().getSeguroPrivado());
					sinteticoVendasBean.setSeguro(seguro);
					Float he =  gerarListaProdutos(unidadenegocio.getIdunidadeNegocio(),
							aplicacaoMB.getParametrosprodutos().getHighereducation());
					sinteticoVendasBean.setTeens(he);
					Float trabalho = gerarListaProdutos(unidadenegocio.getIdunidadeNegocio(),
							aplicacaoMB.getParametrosprodutos().getWork());
					trabalho = trabalho + gerarListaProdutos(unidadenegocio.getIdunidadeNegocio(),
							aplicacaoMB.getParametrosprodutos().getTrainee());
					trabalho = trabalho + gerarListaProdutos(unidadenegocio.getIdunidadeNegocio(),
							aplicacaoMB.getParametrosprodutos().getAupair());
					trabalho = trabalho + gerarListaProdutos(unidadenegocio.getIdunidadeNegocio(),
							aplicacaoMB.getParametrosprodutos().getVoluntariado());
					sinteticoVendasBean.setTrabalho(trabalho);
					Float vistos = gerarListaProdutos(unidadenegocio.getIdunidadeNegocio(),
							aplicacaoMB.getParametrosprodutos().getVisto());
					sinteticoVendasBean.setVistos(vistos);
					sinteticoVendasBean.setVtm(0.0f);
					sinteticoVendasBean.setTotal(sinteticoVendasBean.getCurso() + sinteticoVendasBean.getHighschool()
							+ sinteticoVendasBean.getPacotes() + sinteticoVendasBean.getPassagem()
							+ sinteticoVendasBean.getSeguro() + sinteticoVendasBean.getTeens()
							+ sinteticoVendasBean.getTrabalho() + sinteticoVendasBean.getVistos()
							+ sinteticoVendasBean.getVtm());
					listaSintetico.add(sinteticoVendasBean);
				}
			}else {
				for (int i = 0; i < listaUnidade.size(); i++) {
					if (listaUnidade.get(i).getIdunidadeNegocio() != 6) {
						SinteticoVendasBean sinteticoVendasBean = new SinteticoVendasBean();
						sinteticoVendasBean.setIdUnidade(listaUnidade.get(i).getIdunidadeNegocio());
						sinteticoVendasBean.setUnidade(listaUnidade.get(i).getNomerelatorio());
						sinteticoVendasBean.setCurso(gerarListaProdutos(listaUnidade.get(i).getIdunidadeNegocio(),
								aplicacaoMB.getParametrosprodutos().getCursos()));
						Float teens = gerarListaProdutos(listaUnidade.get(i).getIdunidadeNegocio(),
								aplicacaoMB.getParametrosprodutos().getProgramasTeens());
						teens = teens + gerarListaProdutos(listaUnidade.get(i).getIdunidadeNegocio(),
								aplicacaoMB.getParametrosprodutos().getHighSchool());
						sinteticoVendasBean.setHighschool(teens);
						sinteticoVendasBean.setPacotes(gerarListaProdutos(listaUnidade.get(i).getIdunidadeNegocio(),
								aplicacaoMB.getParametrosprodutos().getPacotes()));
						sinteticoVendasBean.setPassagem(gerarListaProdutos(listaUnidade.get(i).getIdunidadeNegocio(),
								aplicacaoMB.getParametrosprodutos().getPassagem()));
						sinteticoVendasBean.setSeguro(gerarListaProdutos(listaUnidade.get(i).getIdunidadeNegocio(),
								aplicacaoMB.getParametrosprodutos().getSeguroPrivado()));
						sinteticoVendasBean.setTeens(gerarListaProdutos(listaUnidade.get(i).getIdunidadeNegocio(),
								aplicacaoMB.getParametrosprodutos().getHighereducation()));
						Float trabalho = gerarListaProdutos(listaUnidade.get(i).getIdunidadeNegocio(),
								aplicacaoMB.getParametrosprodutos().getWork());
						trabalho = trabalho + gerarListaProdutos(listaUnidade.get(i).getIdunidadeNegocio(),
								aplicacaoMB.getParametrosprodutos().getTrainee());
						trabalho = trabalho + gerarListaProdutos(listaUnidade.get(i).getIdunidadeNegocio(),
								aplicacaoMB.getParametrosprodutos().getAupair());
						trabalho = trabalho + gerarListaProdutos(listaUnidade.get(i).getIdunidadeNegocio(),
								aplicacaoMB.getParametrosprodutos().getVoluntariado());
						sinteticoVendasBean.setTrabalho(trabalho);
						sinteticoVendasBean.setVistos(gerarListaProdutos(listaUnidade.get(i).getIdunidadeNegocio(),
								aplicacaoMB.getParametrosprodutos().getVisto()));
						sinteticoVendasBean.setVtm(0.0f);
						sinteticoVendasBean.setTotal(sinteticoVendasBean.getCurso() + sinteticoVendasBean.getHighschool()
								+ sinteticoVendasBean.getPacotes() + sinteticoVendasBean.getPassagem()
								+ sinteticoVendasBean.getSeguro() + sinteticoVendasBean.getTeens()
								+ sinteticoVendasBean.getTrabalho() + sinteticoVendasBean.getVistos()
								+ sinteticoVendasBean.getVtm());
						listaSintetico.add(sinteticoVendasBean);
						totalcurso = totalcurso + sinteticoVendasBean.getCurso();
						totalhe = totalhe + sinteticoVendasBean.getTeens();
						totalpacotes = totalpacotes + sinteticoVendasBean.getPacotes();
						totalpassagem = totalpassagem + sinteticoVendasBean.getPassagem();
						totalseguro =  totalseguro + sinteticoVendasBean.getSeguro();
						totalteens = totalteens + sinteticoVendasBean.getHighschool();
						totaltrabalho = totaltrabalho + sinteticoVendasBean.getTrabalho();
						totalvistos = totalvistos + sinteticoVendasBean.getVistos();
						total = total + sinteticoVendasBean.getTotal();
					}
				}
				sintetico = new SinteticoVendasBean();
				sintetico.setIdUnidade(0);
				sintetico.setUnidade("Total");
				sintetico.setCurso(totalcurso);
				sintetico.setHighschool(totalteens);
				sintetico.setTeens(totalhe);
				sintetico.setTrabalho(totaltrabalho);
				sintetico.setSeguro(totalseguro);
				sintetico.setVistos(totalvistos);
				sintetico.setVtm(0.0f);
				sintetico.setPassagem(totalpassagem);
				sintetico.setPacotes(totalpacotes);
				sintetico.setCor("background:#459E00;color:white;");
				sintetico.setTotal(total);
				listaSintetico.add(sintetico);
			}
		}
		if (listaSintetico != null && listaSintetico.size() > 0) {
			habilitarPdf = true;
		}else {
			habilitarPdf = false;
		}
	}
	
	
	public Float gerarListaProdutos(int idUnidade, int idProduto){
		Double soma = 0.0;
		String sql = "Select distinct sum(valor) as valor " +
		        " From vendas where (situacao='FINALIZADA' OR situacao='ANDAMENTO') and vendasMatriz='S'"
		        + " and dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' and  dataVenda<='" + Formatacao.ConvercaoDataSql(dataFinal) 
		        		+ "'  and unidadeNegocio_idunidadeNegocio=" + idUnidade + " and produtos_idprodutos=" + idProduto;
		
		soma = vendasDao.saldoAcumulado(sql);
		return soma.floatValue();
	}
	
	
	public void limpar() {
		listaSintetico = new ArrayList<SinteticoVendasBean>();
		unidadenegocio = null;
		dataFinal = null;
		dataInicial = null;
		habilitarPdf = false;
	}
	
	
	public String iniciarRelatorio(){
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		Map parameters = new HashMap();
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = null;
		try {
			logo = ImageIO.read(f);
		} catch (IOException e1) {
			e1.printStackTrace();
			Mensagem.lancarMensagemFatal("Erro Sistema", "Erro gerar ler logo " + e1.getMessage());
		}  
		parameters.put("logo", logo);
		String periodo = Formatacao.ConvercaoDataPadrao(dataInicial) + " - " + Formatacao.ConvercaoDataPadrao(dataFinal);
		parameters.put("periodo", periodo);
		String caminhoRelatorio = "/reports/financeiro/sinteticovendas.jasper";
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		int idSintetico = listaSintetico.size() - 1;
		SinteticoVendasBean sintetico = listaSintetico.get(idSintetico);
		if (sintetico.getUnidade().equalsIgnoreCase("Total")) {
			listaSintetico.remove(sintetico);
		}
		JRDataSource jrds = new JRBeanCollectionDataSource(listaSintetico);
		try {
			gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, "SinteticoVendas.pdf");
		} catch (Exception e) {
			Mensagem.lancarMensagemErro("Erro Relatório", "Erro gerar relatório Sintetico de Vendas " + e.getMessage());
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Erro gerar relatório Sintetico de Vendas."); 
		}
		return "";
	}
	

}
