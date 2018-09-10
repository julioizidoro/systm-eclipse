package br.com.travelmate.managerBean.OrcamentoCurso;
 
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream; 
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;

import br.com.travelmate.dao.OcursoSeguroViagemDao;
import br.com.travelmate.model.Ocrusoprodutos;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.model.Ocursodesconto;
import br.com.travelmate.model.Ocursoformapagamento;
import br.com.travelmate.model.Ocursoseguro;
import br.com.travelmate.util.Formatacao;
   
public class SalvarPDForcamentoFinalizado {
	
	private List<Ocrusoprodutos> listaProdutos;
	private Ocurso ocurso;
	private String corpo;
	private float totalObrigatorio;
	private List<Ocursodesconto> listaDescontos;
	
	
	@Inject
	private OcursoSeguroViagemDao oCursoSeguroViagemDao;
	
	public SalvarPDForcamentoFinalizado(List<Ocrusoprodutos> listaProdutos, Ocurso ocurso, List<Ocursodesconto> listaDescontos) throws IOException, BadElementException {
		super();
		this.listaProdutos = listaProdutos;
		this.ocurso = ocurso;
		this.listaDescontos = listaDescontos;
		criarCorpoEmail();
		try {
			converter(corpo);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}

	
	public static void converter(String input) throws DocumentException, IOException{
        converter(new ByteArrayInputStream(input.getBytes()));
	}

	public static void converter(InputStream input) throws DocumentException, IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();  
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.setContentType("application/pdf");
        String nomeArquivo = "TravelMate";
        response.addHeader("Content-disposition", "attachment; filename=\"" + nomeArquivo + ".pdf\"");
        Tidy tidy =new Tidy();
    	Document doc = tidy.parseDOM(input, null);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(doc, null);
        renderer.layout();    
		renderer.createPDF(response.getOutputStream()); 
		// envia para o navegador o PDF gerado  
	    ServletOutputStream servletOutputStream = response.getOutputStream();  
		servletOutputStream.flush();  
        servletOutputStream.close();  
        facesContext.renderResponse();
        facesContext.responseComplete();
       
    }
	
