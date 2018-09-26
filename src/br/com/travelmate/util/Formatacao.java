package br.com.travelmate.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import javax.swing.JComboBox;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.primefaces.model.UploadedFile;

import com.amazonaws.util.JodaTime;

import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.NotificacaoFacade;
import br.com.travelmate.facade.ParametrosProdutosFacade;
import br.com.travelmate.facade.UsuarioDepartamentoUnidadeFacade;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Notificacao;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Parametrosprodutos;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuariodepartamentounidade;

/**
 *
 * @author Wolverine
 */
public class Formatacao {

	private String emailSenha;
	private String emailUsuario;

	// nova classe

	public static String ConvercaoDataSql(Date data) {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String dataFormatada = df.format(data);
		return dataFormatada;
	}

	public static String ConvercaoDataDDMMAA(Date data) {
		DateFormat df = new SimpleDateFormat("ddMMyy");
		String dataFormatada = df.format(data);
		return dataFormatada;
	}

	public static String ConvercaoDataDDMMAAAA(Date data) {
		DateFormat df = new SimpleDateFormat("ddMMyyyy");
		String dataFormatada = df.format(data);
		return dataFormatada;
	}

	public static String ConvercaoDataPadrao(Date data) {
		if (data == null) {
			return null;
		}
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String dataFormatada = df.format(data);
		return dataFormatada;
	}

	public static String formatarFloatString(Float valor) {
		NumberFormat format = new DecimalFormat("#,###.##");
		format.setMinimumFractionDigits(2);
		String valorFormatado = format.format(valor);
		return valorFormatado;
	}
	
	public static String formatarFloatStringNumero(Float valor) {
		NumberFormat format = new DecimalFormat("#,###.##");
		format.setMinimumFractionDigits(0);
		String valorFormatado = format.format(valor);
		return valorFormatado;
	}

	public static String formatarFloatStringOutras(Float valor) {
		NumberFormat format = new DecimalFormat("#,###.####");
		format.setMinimumFractionDigits(2);
		String valorFormatado = format.format(valor);
		return valorFormatado;
	}

	public static String formatarDoubleString(Double valor) {
		NumberFormat format = new DecimalFormat("#,###.00");
		format.setMinimumFractionDigits(2);
		String valorFormatado = format.format(valor);
		return valorFormatado;
	}

	public static int formatarDouble(Double valor) {
		int valorFormatado = valor.intValue();
		return valorFormatado;
	}

	public static String formatarValorCambio(Float valor) {
		NumberFormat format = new DecimalFormat("#,##0.0000");
		format.setMinimumFractionDigits(4);
		String valorFormatado = format.format(valor);
		return valorFormatado;
	}

	public static Float formatarStringfloat(String valor) {
		String novoValor = "";
		for (int i = 0; i < valor.length(); i++) {
			if (valor.charAt(i) == ',') {
				novoValor = novoValor + ".";
			} else if (!(valor.charAt(i) == '.')) {
				novoValor = novoValor + valor.charAt(i);
			}
		}
		return Float.parseFloat(novoValor);
	}

	public static Double formatarStringDouble(String valor) {
		String novoValor = "";
		for (int i = 0; i < valor.length(); i++) {
			if (valor.charAt(i) == ',') {
				novoValor = novoValor + ".";
			} else if (!(valor.charAt(i) == '.')) {
				novoValor = novoValor + valor.charAt(i);
			}
		}
		return Double.parseDouble(novoValor);
	}

