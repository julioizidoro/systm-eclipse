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

import org.primefaces.context.RequestContext;

import br.com.travelmate.bean.RelatorioErroBean;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.UnidadeNegocioFacade;

import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@ViewScoped
public class RelatorioSinteticoVendasMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private List<SinteticoVendasBean> lista;
	private Date dataInical;
	private Date dataFinal;
	
	@PostConstruct
	public void init(){
		lista = new ArrayList<SinteticoVendasBean>();
	}
	
	public List<SinteticoVendasBean> getLista() {
		return lista;
	}
	public void setLista(List<SinteticoVendasBean> lista) {
		this.lista = lista;
	}
	public Date getDataInical() {
		return dataInical;
	}
	public void setDataInical(Date dataInical) {
		this.dataInical = dataInical;
	}
	public Date getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	public void gerarListaUnidade() {
		lista = new ArrayList<SinteticoVendasBean>();
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		List<Unidadenegocio> listaUnidade = unidadeNegocioFacade.listar(true);
		if (listaUnidade != null) {
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
					lista.add(sinteticoVendasBean);
				}
			}
		}
	}
	
	public Float gerarListaProdutos(int idUnidade, int idProduto){
		Double soma = 0.0;
		String sql = "Select distinct sum(valor) as valor " +
		        " From vendas where (situacao='FINALIZADA' OR situacao='ANDAMENTO') and vendasMatriz='S'"
		        + " and dataVenda>='" + Formatacao.ConvercaoDataSql(dataInical) + "' and  dataVenda<='" + Formatacao.ConvercaoDataSql(dataFinal) 
		        		+ "'  and unidadeNegocio_idunidadeNegocio=" + idUnidade + " and produtos_idprodutos=" + idProduto;
		
		soma = vendasDao.saldoAcumulado(sql);
		return soma.floatValue();
	}
	
	public String iniciarRelatorio(){
		gerarListaUnidade();
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
		String periodo = Formatacao.ConvercaoDataPadrao(dataInical) + " - " + Formatacao.ConvercaoDataPadrao(dataFinal);
		parameters.put("periodo", periodo);
		String caminhoRelatorio = "/reports/financeiro/sinteticovendas.jasper";
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		JRDataSource jrds = new JRBeanCollectionDataSource(lista);
		try {
			gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, "SinteticoVendas.pdf");
		} catch (Exception e) {
			Mensagem.lancarMensagemErro("Erro Relatório", "Erro gerar relatório Sintetico de Vendas " + e.getMessage());
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Erro gerar relatório Sintetico de Vendas."); 
		}
		return "";
	}
	
	public String fechar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
	
	

}
