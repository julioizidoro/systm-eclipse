package br.com.travelmate.converter;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.travelmate.model.Fornecedorarquivotipo;

@FacesConverter(value = "FornecedorTipoArquivoConverter")
public class FornecedorTipoArquivoConverter implements Converter{

	@SuppressWarnings("unchecked")
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Fornecedorarquivotipo> listaTipoArquivo = (List<Fornecedorarquivotipo>) component.getAttributes().get("listaFornecedorTipoArquivo");
        try {
        	value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        if (listaTipoArquivo != null) {
            for (Fornecedorarquivotipo tipoarquivo : listaTipoArquivo) {
                if (tipoarquivo.getNome().equalsIgnoreCase(value)) {
                    return tipoarquivo;
                }
            }
        } else {
        	Fornecedorarquivotipo tipoarquivo = new Fornecedorarquivotipo();
            return tipoarquivo;
        }
        Fornecedorarquivotipo tipoarquivo = new Fornecedorarquivotipo();
        return tipoarquivo;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")) {
            return "Selecione";
        } else {
        	Fornecedorarquivotipo tipoarquivo = (Fornecedorarquivotipo) value;
            if (tipoarquivo.getIdfornecedorarquivotipo()!= null) {
                return tipoarquivo.getNome();
            } else {
                return "";
            }
        }
    }

}