	public static String colcoarVirgulaValor(String valor) {
		String inteiro = valor.substring(0, valor.length() - 2);
		String decimal = valor.substring(valor.length() - 2, valor.length());
		return inteiro + "," + decimal;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static JComboBox preencherComobox(List<?> lista, JComboBox combo, boolean nulo, String valorNulo) {
		if (nulo) {
			combo.addItem(valorNulo);
		}
		for (int i = 0; i < lista.size(); i++) {
			combo.addItem(lista.get(i));
		}
		return combo;
	}

	public static Integer calcularNumeroSemanas(Date dataInicial, Date dataFinal) { 
		int resultado = 0;
		resultado = (int) ((dataFinal.getTime() - dataInicial.getTime()) / 86400000L);
		int numeroSemanas = (resultado % 7);
		if (numeroSemanas > 0) {
			resultado = resultado - numeroSemanas;
		}
		numeroSemanas = resultado / 7;
		numeroSemanas = numeroSemanas + 1;
		return numeroSemanas;
	}

	public static Date calcularDataFinal(Date dataInicial, int numeroSemanas) {
		Calendar c = new GregorianCalendar();
		c.setTime(dataInicial);
		numeroSemanas = numeroSemanas * 7;
		numeroSemanas = numeroSemanas - 1;
		if (numeroSemanas > 0) {
			c.add(Calendar.DAY_OF_MONTH, numeroSemanas);
		}
		return (c.getTime());
	}
	
	public static Date calcularDataFinalAcomodacao(Date dataInicial, int numeroSemanas) {
		Calendar c = new GregorianCalendar();
		c.setTime(dataInicial);
		numeroSemanas = numeroSemanas * 7;
		numeroSemanas = numeroSemanas - 1;
		if (numeroSemanas > 0) {
			c.add(Calendar.DAY_OF_MONTH, numeroSemanas);
		}
		return (c.getTime());
	}

	public static Date calcularDataFinalPorDias(Date dataInicial, int numerodias) {
		Calendar c = new GregorianCalendar();
		c.setTime(dataInicial);
		numerodias = numerodias - 1;
		if (numerodias > 0) {
			c.add(Calendar.DAY_OF_MONTH, numerodias);
		}
		return (c.getTime());
	}

	public static String valorPorExtenso(double vlr, String siglaMoeda) {
		if (vlr == 0)
			return ("ZERO");
		String ve = String.valueOf(vlr);
		String nve = "";
		for (int i = 0; i < ve.length(); i++) {
			if (ve.charAt(i) != '.') {
				nve = nve + ve.charAt(i);
			} else {
				int numeroI = ve.length() - i;
				if (numeroI > 3) {
					nve = nve + ve.charAt(i) + ve.substring(i + 1, i + 4);
				} else {
					if (numeroI == 2) {
						nve = nve + ve.charAt(i) + ve.substring(i + 1, i + 2);
					} else {
						if (numeroI == 3) {
							nve = nve + ve.charAt(i) + ve.substring(i + 1, i + 3);
						}
					}
				}

				i = 1000;
			}
		}
		vlr = Double.parseDouble(nve);
		long inteiro = (long) Math.abs(vlr); // parte inteira do valor
		double resto = vlr - inteiro; // parte fracionária do valor
		String vresto = String.valueOf(resto);
		if (vresto.length() > 5) {
			vresto = vresto.substring(0, 5);
		}
		double valorvResto = Double.parseDouble(vresto);
		if (valorvResto > 0.990) {
			resto = 0.0;
			inteiro = inteiro + 1;
		} else {
			resto = Double.parseDouble(vresto);
		}

		String vlrS = String.valueOf(inteiro);
		if (vlrS.length() > 15)
			return ("Erro: valor superior a 999 trilhões.");

		String s = "", saux, vlrP;
		String centavos = String.valueOf((int) Math.round(resto * 100));

		String[] unidade = { "", "UM", "DOIS", "TRÊS", "QUATRO", "CINCO", "SEIS", "SETE", "OITO", "NOVE", "DEZ", "ONZE",
				"DOZE", "TREZE", "QUATORZE", "QUINZE", "DEZESSEIS", "DEZESSETE", "DEZOITO", "DEZENOVE" };
		String[] centena = { "", "CENTO", "DUZENTOS", "TREZENTOS", "QUATROCENTOS", "QUINHENTOS", "SEISCENTOS",
				"SETECENTOS", "OITOCENTOS", "NOVECENTOS" };
		String[] dezena = { "", "", "VINTE", "TRINTA", "QUARENTA", "CINQUENTA", "SESSENTA", "SETENTA", "OITENTA",
				"NOVENTA" };
		String[] qualificaS = { "", "MIL", "MILHÃO", "BILHÃO", "TRILHÃO" };
		String[] qualificaP = { "", "MIL", "MILHÕES", "BILHÕES", "TRILHÕES" };

		// definindo o extenso da parte inteira do valor
		int n = 0, unid = 0, dez = 0, cent = 0, tam, i = 0;
		boolean umReal = false, tem = false;
		while (!vlrS.equals("0")) {
			tam = vlrS.length();
			// retira do valor a 1a. parte, 2a. parte, por exemplo, para
			// 123456789:
			// 1a. parte = 789 (centena)
			// 2a. parte = 456 (mil)
			// 3a. parte = 123 (milhões)
			if (tam > 3) {
				vlrP = vlrS.substring(tam - 3, tam);
				vlrS = vlrS.substring(0, tam - 3);
			} else { // última parte do valor
				vlrP = vlrS;
				vlrS = "0";
			}
			if (!vlrP.equals("000")) {
				saux = "";
				if (vlrP.equals("100"))
					saux = "CEM";
				else {
					n = Integer.parseInt(vlrP, 10); // para n = 371, tem-se:
					cent = n / 100; // cent = 3 (centena trezentos)
					dez = (n % 100) / 10; // dez = 7 (dezena setenta)
					unid = (n % 100) % 10; // unid = 1 (unidade um)
					if (cent != 0) {
						saux = centena[cent];
						if (saux.length() != 0 && (unid > 0 || dez > 0))
							saux = saux + " E ";
					}
//					else if(dez != 0) {
//						saux = unidade[n];
//					}
					int numero = n% 100;
					if ((numero) <= 19) {
						String valorUnidade = null;
							valorUnidade = unidade[n % 100];
							if (!valorUnidade.equalsIgnoreCase("0") && !valorUnidade.equalsIgnoreCase("") && !valorUnidade.equalsIgnoreCase(" ")) {
								saux = saux + " " + unidade[n % 100];
							}
					} else {
						if (saux.length() != 0)
							saux = saux  + dezena[dez];
						else
							saux = dezena[dez];
						if (unid != 0) {
							if (saux.length() != 0)
								saux = saux + " E " + unidade[unid];
							else
								saux = unidade[unid];
						}
					}
				}
				if (vlrP.equals("1") || vlrP.equals("001")) {
					if (i == 0) // 1a. parte do valor (um real)
						umReal = true;
					else
						saux = saux + " " + qualificaS[i];
				} else if (i != 0)
					saux = saux + " " + qualificaP[i];
				if ((s.length() != 0))
					s = saux + ", " + s;
				else if(i == 0)
					 s = saux + s;
				else 
					s = saux + " E " + s ;
			}
			if (((i == 0) || (i == 1)) && s.length() != 0)
				tem = true; // tem centena ou mil no valor
			i = i + 1; // próximo qualificador: 1- mil, 2- milhão, 3- bilhão,
						// ...
		}

		if (s.length() != 0) {
			
			s = retornarSiglaMoeda(siglaMoeda, s, umReal, tem);
		}

		// definindo o extenso dos centavos do valor
		if (!centavos.equals("0")) { // valor com centavos
			if (s.length() != 0) // se não é valor somente com centavosif (unid != 0)
				s = s + " E ";
				s =  retornarSiglaMoedaCentavo(siglaMoeda, s, centavos, n, unidade, unid, dez, dezena);
		}
		return (s);
	}
	
	public static String retornarSiglaMoeda(String sigla, String s, boolean umReal, boolean tem) {
		if (sigla.equalsIgnoreCase("USD")) {
			if (s.length() != 0) {
				if (umReal)
					s = s + " DÓLAR AMERICANO";
				else if (tem)
					s = s + " DÓLARES AMERICANOS";
				else
					s = s + " DE DÓLARES AMERICANOS";
			}
			return s;
		}else if(sigla.equalsIgnoreCase("R$")) {
			if (s.length() != 0) {
				if (umReal)
					s = s + " REAL";
				else if (tem)
					s = s + " REAIS";
				else
					s = s + " DE REAIS";
			}
			return s;
		}else if(sigla.equalsIgnoreCase("EUR")) {
			if (s.length() != 0) {
				if (umReal)
					s = s + " EURO";
				else if (tem)
					s = s + " EUROS";
				else
					s = s + " DE EUROS";
			}
			return s;
		}else if(sigla.equalsIgnoreCase("IATA")) {
			if (s.length() != 0) {
				if (umReal)
					s = s + " DÓLAR IATA";
				else if (tem)
					s = s + " DÓLARES IATA";
				else
					s = s + " DE DOLÁRES IATA";
			}
			return s;
		}else if(sigla.equalsIgnoreCase("GBP")) {
			if (s.length() != 0) {
				if (umReal)
					s = s + " LIBRA";
				else if (tem)
					s = s + " LIBRAS";
				else
					s = s + " DE LIBRAS";
			}
			return s;
		}else if(sigla.equalsIgnoreCase("AUD")) {
			if (s.length() != 0) {
				if (umReal)
					s = s + " DÓLAR AUSTRIALIANO";
				else if (tem)
					s = s + " DÓLARES AUSTRALIANOS";
				else
					s = s + " DE DÓLARES AUSTRALIANOS";
			}
			return s;
		}else if(sigla.equalsIgnoreCase("CAD")) {
			if (s.length() != 0) {
				if (umReal)
					s = s + " DOLÁR CANADENSE";
				else if (tem)
					s = s + " DÓLARES CANADENSES";
				else
					s = s + " DE DÓLARES CANADENSES";
			}
			return s;
		}else if(sigla.equalsIgnoreCase("NZD")) {
			if (s.length() != 0) {
				if (umReal)
					s = s + " DOLÁR NEOZELANDES";
				else if (tem)
					s = s + " DÓLARES NEOZELANDESES";
				else
					s = s + " DE DÓLARES NEOZELANDESES";
			}
			return s;
		}else if(sigla.equalsIgnoreCase("CHF")) {
			if (s.length() != 0) {
				if (umReal)
					s = s + " FRANCO SUIÇO";
				else if (tem)
					s = s + " FRANCOS SUIÇOS";
				else
					s = s + " DE FRANCOS SUIÇOS";
			}
			return s;
		}else if(sigla.equalsIgnoreCase("ZAR")) {
			if (s.length() != 0) {
				if (umReal)
					s = s + " RANDS";
				else if (tem)
					s = s + " RANDSES";
				else
					s = s + " DE RANDESES";
			}
			return s;
		}
		return "";
	}
	
	
	public static String retornarSiglaMoedaCentavo(String sigla, String s, String centavos, int n, String unidade[], int unid,
			int dez, String dezena[]) {
		if (sigla.equalsIgnoreCase("USD")) {
			if (centavos.equals("1"))
				s = s + " UM CENT";
			else {
				n = Integer.parseInt(centavos, 10);
				if (n <= 19)
					s = s + unidade[n];
				else { // para n = 37, tem-se:
					unid = n % 10; // unid = 37 % 10 = 7 (unidade sete)
					dez = n / 10; // dez = 37 / 10 = 3 (dezena trinta)

					s = s + dezena[dez];
					if (unid != 0)
						s = s + " E " + unidade[unid];
				}
				s = s + " CENTS";
			}
			return s;
		} else if (sigla.equalsIgnoreCase("R$")) {
			if (centavos.equals("1"))
				s = s + " UM CENTAVO";
			else {
				n = Integer.parseInt(centavos, 10);
				if (n <= 19)
					s = s + unidade[n];
				else { // para n = 37, tem-se:
					unid = n % 10; // unid = 37 % 10 = 7 (unidade sete)
					dez = n / 10; // dez = 37 / 10 = 3 (dezena trinta)

					s = s + dezena[dez];
					if (unid != 0)
						s = s + " E " + unidade[unid];
				}
				s = s + " CENTAVOS";
			}
			return s;
		} else if (sigla.equalsIgnoreCase("EUR")) {
			if (centavos.equals("1"))
				s = s + " UM CENT";
			else {
				n = Integer.parseInt(centavos, 10);
				if (n <= 19)
					s = s + unidade[n];
				else { // para n = 37, tem-se:
					unid = n % 10; // unid = 37 % 10 = 7 (unidade sete)
					dez = n / 10; // dez = 37 / 10 = 3 (dezena trinta)

					s = s + dezena[dez];
					if (unid != 0)
						s = s + " E " + unidade[unid];
				}
				s = s + " CENTS";
			}
			return s;
		} else if (sigla.equalsIgnoreCase("IATA")) {
			if (centavos.equals("1"))
				s = s + "UM CENT";
			else {
				n = Integer.parseInt(centavos, 10);
				if (n <= 19)
					s = s + unidade[n];
				else { // para n = 37, tem-se:
					unid = n % 10; // unid = 37 % 10 = 7 (unidade sete)
					dez = n / 10; // dez = 37 / 10 = 3 (dezena trinta)

					s = s + dezena[dez];
					if (unid != 0)
						s = s + " E " + unidade[unid];
				}
				s = s + " CENTS";
			}
			return s;
		} else if (sigla.equalsIgnoreCase("GBP")) {
			if (centavos.equals("1"))
				s = s + " UM PENNY";
			else {
				n = Integer.parseInt(centavos, 10);
				if (n <= 19)
					s = s + unidade[n];
				else { // para n = 37, tem-se:
					unid = n % 10; // unid = 37 % 10 = 7 (unidade sete)
					dez = n / 10; // dez = 37 / 10 = 3 (dezena trinta)

					s = s + dezena[dez];
					if (unid != 0)
						s = s + " E " + unidade[unid];
				}
				s = s + " PENNYS";
			}
			return s;
		} else if (sigla.equalsIgnoreCase("AUD")) {
			if (centavos.equals("1"))
				s = s + " UM CENT";
			else {
				n = Integer.parseInt(centavos, 10);
				if (n <= 19)
					s = s + unidade[n];
				else { // para n = 37, tem-se:
					unid = n % 10; // unid = 37 % 10 = 7 (unidade sete)
					dez = n / 10; // dez = 37 / 10 = 3 (dezena trinta)

					s = s + dezena[dez];
					if (unid != 0)
						s = s + " E " + unidade[unid];
				}
				s = s + " CENTS";
			}
			return s;
		} else if (sigla.equalsIgnoreCase("CAD")) {
			if (centavos.equals("1"))
				s = s + " UM CENT";
			else {
				n = Integer.parseInt(centavos, 10);
				if (n <= 19)
					s = s + unidade[n];
				else { // para n = 37, tem-se:
					unid = n % 10; // unid = 37 % 10 = 7 (unidade sete)
					dez = n / 10; // dez = 37 / 10 = 3 (dezena trinta)

					s = s + dezena[dez];
					if (unid != 0)
						s = s + " E " + unidade[unid];
				}
				s = s + " CENTS";
			}
			return s;
		} else if (sigla.equalsIgnoreCase("NZD")) {
			if (centavos.equals("1"))
				s = s + " UM CENT";
			else {
				n = Integer.parseInt(centavos, 10);
				if (n <= 19)
					s = s + unidade[n];
				else { // para n = 37, tem-se:
					unid = n % 10; // unid = 37 % 10 = 7 (unidade sete)
					dez = n / 10; // dez = 37 / 10 = 3 (dezena trinta)

					s = s + dezena[dez];
					if (unid != 0)
						s = s + " E " + unidade[unid];
				}
				s = s + " CENTS";
			}
			return s;
		} else if (sigla.equalsIgnoreCase("CHF")) {
			if (centavos.equals("1"))
				s = s + " UM CENT";
			else {
				n = Integer.parseInt(centavos, 10);
				if (n <= 19)
					s = s + unidade[n];
				else { // para n = 37, tem-se:
					unid = n % 10; // unid = 37 % 10 = 7 (unidade sete)
					dez = n / 10; // dez = 37 / 10 = 3 (dezena trinta)

					s = s + dezena[dez];
					if (unid != 0)
						s = s + " E " + unidade[unid];
				}
				s = s + " CENTS";
			}
			return s;
		} else if (sigla.equalsIgnoreCase("ZAR")) {
			if (centavos.equals("1"))
				s = s + " UM CENT";
			else {
				n = Integer.parseInt(centavos, 10);
				if (n <= 19)
					s = s + unidade[n];
				else { // para n = 37, tem-se:
					unid = n % 10; // unid = 37 % 10 = 7 (unidade sete)
					dez = n / 10; // dez = 37 / 10 = 3 (dezena trinta)

					s = s + dezena[dez];
					if (unid != 0)
						s = s + " E " + unidade[unid];
				}
				s = s + " CENTS";
			}
			return s;
		}
		return "";
	}

	public static boolean validaCPF(String s_aux) {
		String ncpf = "";
		for (int i = 0; i < s_aux.length(); i++) {
			if ((s_aux.charAt(i) != '.') && (s_aux.charAt(i) != '-')) {
				ncpf += s_aux.charAt(i);
			}
		}
		s_aux = ncpf;

		// ------- Rotina para CPF
		if (s_aux.length() == 11) {
			int d1, d2;
			int digito1, digito2, resto;
			int digitoCPF;
			String nDigResult;
			d1 = d2 = 0;
			digito1 = digito2 = resto = 0;
			for (int n_Count = 1; n_Count < s_aux.length() - 1; n_Count++) {
				digitoCPF = Integer.valueOf(s_aux.substring(n_Count - 1, n_Count)).intValue();
				// --------- Multiplique a ultima casa por 2 a seguinte por 3 a
				// seguinte por 4 e assim por diante.
				d1 = d1 + (11 - n_Count) * digitoCPF;
				// --------- Para o segundo digito repita o procedimento
				// incluindo o primeiro digito calculado no passo anterior.
				d2 = d2 + (12 - n_Count) * digitoCPF;
			}

			// --------- Primeiro resto da divisão por 11.
			resto = (d1 % 11);
			// --------- Se o resultado for 0 ou 1 o digito é 0 caso contrário o
			// digito é 11 menos o resultado anterior.
			if (resto < 2) {
				digito1 = 0;
			} else {
				digito1 = 11 - resto;
			}
			d2 += 2 * digito1;
			// --------- Segundo resto da divisão por 11.
			resto = (d2 % 11);
			// --------- Se o resultado for 0 ou 1 o digito é 0 caso contrário o
			// digito é 11 menos o resultado anterior.
			if (resto < 2) {
				digito2 = 0;
			} else {
				digito2 = 11 - resto;
			}
			// --------- Digito verificador do CPF que está sendo validado.
			String nDigVerific = s_aux.substring(s_aux.length() - 2, s_aux.length());
			// --------- Concatenando o primeiro resto com o segundo.
			nDigResult = String.valueOf(digito1) + String.valueOf(digito2);
			// --------- Comparar o digito verificador do cpf com o primeiro
			// resto + o segundo resto.
			return nDigVerific.equals(nDigResult);
		} // -------- Rotina para CNPJ
		else if (s_aux.length() == 14) {
			int soma = 0;
			int dig = 0;
			String cnpj_calc = s_aux.substring(0, 12);
			char[] chr_cnpj = s_aux.toCharArray();
			// --------- Primeira parte
			for (int i = 0; i < 4; i++) {
				if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
					soma += (chr_cnpj[i]) - 4 * (6 - (i + 1));
				}
			}
			for (int i = 0; i < 8; i++) {
				if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
					soma += (chr_cnpj[i + 4]) - 4 * (10 - (i + 1));
				}
			}
			dig = 11 - (soma % 11);
			cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);
			// --------- Segunda parte
			soma = 0;
			for (int i = 0; i < 5; i++) {
				if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
					soma += (chr_cnpj[i]) - 4 * (7 - (i + 1));
				}
			}
			for (int i = 0; i < 8; i++) {
				if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
					soma += (chr_cnpj[i + 5]) - 4 * (10 - (i + 1));
				}
			}
			dig = 11 - (soma % 11);
			cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);
			return s_aux.equals(cnpj_calc);
		} else {
			return false;
		}
	}

	public static Date SomarDiasDatas(Date data, int dias) throws Exception {
		Calendar c = new GregorianCalendar();
		c.setTime(data);
		c.add(Calendar.DAY_OF_MONTH, dias);
		return c.getTime();
	}

	public static String SubtarirDatas(Date data, int dias, String formato) {
		SimpleDateFormat sd = new SimpleDateFormat(formato);
		Calendar c = new GregorianCalendar();
		c.setTime(data);
		int dobroDias = dias * 2;
		int contaDias = dias - dobroDias;
		c.add(Calendar.DAY_OF_MONTH, contaDias);
		return sd.format(c.getTime());
	}

	public static String retornaDataInicia(int mes) {
		if (mes == 1) {
			return "01-01";
		} else if (mes == 2) {
			return "02-01";
		} else if (mes == 3) {
			return "03-01";
		} else if (mes == 4) {
			return "04-01";
		} else if (mes == 5) {
			return "05-01";
		} else if (mes == 6) {
			return "06-01";
		} else if (mes == 7) {
			return "07-01";
		} else if (mes == 8) {
			return "08-01";
		} else if (mes == 9) {
			return "09-01";
		} else if (mes == 10) {
			return "10-01";
		} else if (mes == 11) {
			return "11-01";
		} else if (mes == 12) {
			return "12-01";
		} else
			return "0";
	}
	
	
	public static String retornaDataFinalBrasil(int mes) {
		if (mes == 1) {
			return "31/01";
		} else if (mes == 2) {
			return "28/02";
		} else if (mes == 3) {
			return "31/03";
		} else if (mes == 4) {
			return "30/04";
		} else if (mes == 5) {
			return "31/05";
		} else if (mes == 6) {
			return "30/06";
		} else if (mes == 7) {
			return "31/07";
		} else if (mes == 8) {
			return "31/08";
		} else if (mes == 9) {
			return "30/09";
		} else if (mes == 10) {
			return "31/10";
		} else if (mes == 11) {
			return "30/11";
		} else if (mes == 12) {
			return "31/12";
		} else
			return "0";
	}

	public static String retornaDataFinal(int mes) {
		if (mes == 1) {
			return "01-31";
		} else if (mes == 2) {
			return "02-28";
		} else if (mes == 3) {
			return "03-31";
		} else if (mes == 4) {
			return "04-30";
		} else if (mes == 5) {
			return "05-31";
		} else if (mes == 6) {
			return "06-30";
		} else if (mes == 7) {
			return "07-31";
		} else if (mes == 8) {
			return "08-31";
		} else if (mes == 9) {
			return "09-30";
		} else if (mes == 10) {
			return "10-31";
		} else if (mes == 11) {
			return "11-30";
		} else if (mes == 12) {
			return "12-31";
		} else
			return "0";
	}

	public static String retornaDataFinalBarras(int mes) {
		if (mes == 1) {
			return "01/31";
		} else if (mes == 2) {
			return "02/28";
		} else if (mes == 3) {
			return "02/31";
		} else if (mes == 4) {
			return "04/30";
		} else if (mes == 5) {
			return "05/31";
		} else if (mes == 6) {
			return "06/30";
		} else if (mes == 7) {
			return "07/31";
		} else if (mes == 8) {
			return "08/31";
		} else if (mes == 9) {
			return "09/30";
		} else if (mes == 10) {
			return "10/31";
		} else if (mes == 11) {
			return "11/30";
		} else if (mes == 12) {
			return "12/31";
		} else
			return "0";
	}

	public static String nomeMes(int mes) {
		if (mes == 1) {
			return "JANEIRO";
		} else if (mes == 2) {
			return "FEVEREIRO";
		} else if (mes == 3) {
			return "MARÇO";
		} else if (mes == 4) {
			return "ABRIL";
		} else if (mes == 5) {
			return "MAIO";
		} else if (mes == 6) {
			return "JUNHO";
		} else if (mes == 7) {
			return "JULHO";
		} else if (mes == 8) {
			return "AGOSTO";
		} else if (mes == 9) {
			return "SETEMBRO";
		} else if (mes == 10) {
			return "OUTUBRO";
		} else if (mes == 11) {
			return "NOVEMBRO";
		} else if (mes == 12) {
			return "DEZEMBRO";
		} else
			return "nao encontrado";
	}

	public static int subtrairDatas(Date dataInicial, Date dataFinal) {
		if (dataInicial==null) {
			dataInicial = new Date();
		}
		DateTime dataI = new DateTime(dataInicial);
		DateTime dataF = new DateTime(dataFinal);
		int resultado = 0;
		//resultado = (int) ((dataFinal.getTime() - dataInicial.getTime()) / 86400000L);
		resultado = Days.daysBetween(dataI, dataF).getDays();
		return resultado;
	}
	
	public static int subtrairDatasC(Date dataInicial, Date dataFinal) {
		Calendar data = Calendar.getInstance();
		data.setTime(dataFinal);
		Calendar   inicio = Calendar.getInstance();
		inicio.setTime(dataInicial);
		int calendarioData = Calendar.DATE;
		int calendarioInicio = -inicio.get(Calendar.DAY_OF_MONTH);
		data.add(calendarioData, calendarioInicio);
		return data.get(Calendar.DAY_OF_MONTH);
	}
	
	

	public static String foramtarHoraString() {
		DateFormat formato = new SimpleDateFormat("HH:mm:ss");
		String formattedDate = formato.format(new Date());
		return formattedDate;
	}

	public static int retornaNumeroMes(String mes) {
		if (mes.equalsIgnoreCase("Janeiro")) {
			return 1;
		} else if (mes.equalsIgnoreCase("Fevereiro")) {
			return 2;
		} else if (mes.equalsIgnoreCase("Março")) {
			return 3;
		} else if (mes.equalsIgnoreCase("Abril")) {
			return 4;
		} else if (mes.equalsIgnoreCase("Maio")) {
			return 5;
		} else if (mes.equalsIgnoreCase("Junho")) {
			return 6;
		} else if (mes.equalsIgnoreCase("Julho")) {
			return 7;
		} else if (mes.equalsIgnoreCase("Agosto")) {
			return 8;
		} else if (mes.equalsIgnoreCase("Setembro")) {
			return 9;
		} else if (mes.equalsIgnoreCase("Outubro")) {
			return 10;
		} else if (mes.equalsIgnoreCase("Novembro")) {
			return 11;
		} else if (mes.equalsIgnoreCase("Dezembro")) {
			return 12;
		} else
			return 0;
	}

	public static int diaSemana(Date data) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		int diaSemana = calendario.get(Calendar.DAY_OF_WEEK);
		return diaSemana;
	}

	public static Date ConvercaoStringData(String data) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date dataFormatada = null;
		try {
			dataFormatada = df.parse(data);
		} catch (ParseException ex) {
			Logger.getLogger(Formatacao.class.getName()).log(Level.SEVERE, null, ex);
		}
		return dataFormatada;
	}

	public static Time converterStringHora(String hora) throws ParseException {
		SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
		Date data = formatador.parse(hora);
		Time time = new Time(data.getTime());
		return time;
	}

	public static int calcularIdade(Date dataNascimento) {
		if(dataNascimento!=null) {
			Calendar dataNascido = new GregorianCalendar();
			dataNascido.setTime(dataNascimento);
			Calendar dataAtual = Calendar.getInstance();
			int ano = dataAtual.get(Calendar.YEAR) - dataNascido.get(Calendar.YEAR);
			dataNascido.add(Calendar.YEAR, ano);
			if (dataAtual.before(dataNascido)) {
				ano--;
			}
			return ano;
		}return 0;
	}

	public static String getCaminho(String caminho) {
		// vamos obter o índice da última aparição do separador de caminho
		int pos = caminho.lastIndexOf(File.separatorChar);
		if (pos > -1) {
			return caminho.substring(0, pos);
		}
		// por padrão vamos retornar uma string vazia
		return "";
	}

	public static String carregarCambioDia() {
		String scambio = null;
		String data = Formatacao.ConvercaoDataSql(new Date());
		CambioFacade cambioFacade = new CambioFacade();
		List<Cambio> listaCambio = cambioFacade.listar(data);
		int contador = 0;
		if (listaCambio == null) {
			while (listaCambio == null) {
				try {
					data = Formatacao.SubtarirDatas(new Date(), contador, "yyyy/MM/dd");
				} catch (Exception ex) {
					Logger.getLogger(Formatacao.class.getName()).log(Level.SEVERE, null, ex);
				}
				listaCambio = cambioFacade.listar(data);
				contador++;
			}
		}
		String iata = "5,000";
		String usd = "0,000";
		String gbp = "0,000";
		String cad = "0,000";
		String aud = "0,000";
		String nzd = "0,000";
		String chf = "0,000";
		String euro = "0,000";
		if (listaCambio != null) {
			for (int i = 0; i < listaCambio.size(); i++) {
				if (listaCambio.get(i).getMoedas().getSigla().equalsIgnoreCase("IATA")) {
					iata = Formatacao.formatarValorCambio(listaCambio.get(i).getValor());
				} else if (listaCambio.get(i).getMoedas().getSigla().equalsIgnoreCase("USD")) {
					usd = Formatacao.formatarValorCambio(listaCambio.get(i).getValor());
				} else if (listaCambio.get(i).getMoedas().getSigla().equalsIgnoreCase("EUR")) {
					euro = Formatacao.formatarValorCambio(listaCambio.get(i).getValor());
				} else if (listaCambio.get(i).getMoedas().getSigla().equalsIgnoreCase("GBP")) {
					gbp = Formatacao.formatarValorCambio(listaCambio.get(i).getValor());
				} else if (listaCambio.get(i).getMoedas().getSigla().equalsIgnoreCase("cad")) {
					cad = Formatacao.formatarValorCambio(listaCambio.get(i).getValor());
				} else if (listaCambio.get(i).getMoedas().getSigla().equalsIgnoreCase("aud")) {
					aud = Formatacao.formatarValorCambio(listaCambio.get(i).getValor());
				} else if (listaCambio.get(i).getMoedas().getSigla().equalsIgnoreCase("nzd")) {
					nzd = Formatacao.formatarValorCambio(listaCambio.get(i).getValor());
				} else if (listaCambio.get(i).getMoedas().getSigla().equalsIgnoreCase("chf")) {
					chf = (Formatacao.formatarValorCambio(listaCambio.get(i).getValor()));
				}
			}
		}
		scambio = "IATA " + iata + "   UDS " + usd + "   EUR " + euro + "   GBP " + gbp + "   CAD " + cad + "   AUD "
				+ aud + "   NZD " + nzd + "   CHF " + chf;
		return scambio;
	}

	public static Date ConvercaoStringDataBrasil(String data) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date dataFormatada = null;
		try {
			dataFormatada = df.parse(data);
		} catch (ParseException ex) {
			Logger.getLogger(Formatacao.class.getName()).log(Level.SEVERE, null, ex);
		}
		return dataFormatada;
	}

	public static String gerarNumeroDocumentoBoleto(String numeroDoc) {
		String nossoNumero = "";
		if (numeroDoc.length()==5) {
			nossoNumero = "000" + numeroDoc;
		}else if (numeroDoc.length()==6) {
			nossoNumero = "00" + numeroDoc;
		}else if (numeroDoc.length()==7) {
			nossoNumero = "000" + numeroDoc;
		}else if (numeroDoc.length()==8) {
			nossoNumero= numeroDoc;
		}
		return nossoNumero;
	}

	public static BigDecimal converterFloatBigDecimal(Float valor) {
		Double dobuleValor = valor.doubleValue();
		BigDecimal bigDecimalValor = new BigDecimal(dobuleValor);
		return bigDecimalValor;
	}

	public static String retirarPontos(String dado) {
		String formatado = "";
		char c = ' ';
		if (dado != null) {
			for (int i = 0; i < dado.length(); i++) {
				c = dado.charAt(i);
				if ((c != '.') && (c != ',') && (c != '-') && (c != '/') && (c != '(') && (c != ')')) {
					formatado += c;
				}
			}
		}
		return formatado;
	}

	public static BufferedReader converterUploadedFileToFile(UploadedFile uploadedFile) throws Exception {
		InputStream is = uploadedFile.getInputstream();
		BufferedReader bfReader = null;
		bfReader = new BufferedReader(new InputStreamReader(is));
		return bfReader;
	}

	public static Date formatarDataAgora() {
		Date data = new Date();
		String sData = Formatacao.ConvercaoDataPadrao(data);
		data = Formatacao.ConvercaoStringData(sData);
		return data;
	}

	public static Date calcularPrevisaoPagamentoFornecedor(Date dataProduto, int idProduto, int idWork) {
		if (dataProduto == null) {
			return null;
		}
		String inicoProduto = Formatacao.ConvercaoDataPadrao(dataProduto);
		String dia = inicoProduto.substring(0, 2);
		String mes = inicoProduto.substring(3, 5);
		String ano = inicoProduto.substring(6, 10);
		int nmes = Integer.parseInt(mes);
		int nano = Integer.parseInt(ano);
		if (idProduto == idWork) {
			if (nmes == 2) {
				nmes = 12;
				nano = nano - 1;
			} else if (nmes == 1) {
				nmes = 11;
				nano = nano - 1;
			} else {
				nmes = nmes - 2;
			}
		} else {
			if (nmes == 1) {
				nmes = 12;
				nano = nano - 1;
			} else {
				nmes = nmes - 1;
			}
		}
		if (nmes < 10) {
			mes = "0" + String.valueOf(nmes);
		} else
			mes = String.valueOf(nmes);
		String novaData = dia + "/" + mes + "/" + String.valueOf(nano);
		return Formatacao.ConvercaoStringDataBrasil(novaData);
	}

	public static void VerificarCambioalterado(float valorCambio, String produto, String cliente, String unidade,
			String consultor, int idProduto, String valorVenda) {
		Formatacao formatacao = new Formatacao();
		String texto = "";
		texto = texto + "Produto           : " + produto + "\r\n";
		texto = texto + "Valor do Câmbio   : " + formatarFloatString(valorCambio) + "\r\n";
		texto = texto + "Unidade           : " + unidade + "\r\n";
		texto = texto + "Cliente           : " + cliente + "\r\n";
		texto = texto + "Consultor         : " + consultor + "\r\n";
		texto = texto + "Valor da Venda R$ : " + valorVenda;
		formatacao.enviarEmailCambio(texto);
	}

	public void enviarEmailCambio(String texto) {
		ParametrosProdutosFacade parametrosProdutosFacade = new ParametrosProdutosFacade();
		Parametrosprodutos parametrosprodutos = parametrosProdutosFacade.consultar();
		int contador = 0;
		if (parametrosprodutos != null) {
			if (parametrosprodutos.getEmailCambio01() != null) {
				contador += 1;
			}
			if (parametrosprodutos.getEmailCambio02() != null) {
				contador += 1;
			}
			if (parametrosprodutos.getEmailCambio03() != null) {
				contador += 1;
			}
			InternetAddress[] listaEmail = new InternetAddress[contador + 01];
			try {
				if (parametrosprodutos.getEmailCambio01() != null) {
					if (parametrosprodutos.getEmailCambio01().length() > 0) {
						listaEmail[1] = new InternetAddress(parametrosprodutos.getEmailCambio01());
					}
				}
				if (parametrosprodutos.getEmailCambio02() != null) {
					if (parametrosprodutos.getEmailCambio02().length() > 0) {
						listaEmail[2] = new InternetAddress(parametrosprodutos.getEmailCambio02());
					}
				}
				if (parametrosprodutos.getEmailCambio03() != null) {
					if (parametrosprodutos.getEmailCambio03().length() > 0) {
						listaEmail[3] = new InternetAddress(parametrosprodutos.getEmailCambio03());
					}
				}
			} catch (AddressException ex) {
				Logger.getLogger(Formatacao.class.getName()).log(Level.SEVERE, null, ex);
			}
			try {
				this.emailSenha = "20SimpleS78";
				this.emailUsuario = "ti@travelmate.com.br";
				Properties p = new Properties();
				p.put("mail.smtp.host", "smtp.travelmate.com.br");
				p.put("mail.smtp.port", 587);
				p.put("mail.smtp.auth", "true");

				Session session = Session.getDefaultInstance(p, new Auth());

				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("systm@travelmate.com.br"));

				msg.setRecipients(Message.RecipientType.TO, listaEmail);

				msg.setSubject("SysTM - Alteração Câmbio");
				msg.setText(texto);

				MimeMultipart mpRoot = new MimeMultipart("mixed");
				MimeMultipart mpContent = new MimeMultipart("alternative");
				MimeBodyPart contentPartRoot = new MimeBodyPart();
				contentPartRoot.setContent(mpContent);
				mpRoot.addBodyPart(contentPartRoot);

				// enviando texto
				MimeBodyPart mbp1 = new MimeBodyPart();
				mbp1.setText(texto);
				mpContent.addBodyPart(mbp1);

				msg.setContent(mpRoot);
				msg.saveChanges();

				Transport.send(msg);
			} catch (Exception e) {
			}
		}
	}

	private class Auth extends Authenticator {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(emailUsuario, emailSenha);
		}
	}

	public void enviarEmailProdutos(String para, String cc, String senha, String de, String assunto, String texto,
			String nomeArquivoFicha, String nomeArquivoContrato, String nomeArquivoRecibo) {
		int numeroEmail = 1;
		if (cc.length() > 0) {
			numeroEmail = numeroEmail + 1;
		}
		InternetAddress[] listaEmail = new InternetAddress[numeroEmail];
		try {
			if (para.length() > 0) {
				listaEmail[0] = new InternetAddress(para);
			}
			if (cc.length() > 0) {
				listaEmail[1] = new InternetAddress(cc);
			}
		} catch (AddressException ex) {
			Logger.getLogger(Formatacao.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println(ex);
		}
		try {
			this.emailUsuario = de;
			this.emailSenha = senha;
			Properties p = new Properties();
			p.put("mail.smtp.host", "smtp.travelmate.com.br");
			p.put("mail.smtp.port", 587);
			p.put("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(p, new Formatacao.Auth());

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(de));

			msg.setRecipients(Message.RecipientType.TO, listaEmail);

			msg.setSubject(assunto);
			// msg.setContent(texto, "text/html");
			msg.setText(texto);

			MimeMultipart mpRoot = new MimeMultipart("mixed");
			MimeMultipart mpContent = new MimeMultipart("alternative");
			MimeBodyPart contentPartRoot = new MimeBodyPart();
			contentPartRoot.setContent(mpContent);
			mpRoot.addBodyPart(contentPartRoot);

			// enviando texto
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(texto);
			mpContent.addBodyPart(mbp1);
			MimeBodyPart mbpAnexoFicha = new MimeBodyPart();
			MimeBodyPart mbpAnexoContrato = new MimeBodyPart();
			MimeBodyPart mbpAnexoRecibo = new MimeBodyPart();

			if (nomeArquivoFicha != null) {
				FileDataSource source = new FileDataSource(nomeArquivoFicha);
				mbpAnexoFicha.setDisposition(Part.ATTACHMENT);
				mbpAnexoFicha.setDataHandler(new DataHandler(source));
				mbpAnexoFicha.setFileName(source.getName());
				mpContent.addBodyPart(mbpAnexoFicha);
			}

			if (nomeArquivoContrato != null) {
				FileDataSource source = new FileDataSource(nomeArquivoContrato);
				mbpAnexoContrato.setDisposition(Part.ATTACHMENT);
				mbpAnexoContrato.setDataHandler(new DataHandler(source));
				mbpAnexoContrato.setFileName(source.getName());
				mpContent.addBodyPart(mbpAnexoContrato);
			}

			if (nomeArquivoRecibo != null) {
				FileDataSource source = new FileDataSource(nomeArquivoRecibo);
				mbpAnexoRecibo.setDisposition(Part.ATTACHMENT);
				mbpAnexoRecibo.setDataHandler(new DataHandler(source));
				mbpAnexoRecibo.setFileName(source.getName());
				mpContent.addBodyPart(mbpAnexoRecibo);
			}

			msg.setContent(mpRoot);
			msg.saveChanges();

			Transport.send(msg);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Email enviado com sucesso", ""));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void enviarEmailProduto(String para, String cc, String senha, String de, String assunto, String texto,
			String nomeArquivoFicha, String nomeArquivoContrato, String nomeArquivoRecibo) throws EmailException {
		SimpleEmail email = new SimpleEmail();
		email.setHostName("smtp.travelmate.com.br");
		email.addTo(para);
		if (!cc.equalsIgnoreCase("")) {
			email.addTo(cc);
		}
		email.setFrom("systm@travelmate.com.br");
		email.setSubject(assunto);
		email.setMsg(texto);
		email.setSmtpPort(587);
		email.setAuthentication(de, senha);
		email.send();
	}

	public static File criarArquivoOrcamento(String html, String idorcamento) throws FileNotFoundException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		File file = new File(servletContext.getRealPath("/orcamento" + idorcamento + ".html"));
		OutputStreamWriter conteudo = new OutputStreamWriter(new FileOutputStream(file),
				Charset.forName("UTF-8").newEncoder());
		try {
			PrintWriter gravarArq = new PrintWriter(conteudo);
			gravarArq.write(html);
			conteudo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	public static String gerarCopetencia(Date data) {
		String copetencia = "";
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		int mes = calendar.get(GregorianCalendar.MONTH) + 1;
		int ano = calendar.get(GregorianCalendar.YEAR);
		String smes = "";
		if (mes > 9) {
			smes = String.valueOf(mes);
		} else
			smes = "0" + String.valueOf(mes);
		copetencia = smes + "/" + String.valueOf(ano);
		return copetencia;

	}

	public static Cambio carregarCambioDia(List<Cambio> listaCambio, Moedas moeda, Pais pais) {
		if (listaCambio != null) {
			int idmoeda = moeda.getIdmoedas();
			for (int i = 0; i < listaCambio.size(); i++) {
				int idmoedacambio = listaCambio.get(i).getMoedas().getIdmoedas();
				if (idmoeda == idmoedacambio && listaCambio.get(i).getPais().getIdpais() == pais.getIdpais()) {
					return listaCambio.get(i);
				}
			}
		}
		return null;
	}
	
	public static Cambio carregarCambioDia(List<Cambio> listaCambio, int idmoeda) {
		if (listaCambio != null) {
			for (int i = 0; i < listaCambio.size(); i++) {
				int idmoedacambio = listaCambio.get(i).getMoedas().getIdmoedas();
				if (idmoeda == idmoedacambio) {
					return listaCambio.get(i);
				}
			}
		}
		return null;
	}

	public static void gravarNotificacaoVendas(String titulo, Unidadenegocio unidade, String cliente, String fornecedor,
			String datainicio, String consultor, String tipovenda, float valorVenda, float valorCambio, String moeda,
			String tipoOperacao, Departamento departamento, String imagem, String tipo) {
		String sql = "select u From Usuariodepartamentounidade u where u.unidadenegocio.idunidadeNegocio="+unidade.getIdunidadeNegocio()
				+ " and (u.tipo='T' or u.tipo='"+tipo + "') and (u.departamento.iddepartamento=12 or u.departamento.iddepartamento=9 or u.departamento.iddepartamento=1"
				+ " or u.departamento.iddepartamento=" + departamento.getIddepartamento()+")";
		UsuarioDepartamentoUnidadeFacade usuarioDepartamentoUnidadeFacade = new UsuarioDepartamentoUnidadeFacade();
		List<Usuariodepartamentounidade> lista = usuarioDepartamentoUnidadeFacade.listar(sql);
		NotificacaoFacade notificacaoFacade = new NotificacaoFacade();
		if (lista != null) {
			for (int i = 0; i < lista.size(); i++) {
				Notificacao notificacao = new Notificacao();
				notificacao.setTitulo(titulo);
				notificacao.setUnidade(unidade.getNomeFantasia());
				notificacao.setCliente(cliente);
				notificacao.setFornecedor(fornecedor);
				notificacao.setDatainicio(datainicio);
				notificacao.setConsultor(consultor);
				notificacao.setTipovenda(tipovenda);
				notificacao.setValorvenda(valorVenda);
				notificacao.setCambio(valorCambio);
				notificacao.setMoeda(moeda);
				notificacao.setLimpar(false);
				notificacao.setData(new Date());
				notificacao.setImagem(imagem);
				notificacao.setUsuario(lista.get(i).getUsuario());
				String hora = Formatacao.foramtarHoraString();
				notificacao.setHora(hora);
				notificacaoFacade.salvar(notificacao);
			}
		}
	}
	
	public static void gravarNotificacaoVendasFinanceiro(String titulo, Unidadenegocio unidade, String cliente, String fornecedor,
			String datainicio, String consultor, String tipovenda, float valorVenda, float valorCambio, String moeda,
			String tipoOperacao, String imagem, String tipo) {
		String sql = "select u From Usuariodepartamentounidade u where u.unidadenegocio.idunidadeNegocio="+unidade.getIdunidadeNegocio()
				+ " and (u.tipo='T' or u.tipo='"+tipo + "') and (u.departamento.iddepartamento=12 or u.departamento.iddepartamento=9 or u.departamento.iddepartamento=1"
				+ ")";
		UsuarioDepartamentoUnidadeFacade usuarioDepartamentoUnidadeFacade = new UsuarioDepartamentoUnidadeFacade();
		List<Usuariodepartamentounidade> lista = usuarioDepartamentoUnidadeFacade.listar(sql);
		NotificacaoFacade notificacaoFacade = new NotificacaoFacade();
		if (lista != null) {
			for (int i = 0; i < lista.size(); i++) {
				Notificacao notificacao = new Notificacao();
				notificacao.setTitulo(titulo);
				notificacao.setUnidade(unidade.getNomeFantasia());
				notificacao.setCliente(cliente);
				notificacao.setFornecedor(fornecedor);
				notificacao.setDatainicio(datainicio);
				notificacao.setConsultor(consultor);
				notificacao.setTipovenda(tipovenda);
				notificacao.setValorvenda(valorVenda);
				notificacao.setCambio(valorCambio);
				notificacao.setMoeda(moeda);
				notificacao.setLimpar(false);
				notificacao.setData(new Date());
				notificacao.setImagem(imagem);
				notificacao.setUsuario(lista.get(i).getUsuario());
				String hora = Formatacao.foramtarHoraString();
				notificacao.setHora(hora);
				notificacaoFacade.salvar(notificacao);
			}
		}
	}
	
	public static void gravarNotificacaoVendasFinalizado(String titulo, Unidadenegocio unidade, String cliente, String fornecedor,
			String datainicio, String consultor, String tipovenda, float valorVenda, float valorCambio, String moeda,
			String tipoOperacao, String imagem, String tipo, Departamento departamento) {
		String sql = "select u From Usuariodepartamentounidade u where u.unidadenegocio.idunidadeNegocio="+unidade.getIdunidadeNegocio()
				+ " and (u.tipo='T' or u.tipo='"+tipo + "') and u.departamento.iddepartamento=" + departamento.getIddepartamento();
		UsuarioDepartamentoUnidadeFacade usuarioDepartamentoUnidadeFacade = new UsuarioDepartamentoUnidadeFacade();
		List<Usuariodepartamentounidade> lista = usuarioDepartamentoUnidadeFacade.listar(sql);
		NotificacaoFacade notificacaoFacade = new NotificacaoFacade();
		if (lista != null) {
			for (int i = 0; i < lista.size(); i++) {
				Notificacao notificacao = new Notificacao();
				notificacao.setTitulo(titulo);
				notificacao.setUnidade(unidade.getNomeFantasia());
				notificacao.setCliente(cliente);
				notificacao.setFornecedor(fornecedor);
				notificacao.setDatainicio(datainicio);
				notificacao.setConsultor(consultor);
				notificacao.setTipovenda(tipovenda);
				notificacao.setValorvenda(valorVenda);
				notificacao.setCambio(valorCambio);
				notificacao.setMoeda(moeda);
				notificacao.setLimpar(false);
				notificacao.setData(new Date());
				notificacao.setImagem(imagem);
				notificacao.setUsuario(lista.get(i).getUsuario());
				String hora = Formatacao.foramtarHoraString();
				notificacao.setHora(hora);
				notificacaoFacade.salvar(notificacao);
			}
		}
	}

	public static boolean validarEmail(String email) {
		email = email.replaceAll(" ","");
		if (email.length() > 0) {
			Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
			Matcher m = p.matcher(email);
			if (m.find()) {
				return true;
			} else {
				Mensagem.lancarMensagemErro("O E-mail " + email + " é inválido", "");
				return false;
			}
		} else {
			Mensagem.lancarMensagemInfo("O E-mail é obrigatório", "");
			return false;
		}
	}

	public static int getAnoData(Date data) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		int ano = calendar.get(GregorianCalendar.YEAR);
		return ano;
	}

	public static int getMesData(Date data) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		int mes = calendar.get(GregorianCalendar.MONTH);
		return mes;
	}

	public static int getDiaData(Date data) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		int dia = calendar.get(GregorianCalendar.DAY_OF_MONTH);
		return dia;
	}

	public static int getPrimeiroDiaMes(int ano, int mes, int dia) {
		Calendar c = new GregorianCalendar(ano, mes, dia);
		return c.getMinimum(Calendar.DAY_OF_MONTH);
	}

	public static int getUltimoDiaMes(int ano, int mes, int dia) {
		Calendar c = new GregorianCalendar(ano, mes, dia);
		return c.getMaximum(Calendar.DAY_OF_MONTH);
	}

	public static Date getPrimeiroDiaSemana(Date data) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(data);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return calendar.getTime();
	}

	public static Date AdcionarDiaMesAnoData(Date data, int numero, String tipo) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		if (tipo.equalsIgnoreCase("d")) {
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + numero);
		} else if (tipo.equalsIgnoreCase("m")) {
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + numero);
		} else if (tipo.equalsIgnoreCase("a")) {
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + numero);
		}
		return calendar.getTime();
	}

	public static boolean calcularDataParcelamento(Date dataPrimeiroVencimento, int numeroParcelas, Date dataInicio) {
		int dias = (numeroParcelas - 1) * 30;
		dias = dias - 20;
		try {
			Date dataUltimoVencimento = Formatacao.SomarDiasDatas(dataPrimeiroVencimento, dias);
			if (dataUltimoVencimento.after(dataInicio)) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean veririfcarParcelamento(List<Parcelamentopagamento> lista) {
		if (lista != null) {
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i).isVerificarParcelamento()) {
					return true;
				}
			}
		}
		return false;
	}

	public static List<String> gerarListaTipoParcelamento(boolean parcelamentoLoja, String formapagamento,
			int idUnidade) {
		if (idUnidade == 1 || idUnidade == 2 || idUnidade == 6) {
			formapagamento = "sn";
		}
		List<String> listaTipoParcelamento = new ArrayList<String>();
		listaTipoParcelamento = new ArrayList<String>();
		String tipoparcelamento;
		if (!formapagamento.equalsIgnoreCase("Dinheiro")) {
			tipoparcelamento = "Matriz";
			listaTipoParcelamento.add(tipoparcelamento);
			tipoparcelamento = "Fornecedor";
			listaTipoParcelamento.add(tipoparcelamento);
			if (parcelamentoLoja) {
				tipoparcelamento = "Loja";
				listaTipoParcelamento.add(tipoparcelamento);
			}
		} else {
			tipoparcelamento = "Loja";
			listaTipoParcelamento.add(tipoparcelamento);
		}
		return listaTipoParcelamento;
	}

	public static boolean validarDataVenda(Date data) {
		String dataAtual = Formatacao.ConvercaoDataPadrao(new Date());
		String dataVenda = Formatacao.ConvercaoDataPadrao(data);
		if (dataVenda.equals(dataAtual)) {
			String hora = Formatacao.foramtarHoraString();
			hora = hora.substring(1, 2);
			int h = Integer.parseInt(hora);
			if (h<17){
				return true;
			}
		}
		return false;
	}

	public static String getMes() {
		String mes = "";
		Calendar cal = new GregorianCalendar();
		mes = "" + (cal.get(Calendar.MONDAY) + 1);
		if (mes.equalsIgnoreCase("1")) {
			mes = "JANEIRO";
		} else if (mes.equalsIgnoreCase("2")) {
			mes = "FEVEREIRO";
		} else if (mes.equalsIgnoreCase("3")) {
			mes = "MARÇO";
		} else if (mes.equalsIgnoreCase("4")) {
			mes = "ABRIL";
		} else if (mes.equalsIgnoreCase("5")) {
			mes = "MAIO";
		} else if (mes.equalsIgnoreCase("6")) {
			mes = "JUNHO";
		} else if (mes.equalsIgnoreCase("7")) {
			mes = "JULHO";
		} else if (mes.equalsIgnoreCase("8")) {
			mes = "AGOSTO";
		} else if (mes.equalsIgnoreCase("9")) {
			mes = "SETEMBRO";
		} else if (mes.equalsIgnoreCase("10")) {
			mes = "OUTUBRO";
		} else if (mes.equalsIgnoreCase("1")) {
			mes = "NOVEMBRO";
		} else if (mes.equalsIgnoreCase("12")) {
			mes = "DEZEMBRO";
		}
		return mes;
	}
	
	public static String validarDataBoleto(Date dataVencimento) throws Exception{
		int diaSemana = Formatacao.diaSemana(new Date());
		String sData = Formatacao.ConvercaoDataPadrao(dataVencimento);
		dataVencimento = Formatacao.ConvercaoStringData(sData);
		String dataIdealString = Formatacao.ConvercaoDataPadrao(new Date());
		Date dataIdeal = Formatacao.ConvercaoStringData(dataIdealString);
		String dataHojeString = Formatacao.ConvercaoDataPadrao(new Date());
		Date datahoje = Formatacao.ConvercaoStringData(dataHojeString);
		if (diaSemana==1){
			dataIdeal = Formatacao.SomarDiasDatas(datahoje, 1);
		}else if (diaSemana==7){
			dataIdeal = Formatacao.SomarDiasDatas(datahoje, 2);
		}else if (diaSemana==6){
			dataIdeal = Formatacao.SomarDiasDatas(datahoje, 3);
		}
		if (dataVencimento.before(dataIdeal)){
			return "Data primeiro vencimento tem que ser no próximo dia util \b\n";
		}
		return "";
	}
	
	public static String getSemana(int dia) { 
		String retorno = "";
		if (dia==0) {
			retorno = "Domingo";
		} else if (dia==1) {
			retorno = "Segunda-Feira";
		} else if (dia==2) {
			retorno = "Terça-Feira";
		} else if (dia==3) {
			retorno = "Quarta-Feira";
		} else if (dia==4) {
			retorno = "Quinta-Feira";
		} else if (dia==5) {
			retorno = "Sexta-Feira";
		} else if (dia==6) {
			retorno = "Sábado";
		}     
		return retorno;
	}
	
	public static String getSemanaIngles(String dia) { 
		String retorno = "";
		if (dia.equals("0")) {
			retorno = "sundayEnabled";
		} else if (dia.equals("1")) {
			retorno = "mondayEnabled";
		} else if (dia.equals("2")) {
			retorno = "tuesdayEnabled";
		} else if (dia.equals("3")) {
			retorno = "wednesdayEnabled";
		} else if (dia.equals("4")) {
			retorno = "thursdayEnabled";
		} else if (dia.equals("5")) {
			retorno = "fridayEnabled";
		} else if (dia.equals("6")) {
			retorno = "saturdayEnabled";
		}     
		return retorno;
	}
	
	public static String numeroMes() {
		String mes = "";
		Calendar cal = new GregorianCalendar();
		mes = "" + (cal.get(Calendar.MONDAY) + 1);
		if (mes.equalsIgnoreCase("1")) {
			mes = "01";
		} else if (mes.equalsIgnoreCase("2")) {
			mes = "02";
		} else if (mes.equalsIgnoreCase("3")) {
			mes = "03";
		} else if (mes.equalsIgnoreCase("4")) {
			mes = "04";
		} else if (mes.equalsIgnoreCase("5")) {
			mes = "05";
		} else if (mes.equalsIgnoreCase("6")) {
			mes = "06";
		} else if (mes.equalsIgnoreCase("7")) {
			mes = "07";
		} else if (mes.equalsIgnoreCase("8")) {
			mes = "08";
		} else if (mes.equalsIgnoreCase("9")) {
			mes = "09";
		} else if (mes.equalsIgnoreCase("10")) {
			mes = "10";
		} else if (mes.equalsIgnoreCase("1")) {
			mes = "11";
		} else if (mes.equalsIgnoreCase("12")) {
			mes = "12";
		}
		return mes;
	}
}
