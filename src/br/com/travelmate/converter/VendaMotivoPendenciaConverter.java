package br.com.travelmate.converter;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.travelmate.model.Vendamotivopendencia;

@FacesConverter(value = "VendaMotivoPendenciaConverter")
public class VendaMotivoPendenciaConverter implements Converter{

    @SuppressWarnings("unchecked")
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			  
		}
    	List<Vendamotivopendencia> listaVendaMotivoPencencia = (List<Vendamotivopendencia>) component.getAttributes().get("listaVendaMotivoPencencia");
        for (Vendamotivopendencia vendamotivopendencia : listaVendaMotivoPencencia) {
                if (vendamotivopendencia.getDescricao().equalsIgnoreCase(value)) {
                    return vendamotivopendencia;
                }
            }
        return null;
    }
        
   @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")) {
            return "Selecione";
        } else {
        	Vendamotivopendencia vendamotivopendencia = (Vendamotivopendencia) value;
            return vendamotivopendencia.getDescricao();
        }
    }

}