	public void criarCorpoEmail() throws IOException, BadElementException {
		 corpo = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                 + "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv= \"Content-Type \" content= \"text/html; charset=utf-8 \" /><title>WebSysTM</title>\n"
                 + "                <style>  *{margin:0; padding:0; list-style: none; text-decoration: none;font-size: 14px; color: #333;}\n"
                 + "                .CSSTableGenerator {margin:0px;padding:0px;width:100%;box-shadow: 10px 10px 5px #888888;border:1px solid #ffffff;-moz-border-radius-bottomleft:0px;-webkit-border-bottom-left-radius:0px;border-bottom-left-radius:0px;-moz-border-radius-bottomright:0px;-webkit-border-bottom-right-radius:0px;border-bottom-right-radius:0px;-moz-border-radius-topright:0px;-webkit-border-top-right-radius:0px;border-top-right-radius:0px;-moz-border-radius-topleft:0px;-webkit-border-top-left-radius:0px;border-top-left-radius:0px;}.CSSTableGenerator table{border-collapse: collapse; border-spacing: 0;width:100%;height:100%;margin:0px;padding:0px;}.CSSTableGenerator tr:last-child td:last-child {-moz-border-radius-bottomright:0px;-webkit-border-bottom-right-radius:0px;border-bottom-right-radius:0px;}\n"
                 + "                .CSSTableGenerator table tr:first-child td:first-child {-moz-border-radius-topleft:0px;-webkit-border-top-left-radius:0px;border-top-left-radius:0px;}.CSSTableGenerator table tr:first-child td:last-child {-moz-border-radius-topright:0px;-webkit-border-top-right-radius:0px;border-top-right-radius:0px;}.CSSTableGenerator tr:last-child td:first-child{-moz-border-radius-bottomleft:0px;-webkit-border-bottom-left-radius:0px;border-bottom-left-radius:0px;}.CSSTableGenerator tr:hover td{}\n"
                 + "                .CSSTableGenerator tr:nth-child(odd){ background-color:#c4f2c4; }.CSSTableGenerator tr:nth-child(even)    { background-color:#ffffff; }.CSSTableGenerator td{vertical-align:middle;border:1px solid #ffffff;border-width:0px 1px 1px 0px;text-align:left;padding:6px;font-size:10px;font-family:Arial;font-weight:normal;color:#000000;}.CSSTableGenerator tr:last-child td{border-width:0px 1px 0px 0px;}.CSSTableGenerator tr td:last-child{\n"
                 + "        border-width:0px 0px 1px 0px;}.CSSTableGenerator tr:last-child td:last-child{border-width:0px 0px 0px 0px;}.CSSTableGenerator tr:first-child td{background:-o-linear-gradient(bottom, #AFCA0B 5%, #98FB98 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #8cd18c), color-stop(1, #0ab25b) );\n"
                 + "        background:-moz-linear-gradient( center top, #AFCA0B 5%, #98FB98 100% );filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=\"#8cd18c\", endColorstr=\"#0ab25b\");	background: -o-linear-gradient(top,#8cd18c,0ab25b);background-color:#8cd18c;border:0px solid #ffffff;text-align:center;border-width:0px 0px 1px 1px;font-size:14px;font-family:Arial;font-weight:bold;color:#ffffff;}\n"
                 + "                .CSSTableGenerator tr:first-child:hover td{background:-o-linear-gradient(bottom, #8cd18c 5%, #0ab25b 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #8cd18c), color-stop(1, #0ab25b) );background:-moz-linear-gradient( center top, #8cd18c 5%, #0ab25b 100% );filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=\"#8cd18c\", endColorstr=\"#0ab25b\");	background: -o-linear-gradient(top,#8cd18c,0ab25b);background-color:#8cd18c;}\n"
                 + "                .CSSTableGenerator tr:first-child td:first-child{border-width:0px 0px 1px 0px;}\n"
                 + "                .CSSTableGenerator tr:first-child td:last-child{border-width:0px 0px 1px 1px;}\n"
                 + "                </style></head><body><br/><br/><table border= \"0 \" cellspacing= \"0 \" cellpadding= \"0 \" style=\"margin-left:0%;margin-top:-20px\" width=\"100%\">  <tr><td></td></tr> <br></br>\n"
                 + "                  <tr><td><table border= \"0 \" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style= \"border:1px;border-style: solid;border-color:#AFCA0B;\">\n"
                 + "                      <tr> <tr><td><table border= \"0 \" cellspacing= \"0 \" cellpadding= \"0 \" width=\"100%\"><tr><td bgcolor= \"#AFCA0B\" style=\"text-align: center\"><strong style=\"font-size: 15px;color:#333\" >Orçamento de Curso</strong><div align= \"center \">\n"
                 + "                            </div></td></tr></table></td></tr><td width= \"100% \" bgcolor= \"\"></td></tr><tr> <td><table border= \"0 \" cellspacing= \"0 \" cellpadding= \"0 \" width= \"100% \"><tr><td >\n"
                 + "                            <br></br><tr >\n"
                 + "                                <td style=\"color:#AFCA0B;font-size:4px;background: #AFCA0B\">Curso </td>\n"
 		         + "                                <td style=\"color:#114A3D;text-align: right;background: #AFCA0B\"> </td> \n"
 		         + "                                <td style=\"color:#114A3D;text-align: right;background: #AFCA0B\"> </td> \n"
 		         + "                            </tr>\n"
                 + "                               <br></br><p class= \"curso-exterior \"><span class= \"curso-exterior \" style= \"margin-left: 5%;\">"
                 + "								<strong style= \"margin-left: 5%;color: #257C67\">Nome: </strong>" + ocurso.getCliente().getNome() + "\n"
                 + "                                <strong style= \"margin-left:20%;color: #257C67\">Data do orçamento:</strong> " + Formatacao.ConvercaoDataPadrao(ocurso.getDataorcamento()) + "<br />\n"
                 + "                                <strong style= \"margin-left: 5%;color: #257C67\">País:</strong> " + ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getNome() + "                                         "+"<strong style= \"margin-left: 5%;color: #257C67\">Cidade:</strong> " + ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getNome()+"                                          "+"<strong style= \"margin-left: 5%;color: #257C67\">Escola:</strong> " + ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor().getNome()+"<br />\n"
                 + "                                <strong style= \"margin-left: 5%;color: #257C67\">Tipo de curso:</strong> " + ocurso.getProdutosorcamento().getDescricao() + "<br />\n"
                 + "                                <strong style= \"margin-left: 5%;color: #257C67\">Período:</strong> " + Formatacao.ConvercaoDataPadrao(ocurso.getDatainicio()) + " á " + Formatacao.ConvercaoDataPadrao(ocurso.getDatatermino()) + "                                   Duração do curso:</strong> " + ocurso.getNumerosemanas() + " Semana (s)<br />\n"
                 + "                                <strong style= \"margin-left: 5%;color: #257C67\">Consultor:</strong> " + ocurso.getUsuario().getNome() + "<br /><br />\n"   
                 + "                          <br></br>\n";
         Float valorTotal=0.0f;
         if(listaProdutos!=null){
	    	 for (int i = 0; i < listaProdutos.size(); i++) {
	             if (listaProdutos.get(i).getValorcoprodutos().getCoprodutos().getTipo().equalsIgnoreCase("Obrigatorio")){
	            	 corpo = corpo + 								"<tr >\n"
	                         + "                                        <td style=\"color:#AFCA0B;font-size:2px;background: #AFCA0B;width:200px\">Curso </td>\n"
	                         + "                                        <td style=\"color:#114A3D;text-align: right;background: #AFCA0B;width:200px\"> </td> \n"
	                         + "                                        <td style=\"color:#114A3D;text-align: right;background: #AFCA0B;width: 90px\"> </td> \n"
	                         + "                                    </tr>\n"
	                         + "                                    <tr >\n"
	                         + "										<td style=\"color:#114A3D;width:200px \">" + listaProdutos.get(i).getValorcoprodutos().getCoprodutos().getNome() + "</td>\n"
	                         + "                                        <td style=\"color:#114A3D;width:200px \">" + listaProdutos.get(i).getValorcoprodutos().getCoprodutos().getDescricao() + "</td>\n"
	                         + "                                        <td style=\"color:#114A3D;text-align: right;width: 90px\">"+ ocurso.getCambio().getMoedas().getSigla()+" "+Formatacao.formatarFloatString(listaProdutos.get(i).getValorcoprodutos().getValororiginal()) + "</td> \n"
	                         + "                                    </tr>\n";
	                         
	                 if(valorTotal>0){
	                	 valorTotal = valorTotal + listaProdutos.get(i).getValorcoprodutos().getValororiginal()*ocurso.getNumerosemanas();
	                 }else{
	                	 valorTotal = listaProdutos.get(i).getValorcoprodutos().getValororiginal()*ocurso.getNumerosemanas();
	                 }
	                 totalObrigatorio = valorTotal;
	             }
	         }
	         corpo = corpo + "                </table><table style=\"width:40%;margin-left:420px;position:right\" class=\"CSSTableGenerator\">\n"
	                 + "                                    <tr >\n"
	                 + "                                        <td style=\"color:#257C67;font-size: 15px;background: #AFCA0B\">Total do Curso</td>\n"
	                 + "                                        <td style=\"font-size: 9px;background: #AFCA0B\"> </td>\n"
	                 + "                                    </tr>\n"
	                 + "                                    <tr >\n"
	                 + "                                        <td style=\"color:#114A3D;font-size: 12px\">Total</td>\n"
	                 + "                                        <td style=\"color:#114A3D;text-align: right;width:120px;font-size: 12px\">"+ocurso.getCambio().getMoedas().getSigla()+" "+Formatacao.formatarFloatString(totalObrigatorio)+"</td> \n"
	                 + "                                    </tr>\n"
	                 + "                                     <tr style=\"background:#fff \">\n"
	                 + "                                        <td style=\"color:#114A3D;font-size: 12px\">Total em Reais</td>\n"
	                 + "                                        <td style=\"color:#114A3D;text-align: right;width: 120px;font-size: 12px\">R$ "+Formatacao.formatarFloatString(totalObrigatorio*ocurso.getCambio().getValor())+"</td> \n"
	                 + "                                    </tr>\n"
	                 + "                                </table><br></br>\n";
	          for (int n = 0; n < listaProdutos.size(); n++) {
	             if (listaProdutos.get(n).getValorcoprodutos().getCoprodutos().getTipo().equalsIgnoreCase("Opcional")){
	                 corpo = corpo + "<strong style= \"margin-left:5.3%;font-size: 15px;margin-top: -3%;color: #257C67;\">Opcionais</strong><br />\n"
	                         + "                               <table style=\"width:100%;\" class=\"CSSTableGenerator\">\n"
	                         + "                                    <tr >\n"
	                         + "                                        <td style=\"color:#AFCA0B;font-size:4px;background: #AFCA0B\">Curso </td>\n"
	                         + "                                        <td style=\"color:#114A3D;text-align: right;background: #AFCA0B\"> </td> \n"
	                         + "                                        <td style=\"color:#114A3D;text-align: right;background: #AFCA0B\"> </td> \n"
	                         + "                                    </tr>\n"
	                         + "                                    <tr >\n"
	                         + "										   <td style=\"color:#114A3D;width:200px;font-size: 12px \">" + listaProdutos.get(n).getValorcoprodutos().getCoprodutos().getNome() + "</td>\n"
	                         + "                                        <td style=\"color:#114A3D;width:200px;font-size: 12px \">" + listaProdutos.get(n).getValorcoprodutos().getCoprodutos().getDescricao() + "</td>\n"
	                         + "                                        <td style=\"color:#114A3D;text-align: right;width: 90px;font-size: 12px\">"+ ocurso.getCambio().getMoedas().getSigla()+" "+Formatacao.formatarFloatString(listaProdutos.get(n).getValorcoprodutos().getValororiginal()) + "</td> \n"
	                         + "                                    </tr>\n"
	                         + "                                </table>\n"
	                         + "                                <br></br>";
	             }
	             if (listaProdutos.get(n).getValorcoprodutos().getCoprodutos().getTipo().equalsIgnoreCase("Acomodacao")){
	             	Float valorAcomodacao = 0.0f;
	             	if(valorAcomodacao==0.0f){
	             		valorAcomodacao= (float) (listaProdutos.get(n).getValororiginal());
	             	}
	                 corpo = corpo + "<br></br><strong style= \"margin-left:5.3%;font-size: 15px;color: #257C67;\">Acomodação</strong><br />\n"
	                         + "                                <table style=\"width:100%;\" class=\"CSSTableGenerator\">\n"
	                         + "                                    <tr >\n"
	                         + "                                        <td style=\"color:#AFCA0B;font-size:4px;background: #AFCA0B\">Acomodação</td>\n"
	                         + "                                        <td style=\"color:#114A3D;text-align: right;width: 90px;background: #AFCA0B\"> </td> \n"
	                         + "                                    </tr>\n"
	                         + "                                    <tr >\n"
	                         + "                                        <td style=\"color:#114A3D;font-size: 12px \">" + listaProdutos.get(n).getValorcoprodutos().getCoprodutos().getDescricao() + "</td>\n"
	                         + "                                        <td style=\"color:#114A3D;text-align: right;width: 90px;font-size: 12px\">"+ ocurso.getCambio().getMoedas().getSigla()+" "+Formatacao.formatarFloatString(valorAcomodacao) + "</td> \n"
	                         + "                                    </tr>\n"
	                         + "                                </table>\n"
	                         + "                                <br></br>";
	             }
	             if (listaProdutos.get(n).getValorcoprodutos().getCoprodutos().getTipo().equalsIgnoreCase("AcOpcional")){
	              	Float valorAcOpcional = 0.0f;
	              	if(valorAcOpcional==0.0f){
	              		valorAcOpcional= (float) (listaProdutos.get(n).getValororiginal()*listaProdutos.get(n).getNumerosemanas());
	              	}
	                  corpo = corpo + "<br></br><strong style= \"margin-left:5.3%;font-size: 15px;color: #257C67;\">Opcional de Acomodação</strong><br />\n"
	                          + "                                <table style=\"width:100%;\" class=\"CSSTableGenerator\">\n"
	                          + "                                    <tr >\n"
	                          + "                                        <td style=\"color:#AFCA0B;font-size:4px;background: #AFCA0B\">Acomodação</td>\n"
	                          + "                                        <td style=\"color:#114A3D;text-align: right;width: 90px;background: #AFCA0B\"> </td> \n"
	                          + "                                    </tr>\n"
	                          + "                                    <tr >\n"
	                          + "                                        <td style=\"color:#114A3D;font-size: 12px \">" + listaProdutos.get(n).getValorcoprodutos().getCoprodutos().getDescricao() + "</td>\n"
	                          + "                                        <td style=\"color:#114A3D;text-align: right;width: 90px;font-size: 12px\">"+ ocurso.getCambio().getMoedas().getSigla()+" "+Formatacao.formatarFloatString(valorAcOpcional) + "</td> \n"
	                          + "                                    </tr>\n"
	                          + "                                </table>\n"
	                          + "                                <br></br>";
	              }
	             if (listaProdutos.get(n).getValorcoprodutos().getCoprodutos().getTipo().equalsIgnoreCase("Transfer")) {
	                 corpo = corpo + "<br></br><strong style= \"margin-left:5.3%;;font-size: 15px;margin-top: -3%;color: #257C67;\">Transfer</strong><br />\n"
	                         + "                                <table style=\"width:100%;\" class=\"CSSTableGenerator\">\n"
	                         + "                                    <tr >\n"
	                         + "                                        <td style=\"color:#AFCA0B;font-size:4px;background: #AFCA0B\">Transfer</td>\n"
	                         + "                                        <td style=\"color:#114A3D;text-align: right;width: 90px;background: #AFCA0B\"> </td> \n"
	                         + "                                    </tr>\n"
	                         + "                                    <tr >\n"
	                         + "                                        <td style=\"color:#114A3D;font-size: 12px \">" + listaProdutos.get(n).getValorcoprodutos().getCoprodutos().getDescricao() + "</td>\n"
	                         + "                                        <td style=\"color:#114A3D;text-align: right;width: 90px;font-size: 12px\">"+ ocurso.getCambio().getMoedas().getSigla()+" "+Formatacao.formatarFloatString(listaProdutos.get(n).getValorcoprodutos().getValororiginal()) + "</td> \n"
	                         + "                                    </tr>\n"
	                         + "                                </table>\n"
	                         + "                                <br></br> <br></br>";
	             }
	         }
         }    
         
         //produtos extras
         Ocursoseguro ocursoseguro = new Ocursoseguro();
         ocursoseguro = oCursoSeguroViagemDao.consultar(ocurso.getIdocurso());
         if (ocursoseguro!=null){
              corpo = corpo + "<strong style= \"margin-left:5.3%;font-size: 15px;margin-top: -3%;color: #257C67;\">Seguro Viagem</strong><br />\n"
                      + "                               <table style=\"width:100%;\" class=\"CSSTableGenerator\">\n"
                      + "                                    <tr >\n"
                      + "                                        <td style=\"color:#AFCA0B;font-size:4px;background: #AFCA0B\">Curso </td>\n"
                      + "                                        <td style=\"color:#114A3D;text-align: right;background: #AFCA0B\"> </td> \n"
                      + "                                        <td style=\"color:#114A3D;text-align: right;background: #AFCA0B\"> </td> \n"
                      + "                                    </tr>\n"
                      + "                                    <tr >\n"
                      + "										  <td style=\"color:#114A3D;width:370px;font-size: 12px \">" + ocursoseguro.getSeguradora() + "</td>\n"
                      + "                                         <td style=\"color:#114A3D;width:200px;font-size: 12px \">" + ocursoseguro.getValoresseguro().getPlano() + "</td>\n"
                      + "                                         <td style=\"color:#114A3D;font-size: 12px\">" + ocursoseguro.getValoresseguro().getCobranca() + "</td> \n"
                      + "                                    </tr>\n"
                      + "                                    <tr >\n"
                      + "                                        <td style=\"color:#AFCA0B;font-size:4px;background: #AFCA0B\">Curso </td>\n"
                      + "                                        <td style=\"color:#114A3D;text-align: right;background: #AFCA0B\"> </td> \n"
                      + "                                    </tr>\n"
                      + "                                    <tr >\n"
                      + "										  <td style=\"color:#114A3D;width:;width:370px;font-size: 12px \">Data Inicial:"+Formatacao.ConvercaoDataPadrao(ocursoseguro.getDatainicial())+ " |  Numero de Dias:"+ ocursoseguro.getNumerodias() +" |  Data Final:" + Formatacao.ConvercaoDataPadrao(ocursoseguro.getDatafinal())+"</td> \n"
                      + "                                         <td style=\"color:#114A3D;text-align: right;width: 90px;font-size: 12px\">R$ "+Formatacao.formatarFloatString(ocursoseguro.getValor()) + "</td> \n"
                      + "                                    </tr>\n"
                      + "                                </table>\n"
                      + "                                <br></br>";
          }
         Float valorDesconto=0.0f;
         for (int d = 0; d < listaDescontos.size(); d++) {
        	 if(valorDesconto>0){
        		 valorDesconto = valorDesconto + listaDescontos.get(d).getValormoedanacional();
        	 }else{
        		 valorDesconto = listaDescontos.get(d).getValormoedanacional();
        	 }
         }
         if(valorDesconto>0){
        	 corpo = corpo + "<strong style= \"margin-left:5.3%;font-size: 15px;margin-top: -3%;color: #257C67;\">Descontos</strong><br />\n";
	         corpo = corpo + "<table style=\"width:100%;\" class=\"CSSTableGenerator\">\n"
	                 + "                                    <tr >\n"
	                 + "                                        <td style=\"color:#114A3D;text-align: right;background: #AFCA0B\"> </td> \n"
	                 + "                                    </tr>\n"
	                 + "                                    <tr >\n"
	                 + "									    <td style=\"color:#114A3D;text-align: right;width: 90px;font-size: 12px\">R$ " + Formatacao.formatarFloatString(valorDesconto) + "</td> \n"
	                 + "                                    </tr>\n"
	                 + "                                </table>\n"
	                 + "                                <br></br>";
         }
         corpo = corpo + "<table style=\"width:40%;margin-left:420px;\" class=\"CSSTableGenerator\">\n"
                 + "                                    <tr >\n"
                 + "                                        <td style=\"color:#257C67;font-size: 12px;background: #AFCA0B\">Total do Pacote</td>\n"
                 + "                                        <td style=\"font-size: 9px;background: #AFCA0B\"> </td>\n"
                 + "                                    </tr>\n"
                 + "                                    <tr >\n"
                 + "                                        <td style=\"color:#114A3D;font-size: 12px\">Total</td>\n"
                 + "                                        <td style=\"color:#114A3D;text-align: right;width:120px;font-size: 12px\">"+ocurso.getCambio().getMoedas().getSigla()+" "+Formatacao.formatarFloatString(ocurso.getTotalmoedaestrangeira())+"</td> \n"
                 + "                                    </tr>\n"
                 + "                                     <tr style=\"background:#fff \">\n"
                 + "                                        <td style=\"color:#114A3D;font-size: 12px\">Total em Reais</td>\n"
                 + "                                        <td style=\"color:#114A3D;text-align: right;width: 120px;font-size: 12px\">R$ "+Formatacao.formatarFloatString(ocurso.getTotalmoedanacional())+"</td> \n"
                 + "                                    </tr>\n"
                 + "                                </table><br></br><br></br>\n";
         /*if(resultadoOrcamentoBean.getValorPassagemAerea()>0 || resultadoOrcamentoBean.getValorVistoConsular()>0){
	         corpo = corpo + "                  <strong style= \"margin-left:5.3%;font-size: 15px;color: #257C67;\">Custos Extras</strong>\n"
	             + "                                 <table style=\"width:100%;\" class=\"CSSTableGenerator\"><tr ><td style=\"color:#AFCA0B;font-size:4px;background: #AFCA0B;margin-top:-10px\">Passagem Aérea</td></tr>\n";
	        if(resultadoOrcamentoBean.getValorPassagemAerea()>0){
	        	 corpo = corpo + "                      <tr >\n"
	             + "                                         <td style=\"color:#114A3D;font-size: 12px \"> Passagem Aérea:   Valor Entrada:  R$ " + Formatacao.formatarFloatString(resultadoOrcamentoBean.getValorPassagemAerea()) + "</td>\n"
	             + "                                    </tr> <br></br>\n";
	        }
	        if(resultadoOrcamentoBean.getValorVistoConsular()>0){
	        	corpo = corpo  + "                       <tr >\n"
	             + "                                         <td style=\"color:#114A3D;font-size: 12px \"> Visto Consular:   Valor Entrada:  R$ " + Formatacao.formatarFloatString(resultadoOrcamentoBean.getValorVistoConsular()) + "</td>\n"
	             + "                                    </tr> <br></br><br></br>\n";
	        }
         }*/
         corpo = corpo + "               <table style=\"width:89%;margin-left: 5%;\" class=\"CSSTableGenerator\">\n";
         	 corpo = corpo  + "                                    <tr >\n"
             + "                                    </tr>\n"
             + "                                    <tr >\n"
             + "                                        <td style=\"color:#114A3D;font-size:12px; \"><b><br></br>Pagamento á vista</b><br></br><br></br>\n"
             + "                                        Valor Entrada:  R$ " + Formatacao.formatarFloatString(ocurso.getTotalmoedanacional()) + "        |          Numero de Parcelas: 0        |           Valor Parcelas: R$ 0,00</td>\n"
             + "                                    </tr> <br></br>\n";
         for(int i=0;i<ocurso.getOcursoformapagamentoList().size();i++){
        	 Ocursoformapagamento formaPagamento = ocurso.getOcursoformapagamentoList().get(i);
             corpo = corpo+ "                                    <tr >\n"
             + "                                        <td style=\"color:#AFCA0B;font-size:4px;background: #AFCA0B\">Pagamento</td>\n"
             + "                                    </tr>\n"                
             + "                                    <tr >\n"
             + "                                        <td style=\"color:#114A3D;font-size:12px; \"><b>" + formaPagamento.getTitulo() + "</b><br></br><br></br>\n"
             + "                                        Valor Entrada:  R$ " + Formatacao.formatarFloatString(formaPagamento.getValorEntrada()) + "         |         Numero de Parcelas: " + formaPagamento.getNumeroparcela() + "       |            Valor Parcelas: R$ "+Formatacao.formatarFloatString(formaPagamento.getValorparcela())+"</td>\n"
             + "                                    </tr> <br></br>\n";
         }
         corpo = corpo + "                                </table>\n";
         corpo= corpo + "                                 <br></br>     <br></br>\n"
         + "</span></p></td><br></br> </tr></table> </td> </tr> <tr><td>\n"
         + "<div align= \"center \"><table border= \"0 \" cellspacing= \"0 \" cellpadding= \"0 \" width= \"100% \">\n"
         + "<tr> <td>&nbsp;</td> </tr></table></div></td> </tr> </table></td></tr></table><br></br><br></br><br></br></body></html>";
     }
  
}