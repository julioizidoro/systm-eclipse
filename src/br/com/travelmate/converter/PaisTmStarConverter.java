package br.com.travelmate.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.travelmate.model.Pais;


@FacesConverter(value="PaisTmStarConverter")
public class PaisTmStarConverter implements Converter{

	
	 	@SuppressWarnings("unchecked")
		@Override
	    public Object getAsObject(FacesContext context, UIComponent component, String value) {
	        List<Pais> listaPais = (List<Pais>) component.getAttributes().get("listaPais");
	        if (listaPais != null) {
	            for (Pais pais : listaPais) {
	                if (pais.getNome().equalsIgnoreCase(value)) {
	                    return pais;
	                }
	            }
	        } else {
	            return new Pais();
	        }
	        return null;
	    }

	    @Override
	    public String getAsString(FacesContext context, UIComponent component, Object value) {
	        if (value.toString().equalsIgnoreCase("0")){
	            return "Selecione";
	        }else {
	            if (value instanceof Pais){
	                Pais pais = (Pais) value;
	                return pais.getNome();
	            }else return "";
	        }
	    }
}
