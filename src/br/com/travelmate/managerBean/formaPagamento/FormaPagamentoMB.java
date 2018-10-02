package br.com.travelmate.managerBean.formaPagamento;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
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

import br.com.travelmate.facade.CoeficienteJurosFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.financeiro.relatorios.RelatorioConciliacaoMB;
import br.com.travelmate.model.Coeficientejuros;
import br.com.travelmate.model.Ocursoformapagamento;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@ViewScoped
public class FormaPagamentoMB implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Ocursoformapagamento formaPagamento02;
	private Ocursoformapagamento formaPagamento03;
	private Ocursoformapagamento formaPagamento04;
	private Float total;
	private boolean habilitaFormaPagamento02=true;
	private boolean habilitaFormaPagamento03=true;
	private boolean habilitaFormaPagamento04=true;
	private String moedaNacional;

	public Float getTotal() {
		return total;
	}


	public void setTotal(Float total) {
		this.total = total;
	}


	@PostConstruct
    public void init(){
		formaPagamento02 = new Ocursoformapagamento();
        formaPagamento02.setNumeroparcela(1);
        formaPagamento02.setPercentualEntrada(30.0);
        formaPagamento02.setPercentualSaldo(70.0);
        formaPagamento02.setValorEntrada(0.0f);
        formaPagamento02.setValorparcela(0.0f);
        formaPagamento02.setValorSaldo(0.0f);
        formaPagamento02.setSelecionado(false);
        formaPagamento03 = new Ocursoformapagamento();
        formaPagamento03.setNumeroparcela(1);
        formaPagamento03.setPercentualEntrada(30.0);
        formaPagamento03.setPercentualSaldo(70.0);
        formaPagamento03.setValorEntrada(0.0f);
        formaPagamento03.setValorparcela(0.0f);
        formaPagamento03.setValorSaldo(0.0f);
        formaPagamento03.setSelecionado(false);
        formaPagamento04 = new Ocursoformapagamento();
        formaPagamento04.setNumeroparcela(1);
        formaPagamento04.setPercentualEntrada(30.0);
        formaPagamento04.setPercentualSaldo(70.0);
        formaPagamento04.setValorEntrada(0.0f);
        formaPagamento04.setValorparcela(0.0f);
        formaPagamento04.setValorSaldo(0.0f);
        formaPagamento04.setSelecionado(false);
        formaPagamento02.setTitulo("Opção 02 - Parcelamento antes da viagem");
        formaPagamento03.setTitulo("Opção 03 - Parcelamento em cartão de crédito em até 12X* via TravelMate");
        formaPagamento04.setTitulo("Opção 4 - Parcelamento em cheque via financeira");
        formaPagamento02.setObservacao1("O valor da entrada não pode ser pago em cartão de crédito/débito");
        formaPagamento02.setObservacao2("Parcelado mensalmente via boleto até 30 dias antes do embarque");
        formaPagamento03.setObservacao1("O valor da entrada não pode ser pago em cartão de crédito/débito");
        formaPagamento03.setObservacao2("Parcelado no cartão de crédito MASTER, VISA OU AMEX");
        formaPagamento04.setObservacao1("Parcelamento em cheque via financeira");
        formaPagamento04.setObservacao2(" ");
        moedaNacional = usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla();
        try {
			calcularParcelamento();
		} catch (SQLException e) {
			  
		}
    }
	
	
	 public Ocursoformapagamento getFormaPagamento02() {
		return formaPagamento02;
	}




	public void setFormaPagamento02(Ocursoformapagamento formaPagamento02) {
		this.formaPagamento02 = formaPagamento02;
	}




	public Ocursoformapagamento getFormaPagamento03() {
		return formaPagamento03;
	}




	public void setFormaPagamento03(Ocursoformapagamento formaPagamento03) {
		this.formaPagamento03 = formaPagamento03;
	}




	public Ocursoformapagamento getFormaPagamento04() {
		return formaPagamento04;
	}




	public void setFormaPagamento04(Ocursoformapagamento formaPagamento04) {
		this.formaPagamento04 = formaPagamento04;
	}




	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}
	
	
	
	
	

	public boolean isHabilitaFormaPagamento02() {
		return habilitaFormaPagamento02;
	}


	public void setHabilitaFormaPagamento02(boolean habilitaFormaPagamento02) {
		this.habilitaFormaPagamento02 = habilitaFormaPagamento02;
	}


	public boolean isHabilitaFormaPagamento03() {
		return habilitaFormaPagamento03;
	}


	public void setHabilitaFormaPagamento03(boolean habilitaFormaPagamento03) {
		this.habilitaFormaPagamento03 = habilitaFormaPagamento03;
	}


	public boolean isHabilitaFormaPagamento04() {
		return habilitaFormaPagamento04;
	}


	public void setHabilitaFormaPagamento04(boolean habilitaFormaPagamento04) {
		this.habilitaFormaPagamento04 = habilitaFormaPagamento04;
	}


	public String getMoedaNacional() {
		return moedaNacional;
	}


	public void setMoedaNacional(String moedaNacional) {
		this.moedaNacional = moedaNacional;
	}


	public void calcular() throws SQLException{
		formaPagamento02.setPercentualEntrada(30.0);
		formaPagamento03.setPercentualEntrada(30.0);
		formaPagamento04.setPercentualEntrada(30.0);
		formaPagamento02.setNumeroparcela(1);
		formaPagamento03.setNumeroparcela(2);
		formaPagamento04.setNumeroparcela(2);
		calcularParcelamento();
	}

	public void calcularParcelamento() throws SQLException{
        Double valorSaldo=0.0;
        Double saldo = 0.0;
        Double valorEntrada=0.0;
        if (this.formaPagamento02.isSelecionado()){
        if (formaPagamento02.getPercentualEntrada()>0){
            valorEntrada = total * (formaPagamento02.getPercentualEntrada()/100);
            saldo = 100 - formaPagamento02.getPercentualEntrada();
            valorSaldo = total - valorEntrada;
            formaPagamento02.setValorEntrada(valorEntrada.floatValue());
            formaPagamento02.setValorSaldo(valorSaldo.floatValue());
            formaPagamento02.setPercentualSaldo(saldo);
        }
        valorSaldo = 0.0;
        if (formaPagamento02.getNumeroparcela() > 1) {
            valorSaldo = formaPagamento02.getValorSaldo().doubleValue();
            if (valorSaldo > 0) {
                valorSaldo = valorSaldo / formaPagamento02.getNumeroparcela();
                formaPagamento02.setValorparcela(valorSaldo.floatValue());
            }
        }
     }

        
        //Opção 03
        
        if (this.formaPagamento03.isSelecionado()){
        
        valorSaldo=0.0;
        saldo = 0.0;
        valorEntrada=0.0;
        if (formaPagamento03.getPercentualEntrada()>0){
            valorEntrada = total * (formaPagamento03.getPercentualEntrada()/100);
            saldo = 100- formaPagamento03.getPercentualEntrada();
            valorSaldo = total - valorEntrada;
            formaPagamento03.setValorEntrada(valorEntrada.floatValue());
            formaPagamento03.setValorSaldo(valorSaldo.floatValue());
            formaPagamento03.setPercentualSaldo(saldo.doubleValue());
        }
        valorSaldo =0.0;
        if (formaPagamento03.getNumeroparcela()>0){
            if(formaPagamento03.getValorSaldo()>0){
                 valorSaldo = formaPagamento03.getValorSaldo().doubleValue();
                 if (valorSaldo>0){
                     CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
                     Coeficientejuros  cf = coneficienteJurosFacade.consultar(formaPagamento03.getNumeroparcela(), "Juros Cliente");
                     valorSaldo = (double) (valorSaldo * cf.getCoeficiente());
                     formaPagamento03.setValorparcela(valorSaldo.floatValue());
                 }
            }
        }
        }
        
        //opção 04
        if (this.formaPagamento04.isSelecionado()){
        	 valorSaldo=0.0;
             saldo = 0.0;
             valorEntrada=0.0;
             if (formaPagamento04.getPercentualEntrada()>0){
                 valorEntrada = total * (formaPagamento04.getPercentualEntrada().doubleValue()/100);
                 saldo = 100- formaPagamento04.getPercentualEntrada().doubleValue();
                 valorSaldo = total - valorEntrada;
                 formaPagamento04.setValorEntrada(valorEntrada.floatValue());
                 formaPagamento04.setValorSaldo(valorSaldo.floatValue());
                 formaPagamento04.setPercentualSaldo(saldo);
             }
             valorSaldo =0.0;
             if (formaPagamento04.getNumeroparcela()>0){
             	if(formaPagamento04.getValorSaldo()>0){
     	            CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
     	            Coeficientejuros  cf = coneficienteJurosFacade.consultar(formaPagamento04.getNumeroparcela(), "Juros Banco");
     	            Double valor = formaPagamento04.getValorSaldo().doubleValue() * cf.getCoeficiente();
     	            formaPagamento04.setValorparcela(valor.floatValue());
             	}else{
             		CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
         	        Coeficientejuros  cf = coneficienteJurosFacade.consultar(formaPagamento04.getNumeroparcela(), "Juros Banco");
         	        Double valor = total * cf.getCoeficiente();
         	        formaPagamento04.setValorparcela(valor.floatValue());
             	}
             }
         }
    }
	
	
	public void calcularParcelamentoValorEntrada() throws SQLException {
		Double valorSaldo = 0.0;
		Double saldo = 0.0;
		Double valorEntrada = 0.0;
		if (this.formaPagamento02.isSelecionado()) {
			if (formaPagamento02.getValorEntrada() > 0) {
				valorEntrada = formaPagamento02.getPercentualEntrada();
				float percentualentrada = (formaPagamento02.getPercentualEntrada().floatValue() / total.floatValue())
						* 100;
				String valor = Formatacao.formatarFloatString(percentualentrada);
				formaPagamento02.setPercentualEntrada(Formatacao.formatarStringDouble(valor));
				saldo = 100 - formaPagamento02.getPercentualEntrada();
				valorSaldo = total - valorEntrada;
				formaPagamento02.setValorEntrada(valorEntrada.floatValue());
				formaPagamento02.setValorSaldo(valorSaldo.floatValue());
				formaPagamento02.setPercentualSaldo(saldo);
			} else if (formaPagamento02.getPercentualEntrada() == 0) {
				valorEntrada = 0.0;
				saldo = 100.0;
				valorSaldo = total.doubleValue();
				formaPagamento02.setValorEntrada(valorEntrada.floatValue());
				formaPagamento02.setPercentualEntrada(0.00);
				formaPagamento02.setValorSaldo(valorSaldo.floatValue());
				formaPagamento02.setPercentualSaldo(saldo);
			}
			valorSaldo = 0.0;
			if (formaPagamento02.getNumeroparcela() > 1) {
				valorSaldo = formaPagamento02.getValorSaldo().doubleValue();
				if (valorSaldo > 0) {
					valorSaldo = valorSaldo / formaPagamento02.getNumeroparcela();
					formaPagamento02.setValorparcela(valorSaldo.floatValue());
				}
			}
		}

		// Opção 03

		if (this.formaPagamento03.isSelecionado()) {

			valorSaldo = 0.0;
			saldo = 0.0;
			valorEntrada = 0.0;

			if (formaPagamento03.getValorEntrada() > 0) {
				valorEntrada = formaPagamento03.getPercentualEntrada();
				float percentualentrada = (formaPagamento03.getPercentualEntrada().floatValue() / total.floatValue())
						* 100;
				String valor = Formatacao.formatarFloatString(percentualentrada);
				formaPagamento03.setPercentualEntrada(Formatacao.formatarStringDouble(valor));
				saldo = 100 - formaPagamento03.getPercentualEntrada();
				valorSaldo = total - valorEntrada;
				formaPagamento03.setValorEntrada(valorEntrada.floatValue());
				formaPagamento03.setValorSaldo(valorSaldo.floatValue());
				formaPagamento03.setPercentualSaldo(saldo);
			} else if (formaPagamento03.getPercentualEntrada() == 0) {
				valorEntrada = 0.0;
				saldo = 100.0;
				valorSaldo = total.doubleValue();
				formaPagamento03.setValorEntrada(valorEntrada.floatValue());
				formaPagamento03.setPercentualEntrada(0.00);
				formaPagamento03.setValorSaldo(valorSaldo.floatValue());
				formaPagamento03.setPercentualSaldo(saldo);
			}

			valorSaldo = 0.0;
			if (formaPagamento03.getNumeroparcela() > 0) {
				if (formaPagamento03.getValorSaldo() > 0) {
					valorSaldo = formaPagamento03.getValorSaldo().doubleValue();
					if (valorSaldo > 0) {
						CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
						Coeficientejuros cf = coneficienteJurosFacade.consultar(formaPagamento03.getNumeroparcela(), "Juros Cliente");
						valorSaldo = (double) (valorSaldo * cf.getCoeficiente());
						formaPagamento03.setValorparcela(valorSaldo.floatValue());
					}
				}
			}
		}

		// opção 04
		if (this.formaPagamento04.isSelecionado()) {
			valorSaldo = 0.0;
			saldo = 0.0;
			valorEntrada = 0.0;
			if (formaPagamento04.getValorEntrada() > 0) {
				valorEntrada = formaPagamento04.getPercentualEntrada();
				float percentualentrada = (formaPagamento04.getPercentualEntrada().floatValue() / total.floatValue())
						* 100;
				String valor = Formatacao.formatarFloatString(percentualentrada);
				formaPagamento04.setPercentualEntrada(Formatacao.formatarStringDouble(valor));
				saldo = 100 - formaPagamento04.getPercentualEntrada();
				valorSaldo = total - valorEntrada;
				formaPagamento04.setValorEntrada(valorEntrada.floatValue());
				formaPagamento04.setValorSaldo(valorSaldo.floatValue());
				formaPagamento04.setPercentualSaldo(saldo);
			} else if (formaPagamento04.getPercentualEntrada() == 0) {
				valorEntrada = 0.0;
				saldo = 100.0;
				valorSaldo = total.doubleValue();
				formaPagamento04.setValorEntrada(valorEntrada.floatValue());
				formaPagamento04.setPercentualEntrada(0.00);
				formaPagamento04.setValorSaldo(valorSaldo.floatValue());
				formaPagamento04.setPercentualSaldo(saldo);
			}
			valorSaldo = 0.0;
			if (formaPagamento04.getNumeroparcela() > 0) {
				if (formaPagamento04.getValorSaldo() > 0) {
					CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
					Coeficientejuros cf = coneficienteJurosFacade.consultar(formaPagamento04.getNumeroparcela(), "Juros Banco");
					Double valor = formaPagamento04.getValorSaldo().doubleValue() * cf.getCoeficiente();
					formaPagamento04.setValorparcela(valor.floatValue());
				} else {
					CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
					Coeficientejuros cf = coneficienteJurosFacade.consultar(formaPagamento04.getNumeroparcela(), "Juros Banco");
					Double valor = total * cf.getCoeficiente();
					formaPagamento04.setValorparcela(valor.floatValue());
				}
			}
		}
	}

	
	
        	 
	public String salvar() {
		List<FormaPagamentoBean> listaFormaPagamento = new ArrayList<FormaPagamentoBean>();
		FormaPagamentoBean forma;
		if (formaPagamento02.isSelecionado()){
			forma = new FormaPagamentoBean();
			forma.setNumeroParcelas(formaPagamento02.getNumeroparcela());
			forma.setObs1(formaPagamento02.getObservacao1());
			forma.setObs2(formaPagamento02.getObservacao2());
			forma.setPercentualEtnrada(formaPagamento02.getPercentualEntrada());
			forma.setPercentualSaldo(formaPagamento02.getPercentualSaldo());
			forma.setTitulo(formaPagamento02.getTitulo());
			forma.setValorentrada(formaPagamento02.getValorEntrada());
			forma.setValorParcela(formaPagamento02.getValorparcela());
			forma.setValorSaldo(formaPagamento02.getValorSaldo());
			listaFormaPagamento.add(forma);
		}
		
		if (formaPagamento03.isSelecionado()){
			forma = new FormaPagamentoBean();
			forma.setNumeroParcelas(formaPagamento03.getNumeroparcela());
			forma.setObs1(formaPagamento03.getObservacao1());
			forma.setObs2(formaPagamento03.getObservacao2());
			forma.setPercentualEtnrada(formaPagamento03.getPercentualEntrada());
			forma.setPercentualSaldo(formaPagamento03.getPercentualSaldo());
			forma.setTitulo(formaPagamento03.getTitulo());
			forma.setValorentrada(formaPagamento03.getValorEntrada());
			forma.setValorParcela(formaPagamento03.getValorparcela());
			forma.setValorSaldo(formaPagamento03.getValorSaldo());
			listaFormaPagamento.add(forma);
		}
		
		if (formaPagamento04.isSelecionado()){
			forma = new FormaPagamentoBean();
			forma.setNumeroParcelas(formaPagamento04.getNumeroparcela());
			forma.setObs1(formaPagamento04.getObservacao1());
			forma.setObs2(formaPagamento04.getObservacao2());
			forma.setPercentualEtnrada(formaPagamento04.getPercentualEntrada());
			forma.setPercentualSaldo(formaPagamento04.getPercentualSaldo());
			forma.setTitulo(formaPagamento04.getTitulo());
			forma.setValorentrada(formaPagamento04.getValorEntrada());
			forma.setValorParcela(formaPagamento04.getValorparcela());
			forma.setValorSaldo(formaPagamento04.getValorSaldo());
			listaFormaPagamento.add(forma);
		}
		if (listaFormaPagamento.size()>0){
			try {
				gerarRelatorio(listaFormaPagamento);
			} catch (SQLException | IOException e) {
				  
				Mensagem.lancarMensagemErro("Erro Sistema", "Erro gerar listar forma de pagamento");
			}
		}else {
			Mensagem.lancarMensagemInfo("Informação", "Sem dados para iprimir.");
		}
		
		return "";
	}
	
	public String voltar(){
		return "paginainicial";
	}
	 
	 
    public void gerarRelatorio(List<FormaPagamentoBean> listaFormaPagamento) throws SQLException, IOException {
	    String caminhoRelatorio = "/reports/financeiro/formaPagamento.jasper";  
	    Map<String, Object> parameters = new HashMap<String, Object>();
	    parameters.put("idocurso", 11);
	    parameters.put("total", Formatacao.formatarFloatString(total));
	    parameters.put("consultor", usuarioLogadoMB.getUsuario().getNome());
	    parameters.put("unidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getNomeFantasia());
	    String moedaNacional = usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla();
	    parameters.put("moedaNacional", moedaNacional);
	    ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	    File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
	    BufferedImage logo = ImageIO.read(f);  
	    parameters.put("logo", logo);
	    GerarRelatorio gerarRelatorio = new GerarRelatorio();
	    JRDataSource jrds = new JRBeanCollectionDataSource(listaFormaPagamento);
	    try {
	        gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, "FormaPagamento.pdf");
	    } catch (JRException ex) {
	        Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
	    } catch (IOException ex) {
	        Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    
    public void verificarFormaPgamento02(){
    	try {
			calcularParcelamento();
		} catch (SQLException e) {
			  
		}
    	if (formaPagamento02.isSelecionado()){
    		habilitaFormaPagamento02=false;
    	}else habilitaFormaPagamento02=true;
    }
    
    public void verificarFormaPgamento03(){
    	try {
			calcularParcelamento();
		} catch (SQLException e) {
			  
		}
    	if (formaPagamento03.isSelecionado()){
    		habilitaFormaPagamento03=false;
    	}else habilitaFormaPagamento03=true;
    }
    
    public void verificarFormaPgamento04(){
    	try {
			calcularParcelamento();
		} catch (SQLException e) {
			  
		}
    	if (formaPagamento04.isSelecionado()){
    		habilitaFormaPagamento04=false;
    	}else habilitaFormaPagamento04=true;
    }
}
