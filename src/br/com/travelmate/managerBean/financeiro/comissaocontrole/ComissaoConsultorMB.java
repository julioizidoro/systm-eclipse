package br.com.travelmate.managerBean.financeiro.comissaocontrole;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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

import br.com.travelmate.facade.ComissaoControleFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.financeiro.relatorios.RelatorioConciliacaoMB;
import br.com.travelmate.model.Comissaocontrole;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;


@Named
@ViewScoped
public class ComissaoConsultorMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Comissaocontrole> listaComissaoControle;
	
	
	@PostConstruct
	public void init(){
		String sql = "SELECT c FROM Comissaocontrole c WHERE c.unidadenegocio.idunidadeNegocio="+
						usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio() +
						" ORDER BY  c.ano DESC, c.mes DESC";
		ComissaoControleFacade comissaoControleFacade = new ComissaoControleFacade();
		listaComissaoControle = comissaoControleFacade.listar(sql);
	}
	
	public List<Comissaocontrole> getListaComissaoControle() {
		return listaComissaoControle;
	}

	public void setListaComissaoControle(List<Comissaocontrole> listaComissaoControle) {
		this.listaComissaoControle = listaComissaoControle;
	}

	public String relatorio(Comissaocontrole comissaoControle) {
		if (usuarioLogadoMB.getUsuario().getCargo().getIdcargo() == 1) {
			try {
				gerarRelatorioComissaoGerente(comissaoControle);
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				gerarReltorioComissaoConsultor(comissaoControle);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public String gerarRelatorioComissaoGerente(Comissaocontrole comissaoControle) throws IOException, SQLException {
		String dataInicial = String.valueOf(comissaoControle.getAno())
				+ Formatacao.retornaDataInicia(comissaoControle.getMes());
		String dataFinal = String.valueOf(comissaoControle.getAno());
		 String sql = "SELECT distinct vendas.idvendas, vendas.dataVenda, produtos.descricao, unidadenegocio.nomefantasia, vendas.valor, "
	                + "cliente.nome as nomecliente, vendascomissao.comissaogerente From "
	            	+ " vendas join produtos on vendas.produtos_idprodutos = produtos.idprodutos"
	                + " join unidadenegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadenegocio.idunidadeNegocio"
	                + " join cliente on vendas.cliente_idcliente = cliente.idcliente"
	                + " join vendascomissao on vendas.idvendas = vendascomissao.vendas_idvendas "
	                + " join usuario on vendas.usuario_idusuario = usuario.idusuario"
	                + " where  (vendas.situacao='FINALIZADA' OR vendas.situacao='ANDAMENTO') and vendas.dataVenda >='"  + dataInicial + " ' and vendas.dataVenda<='" + dataFinal + "'  and vendas.usuario_idusuario="
					+ usuarioLogadoMB.getUsuario().getIdusuario() + " order by vendas.dataVenda ";
		 ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	        String caminhoRelatorio = "/reports/financeiro/comissaoGerente.jasper";  
	        Map parameters = new HashMap();
	        parameters.put("sql", sql);
	        parameters.put("gerente", usuarioLogadoMB.getUsuario().getNome());
	        File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
	        BufferedImage logo = ImageIO.read(f);  
	        parameters.put("logo", logo);
	        String periodo = dataInicial + "              " + dataFinal;
	        parameters.put("periodo", periodo);
	        String nomeRelatorio = "Comissao" + usuarioLogadoMB.getUsuario().getLogin() + String.valueOf(comissaoControle.getMes()) + 
					String.valueOf(comissaoControle.getAno()) + ".pdf";
	        GerarRelatorio gerarRelatorio = new GerarRelatorio();
	        try {
	            gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, nomeRelatorio, null);
	        } catch (JRException ex) {
	            Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
	        } catch (IOException ex) {
	            Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
	        }
		return "";
	}
	
	public String gerarReltorioComissaoConsultor(Comissaocontrole comissaoControle) throws SQLException, IOException {
		String dataInicial = String.valueOf(comissaoControle.getAno())
				+ Formatacao.retornaDataInicia(comissaoControle.getMes());
		String dataFinal = String.valueOf(comissaoControle.getAno())
				+ Formatacao.retornaDataFinal(comissaoControle.getMes());
		String sql = "SELECT distinct vendas.idvendas, vendas.dataVenda, produtos.descricao, unidadenegocio.nomefantasia, vendas.valor, "
				+ "cliente.nome as nomecliente, vendascomissao.comissaoemissor From "
				+ " vendas join produtos on vendas.produtos_idprodutos = produtos.idprodutos"
				+ " join unidadenegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadenegocio.idunidadeNegocio"
				+ " join cliente on vendas.cliente_idcliente = cliente.idcliente"
				+ " join vendascomissao on vendas.idvendas = vendascomissao.vendas_idvendas "
				+ " join usuario on vendas.usuario_idusuario = usuario.idusuario"
				+ " where  (vendas.situacao='FINALIZADA' OR vendas.situacao='ANDAMENTO')" + " and vendas.dataVenda >='"
				+ dataInicial + " ' and vendas.dataVenda<='" + dataFinal + "'  and vendas.usuario_idusuario="
				+ usuarioLogadoMB.getUsuario().getIdusuario() + " order by vendas.dataVenda ";
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = "/reports/financeiro/comissaoConsultor.jasper";
		Map parameters = new HashMap();
		parameters.put("sql", sql);
		parameters.put("consultor", usuarioLogadoMB.getUsuario().getNome());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		String periodo = dataInicial + "              " + dataFinal;
		parameters.put("periodo", periodo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		String nomeRelatorio = "Comissao" + usuarioLogadoMB.getUsuario().getLogin() + String.valueOf(comissaoControle.getMes()) + 
				String.valueOf(comissaoControle.getAno()) + ".pdf";
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, nomeRelatorio, null);
		} catch (JRException ex) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}
}
