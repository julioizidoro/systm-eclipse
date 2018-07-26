package br.com.travelmate.converter;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
 
import br.com.travelmate.model.Tipocontato;

@FacesConverter(value = "TipoContatoConverter")
public class TipoContatoConverter implements Converter{

	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Tipocontato> listaTipocontato = (List<Tipocontato>) component.getAttributes().get("listaTipocontato");
        if (listaTipocontato != null) {
            for (Tipocontato tipocontato : listaTipocontato) {
                if (tipocontato.getTipo().equalsIgnoreCase(value)) {
                    return tipocontato;
                }
            }
        } else {
        	Tipocontato tipocontato = new Tipocontato();
            return tipocontato;
        }
        Tipocontato tipocontato = new Tipocontato();
        return tipocontato;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")) {
            return "Selecione";
        } else {
        	Tipocontato tipocontato = (Tipocontato) value;
            if (tipocontato.getIdtipocontato() != null) {
                return tipocontato.getTipo();
            } else {
                return "";
            }
        }
    }

}
