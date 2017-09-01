package br.com.travelmate.bean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

public class RelatorioErroBean {
	
	public void iniciarRelatorioErro(String erro){
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = "/reports/erro/reportErro.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("erro", erro);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "ERRO", null);
		} catch (JRException | IOException | SQLException e) {
			e.printStackTrace();
		}
	}

}
