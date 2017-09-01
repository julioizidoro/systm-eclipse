package br.com.travelmate.converter;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.travelmate.model.Tipoarquivo;

@FacesConverter(value = "TipoArquivoConverter")
public class TipoArquivoConverter implements Converter{

	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Tipoarquivo> listaTipoArquivo = (List<Tipoarquivo>) component.getAttributes().get("listaTipoArquivo");
        try {
        	value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        if (listaTipoArquivo != null) {
            for (Tipoarquivo tipoarquivo : listaTipoArquivo) {
                if (tipoarquivo.getDescricao().equalsIgnoreCase(value)) {
                    return tipoarquivo;
                }
            }
        } else {
        	Tipoarquivo tipoarquivo = new Tipoarquivo();
            return tipoarquivo;
        }
        Tipoarquivo tipoarquivo = new Tipoarquivo();
        return tipoarquivo;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")) {
            return "Selecione";
        } else {
        	Tipoarquivo tipoarquivo = (Tipoarquivo) value;
            if (tipoarquivo.getIdtipoArquivo() != null) {
                return tipoarquivo.getDescricao();
            } else {
                return "";
            }
        }
    }

}
